package com.dachaerowa.dachaerowa.domain.repository;

import com.dachaerowa.dachaerowa.domain.model.Gathering;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface GatheringRepository extends JpaRepository<Gathering, Long> {
    @Query("SELECT g FROM Gathering g " +
            "LEFT JOIN FETCH g.organizer "+
            "WHERE g.deletedDate IS NULL ")
    Page<Gathering> findAllWithJoinOrganizerOnly(Pageable pageable);

    @Query("SELECT g FROM Gathering g " +
            "LEFT JOIN FETCH g.organizer " +
            "WHERE g.startDateTime > :now AND g.deletedDate IS NULL " +
            "ORDER BY g.startDateTime ASC")
    Page<Gathering> findAllWithJoinOrganizerOnlyAfterNow(Pageable pageable, @Param("now") LocalDateTime now);

    @Modifying
    @Query("UPDATE Gathering g SET g.deletedDate = :deletedDate WHERE g.id = :gatheringId")
    void updateDeletedDateById(@Param("gatheringId") Long gatheringId, @Param("deletedDate") LocalDateTime deletedDate);

    @Modifying
    @Query("UPDATE Gathering g SET g.title = :title, g.description = :description WHERE g.id = :gatheringId")
    void updateTitleAndDescriptionById(@Param("gatheringId") Long gatheringId,@Param("title") String title,@Param("description") String description);
}
