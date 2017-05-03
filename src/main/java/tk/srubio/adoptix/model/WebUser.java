package tk.srubio.adoptix.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;

/**
 * The persistent class for the web_user database table.
 * 
 */
@Entity
@Table(name = "web_user")
public class WebUser implements Serializable {
	private static final long serialVersionUID = 1L;
	private Long id;
	private String address;
	private String mail;
	private String name;
	private String nif;
	private String password;
	private String surname;
	private String username;
	private Province province;
	private List<Role> roles;

	public WebUser() {
	}

	public WebUser(Long id, String address, String mail, String name, String nif, String password, String surname,
			String username, Province province, List<Role> roles) {
		this.id = id;
		this.address = address;
		this.mail = mail;
		this.name = name;
		this.nif = nif;
		this.password = password;
		this.surname = surname;
		this.username = username;
		this.province = province;
		this.roles = roles;
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

	@Column(length = 200)
	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	@Column(nullable = false, length = 150)
	public String getMail() {
		return this.mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	@Column(nullable = false, length = 25)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(length = 9)
	public String getNif() {
		return this.nif;
	}

	public void setNif(String nif) {
		this.nif = nif;
	}

	@Column(nullable = false, length = 32)
	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Column(length = 80)
	public String getSurname() {
		return this.surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	@Column(nullable = false, length = 20)
	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	// bi-directional many-to-one association to Province
	@ManyToOne
	@JoinColumn(name = "province_id", nullable = false)
	public Province getProvince() {
		return this.province;
	}

	public void setProvince(Province province) {
		this.province = province;
	}

	// bi-directional many-to-many association to Role
	@ManyToMany
	@JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
	public List<Role> getRoles() {
		return this.roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}

}