package tk.srubio.adoptix.web.dto;

import java.math.BigDecimal;
import java.util.Date;

public class VetVisitDTO {
	private Long id;
	private BigDecimal cost;
	private String description;
	private Date visitDate;
	private Long petId;

	public VetVisitDTO() {

	}

	public VetVisitDTO(Long id, BigDecimal cost, String description, Date visitDate, Long petId) {
		this.id = id;
		this.cost = cost;
		this.description = description;
		this.visitDate = visitDate;
		this.petId = petId;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public BigDecimal getCost() {
		return cost;
	}

	public void setCost(BigDecimal cost) {
		this.cost = cost;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getVisitDate() {
		return visitDate;
	}

	public void setVisitDate(Date visitDate) {
		this.visitDate = visitDate;
	}

	public Long getPetId() {
		return petId;
	}

	public void setPetId(Long petId) {
		this.petId = petId;
	}
}
