package com.example.finaltry.model;

import javax.persistence.*;

@Entity
@Table(name = "comments")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private int id;

    @Column(name = "comment_text")
    private String text;

    @ManyToOne(optional = false, cascade = CascadeType.MERGE)
    @JoinColumn(name = "user_id")
    private User commentedUser;

    @ManyToOne(optional = false, cascade = CascadeType.MERGE)
    @JoinColumn(name = "post_id")
    private Post commentedPost;

    public Comment() {
    }

    public Comment(String text, User commentedUser) {
        this.text = text;
        this.commentedUser = commentedUser;
    }

    public Comment(String text, User commentedUser, Post commentedPost) {
        this.text = text;
        this.commentedUser = commentedUser;
        this.commentedPost = commentedPost;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public User getCommentedUser() {
        return commentedUser;
    }

    public void setCommentedUser(User commentedUser) {
        this.commentedUser = commentedUser;
    }

    public Post getCommentedPost() {
        return commentedPost;
    }

    public void setCommentedPost(Post commentedPost) {
        this.commentedPost = commentedPost;
    }
}
