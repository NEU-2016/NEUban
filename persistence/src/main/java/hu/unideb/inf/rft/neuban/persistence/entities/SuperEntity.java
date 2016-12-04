package hu.unideb.inf.rft.neuban.persistence.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Super entity for the database entities.
 * 
 * @author Erdei Krisztián
 * @param <ID> Generated ID
 */
@NoArgsConstructor
@AllArgsConstructor 
@Data
@MappedSuperclass
public abstract class SuperEntity<ID extends Serializable> implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private ID id;
}
