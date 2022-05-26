package fr.zhykos.ymodel.business.model;

import java.util.ArrayList;
import java.util.List;

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

}
