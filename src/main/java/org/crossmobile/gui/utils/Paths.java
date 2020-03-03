// (c) 2020 by Panayotis Katsaloulis
// SPDX-License-Identifier: AGPL-3.0-only

package org.crossmobile.gui.utils;

import org.crossmobile.utils.Commander;
import org.crossmobile.utils.Log;
import org.crossmobile.utils.SystemDependent;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.concurrent.atomic.AtomicReference;

import static org.crossmobile.utils.SystemDependent.Execs.MVN;
import static org.crossmobile.utils.SystemDependent.getHome;

public final class Paths {

    private static final String DEV_LIB_INSTALL2 = "../../resources/lib";   // location of libraries when debugging
    private static final String MVNPATH = "apache-maven" + File.separator + "bin";

    private static File APPFILE;
    private static final String MYLIBS;

    static {
        try {
            APPFILE = new File(Paths.class.getProtectionDomain().getCodeSource().getLocation().toURI());
        } catch (URISyntaxException ignore) {
            APPFILE = new File(Paths.class.getProtectionDomain().getCodeSource().getLocation().getFile());
        }
        if (APPFILE.isDirectory()) {
            MYLIBS = new File(APPFILE.getParent(), DEV_LIB_INSTALL2).getAbsolutePath();
        } else
            MYLIBS = getPath(APPFILE.getParentFile().getAbsoluteFile(), HomeReference.NO_OVERRIDE);
    }

    public static String getApplicationPath() {
        File dir = APPFILE.isFile() ? APPFILE.getParentFile() : APPFILE;
        String path = dir.getAbsolutePath();
        if (path.startsWith("/tmp/.mount_")) {
            // as an AppImage
            path = System.getenv("APPIMAGE");
            if (path != null)
                return path;
            Log.error("Application started as AppImage but the file location was not found.");
        }
        return path;
    }

    public static String getPath(File path, HomeReference overrideHome) {
        try {
            return getPath(path.getCanonicalPath(), overrideHome);
        } catch (IOException ex) {
            return getPath(path.getAbsolutePath(), overrideHome);
        }
    }

    public static String getPath(String path, HomeReference overrideHome) {
        path = path.replace('\\', '/');
        if (overrideHome != null && overrideHome != HomeReference.NO_OVERRIDE)
            if (overrideHome == HomeReference.PROP_TO_ABS) {
                if (path.startsWith("${user.home}"))
                    path = getHome() + path.substring("${user.home}".length());
            } else if (path.startsWith(getHome() + "/"))
                path = (overrideHome == HomeReference.PROPERTY_STYLE ? "${user.home}" : "~")
                        + ((getHome().length() + 1) < path.length() ? ("/" + path.substring(getHome().length() + 1)) : "");
        return path;
    }

    public static File getAbsolutePath(String path, String currentDir) {
        return getAbsolutePath(new File(path), currentDir == null ? null : new File(currentDir));
    }

    public static File getAbsolutePath(File path, File currentDir) {
        if (!path.isAbsolute()) {
            if (currentDir == null)
                currentDir = new File(System.getProperty("user.dir"));
            path = new File(currentDir, path.getPath());
        }
        try {
            return path.getCanonicalFile();
        } catch (IOException ex) {
            return path;
        }
    }

    public static String getPathSimple(File path) {
        if (path == null)
            return null;
        return path.getPath().replace('\\', '/');
    }

    public static String getMavenLocation() {
        return MYLIBS + File.separator + MVNPATH + File.separator + MVN.filename();
    }

    public static String getXRayPath() {
        File cmxray = new File(SystemDependent.getPluginsDir(), "cmxray.jar");
        return cmxray.exists() ? cmxray.getAbsolutePath() : null;
    }

    private Paths() {
    }

    public enum HomeReference {

        NO_OVERRIDE,
        PROPERTY_STYLE,
        PATH_STYLE,
        PROP_TO_ABS
    }
}
