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
package org.crossmobile.gui.android;

import org.crossmobile.prefs.Prefs;
import org.crossmobile.utils.Commander;

public class InstallerThread extends Thread {

    private static final String[] yesSignatures = {"N/y", "y/N"};

    private final int yesIndices[] = new int[yesSignatures.length];
    private final InstallerFrame installer;

    private Commander cmdAll;
    private Commander current;

    public InstallerThread(InstallerFrame installer) {
        super("Android SDK License Agreement thread");
        this.installer = installer;
        cmdAll = new Commander(Prefs.getAndroidManagerLocation(), "--sdk_root=" + Prefs.getAndroidSDKLocation(), "--licenses");
        cmdAll.appendEnvironmentalParameter("JAVA_HOME", Prefs.getJDKLocation());
    }

    @Override
    public void run() {
        runCmd(current = cmdAll);
        if (!installer.isCancelled())
            installer.finish();
    }

    private void runCmd(Commander cmd) {
        if (cmd != null) {
            cmd.setCharOutListener(this::incomingOutChar);
            cmd.setCharErrListener(this::incomingErrChar);
            cmd.exec();
            cmd.waitFor();
        }
    }

    public void sendYes() {
        current.sendLine("y");
    }

    public void sendCancel() {
        cmdAll = null;
        if (current != null)
            current.kill();
    }

    private void incomingOutChar(Character c) {
        installer.addChar(c);
        for (int i = 0; i < yesSignatures.length; i++) {
            if (yesSignatures[i].charAt(yesIndices[i]) == c)
                yesIndices[i]++;
            else
                yesIndices[i] = 0;
            if (yesIndices[i] >= yesSignatures[i].length()) {
                for (int ci = 0; ci < yesSignatures.length; ci++)
                    yesIndices[ci] = 0;
                installer.enableYes();
                break;
            }
        }
    }

    private void incomingErrChar(char c) {
        installer.addChar(c);
    }
}
