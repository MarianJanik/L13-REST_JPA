package cz.marianjanik.L13REST_JPA;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
public class MainController {

    @Autowired
    MovieRepository movieRepository;

    @GetMapping(path = "/movie/all")
    public Iterable<Movie> getAllMovie() {
        return movieRepository.findAll();
    }

    @GetMapping(path = "/movie/id/{idMovie}")
    public Movie getMovieById(@PathVariable Integer idMovie) {
        Movie sendMovie = new Movie();
        if (movieRepository.findById(idMovie).isPresent())
                sendMovie = movieRepository.findById(idMovie).orElseThrow();
        return sendMovie;
    }

    @PostMapping(path = "/movie/save")
    public String addMovie(@RequestBody Movie movie) {
        movieRepository.save(movie);
        return "Zapsáno jest.";
    }

    @PutMapping(path = "/movie/update")
    public String updateMovie(@RequestBody Movie movie) {
        String message = "";
        if (movieRepository.findById(movie.getId()).isPresent()) {
            movieRepository.save(movie);
            return "Film byl upraven.";
        } else return "Film se nepodařilo najít podle Id.";
    }

    @DeleteMapping(path = "/movie/id/{idMovie}")
    public String deleteMovieById(@PathVariable Integer idMovie) {
        if (movieRepository.findById(idMovie).isPresent()) {
            movieRepository.deleteById(idMovie);
            return "Film byl odstraněn.";
        } else return "Film se dle 'ID' nepodařilo najít. Zadej jiné ID.";
    }

    @DeleteMapping(path = "/movie/name/")
    public String deleteMovieByName(@RequestParam String name) {
        Iterable<Movie> allMovie = movieRepository.findAll();
        String message = "Film s názvem " + name + " nebyl nalezen.";
        for (Movie movie : allMovie) {
            if (movie.getName().equals(name)) {
                movieRepository.deleteById(movie.getId());
                message = "Všechy filmy s názvem " + name + " byly odstraněny.";
            }
        }
        return message;
    }
}
