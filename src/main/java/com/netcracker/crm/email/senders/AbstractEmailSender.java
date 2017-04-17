package com.netcracker.crm.email.senders;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

/**
 * @author Melnyk_Dmytro
 * @version 1.0
 * @since 17.04.2017
 */

@ConfigurationProperties("mail.templates")
@PropertySource("classpath:application.properties")
public abstract class AbstractEmailSender {

    private static final String TEMPL_PACKAGE = "templates";

    public String getTemplate(String template) {
        StringBuilder stringBuilder = new StringBuilder();
        File file = new File(getClass().getClassLoader().getResource(TEMPL_PACKAGE + "/" + template).getFile());
        if (file.exists()) {
            try {
                List<String> lines = Files.readAllLines(Paths.get(file.getCanonicalPath()));
                for (String line : lines) {
                    stringBuilder.append(line);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return stringBuilder.toString();
    }

}
