package com.dachaerowa.dachaerowa.domain.repository;

import com.dachaerowa.dachaerowa.domain.model.Gathering;
import com.dachaerowa.dachaerowa.domain.model.GatheringDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface GatheringDetailRepository extends JpaRepository<GatheringDetail, Long> {
    @Query("SELECT gd FROM GatheringDetail gd WHERE gd.gatheringId = :gatheringId AND gd.deletedDate IS NULL")
    List<GatheringDetail> findAllByGatheringIdAndDeletedDateIsNull(@Param("gatheringId") Long gatheringId);

    @Modifying
    @Query("UPDATE GatheringDetail g SET g.deletedDate = :deletedDate WHERE g.id IN :ids")
    void updateDeletedDateById(@Param("ids") List<Long> ids, @Param("deletedDate") LocalDateTime deletedDate);
}
