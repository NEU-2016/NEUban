package hu.unideb.inf.rft.neuban.service.domain;

import lombok.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class CardDto extends BaseDto<Long> {

    private static final long serialVersionUID = 1L;

    @NotNull
    @Size(min = 2)
    private String title;

    @NotNull
    private String description;

    @Builder
    public CardDto(final Long id, final String title, final String description) {
        super(id);
        this.title = title;
        this.description = description;
    }

    public static class CardDtoBuilder {
    }
}
