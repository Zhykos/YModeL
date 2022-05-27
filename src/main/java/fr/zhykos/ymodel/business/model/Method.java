package fr.zhykos.ymodel.business.model;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import lombok.Getter;
import lombok.Setter;

public class Method {

    @Getter
    @Setter
    private String name;

    @Getter
    @Setter
    private String returns;

    @Getter
    private List<MethodParameter> params = new ArrayList<>();

    @Override
    public String toString() {
        return "{ name: '%s', returns: '%s', params: [%s]".formatted(this.name, this.returns,
                this.params.stream().map(Object::toString).collect(Collectors.joining(", ")));
    }

}
