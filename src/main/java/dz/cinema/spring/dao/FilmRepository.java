package dz.cinema.spring.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.web.bind.annotation.CrossOrigin;

import dz.cinema.spring.entities.Film;

@RepositoryRestController
@CrossOrigin("*")
public interface FilmRepository extends JpaRepository<Film, Long>{

}
