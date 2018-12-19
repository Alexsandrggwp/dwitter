package com.example.finaltry.web;

import com.example.finaltry.model.User;
import com.example.finaltry.model.UserDetailsImpl;
import com.example.finaltry.service.MyUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

@Controller
public class ProfileEditorController {

    @Autowired
    MyUserService userService;

    @Value("${upload.path}")
    private String uploadPath;

    @GetMapping("/edit")
    public String getEditPage(@AuthenticationPrincipal UserDetailsImpl userDetails,
                              Model model){
        model.addAttribute("loggedInUser", userDetails);
        return "editProfile";
    }

    @GetMapping("/editName")
    public String getNameEditor(){
        return "editName";
    }

    @PostMapping("/editName")
    public String editName(@RequestParam String username,
                           @AuthenticationPrincipal UserDetailsImpl userDetails){
        User user = userDetails.user;
        user.setUsername(username);
        userService.save(user);
        return "redirect:/edit";
    }

    @GetMapping("/editSurname")
    public String getSurnameEditor(){
        return "editSurname";
    }

    @PostMapping("/editSurname")
    public String editSurname(@RequestParam String surname,
                              @AuthenticationPrincipal UserDetailsImpl userDetails){
        User user = userDetails.user;
        user.setSurname(surname);
        userService.save(user);
        return "redirect:/edit";
    }

    @GetMapping("/editCountry")
    public String getCountryEditor(){
        return "editCountry";
    }

    @PostMapping("/editCountry")
    public String editCountry(@RequestParam String country,
                              @AuthenticationPrincipal UserDetailsImpl userDetails){
        User user = userDetails.user;
        user.setCountry(country);
        if(country.length()==0){
            user.setCountry(null);
        }
        userService.save(user);
        return "redirect:/edit";
    }

    @GetMapping("/editTown")
    public String getTownEditor(){
        return "editTown";
    }

    @PostMapping("/editTown")
    public String editTown(@RequestParam String town,
                           @AuthenticationPrincipal UserDetailsImpl userDetails){
        User user = userDetails.user;
        user.setTown(town);
        if(town.length()==0){
            user.setTown(null);
        }
        userService.save(user);
        return "redirect:/edit";
    }

    @GetMapping("/editDescription")
    public String getDescriptionEditor(){
        return "editDescription";
    }

    @PostMapping("/editDescription")
    public String editDescription(@RequestParam String description,
                           @AuthenticationPrincipal UserDetailsImpl userDetails){
        User user = userDetails.user;
        user.setDescription(description);
        if(description.length()==0){
            user.setDescription(null);
        }
        userService.save(user);
        return "redirect:/edit";
    }

    @GetMapping("/editSex")
    public String getSexEditor(){
        return "editSex";
    }

    @PostMapping("/editSex")
    public String editSex(@RequestParam String sex,
                          @AuthenticationPrincipal UserDetailsImpl userDetails){
        User user = userDetails.user;
        user.setSex(sex);
        if(sex.length()==0){
            user.setDescription(null);
        }
        userService.save(user);
        return "redirect:/edit";
    }

    @GetMapping("/editDate")
    public String getDateEditor(){
        return "editDate";
    }

    @PostMapping("/editDate")
    public String editDate(@RequestParam String date,
                           @AuthenticationPrincipal UserDetailsImpl userDetails)
            throws ParseException {
        User user = userDetails.user;
        SimpleDateFormat format = new SimpleDateFormat();
        format.applyPattern("yyyy-MM-dd");
        Date newDate = format.parse(date);
        user.setDateOfBirth(newDate);
        userService.save(user);
        return "redirect:/edit";
    }

    @GetMapping("/editPhoto")
    public String getPhotoEditor(){
        return "editPhoto";
    }

    @PostMapping("/editPhoto")
    public String editPhoto(@RequestParam MultipartFile photo,
                            @AuthenticationPrincipal UserDetailsImpl userDetails) throws IOException {
        String uuidFile = UUID.randomUUID().toString();
        String resultFileName= uuidFile + "_" + photo.getOriginalFilename();
        String path = uploadPath + "/" + resultFileName;
        photo.transferTo(new File(path));
        User user = userDetails.user;
        user.setPhotoPath(resultFileName);
        userService.save(user);
        return "redirect:/edit";
    }
}
