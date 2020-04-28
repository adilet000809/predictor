package com.example.predictor.rest;

import com.example.predictor.entity.Category;
import com.example.predictor.entity.Event;
import com.example.predictor.entity.Tournament;
import com.example.predictor.repositories.CategoryRepository;
import com.example.predictor.repositories.EventRepository;
import com.example.predictor.repositories.TournamentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(path = "/api/admin/")
@PreAuthorize("hasRole('ROLE_ADMIN')")
public class AdminRestController {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private TournamentRepository tournamentRepository;

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private SimpleDateFormat format;

    @GetMapping(path = "/category")
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    public ResponseEntity<Page<Category>> getAllCategories(
            @PageableDefault(sort = {"id"}, direction = Sort.Direction.DESC) Pageable pageable
    ){

        Page<Category> page = categoryRepository.findAllByDeletedAtNull(pageable);

        return new ResponseEntity<>(page, HttpStatus.OK);
    }

    @PostMapping(path = "/category/add")
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    public ResponseEntity<Category> addCategory(
            @RequestBody Map<String, String> category
    ){

        return new ResponseEntity<>(categoryRepository.save(new Category(category.get("name"))), HttpStatus.OK);

    }

    @PutMapping(path = "/category/update")
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    public ResponseEntity<Category> updateCategory(
            @RequestBody Map<String, String> category
    ){

        Long id = Long.parseLong(category.get("id"));
        String name = category.get("name");

        Category c = categoryRepository.getOne(id);
        c.setName(name);
        c.setUpdatedAt(new Date());

        return new ResponseEntity<>(categoryRepository.save(c), HttpStatus.OK);

    }

    @PutMapping(path = "/category/delete")
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    public HttpStatus deleteCategory(
            @RequestBody Map<String, String> category
    ){

        Long id = Long.parseLong(category.get("id"));

        Category c = categoryRepository.getOne(id);
        c.setDeletedAt(new Date());
        categoryRepository.save(c);

        return HttpStatus.OK;

    }

    @GetMapping(path = "/tournament")
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    public ResponseEntity<Page<Tournament>> getAllTournaments(
            @PageableDefault(sort = {"id"}, direction = Sort.Direction.DESC) Pageable pageable
    ){

        Page<Tournament> page = tournamentRepository.findAllByDeletedAtNullAndCategory_DeletedAtNull(pageable);

        return new ResponseEntity<>(page, HttpStatus.OK);
    }

    @PostMapping(path = "/tournament/add")
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    public ResponseEntity<Tournament> addTournament(
            @RequestBody Map<String, String> tournament
    ){

        String name = tournament.get("name");
        Long categoryId = Long.parseLong(tournament.get("categoryId"));
        Category category = categoryRepository.getOne(categoryId);

        return new ResponseEntity<>(tournamentRepository.save(new Tournament(name, category)), HttpStatus.OK);

    }

    @PutMapping(path = "/tournament/update")
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    public ResponseEntity<Tournament> updateTournament(
            @RequestBody Map<String, String> tournament
    ){

        Long id = Long.parseLong(tournament.get("id"));
        String name = tournament.get("name");
        Long categoryId = Long.parseLong(tournament.get("categoryId"));

        Tournament t = tournamentRepository.getOne(id);
        Category c = categoryRepository.getOne(categoryId);
        t.setName(name);
        t.setCategory(c);
        t.setUpdatedAt(new Date());

        return new ResponseEntity<>(tournamentRepository.save(t), HttpStatus.OK);

    }

    @PutMapping(path = "/tournament/delete")
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    public HttpStatus deleteTournament(
            @RequestBody Map<String, String> tournament
    ){

        Long id = Long.parseLong(tournament.get("id"));

        Tournament t = tournamentRepository.getOne(id);
        t.setDeletedAt(new Date());
        tournamentRepository.save(t);

        return HttpStatus.OK;

    }

    @GetMapping(path = "/categories")
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    public ResponseEntity<List<Category>> getAllCategories(){

        List<Category> categories = categoryRepository.findAllByDeletedAtNull();

        return new ResponseEntity<>(categories, HttpStatus.OK);

    }

    @GetMapping(path = "/tournaments")
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    public ResponseEntity<List<Tournament>> getAllTournaments(){

        List<Tournament> tournaments = tournamentRepository.findAllByDeletedAtNullAndCategory_DeletedAtNull();

        return new ResponseEntity<>(tournaments, HttpStatus.OK);

    }

    @GetMapping(path = "/event")
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    public ResponseEntity<Page<Event>> getAllEvents(
            @PageableDefault(sort = {"date"}, direction = Sort.Direction.ASC) Pageable pageable
    ){

        Page<Event> page = eventRepository.findAllByDeletedAtNullAndTournament_DeletedAtNull(pageable);
        Event e = eventRepository.getOne(4L);
        System.out.println(e.getDate());

        return new ResponseEntity<>(page, HttpStatus.OK);
    }

    @PostMapping(path = "/event/add")
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    public ResponseEntity<Event> addEvent(
            @RequestBody Map<String, String> event
    ) throws ParseException {

        String team1 = event.get("team1");
        String team2 = event.get("team2");
        //int scoreTeam1 = Integer.parseInt(event.get("scoreTeam1"));
        //int scoreTeam2 = Integer.parseInt(event.get("scoreTeam2"));
        Date date = format.parse(event.get("date"));
        Tournament tournament = tournamentRepository.getOne(Long.parseLong(event.get("tournamentId")));

        Event e = new Event(team1, team2, null, null, date, tournament);
        e.setCreatedAt(new Date());

        return new ResponseEntity<>(eventRepository.save(e), HttpStatus.OK);

    }

    @PutMapping(path = "/event/update")
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    public ResponseEntity<Event> updateEvent(
            @RequestBody Map<String, String> event
    ) throws ParseException {

        Integer scoreTeam1 = null;
        Integer scoreTeam2 = null;

        Long id = Long.parseLong(event.get("id"));
        String team1 = event.get("team1");
        String team2 = event.get("team2");
        if(event.get("scoreTeam1")!=null && event.get("scoreTeam1")!=null){
            scoreTeam1 = Integer.parseInt(event.get("scoreTeam1"));
            scoreTeam2 = Integer.parseInt(event.get("scoreTeam2"));
        }
        Date date = format.parse(event.get("date"));
        Tournament tournament = tournamentRepository.getOne(Long.parseLong(event.get("tournamentId")));

        Event e = eventRepository.getOne(id);
        e.setTeam1(team1);
        e.setTeam2(team2);
        e.setScoreTeam1(scoreTeam1);
        e.setScoreTeam2(scoreTeam2);
        e.setDate(date);
        e.setTournament(tournament);

        return new ResponseEntity<>(eventRepository.save(e), HttpStatus.OK);

    }

    @PutMapping(path = "/event/delete")
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    public HttpStatus deleteEvent(
            @RequestBody Map<String, String> event
    ){

        Long id = Long.parseLong(event.get("id"));
        Event e = eventRepository.getOne(id);
        e.setDeletedAt(new Date());
        eventRepository.save(e);

        return HttpStatus.OK;

    }

}
