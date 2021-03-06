/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sr5CampaignManager.dialogs;

import javax.swing.JOptionPane;
import javax.swing.tree.DefaultTreeModel;

/**
 *
 * @author nbp184
 */
public class StringTreeSelectDialog extends javax.swing.JDialog {

    private static final String[] buttonNames = {"Try Again", "Cancel"};
    
    private String typeName;
    private boolean ok;
    private StringTreeNode selected;
    
    /**
     * Creates new form AugmentationSelectDialog
     */
    public StringTreeSelectDialog(java.awt.Frame parent, boolean modal, StringTreeNode root, String typeName) {
        super(parent, modal);
        initComponents();
        this.setTitle("Add " +typeName);
        treMain.setModel(new DefaultTreeModel(root));
        this.typeName = typeName;
        ok = false;
        selected = null;
    }
    
    public StringTreeSelectDialog(javax.swing.JPanel parent, boolean modal, StringTreeNode root, String typeName) {
        super((java.awt.Frame)null, modal);
        initComponents();
        this.setLocationRelativeTo(parent);
        this.pack();
        this.setTitle("Add " +typeName);
        treMain.setModel(new DefaultTreeModel(root));
        this.typeName = typeName;
        ok = false;
        selected = null;
    }
    
    public boolean somethingSelected() {
        return ok;
    }
    
    public String getSelectedValue() {
        return selected.getValue();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        treMain = new javax.swing.JTree();
        btnAdd = new javax.swing.JButton();
        btnCancel = new javax.swing.JButton();

        setTitle("Add Augmentation");

        jScrollPane1.setViewportView(treMain);

        btnAdd.setText("Add");
        btnAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddActionPerformed(evt);
            }
        });

        btnCancel.setText("Cancel");
        btnCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnAdd)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 264, Short.MAX_VALUE)
                        .addComponent(btnCancel)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 275, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnAdd)
                    .addComponent(btnCancel))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddActionPerformed
        selected = (StringTreeNode)treMain.getSelectionPath().getLastPathComponent();
        if(!selected.isLeaf()) {
            int result = JOptionPane.showOptionDialog(this, "You cannot add an " +typeName.toLowerCase() +" category.", "Error Adding " +typeName, JOptionPane.YES_NO_OPTION, JOptionPane.ERROR_MESSAGE, null, buttonNames, buttonNames[0]);
            if(result == JOptionPane.NO_OPTION) {
                this.setVisible(false);
            }
        } else {
            ok = true;
            this.setVisible(false);
        }
    }//GEN-LAST:event_btnAddActionPerformed

    private void btnCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelActionPerformed
        this.setVisible(false);
    }//GEN-LAST:event_btnCancelActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAdd;
    private javax.swing.JButton btnCancel;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTree treMain;
    // End of variables declaration//GEN-END:variables
}
