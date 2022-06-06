package fr.zhykos.ymodel.infrastructure.models.yml;

import lombok.Getter;
import lombok.Setter;

/**
 * Method parameter representing the metamodel description YML file
 */
public final class YmlMethodParameter {

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

    @Override
    public String toString() {
        return "{ name: '%s', type: '%s' }".formatted(this.name, this.type);
    }

}
