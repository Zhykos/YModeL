package fr.zhykos.ymodel.business.service.impl;

import java.io.IOException;
import java.io.StringWriter;
import java.util.List;

import com.github.mustachejava.DefaultMustacheFactory;
import com.github.mustachejava.Mustache;
import com.github.mustachejava.MustacheFactory;

import org.mapstruct.factory.Mappers;

import fr.zhykos.ymodel.business.model.Classs;
import fr.zhykos.ymodel.business.model.MetaModel;
import fr.zhykos.ymodel.business.model.templates.typescript.TypescriptClass;
import fr.zhykos.ymodel.business.model.templates.typescript.TypescriptMapper;
import fr.zhykos.ymodel.business.service.ETargetLanguage;
import fr.zhykos.ymodel.business.service.ITransformationService;
import fr.zhykos.ymodel.business.service.Returns;

public class TransformationService implements ITransformationService {

    @Override
    public List<Returns<String, IOException>> transform(final MetaModel metaModel,
            final ETargetLanguage targetLanguage) {
        return transformIntoTypescript(metaModel);
    }

    private static List<Returns<String, IOException>> transformIntoTypescript(final MetaModel metaModel) {
        return metaModel.getClasses().stream().map(TransformationService::transformIntoTypescript).toList();
    }

    private static Returns<String, IOException> transformIntoTypescript(final Classs classs) {
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
        if (classs.getInheritsClass() != null) {
            typescriptClass.setInherits(classs.getInheritsClass().getName());
        }
        return executeTemplate(typescriptClass, ETargetLanguage.TYPESCRIPT.name().toLowerCase());
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
