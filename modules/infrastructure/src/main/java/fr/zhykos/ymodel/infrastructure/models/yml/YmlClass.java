package fr.zhykos.ymodel.infrastructure.models.yml;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import lombok.Getter;
import lombok.Setter;

/**
 * A class representing the metamodel description YML file
 */
public final class YmlClass {

    /**
     * Class name
     */
    @Getter
    @Setter
    private String name;

    /**
     * Inherited class name
     */
    @Getter
    @Setter
    private String inherits;

    /**
     * Fields / Attributes
     */
    @Getter
    private List<YmlField> fields = new ArrayList<>();

    /**
     * Methods
     */
    @Getter
    private List<YmlMethod> methods = new ArrayList<>();

    @Override
    public String toString() {
        return "{ name: '%s', inherits: '%s', fields: [%s], methods: [%s]".formatted(this.name, this.inherits,
                this.fields.stream().map(Object::toString).collect(Collectors.joining(", ")),
                this.methods.stream().map(Object::toString).collect(Collectors.joining(", ")));
    }

}
