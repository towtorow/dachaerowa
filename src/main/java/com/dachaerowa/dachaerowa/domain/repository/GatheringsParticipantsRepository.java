package com.dachaerowa.dachaerowa.domain.repository;

import com.dachaerowa.dachaerowa.domain.model.Gathering;
import com.dachaerowa.dachaerowa.domain.model.GatheringsParticipants;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface GatheringsParticipantsRepository extends JpaRepository<GatheringsParticipants, Long> {

    List<GatheringsParticipants> findByGatheringId(Long gatheringId);

    List<GatheringsParticipants> findByGatheringIdAndParticipantId(Long gatheringId, Long participantId);
}
