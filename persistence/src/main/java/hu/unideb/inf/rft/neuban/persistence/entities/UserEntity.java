package hu.unideb.inf.rft.neuban.persistence.entities;

import java.util.List;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import hu.unideb.inf.rft.neuban.persistence.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * User entity with username and password.
 * 
 * @author Erdei Krisztián
 * 
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true, exclude = "password")
@Table(name = "user")
@Entity
public class UserEntity extends SuperEntity<Long> {

	private static final long serialVersionUID = 1L;

	@Column(name = "user_name", unique = true)
	@NotNull
	@Size(min = 3, max = 20)
	private String userName;

	@Column(name = "password")
	@NotNull
	@Size(min = 5)
	private String password;

	@OrderColumn
	@JoinColumn(name = "user_id")
	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
	private List<BoardEntity> boards;

	@Column(name = "role")
	@NotNull
	@Enumerated(EnumType.STRING)
	private Role role;

	@Builder
	public UserEntity(Long id, String userName, String password, Role role, List<BoardEntity> boards) {
		super(id);
		this.userName = userName;
		this.password = password;
		this.role = role;
		this.boards = boards;
	}
}
