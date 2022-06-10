package fr.zhykos.ymodel.infrastructure.models.yml;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

/**
 * A class representing the metamodel description YML file
 */
public final class YmlClass {

    /**
     * Class name
     */
    @Getter
    @Setter
    private String name;

    /**
     * Inherited class name
     */
    @Getter
    @Setter
    private String inherits;

    /**
     * Fields / Attributes
     */
    @Getter
    private List<YmlField> fields = new ArrayList<>();

    /**
     * Methods
     */
    @Getter
    private List<YmlMethod> methods = new ArrayList<>();

}
