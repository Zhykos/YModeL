package fr.zhykos.ymodel.infrastructure.models.yml;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

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

    /**
     * Origin file of the YML metamodel
     */
    @Getter
    @Setter
    private File originFile;

}
