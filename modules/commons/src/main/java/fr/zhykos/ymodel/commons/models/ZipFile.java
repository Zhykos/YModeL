package fr.zhykos.ymodel.commons.models;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@AllArgsConstructor
@EqualsAndHashCode
public class ZipFile {

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
