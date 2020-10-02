package tech.ibrokhimov.bibliotheca.model;

import java.time.OffsetDateTime;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;

import com.google.common.base.Objects;

@Entity
@Table(name = Book.TABLE_NAME)
public class Book {
	
	protected final static String TABLE_NAME = "book";
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "book_id", updatable = false, unique = true, nullable = false)
	private Long id;
	
	@Column(name = "title")
	private String title;
	
	@Column(name = "subject")
	private String subject;
	
	@Column(name = "publisher")
	private String publisher;
	
	@Column(name = "lang")
	private String language;
	
	@Column(name = "pages")
	private Integer pages;
	
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
	@JoinColumn(name = "book_author_id")
	private Author author;
	
	@OneToMany(mappedBy = "book")
	private List<BookItem> bookItems;

	public static class Builder {
		private Author author;
		private String title;
		private String subject;
		private String publisher;
		private String language;
		private Integer pages;
		
		public Builder(Author author, String title) {
			this.author = author;
			this.title = title;
		}	
		
		public Builder subject(String val)
			{ subject = val; 	return this; }
		public Builder publisher(String val)
			{ publisher = val; 	return this; }
		public Builder language(String val)
			{ language = val; 	return this; }
		public Builder pages(Integer val)
			{ pages = val; 		return this; }
		
		public Book build() {
			return new Book(this);
		}
	}
	private Book(Builder builder) {
		author = builder.author;
		title = builder.title;
		subject = builder.subject;
		publisher = builder.publisher;
		language = builder.language;
		pages = builder.pages;
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getPublisher() {
		return publisher;
	}

	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
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

	public Author getAuthor() {
		return author;
	}

	public void setAuthor(Author author) {
		this.author = author;
	}

	@PrePersist
	public void prePersist() {
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
        final Book other = (Book)obj;
        return Objects.equal(this.id, other.id);
	}
	
	@Override
	public int hashCode() {
		return Objects.hashCode(this.id);
	}
	
	@Override
	public String toString() {
		return "Book[id=" + id + ",title=" + title + ",publisher=" + publisher + ",language=" + language + "]";
	}
}
