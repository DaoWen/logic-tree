/*
 * Wire.java
 *
 * Created on March 20, 2006, 4:56 PM
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

import booltree.ExprNode;

import gui.Const;

import java.awt.*;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

/** Display object for the "wires" connecting the gates
 * @author Nick Vrvilo
 */
public class Wire implements Drawable, Animatable {
  /** "out" stores the ouput point on the last device, "in" the input point on the next device */
  private Point out, in;
  
  /** The value outputted by the last gate */
  private boolean myValue;
  
  /** The slice-size for animation updates */
  private double dx;
  
  /** counter keeps track of the number of animation updates made
   * x1,x2,x3 are the lengths of each wire segment
   * mid stores the halfway point between the two devices passed into the constructor
   */
  private int counter, x1, x2, x3, mid;
  
  /** The outline color for the wire during animation */
  private Color fillColor;
  
  /** Creates a new instance of Wire */
  public Wire(Sender output, Receiver input, boolean leftInput) {
      in = leftInput ? input.getLeftInput() : input.getRightInput();
      out = output.getOutput();
      mid = (in.x + out.x)/2;
      if (out.y != in.y) {
        x1 = mid - out.x;      
        x2 = Math.abs(in.y - out.y);
        x3 = in.x - mid;
      }
      else {
        x1 = in.x - out.x + 1;
        x2 = 0;
        x3 = 0;
      }
      dx = (double)(x1 + x2 + x3) / Const.ANIM_QUALITY;
  }
  
  /** {@inheritDoc} */
  public void initializeGraphics(Graphics2D g2) {
    int full = x1+x2+x3;
    fillColor = Const.CANVAS_COLOR;
    drawOutline1(g2,full);
    drawOutline2(g2,full);
    drawOutline3(g2,full);
    drawLine1(g2,full);
    drawLine2(g2,full);
    drawLine3(g2,full);
  }
  
  /** {@inheritDoc} */
  public void updateGraphics(Graphics2D g2) {
    if (counter < Const.ANIM_QUALITY) {
      double tx = ++counter * dx;
      if (tx <= x1) {
        drawOutline1(g2,tx);
        drawLine1(g2,tx);
      }
      else if (tx <= x2+x1) {
        drawOutline1(g2,tx);
        drawOutline2(g2,tx);
        drawLine1(g2,tx);
        drawLine2(g2,tx);
      }
      else if (tx <= x3+x2+x1) {
        drawOutline1(g2,tx);
        drawOutline2(g2,tx);
        drawOutline3(g2,tx);
        drawLine1(g2,tx);
        drawLine2(g2,tx);
        drawLine3(g2,tx);
      }
    }
  }
  
  private void drawLine1(Graphics2D g2, double tx) {
    if (tx < x1) {
      g2.setColor(Const.OUTLINE_COLOR);
      g2.fillRect(out.x,out.y-1,(int)tx,3);
    }
    else {
      g2.setColor(Const.OUTLINE_COLOR);
      g2.fillRect(out.x,out.y-1,x1,3);
    }
  }
  
  private void drawOutline1(Graphics2D g2, double tx) {
    if (tx < x1) {
      g2.setColor(fillColor);
      g2.fillRect(out.x,out.y-3,(int)tx,7);
    }
    else {
      g2.setColor(fillColor);
      g2.fillRect(out.x,out.y-3,x1,7);
    }
  }
  
  private void drawLine2(Graphics2D g2, double tx) {
    if (tx < x2+x1) {
      if (out.y < in.y) {
        g2.setColor(Const.OUTLINE_COLOR);
        g2.fillRect(mid,out.y-1,3,(int)tx-x1+3);
      }
      else {
        g2.setColor(Const.OUTLINE_COLOR);
        g2.fillRect(mid,out.y+6-((int)tx-x1+7),3,(int)tx-x1+3);
      }
    }
    else {
      if (out.y < in.y) {
        g2.setColor(Const.OUTLINE_COLOR);
        g2.fillRect(mid,out.y-1,3,x2+3);
      }
      else {
        g2.setColor(Const.OUTLINE_COLOR);
        g2.fillRect(mid,in.y-1,3,x2+3);
      }
    }
  }
  
  private void drawOutline2(Graphics2D g2, double tx) {
    if (tx < x2+x1) {
      if (out.y < in.y) {
        g2.setColor(fillColor);
        g2.fillRect(mid-2,out.y-3,7,(int)tx-x1+7);
      }
      else {
        g2.setColor(fillColor);
        g2.fillRect(mid-2,out.y+4-((int)tx-x1+7),7,(int)tx-x1+7);
      }
    }
    else {
      if (out.y < in.y) {
        g2.setColor(fillColor);
        g2.fillRect(mid-2,out.y-3,7,x2+7);
      }
      else {
        g2.setColor(fillColor);
        g2.fillRect(mid-2,in.y-3,7,x2+7);
      }
    }
  }
  
  private void drawLine3(Graphics2D g2, double tx) {
    g2.setColor(Const.OUTLINE_COLOR);
    g2.fillRect(mid,in.y-1,(int)tx-x2-x1,3);
  }
  
  private void drawOutline3(Graphics2D g2, double tx) {
    g2.setColor(fillColor);
    g2.fillRect(mid,in.y-3,(int)tx-x2-x1,7);
  }
  
  /** {@inheritDoc} */
  public void animate(boolean value) {
    counter = 0;
    myValue = value;
    fillColor = myValue ? Const.TRUE_COLOR : Const.FALSE_COLOR;
  }
  
}
