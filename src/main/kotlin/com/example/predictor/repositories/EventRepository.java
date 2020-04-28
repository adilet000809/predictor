package com.example.predictor.repositories;

import com.example.predictor.entity.Event;
import com.example.predictor.entity.Tournament;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EventRepository extends JpaRepository<Event, Long> {

    Page<Event>  findAllByDeletedAtNullAndTournament_DeletedAtNull(Pageable pageable);
    List<Event> findAllByDeletedAtNullAndTournament(Pageable pageable, Tournament tournament);

}
