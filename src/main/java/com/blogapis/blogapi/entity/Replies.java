package com.blogapis.blogapi.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Date;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Replies {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotBlank
    private String content;

    private Date addedDate;

    @Column(columnDefinition = "integer DEFAULT 0")
    private int numberOfLikes;

    @ManyToOne
    private User user;

    @ManyToOne
    private Comment comment;

    @OneToMany(mappedBy = "replies", cascade = CascadeType.ALL)
    private Set<Likes> likes;

}