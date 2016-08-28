/*
 * Input.java
 *
 * Created on March 17, 2006, 3:56 PM
 *
 * Logic Tree: A logic gate simulation program
 * Copyright (c) 2009 Nick Vrvilo
 * https://github.com/DaoWen/logic-tree
 * 
 * This program is distributed under the GNU General Public License.
 * For full deltails please see LicenseInfo.txt included with this source code.
 *
 */

package gui.tree;

import booltree.VarNode;
import gui.Const;

import java.awt.*;
import java.awt.geom.*;
import java.awt.image.BufferedImage;

/** Display object for variable/constant input display objects
 * @author Nick Vrvilo
 */
public class Input implements Sender {
  
  /** The x- and y-coordinates of this object on the canvas */
  private int x,y;
  
  /** Store the graphics for this object in its TRUE and FALSE states */
  private static BufferedImage trueImage, falseImage;
  
  /** The name of this input if it's a variable */
  private String myName;
  
  /** The current value of this input device */
  private boolean myValue;
  
  /** Creates a new instance of Input for a variable
   * @param x Element's x-coordinate
   * @param y Element's y-coordinate
   * @param varName Variable name corresponding to this input
   */
  public Input(int x, int y, String varName) {
    this(x,y);
    myName = varName;
    myValue = true;
  }
  
  /** Creates a new instance of Input for a constant
   * @param x Element's x-coordinate
   * @param y Element's y-coordinate
   * @param constValue Constant value corresponding to this input
   */
  public Input(int x, int y, boolean constValue) {
    this(x,y);
    myValue = constValue;
  }
  
  /** Creates a new instance of Input */
  private Input(int x, int y) {
    this.x = x;
    this.y = y;
    if (trueImage == null) {
      // Draw the TRUE image
      trueImage = new BufferedImage(Const.INPUT_WIDTH,Const.INPUT_HEIGHT,BufferedImage.TYPE_INT_ARGB);
      Graphics2D g2 = trueImage.createGraphics();
      g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
      g2.setStroke(new BasicStroke(2));
      g2.setColor(Const.OUTLINE_COLOR);
      g2.fillRect(23,10,7,7);
      g2.setColor(Const.TRUE_COLOR);
      g2.fill(new Ellipse2D.Double(3,3,20,20));
      g2.setColor(Const.OUTLINE_COLOR);
      g2.draw(new Ellipse2D.Double(3,3,20,20));
      // Draw the FALSE image
      falseImage = new BufferedImage(30,27,BufferedImage.TYPE_INT_ARGB);
      g2 = falseImage.createGraphics();
      g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
      g2.setStroke(new BasicStroke(2));
      g2.setColor(Const.OUTLINE_COLOR);
      g2.fillRect(23,10,7,7);
      g2.setColor(Const.FALSE_COLOR);
      g2.fill(new Ellipse2D.Double(3,3,20,20));
      g2.setColor(Const.OUTLINE_COLOR);
      g2.draw(new Ellipse2D.Double(3,3,20,20));
    }
  }
  
  /** {@inheritDoc} */
  public Point getOutput() {
    return new Point(x+Const.INPUT_WIDTH,y+Const.INPUT_HEIGHT/2);
  }
  
  /** {@inheritDoc} */
  public void initializeGraphics(Graphics2D g2) {
    if (myName != null) {
      myValue = VarNode.getVarVal(myName);
    }
    g2.drawImage((myValue?trueImage:falseImage),x,y,null);
  }
  
}
