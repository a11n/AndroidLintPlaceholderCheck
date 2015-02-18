
/*
 * Copyright (C) 2011 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package de.ad.android.tools.lint;

import com.android.annotations.NonNull;
import com.android.resources.ResourceFolderType;
import com.android.tools.lint.detector.api.*;
import org.w3c.dom.Attr;

import java.util.Arrays;
import java.util.Collection;

import static com.android.SdkConstants.*;

/**
 * Check which looks at the children of ScrollViews and ensures that they fill/match
 * the parent width instead of setting wrap_content.
 */
public class HardcodedValuesDetector extends LayoutDetector {
    /**
     * The main issue discovered by this detector
     */
    public static final Issue ISSUE = Issue.create(
            "HardcodedTextWithPlaceholder", //$NON-NLS-1$
            "Hardcoded text",

            "Hardcoding text attributes directly in layout files is bad for several reasons:\n" +
                    "\n" +
                    "* When creating configuration variations (for example for landscape or portrait)" +
                    "you have to repeat the actual text (and keep it up to date when making changes)\n" +
                    "\n" +
                    "* The application cannot be translated to other languages by just adding new " +
                    "translations for existing string resources.\n" +
                    "\n" +
                    "In Android Studio and Eclipse there are quickfixes to automatically extract this " +
                    "hardcoded string into a resource lookup.",

            Category.I18N,
            5,
            Severity.WARNING,
            new Implementation(
                    HardcodedValuesDetector.class,
                    Scope.RESOURCE_FILE_SCOPE));

    /**
     * Constructs a new {@link HardcodedValuesDetector}
     */
    public HardcodedValuesDetector() {
    }

    @NonNull
    @Override
    public Speed getSpeed() {
        return Speed.FAST;
    }

    @Override
    public Collection<String> getApplicableAttributes() {
        return Arrays.asList(
                // Layouts
                ATTR_TEXT,
                ATTR_CONTENT_DESCRIPTION,
                ATTR_HINT,
                ATTR_LABEL,
                ATTR_PROMPT,

                // Menus
                ATTR_TITLE
        );
    }

    @Override
    public boolean appliesTo(@NonNull ResourceFolderType folderType) {
        return folderType == ResourceFolderType.LAYOUT || folderType == ResourceFolderType.MENU;
    }

    @Override
    public void visitAttribute(@NonNull XmlContext context, @NonNull Attr attribute) {
        String value = attribute.getValue();
        if (!value.isEmpty() && (value.charAt(0) != '@' && value.charAt(0) != '?' && value.charAt(0) != ':')) {
            // Make sure this is really one of the android: attributes
            if (!ANDROID_URI.equals(attribute.getNamespaceURI())) {
                return;
            }

            context.report(ISSUE, attribute, context.getLocation(attribute),
                    String.format("[I18N] Hardcoded string \"%1$s\", should use `@string` resource or placeholder `:string`",
                            value));
        }
    }
}