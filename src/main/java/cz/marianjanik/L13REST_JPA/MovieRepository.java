package cz.marianjanik.L13REST_JPA;

import org.springframework.data.repository.CrudRepository;

public interface MovieRepository extends CrudRepository<Movie,Integer> {
}
