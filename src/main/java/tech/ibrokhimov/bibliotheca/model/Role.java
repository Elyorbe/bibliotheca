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
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;

import org.springframework.util.Assert;

import com.google.common.base.Objects;
import com.google.common.base.Strings;

@Entity
@Table(name = Role.TABLE_NAME)
public class Role {

	protected static final String TABLE_NAME = "role";
	
	private static final Long ADMIN_ROLE_ID = 1L;
	private static final Long USER_ROLE_ID = 2L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "role_id", updatable = false, unique = true, nullable = false)
	private Long id;
	
	@ManyToMany(mappedBy = "roles")
	Set<User> roles;
	
	@Column(name = "role_name", unique = true, nullable = false)
	private String roleName;
	
	@Column(name = "active_status", nullable = false)
	private Boolean activeStatus = true;
	
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

	public Role() {
		super();
	}
	
	public Role(Long id) {
		super();
		this.id = id;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		Assert.isTrue(!Strings.isNullOrEmpty(roleName), "Role name must not be empty!");
		this.roleName = roleName;
	}

	public Boolean isActive() {
		return activeStatus;
	}

	public void setActive(Boolean activeStatus) {
		this.activeStatus = activeStatus;
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
        final Role other = (Role)obj;
        return Objects.equal(this.id, other.id);
	}
	
	@Override
	public int hashCode() {
		return Objects.hashCode(this.id);
	}
	
	@Override
	public String toString() {
		return "User[id=" + id + ",roleName=" + roleName + "]";
	}
	
	public static final Role getAdminRole() {
		return new Role(ADMIN_ROLE_ID);
	}
	
	public static final Role getUserRole() {
		return new Role(USER_ROLE_ID);
	}
}