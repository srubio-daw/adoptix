package tk.srubio.adoptix.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;

/**
 * The persistent class for the pet database table.
 * 
 */
@Entity
@Table(name = "pet")
public class Pet implements Serializable {
	private static final long serialVersionUID = 1L;
	private String id;
	private byte adopted;
	private short age;
	private String breed;
	private byte catsAffinity;
	private String comment;
	private byte dogsAffinity;
	private byte forAdoption;
	private byte forHost;
	private byte kidsAffinity;
	private String name;
	private byte petType;
	private Province province;
	private WebUser adopter;
	private WebUser host;
	private List<Vaccine> vaccines;
	private List<VetVisit> vetVisits;
	private List<VetTest> vetTests;
	private WebUser association;

	public Pet() {
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(unique = true, nullable = false)
	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Column(nullable = false)
	public byte getAdopted() {
		return this.adopted;
	}

	public void setAdopted(byte adopted) {
		this.adopted = adopted;
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
	public byte getCatsAffinity() {
		return this.catsAffinity;
	}

	public void setCatsAffinity(byte catsAffinity) {
		this.catsAffinity = catsAffinity;
	}

	@Column(length = 200)
	public String getComment() {
		return this.comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	@Column(name = "dogs_affinity", nullable = false)
	public byte getDogsAffinity() {
		return this.dogsAffinity;
	}

	public void setDogsAffinity(byte dogsAffinity) {
		this.dogsAffinity = dogsAffinity;
	}

	@Column(name = "for_adoption", nullable = false)
	public byte getForAdoption() {
		return this.forAdoption;
	}

	public void setForAdoption(byte forAdoption) {
		this.forAdoption = forAdoption;
	}

	@Column(name = "for_host", nullable = false)
	public byte getForHost() {
		return this.forHost;
	}

	public void setForHost(byte forHost) {
		this.forHost = forHost;
	}

	@Column(name = "kids_affinity", nullable = false)
	public byte getKidsAffinity() {
		return this.kidsAffinity;
	}

	public void setKidsAffinity(byte kidsAffinity) {
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
	public Province getProvince() {
		return this.province;
	}

	public void setProvince(Province province) {
		this.province = province;
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

	// bi-directional many-to-one association to Vaccine
	@OneToMany(mappedBy = "pet")
	public List<Vaccine> getVaccines() {
		return this.vaccines;
	}

	public void setVaccines(List<Vaccine> vaccines) {
		this.vaccines = vaccines;
	}

	public Vaccine addVaccine(Vaccine vaccine) {
		getVaccines().add(vaccine);
		vaccine.setPet(this);

		return vaccine;
	}

	public Vaccine removeVaccine(Vaccine vaccine) {
		getVaccines().remove(vaccine);
		vaccine.setPet(null);

		return vaccine;
	}

	// bi-directional many-to-one association to VetVisit
	@OneToMany(mappedBy = "pet")
	public List<VetVisit> getVetVisits() {
		return this.vetVisits;
	}

	public void setVetVisits(List<VetVisit> vetVisits) {
		this.vetVisits = vetVisits;
	}

	public VetVisit addVetVisit(VetVisit vetVisit) {
		getVetVisits().add(vetVisit);
		vetVisit.setPet(this);

		return vetVisit;
	}

	public VetVisit removeVetVisit(VetVisit vetVisit) {
		getVetVisits().remove(vetVisit);
		vetVisit.setPet(null);

		return vetVisit;
	}

	// bi-directional many-to-one association to VetTest
	@OneToMany(mappedBy = "pet")
	public List<VetTest> getVetTests() {
		return this.vetTests;
	}

	public void setVetTests(List<VetTest> vetTests) {
		this.vetTests = vetTests;
	}

	public VetTest addVetTest(VetTest vetTest) {
		getVetTests().add(vetTest);
		vetTest.setPet(this);

		return vetTest;
	}

	public VetTest removeVetTest(VetTest vetTest) {
		getVetTests().remove(vetTest);
		vetTest.setPet(null);

		return vetTest;
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