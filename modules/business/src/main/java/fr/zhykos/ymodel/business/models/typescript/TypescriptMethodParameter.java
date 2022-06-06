package fr.zhykos.ymodel.business.models.typescript;

import lombok.Getter;
import lombok.Setter;

/**
 * Method parameter representing the Typescript to generate (used in the
 * mustache template file)
 */
public final class TypescriptMethodParameter {

    /**
     * Name
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

    /**
     * <code>true</code> if it is the last parameter of the containing method
     */
    @Getter
    @Setter
    private boolean last;

    @Override
    public String toString() {
        return "{ name: '%s', type: '%s', last: %s }".formatted(this.name, this.type, this.last);
    }

}
