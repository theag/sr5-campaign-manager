/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sr5CampaignManager.dialogs;

import sr5CampaignManager.objects.Weapon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JSeparator;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;
import sr5CampaignManager.Resources;

/**
 *
 * @author nbp184
 */
public class WeaponEditDialog extends javax.swing.JDialog {

    private Resources r;
    
    /**
     * Creates new form WeaponEditDialog
     */
    public WeaponEditDialog(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        
        rbRanged.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                btnGrpRangedMeleeActionPerformed(e);
            }
        });
        rbMelee.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                btnGrpRangedMeleeActionPerformed(e);
            }
        });
        
        r = Resources.get();
                
        createPopupMenus();
        
        btnEdit.setVisible(false);
        
        rbRanged.doClick();
    }
    
    // <editor-fold defaultstate="collapsed" desc="Popup Menu Actions">
    private void miEditCategoryActionPerformed(ActionEvent e) {
        WeapTreeNode node = (WeapTreeNode)treWeapons.getSelectionPath().getLastPathComponent();
        String input = (String)JOptionPane.showInputDialog(this, "Name of category to edit", "Edit Category", JOptionPane.QUESTION_MESSAGE, null, null, node.getName());
        if(input != null) {
            node.setValue(input);
            DefaultTreeModel model = (DefaultTreeModel)treWeapons.getModel();
            model.reload(node);
        }
        treWeapons.setSelectionRow(-1);
    }
    
    private void miAddCategoryActionPerformed(ActionEvent e) {
        String input = JOptionPane.showInputDialog(this, "Name of category to add", "Add Category", JOptionPane.QUESTION_MESSAGE);
        if(input != null) {
            WeapTreeNode node = (WeapTreeNode)treWeapons.getSelectionPath().getLastPathComponent();
            node.addChild(new WeapTreeNode(input, true));
            DefaultTreeModel model = (DefaultTreeModel)treWeapons.getModel();
            model.reload(node);
        }
        treWeapons.setSelectionRow(-1);
    }
    
    private void miMoveUpActionPerformed(ActionEvent e) {
        WeapTreeNode node = (WeapTreeNode)treWeapons.getSelectionPath().getLastPathComponent();
        WeapTreeNode parent = (WeapTreeNode)node.getParent();
        parent.moveChildUp(parent.getIndex(node));
        DefaultTreeModel model = (DefaultTreeModel)treWeapons.getModel();
        model.reload(parent);
        treWeapons.setSelectionRow(-1);
    }
    
    private void miMoveDownActionPerformed(ActionEvent e) {
        WeapTreeNode node = (WeapTreeNode)treWeapons.getSelectionPath().getLastPathComponent();
        WeapTreeNode parent = (WeapTreeNode)node.getParent();
        parent.moveChildDown(parent.getIndex(node));
        DefaultTreeModel model = (DefaultTreeModel)treWeapons.getModel();
        model.reload(parent);
        treWeapons.setSelectionRow(-1);
    }
    
    private void miMoveOutActionPerformed(ActionEvent e) {
        WeapTreeNode node = (WeapTreeNode)treWeapons.getSelectionPath().getLastPathComponent();
        node.moveOut();
        WeapTreeNode parent = (WeapTreeNode)node.getParent();
        DefaultTreeModel model = (DefaultTreeModel)treWeapons.getModel();
        model.reload(parent);
        treWeapons.setSelectionRow(-1);
    }
    
    private void miMoveInActionPerformed(ActionEvent e) {
        WeapTreeNode node = (WeapTreeNode)treWeapons.getSelectionPath().getLastPathComponent();
        WeapTreeNode parent = (WeapTreeNode)node.getParent();
        WeapTreeNode[] cats = parent.getSubCategoriesWithoutNode(node);
        WeapTreeNode result = (WeapTreeNode)JOptionPane.showInputDialog(this, "Select a category to move " +node +" into.", "Move In", JOptionPane.QUESTION_MESSAGE, null, cats, cats[0]);
        if(result != null) {
            parent.removeChild(node);
            result.addChild(node);
            DefaultTreeModel model = (DefaultTreeModel)treWeapons.getModel();
            model.reload(parent);
        }
        treWeapons.setSelectionRow(-1);
    }
    
    private void miDeleteActionPerformed(ActionEvent e) {
        WeapTreeNode node = (WeapTreeNode)treWeapons.getSelectionPath().getLastPathComponent();
        WeapTreeNode parent = (WeapTreeNode)node.getParent();
        if(node.isLeaf()) {
            parent.removeChild(node);
            DefaultTreeModel model = (DefaultTreeModel)treWeapons.getModel();
            model.reload(parent);
        } else {
            int result = JOptionPane.showConfirmDialog(this, "Deleting a category deletes everything within that category.\nAre you sure you wish to proceed?", "Confirm Delete", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
            if(result == JOptionPane.YES_OPTION) {
                parent.removeChild(node);
                DefaultTreeModel model = (DefaultTreeModel)treWeapons.getModel();
                model.reload(parent);
            }
        }
        treWeapons.setSelectionRow(-1);
    }

    private void miDeleteAllActionPerformed(ActionEvent e) {
        WeapTreeNode node = (WeapTreeNode)treWeapons.getSelectionPath().getLastPathComponent();
        int result = JOptionPane.showConfirmDialog(this, "This will delete everything inside this category.\nAre you sure you wish to proceed?", "Confirm Delete", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        if(result == JOptionPane.YES_OPTION) {
            node.removeAllEditableChildren();
            DefaultTreeModel model = (DefaultTreeModel)treWeapons.getModel();
            model.reload(node);
        }
        treWeapons.setSelectionRow(-1);
    }// </editor-fold>

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        popupLeaf = new javax.swing.JPopupMenu();
        popupCat = new javax.swing.JPopupMenu();
        popupFixedCat = new javax.swing.JPopupMenu();
        btnGrpRangedMelee = new javax.swing.ButtonGroup();
        jLabel1 = new javax.swing.JLabel();
        txtName = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        txtType = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        txtDamage = new javax.swing.JTextField();
        lblAccReach = new javax.swing.JLabel();
        txtAccReach = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        txtAP = new javax.swing.JTextField();
        btnAdd = new javax.swing.JButton();
        btnEdit = new javax.swing.JButton();
        rbRanged = new javax.swing.JRadioButton();
        rbMelee = new javax.swing.JRadioButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        treWeapons = new javax.swing.JTree();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Organize Weapons");

        jLabel1.setText("Name");

        txtName.setColumns(40);

        jLabel2.setText("Type");

        txtType.setColumns(10);

        jLabel3.setText("Damage");

        txtDamage.setColumns(10);

        lblAccReach.setText("Accuracy");

        txtAccReach.setColumns(3);

        jLabel4.setText("AP");

        txtAP.setColumns(3);

        btnAdd.setText("Add");
        btnAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddActionPerformed(evt);
            }
        });

        btnEdit.setText("Edit");
        btnEdit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditActionPerformed(evt);
            }
        });

        btnGrpRangedMelee.add(rbRanged);
        rbRanged.setText("Ranged");

        btnGrpRangedMelee.add(rbMelee);
        rbMelee.setText("Melee");

        treWeapons.setToolTipText("");
        treWeapons.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                treWeaponsMouseReleased(evt);
            }
        });
        treWeapons.addTreeSelectionListener(new javax.swing.event.TreeSelectionListener() {
            public void valueChanged(javax.swing.event.TreeSelectionEvent evt) {
                treWeaponsValueChanged(evt);
            }
        });
        jScrollPane1.setViewportView(treWeapons);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtType, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtDamage, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(lblAccReach)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtAccReach, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel4)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtAP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(btnAdd)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnEdit))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(rbRanged)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(rbMelee)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rbRanged)
                    .addComponent(rbMelee))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 322, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2)
                    .addComponent(txtType, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txtDamage, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblAccReach)
                    .addComponent(txtAccReach, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4)
                    .addComponent(txtAP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnAdd)
                    .addComponent(btnEdit))
                .addGap(0, 10, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddActionPerformed
        Weapon weap = new Weapon(1, true, rbRanged.isSelected());
        weap.name = txtName.getText();
        weap.type = txtType.getText();
        weap.damage = txtDamage.getText();
        weap.accuracyReach = txtAccReach.getText();
        weap.ap = txtAP.getText();
        TreePath path = treWeapons.getSelectionPath();
        WeapTreeNode parent;
        if(path != null) {
            parent = (WeapTreeNode)path.getLastPathComponent();
        } else {
            parent = (WeapTreeNode)treWeapons.getModel().getRoot();
        }
        parent.addChild(new WeapTreeNode(weap, true));
        DefaultTreeModel model = (DefaultTreeModel)treWeapons.getModel();
        model.reload(parent);
        txtName.setText("");
        txtType.setText("");
        txtDamage.setText("");
        txtAccReach.setText("");
        txtAP.setText("");
    }//GEN-LAST:event_btnAddActionPerformed

    private void btnEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditActionPerformed
        WeapTreeNode node = (WeapTreeNode)treWeapons.getSelectionPath().getLastPathComponent();
        Weapon weap = node.getWeapon();
        weap.name = txtName.getText();
        weap.type = txtType.getText();
        weap.damage = txtDamage.getText();
        weap.accuracyReach = txtAccReach.getText();
        weap.ap = txtAP.getText();
        DefaultTreeModel model = (DefaultTreeModel)treWeapons.getModel();
        model.reload(node);
        treWeapons.setSelectionRow(-1);
    }//GEN-LAST:event_btnEditActionPerformed

    private void treWeaponsMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_treWeaponsMouseReleased
        if(evt.isPopupTrigger()) {
            TreePath path = treWeapons.getPathForLocation(evt.getX(), evt.getY());
            if(path != null) {
                treWeapons.setSelectionRow(treWeapons.getRowForLocation(evt.getX(), evt.getY()));
                WeapTreeNode lastPathComponent = (WeapTreeNode)path.getLastPathComponent();
                if(lastPathComponent.isEditable()) {
                    if(lastPathComponent.isLeaf()) {
                        popupLeaf.show(treWeapons, evt.getX(), evt.getY());
                    } else {
                        popupCat.show(treWeapons, evt.getX(), evt.getY());
                    }
                } else {
                    popupFixedCat.show(treWeapons, evt.getX(), evt.getY());
                }
            }
        }
    }//GEN-LAST:event_treWeaponsMouseReleased

    private void treWeaponsValueChanged(javax.swing.event.TreeSelectionEvent evt) {//GEN-FIRST:event_treWeaponsValueChanged
        TreePath path = treWeapons.getSelectionPath();
        if(path != null) {
            WeapTreeNode node = (WeapTreeNode)path.getLastPathComponent();
            if(node.isLeaf()) {
                Weapon weap = node.getWeapon();
                txtName.setText(weap.name);
                txtType.setText(weap.type);
                txtDamage.setText(weap.damage);
                txtAccReach.setText(weap.accuracyReach);
                txtAP.setText(weap.ap);
                btnAdd.setVisible(false);
                btnEdit.setVisible(true);
            } else {
                txtName.setText("");
                txtType.setText("");
                txtDamage.setText("");
                txtAccReach.setText("");
                txtAP.setText("");
                btnAdd.setVisible(true);
                btnEdit.setVisible(false);
            }
        } else {
            txtName.setText("");
            txtType.setText("");
            txtDamage.setText("");
            txtAccReach.setText("");
            txtAP.setText("");
            btnAdd.setVisible(true);
            btnEdit.setVisible(false);
        }
    }//GEN-LAST:event_treWeaponsValueChanged

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAdd;
    private javax.swing.JButton btnEdit;
    private javax.swing.ButtonGroup btnGrpRangedMelee;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblAccReach;
    private javax.swing.JPopupMenu popupCat;
    private javax.swing.JPopupMenu popupFixedCat;
    private javax.swing.JPopupMenu popupLeaf;
    private javax.swing.JRadioButton rbMelee;
    private javax.swing.JRadioButton rbRanged;
    private javax.swing.JTree treWeapons;
    private javax.swing.JTextField txtAP;
    private javax.swing.JTextField txtAccReach;
    private javax.swing.JTextField txtDamage;
    private javax.swing.JTextField txtName;
    private javax.swing.JTextField txtType;
    // End of variables declaration//GEN-END:variables

    public void showDialog() {
        this.setLocationRelativeTo(this.getParent());
        this.pack();
        DefaultTreeModel model = (DefaultTreeModel)treWeapons.getModel();
        model.reload();
        this.setVisible(true);
    }
    
    private void btnGrpRangedMeleeActionPerformed(ActionEvent e) {
        DefaultTreeModel model;
        if(rbRanged.isSelected()) {
            model = new DefaultTreeModel(r.getRangedWeapons());
            lblAccReach.setText("Accuracy");
        } else {
            model = new DefaultTreeModel(r.getMeleeWeapons());
            lblAccReach.setText("Reach");
        }
        treWeapons.setModel(model);
        model.reload();
        txtName.setText("");
        txtType.setText("");
        txtDamage.setText("");
        txtAccReach.setText("");
        txtAP.setText("");
        btnAdd.setVisible(true);
        btnEdit.setVisible(false);
    }
    
    private void createPopupMenus() {
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
        
        mi = new JMenuItem("Edit");
        mi.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                miEditCategoryActionPerformed(e);
            }
        });
        popupCat.add(mi);
        mi = new JMenuItem("Add Category");
        mi.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                miAddCategoryActionPerformed(e);
            }
        });
        popupCat.add(mi);
        popupCat.add(new JSeparator());
        mi = new JMenuItem("Move Up");
        mi.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                miMoveUpActionPerformed(e);
            }
        });
        popupCat.add(mi);
        mi = new JMenuItem("Move Down");
        mi.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                miMoveDownActionPerformed(e);
            }
        });
        popupCat.add(mi);
        mi = new JMenuItem("Move Out");
        mi.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                miMoveOutActionPerformed(e);
            }
        });
        popupCat.add(mi);
        mi = new JMenuItem("Move In");
        mi.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                miMoveInActionPerformed(e);
            }
        });
        popupCat.add(mi);
        popupCat.add(new JSeparator());
        mi = new JMenuItem("Delete");
        mi.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                miDeleteActionPerformed(e);
            }
        });
        popupCat.add(mi);
        popupCat.add(new JSeparator());
        mi = new JMenuItem("Delete All Children");
        mi.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                miDeleteAllActionPerformed(e);
            }
        });
        popupCat.add(mi);
        
        mi = new JMenuItem("Add Category");
        mi.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                miAddCategoryActionPerformed(e);
            }
        });
        popupFixedCat.add(mi);
        popupFixedCat.add(new JSeparator());
        mi = new JMenuItem("Delete All Children");
        mi.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                miDeleteAllActionPerformed(e);
            }
        });
        popupFixedCat.add(mi);
    }

}
