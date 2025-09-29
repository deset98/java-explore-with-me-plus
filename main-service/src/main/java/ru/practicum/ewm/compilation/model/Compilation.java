package ru.practicum.ewm.compilation.model;

import jakarta.persistence.*;
import lombok.*;
import ru.practicum.ewm.event.model.Event;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "compilations")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Compilation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToMany
    @JoinTable(name = "compilation_events",
            joinColumns = @JoinColumn(name = "compilation_id",
                    foreignKey = @ForeignKey(name = "fk_compilation_events__compilations")),
            inverseJoinColumns = @JoinColumn(name = "event_id",
                    foreignKey = @ForeignKey(name = "fk_compilation_events__events")))
    @Builder.Default
    private List<Event> events = new ArrayList<>();

    @Column(nullable = false)
    private Boolean pinned;

    @Column(nullable = false)
    private String title;
}
