package com.dachaerowa.dachaerowa.domain.model;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;

import java.util.Set;

@Data
@Entity
@Table(name = "gathering_detail")
@Builder
public class GatheringDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String imageUrl;

    private Long gatheringId;

}
