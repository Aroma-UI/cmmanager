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

import org.crossmobile.gui.parameters.HiddenParameter;
import org.crossmobile.utils.ParamList;

import static org.crossmobile.utils.ParamsCommon.TARGET_NUMERIC;

public class AndroidTargetNumericParameter extends HiddenParameter {

    public static final String DEFAULT = "21";
    public static final String VERSION = "5.0.1";

    public AndroidTargetNumericParameter(ParamList list) {
        super(list, TARGET_NUMERIC.tag());
    }

    @Override
    public String getValue() {
        return DEFAULT;
    }
}
