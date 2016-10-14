package hu.unideb.inf.rft.neuban.persistence.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * User entity with username and password.
 * @author Erdei Krisztián
 *	
 */
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@Entity
@EqualsAndHashCode(callSuper=true)
@Table(name = "Users")
public class UserEntity extends SuperEntity<Long> {
	
	private static final long serialVersionUID = 1L;
	
	@Column(nullable = false, length = 20, unique = true)
	private String userName;

	@Column(nullable = false ,  length = 5)
	private String password;
}
