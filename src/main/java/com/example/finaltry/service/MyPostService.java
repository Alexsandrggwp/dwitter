package com.example.finaltry.service;

import com.example.finaltry.model.Post;
import com.example.finaltry.model.User;
import com.example.finaltry.repository.PostRepositoryImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class MyPostService {

    @Autowired
    PostRepositoryImpl repository;

    public void addPost(Post post){
        repository.saveAndFlush(post);
    }

    public List<Post> findByPostingUser(User user){
        return repository.findByPostingUser(user);
    }

    public Post findById(int id){
        return repository.findById(id);
    }

    @Transactional
    public void deletePostById(int id){
        repository.deletePostById(id);
    }
}
