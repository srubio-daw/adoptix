package tk.srubio.adoptix.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * The persistent class for the pet database table.
 * 
 */
@Entity
@Table(name = "pet")
public class Pet implements Serializable {
	private static final long serialVersionUID = 1L;
	private Long id;
	private short age;
	private String breed;
	private boolean catsAffinity;
	private String description;
	private boolean dogsAffinity;
	private boolean forAdoption;
	private boolean forHost;
	private boolean kidsAffinity;
	private String name;
	private byte petType;
	private Province location;
	private WebUser adopter;
	private WebUser host;
	private WebUser association;

	public Pet() {
	}

	public Pet(Long id, short age, String breed, boolean catsAffinity, String description, boolean dogsAffinity,
			boolean forAdoption, boolean forHost, boolean kidsAffinity, String name, byte petType, Province province,
			WebUser adopter, WebUser host, WebUser association) {
		this.id = id;
		this.age = age;
		this.breed = breed;
		this.catsAffinity = catsAffinity;
		this.description = description;
		this.dogsAffinity = dogsAffinity;
		this.forAdoption = forAdoption;
		this.forHost = forHost;
		this.kidsAffinity = kidsAffinity;
		this.name = name;
		this.petType = petType;
		this.location = province;
		this.adopter = adopter;
		this.host = host;
		this.association = association;
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

	@Column(nullable = false)
	public short getAge() {
		return this.age;
	}

	public void setAge(short age) {
		this.age = age;
	}

	@Column(nullable = false, length = 50)
	public String getBreed() {
		return this.breed;
	}

	public void setBreed(String breed) {
		this.breed = breed;
	}

	@Column(name = "cats_affinity", nullable = false)
	public boolean getCatsAffinity() {
		return this.catsAffinity;
	}

	public void setCatsAffinity(boolean catsAffinity) {
		this.catsAffinity = catsAffinity;
	}

	@Column(length = 200)
	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Column(name = "dogs_affinity", nullable = false)
	public boolean getDogsAffinity() {
		return this.dogsAffinity;
	}

	public void setDogsAffinity(boolean dogsAffinity) {
		this.dogsAffinity = dogsAffinity;
	}

	@Column(name = "for_adoption", nullable = false)
	public boolean getForAdoption() {
		return this.forAdoption;
	}

	public void setForAdoption(boolean forAdoption) {
		this.forAdoption = forAdoption;
	}

	@Column(name = "for_host", nullable = false)
	public boolean getForHost() {
		return this.forHost;
	}

	public void setForHost(boolean forHost) {
		this.forHost = forHost;
	}

	@Column(name = "kids_affinity", nullable = false)
	public boolean getKidsAffinity() {
		return this.kidsAffinity;
	}

	public void setKidsAffinity(boolean kidsAffinity) {
		this.kidsAffinity = kidsAffinity;
	}

	@Column(nullable = false, length = 20)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "pet_type", nullable = false)
	public byte getPetType() {
		return this.petType;
	}

	public void setPetType(byte petType) {
		this.petType = petType;
	}

	// uni-directional many-to-one association to Province
	@ManyToOne
	@JoinColumn(name = "province", nullable = false)
	public Province getLocation() {
		return this.location;
	}

	public void setLocation(Province province) {
		this.location = province;
	}

	// uni-directional many-to-one association to WebUser
	@ManyToOne
	@JoinColumn(name = "adopter")
	public WebUser getAdopter() {
		return this.adopter;
	}

	public void setAdopter(WebUser adopter) {
		this.adopter = adopter;
	}

	// uni-directional many-to-one association to WebUser
	@ManyToOne
	@JoinColumn(name = "host")
	public WebUser getHost() {
		return this.host;
	}

	public void setHost(WebUser host) {
		this.host = host;
	}

	// uni-directional many-to-one association to WebUser
	@ManyToOne
	@JoinColumn(name = "association", nullable = false)
	public WebUser getAssociation() {
		return this.association;
	}

	public void setAssociation(WebUser association) {
		this.association = association;
	}

}