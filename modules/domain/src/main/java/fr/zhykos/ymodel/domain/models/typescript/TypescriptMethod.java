package fr.zhykos.ymodel.domain.models.typescript;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

/**
 * A method representing the Typescript to generate (used in the
 * mustache template file)
 */
public final class TypescriptMethod {

    /**
     * Name
     */
    @Getter
    @Setter
    private String name;

    /**
     * Returns type (number, string, etc.)
     */
    @Getter
    @Setter
    private String returns;

    /**
     * Parameters
     */
    @Getter
    private List<TypescriptMethodParameter> parameters = new ArrayList<>();

}
