package tk.srubio.adoptix.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the province database table.
 * 
 */
@Entity
@Table(name="province")
public class Province implements Serializable {
	private static final long serialVersionUID = 1L;
	private Integer id;
	private String name;

	public Province() {
	}


	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(unique=true, nullable=false)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}


	@Column(nullable=false, length=50)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

}