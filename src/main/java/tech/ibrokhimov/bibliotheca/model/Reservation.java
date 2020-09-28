package tech.ibrokhimov.bibliotheca.model;

import java.time.OffsetDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;

import com.google.common.base.Objects;

@Entity
@Table(name = Reservation.TABLE_NAME)
public class Reservation {
	
	protected static final String TABLE_NAME = "reservation";
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "reservation_id", updatable = false, unique = true, nullable = false)
	private Long id;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "status")
	private BookStatus bookStatus;
	
	@Column(name = "request_date")
	private OffsetDateTime requestDate;
	
	@ManyToOne
	@JoinColumn(name = "book_item_id")
	private BookItem bookItem;
	
	@ManyToOne
	@JoinColumn(name = "reserver_id")
	private User reserver;
	
	@Column(name = "pick_up_date")
	private OffsetDateTime pickUpDate;
	
	public Reservation() {
		super();
	}
	
	public Reservation(Long id) {
		super();
		this.id = id;
	}
	
	public Reservation(BookStatus bookStatus, BookItem bookItem, User reserver, OffsetDateTime pickUpDate) {
		super();
		this.bookStatus = bookStatus;
		this.bookItem = bookItem;
		this.reserver = reserver;
		this.pickUpDate = pickUpDate;
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public BookStatus getBookStatus() {
		return bookStatus;
	}

	public void setBookStatus(BookStatus bookStatus) {
		this.bookStatus = bookStatus;
	}

	public OffsetDateTime getRequestDate() {
		return requestDate;
	}

	public void setRequestDate(OffsetDateTime requestDate) {
		this.requestDate = requestDate;
	}

	public BookItem getBookItem() {
		return bookItem;
	}

	public void setBookItem(BookItem bookItem) {
		this.bookItem = bookItem;
	}

	public User getReserver() {
		return reserver;
	}

	public void setReserver(User reserver) {
		this.reserver = reserver;
	}

	public OffsetDateTime getPickUpDate() {
		return pickUpDate;
	}

	public void setPickUpDate(OffsetDateTime pickUpDate) {
		this.pickUpDate = pickUpDate;
	}

	@PrePersist
	public void prePersist() {
		requestDate = OffsetDateTime.now();
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj == null) return false;
		if(obj == this) return true;
        if(getClass() != obj.getClass()) return false;
        final Reservation other = (Reservation)obj;
        return Objects.equal(this.id, other.id);
	}
	
	@Override
	public int hashCode() {
		return Objects.hashCode(this.id);
	}
	
	@Override
	public String toString() {
		return "Reservation[id=" + id + ",status=" + bookStatus + ",reserver=" + reserver.getUsername() + ",pickUpDate=" + pickUpDate + "]";
	}
	
}
