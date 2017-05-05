package tk.srubio.adoptix.web.dto;

import java.util.Date;

public class RequestDTO {
	private static final long serialVersionUID = 1L;
	private Long id;
	private byte catsAtHome;
	private String comment;
	private byte dogsAtHome;
	private byte kidsAtHome;
	private String phone;
	private Long user;
	private Long pet;
	private boolean adoptOrHost;
	private Date creationDate;

	// Used for forms
	private String userMail;

	public RequestDTO() {

	}

	public RequestDTO(Long id, byte catsAtHome, String comment, byte dogsAtHome, byte kidsAtHome, String phone,
			Long user, Long pet, boolean adoptOrHost, Date creationDate, String userMail) {
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
		this.userMail = userMail;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public byte getCatsAtHome() {
		return catsAtHome;
	}

	public void setCatsAtHome(byte catsAtHome) {
		this.catsAtHome = catsAtHome;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public byte getDogsAtHome() {
		return dogsAtHome;
	}

	public void setDogsAtHome(byte dogsAtHome) {
		this.dogsAtHome = dogsAtHome;
	}

	public byte getKidsAtHome() {
		return kidsAtHome;
	}

	public void setKidsAtHome(byte kidsAtHome) {
		this.kidsAtHome = kidsAtHome;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Long getUser() {
		return user;
	}

	public void setUser(Long user) {
		this.user = user;
	}

	public Long getPet() {
		return pet;
	}

	public void setPet(Long pet) {
		this.pet = pet;
	}

	public boolean isAdoptOrHost() {
		return adoptOrHost;
	}

	public void setAdoptOrHost(boolean adoptOrHost) {
		this.adoptOrHost = adoptOrHost;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public String getUserMail() {
		return userMail;
	}

	public void setUserMail(String userMail) {
		this.userMail = userMail;
	}

}
