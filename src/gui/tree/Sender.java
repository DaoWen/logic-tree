/*
 * OutputDevice.java
 *
 * Created on March 18, 2006, 1:42 PM
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

/** Interface for tree elements that have output points
 * @author Nick Vrvilo
 */
public interface Sender extends Drawable {
  /** Retrieves the coordinates where the output wire intersects this gate
   * @return Coordinates of the output point
   */
  public java.awt.Point getOutput();

}
