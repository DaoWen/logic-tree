/*
 * AndAnimator.java
 *
 * Created on March 13, 2006, 9:18 PM
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

/** GUI Element for rendering the And Gate and its animation process
 * @author Nick Vrvilo
 */
public class AndAnimator extends GateAnimator {
  
  /** Static shape for this gate's symbol */
  private static GeneralPath andGate;
  
  /** Creates a new instance of the And Gate GUI element at postion (x,y)
   * @param x X-coordinate of the gate on the canvas
   * @param y Y-coordinate of the gate on the canvas
   */
  public AndAnimator(int x, int y) {
    super(BoolTypes.AND,x,y,17,85);
  }
  
  /** {@inheritDoc} */
  public void drawGate(Graphics2D g2, Color fillColor) {
    if (andGate == null) {
      andGate = new GeneralPath();
      andGate.moveTo(17,8);
      andGate.lineTo(57,8);
      andGate.curveTo(95,8,97,68,57,68);
      andGate.lineTo(17,68);
      andGate.lineTo(17,8);
      andGate.closePath();
    }
    g2.setColor(fillColor);
    g2.fill(andGate);
    g2.setColor(Const.OUTLINE_COLOR);
    g2.draw(andGate);
  }
  
}
