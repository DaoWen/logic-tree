/*
 * ExprNode.java
 *
 * Created on January 2, 2006, 4:17 PM
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

import java.io.OutputStream;
import java.io.PrintStream;


/** Simple expression tree node (doesn't have parsing info or need subclasses)
 * @author Nick Vrvilo
 */
public class ExprNode {
  
  /** Keeps track of the current tree depth so as not to have to travers every time we need it */
  private static int treeDepth;
  
  /** Depth of this node in the tree (depths start at 0) */
  private int myDepth;
  
  /** References to the parent node and the left and right child nodes */
  private ExprNode myParent, myLeft, myRight;
  
  /** This node's type */
  private BoolTypes myType;
  
  /** Used to store variable nodes' names */
  private String myName;
  
  /** Creates a new instance of ExprNode */
  public ExprNode(ExprNode parent, BoolTypes type) {
    if (parent == null) {
      myDepth = 0;
    }
    else {
      myDepth = parent.myDepth + 1;
    }
    if (myDepth > treeDepth) {
      treeDepth = myDepth;
    }
    myParent = parent;
    myType = type;
  }
  
  /** Creates a new instance of ExprNode */
  public ExprNode(ExprNode parent, String name) {
    this(parent,BoolTypes.VAR);
    myName = name;
  }
  
  /** Sets the left child of this node
   * @param left Node to set as the left child
   */
  public void setLeft(ExprNode left) {
    myLeft = left;
  }
  
  /** Sets the right child of this node
   * @param right Node to set as the right child
   */
  public void setRight(ExprNode right) {
    myRight = right;
  }
  
  /** Gets the left child of this node
   * @return Left child node
   */
  public ExprNode getLeft() {
    return myLeft;
  }
  
  /** Gets the right child of this node
   * @return Right child node
   */
  public ExprNode getRight() {
    return myRight;
  }
  
  /** Gets the parent of this node
   * @return Parent node
   */
  public ExprNode getParent() {
    return myParent;
  }
  
  /** Gets this node's type
   * @return Token representing this node
   */
  public BoolTypes getType() {
    return myType;
  }
  
  /** Returns variable nodes' names
   * @return Variable's name, or <var>null</var> if this node isn't a variable
   */
  public String getName() {
    return myName;
  }
  
  /** Gets the current depth of the tree
   * @return Current tree depth
   */
  public int getDepth() {
    return treeDepth;
  }
  
  /** Resets the tree's depth so a new tree can be created */
  public static void resetDepth() {
    treeDepth = 0;
  }
  
  /** Evaluates this subtree
   * @return Value of this branch
   */
  public boolean exec() {
    boolean value = false;
    switch (myType) {
      case AND:
        value = myLeft.exec() && myRight.exec();
        break;
      case NAND:
        value = !(myLeft.exec() && myRight.exec());
        break;
      case OR:
        value = myLeft.exec() || myRight.exec();
        break;
      case NOR:
        value = !(myLeft.exec() || myRight.exec());
        break;
      case XOR:
        value = myLeft.exec() ^ myRight.exec();
        break;
      case NXOR:
        value = !(myLeft.exec() ^ myRight.exec());
        break;
      case NOT:
        value = !myLeft.exec();
        break;
      case VAR:
        value = VarNode.getVarVal(myName);
        break;
      case TRUE:
        value = true;
        break;
      case FALSE:
        value = false;
        break;
      case OUTPUT:
        value = myLeft.exec();
        break;
      default:
        System.err.println("ERROR: Encountered invalid symbol in tree");
    }
    return value;
  }
  
}
