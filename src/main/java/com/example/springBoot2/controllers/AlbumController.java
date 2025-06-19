package com.example.springBoot2.controllers;

import com.example.springBoot2.models.Album;
import com.example.springBoot2.models.Book;
import com.example.springBoot2.models.Movie;
import com.example.springBoot2.repositories.AlbumRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/albums")
public class AlbumController {
    private final AlbumRepository albumRepository;

    public AlbumController(AlbumRepository albumRepository) {
        this.albumRepository = albumRepository;
    }

    @GetMapping
    public ResponseEntity<List<Album>> getAllItems() {
        return ResponseEntity.status(HttpStatus.OK).body(albumRepository.findAll());
    }

    @PostMapping
    public ResponseEntity<Album> addMovie(@RequestBody Album newAlbum) {
        // TODO: validate the input
        return ResponseEntity.status(HttpStatus.CREATED).body(albumRepository.save(newAlbum));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Album> getAlbumById(@PathVariable int id) {
        Optional<Album> album = albumRepository.findById(id);
        if (album.isPresent()) {
            return ResponseEntity.status(HttpStatus.OK).body(album.get());
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Album> updateAlbumById(@PathVariable int id, @RequestBody Album updatedAlbum) {
        Optional<Album> existingAlbum = albumRepository.findById(id);
        if (existingAlbum.isPresent()) {
            Album album = existingAlbum.get();
            album.setArtist(updatedAlbum.getArtist());
            album.setName(updatedAlbum.getName());
            album.setTracks(updatedAlbum.getTracks());
            album.setYear(updatedAlbum.getYear());
            return ResponseEntity.status(HttpStatus.OK).body(albumRepository.save(album));
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAlbumById(@PathVariable int id){
        if (albumRepository.existsById(id)) {
            albumRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
