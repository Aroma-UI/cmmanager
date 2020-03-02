/*
 * (c) 2020 by Panayotis Katsaloulis
 *
 * CrossMobile is free software; you can redistribute it and/or modify
 * it under the terms of the CrossMobile Community License as published
 * by the CrossMobile team.
 *
 * CrossMobile is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * CrossMobile Community License for more details.
 *
 * You should have received a copy of the CrossMobile Community
 * License along with CrossMobile; if not, please contact the
 * CrossMobile team at https://crossmobile.tech/contact/
 */
package org.crossmobile.gui.parameters.impl;

import org.crossmobile.gui.elements.VersionDocumentFilter;
import org.crossmobile.gui.parameters.FreeTextParameter;
import org.crossmobile.utils.ParamList;

import static org.crossmobile.utils.ParamsCommon.BUNDLE_VERSION;

public class VersionParameter extends FreeTextParameter {

    public VersionParameter(ParamList list) {
        super(list, BUNDLE_VERSION.tag());
        setValue(getValue());
        setFilter(new VersionDocumentFilter());
        setTooltip(VersionDocumentFilter.TOOLTIP);
    }

    @Override
    public String getVisualTag() {
        return "Application version";
    }

    @Override
    public String getValue() {
        String value = super.getValue();
        String[] parts = value.split("\\.");
        StringBuilder out = new StringBuilder();
        for (int index = 0; index < 4 && index < parts.length; index++) {
            String part = parts[index];
            if (part != null)
                try {
                    part = part.trim();
                    int v = Integer.parseInt(part);
                    if (v < 0)
                        v = -v;
                    if (v > 255)
                        v = 255;
                    if (index == 0)
                        if (v > 127)
                            v = 127;
                    out.append(".").append(v);
                } catch (NumberFormatException ignored) {
                }
        }
        return out.length() < 1 ? "1" : out.substring(1);
    }

}
