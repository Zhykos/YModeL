package fr.zhykos.ymodel.infra.models;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@EqualsAndHashCode
public class GeneratedFile {

    @Getter
    @Setter
    private String filename;

    @Getter
    @Setter
    private String contents;

}
