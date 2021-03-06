/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sr5CampaignManager.dialogs;

import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import sr5CampaignManager.objects.Run;

/**
 *
 * @author nbp184
 */
public class ExportDialog extends javax.swing.JDialog {

    public static final int SITE = 0;
    
    private final Run run;
    private final int type;
    private final File directory;
    
    private int currentPanel;
    private final JPanel[] panels;
    private final boolean[] panelDone;
    
    private final JButton btnCancel;
    private final JButton btnPrevious;
    private final JButton btnNext;
    
    //Panel 1 Stuff
    private int[] mapCounts;
    private DefaultListModel<String> mapCountModel;
    
    /**
     * Creates new form ExportDialog
     */
    public ExportDialog(java.awt.Frame parent, Run run, int type, File directory) {
        super(parent, true);
        initComponents();
        setLocationRelativeTo(parent);
        this.run = run;
        this.type = type;
        this.directory = directory;
        loadStuff();
        
        switch(type) {
            case SITE:
                panels = new JPanel[]{jPanel1};
                panelDone = new boolean[]{true};
                break;
            default:
                panels = new JPanel[0];
                panelDone = new boolean[0];
                break;
        }
        currentPanel = 0;
        
        Container contentPane = getContentPane();
        contentPane.setLayout(new GridBagLayout());
        
        btnCancel = new JButton("Cancel");
        btnCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                btnCancelActionPerformed();
            }
        });
        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 1;
        c.weightx = 1;
        c.insets = new Insets(0, 5, 5, 0);
        c.anchor = GridBagConstraints.LINE_START;
        contentPane.add(btnCancel, c);
        
        btnPrevious = new JButton("Previous");
        btnPrevious.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                btnPreviousActionPerformed();
            }
        });
        c = new GridBagConstraints();
        c.gridx = 1;
        c.gridy = 1;
        c.insets = new Insets(0, 0, 5, 5);
        contentPane.add(btnPrevious, c);
        
        btnNext = new JButton("Next");
        btnNext.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                btnNextActionPerformed();
            }
        });
        c = new GridBagConstraints();
        c.gridx = 2;
        c.gridy = 1;
        c.insets = new Insets(0, 0, 5, 5);
        contentPane.add(btnNext, c);
        
        setCurrentPanel();
        pack();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        lstMapCounts = new javax.swing.JList();
        jLabel1 = new javax.swing.JLabel();
        spnMapCount = new javax.swing.JSpinner();
        btnUpdateMapCount = new javax.swing.JButton();

        lstMapCounts.setModel(new javax.swing.AbstractListModel() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        lstMapCounts.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jScrollPane1.setViewportView(lstMapCounts);

        jLabel1.setText("<html>Every map will be exported at least once for linking purposes, if you want second (or more) copies of any map<br/>then please indicate so now.</html>");

        btnUpdateMapCount.setText("Update");
        btnUpdateMapCount.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUpdateMapCountActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 163, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(spnMapCount, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnUpdateMapCount)))
                .addContainerGap(11, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 317, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(spnMapCount, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnUpdateMapCount)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        setTitle("Export Run");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 611, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 454, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnUpdateMapCountActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateMapCountActionPerformed
        int index = lstMapCounts.getSelectedIndex();
        mapCounts[index] = (Integer)spnMapCount.getValue();
        mapCountModel.setElementAt(run.getMapAt(index).name +": " +mapCounts[index], index);
    }//GEN-LAST:event_btnUpdateMapCountActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnUpdateMapCount;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JList lstMapCounts;
    private javax.swing.JSpinner spnMapCount;
    // End of variables declaration//GEN-END:variables

    private void btnCancelActionPerformed() {
        setVisible(false);
    }

    private void btnPreviousActionPerformed() {
        if(currentPanel > 0) {
            this.getContentPane().remove(panels[currentPanel]);
            currentPanel--;
            setCurrentPanel();
        }
    }

    private void btnNextActionPerformed() {
        if(currentPanel < panels.length-1) {
            this.getContentPane().remove(panels[currentPanel]);
            currentPanel++;
            setCurrentPanel();
        } else {
            doFinish();
        }
    }

    private void setCurrentPanel() {
        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 3;
        c.weighty = 1;
        c.anchor = GridBagConstraints.PAGE_START;
        getContentPane().add(panels[currentPanel], c);
        if(currentPanel == 0) {
            btnPrevious.setEnabled(false);
        } else {
            btnPrevious.setEnabled(true);
        }
        if(currentPanel == panels.length-1) {
            btnNext.setText("Finish");
        } else {
            btnNext.setText("Next");
        }
        if(panelDone[currentPanel]) {
            btnNext.setEnabled(true);
        } else {
            btnNext.setEnabled(false);
        }
    }

    private void doFinish() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private void loadStuff() {
        mapCounts = new int[run.getMapCount()];
        mapCountModel = new DefaultListModel();
        for(int i = 0; i < mapCounts.length; i++) {
            mapCounts[i] = 1;
            mapCountModel.add(i, run.getMapAt(i).name +": " +mapCounts[i]);
        }
        lstMapCounts.setModel(mapCountModel);
        lstMapCounts.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                lstMapCountsValueChanged();
            }
        });
        lstMapCounts.setSelectedIndex(0);
    }

    private void lstMapCountsValueChanged() {
        spnMapCount.setValue(mapCounts[lstMapCounts.getSelectedIndex()]);
    }
    
}
