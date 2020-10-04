package tech.ibrokhimov.bibliotheca.model;

import org.springframework.data.jpa.repository.JpaRepository;

public interface BookItemRepository extends JpaRepository<BookItem, Long> {
	
	BookItem findFirstByiSBN(String iSBN);
	Long countByiSBN(String iSBN);
}
