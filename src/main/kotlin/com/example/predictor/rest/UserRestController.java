package com.example.predictor.rest;

import com.example.predictor.entity.Category;
import com.example.predictor.entity.Event;
import com.example.predictor.entity.Tournament;
import com.example.predictor.repositories.CategoryRepository;
import com.example.predictor.repositories.EventRepository;
import com.example.predictor.repositories.TournamentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping(path = "/api/user/")
public class UserRestController {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private TournamentRepository tournamentRepository;

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private SimpleDateFormat format;

    @GetMapping(path = "/categories")
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    public ResponseEntity<List<Category>> getAllCategories(){

        List<Category> categories = categoryRepository.findAllByDeletedAtNull();

        return new ResponseEntity<>(categories, HttpStatus.OK);

    }

    @GetMapping(path = "/tournaments")
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    public ResponseEntity<List<Tournament>> getAllTournaments(
            @RequestParam(name = "categoryId") Long categoryId
    ){

        Category c = categoryRepository.getOne(categoryId);
        List<Tournament> tournaments = tournamentRepository.findAllByCategoryAndDeletedAtNull(c);

        return new ResponseEntity<>(tournaments, HttpStatus.OK);

    }

    @GetMapping(path = "/event")
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    public ResponseEntity<List<Event>> getAllEvents(
            @PageableDefault(sort = {"date"}, direction = Sort.Direction.ASC, size = 6) Pageable pageable,
            @RequestParam(name = "tournamentId") Long tournamentId
    ){

        Tournament tournament = tournamentRepository.getOne(tournamentId);
        List<Event> page = eventRepository.findAllByDeletedAtNullAndTournament(pageable, tournament);

        return new ResponseEntity<>(page, HttpStatus.OK);
    }

}
