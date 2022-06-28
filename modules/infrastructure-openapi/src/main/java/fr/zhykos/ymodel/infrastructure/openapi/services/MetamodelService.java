/*
 * Copyright 2022 Thomas "Zhykos" Cicognani.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software
 * without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit
 * persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR
 * PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR
 * OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
package fr.zhykos.ymodel.infrastructure.openapi.services;

import java.io.ByteArrayOutputStream;
import java.util.Base64;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

import fr.zhykos.ymodel.infrastructure.models.ELanguage;
import fr.zhykos.ymodel.infrastructure.openapi.api.MetamodelApi;
import fr.zhykos.ymodel.infrastructure.openapi.model.InlineResponse200;
import fr.zhykos.ymodel.infrastructure.services.GenerationService.GenerationException;
import fr.zhykos.ymodel.infrastructure.services.ParsingService.SyntaxException;
import fr.zhykos.ymodel.infrastructure.services.TransformationService.SemanticListException;
import fr.zhykos.ymodel.infrastructure.services.YmodelService;
import fr.zhykos.ymodel.infrastructure.services.ZipResultService.ZipException;

public final class MetamodelService implements MetamodelApi {

    @Override
    public InlineResponse200 generateMetamodel(final GenerateMetamodelMultipartForm multipartForm) {
        // TODO check for null values in query
        // TODO delete ELanguage and check dynamicaly
        final ELanguage targetLanguage = ELanguage.valueOf(multipartForm.language.toUpperCase());
        try {
            final ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
            new YmodelService().generateMetamodel(multipartForm._file, targetLanguage, byteStream);
            final byte[] zipResult = byteStream.toByteArray();
            final byte[] zipBase64 = Base64.getEncoder().encode(zipResult);
            return new InlineResponse200().zip(zipBase64);
        } catch (final GenerationException | SemanticListException | SyntaxException | ZipException e) {
            // XXX Finer exceptions!
            throw new WebApplicationException("Cannot generate metamodel", e,
                    Response.Status.INTERNAL_SERVER_ERROR);
        }
    }

}
