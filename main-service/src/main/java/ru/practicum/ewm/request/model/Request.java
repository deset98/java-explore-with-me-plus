package ru.practicum.ewm.request.model;

import jakarta.persistence.*;
import lombok.*;
import ru.practicum.ewm.event.model.Event;
import ru.practicum.ewm.user.model.User;

@Entity
@Table(name = "requests")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Request {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "event_id", foreignKey = @ForeignKey(name = "fk_requests_events"))
    @ToString.Exclude
    private Event event;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", foreignKey = @ForeignKey(name = "fk_requests_users"))
    @ToString.Exclude
    private User user;

    @Column
    private String created;

    @Enumerated(EnumType.STRING)
    private Status status;
}
