package tech.ibrokhimov.bibliotheca.model;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;

import com.google.common.base.Objects;

@Entity
@Table(name = BookItem.TABLE_NAME)
public class BookItem {
	
	protected final static String TABLE_NAME = "book_item";
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "book_item_id", updatable = false, unique = true, nullable = false)
	private Long id;
	
	@Column(name = "isbn")
	private String iSBN;
	
	@Enumerated(EnumType.STRING)
	@Column(name ="format")
	private BookFormat format;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "status")
	private BookStatus status;
	
	@Column(name = "rack_number")
	private Integer rackNumber;
	
	@Column(name = "location_identifier")
	private String locationIdentifier;
	
	@Column(name = "purchase_date")
	private OffsetDateTime purchaseDate;	
	
	@Column(name = "price")
	private BigDecimal price;
	
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "creator", referencedColumnName = "account_id", updatable = false, nullable = false)
	private User creator;
	
	@Column(name = "creation_date")
	private OffsetDateTime creationDate;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "updater", referencedColumnName = "account_id", nullable = false)
	private User updater;
	
	@Column(name = "update_date", nullable = false)
	private OffsetDateTime updateDate;
	
	@ManyToOne
	@JoinColumn(name = "book_item_book_id")
	private Book book;
	
	public BookItem() {
		super();
	}
	
	public BookItem(String iSBN, BookFormat format, Integer rackNumber, Book book) {
		super();
		this.iSBN = iSBN;
		this.format = format;
		this.rackNumber = rackNumber;
		this.book = book;
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getiSBN() {
		return iSBN;
	}

	public void setiSBN(String iSBN) {
		this.iSBN = iSBN;
	}

	public BookFormat getFormat() {
		return format;
	}

	public void setFormat(BookFormat format) {
		this.format = format;
	}

	public BookStatus getStatus() {
		return status;
	}

	public void setStatus(BookStatus status) {
		this.status = status;
	}

	public Integer getRackNumber() {
		return rackNumber;
	}

	public void setRackNumber(Integer rackNumber) {
		this.rackNumber = rackNumber;
	}

	public String getLocationIdentifier() {
		return locationIdentifier;
	}

	public void setLocationIdentifier(String locationIdentifier) {
		this.locationIdentifier = locationIdentifier;
	}

	public OffsetDateTime getPurchaseDate() {
		return purchaseDate;
	}

	public void setPurchaseDate(OffsetDateTime purchaseDate) {
		this.purchaseDate = purchaseDate;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public User getCreator() {
		return creator;
	}

	public void setCreator(User creator) {
		this.creator = creator;
	}

	public OffsetDateTime getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(OffsetDateTime creationDate) {
		this.creationDate = creationDate;
	}

	public User getUpdater() {
		return updater;
	}

	public void setUpdater(User updater) {
		this.updater = updater;
	}

	public OffsetDateTime getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(OffsetDateTime updateDate) {
		this.updateDate = updateDate;
	}

	public Book getBook() {
		return book;
	}

	public void setBook(Book book) {
		this.book = book;
	}

	@PrePersist
	public void prePersist() {
		this.status = BookStatus.AVAILABLE;
		this.creationDate = OffsetDateTime.now();
		this.updateDate = this.creationDate;
	}
	
	@PreUpdate
	public void preUpdate() {
		this.updateDate = OffsetDateTime.now();
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj == null) return false;
		if(obj == this) return true;
        if(getClass() != obj.getClass()) return false;
        final BookItem other = (BookItem)obj;
        return Objects.equal(this.id, other.id);
	}
	
	@Override
	public int hashCode() {
		return Objects.hashCode(this.id);
	}
	
	@Override
	public String toString() {
		return "BookItem[id=" + id + ",title=" + iSBN + ",format=" + format + "locationIdentifer=" + locationIdentifier + "]";
	}
}
