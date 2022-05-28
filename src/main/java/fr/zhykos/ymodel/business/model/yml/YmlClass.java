package fr.zhykos.ymodel.business.model.yml;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import lombok.Getter;
import lombok.Setter;

public class YmlClass {

    @Getter
    @Setter
    private String name;

    @Getter
    private String inherits;

    @Getter
    private List<YmlField> fields = new ArrayList<>();

    @Getter
    private List<YmlMethod> methods = new ArrayList<>();

    @Override
    public String toString() {
        return "{ name: '%s', inherits: '%s', fields: [%s], methods: [%s]".formatted(this.name, this.inherits,
                this.fields.stream().map(Object::toString).collect(Collectors.joining(", ")),
                this.methods.stream().map(Object::toString).collect(Collectors.joining(", ")));
    }

}
