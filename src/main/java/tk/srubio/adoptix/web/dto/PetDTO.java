package tk.srubio.adoptix.web.dto;

public class PetDTO {
	private Long id;
	private short age;
	private String breed;
	private boolean catsAffinity;
	private String description;
	private boolean dogsAffinity;
	private boolean forAdoption;
	private boolean forHost;
	private String gender;
	private boolean kidsAffinity;
	private String name;
	private Byte petType;
	private Byte locationId;
	private String locationName;
	private Integer adopter;
	private String adopterName;
	private Integer host;
	private String hostName;
	private Integer association;

	// For creation
	private String userMail;

	public PetDTO() {
	}

	public PetDTO(Long id, short age, String breed, boolean catsAffinity, String description, boolean dogsAffinity,
			boolean forAdoption, boolean forHost, String gender, boolean kidsAffinity, String name, Byte petType,
			Byte locationId, String locationName, Integer adopter, String adopterName, Integer host, String hostName,
			Integer association, String userMail) {
		this.id = id;
		this.age = age;
		this.breed = breed;
		this.catsAffinity = catsAffinity;
		this.description = description;
		this.dogsAffinity = dogsAffinity;
		this.forAdoption = forAdoption;
		this.forHost = forHost;
		this.gender = gender;
		this.kidsAffinity = kidsAffinity;
		this.name = name;
		this.petType = petType;
		this.locationId = locationId;
		this.locationName = locationName;
		this.adopter = adopter;
		this.adopterName = adopterName;
		this.host = host;
		this.hostName = hostName;
		this.association = association;
		this.userMail = userMail;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public short getAge() {
		return age;
	}

	public void setAge(short age) {
		this.age = age;
	}

	public String getBreed() {
		return breed;
	}

	public void setBreed(String breed) {
		this.breed = breed;
	}

	public boolean getCatsAffinity() {
		return catsAffinity;
	}

	public void setCatsAffinity(boolean catsAffinity) {
		this.catsAffinity = catsAffinity;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public boolean getDogsAffinity() {
		return dogsAffinity;
	}

	public void setDogsAffinity(boolean dogsAffinity) {
		this.dogsAffinity = dogsAffinity;
	}

	public boolean getForAdoption() {
		return forAdoption;
	}

	public void setForAdoption(boolean forAdoption) {
		this.forAdoption = forAdoption;
	}

	public boolean getForHost() {
		return forHost;
	}

	public void setForHost(boolean forHost) {
		this.forHost = forHost;
	}

	public boolean getKidsAffinity() {
		return kidsAffinity;
	}

	public void setKidsAffinity(boolean kidsAffinity) {
		this.kidsAffinity = kidsAffinity;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getAdopterName() {
		return adopterName;
	}

	public void setAdopterName(String adopterName) {
		this.adopterName = adopterName;
	}

	public String getHostName() {
		return hostName;
	}

	public void setHostName(String hostName) {
		this.hostName = hostName;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Byte getPetType() {
		return petType;
	}

	public void setPetType(Byte petType) {
		this.petType = petType;
	}

	public Byte getLocationId() {
		return locationId;
	}

	public void setLocationId(Byte location) {
		this.locationId = location;
	}

	public String getLocationName() {
		return locationName;
	}

	public void setLocationName(String locationName) {
		this.locationName = locationName;
	}

	public Integer getAdopter() {
		return adopter;
	}

	public void setAdopter(Integer adopter) {
		this.adopter = adopter;
	}

	public Integer getHost() {
		return host;
	}

	public void setHost(Integer host) {
		this.host = host;
	}

	public Integer getAssociation() {
		return association;
	}

	public void setAssociation(Integer association) {
		this.association = association;
	}

	public String getUserMail() {
		return userMail;
	}

	public void setUserMail(String userMail) {
		this.userMail = userMail;
	}

}
