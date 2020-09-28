package tech.ibrokhimov.bibliotheca.model;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;

import com.google.common.base.Objects;

@Entity
@Table(name = CheckOut.TABLE_NAME)
public class CheckOut {
	
	protected static final String TABLE_NAME = "check_out";
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "check_out_id", updatable = false, unique = true, nullable = false)
	private Long id;
	
	@Column(name = "check_out_date")
	private OffsetDateTime checkOutDate;
	
	@Column(name = "due_date")
	private OffsetDateTime dueDate;
	
	@Column(name = "return_date")
	private OffsetDateTime returnDate;
	
	@Column(name = "fine")
	private BigDecimal fine;
	
	@ManyToOne
	@JoinColumn(name = "book_item_id")
	private BookItem bookItem;
	
	@ManyToOne
	@JoinColumn(name = "borrower_id")
	private User borrower;
	
	public CheckOut() {
		super();
	}
	
	public CheckOut(Long id) {
		super();
		this.id = id;
	}

	public CheckOut(OffsetDateTime dueDate, BookItem bookItem, User borrower) {
		super();
		this.dueDate = dueDate;
		this.bookItem = bookItem;
		this.borrower = borrower;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public OffsetDateTime getCheckOutDate() {
		return checkOutDate;
	}

	public void setCheckOutDate(OffsetDateTime checkOutDate) {
		this.checkOutDate = checkOutDate;
	}

	public OffsetDateTime getDueDate() {
		return dueDate;
	}

	public void setDueDate(OffsetDateTime dueDate) {
		this.dueDate = dueDate;
	}

	public OffsetDateTime getReturnDate() {
		return returnDate;
	}

	public void setReturnDate(OffsetDateTime returnDate) {
		this.returnDate = returnDate;
	}

	public BigDecimal getFine() {
		return fine;
	}

	public void setFine(BigDecimal fine) {
		this.fine = fine;
	}

	public BookItem getBookItem() {
		return bookItem;
	}

	public void setBookItem(BookItem bookItem) {
		this.bookItem = bookItem;
	}

	public User getBorrower() {
		return borrower;
	}

	public void setBorrower(User borrower) {
		this.borrower = borrower;
	}

	@PrePersist
	public void prePersist() {
		checkOutDate = OffsetDateTime.now();
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj == null) return false;
		if(obj == this) return true;
        if(getClass() != obj.getClass()) return false;
        final CheckOut other = (CheckOut)obj;
        return Objects.equal(this.id, other.id);
	}
	
	@Override
	public int hashCode() {
		return Objects.hashCode(this.id);
	}
	
	@Override
	public String toString() {
		return "CheckOut[id=" + id + ",checkOutDate=" + checkOutDate + ",dueDate=" + dueDate+ ",borrower=" + borrower.getUsername() + ",returnDate=" + returnDate + "]";
	}
}
