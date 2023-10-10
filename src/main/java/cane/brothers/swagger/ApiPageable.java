package cane.brothers.swagger;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.Explode;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD, ElementType.ANNOTATION_TYPE, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Parameters({
    @Parameter(name = "page", in = ParameterIn.QUERY, explode = Explode.TRUE,
        description = "Results page You want to retrieve (0..N)", schema = @Schema(type = "int", defaultValue = "0")),
    @Parameter(name = "size", in = ParameterIn.QUERY, explode = Explode.TRUE,
        description = "Number of records per page", schema = @Schema(type = "int", defaultValue = "20")),
    // sort
})
public @interface ApiPageable {
}
