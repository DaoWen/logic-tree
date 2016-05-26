/*
 * Drawable.java
 *
 * Created on March 20, 2006, 5:06 PM
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

/** Interface for objects that are drawn to the canvas
 * @author Nick Vrvilo
 */
public interface Drawable {
  /** Draws the initial image for this gate on the canvas 
   * @param g2 Graphics context for the canvas
   */
  public void initializeGraphics(java.awt.Graphics2D g2);
}
