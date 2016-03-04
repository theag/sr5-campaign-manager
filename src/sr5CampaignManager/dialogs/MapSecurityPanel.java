/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sr5CampaignManager.dialogs;

import sr5CampaignManager.models.SpinnerFloatModel;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import sr5CampaignManager.models.LineWidthComboBoxModel;
import sr5CampaignManager.models.MyComboBoxCellRenderer;
import sr5CampaignManager.models.NamedColourComboBoxModel;
import sr5CampaignManager.objects.Map;
import sr5CampaignManager.objects.maps.MapSecurity;
import sr5CampaignManager.objects.maps.NamedColour;
import sr5CampaignManager.events.MapChangeEvent;

/**
 *
 * @author nbp184
 */
public class MapSecurityPanel extends MapDrawingObjectPanel<MapSecurity> {

    private MapSecurity mapSecurity;
    
    /**
     * Creates new form MapSecurityPanel
     */
    public MapSecurityPanel(Map map) {
        initComponents();
        setBorder(null);
        mapSecurity = null;
        cbSecColour.setModel(new NamedColourComboBoxModel(map));
        cbSecColour.setRenderer(new MyComboBoxCellRenderer(MyComboBoxCellRenderer.NAMED_COLOUR));
        cbSecColour.setSelectedItem(MapSecurity.securityColour);
        
        cbColour.setModel(new NamedColourComboBoxModel(map));
        cbColour.setRenderer(new MyComboBoxCellRenderer(MyComboBoxCellRenderer.NAMED_COLOUR));
        cbColour.setSelectedIndex(0);
        
        cbLineWidth.setModel(new LineWidthComboBoxModel());
        cbLineWidth.setRenderer(new MyComboBoxCellRenderer(MyComboBoxCellRenderer.LINE_WIDTH));
        cbLineWidth.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if(e.getStateChange() == ItemEvent.SELECTED) {
                    cbLineWidthItemSelected();
                }
            }
        });
        
        cbDevices.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if(e.getStateChange() == ItemEvent.SELECTED) {
                    cbDevicesItemSelected();
                }
            }
        });
        lblLineWidth.setVisible(false);
        cbLineWidth.setVisible(false);
        lblX2.setVisible(false);
        spnX2.setVisible(false);
        lblY2.setVisible(false);
        spnY2.setVisible(false);
        
        spnX1.setModel(new SpinnerFloatModel(0f, 0f, map.width*1f, 0.5f));
        spnY1.setModel(new SpinnerFloatModel(0f, 0f, map.height*1f, 0.5f));
        spnX2.setModel(new SpinnerFloatModel(0f, 0f, map.width*1f, 0.5f));
        spnY2.setModel(new SpinnerFloatModel(0f, 0f, map.height*1f, 0.5f));
        
        rbDetection.doClick();
        btnRemove.setVisible(false);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        jLabel5 = new javax.swing.JLabel();
        cbSecColour = new javax.swing.JComboBox();
        pnlLine = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        spnX1 = new javax.swing.JSpinner();
        jLabel2 = new javax.swing.JLabel();
        btnAddUpdate = new javax.swing.JButton();
        btnRemove = new javax.swing.JButton();
        btnCancel = new javax.swing.JButton();
        rbDetection = new javax.swing.JRadioButton();
        rbLocks = new javax.swing.JRadioButton();
        cbDevices = new javax.swing.JComboBox();
        lblLineWidth = new javax.swing.JLabel();
        cbLineWidth = new javax.swing.JComboBox();
        lblX2 = new javax.swing.JLabel();
        spnX2 = new javax.swing.JSpinner();
        lblY2 = new javax.swing.JLabel();
        spnY2 = new javax.swing.JSpinner();
        lblColour = new javax.swing.JLabel();
        cbColour = new javax.swing.JComboBox();
        spnY1 = new javax.swing.JSpinner();
        spnLayer = new javax.swing.JSpinner();
        jLabel8 = new javax.swing.JLabel();

        jLabel5.setText("Security Colour");

        cbSecColour.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cbSecColour.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbSecColourActionPerformed(evt);
            }
        });

        pnlLine.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel1.setText("X");

        jLabel2.setText("Y");

        btnAddUpdate.setText("Add");
        btnAddUpdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddUpdateActionPerformed(evt);
            }
        });

        btnRemove.setText("Remove");
        btnRemove.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRemoveActionPerformed(evt);
            }
        });

        btnCancel.setText("Cancel");
        btnCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelActionPerformed(evt);
            }
        });

        buttonGroup1.add(rbDetection);
        rbDetection.setText("Detection");
        rbDetection.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbDetectionActionPerformed(evt);
            }
        });

        buttonGroup1.add(rbLocks);
        rbLocks.setText("Locks");
        rbLocks.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbLocksActionPerformed(evt);
            }
        });

        cbDevices.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        lblLineWidth.setText("Line Width");

        cbLineWidth.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        lblX2.setText("X2");

        lblY2.setText("Y2");

        lblColour.setText("Colour");

        cbColour.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel8.setText("Layer");

        javax.swing.GroupLayout pnlLineLayout = new javax.swing.GroupLayout(pnlLine);
        pnlLine.setLayout(pnlLineLayout);
        pnlLineLayout.setHorizontalGroup(
            pnlLineLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlLineLayout.createSequentialGroup()
                .addComponent(rbDetection)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(rbLocks)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(pnlLineLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlLineLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlLineLayout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(spnX1, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(spnY1, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(cbDevices, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(pnlLineLayout.createSequentialGroup()
                        .addComponent(lblX2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(spnX2, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblY2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(spnY2, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pnlLineLayout.createSequentialGroup()
                        .addComponent(btnAddUpdate)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnRemove)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnCancel))
                    .addGroup(pnlLineLayout.createSequentialGroup()
                        .addComponent(lblLineWidth)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cbLineWidth, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pnlLineLayout.createSequentialGroup()
                        .addComponent(lblColour)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cbColour, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pnlLineLayout.createSequentialGroup()
                        .addComponent(jLabel8)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(spnLayer, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pnlLineLayout.setVerticalGroup(
            pnlLineLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlLineLayout.createSequentialGroup()
                .addGroup(pnlLineLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rbDetection)
                    .addComponent(rbLocks))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cbDevices, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlLineLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(spnLayer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlLineLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(spnX1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2)
                    .addComponent(spnY1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlLineLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblColour)
                    .addComponent(cbColour, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlLineLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblLineWidth)
                    .addComponent(cbLineWidth, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlLineLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblX2)
                    .addComponent(spnX2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblY2)
                    .addComponent(spnY2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(pnlLineLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnAddUpdate)
                    .addComponent(btnRemove)
                    .addComponent(btnCancel))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(pnlLine, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cbSecColour, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(cbSecColour, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pnlLine, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void cbSecColourActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbSecColourActionPerformed
        MapSecurity.securityColour = (NamedColour)cbSecColour.getSelectedItem();
        fireMapChange(new MapChangeEvent(MapChangeEvent.UPDATE, null));
    }//GEN-LAST:event_cbSecColourActionPerformed

    private void btnAddUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddUpdateActionPerformed
        if(mapSecurity != null) {
            mapSecurity.point[0] = (Float)spnX1.getValue();
            mapSecurity.point[1] = (Float)spnY1.getValue();
            if(rbDetection.isSelected()) {
                mapSecurity.type = MapSecurity.DETECTION;
            } else {
                mapSecurity.type = MapSecurity.LOCK;
            }
            mapSecurity.device = (String)cbDevices.getSelectedItem();
            if(MapSecurity.isLineType(mapSecurity)) {
                mapSecurity.point[2] = (Float)spnX2.getValue();
                mapSecurity.point[3] = (Float)spnY2.getValue();
                mapSecurity.lineWidth = ((LineWidthComboBoxModel)cbLineWidth.getModel()).getValue();
            }
            if(mapSecurity.type == MapSecurity.LOCK) {
                mapSecurity.colour = (NamedColour)cbColour.getSelectedItem();
            }
            mapSecurity.layer = (Integer)spnLayer.getValue();
            fireMapChange(new MapChangeEvent(MapChangeEvent.UPDATE, mapSecurity));
        } else {
            mapSecurity = new MapSecurity(MapSecurity.DETECTION, (String)cbDevices.getSelectedItem(), (Float)spnX1.getValue(), (Float)spnY1.getValue());
            if(rbDetection.isSelected()) {
                mapSecurity.type = MapSecurity.DETECTION;
            } else {
                mapSecurity.type = MapSecurity.LOCK;
            }
            if(MapSecurity.isLineType(mapSecurity)) {
                mapSecurity.point[2] = (Float)spnX2.getValue();
                mapSecurity.point[3] = (Float)spnY2.getValue();
                mapSecurity.lineWidth = ((LineWidthComboBoxModel)cbLineWidth.getModel()).getValue();
            }
            if(mapSecurity.type == MapSecurity.LOCK) {
                mapSecurity.colour = (NamedColour)cbColour.getSelectedItem();
            }
            mapSecurity.layer = (Integer)spnLayer.getValue();
            fireMapChange(new MapChangeEvent(MapChangeEvent.ADD, mapSecurity));
        }
    }//GEN-LAST:event_btnAddUpdateActionPerformed

    private void btnRemoveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRemoveActionPerformed
        if(mapSecurity != null) {
            fireMapChange(new MapChangeEvent(MapChangeEvent.REMOVE, mapSecurity));
        } else {
            JOptionPane.showMessageDialog(this, "Must select device", "Removing", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnRemoveActionPerformed

    private void btnCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelActionPerformed
        fireMapChange(new MapChangeEvent(MapChangeEvent.CANCEL, mapSecurity));
    }//GEN-LAST:event_btnCancelActionPerformed

    private void rbDetectionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbDetectionActionPerformed
        cbDevices.setModel(new DefaultComboBoxModel(MapSecurity.detectionList));
        cbDevices.setSelectedIndex(0);
        cbColour.setVisible(false);
        lblColour.setVisible(false);
    }//GEN-LAST:event_rbDetectionActionPerformed

    private void rbLocksActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbLocksActionPerformed
        cbDevices.setModel(new DefaultComboBoxModel(MapSecurity.locksList));
        cbDevices.setSelectedIndex(0);
        cbColour.setVisible(true);
        lblColour.setVisible(false);
    }//GEN-LAST:event_rbLocksActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAddUpdate;
    private javax.swing.JButton btnCancel;
    private javax.swing.JButton btnRemove;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JComboBox cbColour;
    private javax.swing.JComboBox cbDevices;
    private javax.swing.JComboBox cbLineWidth;
    private javax.swing.JComboBox cbSecColour;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel lblColour;
    private javax.swing.JLabel lblLineWidth;
    private javax.swing.JLabel lblX2;
    private javax.swing.JLabel lblY2;
    private javax.swing.JPanel pnlLine;
    private javax.swing.JRadioButton rbDetection;
    private javax.swing.JRadioButton rbLocks;
    private javax.swing.JSpinner spnLayer;
    private javax.swing.JSpinner spnX1;
    private javax.swing.JSpinner spnX2;
    private javax.swing.JSpinner spnY1;
    private javax.swing.JSpinner spnY2;
    // End of variables declaration//GEN-END:variables

    @Override
    public void setObject(MapSecurity object) {
        mapSecurity = object;
        btnAddUpdate.setText("Update");
        btnRemove.setVisible(true);
        spnX1.setValue(mapSecurity.point[0]);
        spnY1.setValue(mapSecurity.point[1]);
        if(mapSecurity.type == MapSecurity.DETECTION) {
            rbDetection.doClick();
        } else {
            rbLocks.doClick();
        }
        cbDevices.setSelectedItem(mapSecurity.device);
        if(MapSecurity.isLineType(mapSecurity)) {
            spnX2.setValue(mapSecurity.point[2]);
            spnY2.setValue(mapSecurity.point[3]);
            boolean found = false;
            for(int i = 0; i < LineWidthComboBoxModel.values.length; i++) {
                if(mapSecurity.lineWidth == LineWidthComboBoxModel.values[i]) {
                    cbLineWidth.setSelectedIndex(i);
                    found = true;
                    break;
                }
            }
            if(!found) {
                ((LineWidthComboBoxModel)cbLineWidth.getModel()).setValue(mapSecurity.lineWidth);
                cbLineWidth.setSelectedIndex(LineWidthComboBoxModel.values.length);
            }
        }
        if(mapSecurity.type == MapSecurity.LOCK) {
            cbColour.setSelectedItem(mapSecurity.colour);
        }
        spnLayer.setValue(mapSecurity.layer);
        pnlLine.setBackground(highlight);
    }

    @Override
    public void clearData() {
        mapSecurity = null;
        btnAddUpdate.setText("Add");
        btnRemove.setVisible(false);
        spnX1.setValue(0);
        spnY1.setValue(0);
        spnX2.setValue(0);
        spnY2.setValue(0);
        cbLineWidth.setSelectedIndex(LineWidthComboBoxModel.defaultIndex);
        cbColour.setSelectedIndex(0);
        rbDetection.doClick();
        spnLayer.setValue(0);
        pnlLine.setBackground(plain);
    }
    
    private void cbLineWidthItemSelected() {
        LineWidthComboBoxModel lwModel = (LineWidthComboBoxModel)cbLineWidth.getModel();
        if(lwModel.isCustomSelected()) {
            NumberSelectorDialog nsd = new NumberSelectorDialog(null, "Custom Line Width..", lwModel.getValue(), false);
            if(nsd.showDialog() == NumberSelectorDialog.OK) {
                lwModel.setValue(nsd.getNumber());
            }
        }
    }
    
    private void cbDevicesItemSelected() {
        boolean setVisible = rbDetection.isSelected() && MapSecurity.isDetectionLineType((String)cbDevices.getSelectedItem());
        lblLineWidth.setVisible(setVisible);
        cbLineWidth.setVisible(setVisible);
        lblX2.setVisible(setVisible);
        spnX2.setVisible(setVisible);
        lblY2.setVisible(setVisible);
        spnY2.setVisible(setVisible);
    }

    @Override
    public boolean isOfType(String typeName) {
        return typeName.compareTo("MapSecurity") == 0;
    }
}