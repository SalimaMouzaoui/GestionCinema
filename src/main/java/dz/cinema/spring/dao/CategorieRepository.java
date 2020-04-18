package dz.cinema.spring.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.webmvc.RepositoryRestController;

import dz.cinema.spring.entities.Categorie;

@RepositoryRestController
public interface CategorieRepository extends JpaRepository<Categorie, Long>{

}
