package hu.unideb.inf.rft.neuban.service.domain;

import lombok.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class ColumnDto extends BaseDto<Long> {

    @NotNull
    @Size(min = 2, max = 20)
    private String title;

    @Builder
    public ColumnDto(Long id, String title) {
        super(id);
        this.title = title;
    }
}
