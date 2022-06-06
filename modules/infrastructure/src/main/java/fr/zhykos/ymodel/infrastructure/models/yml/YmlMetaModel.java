package fr.zhykos.ymodel.infrastructure.models.yml;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;

/**
 * A metamodel: an object containing all the sub objects representing the
 * metamodel description YML file
 */
public class YmlMetaModel {

    /**
     * Contained classes
     */
    @Getter
    private final List<YmlClass> classes = new ArrayList<>();

}
