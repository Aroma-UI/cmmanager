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
package org.crossmobile.gui.actives;

import com.panayotis.hrgui.HiResPasswordField;
import org.crossmobile.gui.elements.Theme;

import javax.swing.*;

import static org.crossmobile.gui.actives.ActiveTextField.updateTheme;

public class ActivePasswordField extends HiResPasswordField implements ThemeChanged {

    private final TooltipManager ttm = new TooltipManager(this);

    public ActivePasswordField() {
        this("");
    }

    @SuppressWarnings("OverridableMethodCallInConstructor")
    public ActivePasswordField(String text) {
        super(text);
        setOpaque(true);
        ThemeNotifier.register(this);
        themeChanged(Theme.current() == Theme.dark());
    }

    @Override
    public void setToolTipText(String text) {
        ttm.setToolTipText(text);
    }

    public void setToolTip(JToolTip toolTip) {
        ttm.setToolTip(toolTip);
    }

    @Override
    public void themeChanged(boolean dark) {
        updateTheme(this, dark);
    }
}
