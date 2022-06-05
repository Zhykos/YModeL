package fr.zhykos.ymodel.business.models.typescript;

import lombok.Getter;
import lombok.Setter;

/**
 * A field / attribute representing the Typescript to generate (used in the
 * mustache template file)
 */
public final class TypescriptField {

    /**
     * Field name
     */
    @Getter
    @Setter
    private String name;

    /**
     * Type (number, string, etc.)
     */
    @Getter
    @Setter
    private String type;

    @Override
    public String toString() {
        return "{ name: '%s', type: '%s' }".formatted(this.name, this.type);
    }

}
