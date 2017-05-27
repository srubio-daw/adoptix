package tk.srubio.adoptix.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * The persistent class for the vet_visit database table.
 * 
 */
@Entity
@Table(name = "vet_visit")
public class VetVisit implements Serializable {
	private static final long serialVersionUID = 1L;
	private Long id;
	private BigDecimal cost;
	private String description;
	private Date visitDate;
	private Pet pet;

	public VetVisit() {
	}

	public VetVisit(Long id, BigDecimal cost, String description, Date visitDate, Pet pet) {
		this.id = id;
		this.cost = cost;
		this.description = description;
		this.visitDate = visitDate;
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

	@Column(nullable = false, precision = 10, scale = 2)
	public BigDecimal getCost() {
		return this.cost;
	}

	public void setCost(BigDecimal cost) {
		this.cost = cost;
	}

	@Column(nullable = false, length = 200)
	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "visit_date", nullable = false)
	public Date getVisitDate() {
		return this.visitDate;
	}

	public void setVisitDate(Date visitDate) {
		this.visitDate = visitDate;
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