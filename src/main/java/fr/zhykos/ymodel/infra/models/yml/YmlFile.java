package fr.zhykos.ymodel.infra.models.yml;

import lombok.Getter;

/**
 * A file (on disk) representing the metamodel description YML file
 */
public class YmlFile {

    /**
     * The metamodel declared in the current file
     */
    @Getter
    private YmlMetaModel metamodel;

}
