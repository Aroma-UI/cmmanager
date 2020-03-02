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
package org.crossmobile.gui.project;

import com.panayotis.hrgui.HiResComponent;
import org.crossmobile.gui.actives.ActivePanel;
import org.crossmobile.gui.parameters.ProjectParameter;
import org.crossmobile.utils.UIUtils;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class PropertySheet {

    private final List<ProjectParameter> properties = new ArrayList<>();
    private final String name;
    private final GlobalParamListener listener;
    private ActivePanel bottomPanel;

    public PropertySheet(String name, GlobalParamListener listener) {
        this.name = name;
        this.listener = listener;
    }

    public void add(ProjectParameter pp) {
        if (properties.add(pp))
            listener.addParameter(pp);
    }

    public Iterable<ProjectParameter> getProperties() {
        return properties;
    }

    public String getName() {
        return name;
    }

    public void alignVisuals() {
        Collection<Component> comps = new ArrayList<>();
        for (ProjectParameter param : properties) {
            HiResComponent cmp = param.getIndentedComponent();
            if (cmp != null)
                comps.add(cmp.comp());
        }
        UIUtils.syncWidth(comps);
    }

    public void setBottomPanel(ActivePanel bottomPanel) {
        this.bottomPanel = bottomPanel;
    }

    public ActivePanel getBottomPanel() {
        return bottomPanel;
    }
}
