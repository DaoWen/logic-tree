/*
 * BoolTypes.java
 *
 * Created on December 22, 2005, 10:28 PM
 *
 * Logic Tree: A logic gate simulation program
 * Copyright (c) 2009 Nick Vrvilo
 * http://ouuuuch.phoenixteam.org/
 * 
 * This program is distributed under the GNU General Public License.
 * For full deltails please see LicenseInfo.txt included with this source code.
 *
 */

package boolparser;

/** Enumeration of the different token-types for a parsed boolean-expression
 * @author Nick Vrvilo
 */
public enum BoolTypes {
  NXOR, NOR, NAND, XOR, OR, AND, NOT, TRUE, FALSE, VAR, LPAREN, RPAREN, OUTPUT
}
