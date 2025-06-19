package com.example.springBoot2.controllers;

import com.example.springBoot2.models.Book;
import com.example.springBoot2.models.Movie;
import com.example.springBoot2.repositories.BookRepository;
import com.example.springBoot2.repositories.MovieRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/movies")
public class MovieController {
    private final MovieRepository movieRepository;

    public MovieController(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    @GetMapping
    public ResponseEntity<List<Movie>> getAllItems() {
        return ResponseEntity.status(HttpStatus.OK).body(movieRepository.findAll());
    }

    @PostMapping
    public ResponseEntity<Movie> addMovie(@RequestBody Movie newMovie) {
        // TODO: validate the input
        return ResponseEntity.status(HttpStatus.CREATED).body(movieRepository.save(newMovie));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Movie> getMovieById(@PathVariable int id) {
        Optional<Movie> movie = movieRepository.findById(id);
        if (movie.isPresent()) {
            return ResponseEntity.status(HttpStatus.OK).body(movie.get());
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Movie> updateMovieById(@PathVariable int id, @RequestBody Movie updatedMovie) {
        Optional<Movie> existingMovie = movieRepository.findById(id);
        if (existingMovie.isPresent()) {
            Movie movie = existingMovie.get();
            movie.setDirector(updatedMovie.getDirector());
            movie.setName(updatedMovie.getName());
            movie.setRuntime(updatedMovie.getRuntime());
            movie.setYear(updatedMovie.getYear());
            return ResponseEntity.status(HttpStatus.OK).body(movieRepository.save(movie));
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMovieById(@PathVariable int id) {
        if (movieRepository.existsById(id)) {
            movieRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
