package hu.unideb.inf.rft.neuban.service.domain;

import lombok.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Collections;
import java.util.List;

@NoArgsConstructor
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class BoardDto extends BaseDto<Long> {

    private static final long serialVersionUID = 1L;

    @NotNull
    @Size(min = 2, max = 30)
    private String title;

    private List<ColumnDto> columns;

    @Builder
    public BoardDto(final Long id, final String title, final List<ColumnDto> columns) {
        super(id);
        this.title = title;
        this.columns = columns;
    }

    public static class BoardDtoBuilder {

        private List<ColumnDto> columns = Collections.emptyList();

        public BoardDtoBuilder columns(final List<ColumnDto> columns) {
            if (columns != null) {
                this.columns = columns;
            }
            return this;
        }
    }
}