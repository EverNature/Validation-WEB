package eus.evernature.evern.service.mail;

public interface MailService {
    public void sendEmail(String email, String resetPasswordLink, String subject, String text);
}
