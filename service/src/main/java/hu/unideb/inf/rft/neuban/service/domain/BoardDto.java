package hu.unideb.inf.rft.neuban.service.domain;

import lombok.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Collections;
import java.util.List;

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

    private List<ColumnDto> columns = Collections.emptyList();

    @Builder
    public BoardDto(Long id, String title, List<ColumnDto> columns) {
        super(id);
        this.title = title;
        this.columns = columns;
    }
}