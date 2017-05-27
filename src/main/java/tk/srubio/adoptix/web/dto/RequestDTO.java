package tk.srubio.adoptix.web.dto;

import java.util.Date;

public class RequestDTO {
	private Long id;
	private byte catsAtHome;
	private String comment;
	private byte dogsAtHome;
	private byte kidsAtHome;
	private String phone;
	private Long user;
	private Long pet;
	private String petName;
	private byte petType;
	private boolean adoptOrHost;
	private Date creationDate;
	private Boolean status;
	private String rejectComment;

	// Used for forms
	private String userMail;

	// Used for visualization
	private String userName;

	public RequestDTO() {

	}

	public RequestDTO(Long id, byte catsAtHome, String comment, byte dogsAtHome, byte kidsAtHome, String phone,
			Long user, Long pet, String petName, byte petType, boolean adoptOrHost, Date creationDate, String userMail,
			Boolean status, String rejectComment, String userName) {
		this.id = id;
		this.catsAtHome = catsAtHome;
		this.comment = comment;
		this.dogsAtHome = dogsAtHome;
		this.kidsAtHome = kidsAtHome;
		this.phone = phone;
		this.user = user;
		this.pet = pet;
		this.petName = petName;
		this.petType = petType;
		this.adoptOrHost = adoptOrHost;
		this.creationDate = creationDate;
		this.userMail = userMail;
		this.status = status;
		this.rejectComment = rejectComment;
		this.userName = userName;
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

	public Boolean getStatus() {
		return status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}

	public String getRejectComment() {
		return rejectComment;
	}

	public void setRejectComment(String rejectComment) {
		this.rejectComment = rejectComment;
	}

	public String getPetName() {
		return petName;
	}

	public void setPetName(String petName) {
		this.petName = petName;
	}

	public byte getPetType() {
		return petType;
	}

	public void setPetType(byte petType) {
		this.petType = petType;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

}
