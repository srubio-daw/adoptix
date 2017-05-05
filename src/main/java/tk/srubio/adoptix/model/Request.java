package tk.srubio.adoptix.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Generated;
import org.hibernate.annotations.GenerationTime;

/**
 * The persistent class for the request database table.
 * 
 */
@Entity
@Table(name = "request")
public class Request implements Serializable {
	private static final long serialVersionUID = 1L;
	private Long id;
	private byte catsAtHome;
	private String comment;
	private byte dogsAtHome;
	private byte kidsAtHome;
	private String phone;
	private WebUser user;
	private Pet pet;
	private boolean adoptOrHost;
	private Date creationDate;

	public Request() {
	}

	public Request(Long id, byte catsAtHome, String comment, byte dogsAtHome, byte kidsAtHome, String phone,
			WebUser user, Pet pet, boolean adoptOrHost, Date creationDate) {
		this.id = id;
		this.catsAtHome = catsAtHome;
		this.comment = comment;
		this.dogsAtHome = dogsAtHome;
		this.kidsAtHome = kidsAtHome;
		this.phone = phone;
		this.user = user;
		this.pet = pet;
		this.adoptOrHost = adoptOrHost;
		this.creationDate = creationDate;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(unique = true, nullable = false)
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "cats_at_home", nullable = false)
	public byte getCatsAtHome() {
		return this.catsAtHome;
	}

	public void setCatsAtHome(byte catsAtHome) {
		this.catsAtHome = catsAtHome;
	}

	@Column(length = 200)
	public String getComment() {
		return this.comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	@Column(name = "dogs_at_home", nullable = false)
	public byte getDogsAtHome() {
		return this.dogsAtHome;
	}

	public void setDogsAtHome(byte dogsAtHome) {
		this.dogsAtHome = dogsAtHome;
	}

	@Column(name = "kids_at_home", nullable = false)
	public byte getKidsAtHome() {
		return this.kidsAtHome;
	}

	public void setKidsAtHome(byte kidsAtHome) {
		this.kidsAtHome = kidsAtHome;
	}

	@Column(nullable = false, length = 9)
	public String getPhone() {
		return this.phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	@ManyToOne
	@JoinColumn(name = "web_user", nullable = false)
	public WebUser getUser() {
		return this.user;
	}

	public void setUser(WebUser user) {
		this.user = user;
	}

	@ManyToOne
	@JoinColumn(name = "pet", nullable = false)
	public Pet getPet() {
		return pet;
	}

	public void setPet(Pet pet) {
		this.pet = pet;
	}

	@Column(name = "adopt_or_host")
	public boolean isAdoptOrHost() {
		return adoptOrHost;
	}

	public void setAdoptOrHost(boolean adoptOrHost) {
		this.adoptOrHost = adoptOrHost;
	}

	@Column(name = "creation_date", insertable = false, updatable = false)
	@Generated(GenerationTime.INSERT)
	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

}