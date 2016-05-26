/*
 * NotAnimator.java
 *
 * Created on March 12, 2006, 10:35 PM
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

import boolparser.BoolTypes;
import gui.*;

import java.awt.*;
import java.awt.geom.GeneralPath;

/** GUI Element for rendering the Not Gate and its animation process
 * @author Nick Vrvilo
 */
public class NotAnimator extends GateAnimator {
  
  /** Static shape for this gate's symbol */
  private static GeneralPath notGate;
  
  /** Creates a new instance of the Not Gate GUI element at postion (x,y)
   * @param x X-coordinate of the gate on the canvas
   * @param y Y-coordinate of the gate on the canvas
   */
  public NotAnimator(int x, int y) {
    super(BoolTypes.NOT,x,y,10,92);
  }
  
  /** {@inheritDoc} */
  public void drawGate(Graphics2D g2, Color fillColor) {
    if (notGate == null) {
      notGate = new GeneralPath();
      notGate.moveTo(10,8);
      notGate.lineTo(78,38);
      notGate.lineTo(10,68);
      notGate.lineTo(10,8);
      notGate.closePath();
      notGate.moveTo(80,38);
      notGate.curveTo(80,29,92,29,92,38);
      notGate.curveTo(92,45,80,45,80,38);
      notGate.closePath();
    }
    g2.setColor(fillColor);
    g2.fill(notGate);
    g2.setColor(Const.OUTLINE_COLOR);
    g2.draw(notGate);
  }
  
  /** {@inheritDoc} */
  public Point getLeftInput() {
    // The Not Gate input needs to be centered
    Point temp = super.getLeftInput();
    temp.translate(0,20);
    return temp;
  }
  
  /** This method is overridden for the Not Gate since it has only one input
   * @return NULL
  */
  public Point getRightInput() {
    return null;
  }
  
}
