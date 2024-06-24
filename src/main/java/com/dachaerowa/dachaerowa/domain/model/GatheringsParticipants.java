package com.dachaerowa.dachaerowa.domain.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Entity
@Builder
@Table(name = "gatherings_participants")
@NoArgsConstructor
@AllArgsConstructor
public class GatheringsParticipants {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long gatheringId;

    private Long participantId;

    private String participantUsername;
}
