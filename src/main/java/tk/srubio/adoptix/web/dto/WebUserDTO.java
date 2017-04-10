package tk.srubio.adoptix.web.dto;

public class WebUserDTO {
	private Integer id;
	private String address;
	private String mail;
	private String name;
	private String nif;
	private String password;
	private String surname;
	private String username;
	private Byte province;
	private boolean association;

	public WebUserDTO() {
	}

	public WebUserDTO(Integer id, String address, String mail, String name, String nif, String password, String surname,
			String username, byte province, boolean association) {
		this.id = id;
		this.address = address;
		this.mail = mail;
		this.name = name;
		this.nif = nif;
		this.password = password;
		this.surname = surname;
		this.username = username;
		this.province = province;
		this.association = association;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNif() {
		return nif;
	}

	public void setNif(String nif) {
		this.nif = nif;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Byte getProvince() {
		return province;
	}

	public void setProvince(Byte province) {
		this.province = province;
	}

	public boolean isAssociation() {
		return association;
	}

	public void setAssociation(boolean association) {
		this.association = association;
	}

}
