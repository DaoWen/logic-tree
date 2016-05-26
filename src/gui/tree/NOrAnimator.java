/*
 * NOrAnimator.java
 *
 * Created on March 13, 2006, 8:43 PM
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

/** GUI Element for rendering the Nor Gate and its animation process
 * @author Nick Vrvilo
 */
public class NOrAnimator extends GateAnimator {
  
  /** Static shape for this gate's symbol */
  private static GeneralPath norGate;
  
  /** Creates a new instance of the Nor Gate GUI element at postion (x,y)
   * @param x X-coordinate of the gate on the canvas
   * @param y Y-coordinate of the gate on the canvas
   */
  public NOrAnimator(int x, int y) {
    super(BoolTypes.NOR,x,y,10,92);
  }
  
  /** {@inheritDoc} */
  public void drawGate(Graphics2D g2, Color fillColor) {
    if (norGate == null) {
      norGate = new GeneralPath();
      norGate.moveTo(10,8);
      norGate.curveTo(40,8,50,8,78,38);
      norGate.curveTo(50,68,40,68,10,68);
      norGate.curveTo(35,58,35,18,10,8);
      norGate.closePath();
      norGate.moveTo(80,38);
      norGate.curveTo(80,29,92,29,92,38);
      norGate.curveTo(92,45,80,45,80,38);
      norGate.closePath();
    }
    g2.setColor(fillColor);
    g2.fill(norGate);
    g2.setColor(Const.OUTLINE_COLOR);
    g2.draw(norGate);
  }
  
}
