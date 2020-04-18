package dz.cinema.spring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import dz.cinema.spring.entities.Film;
import dz.cinema.spring.entities.Salle;
import dz.cinema.spring.entities.Ticket;
import dz.cinema.spring.service.ICinemaInitService;

@SpringBootApplication
public class CinemaApplication implements CommandLineRunner {

	@Autowired
	private ICinemaInitService cinemaInitService;
	
	@Autowired
	private RepositoryRestConfiguration configurer;
	
	public static void main(String[] args) {
		SpringApplication.run(CinemaApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		// TODO Auto-generated method stub
		configurer.exposeIdsFor(Film.class, Salle.class, Ticket.class);
		cinemaInitService.InitVilles();
		cinemaInitService.InitCinemas();
		cinemaInitService.InitSalles();
		cinemaInitService.InitPlaces();
		cinemaInitService.InitSeances();
		cinemaInitService.InitCategories();
		cinemaInitService.InitFilms();
		cinemaInitService.InitProjections();
		cinemaInitService.InitTickets();
	}

}
