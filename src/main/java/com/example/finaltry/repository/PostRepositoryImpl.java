package com.example.finaltry.repository;

import com.example.finaltry.model.Post;
import com.example.finaltry.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepositoryImpl extends JpaRepository<Post, Long> {

    List<Post> findByPostingUser(User user);

    Post findById(int id);

    void deletePostById(int id);
}
