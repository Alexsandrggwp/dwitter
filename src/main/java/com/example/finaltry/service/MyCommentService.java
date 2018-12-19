package com.example.finaltry.service;

import com.example.finaltry.model.Comment;
import com.example.finaltry.model.Post;
import com.example.finaltry.repository.CommentRepositoryImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MyCommentService {

    @Autowired
    CommentRepositoryImpl repository;

    public void save(Comment comment){
        repository.save(comment);
    }

    public void deleteByCommentedPost(Post post){
        repository.deleteByCommentedPost(post);
    }
}
