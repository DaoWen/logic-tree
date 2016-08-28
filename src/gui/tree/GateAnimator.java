/*
 * GateAnimator.java
 *
 * Created on January 2, 2006, 8:15 PM
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

import boolparser.BoolTypes;
import gui.*;

import java.util.HashMap;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.geom.*;

/** Abstract class for all Gate display objects
 * @author Nick Vrvilo
 */
public abstract class GateAnimator implements Sender, Receiver, Animatable {
  
  /** Static nested hashmap for storing all gate images */
  private static HashMap<BoolTypes,HashMap<Boolean,BufferedImage>> gateImages;
  
  /** X and Y coordinates of this image on the canvas */
  private int x, y;
  
  /** Type of gate this object draws (e.g. AND, NXOR, ect) */
  private BoolTypes myType;
  
  /** Current status of the gate (TRUE or FALSE) */
  private boolean myValue;
  
  /** xStart is the starting x-position for the unique gate symbol and counter
   * keeps track of the number of animation updates (both used for animation)
   */
  private int xStart, counter;
  
  /** Width of the slice to paint during animation updates */
  private double sliceWidth;
  
  /** The image replacing the NULL image during animation */
  private BufferedImage animationImage;
      
  /** Creates a new instance of an animated gate GUI component at position (posX, posY)
   * @param gateType Type of gate this represents (e.g. AND, NXOR, ect)
   * @param posX X-coordinate of the gate on the canvas
   * @param posY Y-coordinate of the gate on the canvas
   * @param sxStart Starting x-coordinate of the gate's symbol on the general gate image
   * @param sxEnd Ending x-coordinate of the gate's symbol on the general gate image
   */
  public GateAnimator(BoolTypes gateType, int posX, int posY, int sxStart, int sxEnd) {
    x = posX;
    y = posY;
    xStart = sxStart;
    sliceWidth = (double)(sxEnd - sxStart + 5) / Const.ANIM_QUALITY;
    myType = gateType;
    if (gateImages == null) {
      gateImages = new HashMap<BoolTypes,HashMap<Boolean,BufferedImage>>();
    }
    if (!gateImages.containsKey(gateType)) {
      // Set up variables for TRUE gate
      HashMap<Boolean,BufferedImage> images = new HashMap<Boolean,BufferedImage>(3);
      BufferedImage tempImage = new BufferedImage(Const.GATE_WIDTH,Const.GATE_HEIGHT,BufferedImage.TYPE_INT_ARGB);
      Graphics2D g2 = tempImage.createGraphics();
      // Fill & outline the box
      g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
      g2.setColor(Const.NULL_COLOR);
      g2.fillRect(0,0,tempImage.getWidth(),tempImage.getHeight());
      g2.setColor(Const.OUTLINE_COLOR);
      g2.setStroke(new BasicStroke(2));
      g2.draw(new Rectangle2D.Double(1,1,tempImage.getWidth()-3,tempImage.getHeight()-3));    
      // Draw the proper gate picture onto the box & put it in the map
      drawGate(g2,Const.TRUE_COLOR);
      images.put(true,tempImage);
      // Set up variables for FALSE gate
      tempImage = new BufferedImage(Const.GATE_WIDTH,Const.GATE_HEIGHT,BufferedImage.TYPE_INT_ARGB);
      g2 = tempImage.createGraphics();
      // Fill & outline the box
      g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
      g2.setColor(Const.NULL_COLOR);
      g2.fillRect(0,0,tempImage.getWidth(),tempImage.getHeight());
      g2.setColor(Const.OUTLINE_COLOR);
      g2.setStroke(new BasicStroke(2));
      g2.draw(new Rectangle2D.Double(1,1,tempImage.getWidth()-3,tempImage.getHeight()-3));    
      // Draw the proper gate picture onto the box
      drawGate(g2,Const.FALSE_COLOR);
      images.put(false,tempImage);
      // Set up variables for NULL gate
      tempImage = new BufferedImage(Const.GATE_WIDTH,Const.GATE_HEIGHT,BufferedImage.TYPE_INT_ARGB);
      g2 = tempImage.createGraphics();
      // Fill & outline the box
      g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
      g2.setColor(Const.NULL_COLOR);
      g2.fillRect(0,0,tempImage.getWidth(),tempImage.getHeight());
      g2.setColor(Const.OUTLINE_COLOR);
      g2.setStroke(new BasicStroke(2));
      g2.draw(new Rectangle2D.Double(1,1,tempImage.getWidth()-3,tempImage.getHeight()-3));    
      // Draw the proper gate picture onto the box
      drawGate(g2,Const.NULL_COLOR);
      images.put(null,tempImage);
      gateImages.put(gateType,images);
    }
  }
  
  /** Used for drawing a specific gate symbol onto the generic gate block image
   * @param g2 Graphics2D context of the image to draw the gate symbol on
   * @param fillColor Fill color to use when drawing the gate symbol
   */
  protected abstract void drawGate(Graphics2D g2, Color fillColor);
  
  /** {@inheritDoc} */
  public Point getLeftInput() {
    return new Point(x-1,y+18);
  }
  
  /** {@inheritDoc} */
  public Point getRightInput() {
    return new Point(x-1,y+58);
  }
  
  /** {@inheritDoc} */
  public Point getOutput() {
    return new Point(x+Const.GATE_WIDTH,y+Const.GATE_HEIGHT/2);
  }
  
  /** {@inheritDoc} */
  public void updateGraphics(Graphics2D g2) {
    if (counter < Const.ANIM_QUALITY) {
      int tx = (int)(xStart + counter * sliceWidth) - 1;
      g2.drawImage(animationImage.getSubimage(tx,0,(int)sliceWidth+1,Const.GATE_HEIGHT),x+tx,y,null);
      counter++;
    }
  }
  
  /** {@inheritDoc} */
  public void initializeGraphics(Graphics2D g2) {
    g2.drawImage(gateImages.get(myType).get(null),x,y,null);
  }
  
  /** {@inheritDoc} */
  public void animate(boolean value) {
    myValue = value;
    animationImage = gateImages.get(myType).get(myValue);
    counter = 0;
  }
  
}
