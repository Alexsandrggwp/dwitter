package com.example.finaltry.repository;

import com.example.finaltry.model.Comment;
import com.example.finaltry.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

public interface CommentRepositoryImpl extends JpaRepository<Comment, Long> {

    @Transactional
    void deleteByCommentedPost(Post post);
}
