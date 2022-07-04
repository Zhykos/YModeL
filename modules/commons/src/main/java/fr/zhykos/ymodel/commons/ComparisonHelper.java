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
package fr.zhykos.ymodel.commons;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import fr.zhykos.ymodel.commons.models.comparison.ComparisonOK;
import fr.zhykos.ymodel.commons.models.comparison.Fail;
import fr.zhykos.ymodel.commons.models.comparison.IComparisonResult;
import fr.zhykos.ymodel.commons.models.comparison.NotEqual;

public final class ComparisonHelper {

    private ComparisonHelper() {
        // Helper class: do nothing and must not be called
    }

    /**
     * Compare a string with a file content
     *
     * @param actualString             The string to compare
     * @param expectedContentsFilepath The expected file containing the other string
     *                                 to compare
     * @return An object typed with the comparison result
     */
    public static IComparisonResult compareStringEqualsFileContentsAsExcepted(final String actualString,
            final Path expectedContentsFilepath) {
        try {
            final String expectedContents = Files.readString(expectedContentsFilepath);
            if (removeLineBreaks(expectedContents).equals(removeLineBreaks(actualString))) {
                return new ComparisonOK();
            }
            return new NotEqual(actualString, expectedContents);
        } catch (final IOException e) {
            return new Fail().setCause(e);
        }
    }

    private static String removeLineBreaks(final String string) {
        return string.replace("\r", "").replace("\n", "");
    }

}
