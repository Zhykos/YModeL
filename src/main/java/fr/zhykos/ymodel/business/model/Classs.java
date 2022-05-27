package fr.zhykos.ymodel.business.model;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import lombok.Getter;
import lombok.Setter;

public class Classs {

    @Getter
    @Setter
    private String name;

    @Getter
    private String inherits;

    @Getter
    @Setter
    private Classs inheritsClass;

    @Getter
    private List<Method> methods = new ArrayList<>();

    @Override
    public String toString() {
        return "{ name: '%s', inherits: '%s', methods: [%s]".formatted(this.name, this.inherits,
                this.methods.stream().map(Object::toString).collect(Collectors.joining(", ")));
    }
}
