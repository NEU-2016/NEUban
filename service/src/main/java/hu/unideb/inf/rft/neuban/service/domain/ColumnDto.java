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
public class ColumnDto extends BaseDto<Long> {

    private static final long serialVersionUID = 1L;

    @NotNull
    @Size(min = 2, max = 20)
    private String title;

    private List<CardDto> cards;

    @Builder
    public ColumnDto(final Long id, final String title, final List<CardDto> cards) {
        super(id);
        this.title = title;
        this.cards = cards;
    }

    public static class ColumnDtoBuilder {

        private List<CardDto> cards = Collections.emptyList();

        public ColumnDtoBuilder cards(final List<CardDto> cards) {
            if (cards != null) {
                this.cards = cards;
            }
            return this;
        }
    }
}
