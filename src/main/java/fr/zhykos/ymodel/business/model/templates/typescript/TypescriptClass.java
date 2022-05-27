package fr.zhykos.ymodel.business.model.templates.typescript;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import lombok.Getter;
import lombok.Setter;

public class TypescriptClass {

    @Getter
    @Setter
    private String name;

    @Getter
    @Setter
    private String inherits;

    @Getter
    private List<TypescriptMethod> methods = new ArrayList<>();

    @Override
    public String toString() {
        return "{ name: '%s', inherits: '%s', methods: [%s]".formatted(this.name, this.inherits,
                this.methods.stream().map(Object::toString).collect(Collectors.joining(", ")));
    }

}
