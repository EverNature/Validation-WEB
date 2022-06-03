package eus.evernature.evern.service.mail;

import java.io.UnsupportedEncodingException;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

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
     * @param email
     * @param resetPasswordLink
     * @param subject
     * @param text
     * @throws RuntimeException
     */
    @Override
    public void sendEmail(String email, String resetPasswordLink, String subject, String text) throws RuntimeException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        try {
            helper.setFrom("evernature.solutions@gmail.com", "Evern team");
            helper.setTo(email);
            helper.setSubject(subject);
            helper.setText(text);
        } catch (UnsupportedEncodingException | MessagingException e) {
            log.error("Error while sending email to {}", email);
            throw new RuntimeException(e.getMessage());
        }

        mailSender.send(message);
    }

}
