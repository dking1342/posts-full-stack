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
@Table(name = "post_likes")
public class PostLike {
    @Id
    @GeneratedValue
    private UUID vote_id;
    @Column(name = "value")
    private Integer value;
    @Column(name = "user_id")
    private UUID user_id;
    @ManyToOne()
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "user_votes")
    public User user;
    @Column(name="post_id")
    private UUID post_id;
    @ManyToOne()
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "post_votes")
    public Post post;
}
