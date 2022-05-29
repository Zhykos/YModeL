package fr.zhykos.ymodel.business.models.typescript;

import java.util.ArrayList;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;
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
    private SortedSet<String> imports = new TreeSet<>();

    @Getter
    private List<TypescriptField> fields = new ArrayList<>();

    @Getter
    private List<TypescriptMethod> methods = new ArrayList<>();

    @Override
    public String toString() {
        return "{ name: '%s', inherits: '%s', imports: [%s], fields: [%s], methods: [%s]".formatted(this.name,
                this.inherits,
                this.imports.stream().map(Object::toString).collect(Collectors.joining(", ")),
                this.fields.stream().map(Object::toString).collect(Collectors.joining(", ")),
                this.methods.stream().map(Object::toString).collect(Collectors.joining(", ")));
    }

    public boolean hasImports() {
        return !this.imports.isEmpty();
    }

}
