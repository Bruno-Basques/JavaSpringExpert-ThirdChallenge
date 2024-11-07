package com.devsuperior.movieflix.controllers;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.devsuperior.movieflix.dto.GenreDTO;
import com.devsuperior.movieflix.entities.Genre;
import com.devsuperior.movieflix.services.GenreService;

@RestController
@RequestMapping(value = "/genres")
public class GenreController {

	@Autowired
	private GenreService service;
	
    @Autowired
    private ModelMapper modelMapper;
    
    @PreAuthorize("hasAnyRole('VISITOR', 'MEMBER')")
	@GetMapping()
	public ResponseEntity<List<GenreDTO>> getAll() {
    	List<Genre> entityList = service.findAll();
    	List<GenreDTO> dtoList = entityList.stream().map(x -> modelMapper.map(x, GenreDTO.class)).toList();
		return ResponseEntity.ok(dtoList);
	}
}
