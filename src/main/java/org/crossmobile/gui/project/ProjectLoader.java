// (c) 2020 by Panayotis Katsaloulis
// SPDX-License-Identifier: AGPL-3.0-only

package org.crossmobile.gui.project;

import com.panayotis.appenh.EnhancerManager;
import org.crossmobile.gui.ProjectFrame;
import org.crossmobile.gui.RegisteredFrame;
import org.crossmobile.gui.WelcomeFrame;
import org.crossmobile.gui.utils.EventUtils;
import org.crossmobile.utils.Log;
import org.crossmobile.utils.ProjectException;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class ProjectLoader {

    private static final Map<String, ProjectFrame> ACTIVE_PROJECTS = new HashMap<>();

    private static synchronized ProjectFrame retrieveProject(File projPath) {
        return ACTIVE_PROJECTS.get(projPath.getAbsolutePath());
    }

    public static synchronized void registerProject(ProjectFrame projFrame, File projPath) {
        ACTIVE_PROJECTS.put(projPath.getAbsolutePath(), projFrame);
    }

    public static synchronized void unregisterProject(ProjectFrame projFrame) {
        String key = null;
        for (String path : ACTIVE_PROJECTS.keySet())
            if (ACTIVE_PROJECTS.get(path).equals(projFrame)) {
                key = path;
                break;
            }
        if (key != null)
            ACTIVE_PROJECTS.remove(key);
    }

    public static void showProject(final ProjectInfo pinfo, final WelcomeFrame frame) {
        {
            ProjectFrame retrievedFrame = retrieveProject(pinfo.getPath());
            if (retrievedFrame != null) {
                retrievedFrame.setVisible(true);
                RecentsProjectManager.addProject(pinfo, true);
                return;
            }
        }

        if (frame != null)
            frame.ProjectsL.setEnabled(false);
        final ProjectFrame projframe = new ProjectFrame(pinfo.getPath());
        projframe.setVisible(true);
        EventUtils.postAction(() -> {
            try {
                Project proj = new Project(pinfo);
                proj.save();
                projframe.initVisuals(proj);
                ProjectLoader.registerProject(projframe, proj.getPath());
                RecentsProjectManager.addProject(pinfo, true);
                if (frame != null)
                    frame.updateProjects(pinfo);
                proj.setApplicationNameListener((dirty, name) -> {
                    EnhancerManager.getDefault().setFrameSaveState(projframe, dirty);
                    projframe.setTitle(name);
                });
                if (frame != null) {
                    projframe.setCloseCallback(selected -> {
                        frame.updateProjects(pinfo);
                        if (frame.isVisible() || RegisteredFrame.count() <= 1)  // if welcome frame is already visible or if the closing frame is the last one
                            frame.setVisible(true); // bring to front
                    });
                    proj.setSaveCallback(selected -> {
                        pinfo.refresh(null);
                        frame.updateProjects(pinfo);
                    });
                }
            } catch (ProjectException ex) {
                Log.error("Error while loading project" + (ex.getMessage() == null ? "" : "\n" + ex.getMessage()), ex);
            } finally {
                if (frame != null)
                    frame.ProjectsL.setEnabled(true);
            }
        });
    }
}
