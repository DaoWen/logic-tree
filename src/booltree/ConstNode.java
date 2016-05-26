/*
 * ConstNode.java
 *
 * Created on December 23, 2005, 1:25 AM
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

import boolparser.BoolTypes;

/** Boolean constant argument node (either <var>TRUE</var> or <var>FALSE</var>)
 * @author Nick Vrvilo
 */
public class ConstNode extends BoolTreeNode {
  
  /** Tells if this is a TRUE or FALSE constant */
  private boolean myValue;
  
  /** Creates a new instance of ConstNode */
  public ConstNode(boolean constValue) {
    myValue = constValue;
  }
  
  /** {@inheritDoc} */
  public boolean exec() {
    return myValue;
  }
  
  /** {@inheritDoc} */
  protected boolean isActive() {
    return true;
  }
  
  /** {@inheritDoc} */
  protected BoolTypes getType() {
    return myValue ? BoolTypes.TRUE : BoolTypes.FALSE;
  }
  
  /** {@inheritDoc} */
  public ExprNode getSimpleTree(ExprNode parent) {
    return new ExprNode(parent, getType());
  }
  
}
