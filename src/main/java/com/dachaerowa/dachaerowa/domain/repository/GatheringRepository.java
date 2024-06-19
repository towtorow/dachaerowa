package com.dachaerowa.dachaerowa.domain.repository;

import com.dachaerowa.dachaerowa.domain.model.Gathering;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.repository.PagingAndSortingRepository;

@Repository
public interface GatheringRepository extends JpaRepository<Gathering, Long> {


}
