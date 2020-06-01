package com.example.predictor.rest;

import com.example.predictor.entity.*;
import com.example.predictor.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(path = "/api/user/")
public class UserRestController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private TournamentRepository tournamentRepository;

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private PredictionRepository predictionRepository;

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

    @PostMapping(path = "/predict")
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    public ResponseEntity<Prediction> predict(
            @RequestBody Map<String, String> prediction
    ){

        Event event = eventRepository.getOne(Long.parseLong(prediction.get("eventId")));
        String data = prediction.get("prediction");
        Users user = getUserData();
        Prediction p;
        if(predictionRepository.existsByEventAndUser(event, user)){
            p = predictionRepository.findByEventAndUser(event, user);
            p.setPrediction(data);
        } else {
            p = new Prediction(event, user, data);
        }
        predictionRepository.save(p);


        return new ResponseEntity<>(p, HttpStatus.OK);
    }

    public Users getUserData(){
        Users userData = null;
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(!(authentication instanceof AnonymousAuthenticationToken)){
            User secUser = (User)authentication.getPrincipal();
            userData = userRepository.findByEmail(secUser.getUsername()).get();
        }
        return userData;
    }

}
