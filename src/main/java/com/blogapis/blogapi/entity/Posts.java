package com.blogapis.blogapi.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Entity @Getter @Setter @AllArgsConstructor @NoArgsConstructor
public class Posts {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int postId;

    private String postTitle;

    @Column(length = 10000)
    private String postContent;

    @Column(columnDefinition = "integer DEFAULT 0")
    private int numberOfLikes;

    @Column(updatable = false)
    private Date addedDate;

    @ManyToOne
    private Category category;

    @ManyToOne
    private User user;

    @OneToMany(mappedBy = "posts", cascade = CascadeType.ALL)
    private Set<Comment> comments;

    @OneToMany(mappedBy = "posts", cascade = CascadeType.ALL)
    private Set<Likes> likes;

    @OneToMany(mappedBy = "posts", cascade = CascadeType.ALL)
    private Set<Image> images;

}