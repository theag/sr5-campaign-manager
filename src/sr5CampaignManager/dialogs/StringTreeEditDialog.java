/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sr5CampaignManager.dialogs;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JSeparator;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;

/**
 *
 * @author nbp184
 */
public class StringTreeEditDialog extends javax.swing.JDialog {
    
    private String typeName;
    
    /**
     * Creates new form AugmentationDialog
     */
    public StringTreeEditDialog(java.awt.Frame parent, boolean modal, StringTreeNode root, String typeName) {
        super(parent, modal);
        initComponents();
        this.typeName = typeName;
        this.setTitle("Organize " +typeName +"s");
        
        DefaultTreeModel model = new DefaultTreeModel(root);
        treMain.setModel(model);
        JMenuItem mi = new JMenuItem("Move Up");
        mi.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                miMoveUpActionPerformed(e);
            }
        });
        popupLeaf.add(mi);
        mi = new JMenuItem("Move Down");
        mi.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                miMoveDownActionPerformed(e);
            }
        });
        popupLeaf.add(mi);
        mi = new JMenuItem("Move Out");
        mi.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                miMoveOutActionPerformed(e);
            }
        });
        popupLeaf.add(mi);
        mi = new JMenuItem("Move In");
        mi.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                miMoveInActionPerformed(e);
            }
        });
        popupLeaf.add(mi);
        popupLeaf.add(new JSeparator());
        mi = new JMenuItem("Delete");
        mi.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                miDeleteActionPerformed(e);
            }
        });
        popupLeaf.add(mi);
        
        mi = new JMenuItem("Add Category");
        mi.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                miAddCategoryActionPerformed(e);
            }
        });
        popupCategory.add(mi);
        mi = new JMenuItem("Add " +typeName);
        mi.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                miAddStringActionPerformed(e);
            }
        });
        popupCategory.add(mi);
        popupCategory.add(new JSeparator());
        mi = new JMenuItem("Move Up");
        mi.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                miMoveUpActionPerformed(e);
            }
        });
        popupCategory.add(mi);
        mi = new JMenuItem("Move Down");
        mi.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                miMoveDownActionPerformed(e);
            }
        });
        popupCategory.add(mi);
        mi = new JMenuItem("Move Out");
        mi.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                miMoveOutActionPerformed(e);
            }
        });
        popupCategory.add(mi);
        mi = new JMenuItem("Move In");
        mi.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                miMoveInActionPerformed(e);
            }
        });
        popupCategory.add(mi);
        popupCategory.add(new JSeparator());
        mi = new JMenuItem("Delete");
        mi.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                miDeleteActionPerformed(e);
            }
        });
        popupCategory.add(mi);
        popupCategory.add(new JSeparator());
        mi = new JMenuItem("Delete All Children");
        mi.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                miDeleteAllActionPerformed(e);
            }
        });
        popupCategory.add(mi);
        
        mi = new JMenuItem("Add Category");
        mi.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                miAddCategoryActionPerformed(e);
            }
        });
        popupFixedCategory.add(mi);
        mi = new JMenuItem("Add " +typeName);
        mi.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                miAddStringActionPerformed(e);
            }
        });
        popupFixedCategory.add(mi);
        popupFixedCategory.add(new JSeparator());
        mi = new JMenuItem("Delete All Children");
        mi.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                miDeleteAllActionPerformed(e);
            }
        });
        popupFixedCategory.add(mi);
        
        btnEdit.setEnabled(false);
    }
    
    private void miAddCategoryActionPerformed(ActionEvent e) {
        String input = JOptionPane.showInputDialog(this, "Name of category to add", "Add Category", JOptionPane.QUESTION_MESSAGE);
        if(input != null) {
            StringTreeNode node = (StringTreeNode)treMain.getSelectionPath().getLastPathComponent();
            node.addChild(new StringTreeNode(input, false));
            DefaultTreeModel model = (DefaultTreeModel)treMain.getModel();
            model.reload(node);
        }
        treMain.setSelectionRow(-1);
    }
    
    private void miAddStringActionPerformed(ActionEvent e) {
        String input = JOptionPane.showInputDialog(this, "Name of " +typeName.toLowerCase() +" to add", "Add " +typeName, JOptionPane.QUESTION_MESSAGE);
        if(input != null) {
            StringTreeNode node = (StringTreeNode)treMain.getSelectionPath().getLastPathComponent();
            node.addChild(new StringTreeNode(input, true));
            DefaultTreeModel model = (DefaultTreeModel)treMain.getModel();
            model.reload(node);
        }
        treMain.setSelectionRow(-1);
    }
    
    private void miMoveUpActionPerformed(ActionEvent e) {
        StringTreeNode node = (StringTreeNode)treMain.getSelectionPath().getLastPathComponent();
        StringTreeNode parent = (StringTreeNode)node.getParent();
        parent.moveChildUp(parent.getIndex(node));
        DefaultTreeModel model = (DefaultTreeModel)treMain.getModel();
        model.reload(parent);
        treMain.setSelectionRow(-1);
    }
    
    private void miMoveDownActionPerformed(ActionEvent e) {
        StringTreeNode node = (StringTreeNode)treMain.getSelectionPath().getLastPathComponent();
        StringTreeNode parent = (StringTreeNode)node.getParent();
        parent.moveChildDown(parent.getIndex(node));
        DefaultTreeModel model = (DefaultTreeModel)treMain.getModel();
        model.reload(parent);
        treMain.setSelectionRow(-1);
    }
    
    private void miMoveOutActionPerformed(ActionEvent e) {
        StringTreeNode node = (StringTreeNode)treMain.getSelectionPath().getLastPathComponent();
        node.moveOut();
        StringTreeNode parent = (StringTreeNode)node.getParent();
        DefaultTreeModel model = (DefaultTreeModel)treMain.getModel();
        model.reload(parent);
        treMain.setSelectionRow(-1);
    }
    
    private void miMoveInActionPerformed(ActionEvent e) {
        StringTreeNode node = (StringTreeNode)treMain.getSelectionPath().getLastPathComponent();
        StringTreeNode parent = (StringTreeNode)node.getParent();
        StringTreeNode[] cats = parent.getSubCategoriesWithoutNode(node);
        StringTreeNode result = (StringTreeNode)JOptionPane.showInputDialog(this, "Select a category to move " +node +" into.", "Move In", JOptionPane.QUESTION_MESSAGE, null, cats, cats[0]);
        if(result != null) {
            parent.removeChild(node);
            result.addChild(node);
            DefaultTreeModel model = (DefaultTreeModel)treMain.getModel();
            model.reload(parent);
            //model.reload(result);
        }
        treMain.setSelectionRow(-1);
    }
    
    private void miDeleteActionPerformed(ActionEvent e) {
        StringTreeNode node = (StringTreeNode)treMain.getSelectionPath().getLastPathComponent();
        StringTreeNode parent = (StringTreeNode)node.getParent();
        if(node.isLeaf()) {
            parent.removeChild(node);
            DefaultTreeModel model = (DefaultTreeModel)treMain.getModel();
            model.reload(parent);
        } else {
            int result = JOptionPane.showConfirmDialog(this, "Deleting a category deletes everything within that category.\nAre you sure you wish to proceed?", "Confirm Delete", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
            if(result == JOptionPane.YES_OPTION) {
                parent.removeChild(node);
                DefaultTreeModel model = (DefaultTreeModel)treMain.getModel();
                model.reload(parent);
            }
        }
        treMain.setSelectionRow(-1);
    }
    
    private void miDeleteAllActionPerformed(ActionEvent e) {
        StringTreeNode node = (StringTreeNode)treMain.getSelectionPath().getLastPathComponent();
        int result = JOptionPane.showConfirmDialog(this, "This will delete everything inside this category.\nAre you sure you wish to proceed?", "Confirm Delete", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        if(result == JOptionPane.YES_OPTION) {
            node.removeAllEditableChildren();
            DefaultTreeModel model = (DefaultTreeModel)treMain.getModel();
            model.reload(node);
        }
        treMain.setSelectionRow(-1);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        popupLeaf = new javax.swing.JPopupMenu();
        popupCategory = new javax.swing.JPopupMenu();
        popupFixedCategory = new javax.swing.JPopupMenu();
        jScrollPane1 = new javax.swing.JScrollPane();
        treMain = new javax.swing.JTree();
        txtEdit = new javax.swing.JTextField();
        btnEdit = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Organize Augmentations");

        treMain.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                treMainMouseReleased(evt);
            }
        });
        treMain.addTreeSelectionListener(new javax.swing.event.TreeSelectionListener() {
            public void valueChanged(javax.swing.event.TreeSelectionEvent evt) {
                treMainValueChanged(evt);
            }
        });
        jScrollPane1.setViewportView(treMain);

        btnEdit.setText("Edit");
        btnEdit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 264, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(txtEdit)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnEdit)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 275, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtEdit, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnEdit))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void treMainMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_treMainMouseReleased
        if(evt.isPopupTrigger()) {
            TreePath path = treMain.getPathForLocation(evt.getX(), evt.getY());
            if(path != null) {
                treMain.setSelectionRow(treMain.getRowForLocation(evt.getX(), evt.getY()));
                StringTreeNode lastPathComponent = (StringTreeNode)path.getLastPathComponent();
                if(lastPathComponent.isEditable()) {
                    if(lastPathComponent.isLeaf()) {
                        popupLeaf.show(treMain, evt.getX(), evt.getY());
                    } else {
                        popupCategory.show(treMain, evt.getX(), evt.getY());
                    }
                } else {
                    popupFixedCategory.show(treMain, evt.getX(), evt.getY());
                }
            }
        }
    }//GEN-LAST:event_treMainMouseReleased

    private void treMainValueChanged(javax.swing.event.TreeSelectionEvent evt) {//GEN-FIRST:event_treMainValueChanged
        TreePath path = treMain.getSelectionPath();
        if(path != null) {
            StringTreeNode node = (StringTreeNode)path.getLastPathComponent();
            if(node.isEditable()) {
                txtEdit.setText(node.getValue());
                btnEdit.setEnabled(true);
            } else {
                txtEdit.setText("");
                btnEdit.setEnabled(false);
            }
        } else {
            txtEdit.setText("");
            btnEdit.setEnabled(false);
        }
    }//GEN-LAST:event_treMainValueChanged

    private void btnEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditActionPerformed
        TreePath path = treMain.getSelectionPath();
        StringTreeNode node = (StringTreeNode)path.getLastPathComponent();
        node.setValue(txtEdit.getText());
        DefaultTreeModel model = (DefaultTreeModel)treMain.getModel();
        model.reload(node);
    }//GEN-LAST:event_btnEditActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnEdit;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JPopupMenu popupCategory;
    private javax.swing.JPopupMenu popupFixedCategory;
    private javax.swing.JPopupMenu popupLeaf;
    private javax.swing.JTree treMain;
    private javax.swing.JTextField txtEdit;
    // End of variables declaration//GEN-END:variables

    public void showDialog() {
        this.setLocationRelativeTo(this.getParent());
        this.pack();
        DefaultTreeModel model = (DefaultTreeModel)treMain.getModel();
        model.reload();
        this.setVisible(true);
    }
}
