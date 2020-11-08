package tech.ibrokhimov.bibliotheca.model;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookItemRepository extends JpaRepository<BookItem, Long> {
	
	BookItem findFirstByiSBN(String iSBN);
	BookItem findByCallNumber(String callNumber);
	
	Long countByiSBN(String iSBN);
	Long countByStatus(BookStatus status);
	
	Page<BookItem> findAll(Pageable pageable);
}
