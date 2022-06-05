package fr.zhykos.ymodel.infra.models;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

/**
 * A model representing a templated generated file
 */
@AllArgsConstructor
@EqualsAndHashCode
public final class GeneratedFile {

    /**
     * Filename
     */
    @Getter
    @Setter
    private String filename;

    /**
     * The contents of the file
     */
    @Getter
    @Setter
    private String contents;

}
