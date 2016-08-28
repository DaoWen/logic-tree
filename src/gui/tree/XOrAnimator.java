/*
 * XOrAnimator.java
 *
 * Created on March 13, 2006, 9:13 PM
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

import java.awt.*;
import java.awt.geom.GeneralPath;

/** GUI Element for rendering the XOr Gate and its animation process
 * @author Nick Vrvilo
 */
public class XOrAnimator extends GateAnimator {
  
  /** Static shape for this gate's symbol */
  private static GeneralPath xorGate, xcurve;
  
  /** Creates a new instance of the XOr Gate GUI element at postion (x,y)
   * @param x X-coordinate of the gate on the canvas
   * @param y Y-coordinate of the gate on the canvas
   */
  public XOrAnimator(int x, int y) {
    super(BoolTypes.XOR,x,y,17,85);
  }
  
  /** {@inheritDoc} */
  public void drawGate(Graphics2D g2, Color fillColor) {
    if (xorGate == null) {
      xorGate = new GeneralPath();
      xorGate.moveTo(17,8);
      xorGate.curveTo(47,8,57,8,85,38);
      xorGate.curveTo(57,68,47,68,17,68);
      xorGate.curveTo(42,58,42,18,17,8);
      xorGate.closePath();
      xcurve = new GeneralPath();
      xcurve.moveTo(12,63);
      xcurve.curveTo(32,58,32,18,12,13);
    }
    g2.setColor(fillColor);
    g2.fill(xorGate);
    g2.setColor(Const.OUTLINE_COLOR);
    g2.draw(xorGate);
    g2.draw(xcurve);
  }
  
}
