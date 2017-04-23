package com.netcracker.crm.config;

import com.netcracker.crm.security.PersistentTokenRepositoryImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import javax.sql.DataSource;

/**
 * Created by Pasha on 21.04.2017.
 */
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private DataSource dataSource;
    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private AuthenticationSuccessHandler successHandler;
    @Autowired
    private AccessDeniedHandler deniedHandler;

    @Autowired
    public void configAuthentication(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/").access("hasAnyRole('ROLE_ADMIN', 'ROLE_CSR', 'ROLE_CUSTOMER', 'ROLE_PMG')")
                .antMatchers("/admin/**").access("hasRole('ROLE_ADMIN')")
                .antMatchers("/pmg/**").access("hasRole('ROLE_PMG')")
                .antMatchers("/csr/**").access("hasRole('ROLE_CSR')")
                .antMatchers("/customer/**").access("hasRole('ROLE_CUSTOMER')")
                .and()
                .formLogin()
                .loginPage("/login").permitAll()
                .failureUrl("/login?error")
                .usernameParameter("email").passwordParameter("password")
                .successHandler(successHandler)
                .permitAll()
                .and()
                .logout().logoutSuccessUrl("/login?logout").deleteCookies("JSESSIONID")
                .and()
                .exceptionHandling().accessDeniedHandler(deniedHandler)
                .and()
                .csrf()
                .and()
                .rememberMe().tokenRepository(persistentTokenRepository())
                .tokenValiditySeconds(1209600)
        ;
    }

    @Bean
    public PersistentTokenRepository persistentTokenRepository() {
        PersistentTokenRepositoryImpl db = new PersistentTokenRepositoryImpl();
        db.setDataSource(dataSource);
        return db;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
