package com.devsuperior.movieflix.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.devsuperior.movieflix.dto.MovieCardDTO;
import com.devsuperior.movieflix.dto.MovieDetailsDTO;
import com.devsuperior.movieflix.entities.Movie;
import com.devsuperior.movieflix.services.MovieService;

@RestController
@RequestMapping(value = "/movies")
public class MovieController {

	@Autowired
	private MovieService service;
	
    @Autowired
    private ModelMapper modelMapper;
    
    @PreAuthorize("hasAnyRole('VISITOR', 'MEMBER')")
	@GetMapping()
	public ResponseEntity<Page<MovieCardDTO>> getAll(Pageable pageable) {
    	PageRequest pageRequest = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by("title"));
    	Page<Movie> entityPage = service.findAllPaged(pageRequest);
    	Page<MovieCardDTO> dtoPage = entityPage.map(x -> modelMapper.map(x, MovieCardDTO.class));
		return ResponseEntity.ok(dtoPage);
	}
    
    @PreAuthorize("hasAnyRole('VISITOR', 'MEMBER')")
	@GetMapping(value = "/{id}")
	public ResponseEntity<MovieDetailsDTO> getById(@PathVariable Long id) {
    	Movie entity = service.findById(id);
    	MovieDetailsDTO dto = modelMapper.map(entity, MovieDetailsDTO.class);
		return ResponseEntity.ok(dto);
	}
    
    @PreAuthorize("hasAnyRole('VISITOR', 'MEMBER')")
	@GetMapping(value = "/byGenre")
	public ResponseEntity<Page<MovieCardDTO>> getByGenreId(
			@RequestParam(name = "genreId", defaultValue = "") String genreId,
			Pageable pageable) {
    	PageRequest pageRequest = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by("title"));
    	Page<Movie> entityPage = service.findByGenreIdPaged(genreId, pageRequest);
    	Page<MovieCardDTO> dtoPage = entityPage.map(x -> modelMapper.map(x, MovieCardDTO.class));
		return ResponseEntity.ok(dtoPage);
	}
}
