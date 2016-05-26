/*
 * VarValueEditor.java
 *
 * Created on March 23, 2006, 11:23 AM
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

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.event.*;

import booltree.VarNode;
import java.util.Set;

/** Display dialog for editing the variable input values
 * @author Nick Vrvilo
 */
public class VarValueEditor extends JDialog implements ActionListener, ItemListener {
  
  /** Constants for use with menu item actionCommands */
  private final String OK = "_", CANCEL = "!";
  
  /** Creates a new instance of VarValueEditor
   * @param owner Frame that this dialog is attached to
   */
  public VarValueEditor(JFrame owner, ActionListener canvas) {
    super(owner,"Set Variable Values",true); // 'true' makes this a model dialog
    Container cp = getContentPane();
    Set<String> varNames = VarNode.getVarNames();
    int n = varNames.size();
    JPanel formPanel = new JPanel(new GridLayout(n,1));
    JCheckBox checkBox;
    for (String varName : varNames) {
      checkBox = new JCheckBox("$"+varName,VarNode.getVarVal(varName));
      checkBox.setActionCommand(varName);
      checkBox.addItemListener(this);
      formPanel.add(checkBox);
    }
    JPanel buttonPanel = new JPanel(new FlowLayout());
    JButton okButton = new JButton("OK");
    okButton.setActionCommand(OK);
    okButton.setMnemonic('O');
    okButton.addActionListener(this);
    okButton.addActionListener(canvas);
    buttonPanel.add(okButton);
    JButton cancelButton = new JButton("Cancel");
    cancelButton.setActionCommand(CANCEL);
    cancelButton.setMnemonic('C');
    cancelButton.addActionListener(this);
    buttonPanel.add(cancelButton);
    cp.add(formPanel,BorderLayout.NORTH);
    cp.add(buttonPanel,BorderLayout.SOUTH);
    pack();
    setResizable(false);
    setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
    setLocation(owner.getX()+100,owner.getY()+50);
    VarNode.saveVarValues(); // Back up variable values just in case they decide to cancel
    setVisible(true);
  }
  
  /** Handles the OK/Cancel buttons events */
  public void actionPerformed(ActionEvent e) {
    String actionCommand = e.getActionCommand();
    // Using the == operator is fine here because we're comparing
    // string constants which will have the same memory location
    if (actionCommand == CANCEL) {
      VarNode.restoreVarValues();
    }
     setVisible(false);
     dispose();
  }
  
  /** Handles the checkbox state-change events */
  public void itemStateChanged(ItemEvent e) {
    VarNode.setVarVal(((JCheckBox)e.getSource()).getActionCommand(),e.getStateChange()==ItemEvent.SELECTED);
  }
  
}
