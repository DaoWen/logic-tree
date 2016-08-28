/*
 * Animatable.java
 *
 * Created on March 25, 2006, 11:34 AM
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

/** Interface for animatable display objects
 * @author Nick Vrvilo
 */
public interface Animatable {
  
  /** Updates the graphical representation of this gate on the canvas during animation */
  public void updateGraphics(java.awt.Graphics2D g2);
  
  /** Begins the animation process for this gate
   * @param value Evaluation of the two inputs based on the gate type
   */  
  public void animate(boolean value);
  
}
