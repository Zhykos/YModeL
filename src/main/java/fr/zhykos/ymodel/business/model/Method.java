package fr.zhykos.ymodel.business.model;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;

public class Method {

    @Getter
    private String name;

    @Getter
    private String returns;

    @Getter
    private List<MethodParam> params = new ArrayList<>();

}
