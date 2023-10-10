package cane.brothers.tags;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

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
