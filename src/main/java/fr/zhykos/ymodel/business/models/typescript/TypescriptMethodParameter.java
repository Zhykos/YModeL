package fr.zhykos.ymodel.business.models.typescript;

import lombok.Getter;
import lombok.Setter;

public class TypescriptMethodParameter {

    @Getter
    @Setter
    private String name;

    @Getter
    @Setter
    private String type;

    @Getter
    @Setter
    private boolean lastParam;

    @Override
    public String toString() {
        return "{ name: '%s', type: '%s', lastParam: %s }".formatted(this.name, this.type, this.lastParam);
    }

}
