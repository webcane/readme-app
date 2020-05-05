package cane.brothers.tags;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

/**
 * @author mniedre
 */
@Data
@NoArgsConstructor
public class TagForm {

    @NotNull
    private String value;
}
