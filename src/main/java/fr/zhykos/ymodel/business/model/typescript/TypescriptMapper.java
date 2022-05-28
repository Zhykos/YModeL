package fr.zhykos.ymodel.business.model.typescript;

import org.mapstruct.Mapper;

import fr.zhykos.ymodel.business.model.yml.YmlClass;
import fr.zhykos.ymodel.business.model.yml.YmlMethod;
import fr.zhykos.ymodel.business.model.yml.YmlMethodParameter;

@Mapper
public interface TypescriptMapper {

    TypescriptClass map(YmlClass classs);

    TypescriptMethod map(YmlMethod method);

    TypescriptMethodParameter map(YmlMethodParameter methodParameter);

}
