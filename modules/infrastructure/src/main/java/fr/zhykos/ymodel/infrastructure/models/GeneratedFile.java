package fr.zhykos.ymodel.infrastructure.models;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

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
    private String filename;

    /**
     * The contents of the file
     */
    @Getter
    private String contents;

}
