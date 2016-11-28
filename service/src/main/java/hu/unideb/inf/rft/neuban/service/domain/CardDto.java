package hu.unideb.inf.rft.neuban.service.domain;

import lombok.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;


@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class CardDto extends BaseDto<Long> {

    @NotNull
    @Size(min = 2)
    private String title;

    @NotNull
    private String description;

    private List<UserDto> users;

    @Builder
    public CardDto(Long id, String title, String description, List<UserDto> users) {
        super(id);
        this.title = title;
        this.description = description;
        this.users = users;
    }
}
