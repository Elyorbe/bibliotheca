package tech.ibrokhimov.bibliotheca.model;

import java.time.OffsetDateTime;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;

import org.springframework.util.Assert;

import com.google.common.base.Objects;
import com.google.common.base.Strings;

@Entity
@Table(name = User.TABLE_NAME)
public class User {
	
	protected static final String TABLE_NAME = "account";
	
	private static final Long SYSTEM_USER_ID = 1L;
	private static final Long ADMIN_USER_ID = 2L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "account_id", updatable = false, unique = true, nullable = false)
	private Long id;
	
	@Column(name = "username", updatable = false, unique = true, nullable = false)
	private String username;
	
	@Column(name = "password", nullable = false)
	private String password;
	
	@Column(name = "email", unique = true, nullable = false)
	private String email;
	
	@Column(name = "first_name")
	private String firstName;
	
	@Column(name = "last_name")
	private String lastName;
	
	@Column(name = "books_checked_out")
	private Integer totalBooksCheckedOut;
	
	@Column(name = "active_status", nullable = false)
	private Boolean activeStatus = true;
	
	@Column(name = "deleted_status", nullable = false)
	private Boolean deletedStatus = false;
	
	@Column(name = "immutable_status", nullable = false)
	private Boolean immutableStatus = true;
	
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
	
	@ManyToMany
	@JoinTable(name = "account_role", joinColumns = @JoinColumn(name = "account_id", referencedColumnName = "account_id"),
	inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "role_id"))
	private Set<Role> roles;

	public User() {
		super();
	}

	public User(Long id) {
		super();
		this.id = id;
	}
	
	public Long getId() {
		return id;
	}
	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		Assert.isTrue(!Strings.isNullOrEmpty(username), "Username must not be empty!");
		
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	public Integer getTotalBooksCheckedOut() {
		return totalBooksCheckedOut;
	}
	
	public void setTotalBooksCheckedOut(Integer totalBooksCheckedOut) {
		this.totalBooksCheckedOut = totalBooksCheckedOut;
	}

	public Boolean isActive() {
		return activeStatus;
	}

	public void setActive(Boolean activeStatus) {
		this.activeStatus = activeStatus;
	}

	public Boolean isDeleted() {
		return deletedStatus;
	}

	public void setDeleted(Boolean deletedStatus) {
		this.deletedStatus = deletedStatus;
	}

	public Boolean isImmutable() {
		return immutableStatus;
	}

	public void setImmutable(Boolean immutableStatus) {
		this.immutableStatus = immutableStatus;
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

	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
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
        final User other = (User)obj;
        return Objects.equal(this.id, other.id);
	}
	
	@Override
	public int hashCode() {
		return Objects.hashCode(this.id);
	}
	
	@Override
	public String toString() {
		return "User[id=" + id + ",username=" + username + ",email=" + email + "]";
	}
	
	public static final User getSystemUser() {
		return new User(SYSTEM_USER_ID);
	}
	
	public static final User getAdminUser() {
		return new User(ADMIN_USER_ID);
	}
}

