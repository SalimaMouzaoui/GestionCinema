package dz.cinema.spring.service.impl;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Random;
import java.util.stream.Stream;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import dz.cinema.spring.dao.CategorieRepository;
import dz.cinema.spring.dao.CinemaRepository;
import dz.cinema.spring.dao.FilmRepository;
import dz.cinema.spring.dao.PlaceRepository;
import dz.cinema.spring.dao.ProjectionRepository;
import dz.cinema.spring.dao.SalleRepository;
import dz.cinema.spring.dao.SeanceRepository;
import dz.cinema.spring.dao.TicketRepository;
import dz.cinema.spring.dao.VilleRepository;
import dz.cinema.spring.entities.Categorie;
import dz.cinema.spring.entities.Cinema;
import dz.cinema.spring.entities.Film;
import dz.cinema.spring.entities.Place;
import dz.cinema.spring.entities.Projection;
import dz.cinema.spring.entities.Salle;
import dz.cinema.spring.entities.Seance;
import dz.cinema.spring.entities.Ticket;
import dz.cinema.spring.entities.Ville;
import dz.cinema.spring.service.ICinemaInitService;

@Service
@Transactional

public class ICinemaInitServiceImpl implements ICinemaInitService {

	@Autowired
	private VilleRepository villeR;
	
	@Autowired
	private CinemaRepository cinemaR;
	
	@Autowired
	private SalleRepository salleR;
	
	@Autowired
	private PlaceRepository placeR;
	
	@Autowired
	private SeanceRepository seanceR;
	
	@Autowired
	private FilmRepository filmR;
	
	@Autowired
	private ProjectionRepository projectionR;
	
	@Autowired
	private CategorieRepository categorieR;
	
	@Autowired
	private TicketRepository ticketR;
	
	@Override
	public void InitVilles() {
		// TODO Auto-generated method stub
		Stream.of("Alger", "Blida", "Oran", "Annaba").forEach(v -> {
			Ville ville = new Ville();
			ville.setName(v);
			villeR.save(ville);
		});
	}

	@Override
	public void InitCinemas() {
		// TODO Auto-generated method stub
		villeR.findAll().forEach(v -> {
			Stream.of("Ibn Khaldoun", "Ibn Zidoun", "EL Sahel", "Afrique", "Cosmos").forEach(nameCinema -> {
				Cinema cinema = new Cinema();
				cinema.setName(nameCinema);
				cinema.setNbSalles(3 + (int) (Math.random()*7));
				cinema.setVille(v);
				cinemaR.save(cinema);
			});
		});
	}

	@Override
	public void InitSalles() {
		// TODO Auto-generated method stub
		cinemaR.findAll().forEach(cinema -> {
			for (int i = 0; i < cinema.getNbSalles(); i++) {
				Salle salle = new Salle();
				salle.setName("Salle " + (i+1));
				salle.setNbPlaces(13 + (int) (Math.random()*7));
				salle.setCinema(cinema);
				salleR.save(salle);
			}
		});
	}

	@Override
	public void InitPlaces() {
		// TODO Auto-generated method stub
		salleR.findAll().forEach(salle -> {
			for (int i = 0; i < salle.getNbPlaces(); i++) {
				Place place = new Place();
				place.setNumero(i+1);
				place.setSalle(salle);
				placeR.save(place);
				
			}
		});
	}

	@Override
	public void InitSeances() {
		// TODO Auto-generated method stub
		DateFormat dateFormat = new SimpleDateFormat("HH:mm");
		Stream.of("12:00", "15:00", "17:00", "19:00", "21:00").forEach(s -> {
			Seance seance = new Seance();
			try {
				seance.setHeureDebut(dateFormat.parse(s));
				seanceR.save(seance);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});
	}

	@Override
	public void InitFilms() {
		// TODO Auto-generated method stub
		double[] durees = {1, 1.5, 2, 2.5, 3};
		java.util.List<Categorie> categories = categorieR.findAll();
		Stream.of("The fault in our stars", "Me before you", "Spider man", "Hit Man",
				"Fast and furious").forEach(titreFilm -> {
			Film film = new Film();
			film.setTitre(titreFilm);
			film.setDuree(durees[new Random().nextInt(durees.length)]);
			film.setPhoto(titreFilm.replaceAll(" ", ""));
			film.setCategorie(categories.get(new Random().nextInt(categories.size())));
			filmR.save(film);
		});
	}

	@Override
	public void InitProjections() {
		// TODO Auto-generated method stub
		double[] prices = new double[] {250, 400, 600, 1000, 1200};
		List<Film> listFilms = filmR.findAll();
		villeR.findAll().forEach(ville -> {
			ville.getCinemas().forEach(cinema -> {
				cinema.getSalles().forEach(salle -> {
					int index = new Random().nextInt(listFilms.size());
					Film film = listFilms.get(index);
						seanceR.findAll().forEach(seance -> {
							Projection projection = new Projection();
							projection.setDateProjection(new Date());
							projection.setFilm(film);
							projection.setPrix(prices[new Random().nextInt(prices.length)]);
							projection.setSalle(salle);
							projection.setSeance(seance);
							projectionR.save(projection);					
					});
				});
			});
		});
	}

	@Override
	public void InitTickets() {
		// TODO Auto-generated method stub
		projectionR.findAll().forEach(p -> {
			p.getSalle().getPlaces().forEach(place -> {
				Ticket ticket = new Ticket();
				ticket.setPlace(place);
				ticket.setProjection(p);
				ticket.setPrix(p.getPrix());
				ticket.setReserved(false);
				ticketR.save(ticket);
			});
			
		});
	}

	@Override
	public void InitCategories() {
		// TODO Auto-generated method stub
		Stream.of("Histoire", "Actions", "Fiction", "Drama").forEach(cat -> {
			Categorie categorie = new Categorie();
			categorie.setName(cat);
			categorieR.save(categorie);
		});
	}

}
