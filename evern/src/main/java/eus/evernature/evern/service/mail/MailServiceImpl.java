package eus.evernature.evern.service.mail;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@NoArgsConstructor
@Transactional
@Slf4j
public class MailServiceImpl implements MailService {

    @Autowired
    private JavaMailSender mailSender;

    /**
     * Esta función envia un correo de recuperación de contraseña a un usuario
     * 
     * @param email
     * @param resetPasswordLink
     * @param subject
     * @param text
     * @throws RuntimeException
     */
    @Override
    public void sendEmail(String email, String mailText, String subject) throws RuntimeException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        try {
            helper.setFrom("evernature.solutions@gmail.com", "Evern team");
            helper.setTo(email);
            helper.setSubject(subject);
            helper.setText(mailText);
        } catch (UnsupportedEncodingException | MessagingException e) {
            log.error("Error while sending email to {}", email);
            throw new RuntimeException(e.getMessage());
        }

        mailSender.send(message);
        
        log.info("Email correctly sent to {}", email);
    }

    @Override
    public String loadMail(String mailName, String resetPasswordLink) throws RuntimeException {

        String mail = "";

        try {
            File fileMail = loadHtmlWithInternalClass(mailName);

            BufferedReader in = new BufferedReader(new FileReader(fileMail.getPath()));
            String str;
            while ((str = in.readLine()) != null) {
                mail += str;
            }
            in.close();
        } catch (IOException e) {
            log.error("Error while loading mail {}", mailName);
            throw new RuntimeException(e.getMessage());
        }

        log.info("Email {} correctly loaded", mailName);
        mail = mail.replace("REPLACEME", resetPasswordLink);

        return mail;
    }

    public File loadHtmlWithInternalClass(String mailName)
            throws FileNotFoundException {
        return ResourceUtils.getFile(
                "classpath:resources/static/mail/" + mailName);
    }

}
