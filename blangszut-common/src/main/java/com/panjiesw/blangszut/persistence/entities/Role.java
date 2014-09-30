package com.panjiesw.blangszut.persistence.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
public class Role implements Serializable {

	private static final long serialVersionUID = -7877659400014841167L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	private Long id;

	@Basic(optional = false)
	@Column(nullable = false, length = 50, unique = true)
	private String name;

	@Basic(optional = false)
	@Column(length = 150)
	private String description;

	@JoinTable(name = "user_role", joinColumns = {
		@JoinColumn(name = "role_id", referencedColumnName = "id", nullable = false)}, inverseJoinColumns = {
		@JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)})
	@ManyToMany(fetch = FetchType.LAZY)
	private List<User> userList;

	public Role() {}

	public Role(String name, String description) {
		this.name = name;
		this.description = description;
	}

	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<User> getUserList() {
		return userList;
	}

	public void setUserList(List<User> userList) {
		this.userList = userList;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof Role)) return false;

		Role role = (Role) o;

		return !(description != null ? !description.equals(role.description) : role.description != null) && id.equals(role.id) && name.equals(role.name);

	}

	@Override
	public int hashCode() {
		int result = id.hashCode();
		result = 31 * result + name.hashCode();
		result = 31 * result + (description != null ? description.hashCode() : 0);
		return result;
	}

	@Override
	public String toString() {
		return String.format("Role[id=%d, name='%s', description='%s']", id, name, description);
	}
}
