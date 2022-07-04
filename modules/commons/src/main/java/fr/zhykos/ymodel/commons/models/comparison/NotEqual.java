package fr.zhykos.ymodel.commons.models.comparison;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@AllArgsConstructor
@ToString
public class NotEqual implements IComparisonResult {

    @Getter
    private String actual;

    @Getter
    private String expected;

}
