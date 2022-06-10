package fr.zhykos.ymodel.domain.models.typescript;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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

    @Override
    public String toString() {
        return "{ name: '%s', returns: '%s', params: [%s]".formatted(this.name, this.returns,
                this.parameters.stream().map(Object::toString).collect(Collectors.joining(", ")));
    }

}
