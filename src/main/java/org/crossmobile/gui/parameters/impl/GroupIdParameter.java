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

import org.crossmobile.gui.elements.IdDocumentFilter;
import org.crossmobile.gui.parameters.FreeTextParameter;
import org.crossmobile.utils.ParamList;

import static org.crossmobile.utils.ParamsCommon.GROUP_ID;

public class GroupIdParameter extends FreeTextParameter {

    public static final String DEFAULT_GROUP_ID = "my.company";

    @SuppressWarnings("OverridableMethodCallInConstructor")
    public GroupIdParameter(ParamList list) {
        super(list, GROUP_ID.tag());
        setValue(getValue());
        setFilter(new IdDocumentFilter());
        setTooltip(IdDocumentFilter.TOOLTIP);
    }

    @Override
    public String getVisualTag() {
        return "Organization ID";
    }

    @Override
    public String getValue() {
        String val = super.getValue();
        if (val == null)
            return DEFAULT_GROUP_ID;
        if (val.startsWith("."))
            val = val.substring(1);
        if (val.endsWith("."))
            val = val.substring(0, val.length() - 1);
        return val.toLowerCase();
    }

}
