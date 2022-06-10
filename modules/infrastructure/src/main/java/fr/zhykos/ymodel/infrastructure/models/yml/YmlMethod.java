package fr.zhykos.ymodel.infrastructure.models.yml;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

/**
 * A method representing the metamodel description YML file
 */
public final class YmlMethod {

    /**
     * Name
     */
    @Getter
    @Setter
    private String name;

    /**
     * Returns type (number, string, etc.)
     */
    @Getter
    @Setter
    private String returns;

    /**
     * Parameters
     */
    @Getter
    private List<YmlMethodParameter> parameters = new ArrayList<>();

}
