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
@Table(name = "comments")
public class Comment {
    @Id
    @GeneratedValue
    @Column(name = "comment_id",nullable = false,unique = true)
    private UUID comment_id;
    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "comment_author",nullable = false)
    private User comment_author;
    @Column(name="comment_body",nullable = false)
    private String comment_body;
    @Column(name="comment_likes")
    private Integer comment_likes;
    @Column(name = "comment_dislikes")
    private Integer comment_dislikes;
    @Column(name = "createdAt",nullable = false)
    private LocalDateTime comment_createdAt;
    @LazyCollection(LazyCollectionOption.FALSE)
    @OneToMany(cascade = CascadeType.MERGE)
    private List<CommentLike> comment_votes;

}
