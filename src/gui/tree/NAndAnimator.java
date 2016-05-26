/*
 * NAndAnimator.java
 *
 * Created on March 13, 2006, 9:21 PM
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

/** GUI Element for rendering the NAnd Gate and its animation process
 * @author Nick Vrvilo
 */
public class NAndAnimator extends GateAnimator {
  
  /** Static shape for this gate's symbol */
  private static GeneralPath nandGate;
  
  /** Creates a new instance of the NAnd Gate GUI element at postion (x,y)
   * @param x X-coordinate of the gate on the canvas
   * @param y Y-coordinate of the gate on the canvas
   */
  public NAndAnimator(int x, int y) {
    super(BoolTypes.NAND,x,y,10,92);
  }
  
  /** {@inheritDoc} */
  public void drawGate(Graphics2D g2, Color fillColor) {
    if (nandGate == null) {
      nandGate = new GeneralPath();
      nandGate.moveTo(10,8);
      nandGate.lineTo(50,8);
      nandGate.curveTo(88,8,90,68,50,68);
      nandGate.lineTo(10,68);
      nandGate.lineTo(10,8);
      nandGate.closePath();
      nandGate.moveTo(80,38);
      nandGate.curveTo(80,29,92,29,92,38);
      nandGate.curveTo(92,45,80,45,80,38);
      nandGate.closePath();
    }
    g2.setColor(fillColor);
    g2.fill(nandGate);
    g2.setColor(Const.OUTLINE_COLOR);
    g2.draw(nandGate);
  }
  
}
