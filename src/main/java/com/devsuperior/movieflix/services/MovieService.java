package com.devsuperior.movieflix.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devsuperior.movieflix.entities.Movie;
import com.devsuperior.movieflix.repositories.GenreRepository;
import com.devsuperior.movieflix.repositories.MovieRepository;
import com.devsuperior.movieflix.services.exceptions.ResourceNotFoundException;

@Service
public class MovieService {

	@Autowired
	private MovieRepository movieRepository;
	
	@Autowired
	private GenreRepository genreRepository;
	
	@Transactional(readOnly = true)
	public Page<Movie> findAllPaged(Pageable pageable){
		Page<Movie> entityPage = movieRepository.findAll(pageable);
		return entityPage;
	}
	
	@Transactional(readOnly = true)
	public Movie findById(Long movieId){
		
    	if (!movieRepository.existsById(movieId)) {
    		throw new ResourceNotFoundException("A movie with the id:" + movieId + " could not be found.");
    	}
    	
		Optional<Movie> entity = movieRepository.findById(movieId);		
		return entity.get();
	}
	
	@Transactional(readOnly = true)
	public Page<Movie> findByGenreIdPaged(String id, Pageable pageable){
		
		Long genreId = Long.parseLong(id);
		
    	if (!genreRepository.existsById(genreId)) {
    		throw new ResourceNotFoundException("A movie with the genre Id:" + genreId + " could not be found, because this genre Id does not exist.");
    	}
    	
		Page<Movie> entityPage = movieRepository.findByGenreIdPaged(genreId, pageable);		
		return entityPage;
	}
}
