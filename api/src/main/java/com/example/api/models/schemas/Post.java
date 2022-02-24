package com.example.api.models.schemas;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "posts")
public class Post {
    @Id
    @GeneratedValue
    @Column(name = "post_id", nullable = false, unique = true)
    private UUID post_id;
    @Column(name = "post_title",nullable = false)
    private String post_title;
    @Column(name="post_body",nullable = false)
    private String post_body;
    @ManyToOne(cascade = CascadeType.MERGE,fetch = FetchType.EAGER)
    @JoinColumn(name = "post_author",nullable = false)
    private User post_author;
    @Column(name="post_likes")
    private Integer post_likes;
    @Column(name="post_dislikes")
    private Integer post_dislikes;
    @LazyCollection(LazyCollectionOption.FALSE)
    @OneToMany(cascade = CascadeType.MERGE)
    @JoinColumn(name = "comment")
    private List<Comment> comments;
    @Column(name = "post_created_at",nullable = false)
    private LocalDateTime post_createdAt;
    @LazyCollection(LazyCollectionOption.FALSE)
    @OneToMany(cascade = CascadeType.MERGE)
    private List<PostLike> post_votes;
}
