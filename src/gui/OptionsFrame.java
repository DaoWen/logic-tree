/*
 * OptionsFrame.java
 *
 * Created on March 31, 2006, 12:30 PM
 *
 * Logic Tree: A logic gate simulation program
 * Copyright (c) 2009 Nick Vrvilo
 * https://github.com/DaoWen/logic-tree
 * 
 * This program is distributed under the GNU General Public License.
 * For full deltails please see LicenseInfo.txt included with this source code.
 *
 */

package gui;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/** Allows the user to change some basic program options
 * @author Nick Vrvilo
 */
public class OptionsFrame extends JDialog implements ActionListener {
  
  /** Stores temp color values */
  private Color tmpTrue, tmpFalse, tmpNull;
  
  /** Constants for ActionCommands */
  private final char TRUE = 'T', FALSE = 'F', NULL = 'N', OK = 'O', CANCEL = 'C';
  
  /** Creates a new instance of OptionsFrame */
  public OptionsFrame(JFrame owner) {
    super(owner,"Color Options",true); // 'true' makes this a model dialog
    float brightness;
    tmpTrue = Const.TRUE_COLOR;
    tmpFalse = Const.FALSE_COLOR;
    tmpNull = Const.NULL_COLOR;
    Container cp = getContentPane();
    JPanel upperPanel = new JPanel(new FlowLayout());
    JPanel lowerPanel = new JPanel(new FlowLayout());
    // TRUE
    JButton trueButton = new JButton("Set True Color");
    trueButton.setBackground(tmpTrue);
    brightness = getBrightness(tmpTrue);
    trueButton.setForeground(brightness<=.5f?Color.WHITE:Color.BLACK);
    trueButton.setActionCommand(String.valueOf(TRUE));
    trueButton.addActionListener(this);
    upperPanel.add(trueButton);
    // FALSE
    JButton falseButton = new JButton("Set False Color");
    falseButton.setBackground(tmpFalse);
    falseButton.setActionCommand(String.valueOf(FALSE));
    falseButton.addActionListener(this);
    brightness = getBrightness(tmpFalse);
    falseButton.setForeground(brightness<=.5f?Color.WHITE:Color.BLACK);
    upperPanel.add(falseButton);
    // NULL
    JButton nullButton = new JButton("Set Null Color");
    nullButton.setBackground(tmpNull);
    nullButton.setActionCommand(String.valueOf(NULL));
    nullButton.addActionListener(this);
    brightness = getBrightness(tmpNull);
    nullButton.setForeground(brightness<=.5f?Color.WHITE:Color.BLACK);
    upperPanel.add(nullButton);
    // OK
    JButton okButton = new JButton("OK");
    lowerPanel.add(okButton);
    okButton.setActionCommand(String.valueOf(OK));
    okButton.addActionListener(this);
    // CANCEL
    JButton cancelButton = new JButton("Cancel");
    lowerPanel.add(cancelButton);
    cancelButton.setActionCommand(String.valueOf(CANCEL));
    cancelButton.addActionListener(this);
    // window setup
    cp.add(upperPanel,BorderLayout.NORTH);
    cp.add(lowerPanel,BorderLayout.SOUTH);
    setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    pack();
    setSize(trueButton.getWidth() + falseButton.getWidth() + nullButton.getWidth() + 100, getHeight());
    setLocation(owner.getX()+75,owner.getY()+100);
    setVisible(true);
  }
  
  /** Returns the of the brightness of the color
   * @param c Color to retrieve brightness component from
   * @return Decimal value from 0 to 1 representing the brightness
   */
  private float getBrightness(Color c) {
    float[] hsb = new float[3];
    Color.RGBtoHSB(c.getRed(),c.getGreen(),c.getBlue(),hsb);
    return hsb[2];
  }
  
  /** Handles the button events */
  public void actionPerformed(ActionEvent e) {
    Color tempColor;
    float b=0; // hue, saturation, brightness
    JButton btn = (JButton)e.getSource();
    switch (e.getActionCommand().charAt(0)) {
      case TRUE:
        tempColor = JColorChooser.showDialog(this,"Choose True Color",tmpTrue);
        if (tempColor != null) {
          tmpTrue = tempColor;
          btn.setBackground(tempColor);
          b = getBrightness(tempColor);
          btn.setForeground(b<=.5f?Color.WHITE:Color.BLACK);
        }
        break;
      case FALSE:
        tempColor = JColorChooser.showDialog(this,"Choose False Color",tmpFalse);
        if (tempColor != null) {
          tmpFalse = tempColor;
          btn.setBackground(tempColor);
          b = getBrightness(tempColor);
          btn.setForeground(b<=.5f?Color.WHITE:Color.BLACK);
        }
        break;
      case NULL:
        tempColor = JColorChooser.showDialog(this,"Choose Null Color",tmpNull);
        if (tempColor != null) {
          tmpNull = tempColor;
          btn.setBackground(tempColor);
          b = getBrightness(tempColor);
          btn.setForeground(b<=.5f?Color.WHITE:Color.BLACK);
        }
        break;
      case OK:
        Const.TRUE_COLOR = tmpTrue;
        Const.FALSE_COLOR = tmpFalse;
        Const.NULL_COLOR = tmpNull;
        // No break because I want it to fall through to CANCEL
      case CANCEL:
        setVisible(false);
        dispose();
        break;
      default:
        System.err.println("Undefined Command Encountered");
    }
  }
  
}
