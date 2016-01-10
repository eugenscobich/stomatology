package md.stomatology.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.security.core.GrantedAuthority;

@Entity
@Table(name = "authorities")
public class Authority implements GrantedAuthority {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	@Column(name = "id")
	private Integer id;

	@Column(name = "username", length = 50)
	private String username;

	@Column(name = "authority", length = 50)
	@Enumerated(EnumType.STRING)
	private AuthorityType authority;

	// ============ Properties Section ============

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getAuthority() {
		return authority.toString();
	}

	public void setAuthority(AuthorityType authority) {
		this.authority = authority;
	}

}
