package eus.evernature.evern.service.mail;

public interface MailService {
    public void sendEmail(String email, String mailText, String subject);
    public String loadMail(String mailName, String resetPasswordLink);
}
