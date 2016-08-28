/*
 * VarToken.java
 *
 * Created on December 22, 2005, 10:37 PM
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

/** Token parsed from a boolean-expression string representing a variable
 * @author Nick Vrvilo
 */
public class VarToken extends BoolToken {
  
  /** Stores this variable's name */
  private String myName;
  
  /** Creates a new instance of VarToken */
  public VarToken(String name) {
    super(BoolTypes.VAR);
    myName = name;
  }
  
  public String getName() {
    return myName;
  }
  
}
