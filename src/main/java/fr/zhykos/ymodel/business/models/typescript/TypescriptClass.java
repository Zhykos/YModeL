package fr.zhykos.ymodel.business.models.typescript;

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
    private List<TypescriptField> fields = new ArrayList<>();

    @Getter
    private List<TypescriptMethod> methods = new ArrayList<>();

    @Override
    public String toString() {
        return "{ name: '%s', inherits: '%s', fields: [%s], methods: [%s]".formatted(this.name, this.inherits,
                this.fields.stream().map(Object::toString).collect(Collectors.joining(", ")),
                this.methods.stream().map(Object::toString).collect(Collectors.joining(", ")));
    }

}
