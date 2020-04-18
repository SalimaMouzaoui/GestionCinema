package dz.cinema.spring.web;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import dz.cinema.spring.dao.FilmRepository;
import dz.cinema.spring.dao.TicketRepository;
import dz.cinema.spring.entities.Film;
import dz.cinema.spring.entities.Ticket;
import lombok.Data;

@RestController
@CrossOrigin("*")
public class CinemaRestController {
	
	@Autowired
	private FilmRepository filmRepository;
	
	@Autowired
	private TicketRepository ticketRepository;
//
//	@GetMapping("/listFilms")
//	public List<Film> listFilms(){
//		return filmRepository.findAll();
//	}
	
	@GetMapping(path="/imageFilm/{id}", produces = MediaType.IMAGE_JPEG_VALUE)
	public byte[] image(@PathVariable(name = "id") Long id ) throws IOException {
		Film film = filmRepository.findById(id).get();
		String namePhoto = film.getPhoto();
		File f = new File(System.getProperty("user.home")+"/cinema/images/"+namePhoto+".jpg");
		Path path = Paths.get(f.toURI());
		return Files.readAllBytes(path);
	}
	
	@PostMapping("/payerTickets")
	@Transactional
	public List<Ticket> payerTickets(@RequestBody TicketForm ticketForm) {
		List<Ticket> tickets = new ArrayList<Ticket>();
		ticketForm.getTickets().forEach(id -> {
			Ticket ticket = ticketRepository.findById(id).get();
			ticket.setNomClient(ticketForm.getNomClient());
			ticket.setReserved(true);
			ticket.setCodePayment(ticketForm.getCodePayment());
			ticketRepository.save(ticket);
			tickets.add(ticket);
		});
		return tickets;
	}
}

@Data
class TicketForm {
	private String nomClient;
	private List<Long> tickets = new ArrayList<>();
	private int codePayment;
}
