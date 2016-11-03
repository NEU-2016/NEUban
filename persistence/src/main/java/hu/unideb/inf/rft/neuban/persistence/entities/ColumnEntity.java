package hu.unideb.inf.rft.neuban.persistence.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * Column entity with username and password.
 * @author Erdei Krisztián
 *	
 */
@Builder //TODO 
@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode(callSuper=true)
@ToString(callSuper = true)
@Table(name = "table_column")
@Entity
public class ColumnEntity extends SuperEntity<Long> {
	
	private static final long serialVersionUID = 1L;
	
	@Column(name = "title")
	@NotNull
	@Size(min = 2, max = 20)
	private String title;
	
	//TODO
}
