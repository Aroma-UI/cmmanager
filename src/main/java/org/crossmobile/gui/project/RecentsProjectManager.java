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

import org.crossmobile.prefs.Prefs;
import org.crossmobile.utils.ProjectException;

import java.util.ArrayList;
import java.util.List;

public class RecentsProjectManager {

    private static final List<ProjectInfo> PROJECTS = new ArrayList<>();

    static {
        int counter = 1;
        String path;
        while ((path = Prefs.getProject(counter)) != null) {
            counter++;
            try {
                ProjectInfo project = ProjectInfo.load(path);
                if (!PROJECTS.contains(project))
                    PROJECTS.add(project);
            } catch (ProjectException ex) {
            }
        }
    }

    public static void addProject(ProjectInfo project, boolean bringToFront) {
        if (bringToFront) {
            PROJECTS.remove(project);
            PROJECTS.add(0, project);
        } else if (!PROJECTS.contains(project))
            PROJECTS.add(0, project);
        updatePrefs();
    }

    public static List<ProjectInfo> getProjects() {
        return PROJECTS;
    }

    public static void deleteProject(ProjectInfo project) {
        PROJECTS.remove(project);
        updatePrefs();
    }

    public static void clearProjects() {
        PROJECTS.clear();
        updatePrefs();
    }

    private static void updatePrefs() {
        for (int i = PROJECTS.size() - 1; i >= 0; i--)
            if (!PROJECTS.get(i).isValid())
                PROJECTS.remove(i);

        int counter = 1;
        while (Prefs.removeProject(counter))
            counter++;

        for (int i = 0; i < PROJECTS.size(); i++)
            Prefs.storeProject(i + 1, PROJECTS.get(i).getPath().getAbsolutePath());

        Prefs.removeProject(PROJECTS.size() + 1);
    }
}
