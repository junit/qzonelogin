package org.chinasb.example.qzonelogin.component;

import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashMap;

import javax.swing.Action;
import javax.swing.JComponent;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JSeparator;
import javax.swing.JTextArea;
import javax.swing.text.DefaultEditorKit;
import javax.swing.text.JTextComponent;
import javax.swing.text.TextAction;

public class JPopupTextArea extends JTextArea {

    private HashMap actions;

    final static String COPY = "Copy";
    final static String CUT = "Cut";
    final static String PASTE = "Paste";
    final static String SELECTALL = "Select All";
    final static String CLEARAll = "Clear Text";
    
    public JPopupTextArea() {
        addPopupMenu();
    }

    private void addPopupMenu() {
        final JPopupMenu menu = new JPopupMenu();
        final JMenuItem copyItem = new JMenuItem();
        copyItem.setAction(getActionMap().get(DefaultEditorKit.copyAction));
        copyItem.setText(COPY);

        final JMenuItem cutItem = new JMenuItem();
        cutItem.setAction(getActionMap().get(DefaultEditorKit.cutAction));
        cutItem.setText(CUT);

        final JMenuItem pasteItem = new JMenuItem(PASTE);
        pasteItem.setAction(getActionMap().get(DefaultEditorKit.pasteAction));
        pasteItem.setText(PASTE);
        
        final JMenuItem selectAllItem = new JMenuItem(SELECTALL);
        selectAllItem.setAction(getActionMap().get(DefaultEditorKit.selectAllAction));
        selectAllItem.setText(SELECTALL);
        
        final JMenuItem clearAllItem = new JMenuItem();
        clearAllItem.setAction(new ClearAllAction());
        clearAllItem.setText(CLEARAll);
        
        menu.add(copyItem);
        menu.add(cutItem);
        menu.add(pasteItem);
        menu.add(new JSeparator());
        menu.add(selectAllItem);
        menu.add(new JSeparator());
        menu.add(clearAllItem);

        add(menu);
        addMouseListener(new PopupTriggerMouseListener(menu, this));
        //no need to hold the references in the map,
        // we have used the ones we need.
    }

    private Action getActionByName(String name, String description) {
        Action a = (Action)(actions.get(name));
        a.putValue(Action.NAME, description);
        return a;
    }

    private void createActionTable() {
        actions = new HashMap();
        Action[] actionsArray = getActions();
        for (int i = 0; i < actionsArray.length; i++) {
            Action a = actionsArray[i];
            actions.put(a.getValue(Action.NAME), a);
        }
    }

    public static class PopupTriggerMouseListener extends MouseAdapter {
        private JPopupMenu popup;
        private JComponent component;

        public PopupTriggerMouseListener(JPopupMenu popup, JComponent component) {
            this.popup = popup;
            this.component = component;
        }

        //some systems trigger popup on mouse press, others on mouse release, we want to cater for both
        private void showMenuIfPopupTrigger(MouseEvent e) {
            if (e.isPopupTrigger()) {
                popup.show(component, e.getX() + 3, e.getY() + 3);
            }
        }

        //according to the javadocs on isPopupTrigger, checking for popup trigger on mousePressed and mouseReleased 
        //should be all  that is required
        //public void mouseClicked(MouseEvent e)  
        //{
        //    showMenuIfPopupTrigger(e);
        //}

        public void mousePressed(MouseEvent e) {
            showMenuIfPopupTrigger(e);
        }

        public void mouseReleased(MouseEvent e) {
            showMenuIfPopupTrigger(e);
        }

    }
    
    static class ClearAllAction extends TextAction {
        ClearAllAction() {
            super(CLEARAll);
        }

        public void actionPerformed(ActionEvent e) {
            JTextComponent target = getTextComponent(e);
            if (target != null) {
                if(target instanceof JTextArea) {
                    target.setText("");
                }
            }
        }

    }
}
