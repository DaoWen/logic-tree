/*
 * AndNode.java
 *
 * Created on December 23, 2005, 1:24 AM
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

/** Boolean <var>AND</var> operator node
 * @author Nick Vrvilo
 */
public class AndNode extends BinaryOperatorNode {
  
  /** {@inheritDoc} */
  protected boolean eval(boolean lhs, boolean rhs) {
    return (isNegated()) ? !(lhs && rhs) : lhs && rhs;
  }
  
  /** {@inheritDoc} */
  public void add(BoolTypes type) throws InvalidTreeException {
    // If the gate type being added matches this gate
    if (type == BoolTypes.AND || type == BoolTypes.NAND) {
      if (current() != null) {
        throw new InvalidTreeException("Unexpected "+type+" gate");
      }
      // If this gate already has both inputs      
      else if (getRight() != null) {
        // Create a duplicate gate
        OperatorNode duplicate = new AndNode();
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
      if (type == BoolTypes.NAND) {
          setNegated(true);
      }
    }
    // If the left-hand input isn't full
    else if (getRight() == null && current() != this) {
      // If we need to build the left subtree
      if (getLeft() == null) {
        switch (type) {
          case NOT:
            setLeft(new NotNode());
            ((OperatorNode)getLeft()).add(type);
            break;
          case TRUE:
            setLeft(new ConstNode(true));
            current(null);
            break;
          case FALSE:
            setLeft(new ConstNode(false));
            current(null);
            break;
          case LPAREN:
            setLeft(new XOrNode());
            deepestParen((OperatorNode)getLeft());
            break;
          default:
            throw new InvalidTreeException("Unexpected child being added to an AND gate's subtree");
        }
      }
      // If the left subtree already exists
      else {
        if (getLeft() instanceof OperatorNode) {
          ((OperatorNode)getLeft()).add(type);
        }
        else {
          throw new InvalidTreeException("Expected Binary Operator");
        }
      }
    }
    // If the right-hand input isn't full
    else {
      // If we need to build the right subtree
      if (getRight() == null && current() == this) {
        switch (type) {
          case NOT:
            setRight(new NotNode());
            ((OperatorNode)getRight()).add(type);
            break;
          case TRUE:
            setRight(new ConstNode(true));
            current(null);
            break;
          case FALSE:
            setRight(new ConstNode(false));
            current(null);
            break;
          case LPAREN:
            setRight(new XOrNode());
            current(null);
            deepestParen((OperatorNode)getRight());
            break;
          default:
            throw new InvalidTreeException("Unexpected child being added to an AND gate's subtree");
        }
      }
      // If the right-input is ready
      else {
        if (getLeft() == null) {
          throw new InvalidTreeException("No left-hand argument for AND gate");
        }
        if (getRight() instanceof OperatorNode) {
          ((OperatorNode)getRight()).add(type);
        }
        else {
          throw new InvalidTreeException("Unexpected "+type+" gate");
        }
      }
    }
  }
  
  /** {@inheritDoc} */
  public void addVar(String varName) throws InvalidTreeException {
    // If the left-hand input isn't full
    if (getRight() == null && current() != this) {
      // If we need to build the left subtree
      if (getLeft() == null) {
        setLeft(new VarNode(varName));
      }
      // If there is a left subtree
      else {
        if (getLeft() instanceof OperatorNode) {
          ((OperatorNode)getLeft()).addVar(varName);
        }
        else {
          throw new InvalidTreeException("Expected Binary Operator");
        }
      }
    }
    // If the right-hand input isn't full
    else {
      // If we need to build the right subtree
      if (getRight() == null && current() == this) {
        setRight(new VarNode(varName));
      }
      // If there is a right subtree
      else {
        ((OperatorNode)getRight()).addVar(varName);
      }
    }
    current(null);
  }
  
  /** {@inheritDoc} */
  protected BoolTypes getType() {
    return isNegated() ? BoolTypes.NAND : BoolTypes.AND;
  }
}
