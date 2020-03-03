// (c) 2020 by Panayotis Katsaloulis
// SPDX-License-Identifier: AGPL-3.0-only

package org.crossmobile.gui.actives;

import com.panayotis.hrgui.HiResButton;
import com.panayotis.hrgui.HiResEmptyBorder;
import org.crossmobile.gui.elements.Theme;

import javax.swing.*;
import javax.swing.plaf.basic.BasicButtonUI;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ActiveButton extends HiResButton {

    private final TooltipManager ttm = new TooltipManager(this);
    private boolean isRollover = false;

    {
        setUI(new BasicButtonUI());
        setOpaque(false);
        setBorder(new HiResEmptyBorder(8, 8, 8, 8));
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                isRollover = true;
                repaint();
            }

            @Override
            public void mouseExited(MouseEvent e) {
                isRollover = false;
                repaint();
            }
        });
    }

    @Override
    public void setToolTipText(String text) {
        ttm.setToolTipText(text);
    }

    public void setToolTip(JToolTip toolTip) {
        ttm.setToolTip(toolTip);
    }

    @Override
    public void paint(Graphics g) {
        Dimension d = getSize();
        boolean isPressed = getModel().isPressed();
        if (isEnabled() && (isPressed || isRollover)) {
            g.setColor(isPressed ? Theme.current().pressedButton : Theme.current().rolloverButton);
            g.fillRect(0, 0, d.width, d.height);
        }
        super.paint(g);
    }

    public void setIcon(String iconResource) {
        setIcon(new ActiveIcon(iconResource));
    }

    @Override
    public Color getForeground() {
        return Theme.current().text;
    }
}
