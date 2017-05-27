package tk.srubio.adoptix.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;

/**
 * The persistent class for the vet_test database table.
 * 
 */
@Entity
@Table(name = "vet_test")
public class VetTest implements Serializable {
	private static final long serialVersionUID = 1L;
	private Long id;
	private Date appliedOn;
	private String description;
	private String name;
	private Pet pet;

	public VetTest() {
	}

	public VetTest(Long id, Date appliedOn, String description, String name, Pet pet) {
		this.id = id;
		this.appliedOn = appliedOn;
		this.description = description;
		this.name = name;
		this.pet = pet;
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

	@Temporal(TemporalType.DATE)
	@Column(name = "applied_on", nullable = false)
	public Date getAppliedOn() {
		return this.appliedOn;
	}

	public void setAppliedOn(Date appliedOn) {
		this.appliedOn = appliedOn;
	}

	@Column(length = 200)
	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Column(nullable = false, length = 50)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	// bi-directional many-to-one association to Pet
	@ManyToOne
	@JoinColumn(name = "pet", nullable = false)
	public Pet getPet() {
		return this.pet;
	}

	public void setPet(Pet pet) {
		this.pet = pet;
	}

}