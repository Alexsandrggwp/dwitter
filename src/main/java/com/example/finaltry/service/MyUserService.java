package com.example.finaltry.service;

import com.example.finaltry.model.User;
import com.example.finaltry.model.UserDetailsImpl;
import com.example.finaltry.repository.UserRepositoryImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MyUserService implements UserDetailsService {

    @Autowired
    private UserRepositoryImpl repository;

    @Autowired
    private BCryptPasswordEncoder encoder;

    @Autowired
    private MailSender mailSender;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = repository.findByEmail(email);
        if (user == null) {
            throw new UsernameNotFoundException("Could not find user with name '" + email + "'");
        }
        return new UserDetailsImpl(user);
    }

    public boolean addUser(User user){
        User userFromDB = repository.findByEmail(user.getEmail());
        if(userFromDB != null){
            return false;
        }
        user.setPassword(encoder.encode(user.getPassword()));
        user.setEnable(false);
        repository.save(user);
        String message = String.format(
                "Hello %s! \n" +
                        "Welcome to HobNob. Visit next link to activate your account : \n" +
                        "http://localhost:8080/activate/%s",
                user.getUsername(),
                user.getActivationCode()
        );
        mailSender.send(user.getEmail(),"Activation code", message);
        return true;
    }

    public boolean activateUser(String code) {
        User user = repository.findByActivationCode(code);
        if(user == null){
            return false;
        }
        user.setActivationCode(null);
        user.setEnable(true);
        repository.save(user);
        return true;
    }

    public void update(User user){
        user.setPassword(encoder.encode(user.getPassword()));
        repository.save(user);
    }

    public void save(User user){
        repository.save(user);
    }

    public User findById(int id){
        return repository.findById(id);
    }

    public User findByEmail(String email){
        return repository.findByEmail(email);
    }

    public User findByActivationCode(String code){
        return repository.findByActivationCode(code);
    }

    public List<User> findByNameOrSurname(String name){
        List<User> array = new ArrayList<>();
        array.addAll(repository.findBySurname(name));
        if(!repository.findBySurname(name).equals(repository.findByUsername(name))) {
            array.addAll(repository.findByUsername(name));
        }
        return array;
    }
}
