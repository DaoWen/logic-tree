/*
 * InvalidExpressionException.java
 *
 * Created on December 22, 2005, 11:04 PM
 *
 * Logic Tree: A logic gate simulation program
 * Copyright (c) 2009 Nick Vrvilo
 * https://github.com/DaoWen/logic-tree
 * 
 * This program is distributed under the GNU General Public License.
 * For full deltails please see LicenseInfo.txt included with this source code.
 *
 */

package boolparser;

/** Exception thrown for invalidly formatted expression strings
 * @author Nick Vrvilo
 */
public class InvalidExpressionException extends Exception {
  
  /** Creates a new instance of InvalidExpressionException */
  public InvalidExpressionException(String msg) {
    super(msg);
  }
}
