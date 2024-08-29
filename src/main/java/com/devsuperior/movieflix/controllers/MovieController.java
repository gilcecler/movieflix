package com.devsuperior.movieflix.controllers;
 import com.devsuperior.movieflix.dto.MovieCardDTO;
 import com.devsuperior.movieflix.dto.MovieDetailsDTO;
 import com.devsuperior.movieflix.dto.ReviewDTO;
 import com.devsuperior.movieflix.entities.Genre;
  import com.devsuperior.movieflix.services.MovieService;
 import org.springframework.beans.factory.annotation.Autowired;
 import org.springframework.data.domain.Page;
 import org.springframework.data.domain.Pageable;
 import org.springframework.data.domain.Sort;
 import org.springframework.data.web.PageableDefault;
 import org.springframework.http.ResponseEntity;
 import org.springframework.security.access.prepost.PreAuthorize;
 import org.springframework.web.bind.annotation.*;

 import java.util.List;

@RestController
@RequestMapping("/movies")
public class MovieController {

    @Autowired
    private MovieService service;

    @PreAuthorize("hasAnyRole('ROLE_VISITOR','ROLE_MEMBER')")
    @GetMapping
    public ResponseEntity<Page<MovieCardDTO>>findByGenre(
            @RequestParam(value = "genreId", defaultValue = "0") Genre genreId,
            @PageableDefault(sort = { "title" }, direction = Sort.Direction.ASC) Pageable pageable) {

            Page<MovieCardDTO> result = service.findAllPaged(genreId, pageable);

        return ResponseEntity.ok().body(result);
    }

    @Autowired
    private MovieService movieService;

    @GetMapping(value = "/{id}")
    @PreAuthorize("hasAnyRole('MEMBER','VISITOR')")
    public ResponseEntity<MovieDetailsDTO> findById(@PathVariable Long id) {
        MovieDetailsDTO dto = service.findById(id);
        return ResponseEntity.ok().body(dto);
    }

    @GetMapping(value = "/{id}/reviews")
    @PreAuthorize("hasAnyRole('MEMBER','VISITOR')")
    public ResponseEntity<List<ReviewDTO>> findByReviewMovieId(@PathVariable Long id) {
        List<ReviewDTO> list = service.findByReviewMovieId(id);
        return ResponseEntity.ok().body(list);
    }

}