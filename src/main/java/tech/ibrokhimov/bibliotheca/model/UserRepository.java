package tech.ibrokhimov.bibliotheca.model;

import java.time.OffsetDateTime;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<User, Long>{

	Optional<User> findByUsername(String username);
	Optional<User> findByUsernameAndFirstName(String username, String firstName);
	
	@Modifying(clearAutomatically = true)
	@Query("UPDATE User SET password = :password, updater = :updater, updateDate = :updateDate WHERE id = :id")
	void updatePassword(@Param("id") Long id, @Param("password") String password, @Param("updater") User updater,@Param("updateDate") OffsetDateTime updateDate);
}
