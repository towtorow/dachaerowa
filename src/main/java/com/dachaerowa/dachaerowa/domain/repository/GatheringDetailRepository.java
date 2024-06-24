package com.dachaerowa.dachaerowa.domain.repository;

import com.dachaerowa.dachaerowa.domain.model.Gathering;
import com.dachaerowa.dachaerowa.domain.model.GatheringDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GatheringDetailRepository extends JpaRepository<GatheringDetail, Long> {
    List<GatheringDetail> findAllByGatheringId(Long gatheringId);
}
