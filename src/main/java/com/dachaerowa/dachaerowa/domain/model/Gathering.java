package com.dachaerowa.dachaerowa.domain.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;


@Data
@Entity
@Table(name = "gatherings")
public class Gathering  extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String imageUrl;

    private String title;

    private String description;

    private String category;

    private LocalDateTime startDateTime;

    private LocalDateTime endDateTime;

    private String location;

    @ManyToOne
    @JoinColumn(name = "organizer_id")
    private User organizer;

}
