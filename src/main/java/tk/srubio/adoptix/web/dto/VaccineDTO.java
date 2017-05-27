package tk.srubio.adoptix.web.dto;

import java.util.Date;

public class VaccineDTO {
	private Long id;
	private Date appliedOn;
	private String description;
	private String name;
	private Long petId;

	public VaccineDTO() {

	}

	public VaccineDTO(Long id, Date appliedOn, String description, String name, Long petId) {
		this.id = id;
		this.appliedOn = appliedOn;
		this.description = description;
		this.name = name;
		this.petId = petId;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getAppliedOn() {
		return appliedOn;
	}

	public void setAppliedOn(Date appliedOn) {
		this.appliedOn = appliedOn;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getPetId() {
		return petId;
	}

	public void setPetId(Long petId) {
		this.petId = petId;
	}

}
