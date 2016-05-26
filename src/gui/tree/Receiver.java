/*
 * InputDevice.java
 *
 * Created on March 20, 2006, 5:01 PM
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

/** Interface for tree elements that have input points
 * @author Nick Vrvilo
 */
public interface Receiver extends Drawable {
  /** Retrieves the coordinates for the left-hand input wire to intersect this gate
   * @return Coordinates of the left-hand input point
   */
  public java.awt.Point getLeftInput();
  
  /** Retrieves the coordinates for the right-hand input wire to intersect this gate
   * @return Coordinates of the right-hand input point
   */
  public java.awt.Point getRightInput();
}
