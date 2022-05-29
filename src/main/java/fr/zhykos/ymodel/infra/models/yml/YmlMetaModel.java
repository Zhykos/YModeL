package fr.zhykos.ymodel.infra.models.yml;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;

public class YmlMetaModel {

    @Getter
    private final List<YmlClass> classes = new ArrayList<>();

}
