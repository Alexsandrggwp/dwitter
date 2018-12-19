package com.example.finaltry.model;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "posts")
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id")
    private int id;

    @Column(name = "post_text")
    private String text;

    @Column(name = "post_date")
    private Date date;

    @ManyToOne(optional = false, cascade = CascadeType.MERGE)
    @JoinColumn(name = "user_id")
    private User postingUser;

    @OneToMany(mappedBy = "commentedPost", fetch = FetchType.EAGER)
    private List<Comment> postComments;

    public Post() {
    }

    public Post(String text, Date date, User postingUser) {
        this.text = text;
        this.date = date;
        this.postingUser = postingUser;
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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public User getPostingUser() {
        return postingUser;
    }

    public void setPostingUser(User postingUser) {
        this.postingUser = postingUser;
    }

    public List<Comment> getPostComments() {
        return postComments;
    }

    public void setPostComments(List<Comment> postComments) {
        this.postComments = postComments;
    }
}
