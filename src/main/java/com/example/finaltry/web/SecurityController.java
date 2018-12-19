package com.example.finaltry.web;

import com.example.finaltry.model.User;
import com.example.finaltry.service.MailSender;
import com.example.finaltry.service.MyUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.thymeleaf.util.StringUtils;

import javax.validation.Valid;
import java.util.Map;
import java.util.UUID;

@Controller
public class SecurityController {

    @Autowired
    MyUserService service;

    @Autowired
    MailSender sender;

    @GetMapping(value = "/login")
    public String getLogin(
            @RequestParam(value = "error", required = false) String error,
            @RequestParam(value = "login", required = false) String login,
            Model model
    ){
        model.addAttribute("error", error!=null);
        model.addAttribute("login", login!=null);
        return "login";
    }

    @GetMapping(value = "/registration")
    public String  getUser(Model model){
        model.addAttribute("user", new User());
        return "registration";
    }

    @PostMapping(value = "/registration")
    public String setUser(@RequestParam String password2,
                          User user,
                          Model model){
        if(!user.getPassword().equals(password2)){
            model.addAttribute("password2Error", "passwords don't match, dude");
            return "registration";
        }

        user.setActivationCode(UUID.randomUUID().toString());
        if (!service.addUser(user)){
            model.addAttribute("emailError", "user with such email exists already, dude");
            return "registration";
        }
        return "redirect:/login";
    }

    @GetMapping("/activate/{code}")
    public String activate(@PathVariable String code){
        service.activateUser(code);
        return "login";
    }

    @GetMapping("/forgot")
    public String getForgotPage(){
        return "forgotPassword";
    }

    @PostMapping("/forgot")
    public String getForgotMail(@RequestParam String email){
        User user = service.findByEmail(email);
        user.setActivationCode(UUID.randomUUID().toString());
        service.update(user);
        String message = String.format(
                "Hello there! You've(or not you) just send us a request for your password \n" +
                        "visit next link to enter new password \n" +
                        " http://localhost:8080/forgot/%s" +
                        " if it wasn't just ignore this message \n" +
                        "Good luck",
                user.getActivationCode()
        );
        sender.send(email, "Forgot your password", message);
        return "redirect:/";
    }

    @GetMapping("/forgot/{code}")
    public String getPasswordChangePage(@PathVariable String code,
                                        Model model){
        model.addAttribute("code",code);
        return "newPassword";
    }

    @PostMapping("/forgot/{code}")
    public String setNewPass(@PathVariable String code,
                             @RequestParam String password,
                             @RequestParam String hash){
        User user = service.findByActivationCode(hash);
        user.setPassword(password);
        service.update(user);
        return "redirect:/";
    }
}
