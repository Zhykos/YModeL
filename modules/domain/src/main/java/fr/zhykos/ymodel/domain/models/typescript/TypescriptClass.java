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
package fr.zhykos.ymodel.domain.models.typescript;

import java.util.ArrayList;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

import fr.zhykos.ymodel.domain.models.ITemplateClass;
import lombok.Getter;
import lombok.Setter;

/**
 * A class representing the Typescript to generate (used in the
 * mustache template file)
 */
public final class TypescriptClass implements ITemplateClass {

    /**
     * Class name
     */
    @Getter
    @Setter
    private String name;

    /**
     * Inherited class name
     */
    @Getter
    @Setter
    private String inherits;

    /**
     * Referenced classes
     */
    @Getter
    private SortedSet<String> imports = new TreeSet<>();

    /**
     * Fields / Attributes
     */
    @Getter
    private List<TypescriptField> fields = new ArrayList<>();

    /**
     * Methods
     */
    @Getter
    private List<TypescriptMethod> methods = new ArrayList<>();

    @Override
    public boolean hasImports() {
        return !this.imports.isEmpty();
    }

    @Override
    public String getFileClassName() {
        return this.name + ".ts";
    }

}
