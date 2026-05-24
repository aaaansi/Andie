package cosc202.andie;

import java.util.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*;

/**
 * <p>
 * Actions provided by the File menu.
 * </p>
 * 
 * <p>
 * The File menu is very common across applications,
 * and there are several items that the user will expect to find here.
 * Opening and saving files is an obvious one, but also exiting the program.
 * </p>
 * 
 * <p>
 * <a href="https://creativecommons.org/licenses/by-nc-sa/4.0/">CC BY-NC-SA
 * 4.0</a>
 * </p>
 * 
 * @author Steven Mills
 * @version 1.0
 */
public class FileActions {

    /** A list of actions for the File menu. */
    protected ArrayList<Action> actions;

    /**
     * <p>
     * Create a set of File menu actions.
     * </p>
     */
    public FileActions() {
        actions = new ArrayList<Action>();
        actions.add(new FileOpenAction("Open", null, "Open a file", Integer.valueOf(KeyEvent.VK_O)));
        actions.add(new FileSaveAction("Save", null, "Save the file", Integer.valueOf(KeyEvent.VK_S)));
        actions.add(new FileSaveAsAction("Save As", null, "Save a copy", Integer.valueOf(KeyEvent.VK_A)));
        actions.add(new FileExitAction("Exit", null, "Exit the program", Integer.valueOf(0)));
    }

    /**
     * <p>
     * Create a menu containing the list of File actions
     * and assign keyboard shortcuts to each file action.
     * </p>
     * 
     * @return The File menu UI element.
     */
    public JMenu createMenu() {
        JMenu fileMenu = new JMenu("File");

        actions.get(0).putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_O, KeyEvent.CTRL_DOWN_MASK));
        actions.get(1).putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_S, KeyEvent.CTRL_DOWN_MASK));
        actions.get(2).putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_A, KeyEvent.CTRL_DOWN_MASK));
        actions.get(3).putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("ESCAPE"));

        for (Action action : actions) {
            fileMenu.add(new JMenuItem(action));
        }

        return fileMenu;
    }

    /**
     * Create a save button for the tool bar
     * 
     * @return A button whichs calls the file save action
     * @throws IOException
     */
    public JButton createSaveButton() throws IOException {
        ImageIcon saveIcon = new ImageIcon(ImageIO.read(FileActions.class.getClassLoader().getResource("save.png")));
        JButton saveButton = new JButton(saveIcon);
        saveButton.addActionListener(new FileSaveAction());

        return saveButton;
    }

    /**
     * <p>
     * Action to open an image from file.
     * </p>
     * 
     * @see EditableImage#open(String)
     */
    public class FileOpenAction extends ImageAction {

        /**
         * <p>
         * Create a new file-open action.
         * </p>
         * 
         * @param name     The name of the action (ignored if null).
         * @param icon     An icon to use to represent the action (ignored if null).
         * @param desc     A brief description of the action (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut (ignored if null).
         */
        FileOpenAction(String name, ImageIcon icon, String desc, Integer mnemonic) {
            super(name, icon, desc, mnemonic);
        }

        /**
         * <p>
         * Callback for when the file-open action is triggered.
         * </p>
         * 
         * <p>
         * This method is called whenever the FileOpenAction is triggered.
         * It prompts the user to select a file and opens it as an image.
         * </p>
         * 
         * @param e The event triggering this callback.
         */
        public void actionPerformed(ActionEvent e) {
            // checks if image is saved before opening image
            target.deselectMouse();
            target.repaint();
            boolean openImage = false;
            if (EditableImage.isSaved()) {
                openImage = true;
            } else {
                int dialogButton = JOptionPane.YES_NO_OPTION;
                int dialogResult = JOptionPane.showConfirmDialog(null,
                        "Are you sure you want to open a new image without saving?", "Warning", dialogButton);
                if (dialogResult == JOptionPane.YES_OPTION) {
                    openImage = true;
                }
            }

            if (openImage) {
                JFileChooser fileChooser = new JFileChooser();
                int result = fileChooser.showOpenDialog(target);

                if (result == JFileChooser.APPROVE_OPTION) {
                    try {
                        String imageFilepath = fileChooser.getSelectedFile().getCanonicalPath();
                        // checks if file is an image
                        if (!(imageFilepath.substring(imageFilepath.length() - 4).equals(".jpg")
                                || imageFilepath.substring(imageFilepath.length() - 4).equals(".png")
                                || imageFilepath.substring(imageFilepath.length() - 5).equals(".jpeg")
                                || imageFilepath.substring(imageFilepath.length() - 4).equals(".JPG")
                                || imageFilepath.substring(imageFilepath.length() - 4).equals(".gif"))) {
                            JFrame exceptionFrame = new JFrame();
                            JOptionPane.showMessageDialog(exceptionFrame, "Incorrect file type");
                        } else {
                            // checks if the file exists
                            try {
                                target.getImage();
                                target.getImage().open(imageFilepath);
                            } catch (Exception ex) {
                                JFrame exceptionFrame = new JFrame();
                                JOptionPane.showMessageDialog(exceptionFrame, "Can't find that file");
                            }
                        }
                        // if for any other reason the file can't open (if the file is corrupt)
                    } catch (Exception ex) {
                        JFrame exceptionFrame = new JFrame();
                        JOptionPane.showMessageDialog(exceptionFrame, "File didn't work");
                        // System.exit(1);

                    }
                }

                target.repaint();
                target.getParent().revalidate();
            }
        }

    }

    /**
     * <p>
     * Action to save an image to its current file location.
     * </p>
     * 
     * @see EditableImage#save()
     */
    public class FileSaveAction extends ImageAction {

        /**
         * <p>
         * Create a new file-save action.
         * </p>
         * 
         * @param name     The name of the action (ignored if null).
         * @param icon     An icon to use to represent the action (ignored if null).
         * @param desc     A brief description of the action (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut (ignored if null).
         */
        FileSaveAction(String name, ImageIcon icon, String desc, Integer mnemonic) {
            super(name, icon, desc, mnemonic);
        }

        /**
         * Constructor which doesn't require parameters for use in the toolbar
         * 
         */
        FileSaveAction() {
        }

        /**
         * <p>
         * Callback for when the file-save action is triggered.
         * </p>
         * 
         * <p>
         * This method is called whenever the FileSaveAction is triggered.
         * It saves the image to its original filepath.
         * </p>
         * 
         * @param e The event triggering this callback.
         */
        public void actionPerformed(ActionEvent e) {
            target.deselectMouse();
            target.repaint();
            try {
                target.getImage().save();
            } catch (Exception ex) {
                System.exit(1);
            }
        }

    }

    /**
     * <p>
     * Action to save an image to a new file location.
     * </p>
     * 
     * @see EditableImage#saveAs(String)
     */
    public class FileSaveAsAction extends ImageAction {

        /**
         * <p>
         * Create a new file-save-as action.
         * </p>
         * 
         * @param name     The name of the action (ignored if null).
         * @param icon     An icon to use to represent the action (ignored if null).
         * @param desc     A brief description of the action (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut (ignored if null).
         */
        FileSaveAsAction(String name, ImageIcon icon, String desc, Integer mnemonic) {
            super(name, icon, desc, mnemonic);
        }

        /**
         * <p>
         * Callback for when the file-save-as action is triggered.
         * </p>
         * 
         * <p>
         * This method is called whenever the FileSaveAsAction is triggered.
         * It prompts the user to select a file and saves the image to it.
         * </p>
         * 
         * @param e The event triggering this callback.
         */
        public void actionPerformed(ActionEvent e) {
            target.deselectMouse();
            target.repaint();
            JFileChooser fileChooser = new JFileChooser();
            int result = fileChooser.showSaveDialog(target);

            if (result == JFileChooser.APPROVE_OPTION) {
                try {
                    String imageFilepath = fileChooser.getSelectedFile().getCanonicalPath();
                    target.getImage().saveAs(imageFilepath);
                } catch (Exception ex) {
                    System.exit(1);
                }
            }
        }

    }

    /**
     * <p>
     * Action to quit the ANDIE application.
     * </p>
     */
    public class FileExitAction extends AbstractAction {

        /**
         * <p>
         * Create a new file-exit action.
         * </p>
         * 
         * @param name     The name of the action (ignored if null).
         * @param icon     An icon to use to represent the action (ignored if null).
         * @param desc     A brief description of the action (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut (ignored if null).
         */
        FileExitAction(String name, ImageIcon icon, String desc, Integer mnemonic) {
            super(name, icon);
            putValue(SHORT_DESCRIPTION, desc);
            putValue(MNEMONIC_KEY, mnemonic);
        }

        /**
         * <p>
         * Callback for when the file-exit action is triggered.
         * </p>
         * 
         * <p>
         * This method is called whenever the FileExitAction is triggered.
         * It quits the program.
         * </p>
         * 
         * @param e The event triggering this callback.
         */
        public void actionPerformed(ActionEvent e) {
            // will check if image is saved before exiting
            if (EditableImage.isSaved()) {
                System.exit(0);
            } else {
                int dialogButton = JOptionPane.YES_NO_OPTION;
                int dialogResult = JOptionPane.showConfirmDialog(null, "Are you sure you want to exit without saving?",
                        "Warning", dialogButton);
                if (dialogResult == JOptionPane.YES_OPTION) {
                    System.exit(0);
                }
            }
        }

    }

}
