/*
 * AnimationCanvas.java
 *
 * Created on January 2, 2006, 7:34 PM
 *
 * Logic Tree: A logic gate simulation program
 * Copyright (c) 2009 Nick Vrvilo
 * https://github.com/DaoWen/logic-tree
 * 
 * This program is distributed under the GNU General Public License.
 * For full deltails please see LicenseInfo.txt included with this source code.
 *
 */

package gui;

import booltree.*;
import boolparser.BoolTypes;

import gui.tree.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.awt.image.BufferedImage;
import javax.swing.JComponent;
import java.awt.font.FontRenderContext;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.*;

import javax.imageio.ImageIO;
import java.io.*;

/** Component for displaying and animating the logical tree inputted by the user
 * @author Nick Vrvilo
 */
public class AnimationCanvas extends JComponent implements ActionListener, Runnable {
  
  /** Double-buffer image for the canvas */
  private BufferedImage buffer;
  
  /** Depth of the expression-tree */
  private int treeDepth;
  
  /** Width and Height of the canvas */
  private int myWidth, myHeight;
  
  /** Nested list for easy access to expression nodes on a specific level of the tree */
  private ArrayList<ArrayList<ExprNode>> treeLevels;
  
  /** Nested list for easy access to wires on a specific level of the tree */
  private ArrayList<ArrayList<Wire>> wireLevels;
  
  /** Stores all the display objects for the canvas */
  private HashMap<ExprNode,Sender> displayObjects;
  
  /** Maps the wires to their input nodes */
  private HashMap<Wire,ExprNode> wireValNodes;
  
  /** Stores the reference to the animation thread */
  private Thread animationThread;
  
  /** Stores a reference to the Output display object (won't go in displayObjects because it's not a sender) */
  private Output outputObject;
  
  /** Flag to show that the animation has finished running (true if no animation is running) */
  private boolean animationFinished;
  
  /** Creates a new instance of AnimationCanvas */
  public AnimationCanvas() {
    // set SWING stuff...
    setDoubleBuffered(false); // Well, it is, but I'm handling it...
    setOpaque(true);
    // Start configuring the display!    
    initDisplay();
    animationFinished = true; // There isn't one running right now
    setPreferredSize(new Dimension(myWidth,myHeight));
  }

  /** Recursive method for sorting the expression tree elements into their respective levels for easy access
   * @param current The node to place in the sorted collection
   * @param currentDepth The depth at which the current node is located in the tree
   */
  private void sortLevels(ExprNode current, int currentDepth, ArrayList<ExprNode> inputs, ArrayList<Integer> inputLevels) {
    if (current != null) {
      treeLevels.get(treeDepth-currentDepth).add(current);
      switch (current.getType()) {
        case VAR:
        case TRUE:
        case FALSE:
          inputs.add(current);
          inputLevels.add(treeDepth-currentDepth);
      }
      sortLevels(current.getLeft(),currentDepth+1,inputs,inputLevels);
      sortLevels(current.getRight(),currentDepth+1,inputs,inputLevels);
    }
  }
  
  /** Initializes the canvas and all related display elements for the expression tree
   * (This method is a <em>beast</em>)
   */
  private void initDisplay() {
    // Initializing some variables ...
    ExprNode root = BoolExpTree.getSimpleTree();
    treeDepth = root.getDepth();
    treeLevels = new ArrayList<ArrayList<ExprNode>>(treeDepth);
    wireLevels = new ArrayList<ArrayList<Wire>>(treeDepth-1);
    wireValNodes = new HashMap<Wire,ExprNode>();
    // Initialize treeLevels and wireLevels
    for (int i=0;i<treeDepth;i++) {
      treeLevels.add(new ArrayList<ExprNode>());
      wireLevels.add(new ArrayList<Wire>());
    }
    treeLevels.add(new ArrayList<ExprNode>()); // treeLevels has one more list than wireLevels
    ArrayList<ExprNode> inputs = new ArrayList<ExprNode>();
    ArrayList<Integer> inputLevels = new ArrayList<Integer>();
    sortLevels(root,0,inputs,inputLevels);
    // The x- and y-coordinates for positioning Input displays
    // (the gates just center themselves over their inputs so they're easier)
    int x=20,y=20+Const.INPUT_HEIGHT/2;
    // Stores all the display objects except the output
    displayObjects = new HashMap<ExprNode,Sender>();
    // Stores the output display object
    outputObject = null;
    //======================================//
    //==== Compute Variable Name Widths ====//
    //======================================//
    // Contains the strings and positions to write out as the variable input labels
    HashMap<Point,String> varNamePos = new HashMap<Point,String>();
    // Stores the computed width of each variable name for proper positioning
    HashMap<String,Integer> varNameWidths = new HashMap<String,Integer>();
    // Setting the font for writing the variable names to the canvas
    Font myFont = new Font("Serif",Font.PLAIN,14);
    // Calculating the variables' names' widths
    FontRenderContext frc = new FontRenderContext(null, true, false);    
    // 'longestName' stores the width (px) of the longest variable name,
    // and 'stringWidth' stores the current string's width
    int longestName = 0, stringWidth;
    for (String s : VarNode.getVarNames()) {
      stringWidth = (int) myFont.getStringBounds("$"+s,frc).getWidth();
      if (stringWidth > longestName) {
        longestName = stringWidth;
      }
      varNameWidths.put(s,stringWidth+Const.TXT_DX);
    }
    frc = null; // Just freeing up resources...
    // The variable name width is added to 'x' so there'll be room for writing out the variable names later
    x += longestName;
    //=============================================//
    //==== Position all Input display elements ====//
    //=============================================//
    for (int i=0;i<inputs.size();i++) {
      ExprNode node = inputs.get(i); // Current node
      int nodeLevel = inputLevels.get(i); // Tree-level of the current node
      // Calculate the x-position of the node based off its depth
      int tempx = x;
      if (nodeLevel > 0) {
        tempx += Const.GATE_WIDTH * 1.5;
      }
      if (nodeLevel > 1) {
        tempx += Const.GATE_WIDTH * 1.5 * (nodeLevel-1);
      }
      // Position the node on the canvas based on how it connects to its parent,
      // and if it's a variable input also position the variable name label
      switch (node.getParent().getType()) {
        case NOT:
          switch(node.getType()) {
            case VAR:
              varNamePos.put(new Point(tempx-varNameWidths.get(node.getName()),y+Const.GATE_HEIGHT/2+Const.TXT_DY),"$"+node.getName());
              displayObjects.put(node,new Input(tempx,y+Const.GATE_HEIGHT/2,node.getName()));
              break;
            case TRUE:
              displayObjects.put(node,new Input(tempx,y+Const.GATE_HEIGHT/2,true));
              break;
            case FALSE:
              displayObjects.put(node,new Input(tempx,y+Const.GATE_HEIGHT/2,false));
              break;
            default:
              System.err.println("Incorrect input type found");
          }
          y += Const.GATE_HEIGHT * 1.5; // padding between inputs
          break;
        default:
          // If this node is a left-hand input
          if (node.getParent().getLeft() == node) {
            switch(node.getType()) {
              case VAR:
                varNamePos.put(new Point(tempx-varNameWidths.get(node.getName()),y+Const.TXT_DY),"$"+node.getName());
                displayObjects.put(node,new Input(tempx,y,node.getName()));
                break;
              case TRUE:
                displayObjects.put(node,new Input(tempx,y, true));
                break;
              case FALSE:
                displayObjects.put(node,new Input(tempx,y, false));
                break;
              default:
                System.err.println("Incorrect input type found");
            }
            y += Const.GATE_HEIGHT; // padding between inputs
          }
          // If this node is a right-hand input
          else {
            switch(node.getType()) {
              case VAR:
                varNamePos.put(new Point(tempx-varNameWidths.get(node.getName()),y+Const.TXT_DY),"$"+node.getName());
                displayObjects.put(node,new Input(tempx,y,node.getName()));
                break;
              case TRUE:
                displayObjects.put(node,new Input(tempx,y,true));
                break;
              case FALSE:
                displayObjects.put(node,new Input(tempx,y,false));
                break;
              default:
                System.err.println("Incorrect input type found");
            }
            y += Const.GATE_HEIGHT * 1.5; // padding between inputs
          }
      }
    }
    // Increments the x-position to the 2nd-from-lowest
    x += Const.GATE_WIDTH/2 + Const.INPUT_WIDTH;
    // The height of the canvas has been determined based off the input positioning
    myHeight = y;
    //=============================================//
    //==== Position all other display elements ====//
    //=============================================//
    // Variables to store pointers to this node's display object and its childrens'
    GateAnimator disp = null;
    Sender child = null;
    Wire w = null;
    // Loop through all the rest of the tree levels
    for (int i=1;i<treeLevels.size();i++) {
      for (ExprNode node : treeLevels.get(i)) {
        switch (node.getType()) {
          case VAR:
          case TRUE:
          case FALSE:
            // They've already been calculated so we don't have to do anything
            break;
          case OUTPUT:
              child = displayObjects.get(node.getLeft());
              y = (int)child.getOutput().getY()-Const.INPUT_HEIGHT/2;
              outputObject = new Output(x,y);
              // Add the input wire for the Output
              w = new Wire(child,outputObject,true);
              wireLevels.get(i-1).add(w);
              wireValNodes.put(w,node.getLeft());
            break;
          default:
            if (node.getType() == BoolTypes.NOT) {
              child = displayObjects.get(node.getLeft());
              y = (int)child.getOutput().getY()-Const.GATE_HEIGHT/2;
              disp = new NotAnimator(x,y);
              displayObjects.put(node,disp);
              // Add the input wire for this gate
              w = new Wire(child,disp,true);
              wireLevels.get(i-1).add(w);
              wireValNodes.put(w,node.getLeft());
            }
            else {
              y = (displayObjects.get(node.getLeft()).getOutput().y
                  +displayObjects.get(node.getRight()).getOutput().y)/2-Const.GATE_HEIGHT/2;
              switch(node.getType()) {
                case AND:
                  disp = new AndAnimator(x,y);
                  break;
                case NAND:
                  disp = new NAndAnimator(x,y);
                  break;
                case OR:
                  disp = new OrAnimator(x,y);
                  break;
                case NOR:
                  disp = new NOrAnimator(x,y);
                  break;
                case XOR:
                  disp = new XOrAnimator(x,y);
                  break;
                case NXOR:
                  disp = new NXOrAnimator(x,y);
                  break;
                default:
                  System.err.println("Invalid gate type attempted to be created");
              }
              displayObjects.put(node,disp);
              // Add the input wires for this gate
              w = new Wire(displayObjects.get(node.getLeft()),disp,true);
              wireLevels.get(i-1).add(w);
              wireValNodes.put(w,node.getLeft());
              w = new Wire(displayObjects.get(node.getRight()),disp,false);
              wireLevels.get(i-1).add(w);
              wireValNodes.put(w,node.getRight());
            }
        }
      }
      x += Const.GATE_WIDTH * 1.5; // Provides padding between tree levels (for wires)
    }
    // The canvas width has been determined by the Output-element placement (last element in the tree)
    myWidth = (int)(x - Const.GATE_WIDTH * 1.5 + Const.INPUT_WIDTH * 2);
    //=================================================================//
    //==== Paint all display elements and variable names to canvas ====//
    //=================================================================//
    // Create the double-buffer image with the correct dimensions
    buffer = new BufferedImage(myWidth,myHeight,BufferedImage.TYPE_INT_ARGB);
    // Get the graphics context so we can draw to the double-buffer
    Graphics2D g2 = buffer.createGraphics();
    // Fill the background of the canvas
    g2.setColor(Const.CANVAS_COLOR);
    g2.fillRect(0,0,myWidth,myHeight);
    // Make the variable name labels pretty
    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
    g2.setFont(myFont);
    g2.setColor(Const.OUTLINE_COLOR);
    // Draw all the variable name labels
    for (Point p : varNamePos.keySet()) {
      g2.drawString(varNamePos.get(p),p.x,p.y);
    }
    // Draw all the display elements
    for (ExprNode key : displayObjects.keySet()) {
      displayObjects.get(key).initializeGraphics(g2);
    }
    // Draw the output element
    outputObject.initializeGraphics(g2);
    // Draw the wires
    g2.setStroke(new BasicStroke(2));
    for (ArrayList<Wire> wl : wireLevels) {
      for (Wire wire : wl) {
        wire.initializeGraphics(g2);
      }
    }
  }
  
  /** Used to update the Input displays after their values have been changed */
  private void repaintInputs() {
    // Get the graphics context so we can draw to the double-buffer
    Graphics2D g2 = buffer.createGraphics();
    // Draw all the display elements
    for (ExprNode key : displayObjects.keySet()) {
      if (displayObjects.get(key) instanceof Input) {
        displayObjects.get(key).initializeGraphics(g2);
      }
    }
    repaint();
  }
  
  /** Exports the current image on the canvas to a PNG file.
   * Note: If the file passed in is not a PNG file then a new file will be
   * created witht he same name but the PNG extension.
   * @param myImg File to write the image to
   */
  public boolean exportImage(File myImg) {
    try {
      // Append the .png extention to the file if needed
      if (myImg.getName().length() < 5 || myImg.getName().toUpperCase().lastIndexOf(".PNG") != myImg.getName().length()-4) {
        myImg = new File(myImg.getAbsolutePath()+".png");
      }
      // Create the file if it doesn't already exist
      if (!myImg.exists()) {
        myImg.createNewFile();
      }
      // Write the image data out to the file
      FileOutputStream imgOut = new FileOutputStream(myImg);
      ImageIO.write(buffer,"PNG",imgOut);
      imgOut.close();
      return true;
    }
    catch (Exception e) {
      System.err.println(e);
      return false;
    }
  }
  
  /** (Overridden) Draws the double-buffer image to the component's graphics context
   * @param g Component's graphics context
   */
  protected void paintComponent(Graphics g) {
    g.drawImage(buffer,0,0,null);
  }
  
  /** Used to alert the canvas that the input values have been changed */
  public void actionPerformed(ActionEvent e) {
    repaintInputs();
  }
  
  /** Start animating the logic tree display */
  public void animate() {
    animationThread = new Thread(this,"GateAnimator");
    animationFinished = false;
    animationThread.start();
  }
  
  /** Manages the animation of the logic tree on a separate thread */
  public void run() {
    // Initialize various variables...
    Thread myThread = Thread.currentThread();
    Animatable gateAnimator;
    Graphics2D g2 = buffer.createGraphics();
    ArrayList<Animatable> currentAnimations = new ArrayList<Animatable>();
    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
    mainLoop: // Break Label
    // Loop through each level of the tree
    for (int i=1;i<=treeDepth;i++) {
      // Get all the wires for this level
      for (Wire w : wireLevels.get(i-1)) {
        w.animate(wireValNodes.get(w).exec());
        currentAnimations.add(w);
      }
      // Animate each of the wires on this level
      for (int j=0;j<Const.ANIM_QUALITY;j++) {
        for (Animatable anim : currentAnimations) {
          anim.updateGraphics(g2);
        }
        repaint();
        // Check for interruption and break out of these loops if necessary
        if (myThread != animationThread) { break mainLoop; }
        // Wait 10ms between drawings
        try {
          animationThread.sleep(10);
        }
        catch (InterruptedException e) {}
      }
      currentAnimations.clear();
      // Now get all the gates for this level
      for (ExprNode node : treeLevels.get(i)) {
        switch (node.getType()) {
          case AND:
          case NAND:
          case OR:
          case NOR:
          case XOR:
          case NXOR:
          case NOT:
            gateAnimator = (Animatable)displayObjects.get(node);
            gateAnimator.animate(node.exec());
            currentAnimations.add(gateAnimator);
            break;
          case OUTPUT:
            outputObject.animate(node.exec());
        }
      }
      // If this isn't the Output level, then animate all the gates
      if (i < treeDepth) {
        for (int j=0;j<Const.ANIM_QUALITY;j++) {
          for (Animatable anim : currentAnimations) {
            anim.updateGraphics(g2);
          }
          repaint();
          // Check for interruption and break out of these loops if necessary
          if (myThread != animationThread) { break mainLoop; }
          // Wait 10ms between drawings
          try {
            animationThread.sleep(10);
          }
          catch (InterruptedException e) {}
        }
        currentAnimations.clear();
      }
    }
    // If the animation was interrupted then terminate the animation process
    if (animationThread == myThread) {
      outputObject.updateGraphics(g2);
      repaint();
      animationThread = null;
    }
    // We're all done animating
    animationFinished = true;
  }
  
  /** Redraws the wires and gates on the canvas so that they're all in the NULL state again 
   * (Kills the animation thread if necessary)
   */
  public void resetCanvas() {
    // If the program is animating then we need to stop it
    if (animationThread != null) {
      animationThread = null;
      // I wanted to use a wait() here instead, but I'd have to make another thread for that, and I don't think it's worth it
      while (!animationFinished); // wait till the thread is finished
    }
    // Reset all the dispaly items except the inputs
    Graphics2D g2 = buffer.createGraphics();
    for (ArrayList<Wire> wl : wireLevels) {
      for (Wire w : wl) {
        w.initializeGraphics(g2);
      }
    }
    for (ExprNode key : displayObjects.keySet()) {
      displayObjects.get(key).initializeGraphics(g2);
    }
    outputObject.initializeGraphics(g2);
    repaint();
  }
  
}
