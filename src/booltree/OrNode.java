/*
 * OrNode.java
 *
 * Created on December 23, 2005, 1:24 AM
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

import boolparser.BoolTypes;

/** Boolean <var>OR</var> operator node
 * @author Nick Vrvilo
 */
public class OrNode extends BinaryOperatorNode {
  
  /** {@inheritDoc} */
  protected boolean eval(boolean lhs, boolean rhs) {
    return (isNegated()) ? !(lhs || rhs) : lhs || rhs;
  }
  
  /** {@inheritDoc} */
  public void add(BoolTypes type) throws InvalidTreeException {
    // If the gate type being added matches this gate
    if (type == BoolTypes.OR || type == BoolTypes.NOR) {
      // If this gate already has both inputs
      if (getRight() != null) {
        // Create a duplicate gate
        OperatorNode duplicate = new OrNode();
        if (isNegated()) {
          duplicate.setNegated(true);
        }
        duplicate.setLeft(getLeft());
        duplicate.setRight(getRight());
        // Set the duplicate as the left-hand node
        setLeft(duplicate);
        // Clear the right-hand node
        setRight(null);
        // Un-negate this, just in case
        setNegated(false);
      }
      current(this);
      // If the gate needs to be negated
      if (type == BoolTypes.NOR) {
          setNegated(true);
      }
    }
    // If the left-hand input isn't full
    else if (getRight() == null && current() != this) {
      if (getLeft() == null) {
        setLeft(new AndNode());
      }
      ((OperatorNode)getLeft()).add(type);
    }
    // If the right-hand input isn't full
    else {
      if (getRight() == null && current() == this) {
        setRight(new AndNode());
      }
      if (getLeft() == null) {
        throw new InvalidTreeException("No left-hand argument for OR gate");
      }
      ((OperatorNode)getRight()).add(type);
    }
  }
  
  /** {@inheritDoc} */
  public void addVar(String varName) throws InvalidTreeException {
    // If the left-hand input isn't full
    if (getRight() == null && current() != this) {
      if (getLeft() == null) {
        setLeft(new AndNode());
      }
      ((OperatorNode)getLeft()).addVar(varName);
    }
    // If the right-hand input isn't full
    else {
      if (getRight() == null && current() == this) {
        setRight(new AndNode());
      }
      ((OperatorNode)getRight()).addVar(varName);
    }
  }
  
  /** {@inheritDoc} */
  protected BoolTypes getType() {
    return isNegated() ? BoolTypes.NOR : BoolTypes.OR;
  }
  
}
