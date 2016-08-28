/*
 * BoolExpTree.java
 *
 * Created on December 31, 2005, 1:46 AM
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

import boolparser.*;

import java.util.ArrayList;
import java.io.*;

/** Creates, stores, and handles the expression tree
 * @author Nick Vrvilo
 */
public class BoolExpTree {
  
  private static OperatorNode tree;
  private static ExprNode simpleTree;
  private static String expression;

  /** Static class needs no constructor */
  private BoolExpTree() {}
  
  /** Attempts to add a new element to the expression tree
   * @param token New node to add to the tree
   * @throws InvalidTreeException
   */
  private static void add(BoolToken token) throws InvalidTreeException {
    if (token.getType() == BoolTypes.VAR) {
      if (tree.current() == null) {
        tree.deepestParen().addVar(((VarToken)token).getName());
      }
      else {
        tree.current().addVar(((VarToken)token).getName());
      }
    }
    else {
      tree.deepestParen().add(token.getType());
    }
  }
  
  /** Initializes the expression tree by parsing the input string
   * @param expr Boolean expression to be parsed
   * @throws InvalidExpressionException
   * @throws InvalidTreeException
   */
  public static void initTree(String expr) throws InvalidExpressionException, InvalidTreeException {
    tree = new XOrNode();
    tree.initTree(tree);
    simpleTree = null;
    VarNode.clearVars();
    ArrayList<BoolToken> tokens = Lexer.tokenizeString(expr);
    for (BoolToken t : tokens) {
      add(t);
    }
    expression = expr;
    ExprNode.resetDepth();
    simpleTree = new ExprNode(null,BoolTypes.OUTPUT);
    simpleTree.setLeft(tree.getSimpleTree(simpleTree));
    tree = null; // cleans up the other tree since it isn't needed anymore
  }
  
  /** Traverses the expression tree to find the value
   * @return Value returned by tree traversal
   */
  public static boolean exec() {
    if (simpleTree == null) {
      getSimpleTree();
    }
    return simpleTree.exec();
  }
  
  
  /** Writes out a text-only truth table
   * @param outStream OutputStream to which the table should be written
   */
  public static void generateTruthTable(OutputStream outStream) throws IOException {
    VarNode.saveVarValues(); // Saves the current variable values so they can be restored later
    PrintStream output = new PrintStream(outStream);
    ArrayList<String> varNames = new ArrayList<String>(VarNode.getVarNames());
    int n = varNames.size();
    // start visual
    StringBuilder header = new StringBuilder();
    int[] paddingFront = new int[n];
    int[] paddingBack = new int[n];
    output.println("f() = "+expression+'\n');
    header.append('|');
    int m=0;
    for (String s : varNames) {
      paddingFront[m] = s.length()/2;
      // x&1 == x%2
      paddingBack[m] = paddingFront[m];
      if ((s.length()&1)==0) {
        paddingFront[m]--;
      }
      header.append(' ');
      header.append(s);
      header.append(" |");
      m++;
    }
    header.append(" f() |");
    for (int i=0;i<header.length();i++) {
      output.print('-');
    }
    output.println();
    output.println(header.toString());
    for (int i=0;i<header.length();i++) {
      output.print('-');
    }
    output.println();
    // end visual
    for (int i=0;i<(1<<n);i++) {
      output.print('|');
      for (int j=0;j<n;j++) {
        // 1<<x == Math.pow(2,x)
        if (i%((1<<n)/(1<<j+1))==0) {
          // x%2 == x&1
          boolean val = ((i/((1<<n)/(1<<j+1))) & 1) == 0;
          VarNode.setVarVal(varNames.get(j), val);
        }
        for (int k=0;k<paddingFront[j];k++) {
          output.print(' ');
        }
        output.print(VarNode.getVarVal(varNames.get(j)) ? " T " : " F ");
        for (int k=0;k<paddingBack[j];k++) {
          output.print(' ');
        }
        output.print('|');
      }
      output.println("  "+(exec()?'T':'F')+"  |");
    }
    for (int i=0;i<header.length();i++) {
      output.print('-');
    }
    output.println();
    VarNode.restoreVarValues(); // Restores the variable values to what they were originally
  }
  
  /** Writes a truth table using HTML for formatting
   * @param out OutputStream to which the HTML Truth Table should be written
   */
  public static void generateTruthTableHTML(OutputStream out) throws IOException {
    VarNode.saveVarValues(); // Saves the current variable values so they can be restored later
    ArrayList<String> varNames = new ArrayList<String>(VarNode.getVarNames());
    int n = varNames.size();
    // HTML generation
    OutputStreamWriter htmlWriter = new OutputStreamWriter(out,"utf-8");
    htmlWriter.write("<?xml version=\"1.0\" encoding=\"utf-8\"?>\n<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.1//EN\" \n     \"http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd\">\n<html xmlns=\"http://www.w3.org/1999/xhtml\" xml:lang=\"en\">\n<head>\n<meta http-equiv=\"content-type\" content=\"application/xhtml+xml\" />\n<title>Truth Table</title>\n<style type=\"text/css\">\nbody {padding:10px;text-align:center;}\ncaption {font-size:x-large;margin:auto;}\ntable {font-size:large;border:3px double black;border-collapse:collapse;margin:auto;}\ntd, th {border:1px solid black;padding:.5em 2em;}\nth {border-bottom:3px double black}\ncol.result{background-color:#eee;}\n</style>\n</head>\n<body>\n<h1>f(");
    for (int i=0;i<n;i++) {
      htmlWriter.write('$');
      htmlWriter.write(varNames.get(i));
      if (i < n-1) {
        htmlWriter.write(',');
      }
    }
    htmlWriter.write(") = ");
    htmlWriter.write(expression);
    htmlWriter.write("</h1>\n<table summary=\"Truth table for the function f()\">\n<caption>Truth Table</caption>\n<colgroup span=\"");
    htmlWriter.write(String.valueOf(n));
    htmlWriter.write("\"></colgroup><col class=\"result\"></col>\n<thead>\n<tr><th>");
    for (String s : varNames) {
      htmlWriter.write('$');
      htmlWriter.write(s);
      htmlWriter.write("</th><th>");
    }
    htmlWriter.write("f()</th></tr>\n</thead>\n<tbody>\n");
    // HTML generation
    for (int i=0;i<(1<<n);i++) {
      htmlWriter.write("<tr><td>");
      for (int j=0;j<n;j++) {
        // 1<<x == Math.pow(2,x)
        if (i%((1<<n)/(1<<j+1))==0) {
          // x%2 == x&1
          boolean val = ((i/((1<<n)/(1<<j+1))) & 1) == 0;
          VarNode.setVarVal(varNames.get(j), val);
        }
        htmlWriter.write(VarNode.getVarVal(varNames.get(j)) ? 'T' : 'F');
        htmlWriter.write("</td><td>");
      }
      htmlWriter.write(exec()?'T':'F');
      htmlWriter.write("</td></tr>\n");
    }
    htmlWriter.write("</tbody>\n</table>\n</body>\n</html>");
    htmlWriter.close();
    VarNode.restoreVarValues(); // Restores the variable values to what they were originally
  }
  
  /** Gets the expression tree for evaluation purposes
   * @return Root node of the simple expression tree
   */
  public static ExprNode getSimpleTree() {
    return simpleTree;
  }
  
  /** Tells whether or not there is a tree in memory
   * @return <var>True</var> if there is a tree, <var>false</var> if not
   */
  public static boolean initialized() {
    return expression != null;
  }
  
 /** Gets the user's Boolean expression string
  * @return User's original input from which the tree was parsed
  */
  public static String getExpression() {
    return expression;
  }
  
}
