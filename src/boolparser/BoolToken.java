/*
 * BoolToken.java
 *
 * Created on December 22, 2005, 10:28 PM
 *
 * Logic Tree: A logic gate simulation program
 * Copyright (c) 2009 Nick Vrvilo
 * http://ouuuuch.phoenixteam.org/
 * 
 * This program is distributed under the GNU General Public License.
 * For full deltails please see LicenseInfo.txt included with this source code.
 *
 */

package boolparser;

/** Token parsed from a boolean-expression string representing an operator or argument
 * @author Nick Vrvilo
 */
public class BoolToken {
  
  /** Stores the this token's type */
  private BoolTypes myType;
  
  /** Creates a new instance of BoolToken */
  public BoolToken(BoolTypes type) {
    myType = type;
  }
  
  public BoolTypes getType() {
    return myType;
  }
  
}
