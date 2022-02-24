package com.example.api.models.schemas;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "comment_dislikes")
public class CommentDislike {
    @Id
    @GeneratedValue
    private UUID vote_id;
    @Column(name = "value")
    private Integer value;
    @Column(name = "user_id")
    private UUID user_id;
    @ManyToOne()
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "user_comment_votes")
    public User user;
    @Column(name="post_id")
    private UUID post_id;
    @Column(name = "comment_id")
    private UUID comment_id;
    @ManyToOne()
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name="comment_votes")
    public Comment comment;
}
