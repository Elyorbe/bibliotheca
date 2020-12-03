package tech.ibrokhimov.bibliotheca.model;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CheckOutRepository extends JpaRepository<CheckOut, Long> {
	
	CheckOut findTopByBookItem_Id(Long bookItemId);
	List<CheckOut> findByDueDate(LocalDate dueDate);
}
