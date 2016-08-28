/*
 * Lexer.java
 *
 * Created on December 22, 2005, 10:27 PM
 *
 * Logic Tree: A logic gate simulation program
 * Copyright (c) 2009 Nick Vrvilo
 * https://github.com/DaoWen/logic-tree
 * 
 * This program is distributed under the GNU General Public License.
 * For full deltails please see LicenseInfo.txt included with this source code.
 *
 */

package boolparser;

import java.util.ArrayList;

/** Lexigraphical parser class for boolean-expression strings
 * @author Nick Vrvilo
 */
public class Lexer {
  
  
  /** Lexer is Static, so you don't need to make a new instance */
  private Lexer() {}
  
  /** Lexigraphically parses a string into tokens
   * @param stringToParse Boolean-expression string to be parsed
   * @return Collection of all tokens parsed from the string
   * @throws InvalidExpressionException Thrown if an error occurs while
   * parsing the input string because of invalid formatting
   */
  public static ArrayList<BoolToken> tokenizeString(String stringToParse) throws InvalidExpressionException {
    // Validation variables
    int numInputlessParen=0;
    int numInputs=0, numBinaryGates=0;
    int numOpenParen = 0;
    // Lexer variables
    ArrayList<BoolToken> tokens = new ArrayList<BoolToken>();
    char[] chars = stringToParse.toLowerCase().toCharArray();
    boolean parsingToken = false;
    // Main loop
    for (int i=0;i<chars.length;++i) {
      switch (chars[i]) {
        // Check for $VAR, $_TRUE, $_FALSE
        case '$':
          if (chars[i+1] == '_') {
            if (i+5 < chars.length && chars[i+2] == 't' && chars[i+3] == 'r' && chars[i+4] == 'u' && chars[i+5] == 'e') {
              tokens.add(new BoolToken(BoolTypes.TRUE));
              i += 5;
              // Validation Stuff
              numInputlessParen = 0;
              numInputs++;
            }
            else if (i+4 < chars.length && chars[i+2] == 'f' && chars[i+3] == 'a' && chars[i+4] == 'l' && chars[i+5] == 's' && chars[i+6] == 'e') {
              tokens.add(new BoolToken(BoolTypes.FALSE));
              i += 6;
              // Validation Stuff
              numInputlessParen = 0;
              numInputs++;
            }
            else {
              throw new InvalidExpressionException("Invalid constant\nOnly $_TRUE or $_FALSE allowed");
            }
          }
          else {
            int j=0;
            while (i+(++j) < chars.length && Character.isLetterOrDigit(chars[i+j]));
            if (i+j >= chars.length || Character.isWhitespace(chars[i+j]) || chars[i+j] == ')') {
              tokens.add(new VarToken(stringToParse.substring(i+1,i+j)));
              i += j-1;
              // Validation Stuff
              numInputlessParen = 0;
              numInputs++;
            }
            else {
              throw new InvalidExpressionException("Invalid variable name\nOnly alpha-numeric characters allowed");
            }
          }
          break;
        // Check for Parenthesis
        case '(':
          tokens.add(new BoolToken(BoolTypes.LPAREN));
          numInputlessParen++;
          numOpenParen++;
          break;
        case ')':
          tokens.add(new BoolToken(BoolTypes.RPAREN));
          if (numInputlessParen > 0 || numOpenParen == 0) {
            throw new InvalidExpressionException("Improperly nested parenthesis");
          }
          numOpenParen--;
          break;
        // Check for NOR, NOT, NXOR,  NAND
        case 'n':
          if (i+2 < chars.length) {
            if (chars[i+1] == 'o' && chars[i+2] == 'r') {
              tokens.add(new BoolToken(BoolTypes.NOR));
              i += 2;
              numBinaryGates++;
            }
            else if (chars[i+1] == 'o' && chars[i+2] == 't') {
              tokens.add(new BoolToken(BoolTypes.NOT));
              i += 2;
            }
            else if (i+3 < chars.length) {
              if (chars[i+1] == 'x' && chars[i+2] == 'o' && chars[i+3] == 'r') {
                tokens.add(new BoolToken(BoolTypes.NXOR));
                i += 3;
                numBinaryGates++;
              }
              else if (chars[i+1] == 'a' && chars[i+2] == 'n' && chars[i+3] == 'd') {
                tokens.add(new BoolToken(BoolTypes.NAND));
                i += 3;
                numBinaryGates++;
              }
              else {
                String badStr = stringToParse.substring(i,(stringToParse.length()<i+4)?stringToParse.length():i+4);
                throw new InvalidExpressionException("Unrecognized token \""+badStr+"\"");
              }
            }
            else {
              String badStr = stringToParse.substring(i,(stringToParse.length()<i+3)?stringToParse.length():i+3);
              throw new InvalidExpressionException("Unrecognized token \""+badStr+"\"");
            }
          }
          else {
            String badStr = stringToParse.substring(i,(stringToParse.length()<i+2)?stringToParse.length():i+2);
            throw new InvalidExpressionException("Unrecognized token \""+badStr+"\"");
          }
          break;
        // Check for XOR
        case 'x':
          if (i+2 < chars.length && chars[i+1] == 'o' && chars[i+2] == 'r') {
            tokens.add(new BoolToken(BoolTypes.XOR));
            i += 2;
            numBinaryGates++;
          }
          else {
            String badStr = stringToParse.substring(i,(stringToParse.length()<i+3)?stringToParse.length():i+3);
            throw new InvalidExpressionException("Unrecognized token \""+badStr+"\"");
          }
          break;
        // Check for OR
        case 'o':
          if (i+1 < chars.length && chars[i+1] == 'r') {
            tokens.add(new BoolToken(BoolTypes.OR));
            ++i;
            numBinaryGates++;
          }
          else {
            String badStr = stringToParse.substring(i,(stringToParse.length()<i+2)?stringToParse.length():i+2);
            throw new InvalidExpressionException("Unrecognized token \""+badStr+"\"");
          }
          break;
        // Check for AND
        case 'a':
          if (i+2 < chars.length && chars[i+1] == 'n' && chars[i+2] == 'd') {
            tokens.add(new BoolToken(BoolTypes.AND));
            i += 2;
            numBinaryGates++;
          }
          else {
            String badStr = stringToParse.substring(i,(stringToParse.length()<i+3)?stringToParse.length():i+3);
            throw new InvalidExpressionException("Unrecognized token \""+badStr+"\"");
          }
          break;
        // Eat Whitespace
        default:
          if (!Character.isWhitespace(chars[i])) {
            String badStr = stringToParse.substring(i,(stringToParse.length()<i+1)?stringToParse.length():i+1);
            throw new InvalidExpressionException("Unrecognized token \""+badStr+"\"");
          }
      }
    }
    // The gates form a tree, so the number of leaves is n-1
    if (numBinaryGates != numInputs-1) {
      throw new InvalidExpressionException("Number of inputs is invalid for number of gates");
    }
    tokens.trimToSize();
    return tokens;
  }
  
}
