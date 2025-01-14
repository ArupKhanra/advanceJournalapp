package com.arupkhanra.advanceSpringbootFeaturesAZ.entity;

import com.arupkhanra.advanceSpringbootFeaturesAZ.Enum.Sentiment;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "journal_entries")
public class JournalEntry {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    @Column(nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String content;

    private LocalDateTime date;

    @Enumerated(EnumType.STRING)
    private Sentiment sentiment;


}
