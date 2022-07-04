package fr.zhykos.ymodel.commons.models.comparison;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

@ToString
public class Fail implements IComparisonResult {

    @Getter
    @Setter
    @Accessors(chain = true)
    private Throwable cause;

}
