package cane.brothers.tags;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * @author mniedre
 */
@Data
@NoArgsConstructor
public class TagForm {

    @NotNull
    @Size(max = 255)
    private String value;
}
