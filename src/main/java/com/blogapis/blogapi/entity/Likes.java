package com.blogapis.blogapi.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Likes {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private Date likedOnDate;

    @Column(columnDefinition = "boolean DEFAULT false")
    private Boolean isLiked;

    @ManyToOne
    private User user;

    @ManyToOne
    private Posts posts;

    @ManyToOne
    private Comment comment;

    @ManyToOne
    private Replies replies;
}
