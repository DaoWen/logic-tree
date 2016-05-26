/*
 * Output.java
 *
 * Created on March 20, 2006, 12:27 AM
 *
 * Logic Tree: A logic gate simulation program
 * Copyright (c) 2009 Nick Vrvilo
 * http://ouuuuch.phoenixteam.org/
 * 
 * This program is distributed under the GNU General Public License.
 * For full deltails please see LicenseInfo.txt included with this source code.
 *
 */

package gui.tree;

import gui.Const;

import java.awt.*;
import java.awt.geom.*;
import java.awt.image.BufferedImage;

/** Display object for the expression output value's display object
 * @author Nick Vrvilo
 */
public class Output implements Receiver, Animatable {
  
  /** The x- and y-coordinates of this object on the canvas */
  private int x,y;
  
  /** Store the graphics for this object in its TRUE and FALSE states */
  private static BufferedImage trueImage, falseImage, nullImage;
  
  /** Holds the output value of the expression tree */
  private boolean myValue;
  
  /** Creates a new instance of Output */
  public Output(int x, int y) {
    this.x = x;
    this.y = y;
    if (trueImage == null) {
      trueImage = new BufferedImage(Const.INPUT_WIDTH,Const.INPUT_HEIGHT,BufferedImage.TYPE_INT_ARGB);
      Graphics2D g2 = trueImage.createGraphics();
      g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
      g2.setStroke(new BasicStroke(2));
      g2.setColor(Const.OUTLINE_COLOR);
      g2.fillRect(0,10,7,7);
      g2.setColor(Const.TRUE_COLOR);
      g2.fill(new Ellipse2D.Double(7,3,20,20));
      g2.setColor(Const.OUTLINE_COLOR);
      g2.draw(new Ellipse2D.Double(7,3,20,20));
      falseImage = new BufferedImage(30,27,BufferedImage.TYPE_INT_ARGB);
      g2 = falseImage.createGraphics();
      g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
      g2.setStroke(new BasicStroke(2));
      g2.setColor(Const.OUTLINE_COLOR);
      g2.fillRect(0,10,7,7);
      g2.setColor(Const.FALSE_COLOR);
      g2.fill(new Ellipse2D.Double(7,3,20,20));
      g2.setColor(Const.OUTLINE_COLOR);
      g2.draw(new Ellipse2D.Double(7,3,20,20));
      nullImage = new BufferedImage(30,27,BufferedImage.TYPE_INT_ARGB);
      g2 = nullImage.createGraphics();
      g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
      g2.setStroke(new BasicStroke(2));
      g2.setColor(Const.OUTLINE_COLOR);
      g2.fillRect(0,10,7,7);
      g2.setColor(Const.NULL_COLOR);
      g2.fill(new Ellipse2D.Double(7,3,20,20));
      g2.setColor(Const.OUTLINE_COLOR);
      g2.draw(new Ellipse2D.Double(7,3,20,20));
    }
  }
  
  /** {@inheritDoc} */
  public java.awt.Point getLeftInput() {
    return new Point(x-1,y+Const.INPUT_HEIGHT/2);
  }
  
  /** {@inheritDoc} */
  public Point getRightInput() {
    return null;
  }
  
  /** {@inheritDoc} */
  public void initializeGraphics(Graphics2D g2) {
    g2.drawImage(nullImage,x,y,null);
  }
  
  /** {@inheritDoc} */
  public void updateGraphics(Graphics2D g2) {
    g2.drawImage((myValue ? trueImage : falseImage),x,y,null);
  }
  
  /** {@inheritDoc} */
  public void animate(boolean value) {
    myValue = value;
  }
  
}
