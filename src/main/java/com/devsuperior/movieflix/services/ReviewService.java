package com.devsuperior.movieflix.services;

import com.devsuperior.movieflix.dto.ReviewDTO;
import com.devsuperior.movieflix.entities.Movie;
import com.devsuperior.movieflix.entities.Review;
import com.devsuperior.movieflix.entities.User;
import com.devsuperior.movieflix.repositories.MovieRepository;
import com.devsuperior.movieflix.repositories.ReviewRepository;
import com.devsuperior.movieflix.services.exceptions.UnauthorizedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class ReviewService {

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private AuthService authService;

    @Autowired
    private UserService userService;

    @Transactional(readOnly = true)
    public ReviewDTO saveReview(ReviewDTO dto) {
        try {
            Review entity = new Review();
            User user = new User();
            user = authService.authenticated();
            copyEntityToDTO(user, entity, dto);
            entity = reviewRepository.save(entity);
            return new ReviewDTO(entity);
        } catch (RuntimeException e) {
            throw new UnauthorizedException("Unauthorized User");
        }

    }

    public void copyEntityToDTO(User user, Review entity, ReviewDTO dto) {

        entity.setText(dto.getText());

        Movie movie = movieRepository.getReferenceById(dto.getMovieId());
        entity.setMovie(movie);

        user.setId(userService.getProfile().getId());
        user.setName(userService.getProfile().getName());
        user.setEmail(userService.getProfile().getEmail());
        entity.setUser(user);
    }

//    private void copyDtoToEntity(ReviewDTO reviewDTO, Review reviewEntity) {
//        User user = authService.authenticated();
//        reviewEntity.setId(reviewDTO.getId());
//        reviewEntity.setText(reviewDTO.getText());
//        reviewEntity.setMovie(movieRepository.getReferenceById(reviewDTO.getMovieId()));
//        reviewEntity.setUser(user);
//        reviewRepository.save(reviewEntity);
//    }
}
