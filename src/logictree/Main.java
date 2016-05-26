/*
 * Main.java
 *
 * Created on January 1, 2006, 7:33 PM
 *
 * Logic Tree: A logic gate simulation program
 * Copyright (c) 2009 Nick Vrvilo
 * http://ouuuuch.phoenixteam.org/
 * 
 * This program is distributed under the GNU General Public License.
 * For full deltails please see LicenseInfo.txt included with this source code.
 *
 */

package logictree;

import gui.MainFrame;
import javax.swing.JOptionPane;

/** Class for launching the application
 * @author Nick Vrvilo
 */
public class Main {
  
  /** Simply creates a new gui.MainFrame object
   * @param args the command line arguments
   */
  public static void main(String[] args) {
    // Check version
    // Versions lower than 1.5 don't support the Enum datatype
    if (Double.parseDouble(System.getProperty("java.version").substring(0,3)) < 1.5) {
      JOptionPane.showMessageDialog(null,"An error occurred while attempting to execute the program.\nPlease visit www.java.com to get the latest version of the Java Runtime Environment.","Error",JOptionPane.ERROR_MESSAGE);
      System.exit(1);
    }
    MainFrame myFrame = new MainFrame();
  }
  
}
