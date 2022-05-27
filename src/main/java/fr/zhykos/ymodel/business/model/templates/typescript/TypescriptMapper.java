package fr.zhykos.ymodel.business.model.templates.typescript;

import org.mapstruct.Mapper;

import fr.zhykos.ymodel.business.model.Classs;
import fr.zhykos.ymodel.business.model.Method;
import fr.zhykos.ymodel.business.model.MethodParameter;

@Mapper
public interface TypescriptMapper {

    TypescriptClass map(Classs classs);

    TypescriptMethod map(Method method);

    TypescriptMethodParameter map(MethodParameter methodParameter);

}
