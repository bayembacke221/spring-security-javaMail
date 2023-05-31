package sn.bayembacke.test.service.mail;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import sn.bayembacke.test.be.Users;

@Component
public class MailService {

    @Autowired
    private JavaMailSender javaMailSender;

    /**
     * This function is used to send mail without attachment.
     * @param compte
     * @throws MailException
     */
    public void sendEmail(Users compte) throws MailException {
        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setTo(compte.getEmail());
        mail.setSubject("Activation compte !!! ");
        mail.setText("Compte activé avec succès. "
                + " Veuillez noter vos identifiants de connexion au site Dara j : "
                + " username => " + compte.getUsername() + ""
                + " password => " + compte.getPassword());
        /*
         * This send() contains an Object of SimpleMailMessage as an Parameter
         */
        javaMailSender.send(mail);
    }
}

