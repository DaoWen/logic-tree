/*
 * Constants.java
 *
 * Created on March 12, 2006, 5:11 PM
 *
 * Logic Tree: A logic gate simulation program
 * Copyright (c) 2009 Nick Vrvilo
 * http://ouuuuch.phoenixteam.org/
 * 
 * This program is distributed under the GNU General Public License.
 * For full deltails please see LicenseInfo.txt included with this source code.
 *
 */

package gui;

import java.awt.Color;

/** Stores the constant values used in the GUI
 * @author Nick Vrvilo
 */
public class Const {
  // Static class needs no constructor
  private Const() {};
  
  // Various color constants  
  // I removed "final" from the first 3 so that they could be changed in the Option dialog
  public static Color TRUE_COLOR = Color.BLUE;
  public static Color FALSE_COLOR = Color.RED;
  public static Color NULL_COLOR = Color.LIGHT_GRAY;
  public static final Color OUTLINE_COLOR = Color.BLACK;
  public static final Color CANVAS_COLOR = Color.WHITE;
  
  // Dimensions for Gate display objects
  public static final int GATE_WIDTH = 100, GATE_HEIGHT = 77;
  
  // Dimensions for Input/Output display objects
  public static final int INPUT_WIDTH = 30, INPUT_HEIGHT = 27;
  
  // Offsets for displaying variable names on the canvas
  public static final int TXT_DX = 10;
  public static final int TXT_DY = (int)(INPUT_HEIGHT * 2./3.);
  
  // Number of slices each gate/wire is sliced into for animation
  public static final int ANIM_QUALITY = 50;
      
}
