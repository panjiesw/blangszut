package com.panjiesw.blangszut.persistence.entities;

import com.panjiesw.blangszut.persistence.entities.enums.UserStatus;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
public class User implements Serializable {

	private static final long serialVersionUID = -3323176454533795498L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	@Column(name = "id", nullable = false)
	private Long id;

	@Column(nullable = false, unique = true, length = 50)
	@Basic(optional = false)
	private String username;

	@Column(nullable = false, unique = true, length = 50)
	@Basic(optional = false)
	private String email;

	@Column(nullable = false, length = 100)
	@Basic(optional = false)
	private String password;

	@Column(name = "first_name", length = 50)
	@Basic(optional = false)
	private String firstName;

	@Column(name = "last_name", length = 50)
	@Basic(optional = false)
	private String lastName;

	@Column(nullable = false)
	@Basic(optional = false)
	private Integer status = UserStatus.ACTIVE.getStatus();

	@ManyToMany(mappedBy = "userList", fetch = FetchType.LAZY)
	private List<Role> roleList;

	public User() {}

	public User(String username, String email, String password) {
		this.username = username;
		this.email = email;
		this.password = password;
	}

	public User(String username, String email, String password, String firstName, String lastName) {
		this.username = username;
		this.email = email;
		this.password = password;
		this.firstName = firstName;
		this.lastName = lastName;
	}

	public Long getId() {
		return id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public UserStatus getStatus() {
		return UserStatus.getType(this.status);
	}

	public void setStatus(UserStatus status) {
		this.status = status == null ? null : status.getStatus();
	}

	public List<Role> getRoleList() {
		return roleList;
	}

	public void setRoleList(List<Role> roleList) {
		this.roleList = roleList;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof User)) return false;

		User user = (User) o;

		return email.equals(user.email) && id.equals(user.id) && username.equals(user.username);

	}

	@Override
	public int hashCode() {
		int result = id.hashCode();
		result = 31 * result + username.hashCode();
		result = 31 * result + email.hashCode();
		return result;
	}

	@Override
	public String toString() {
		return String.format(
			"User[id=%d, username='%s', email='%s', password='%s', firstName='%s', lastName='%s', status=%d]",
			id, username, email, password, firstName, lastName, status);
	}
}
