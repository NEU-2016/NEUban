package hu.unideb.inf.rft.neuban.persistence.entities;

import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
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
 * Board entity , title and column list.
 * 
 * @author Erdei Krisztián
 * 
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Table(name = "board")
@Entity
public class BoardEntity extends SuperEntity<Long> {

	private static final long serialVersionUID = 1L;

	@Column(name = "title")
	@NotNull
	@Size(min = 2, max = 30)
	private String title;
	
	@JoinColumn(name = "board_id")
	@OrderBy("id")
	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
	private Collection<ColumnEntity> columns;

	@Builder
	public BoardEntity(Long id, String title, Collection<ColumnEntity> columns) {
		super(id);
		this.title = title;
		this.columns = columns;
	}
}
