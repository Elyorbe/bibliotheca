package tech.ibrokhimov.bibliotheca.model;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorRepository extends JpaRepository<Author, Long> {
	
	Optional<Author> findByFirstNameAndLastName(String firstName, String lastName);
}
