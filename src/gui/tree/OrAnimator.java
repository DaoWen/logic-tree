/*
 * OrAnimator.java
 *
 * Created on March 13, 2006, 8:43 PM
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

/** GUI Element for rendering the Or Gate and its animation process
 * @author Nick Vrvilo
 */
public class OrAnimator extends GateAnimator {
  
  /** Static shape for this gate's symbol */
  private static GeneralPath orGate;
  
  /** Creates a new instance of the Or Gate GUI element at postion (x,y)
   * @param x X-coordinate of the gate on the canvas
   * @param y Y-coordinate of the gate on the canvas
   */
  public OrAnimator(int x, int y) {
    super(BoolTypes.OR,x,y,17,85);
  }
  
  /** {@inheritDoc} */
  public void drawGate(Graphics2D g2, Color fillColor) {
    if (orGate == null) {
      orGate = new GeneralPath();
      orGate.moveTo(17,8);
      orGate.curveTo(47,8,57,8,85,38);
      orGate.curveTo(57,68,47,68,17,68);
      orGate.curveTo(42,58,42,18,17,8);
      orGate.closePath();
    }
    g2.setColor(fillColor);
    g2.fill(orGate);
    g2.setColor(Const.OUTLINE_COLOR);
    g2.draw(orGate);
  }
  
}
