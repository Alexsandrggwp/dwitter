package com.example.finaltry.model;

import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private int id;

    @NotBlank(message = "field can't be empty, dude")
    @Length(max = 45, message = "too long, dude")
    @Column(name = "user_name")
    private String username;

    @NotBlank(message = "field can't be empty, dude")
    @Column(name = "user_password")
    private String password;

    @Column(name ="user_surname")
    private String surname;

    @Column(name ="user_sex")
    private String sex;

    @Email(message = "email isn't correct, dude")
    @NotBlank(message = "field can't be empty, dude")
    @Column(name ="user_email")
    private String email;

    @Column(name ="user_country")
    private String country;

    @Column(name ="user_town")
    private String town;

    @Column(name = "user_date_of_birth")
    private Date dateOfBirth;

    @Column(name ="user_description")
    private String description;

    @Column(name = "user_photoPath")
    private String photoPath;

    @Column(name = "user_enabled")
    private boolean enable;

    @Column(name = "user_activation_code")
    private String activationCode;

    @OneToMany(mappedBy = "commentedUser", fetch = FetchType.EAGER)
    private Set<Comment> comments = new HashSet<>();

    @OneToMany(mappedBy = "postingUser", fetch = FetchType.EAGER)
    private Set<Post> posts = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "friends",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "friend_id")
    )
    private Set<User> friends = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "requested_friends",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "requared_friend_id")
    )
    private Set<User> ReqFriends = new HashSet<>();

    public User(String username, String password, String surname, String sex, String email, String country, String town, Date dateOfBirth, String description, boolean enable) {
        this.username = username;
        this.password = password;
        this.surname = surname;
        this.sex = sex;
        this.email = email;
        this.country = country;
        this.town = town;
        this.dateOfBirth = dateOfBirth;
        this.description = description;
        this.enable = enable;
    }

    public User() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getTown() {
        return town;
    }

    public void setTown(String town) {
        this.town = town;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    public String getActivationCode() {
        return activationCode;
    }

    public void setActivationCode(String activationCode) {
        this.activationCode = activationCode;
    }

    public Set<Comment> getComments() {
        return comments;
    }

    public void setComments(Set<Comment> comments) {
        this.comments = comments;
    }

    public Set<Post> getPosts() {
        return posts;
    }

    public void setPosts(Set<Post> posts) {
        this.posts = posts;
    }

    public Set<User> getFriends() {
        return friends;
    }

    public void setFriends(Set<User> friends) {
        this.friends = friends;
    }

    public String getPhotoPath() {
        return photoPath;
    }

    public void setPhotoPath(String photoPath) {
        this.photoPath = photoPath;
    }

    public Set<User> getReqFriends() {
        return ReqFriends;
    }

    public void setReqFriends(Set<User> reqFriends) {
        ReqFriends = reqFriends;
    }
}
