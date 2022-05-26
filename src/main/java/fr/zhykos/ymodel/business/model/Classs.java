package fr.zhykos.ymodel.business.model;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

public class Classs {

    @Getter
    private String name;

    @Getter
    private String inherits;

    @Getter
    @Setter
    private Classs inheritsClass;

    @Getter
    private List<Method> methods = new ArrayList<>();

}
