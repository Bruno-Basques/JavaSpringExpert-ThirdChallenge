package com.devsuperior.movieflix.repositories;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.devsuperior.movieflix.entities.Movie;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Long>{
	
	@Query("SELECT obj FROM Movie obj JOIN FETCH obj.genre WHERE obj.id = :id")
	Optional<Movie> findById(Long id);
	
	@Query("SELECT obj FROM Movie obj JOIN FETCH obj.genre WHERE obj.genre.id = :id")
	Page<Movie> findByGenreIdPaged(Long id, Pageable pageable);
}
