package com.sigma.beacon_control.common.util;

import com.sigma.beacon_control.common.util.exceptions.UnsentEmailException;
import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.regex.Pattern;

/**
 * Created by Wilson on 5/2/17.
 */
public class EmailUtility {

    private static final String EMAIL_REGEX = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";
    private static final Logger log = LogManager.getLogger(EmailUtility.class);

    private EmailUtility() {
    }

    public static boolean validate(String email) {
        if (email == null || email.trim().isEmpty()) {
            return false;
        }

        Pattern pattern = Pattern.compile(EMAIL_REGEX);
        return pattern.matcher(email).matches();
    }

    public static void sendEmail(String email, String subject, String htmlContent) throws UnsentEmailException {
        try {
            HtmlEmail htmlEmail = new HtmlEmail();
            htmlEmail.setHostName(Config.getString("email.hostname"));
            htmlEmail.setSmtpPort(Config.getInt("email.port"));
            htmlEmail.setAuthenticator(
                    new DefaultAuthenticator(Config.getString("email.user"),
                            Config.getString("email.password"))
            );

            htmlEmail.setSSL(true);
            htmlEmail.setFrom(Config.getString("email.from"));
            htmlEmail.setSubject(subject);
            htmlEmail.setHtmlMsg(htmlContent);
            htmlEmail.addTo(email);
            htmlEmail.send();
        } catch (EmailException ex) {
            log.error("Error: ", ex);
            throw new UnsentEmailException("email could not be sent", 500);
        }
    }
}
