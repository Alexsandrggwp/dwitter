package com.example.finaltry.web;

import com.example.finaltry.model.Comment;
import com.example.finaltry.model.Post;
import com.example.finaltry.model.User;
import com.example.finaltry.model.UserDetailsImpl;
import com.example.finaltry.service.MyCommentService;
import com.example.finaltry.service.MyPostService;
import com.example.finaltry.service.MyUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Controller
public class ProfileController {

    @Autowired
    MyUserService userService;

    @Autowired
    MyPostService postService;

    @Autowired
    MyCommentService commentService;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String getProfilePage(Model model,
                                @AuthenticationPrincipal UserDetailsImpl userDetails
    ){
        model.addAttribute("loggedInUser", userDetails);
        model.addAttribute("addedPost", new Post());
        model.addAttribute("postList", postService.findByPostingUser(userDetails.getUser()));
        return "userPage";
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public String addPost(
            @RequestParam String text,
            @ModelAttribute("addedPost") Post post,
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ){
        post.setText(text);
        post.setDate(new Date());
        post.setPostingUser(userDetails.getUser());
        postService.addPost(post);
        return "redirect:/";
    }

    @RequestMapping(value = "/post", method = RequestMethod.GET)
    public String getComments(@RequestParam int id,
                              Model model,
                              @AuthenticationPrincipal UserDetailsImpl userDetails){
        model.addAttribute("loggedInUser", userDetails);
        model.addAttribute("addedPost", new Post());
        model.addAttribute("postList", postService.findByPostingUser(userDetails.getUser()));
        model.addAttribute("id", id);
        return "userPage";
    }

    @RequestMapping(value = "/post", method = RequestMethod.POST)
    public String setNewComment(@RequestParam int id,
                                @RequestParam String text,
                                Model model,
                                @AuthenticationPrincipal UserDetailsImpl userDetails){
        model.addAttribute("loggedInUser", userDetails);
        model.addAttribute("addedPost", new Post());
        model.addAttribute("postList", postService.findByPostingUser(userDetails.getUser()));
        model.addAttribute("id", id);
        commentService.save(new Comment(text, userDetails.user, postService.findById(id)));
        return "redirect:/post?id=" + id;
    }

    @RequestMapping(value = "/deletePost", method = RequestMethod.GET)
    public String deletePost(@RequestParam int id){
        commentService.deleteByCommentedPost(postService.findById(id));
        postService.deletePostById(id);
        return "redirect:/";
    }


    @RequestMapping(value = "/friend", method = RequestMethod.POST)
    public String setNewFriendComment(@RequestParam int postId,
                                      @RequestParam String text,
                                      @RequestParam int friendId,
                                      Model model,
                                      @AuthenticationPrincipal UserDetailsImpl userDetails){
        model.addAttribute("loggedInUser", userDetails);
        model.addAttribute("addedPost", new Post());
        model.addAttribute("postList", postService.findByPostingUser(userDetails.getUser()));
        model.addAttribute("postId", postId);
        commentService.save(new Comment(text, userDetails.user, postService.findById(postId)));
        return "redirect:/friend?id=" + friendId + "&postId=" + postId;
    }
}
