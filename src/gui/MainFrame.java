/*
 * MainFrame.java
 *
 * Created on January 1, 2006, 7:36 PM
 *
 * Logic Tree: A logic gate simulation program
 * Copyright (c) 2009 Nick Vrvilo
 * http://ouuuuch.phoenixteam.org/
 * 
 * This program is distributed under the GNU General Public License.
 * For full deltails please see LicenseInfo.txt included with this source code.
 *
 */

package gui;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import java.util.ArrayList;
import java.io.*;

import boolparser.*;
import booltree.*;

/** The primary JFrame for this program
 * @author Nick Vrvilo
 */
public class MainFrame extends JFrame implements ActionListener {
  
  /** Pointer to the content pane */
  private Container cp;
  
  /** The JMenuBar for this JFrame */
  private JMenuBar myMenuBar;
  
  /** Constants for use with ActionCommands */
  private final char QUIT = 'Q', NEW_TREE = 'N', ABOUT = 'A', IMAGE = 'I', TRUTH_TABLE = 'T',
      VAR = 'V', ANIMATE = 'a', DOC = 'D', RESET = 'R', OPTIONS = 'O';
  
  /** The "status bar" for this JFrame */
  private JLabel statusBar;
  
  /** Reference to the main "center component" contained in the content pane */
  private JComponent centerComponent;
  
  /** Reference to the AnimationCanvas (or NULL if none) */
  private AnimationCanvas canvas;
  
  /** Creates a new instance of MainFrame */
  public MainFrame() {
    super("Logic Tree");
    this.setBounds(200,200,600,500);
    this.setDefaultCloseOperation(this.EXIT_ON_CLOSE);
    cp = getContentPane(); // Default layout mananger is BorderLayout
    buildMenu();
    createLoadScreen();
    this.setVisible(true);
  }
  
  /** Creates the center panel displayed at program startup */
  private void createLoadScreen() {
    // Add logo
    ImageIcon logoImage = new ImageIcon(MainFrame.class.getResource("resource/TreeLogo.gif"));
    JLabel logoLabel = new JLabel(logoImage);
    logoLabel.setBorder(BorderFactory.createLoweredBevelBorder());
    cp.add(logoLabel,BorderLayout.CENTER);
    centerComponent = logoLabel;
    // Add status bar
    statusBar = new JLabel("  Done");
    statusBar.setBorder(BorderFactory.createEtchedBorder());
    cp.add(statusBar,BorderLayout.SOUTH);
  }
  
  /** Creates the MenuBar for this window */
  private void buildMenu() {
    //== Menu Bar ==//
    myMenuBar = new JMenuBar();
    this.setJMenuBar(myMenuBar);
    //-- File Menu --//
    JMenu fileMenu = new JMenu("File");
    fileMenu.setMnemonic(KeyEvent.VK_F);
    myMenuBar.add(fileMenu);
    // New Logic Tree
    JMenuItem newTreeButton = new JMenuItem("New Logic Tree",KeyEvent.VK_N);
    newTreeButton.setActionCommand(String.valueOf(NEW_TREE));
    newTreeButton.addActionListener(this);
    newTreeButton.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N,InputEvent.CTRL_DOWN_MASK));
    fileMenu.add(newTreeButton);
    // Image
    JMenuItem image = new JMenuItem("Export Image",KeyEvent.VK_E);
    image.setActionCommand(String.valueOf(IMAGE));
    image.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S,InputEvent.CTRL_DOWN_MASK));
    image.addActionListener(this);
    fileMenu.add(image);
    // HTML
    JMenuItem html = new JMenuItem("Show Truth Table",KeyEvent.VK_T);
    html.setActionCommand(String.valueOf(TRUTH_TABLE));
    html.addActionListener(this);
    html.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_T,InputEvent.CTRL_DOWN_MASK));
    fileMenu.add(html);
    // HTML
    JMenuItem options = new JMenuItem("Color Options",KeyEvent.VK_O);
    options.setActionCommand(String.valueOf(OPTIONS));
    options.addActionListener(this);
    options.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O,InputEvent.CTRL_DOWN_MASK));
    fileMenu.add(options);
    // Quit
    JMenuItem quitButton = new JMenuItem("Quit",KeyEvent.VK_Q);
    quitButton.setActionCommand(String.valueOf(QUIT));
    quitButton.addActionListener(this);
    quitButton.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q,InputEvent.CTRL_DOWN_MASK));
    fileMenu.add(quitButton);  
    //-- Action Menu--//
    JMenu actionMenu = new JMenu("Tree Actions");
    actionMenu.setMnemonic(KeyEvent.VK_A);
    // Animate
    JMenuItem animate = new JMenuItem("Animate Tree",KeyEvent.VK_A);
    animate.setActionCommand(String.valueOf(ANIMATE));
    animate.addActionListener(this);
    animate.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F2,0));
    actionMenu.add(animate);
    // Reset
    JMenuItem reset = new JMenuItem("Reset Tree",KeyEvent.VK_R);
    reset.setActionCommand(String.valueOf(RESET));
    reset.addActionListener(this);
    reset.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F3,0));
    actionMenu.add(reset);
    // Edit Variables
    JMenuItem varEdit = new JMenuItem("Edit Variables",KeyEvent.VK_V);
    varEdit.setActionCommand(String.valueOf(VAR));
    varEdit.addActionListener(this);
    varEdit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F5,0));
    actionMenu.add(varEdit);
    myMenuBar.add(actionMenu);
    //-- Help Menu --//
    JMenu helpMenu = new JMenu("Help");
    helpMenu.setMnemonic(KeyEvent.VK_H);
    // Documentation
    JMenuItem doc = new JMenuItem("Show Documentation",KeyEvent.VK_D);
    doc.setActionCommand(String.valueOf(DOC));
    doc.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F1,0));
    doc.addActionListener(this);
    helpMenu.add(doc);
    // About
    JMenuItem about = new JMenuItem("About This Program",KeyEvent.VK_A);
    about.setActionCommand(String.valueOf(ABOUT));
    about.addActionListener(this);
    helpMenu.add(about);
    myMenuBar.add(helpMenu); // setHelpMenu() method not yet implemented?
  }
  
  /** Reads in input from the user and attempts to build a logic tree from the parsed data */
  private void getTreeExpression() {
    boolean error;
    String expr = BoolExpTree.getExpression();
    statusBar.setText("  Receiving Expresion Input");
    do {
      error = false;
      expr = (String)JOptionPane.showInputDialog(this,"Please input your boolean expression:",
          "New Expression Tree",JOptionPane.PLAIN_MESSAGE,(Icon)null,null,(Object)expr);
      if (expr == null || expr.length() == 0) { // No input
        statusBar.setText("  Input Cancelled");
      }
      else {
        try {
          // Clear the current animation pane (or splash screen)
          cp.remove(centerComponent);
          // Attempt to parse the user input
          BoolExpTree.initTree(expr);
          // Create a new AnimationCanvas packed inside a JScrollPanel in case of large display
          canvas = new AnimationCanvas();
          JPanel backgroundPanel = new JPanel(); // Allows for proper display of small canvases
          backgroundPanel.setOpaque(true);
          backgroundPanel.setBackground(Const.CANVAS_COLOR);
          backgroundPanel.add(canvas,BorderLayout.CENTER);
          JScrollPane scroller = new JScrollPane(backgroundPanel);
          cp.add(scroller);
          centerComponent = scroller;
          statusBar.setText("  Success!");
        }
        catch (OutOfMemoryError e) {
          statusBar.setText("  Not Enough Memory");
          JOptionPane.showMessageDialog(this,"Insufficient memory to compute this expression","Error",JOptionPane.ERROR_MESSAGE);
        }
        catch (InvalidExpressionException e) {
          statusBar.setText("  Parse Error");
          JOptionPane.showMessageDialog(this,"Parse Error:\n"+e.getMessage(),"Error",JOptionPane.ERROR_MESSAGE);
          error = true;
        }
        catch (InvalidTreeException e) {
          statusBar.setText("  Parse Error");
          JOptionPane.showMessageDialog(this,"Parse Error:\n"+e.getMessage(),"Error",JOptionPane.ERROR_MESSAGE);
          error = true;
        }
      }
    } while (error);
    // I tried just revalidating the canvas, but it still wouldn't display
    // until the window was resized, so I did this instead so it'd show up
    this.setVisible(true);
  }
  
  /** Handles all the menu actions */
  public void actionPerformed(ActionEvent e) {
    // All ActionCommand values are a single character, so I'm using a case statement
    switch (e.getActionCommand().charAt(0)) {
      // Create a new logic tree
      case NEW_TREE:
        getTreeExpression();
        break;
      // Export a PNG file of the current canvas
      case IMAGE:
        if (canvas != null) {
          JFileChooser fileChooser = new JFileChooser();
          fileChooser.showSaveDialog(this);
          if (!canvas.exportImage(fileChooser.getSelectedFile())) {
            JOptionPane.showMessageDialog(this,"Error exporting image!","Error",JOptionPane.ERROR_MESSAGE);
          }
        }
        else {
          JOptionPane.showMessageDialog(this,"ERROR: No tree found","Error",JOptionPane.ERROR_MESSAGE);
        }
        break;
      // Display the truth table for the tree
      case TRUTH_TABLE:
        if (canvas != null) {
          try {
            File htmlFile = new File("truthTable.html");
            FileOutputStream fileStream = new FileOutputStream(htmlFile);          
            BoolExpTree.generateTruthTableHTML(fileStream);
            fileStream.close();
            //BrowserControl.displayURL("file://"+htmlFile.getAbsolutePath());
            BrowserControl.displayURL(htmlFile.toURL().toString());
          }
          catch (IOException ie) {
            JOptionPane.showMessageDialog(this,"ERROR: Problem creating HTML file","Error",JOptionPane.ERROR_MESSAGE);
            System.err.println("Error writing HTML file:\n"+ie);
          }
        }
        else {
          JOptionPane.showMessageDialog(this,"ERROR: No tree found","Error",JOptionPane.ERROR_MESSAGE);
        }
        break;
      // Display the editor window for the current variable values
      case VAR:
        if (canvas != null) {
          canvas.resetCanvas();
          new VarValueEditor(this,canvas);
        }
        else {
          JOptionPane.showMessageDialog(this,"ERROR: No tree found","Error",JOptionPane.ERROR_MESSAGE);
        }
        break;
      // Display the "About" dialog
      case ABOUT:
        new AboutFrame(this);
        statusBar.setText("About Dialog");
        break;
      // Begin the animation of the current logic tree
      case ANIMATE:
        if (canvas != null) {
          canvas.resetCanvas();
          canvas.animate();
        }
        else {
          JOptionPane.showMessageDialog(this,"ERROR: No tree found","Error",JOptionPane.ERROR_MESSAGE);
        }
        break;
      // Reset the animation display
      case RESET:
        if (canvas != null) {
          canvas.resetCanvas();
        }
        else {
          JOptionPane.showMessageDialog(this,"ERROR: No tree found","Error",JOptionPane.ERROR_MESSAGE);
        }
        break;
      // Display the documentation file for this program
      case DOC:
        try {
          File docFile = new File("docs/documentation.html");
          if (docFile.exists()) {
            BrowserControl.displayURL(docFile.toURL().toString());
          }
          else {
            JOptionPane.showMessageDialog(this,"ERROR: Problem retrieving documentation file","Error",JOptionPane.ERROR_MESSAGE);
          }
        }
        catch (IOException ie) {
          JOptionPane.showMessageDialog(this,"ERROR: Problem creating HTML file","Error",JOptionPane.ERROR_MESSAGE);
          System.err.println("Error writing HTML file:\n"+ie);
        }
        break;
      // Display options dialog
      case OPTIONS:
        if (canvas != null) {
          JOptionPane.showMessageDialog(this,"Sorry, but color values must be set before\nthe expression tree is created!",
              "Error",JOptionPane.ERROR_MESSAGE);
        }
        else {
          new OptionsFrame(this);
        }
        break;
      // Exit the program
      case QUIT:
        this.setVisible(false);
        System.exit(0);
        break;
      default:
        JOptionPane.showMessageDialog(this,"ERROR: Unknown Command Executed","Error",JOptionPane.ERROR_MESSAGE);
    }
  }
  
}
