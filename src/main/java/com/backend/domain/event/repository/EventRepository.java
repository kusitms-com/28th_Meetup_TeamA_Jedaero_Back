package com.backend.domain.event.repository;

import com.backend.domain.contract.entity.Contract;
import com.backend.domain.event.entity.Event;
import com.backend.domain.event.entity.EventType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface EventRepository extends JpaRepository<Event, Long> {

    @Query(
            value = "select e" +
                    "  from Event e" +
                    " where e.type = :type and e.contract in :contracts and :now between e.startDate and e.endDate"
    )
    Page<Event> findAllByTypeAndContractIn(@Param(value = "type") EventType type, @Param(value = "contracts") List<Contract> contracts, @Param(value = "now") LocalDate now, PageRequest page);

    List<Event> findAllByEventIdInAndType(List<Long> ids, EventType type);

}
