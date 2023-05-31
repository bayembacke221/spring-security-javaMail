package sn.bayembacke.test.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import sn.bayembacke.test.be.UserEntity;
import sn.bayembacke.test.be.Users;
import sn.bayembacke.test.dao.UserRepository;
import sn.bayembacke.test.service.UserSevice;
import sn.bayembacke.test.service.mail.MailService;

import javax.xml.bind.ValidationException;
import java.util.Set;

@Service
public class UserServiceImpl implements UserSevice {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private MailService mailService;
    @Override
    public Users saveUser(Users user) throws ValidationException {
        if (userRepository.findByUsername(user.getUsername()).isPresent())
            throw new ValidationException("User already exist");

        if(user.getRoles() == null){
            user.setRoles(Set.of("USER_ADMIN").toString());
        }

        Users request = user;

        Users compteToSend = new Users();

        compteToSend.setUsername(request.getUsername());
        compteToSend.setPassword(request.getPassword());
        compteToSend.setEmail(request.getEmail());
        compteToSend.setRoles(request.getRoles());

        if (request!=null){

            mailService.sendEmail(compteToSend);
            request.setPassword(passwordEncoder.encode(request.getPassword()));

        }

        return userRepository.save(request);
    }
}
