/*
 * AboutFrame.java
 *
 * Created on March 28, 2006, 7:05 PM
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
import java.io.*;
import javax.swing.*;

/** Displays the version/license info for the program
 * @author Nick Vrvilo
 */
public class AboutFrame extends JDialog implements MouseListener, ActionListener {

  /** Creates a new instance of AboutFrame and displays it */
  public AboutFrame(JFrame owner) {
    super(owner,"About Logic Tree",true); // 'true' makes this a model dialog
    Container cp = getContentPane();
    cp.setLayout(new BorderLayout());
    JPanel topPanel = new JPanel(new BorderLayout());
    JLabel label1 = new JLabel("<html><br>&nbsp;&nbsp;Logic Tree v1.0.3<br>&nbsp;&nbsp;Copyright &copy; 2009 Nick Vrvilo</html>");
    JLabel label2 = new JLabel("<html><font color=\"blue\">&nbsp;&nbsp;<u>https://github.com/DaoWen/logic-tree</u></font></html>");
    label2.addMouseListener(this);
    label2.setCursor(new Cursor(Cursor.HAND_CURSOR));
    JLabel label3 = new JLabel("<html><br>&nbsp;&nbsp;This program is distributed under the GNU General Public License (Version 2):</html>");
    JTextArea licenseDisplay = new JTextArea("GNU GPL v2",10,78);
    try {
      InputStream licenseFile = AboutFrame.class.getResourceAsStream("/GNU GPL V2.txt");
      licenseDisplay.read(new InputStreamReader(licenseFile), "GPLv2");
    }
    catch (IOException ex) {
      System.err.printf("Error opening license file:%n%s", ex);
    }
    licenseDisplay.setFont(new Font("Monospaced",Font.PLAIN,12));
    licenseDisplay.setEnabled(false);
    licenseDisplay.setDisabledTextColor(Color.BLACK);
    JScrollPane scroller = new JScrollPane(licenseDisplay);
    JButton closeButton = new JButton("OK");
    closeButton.addActionListener(this);
    topPanel.add(label1,BorderLayout.NORTH);
    topPanel.add(label2,BorderLayout.CENTER);
    topPanel.add(label3,BorderLayout.SOUTH);
    cp.add(topPanel,BorderLayout.NORTH);
    cp.add(scroller,BorderLayout.CENTER);
    JPanel bottomPanel = new JPanel(new FlowLayout());
    bottomPanel.add(closeButton);
    cp.add(bottomPanel,BorderLayout.SOUTH);
    setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    pack();
    setLocation(owner.getX()+75,owner.getY()+100);
    setVisible(true);
  }

  /** Handles the OK button events */
  public void actionPerformed(ActionEvent e) {
    setVisible(false);
    dispose();
  }

  /** Used to launch the website link when clicked */
  public void mouseClicked(MouseEvent e) {
    BrowserControl.displayURL("https://github.com/DaoWen/logic-tree");
  }

  /** Unused */
  public void mousePressed(MouseEvent e) {}
  /** Unused */
  public void mouseReleased(MouseEvent e) {}
  /** Unused */
  public void mouseEntered(MouseEvent e) {}
  /** Unused */
  public void mouseExited(MouseEvent e) {}

}
