/*
 * NotNode.java
 *
 * Created on December 23, 2005, 1:23 AM
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

/** Boolean <var>NOT</var> operator node
 * @author Nick Vrvilo
 */
public class NotNode extends OperatorNode {
  
  /** {@inheritDoc} */
  public boolean exec() {
    return (isNegated()) ? !getLeft().exec() : getLeft().exec();
  }
  
  /** {@inheritDoc} */
  public void add(BoolTypes type) throws InvalidTreeException {
    // If this gate is currently accepting input
    if (current() == this) {
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
          current(null);
          deepestParen((OperatorNode)getLeft());
          break;
        default:
          throw new InvalidTreeException("Unexpected child being added to a NOT gate's subtree");
      }
    }
    // If this gate has an open input
    else if (getLeft() == null) {
      setNegated(true);
      current(this);
    }
    else {
      ((OperatorNode)getLeft()).add(type);
    }
  }
  
  /** {@inheritDoc} */
  public void addVar(String varName) throws InvalidTreeException {
    // If the input isn't full
    if (getLeft() == null && current() == this) {
      setLeft(new VarNode(varName));
      current(null);
    }
    // If there is a subtree
    else {
      ((OperatorNode)getLeft()).addVar(varName);
    }
  }
  
  /** {@inheritDoc} */
  protected boolean isActive() {
    return isNegated();
  }
  
  /** {@inheritDoc} */
  protected BoolTypes getType() {
    return BoolTypes.NOT;
  }
  
  /** {@inheritDoc} */
  public ExprNode getSimpleTree(ExprNode parent) {
    // Base case in overridden leaf node methods
    ExprNode simpleMe;
    if (isActive()) {
      simpleMe = new ExprNode(parent, getType());
      simpleMe.setLeft(getLeft().getSimpleTree(simpleMe));
    }
    else {
      simpleMe = getLeft().getSimpleTree(parent);
    }
    return simpleMe;
  }
  
}
