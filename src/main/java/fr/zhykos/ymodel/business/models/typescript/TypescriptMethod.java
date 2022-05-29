package fr.zhykos.ymodel.business.models.typescript;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import lombok.Getter;
import lombok.Setter;

public class TypescriptMethod {

    @Getter
    @Setter
    private String name;

    @Getter
    @Setter
    private String returns;

    @Getter
    private List<TypescriptMethodParameter> parameters = new ArrayList<>();

    @Override
    public String toString() {
        return "{ name: '%s', returns: '%s', params: [%s]".formatted(this.name, this.returns,
                this.parameters.stream().map(Object::toString).collect(Collectors.joining(", ")));
    }

}
