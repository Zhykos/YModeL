package fr.zhykos.ymodel.business.services.impl;

import java.io.IOException;
import java.io.StringWriter;
import java.util.List;

import com.github.mustachejava.DefaultMustacheFactory;
import com.github.mustachejava.Mustache;
import com.github.mustachejava.MustacheFactory;

import org.mapstruct.factory.Mappers;

import fr.zhykos.ymodel.business.model.typescript.TypescriptClass;
import fr.zhykos.ymodel.business.model.typescript.TypescriptMapper;
import fr.zhykos.ymodel.infra.Returns;
import fr.zhykos.ymodel.infra.models.yml.YmlClass;
import fr.zhykos.ymodel.infra.models.yml.YmlMetaModel;

public class GenerationTypescriptService {

    public List<Returns<String, IOException>> generate(final YmlMetaModel metaModel) {
        return metaModel.getClasses().stream().map(GenerationTypescriptService::transformIntoTypescript).toList();
    }

    private static Returns<String, IOException> transformIntoTypescript(final YmlClass classs) {
        final TypescriptMapper mapper = Mappers.getMapper(TypescriptMapper.class);
        final TypescriptClass typescriptClass = mapper.map(classs);
        typescriptClass.getMethods()
                .forEach(method -> {
                    if (!method.getParams().isEmpty()) {
                        method.getParams().get(method.getParams().size() - 1).setLastParam(true);
                        method.getParams().forEach(param -> param.setType(mapTypescriptType(param.getType())));
                    }
                    method.setReturns(mapTypescriptType(method.getReturns()));
                });
        typescriptClass.getFields()
                .forEach(field -> field.setType(mapTypescriptType(field.getType())));
        // if (classs.getInheritsClass() != null) {
        // typescriptClass.setInherits(classs.getInheritsClass().getName());
        // }
        return executeTemplate(typescriptClass, "typescript");
    }

    private static String mapTypescriptType(final String type) {
        return switch (type) {
            case "int", "float" -> "number";
            case "char" -> "string";
            default -> type;
        };
    }

    private static Returns<String, IOException> executeTemplate(final TypescriptClass templateModel,
            final String mustacheFilename) {
        try {
            final MustacheFactory mustacheFactory = new DefaultMustacheFactory();
            final Mustache mustache = mustacheFactory
                    .compile("src/main/resources/templates/%s.mustache".formatted(mustacheFilename));
            final StringWriter stringWriter = new StringWriter();
            mustache.execute(stringWriter, templateModel).flush();
            return Returns.resolve(stringWriter.toString().replace("&#39;", "'"));
        } catch (IOException exception) {
            return Returns.reject(exception);
        }
    }
}
