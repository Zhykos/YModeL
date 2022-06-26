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
package fr.zhykos.ymodel.infrastructure.openapi;

import java.io.File;

import fr.zhykos.ymodel.infrastructure.models.ELanguage;
import fr.zhykos.ymodel.infrastructure.openapi.api.MetamodelApi;
import fr.zhykos.ymodel.infrastructure.services.YmodelService;

public class MetamodelService implements MetamodelApi {

    @Override
    public File generateMetamodel(final GenerateMetamodelMultipartForm multipartForm) {
        final ELanguage targetLanguage = ELanguage.valueOf(multipartForm.language.getName().value());
        new YmodelService().generateMetamodel(multipartForm._file, targetLanguage);
        return null; // TODO
        // throw new WebApplicationException("Cannot generate metamodel", e,
        // Response.Status.INTERNAL_SERVER_ERROR);
    }

}