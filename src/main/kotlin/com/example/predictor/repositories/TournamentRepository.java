package com.example.predictor.repositories;

import com.example.predictor.entity.Tournament;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TournamentRepository extends JpaRepository<Tournament, Long> {

    //int countAll();
    Page<Tournament> findAllByDeletedAtNullAndCategory_DeletedAtNull(Pageable pageable);
    List<Tournament> findAllByDeletedAtNullAndCategory_DeletedAtNull();
}
