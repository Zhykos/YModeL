package fr.zhykos.ymodel.domain.models;

/**
 * Common class for all template classes
 */
public interface ITemplateClass {

    /**
     * @return Class name
     */
    String getName();

    /**
     * @return <code>true</code> if the class has imports
     */
    boolean hasImports();

    /**
     * @return The file name containing the current class
     */
    String getFileClassName();

}
