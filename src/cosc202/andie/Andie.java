package cosc202.andie;

import java.io.*;
import java.awt.*;
import javax.swing.*;

import cosc202.andie.Colours.ColourActions;
import cosc202.andie.Draw.AddTextAction;
import cosc202.andie.Draw.DrawLineActions;
import cosc202.andie.Draw.DrawShapeActions;
import cosc202.andie.Filters.FilterActions;
import cosc202.andie.Transformations.TransformationActions;
import cosc202.andie.ViewActions.ViewActions;

import javax.imageio.*;

/**
 * <p>
 * Main class for A Non-Destructive Image Editor (ANDIE).
 * </p>
 * 
 * <p>
 * This class is the entry point for the program.
 * It creates a Graphical User Interface (GUI) that provides access to various
 * image editing and processing operations.
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
public class Andie {

    // There mught be reasons as to why I shouldn't do this but I really don't know
    private static FileActions fileActions = new FileActions();
    private static EditActions editActions = new EditActions();
    private static ViewActions viewActions = new ViewActions();
    // private static ViewActions macrosActions = new MacrosActions();
    private static MacrosActions macrosActions = new MacrosActions();
    private static FilterActions filterActions = new FilterActions();
    private static ColourActions colourActions = new ColourActions();
    private static TransformationActions transformActions = new TransformationActions();
    private static AddTextAction textAction = new AddTextAction();
    private static DrawShapeActions drawShapeActions = new DrawShapeActions();
    private static DrawLineActions drawLineActions = new DrawLineActions();


    /**
     * <p>
     * Launches the main GUI for the ANDIE program.
     * </p>
     * 
     * <p>
     * This method sets up an interface consisting of an active image (an
     * {@code ImagePanel})
     * and various menus which can be used to trigger operations to load, save,
     * edit, etc.
     * These operations are implemented {@link ImageOperation}s and triggerd via
     * {@code ImageAction}s grouped by their general purpose into menus.
     * </p>
     * 
     * @see ImagePanel
     * @see ImageAction
     * @see ImageOperation
     * @see FileActions
     * @see EditActions
     * @see ViewActions
     * @see FilterActions
     * @see ColourActions
     * 
     * @throws Exception if something goes wrong.
     */
    private static void createAndShowGUI() throws Exception {
        // Set up the main GUI frame
        JFrame frame = new JFrame("ANDIE");

        java.net.URL iconUrl = Andie.class.getClassLoader().getResource("icon.png");
        if (iconUrl != null) {
            frame.setIconImage(ImageIO.read(iconUrl));
        }
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JToolBar toolbar = new JToolBar("test");
        toolbar.setFloatable(false);
        createJButtons(toolbar);

        frame.add(toolbar, BorderLayout.NORTH);

        // The main content area is an ImagePanel
        ImagePanel imagePanel = new ImagePanel();
        ImageAction.setTarget(imagePanel);
        JScrollPane scrollPane = new JScrollPane(imagePanel);
        frame.add(scrollPane, BorderLayout.CENTER);

        // Add in menus for various types of action the user may perform.
        JMenuBar menuBar = new JMenuBar();

        // File menus are pretty standard, so things that usually go in File menus go
        // here.
        menuBar.add(fileActions.createMenu());

        // Likewise Edit menus are very common, so should be clear what might go here.
        menuBar.add(editActions.createMenu());

        // View actions control how the image is displayed, but do not alter its actual
        // content
        menuBar.add(viewActions.createMenu());

        // Macros actions are for 'macros -record and play' includes start stop, save
        // and load
        // menuBar.add(macrosActions.createMenu());
        // Macros actions are for 'macros -record and play' includes start stop, save
        // and load
        menuBar.add(macrosActions.createMenu());

        // Filters apply a per-pixel operation to the image, generally based on a local
        // window
        menuBar.add(filterActions.createMenu());

        // Actions that affect the representation of colour in the image
        menuBar.add(colourActions.createMenu());

        menuBar.add(transformActions.createMenu());

        menuBar.add(textAction.createMenu());

        menuBar.add(drawShapeActions.createMenu());

        menuBar.add(drawLineActions.createMenu());

        frame.setJMenuBar(menuBar);
        frame.pack();
        frame.setVisible(true);

    }

    /**
     * Create the JButtons for the toolbar
     * 
     * @param toolbar The toolbar for quick access to common commands
     * @throws Exception
     */
    private static void createJButtons(JToolBar toolbar) throws Exception {
        toolbar.add(editActions.createUndoButton());
        toolbar.add(editActions.createRedoButton());

        toolbar.add(viewActions.createZoomOutButton());
        toolbar.add(viewActions.createZoomInButton());

        toolbar.add(fileActions.createSaveButton());
    }

    /**
     * <p>
     * Main entry point to the ANDIE program.
     * </p>
     * 
     * <p>
     * Creates and launches the main GUI in a separate thread.
     * As a result, this is essentially a wrapper around {@code createAndShowGUI()}.
     * </p>
     * 
     * @param args Command line arguments, not currently used
     * @throws Exception If something goes awry
     * @see #createAndShowGUI()
     */
    public static void main(String[] args) throws Exception {

        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                try {
                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                    createAndShowGUI();
                } catch (Exception ex) {
                    System.exit(1);
                }
            }
        });
    }
}