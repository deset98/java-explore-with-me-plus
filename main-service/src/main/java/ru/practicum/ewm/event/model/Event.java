package ru.practicum.ewm.event.model;

import jakarta.persistence.*;
import lombok.*;
import ru.practicum.ewm.category.model.Category;
import ru.practicum.ewm.event.dto.Location;
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
            foreignKey = @ForeignKey(name = "fk_events_categories",
                    value = ConstraintMode.CONSTRAINT))
    @ToString.Exclude
    private Category category;

    @Column(name = "confirmed_requests",
            nullable = false,
            columnDefinition = "integer default 0")
    @Builder.Default
    private Long confirmedRequests = 0L;

    @Column(name = "created_on",
            nullable = false)
    @Builder.Default
    private Instant createdOn = Instant.now();

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

    @Embedded
    private Location location;

    @Column(nullable = false,
            columnDefinition = "boolean default false")
    @Builder.Default
    private Boolean paid = false;

    @Column(name = "participant_limit",
            columnDefinition = "integer default 0")
    @Builder.Default
    private Integer participantLimit = 0;

    @Column(name = "published_on")
    private Instant publishedOn;

    @Column(name = "request_moderation",
            columnDefinition = "boolean default true")
    @Builder.Default
    private Boolean requestModeration = true;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @Builder.Default
    private EventState state = EventState.PENDING;

    @Column(length = 120,
            nullable = false)
    private String title;

    @Column(nullable = false,
            columnDefinition = "integer default 0")
    @Builder.Default
    private Long views = 0L;
}