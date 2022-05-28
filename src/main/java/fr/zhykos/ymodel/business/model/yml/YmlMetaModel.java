package fr.zhykos.ymodel.business.model.yml;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;

public class YmlMetaModel {

    @Getter
    private final List<YmlClass> classes = new ArrayList<>();

}
