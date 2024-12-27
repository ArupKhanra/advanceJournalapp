package com.arupkhanra.advanceSpringbootFeaturesAZ.entity;


import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "JournalEntry")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class JournalEntry {

    @Id
    private int id;
    @NonNull
    private String title;
    private String content;
    private LocalDateTime date;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @JsonBackReference
    private User user;
}



