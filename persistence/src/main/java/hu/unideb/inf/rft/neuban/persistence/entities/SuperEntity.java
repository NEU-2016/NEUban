package hu.unideb.inf.rft.neuban.persistence.entities;

import java.io.Serializable;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import lombok.Data;

/**
 * Super entity for the database entities.
 * 
 * @author Erdei Krisztián
 * @param <ID> Generated ID
 */
@Data
@MappedSuperclass
public abstract class SuperEntity<ID extends Serializable> implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE)
	private Long id;
}
