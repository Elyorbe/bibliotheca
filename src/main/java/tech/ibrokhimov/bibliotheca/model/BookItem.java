package tech.ibrokhimov.bibliotheca.model;

import java.time.LocalDate;
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
	
	@Column(name = "edition")
	private String editon;
	
	@Column(name = "pub_date")
	private LocalDate publicationDate;
	
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
	private LocalDate purchaseDate;	
	
	@Column(name = "price")
	private String price;
	
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
	
	public static class Builder {
		private String editon;
		private LocalDate publicationDate;
		private String iSBN;
		private BookFormat format;
		private BookStatus status;
		private Integer rackNumber;
		private String locationIdentifier;
		private LocalDate purchaseDate;
		private String price;
		private Book book;
		
		public Builder(String edition, String iSBN) {
			this.editon = edition;
			this.iSBN = iSBN;
		}
		
		public Builder publicationDate(LocalDate val)
			{ publicationDate = val;	return this; }
		public Builder format(BookFormat val)
			{ format = val;				return this; }
		public Builder status(BookStatus val)
			{ status = val;				return this; }
		public Builder rackNumber(Integer val)
			{ rackNumber = val;			return this; }
		public Builder locationIdentifier(String val)
			{ locationIdentifier = val;	return this; }
		public Builder purchaseDate(LocalDate val) 
			{ purchaseDate = val; 		return this; }
		public Builder price(String val)
			{ price = val;				return this; }
		public Builder book(Book val)
			{ book = val;				return this; }
		
		public BookItem build() {
			return new BookItem(this);
		}
	}
	
	private BookItem(Builder builder) {
		editon = builder.editon;
		publicationDate = builder.publicationDate;
		iSBN = builder.iSBN;
		format = builder.format;
		status = builder.status;
		rackNumber = builder.rackNumber;
		locationIdentifier = builder.locationIdentifier;
		purchaseDate = builder.purchaseDate;
		price = builder.price;
		book = builder.book;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public String getEditon() {
		return editon;
	}

	public void setEditon(String editon) {
		this.editon = editon;
	}
	
	public LocalDate getPublicationDate() {
		return publicationDate;
	}

	public void setPublicationDate(LocalDate publicationDate) {
		this.publicationDate = publicationDate;
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

	public LocalDate getPurchaseDate() {
		return purchaseDate;
	}

	public void setPurchaseDate(LocalDate purchaseDate) {
		this.purchaseDate = purchaseDate;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
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
