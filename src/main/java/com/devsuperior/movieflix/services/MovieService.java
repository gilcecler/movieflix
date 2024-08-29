package com.devsuperior.movieflix.services;

import com.devsuperior.movieflix.dto.MovieCardDTO;
import com.devsuperior.movieflix.dto.MovieDetailsDTO;
import com.devsuperior.movieflix.dto.ReviewDTO;
import com.devsuperior.movieflix.entities.Genre;
import com.devsuperior.movieflix.entities.Movie;
import com.devsuperior.movieflix.entities.Review;
import com.devsuperior.movieflix.repositories.MovieRepository;
import com.devsuperior.movieflix.repositories.ReviewRepository;
import com.devsuperior.movieflix.services.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


@Service
public class MovieService {
    @Autowired
    private MovieRepository repository;

    @Autowired
    private ReviewRepository reviewRepository;

    @Transactional(readOnly = true)
    public Page<MovieCardDTO> findAllPaged(Genre genreId, Pageable pageable) {

        //List<Long> movieIds = new ArrayList<>();
        if ("0".equals(genreId)) {
            genreId = null;
        }
        Page<Movie> page = repository.searchMovieByGenreId(genreId, pageable);
        return page.map(x -> new MovieCardDTO(x));

    }

    @Transactional(readOnly = true)
    public MovieDetailsDTO findById(Long id) {
        Optional<Movie> dto = repository.findById(id);
        Movie entity = dto.orElseThrow(() -> new ResourceNotFoundException(" Entity not found"));
        return new MovieDetailsDTO(entity);
    }

    @Transactional(readOnly = true)
    public List<ReviewDTO> findByReviewMovieId(Long id) {
        List<Review> reviews = reviewRepository.findByReviewMovieId(id);
        return reviews.stream().map(x -> new ReviewDTO(x)).toList();
    }

}



