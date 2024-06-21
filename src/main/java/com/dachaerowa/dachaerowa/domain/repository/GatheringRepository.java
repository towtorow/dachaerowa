package com.dachaerowa.dachaerowa.domain.repository;

import com.dachaerowa.dachaerowa.domain.model.Gathering;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

@Repository
public interface GatheringRepository extends JpaRepository<Gathering, Long> {
    @Query("SELECT g FROM Gathering g " +
            "LEFT JOIN FETCH g.organizer ")
    Page<Gathering> findAllWithJoinOrganizerOnly(Pageable pageable);

}
