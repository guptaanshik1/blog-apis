package com.blogapis.blogapi.entity;

import lombok.*;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String content;

    @Column(columnDefinition = "integer DEFAULT 0")
    private int numberOfLikes;

    @ManyToOne
    private Posts posts;

    @ManyToOne
    private User user;

    @OneToMany(mappedBy = "comment", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Replies> replies;

    @OneToMany(mappedBy = "comment", cascade = CascadeType.ALL)
    private Set<Likes> likes;

}