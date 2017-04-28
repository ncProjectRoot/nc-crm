package com.netcracker.crm.email.senders;

import com.netcracker.crm.exception.IncorrectEmailElementException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;

import javax.mail.MessagingException;
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
@PropertySource("classpath:email.properties")
public abstract class AbstractEmailSender {

    private static final Logger log = LoggerFactory.getLogger(AbstractEmailSender.class);

    private static final String TEMPL_PACKAGE = "email";

    public abstract void send(EmailMap emailMap) throws MessagingException, IncorrectEmailElementException;
    protected abstract void checkEmailMap(EmailMap emailMap) throws IncorrectEmailElementException;

    public String getTemplate(String template) {
        log.debug("Getting email template " + TEMPL_PACKAGE + "/" + template);
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
        } else {
            log.error("File " + TEMPL_PACKAGE + "/" + template + " not found");
        }
        return stringBuilder.toString();
    }

}
