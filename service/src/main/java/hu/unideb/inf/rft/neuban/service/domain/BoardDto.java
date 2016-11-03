package hu.unideb.inf.rft.neuban.service.domain;

import java.util.Collection;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import hu.unideb.inf.rft.neuban.persistence.entities.ColumnEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class BoardDto extends BaseDto<Long> {
	
	private static final long serialVersionUID = 1L;

	@NotNull
	@Size(min = 2, max = 30)
	private String title;

	private Collection<ColumnEntity> columns;

	@Builder
	public BoardDto(Long id, String title, Collection<ColumnEntity> columns) {
		super(id);
		this.title = title;
		this.columns = columns;
	}
}