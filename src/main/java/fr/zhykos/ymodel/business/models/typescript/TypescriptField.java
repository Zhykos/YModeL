package fr.zhykos.ymodel.business.models.typescript;

import lombok.Getter;
import lombok.Setter;

public class TypescriptField {

    @Getter
    @Setter
    private String name;

    @Getter
    @Setter
    private String type;

    @Override
    public String toString() {
        return "{ name: '%s', type: '%s' }".formatted(this.name, this.type);
    }

}
