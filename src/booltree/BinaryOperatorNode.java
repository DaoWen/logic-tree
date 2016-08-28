/*
 * BinaryOperatorNode.java
 *
 * Created on December 31, 2005, 4:08 AM
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

/** Abstract class for gates with two inputs
 * @author Nick Vrvilo
 */
public abstract class BinaryOperatorNode extends OperatorNode {
  
  /** Applies the operation represented by this node to the two arguments
   * @param lhs Left-hand argument for the operator
   * @param rhs Right-hand argument for the operator
   * @return Result from evaluating <code>(lhs ~ rhs)</code>,
   * where <code>~</code> is the operator this node represents
   */
  protected abstract boolean eval(boolean lhs, boolean rhs);
  
  /** {@inheritDoc} */
  public boolean exec() {
    return (getRight() == null) ? getLeft().exec() : eval(getLeft().exec(),getRight().exec());
  }
  
  /** {@inheritDoc} */
  protected boolean isActive() {
    return (getLeft() != null) && (getRight() != null);
  }
  
}
