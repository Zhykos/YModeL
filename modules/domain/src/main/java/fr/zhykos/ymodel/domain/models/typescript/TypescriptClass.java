package fr.zhykos.ymodel.domain.models.typescript;

import java.util.ArrayList;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

import lombok.Getter;
import lombok.Setter;

/**
 * A class representing the Typescript to generate (used in the
 * mustache template file)
 */
public final class TypescriptClass {

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
     * Referenced classes
     */
    @Getter
    private SortedSet<String> imports = new TreeSet<>();

    /**
     * Fields / Attributes
     */
    @Getter
    private List<TypescriptField> fields = new ArrayList<>();

    /**
     * Methods
     */
    @Getter
    private List<TypescriptMethod> methods = new ArrayList<>();

    /**
     * @return <code>true</code> if the class has imports
     */
    public boolean hasImports() {
        return !this.imports.isEmpty();
    }

}
