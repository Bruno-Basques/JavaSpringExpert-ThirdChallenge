package com.devsuperior.movieflix.services;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devsuperior.movieflix.dto.ReviewDTO;
import com.devsuperior.movieflix.dto.UserDTO;
import com.devsuperior.movieflix.entities.Movie;
import com.devsuperior.movieflix.entities.Review;
import com.devsuperior.movieflix.entities.User;
import com.devsuperior.movieflix.repositories.ReviewRepository;

@Service
public class ReviewService {

	@Autowired
	private ReviewRepository reviewRepository;
	
	@Autowired
	private MovieService movieService;
	
	@Autowired
	private UserService userService;
	
    @Autowired
    private ModelMapper modelMapper;
	
	@Transactional(readOnly = true)
	public ReviewDTO insert(ReviewDTO reviewDTO){
		
		Movie movie = movieService.findById(reviewDTO.getMovieId());		
		UserDTO userDTO = userService.getProfile();
		User user = modelMapper.map(userDTO, User.class);		
		Review review = new Review(reviewDTO.getText(), movie, user);		
		review = reviewRepository.save(review);
		
		reviewDTO.setId(review.getId());
		reviewDTO.addUser(user);
		
		return reviewDTO;
	}
}
