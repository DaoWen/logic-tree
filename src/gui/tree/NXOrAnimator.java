/*
 * NXOrAnimator.java
 *
 * Created on March 13, 2006, 9:07 PM
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

/** GUI Element for rendering the NXOr Gate and its animation process
 * @author Nick Vrvilo
 */
public class NXOrAnimator extends GateAnimator {
  
  /** Static shape for this gate's symbol */
  private static GeneralPath nxorGate, xcurve;
  
  /** Creates a new instance of the NXOr Gate GUI element at postion (x,y)
   * @param x X-coordinate of the gate on the canvas
   * @param y Y-coordinate of the gate on the canvas
   */
  public NXOrAnimator(int x, int y) {
    super(BoolTypes.NXOR,x,y,10,92);
  }
  
  /** {@inheritDoc} */
  public void drawGate(Graphics2D g2, Color fillColor) {
    if (nxorGate == null) {
      nxorGate = new GeneralPath();
      nxorGate.moveTo(10,8);
      nxorGate.curveTo(40,8,50,8,78,38);
      nxorGate.curveTo(50,68,40,68,10,68);
      nxorGate.curveTo(35,58,35,18,10,8);
      nxorGate.closePath();
      nxorGate.moveTo(80,38);
      nxorGate.curveTo(80,29,92,29,92,38);
      nxorGate.curveTo(92,45,80,45,80,38);
      nxorGate.closePath();
      xcurve = new GeneralPath();
      xcurve.moveTo(7,63);
      xcurve.curveTo(27,58,27,18,7,13);
    }
    g2.setColor(fillColor);
    g2.fill(nxorGate);
    g2.setColor(Const.OUTLINE_COLOR);
    g2.draw(nxorGate);
    g2.draw(xcurve);
  }
  
}
