package com.example.finaltry.web;

import com.example.finaltry.model.User;
import com.example.finaltry.model.UserDetailsImpl;
import com.example.finaltry.service.MyPostService;
import com.example.finaltry.service.MyUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
public class FriendsController {

    @Autowired
    MyUserService userService;

    @Autowired
    MyPostService postService;

    @GetMapping("/friends")
    public String getFriends(Model model,
                             @AuthenticationPrincipal UserDetailsImpl userDetails){
        model.addAttribute("friends", userDetails.user.getFriends());
        return "friends";
    }

    @GetMapping("/friend")
    public String getFriend(@RequestParam int id,
                            @RequestParam(required = false) Integer postId,
                            Model model){
        model.addAttribute("id", id);
        model.addAttribute("postId", postId);
        model.addAttribute("friend", userService.findById(id));
        model.addAttribute("postList", postService.findByPostingUser(userService.findById(id)));
        return "friendPage";
    }

    @GetMapping("/deleteFriend")
    public String deleteFriend(@RequestParam int id,
                               @AuthenticationPrincipal UserDetailsImpl userDetails){
        User user = userDetails.user;
        Set<User> friends = user.getFriends();
        for(User user1 : friends){
            if (user1.getId()==id){
                friends.remove(user1);
                user.setFriends(friends);
                userService.save(user);
                return "redirect:/friends";
            }
        }
        return "redirect:/friends";
    }

    @GetMapping("/find")
    public String getPhotoEditor(){
        return "search";
    }

    @PostMapping("/find")
    public String findFriends(@RequestParam String name,
                              @AuthenticationPrincipal UserDetailsImpl details,
                              Model model){
        List<User> users = userService.findByNameOrSurname(name);
        for(User user1: details.user.getReqFriends()){
            for(User user: users){
                if(user1.getId()==user.getId()){
                    users.remove(user);
                    break;
                }
            }
        }
        model.addAttribute("found", users);
        return "search";
    }

    @GetMapping("/requestFriend")
    public String requestFriend(@RequestParam int id,
                            @AuthenticationPrincipal UserDetailsImpl details){
        User reqUser = userService.findById(id);
        reqUser.getReqFriends().add(details.user);
        try {
            userService.save(reqUser);
        }
        catch (Exception ex){
            return "redirect:/find";
        }
        return "redirect:/find";
    }

    @GetMapping("/reqforme")
    public String getRequests(@AuthenticationPrincipal UserDetailsImpl details,
                              Model model){
        model.addAttribute("loggedInUser", details.user);
        return "req";
    }

    @GetMapping("/addFriend")
    public String addFriend(@RequestParam int id,
                            @AuthenticationPrincipal UserDetailsImpl details){
        User addingUser = userService.findById(id);
        User user = details.getUser();
        for(User deletingUser: user.getReqFriends()){
            if(deletingUser.getId()== id){
                user.getReqFriends().remove(deletingUser);
                break;
            }
        }
        user.getFriends().add(addingUser);
        addingUser.getFriends().add(user);
        userService.save(user);
        userService.save(addingUser);
        return "redirect:/reqforme";
    }
}
