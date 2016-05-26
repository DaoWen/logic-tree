/*
 * OperatorNode.java
 *
 * Created on December 23, 2005, 3:20 PM
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

/** Abstract class for boolean operator nodes
 * @author Nick Vrvilo
 */
public abstract class OperatorNode extends BoolTreeNode {
  
  /** Pointer to the deepest gate currently accepting input */
  private static OperatorNode current;
  
  /** Pointer to the deepest XOR gate */
  private static OperatorNode deepestParen;
  
  /** Pointer to the root node of the binary expression tree */
  private static OperatorNode root;
  
  /** Flag representing a negation on the gate (e.g. NAND, NOR, NXOR) */
  private boolean negated;

  /** Negates this gate (e.g. AND becomes NAND) */
  protected void setNegated(boolean isNegated) {
    negated = isNegated;
  }
  
  
  /** Initializes the expression tree static variables
   * @param rootNode Root node of the expression tree
   */
  public static void initTree(OperatorNode rootNode) {
    root = rootNode;
    deepestParen = rootNode;
  }
  
  /** Gets the root of the binary expression tree
   * @return Pointer to the root node
   */
  protected static OperatorNode getRoot() {
    return root;
  }
  
  /** Sets the root of the binary expression tree
   * @param treeRoot New root node for the tree
   */
  protected static void setRoot(OperatorNode treeRoot) {
    root = treeRoot;
  }
  
  /** Accessor for the current node pointer
   * @return Pointer to the current node
   */
  public static OperatorNode current() {
    return current;
  }
  
  /** Mutator for the current node pointer
   * @param node Operator which is waiting for a right-hand argument
   */
  protected static void current(OperatorNode node) {
    current = node;
  }
  
  /** Accessor for the deepest parenthesized expression pointer
   * @return Pointer to the deepest parenthesized expression node
   */
  public static OperatorNode deepestParen() {
    return deepestParen;
  }
  
  /** Mutator for the reference to the deepest parenthesized expression
   * @param node Operator node whereat to begin traversals for adding items
   */
  protected static void deepestParen(OperatorNode node) {
    deepestParen = node;
  }
  
  /** Closes the deepest open parenthesized expression
   * @param node Node at which to start the traversal
   */
  protected static boolean closeParen(BoolTreeNode node, OperatorNode lastParen) {
    boolean done = false;
    if (node != null) {
      if (node != deepestParen) {
        if (node instanceof XOrNode) { // Finding next-deepest parenthesis
          lastParen = (OperatorNode)node;
        }
        // Using short-circuit logic to optimize recursion
        // If you find it in the left subtree, the right is never searched
        done = closeParen(node.getLeft(),lastParen) || closeParen(node.getRight(),lastParen);
      }
      else { // Base Case
        deepestParen = lastParen;
        done = true;
      }      
    }
    return done;
  }
  
  /** Adds a new boolean operator or constant to the tree
   * @param type Type of operator/constant to add
   */
  public abstract void add(BoolTypes type) throws InvalidTreeException;
  
  /** Adds a new variable to the tree
   * @param varName Name of the variable to add
   */
  public abstract void addVar(String varName) throws InvalidTreeException;
  
  /** Tells if the gate is negated (e.g. NAND, NOR, NXOR)
   * @return True if the gate is negated, false otherwise
   */
  protected boolean isNegated() {
    return negated;
  }
  
}
