package fr.zhykos.ymodel.business.model.yml;

import lombok.Getter;
import lombok.Setter;

public class YmlMethodParameter {

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
