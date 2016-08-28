/*
 * VarNode.java
 *
 * Created on December 23, 2005, 1:25 AM
 *
 * Logic Tree: A logic gate simulation program
 * Copyright (c) 2009 Nick Vrvilo
 * https://github.com/DaoWen/logic-tree
 * 
 * This program is distributed under the GNU General Public License.
 * For full deltails please see LicenseInfo.txt included with this source code.
 *
 */

package booltree;

import java.util.Set;
import java.util.TreeMap;

import boolparser.BoolTypes;

/** Boolean-expression variable node
 * @author Nick Vrvilo
 */
public class VarNode extends BoolTreeNode {
  
  /** This map keeps track of all variable values since a variabe can appear multiple
   *  times in the same tree, and the value of that variable must be consistant.
   */
  private static TreeMap<String,Boolean> varValMap = new TreeMap<String,Boolean>(), backupMap;
  
  /** This variable's name */
  private String myName;
  
  /** Creates a new instance of VarNode */
  public VarNode(String varName) {
    if (!varValMap.containsKey(varName)) {
      // Default value for all variables is TRUE
      varValMap.put(varName, true);
    }
    myName = varName;
  }
  
  /** Sets the value of a variable in the expression tree
   * @param varName Name of the variable
   * @param varValue New value for the variable
   */
  public static void setVarVal(String varName, Boolean varValue) {
    if (varValMap.containsKey(varName)) {
      varValMap.put(varName, varValue); 
    }
  }
  
  /** Retrieves the value of a variable in the expression tree
   * @param varName Name of the desired variable
   * @return Value of the variable requested
   */
  public static Boolean getVarVal(String varName) {
    return varValMap.get(varName);
  }
  
  /** Gets all the variable names in the tree
   * @return Collection of all variable names
   */
  public static Set<String> getVarNames() {
    return varValMap.keySet();
  }
  
  /** Erases all current variables so a new tree can be created */
  public static void clearVars() {
    varValMap = new TreeMap<String,Boolean>();
    backupMap = null;
  }
  
  /** {@inheritDoc} */
  public boolean exec() {
    Boolean val = varValMap.get(myName);
    if (val == null) {
      System.err.println("ERROR: Executed undefined variable $"+myName);
    }
    return val;
  }
  
  /** {@inheritDoc} */
  protected boolean isActive() {
    return true;
  }
  
  /** {@inheritDoc} */
  protected BoolTypes getType() {
    return BoolTypes.VAR;
  }
  
  /** {@inheritDoc} */
  public ExprNode getSimpleTree(ExprNode parent) {
    return new ExprNode(parent, myName);
  }
  
  /** Saves the current variable values */
  public static void saveVarValues() {
    backupMap = new TreeMap<String,Boolean>(varValMap);
  }
  
  /** Restores the saved variable values */
  public static void restoreVarValues() {
    varValMap = backupMap;
  }
  
}
