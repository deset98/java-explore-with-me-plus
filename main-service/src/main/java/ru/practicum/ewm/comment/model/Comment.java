package ru.practicum.ewm.comment.model;

import jakarta.persistence.*;
import lombok.*;
import ru.practicum.ewm.event.model.Event;
import ru.practicum.ewm.user.model.User;

import java.time.Instant;

@Entity
@Table(name = "comments")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 300,
            nullable = false)
    private String annotation;

    @Column(length = 3000,
            nullable = false)
    private String text;

    @Column(name = "published_on",
            nullable = false)
    @Builder.Default
    private Instant publishedOn = Instant.now();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id",
            nullable = false,
            foreignKey = @ForeignKey(name = "fk_comments_users"))
    @ToString.Exclude
    private User author;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "event_id",
            nullable = false,
            foreignKey = @ForeignKey(name = "fk_comments_events"))
    @ToString.Exclude
    private Event event;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @Builder.Default
    private CommentState state = CommentState.PUBLIC;
}