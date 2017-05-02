package com.netcracker.crm.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

/**
 * Created by Pasha on 21.04.2017.
 */
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private AuthenticationSuccessHandler successHandler;
    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private AccessDeniedHandler deniedHandler;
    @Autowired
    private AuthenticationProvider authenticationProvider;
    @Autowired
    private Environment env;
    @Autowired
    private PersistentTokenRepository tokenRepository;

    @Autowired
    public void configAuthentication(AuthenticationManagerBuilder auth) throws Exception {
        if (env.acceptsProfiles("!production")) {
            auth.inMemoryAuthentication().withUser("admin@gmail.com").password("123456").roles("ADMIN");
            auth.inMemoryAuthentication().withUser("csr@gmail.com").password("123456").roles("CSR");
            auth.inMemoryAuthentication().withUser("pmg@gmail.com").password("123456").roles("PMG");
            auth.inMemoryAuthentication().withUser("customer@gmail.com").password("123456").roles("CUSTOMER");
        } else {
            auth.authenticationProvider(authenticationProvider).userDetailsService(userDetailsService);
        }

    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/forgot**").permitAll()
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
                .rememberMe().tokenRepository(tokenRepository)
                .tokenValiditySeconds(1209600)
        ;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Bean
    public RedirectStrategy redirectStrategy() {
        return new DefaultRedirectStrategy();
    }
}
