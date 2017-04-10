package tk.srubio.adoptix.web.dto;

public enum RoleEnum {
	USUARIO(1, "usuario"), ASOCIACION(2, "asociacion");

	private int id;
	private String name;

	RoleEnum(int id, String name) {
		this.id = id;
		this.name = name;
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}
}
