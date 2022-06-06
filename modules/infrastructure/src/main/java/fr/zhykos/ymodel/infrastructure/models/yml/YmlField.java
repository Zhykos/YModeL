package fr.zhykos.ymodel.infrastructure.models.yml;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * A field / attribute representing the metamodel description YML file
 */
@AllArgsConstructor
public final class YmlField {

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
