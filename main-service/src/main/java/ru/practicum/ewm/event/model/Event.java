package ru.practicum.ewm.event.model;

import jakarta.persistence.*;
import lombok.*;
import ru.practicum.ewm.category.model.Category;
import ru.practicum.ewm.user.model.User;

import java.time.Instant;

@Entity
@Table(name = "events")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 2000,
            nullable = false)
    private String annotation;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id",
                nullable = false,
                foreignKey = @ForeignKey(name = "fk_events_categories"))
    @ToString.Exclude
    private Category category;

    @Column(name = "confirmed_requests")
    private Integer confirmedRequests;

    @Column(name = "created_on",
            nullable = false)
    private Instant createdOn;

    @Column(length = 7000,
            nullable = false)
    private String description;

    @Column(name = "event_date",
            nullable = false)
    private Instant eventDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id",
                nullable = false,
                foreignKey = @ForeignKey(name = "fk_events_users"))
    @ToString.Exclude
    private User initiator;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "location_id",
                nullable = false,
                foreignKey = @ForeignKey(name = "fk_events_locations"))
    @ToString.Exclude
    private Location location;

    @Column(nullable = false)
    private Boolean paid;

    @Column(name = "participant_limit",
            nullable = false)
    private Integer participantLimit;

    @Column(name = "published_on")
    private Instant publishedOn;

    @Column(name = "request_moderation",
            nullable = false)
    private Boolean requestModeration;

    @Enumerated(EnumType.STRING)
    private State state;

    @Column(length = 120,
            nullable = false)
    private String title;

    @Column
    private Integer views;
}