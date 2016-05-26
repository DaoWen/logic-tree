/*
 * InvalidTreeException.java
 *
 * Created on December 31, 2005, 1:41 AM
 *
 * Logic Tree: A logic gate simulation program
 * Copyright (c) 2009 Nick Vrvilo
 * http://ouuuuch.phoenixteam.org/
 * 
 * This program is distributed under the GNU General Public License.
 * For full deltails please see LicenseInfo.txt included with this source code.
 *
 */

package booltree;

/** Thrown when an invalid node is added to an expression tree
 * @author Nick Vrvilo
 */
public class InvalidTreeException extends Exception {
  
  /** Creates a new instance of InvalidTreeException */
  public InvalidTreeException(String msg) {
    super(msg);
  }
  
}
