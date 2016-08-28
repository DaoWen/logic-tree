/*
 * BoolTreeNode.java
 *
 * Created on December 22, 2005, 10:53 PM
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

/** Abstract class for nodes of a boolean expression tree
 * @author Nick Vrvilo
 */
public abstract class BoolTreeNode {
  
  /** The left and right node pointers */
  private BoolTreeNode myLeftNode, myRightNode;
  
  /** Sets the left node-pointer
   * @param node Pointer to the new left node
   */
  protected void setLeft(BoolTreeNode node) {
    myLeftNode = node;
  }
  
  /** Sets the right node pointer
   * @param node Pointer to the new right node
   */
  protected void setRight(BoolTreeNode node) {
    myRightNode = node;
  }

  /** Gets a pointer to the left-hand node
   * @return Pointer to the left-hand node
   */
  protected BoolTreeNode getLeft() {
    return myLeftNode;
  }
  
  /** Gets a pointer to the right-hand node
   * @return Pointer to the right-hand node
   */
  protected BoolTreeNode getRight() {
    return myRightNode;
  }
  
  /** Evaluates the boolean expression tree
   * @return Value resulting from evaluating the expression tree
   */
  public abstract boolean exec();
  
  /** Tells is this node is actually being used or not
   * @return <var>True</var> if this node actually represents
   * a token in the parsed expression, <var>false</var> otherwise
   */
  protected abstract boolean isActive();
  
  /** Returns a simple tree representing this subtree
   * @param parent Simple tree node to set as the parent of the returned subtree
   */
  public ExprNode getSimpleTree(ExprNode parent) {
    // Base case in overridden leaf node methods
    ExprNode simpleMe;
    if (isActive()) {
      simpleMe = new ExprNode(parent, getType());
      simpleMe.setLeft(getLeft().getSimpleTree(simpleMe));
      simpleMe.setRight(getRight().getSimpleTree(simpleMe));
    }
    else {
      simpleMe = getLeft().getSimpleTree(parent);
    }
    return simpleMe;
  }
  
  /** Gets the node's type
   * @return Token representing this node
   */
  protected abstract BoolTypes getType();
  
}
