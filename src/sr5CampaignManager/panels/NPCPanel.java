/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sr5CampaignManager.panels;

import sr5CampaignManager.events.RunChangeEvent;
import sr5CampaignManager.events.RunChangeListener;
import sr5CampaignManager.dialogs.WeaponSelectDialog;
import sr5CampaignManager.dialogs.StringTreeSelectDialog;
import sr5CampaignManager.tableModels.*;
import java.awt.Color;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Collections;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.SpinnerNumberModel;
import javax.swing.table.TableColumnModel;
import sr5CampaignManager.Resources;
import sr5CampaignManager.objects.*;

/**
 *
 * @author nbp184
 */
public class NPCPanel extends RunEditor {
    
    private final NPC npc;
    
    /**
     * Creates new form MakePanel
     * @param npc
     */
    public NPCPanel(NPC npc) {
        initComponents();
        this.npc = npc;
        Resources r = Resources.get();
        
        rbNormal.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbNormalActionPerformed(evt);
            }
        });
        rbMagic.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbNormalActionPerformed(evt);
            }
        });
        
        spnBody.setModel(new SpinnerNumberModel(1, 1, 6, 1));
        spnAgility.setModel(new SpinnerNumberModel(1, 1, 6, 1));
        spnReaction.setModel(new SpinnerNumberModel(1, 1, 6, 1));
        spnStrength.setModel(new SpinnerNumberModel(1, 1, 6, 1));
        spnWillpower.setModel(new SpinnerNumberModel(1, 1, 6, 1));
        spnLogic.setModel(new SpinnerNumberModel(1, 1, 6, 1));
        spnIntuition.setModel(new SpinnerNumberModel(1, 1, 6, 1));
        spnCharisma.setModel(new SpinnerNumberModel(1, 1, 6, 1));
        spnMagic.setModel(new SpinnerNumberModel(1, 1, 6, 1));
        spnEssence.setModel(new SpinnerNumberModel(6, 1, 6, 0.1));
        
        tblSkills.setModel(new SkillTableModel());
        TableButton tb = new TableButton("Remove");
        tb.addTableButtonListener(new TableButtonListener() {
            @Override
            public void actionPerformed(int rowIndex, int columnIndex) {
                skillTableButtonActionPerformed(rowIndex);
            }
        });
        tblSkills.setDefaultRenderer(JButton.class, tb);
        tblSkills.getColumnModel().getColumn(SkillTableModel.REMOVE_COLUMN).setCellEditor(tb);
        comboSkills.setModel(new DefaultComboBoxModel(r.getSkills()));
        
        tb = new TableButton("Remove");
        tb.addTableButtonListener(new TableButtonListener() {
            @Override
            public void actionPerformed(int rowIndex, int columnIndex) {
                augmentationTableButtonActionPerformed(rowIndex, columnIndex);
            }
        });
        tblAugs.setDefaultEditor(JButton.class, tb);
        HideCellsRenderer renderer = new HideCellsRenderer(Color.gray);
        renderer.addSubRenderer(JButton.class, tb);
        tblAugs.setDefaultRenderer(Object.class, renderer);
        tblAugs.setModel(new AugmentationTableModel());
        tblAugs.setTableHeader(null);
        
        tb = new TableButton("Remove");
        tb.addTableButtonListener(new TableButtonListener() {
            @Override
            public void actionPerformed(int rowIndex, int columnIndex) {
                programTableButtonActionPerformed(rowIndex, columnIndex);
            }
        });
        tblPrograms.setDefaultEditor(JButton.class, tb);
        tblPrograms.setDefaultRenderer(JButton.class, tb);
        tblPrograms.setModel(new ProgramTableModel());
        tblPrograms.createDefaultColumnsFromModel();
        TableColumnModel columnModel = tblPrograms.getColumnModel();
        columnModel.getColumn(ProgramTableModel.NAME_COLUMN).setPreferredWidth(50);
        columnModel.getColumn(ProgramTableModel.EFFECT_COLUMN).setPreferredWidth(150);
        columnModel.getColumn(ProgramTableModel.BUTTON_COLUMN).setPreferredWidth(50);
        comboDecks.setModel(new DefaultComboBoxModel(r.getDecks()));
        comboPrograms.setModel(new DefaultComboBoxModel(r.getPrograms()));
        
        tb = new TableButton("Remove");
        tb.addTableButtonListener(new TableButtonListener() {
            @Override
            public void actionPerformed(int rowIndex, int columnIndex) {
                weaponTableButtonActionPerformed(rowIndex, columnIndex);
            }
        });
        tblWeapons.setDefaultEditor(JButton.class, tb);
        tblWeapons.setDefaultRenderer(JButton.class, tb);
        tblWeapons.setModel(new WeaponTableModel());
        
        tb = new TableButton("Remove");
        tb.addTableButtonListener(new TableButtonListener() {
            @Override
            public void actionPerformed(int rowIndex, int columnIndex) {
                gearTableButtonActionPerformed(rowIndex, columnIndex);
            }
        });
        tblOtherGear.setDefaultEditor(JButton.class, tb);
        tblOtherGear.setDefaultRenderer(JButton.class, tb);
        tblOtherGear.setModel(new GearTableModel());
        
        tb = new TableButton("Remove");
        tb.addTableButtonListener(new TableButtonListener() {
            @Override
            public void actionPerformed(int rowIndex, int columnIndex) {
                spellsTableButtonActionPerformed(rowIndex, columnIndex);
            }
        });
        tblSpells.setDefaultEditor(JButton.class, tb);
        tblSpells.setDefaultRenderer(JButton.class, tb);
        tblSpells.setModel(new SpellTableModel());
                
        loadCharacter();
    }
    
    private void rbNormalActionPerformed(java.awt.event.ActionEvent evt) {
        lblMagic.setVisible(rbMagic.isSelected());
        spnMagic.setVisible(rbMagic.isSelected());
        if(rbNormal.isSelected()) {
            if(tbpnMain.indexOfComponent(pnlSpells) >= 0) {
                tbpnMain.remove(pnlSpells);
            }
        } else {
            if(tbpnMain.indexOfComponent(pnlSpells) < 0) {
                tbpnMain.insertTab("Spells", null, pnlSpells, "", 4);
            }
        }
    }
    
    private void skillTableButtonActionPerformed(int rowIndex) {
        SkillTableModel model = (SkillTableModel)tblSkills.getModel();
        model.removeSkill(rowIndex);
    }
    
    private void augmentationTableButtonActionPerformed(int rowIndex, int columnIndex) {
        AugmentationTableModel model = (AugmentationTableModel)tblAugs.getModel();
        model.removeAugmentation(rowIndex, columnIndex - 1);
    }
    
    private void programTableButtonActionPerformed(int rowIndex, int columnIndex) {
        ProgramTableModel model = (ProgramTableModel)tblPrograms.getModel();
        model.removeProgram(rowIndex);
    }
    
    private void weaponTableButtonActionPerformed(int rowIndex, int columnIndex) {
        WeaponTableModel model = (WeaponTableModel)tblWeapons.getModel();
        model.removeWeapon(rowIndex);
    }
    
    private void gearTableButtonActionPerformed(int rowIndex, int columnIndex) {
        GearTableModel model = (GearTableModel)tblOtherGear.getModel();
        model.removeGear(rowIndex);
    }
    
    private void spellsTableButtonActionPerformed(int rowIndex, int columnIndex) {
        SpellTableModel model = (SpellTableModel)tblSpells.getModel();
        model.removeSpell(rowIndex);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        bgCharType = new javax.swing.ButtonGroup();
        txtName = new javax.swing.JTextField();
        tbpnMain = new javax.swing.JTabbedPane();
        pnlMain = new javax.swing.JPanel();
        pnlAttributes = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        spnBody = new javax.swing.JSpinner();
        jLabel3 = new javax.swing.JLabel();
        spnAgility = new javax.swing.JSpinner();
        jLabel4 = new javax.swing.JLabel();
        spnReaction = new javax.swing.JSpinner();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        spnWillpower = new javax.swing.JSpinner();
        spnStrength = new javax.swing.JSpinner();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        lblMagic = new javax.swing.JLabel();
        spnCharisma = new javax.swing.JSpinner();
        spnIntuition = new javax.swing.JSpinner();
        spnLogic = new javax.swing.JSpinner();
        spnEssence = new javax.swing.JSpinner();
        spnMagic = new javax.swing.JSpinner();
        jLabel11 = new javax.swing.JLabel();
        txtInitiative = new javax.swing.JTextField();
        lblCondMon = new javax.swing.JLabel();
        txtCondMon = new javax.swing.JTextField();
        pnlLimits = new javax.swing.JPanel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        txtPhysical = new javax.swing.JTextField();
        txtMental = new javax.swing.JTextField();
        txtSocial = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        txtArmor = new javax.swing.JTextField();
        rbNormal = new javax.swing.JRadioButton();
        rbMagic = new javax.swing.JRadioButton();
        btnCalculate = new javax.swing.JButton();
        pnlSkills = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblSkills = new javax.swing.JTable();
        btnAddNewSkill = new javax.swing.JButton();
        comboSkills = new javax.swing.JComboBox();
        btnAddSkill = new javax.swing.JButton();
        pnlAugmentations = new javax.swing.JPanel();
        spAugs = new javax.swing.JScrollPane();
        tblAugs = new javax.swing.JTable();
        btnAddNewAug = new javax.swing.JButton();
        addAug = new javax.swing.JButton();
        pnlMatrixGear = new javax.swing.JPanel();
        btnAddNewProgram = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblPrograms = new javax.swing.JTable();
        cbHasMatrixGear = new javax.swing.JCheckBox();
        lblDeckName = new javax.swing.JLabel();
        txtDeckName = new javax.swing.JTextField();
        lblDeckRating = new javax.swing.JLabel();
        txtDeckRating = new javax.swing.JTextField();
        lblDeckAttr = new javax.swing.JLabel();
        txtDeckAttr = new javax.swing.JTextField();
        comboDecks = new javax.swing.JComboBox();
        btnSelectDeck = new javax.swing.JButton();
        comboPrograms = new javax.swing.JComboBox();
        btnAddProgram = new javax.swing.JButton();
        pnlGear = new javax.swing.JPanel();
        btnAddRanged = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        tblWeapons = new javax.swing.JTable();
        jScrollPane4 = new javax.swing.JScrollPane();
        tblOtherGear = new javax.swing.JTable();
        btnAddMelee = new javax.swing.JButton();
        btnAddNewOtherGear = new javax.swing.JButton();
        btnSortGear = new javax.swing.JButton();
        btnAddWeapon = new javax.swing.JButton();
        btnAddOtherGear = new javax.swing.JButton();
        pnlSpells = new javax.swing.JPanel();
        btnAddSpell = new javax.swing.JButton();
        jScrollPane5 = new javax.swing.JScrollPane();
        tblSpells = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        btnSave = new javax.swing.JButton();

        txtName.setColumns(50);

        pnlAttributes.setBorder(javax.swing.BorderFactory.createTitledBorder("Attributes"));

        jLabel2.setText("Body");

        jLabel3.setText("Agility");

        jLabel4.setText("Reaction");

        jLabel5.setText("Strength");

        jLabel6.setText("Willpower");

        jLabel7.setText("Logic");

        jLabel8.setText("Intuition");

        jLabel9.setText("Charisma");

        jLabel10.setText("Essence");

        lblMagic.setText("Magic");

        javax.swing.GroupLayout pnlAttributesLayout = new javax.swing.GroupLayout(pnlAttributes);
        pnlAttributes.setLayout(pnlAttributesLayout);
        pnlAttributesLayout.setHorizontalGroup(
            pnlAttributesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlAttributesLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(pnlAttributesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlAttributesLayout.createSequentialGroup()
                        .addGroup(pnlAttributesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(jLabel3))
                        .addGap(28, 28, 28)
                        .addGroup(pnlAttributesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(spnBody, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(spnAgility, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(pnlAttributesLayout.createSequentialGroup()
                        .addGroup(pnlAttributesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel6)
                            .addComponent(jLabel5)
                            .addComponent(jLabel4))
                        .addGroup(pnlAttributesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pnlAttributesLayout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(pnlAttributesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(spnStrength, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(spnWillpower, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(pnlAttributesLayout.createSequentialGroup()
                                .addGap(11, 11, 11)
                                .addComponent(spnReaction, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addGap(34, 34, 34)
                .addGroup(pnlAttributesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel9)
                    .addComponent(jLabel8)
                    .addComponent(jLabel7)
                    .addComponent(jLabel10)
                    .addComponent(lblMagic))
                .addGap(18, 18, 18)
                .addGroup(pnlAttributesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(spnMagic, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(spnEssence, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(spnLogic, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(spnIntuition, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(spnCharisma, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );
        pnlAttributesLayout.setVerticalGroup(
            pnlAttributesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlAttributesLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlAttributesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(spnBody, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7)
                    .addComponent(spnLogic, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlAttributesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(spnAgility, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8)
                    .addComponent(spnIntuition, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlAttributesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(spnReaction, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4)
                    .addComponent(jLabel9)
                    .addComponent(spnCharisma, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlAttributesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(spnStrength, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10)
                    .addComponent(spnEssence, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlAttributesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(spnWillpower, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblMagic)
                    .addComponent(spnMagic, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel11.setText("Initiative");

        txtInitiative.setColumns(10);

        lblCondMon.setText("Condition Monitor");

        txtCondMon.setColumns(3);

        pnlLimits.setBorder(javax.swing.BorderFactory.createTitledBorder("Limits"));

        jLabel13.setText("Physical");

        jLabel14.setText("Mental");

        jLabel15.setText("Social");

        txtPhysical.setColumns(3);

        txtMental.setColumns(3);

        txtSocial.setColumns(3);

        javax.swing.GroupLayout pnlLimitsLayout = new javax.swing.GroupLayout(pnlLimits);
        pnlLimits.setLayout(pnlLimitsLayout);
        pnlLimitsLayout.setHorizontalGroup(
            pnlLimitsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlLimitsLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(pnlLimitsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel13)
                    .addComponent(jLabel14)
                    .addComponent(jLabel15))
                .addGap(18, 18, 18)
                .addGroup(pnlLimitsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtSocial, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtMental, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtPhysical, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );
        pnlLimitsLayout.setVerticalGroup(
            pnlLimitsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlLimitsLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(pnlLimitsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel13)
                    .addComponent(txtPhysical, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlLimitsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel14)
                    .addComponent(txtMental, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlLimitsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel15)
                    .addComponent(txtSocial, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        jLabel16.setText("Armor");

        txtArmor.setColumns(3);

        bgCharType.add(rbNormal);
        rbNormal.setText("Normal");

        bgCharType.add(rbMagic);
        rbMagic.setText("Magic");

        btnCalculate.setText("Calculate");
        btnCalculate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCalculateActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlMainLayout = new javax.swing.GroupLayout(pnlMain);
        pnlMain.setLayout(pnlMainLayout);
        pnlMainLayout.setHorizontalGroup(
            pnlMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlMainLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(pnlAttributes, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(pnlMainLayout.createSequentialGroup()
                        .addComponent(pnlLimits, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(pnlMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(rbNormal)
                            .addComponent(rbMagic))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnlMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnCalculate)
                    .addGroup(pnlMainLayout.createSequentialGroup()
                        .addComponent(jLabel11)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtInitiative, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pnlMainLayout.createSequentialGroup()
                        .addComponent(lblCondMon)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtCondMon, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pnlMainLayout.createSequentialGroup()
                        .addComponent(jLabel16)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtArmor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(260, Short.MAX_VALUE))
        );
        pnlMainLayout.setVerticalGroup(
            pnlMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlMainLayout.createSequentialGroup()
                .addGroup(pnlMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlMainLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(pnlAttributes, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pnlMainLayout.createSequentialGroup()
                        .addGap(17, 17, 17)
                        .addGroup(pnlMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel11)
                            .addComponent(txtInitiative, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(pnlMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblCondMon)
                            .addComponent(txtCondMon, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(pnlMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel16)
                            .addComponent(txtArmor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGroup(pnlMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlMainLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(pnlMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pnlMainLayout.createSequentialGroup()
                                .addComponent(rbNormal)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(rbMagic))
                            .addComponent(pnlLimits, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(pnlMainLayout.createSequentialGroup()
                        .addGap(24, 24, 24)
                        .addComponent(btnCalculate)))
                .addContainerGap(110, Short.MAX_VALUE))
        );

        tbpnMain.addTab("Main", pnlMain);

        tblSkills.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(tblSkills);

        btnAddNewSkill.setText("Add New Skill");
        btnAddNewSkill.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddNewSkillActionPerformed(evt);
            }
        });

        comboSkills.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        btnAddSkill.setText("Add Skill");
        btnAddSkill.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddSkillActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlSkillsLayout = new javax.swing.GroupLayout(pnlSkills);
        pnlSkills.setLayout(pnlSkillsLayout);
        pnlSkillsLayout.setHorizontalGroup(
            pnlSkillsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlSkillsLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlSkillsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlSkillsLayout.createSequentialGroup()
                        .addComponent(btnAddNewSkill)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(comboSkills, javax.swing.GroupLayout.PREFERRED_SIZE, 212, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnAddSkill)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 625, Short.MAX_VALUE))
                .addContainerGap())
        );
        pnlSkillsLayout.setVerticalGroup(
            pnlSkillsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlSkillsLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlSkillsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnAddNewSkill)
                    .addComponent(comboSkills, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnAddSkill))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 351, Short.MAX_VALUE)
                .addContainerGap())
        );

        tbpnMain.addTab("Skills", pnlSkills);

        spAugs.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);

        tblAugs.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        spAugs.setViewportView(tblAugs);
        if (tblAugs.getColumnModel().getColumnCount() > 0) {
            tblAugs.getColumnModel().getColumn(0).setPreferredWidth(50);
        }

        btnAddNewAug.setText("Add New Augmentation");
        btnAddNewAug.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddNewAugActionPerformed(evt);
            }
        });

        addAug.setText("Add Augmenation");
        addAug.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addAugActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlAugmentationsLayout = new javax.swing.GroupLayout(pnlAugmentations);
        pnlAugmentations.setLayout(pnlAugmentationsLayout);
        pnlAugmentationsLayout.setHorizontalGroup(
            pnlAugmentationsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlAugmentationsLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlAugmentationsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlAugmentationsLayout.createSequentialGroup()
                        .addComponent(btnAddNewAug)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(addAug)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(spAugs, javax.swing.GroupLayout.DEFAULT_SIZE, 625, Short.MAX_VALUE))
                .addContainerGap())
        );
        pnlAugmentationsLayout.setVerticalGroup(
            pnlAugmentationsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlAugmentationsLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(pnlAugmentationsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnAddNewAug)
                    .addComponent(addAug))
                .addGap(18, 18, 18)
                .addComponent(spAugs, javax.swing.GroupLayout.PREFERRED_SIZE, 346, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(21, 21, 21))
        );

        tbpnMain.addTab("Augmentations", pnlAugmentations);

        btnAddNewProgram.setText("Add New Program");
        btnAddNewProgram.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddNewProgramActionPerformed(evt);
            }
        });

        tblPrograms.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane2.setViewportView(tblPrograms);

        cbHasMatrixGear.setSelected(true);
        cbHasMatrixGear.setText("Has Matrix Gear");
        cbHasMatrixGear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbHasMatrixGearActionPerformed(evt);
            }
        });

        lblDeckName.setText("Cyberdeck Name");

        txtDeckName.setColumns(30);

        lblDeckRating.setText("Rating");

        txtDeckRating.setColumns(3);

        lblDeckAttr.setText("ASDF Attribute Array");

        txtDeckAttr.setColumns(10);

        comboDecks.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        btnSelectDeck.setText("Select Deck");
        btnSelectDeck.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSelectDeckActionPerformed(evt);
            }
        });

        comboPrograms.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        btnAddProgram.setText("Add Program");
        btnAddProgram.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddProgramActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlMatrixGearLayout = new javax.swing.GroupLayout(pnlMatrixGear);
        pnlMatrixGear.setLayout(pnlMatrixGearLayout);
        pnlMatrixGearLayout.setHorizontalGroup(
            pnlMatrixGearLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlMatrixGearLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlMatrixGearLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 625, Short.MAX_VALUE)
                    .addGroup(pnlMatrixGearLayout.createSequentialGroup()
                        .addGroup(pnlMatrixGearLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(pnlMatrixGearLayout.createSequentialGroup()
                                .addComponent(cbHasMatrixGear)
                                .addGap(90, 90, 90)
                                .addComponent(comboDecks, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(pnlMatrixGearLayout.createSequentialGroup()
                                .addComponent(lblDeckName)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtDeckName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(lblDeckRating)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtDeckRating, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(pnlMatrixGearLayout.createSequentialGroup()
                                .addGroup(pnlMatrixGearLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblDeckAttr)
                                    .addComponent(btnAddNewProgram))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(pnlMatrixGearLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtDeckAttr, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(pnlMatrixGearLayout.createSequentialGroup()
                                        .addComponent(comboPrograms, javax.swing.GroupLayout.PREFERRED_SIZE, 189, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(btnAddProgram)))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnSelectDeck)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        pnlMatrixGearLayout.setVerticalGroup(
            pnlMatrixGearLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlMatrixGearLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlMatrixGearLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cbHasMatrixGear)
                    .addComponent(comboDecks, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSelectDeck))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlMatrixGearLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblDeckName)
                    .addComponent(txtDeckName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblDeckRating)
                    .addComponent(txtDeckRating, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlMatrixGearLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblDeckAttr)
                    .addComponent(txtDeckAttr, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlMatrixGearLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnAddNewProgram)
                    .addComponent(comboPrograms, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnAddProgram))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 270, Short.MAX_VALUE)
                .addContainerGap())
        );

        tbpnMain.addTab("Matrix Gear", pnlMatrixGear);

        btnAddRanged.setText("Add New Ranged Weapon");
        btnAddRanged.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddRangedActionPerformed(evt);
            }
        });

        tblWeapons.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane3.setViewportView(tblWeapons);

        tblOtherGear.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane4.setViewportView(tblOtherGear);

        btnAddMelee.setText("Add New Melee Weapon");
        btnAddMelee.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddMeleeActionPerformed(evt);
            }
        });

        btnAddNewOtherGear.setText("Add New Other");
        btnAddNewOtherGear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddNewOtherGearActionPerformed(evt);
            }
        });

        btnSortGear.setText("Sort");
        btnSortGear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSortGearActionPerformed(evt);
            }
        });

        btnAddWeapon.setText("Add Weapon");
        btnAddWeapon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddWeaponActionPerformed(evt);
            }
        });

        btnAddOtherGear.setText("Add Other");
        btnAddOtherGear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddOtherGearActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlGearLayout = new javax.swing.GroupLayout(pnlGear);
        pnlGear.setLayout(pnlGearLayout);
        pnlGearLayout.setHorizontalGroup(
            pnlGearLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlGearLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlGearLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 625, Short.MAX_VALUE)
                    .addGroup(pnlGearLayout.createSequentialGroup()
                        .addComponent(btnAddNewOtherGear)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnAddOtherGear)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jScrollPane4)
                    .addGroup(pnlGearLayout.createSequentialGroup()
                        .addComponent(btnAddRanged)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnAddMelee)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnAddWeapon)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnSortGear)))
                .addContainerGap())
        );
        pnlGearLayout.setVerticalGroup(
            pnlGearLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlGearLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlGearLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnAddRanged)
                    .addComponent(btnAddMelee)
                    .addComponent(btnSortGear)
                    .addComponent(btnAddWeapon))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlGearLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnAddNewOtherGear)
                    .addComponent(btnAddOtherGear))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 204, Short.MAX_VALUE)
                .addContainerGap())
        );

        tbpnMain.addTab("Gear", pnlGear);

        btnAddSpell.setText("Add Spell");
        btnAddSpell.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddSpellActionPerformed(evt);
            }
        });

        tblSpells.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane5.setViewportView(tblSpells);

        javax.swing.GroupLayout pnlSpellsLayout = new javax.swing.GroupLayout(pnlSpells);
        pnlSpells.setLayout(pnlSpellsLayout);
        pnlSpellsLayout.setHorizontalGroup(
            pnlSpellsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlSpellsLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlSpellsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlSpellsLayout.createSequentialGroup()
                        .addComponent(btnAddSpell)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 625, Short.MAX_VALUE))
                .addContainerGap())
        );
        pnlSpellsLayout.setVerticalGroup(
            pnlSpellsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlSpellsLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnAddSpell)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 356, Short.MAX_VALUE)
                .addContainerGap())
        );

        tbpnMain.addTab("Spells", pnlSpells);

        jLabel1.setText("Name");

        btnSave.setText("Save");
        btnSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaveActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(tbpnMain)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(btnSave))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addGap(14, 14, 14)
                .addComponent(tbpnMain, javax.swing.GroupLayout.PREFERRED_SIZE, 435, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnSave)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnAddNewSkillActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddNewSkillActionPerformed
        SkillTableModel model = (SkillTableModel)tblSkills.getModel();
        model.addSkill(false, "");
    }//GEN-LAST:event_btnAddNewSkillActionPerformed

    private void btnAddNewAugActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddNewAugActionPerformed
        AugmentationTableModel model = (AugmentationTableModel)tblAugs.getModel();
        int row = model.addAugmentation("", false);
        Rectangle cellRect = tblAugs.getCellRect(row, 0, true);
        if(cellRect.y + cellRect.height > spAugs.getHeight()) {
            model.setMaxRowCount(row);
        }
    }//GEN-LAST:event_btnAddNewAugActionPerformed

    private void btnAddNewProgramActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddNewProgramActionPerformed
        ProgramTableModel model = (ProgramTableModel)tblPrograms.getModel();
        model.addProgram();
    }//GEN-LAST:event_btnAddNewProgramActionPerformed

    private void cbHasMatrixGearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbHasMatrixGearActionPerformed
        comboDecks.setEnabled(cbHasMatrixGear.isSelected());
        btnSelectDeck.setEnabled(cbHasMatrixGear.isSelected());
        
        lblDeckName.setEnabled(cbHasMatrixGear.isSelected());
        txtDeckName.setEnabled(cbHasMatrixGear.isSelected());
        
        lblDeckRating.setEnabled(cbHasMatrixGear.isSelected());
        txtDeckRating.setEnabled(cbHasMatrixGear.isSelected());
        
        lblDeckAttr.setEnabled(cbHasMatrixGear.isSelected());
        txtDeckAttr.setEnabled(cbHasMatrixGear.isSelected());
        
        btnAddNewProgram.setEnabled(cbHasMatrixGear.isSelected());
        tblPrograms.setEnabled(cbHasMatrixGear.isSelected());
        
        comboPrograms.setEnabled(cbHasMatrixGear.isSelected());
        btnAddProgram.setEnabled(cbHasMatrixGear.isSelected());
    }//GEN-LAST:event_cbHasMatrixGearActionPerformed

    private void btnAddRangedActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddRangedActionPerformed
        WeaponTableModel model = (WeaponTableModel)tblWeapons.getModel();
        model.addWeapon(true);
    }//GEN-LAST:event_btnAddRangedActionPerformed

    private void btnAddMeleeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddMeleeActionPerformed
        WeaponTableModel model = (WeaponTableModel)tblWeapons.getModel();
        model.addWeapon(false);
    }//GEN-LAST:event_btnAddMeleeActionPerformed

    private void btnAddNewOtherGearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddNewOtherGearActionPerformed
        GearTableModel model = (GearTableModel)tblOtherGear.getModel();
        model.addGear();
    }//GEN-LAST:event_btnAddNewOtherGearActionPerformed

    private void btnSortGearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSortGearActionPerformed
        WeaponTableModel model1 = (WeaponTableModel)tblWeapons.getModel();
        model1.sort();
        GearTableModel model2 = (GearTableModel)tblOtherGear.getModel();
        model2.sort();
    }//GEN-LAST:event_btnSortGearActionPerformed

    private void btnAddSpellActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddSpellActionPerformed
        SpellTableModel model = (SpellTableModel)tblSpells.getModel();
        model.addSpell();
    }//GEN-LAST:event_btnAddSpellActionPerformed

    private void btnAddSkillActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddSkillActionPerformed
        String skillName = (String)comboSkills.getSelectedItem();
        if(skillName != null) {
            SkillTableModel model = (SkillTableModel)tblSkills.getModel();
            model.addSkill(true, skillName);
        }
    }//GEN-LAST:event_btnAddSkillActionPerformed

    private void addAugActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addAugActionPerformed
        StringTreeSelectDialog augSelectDialog = new StringTreeSelectDialog(this, true, Resources.get().getAugs(), "Augmentation");
        augSelectDialog.setVisible(true);
        if(augSelectDialog.somethingSelected()) {
            AugmentationTableModel model = (AugmentationTableModel)tblAugs.getModel();
            int row = model.addAugmentation(augSelectDialog.getSelectedValue(), true);
            Rectangle cellRect = tblAugs.getCellRect(row, 0, true);
            if(cellRect.y + cellRect.height > spAugs.getHeight()) {
                model.setMaxRowCount(row);
            }
        }
        augSelectDialog.dispose();
    }//GEN-LAST:event_addAugActionPerformed

    private void btnSelectDeckActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSelectDeckActionPerformed
        CyberDeck deck = (CyberDeck)comboDecks.getSelectedItem();
        if(deck != null) {
            txtDeckName.setText(deck.name);
            txtDeckRating.setText(deck.rating);
            txtDeckAttr.setText(deck.attributeArray);
        }
    }//GEN-LAST:event_btnSelectDeckActionPerformed

    private void btnAddProgramActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddProgramActionPerformed
        Program prog = (Program)comboPrograms.getSelectedItem();
        if(prog != null) {
            ProgramTableModel model = (ProgramTableModel)tblPrograms.getModel();
            model.addProgram(prog);
        }
    }//GEN-LAST:event_btnAddProgramActionPerformed

    private void btnAddWeaponActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddWeaponActionPerformed
        WeaponSelectDialog wsd = new WeaponSelectDialog(this, true);
        wsd.setVisible(true);
        if(wsd.weaponSelected()) {
            WeaponTableModel model = (WeaponTableModel)tblWeapons.getModel();
            model.addWeapon(wsd.getSelectedWeapon());
        }
        wsd.dispose();
    }//GEN-LAST:event_btnAddWeaponActionPerformed

    private void btnAddOtherGearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddOtherGearActionPerformed
        StringTreeSelectDialog gearSelectDialog = new StringTreeSelectDialog(this, true, Resources.get().getGear(), "Gear");
        gearSelectDialog.setVisible(true);
        if(gearSelectDialog.somethingSelected()) {
            GearTableModel model = (GearTableModel)tblOtherGear.getModel();
            model.addGear(gearSelectDialog.getSelectedValue());
        }
        gearSelectDialog.dispose();
    }//GEN-LAST:event_btnAddOtherGearActionPerformed

    private void btnCalculateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCalculateActionPerformed
        txtInitiative.setText("" +((int)spnIntuition.getValue() + (int)spnReaction.getValue()) +"+1D6");
        int limit = (int)Math.ceil((2*(int)spnLogic.getValue() + (int)spnIntuition.getValue() + (int)spnWillpower.getValue())/3.0);
        txtMental.setText("" +limit);
        limit = (int)Math.ceil((2*(int)spnStrength.getValue() + (int)spnBody.getValue() + (int)spnReaction.getValue())/3.0);
        txtPhysical.setText(""+limit);
        limit = (int)Math.ceil((2*(int)spnCharisma.getValue() + (double)spnEssence.getValue() + (int)spnWillpower.getValue())/3.0);
        txtSocial.setText(""+limit);
        limit = (int)Math.max(Math.ceil((int)spnBody.getValue()/2.0), Math.ceil((int)spnWillpower.getValue()/2.0)) + 8;
        txtCondMon.setText(""+limit);
    }//GEN-LAST:event_btnCalculateActionPerformed

    private void btnSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveActionPerformed
        saveNPC();
        fireRunChangeEvent(new RunChangeEvent(npc, RunChangeEvent.NO_ADD_REMOVE));
    }//GEN-LAST:event_btnSaveActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addAug;
    private javax.swing.ButtonGroup bgCharType;
    private javax.swing.JButton btnAddMelee;
    private javax.swing.JButton btnAddNewAug;
    private javax.swing.JButton btnAddNewOtherGear;
    private javax.swing.JButton btnAddNewProgram;
    private javax.swing.JButton btnAddNewSkill;
    private javax.swing.JButton btnAddOtherGear;
    private javax.swing.JButton btnAddProgram;
    private javax.swing.JButton btnAddRanged;
    private javax.swing.JButton btnAddSkill;
    private javax.swing.JButton btnAddSpell;
    private javax.swing.JButton btnAddWeapon;
    private javax.swing.JButton btnCalculate;
    private javax.swing.JButton btnSave;
    private javax.swing.JButton btnSelectDeck;
    private javax.swing.JButton btnSortGear;
    private javax.swing.JCheckBox cbHasMatrixGear;
    private javax.swing.JComboBox comboDecks;
    private javax.swing.JComboBox comboPrograms;
    private javax.swing.JComboBox comboSkills;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JLabel lblCondMon;
    private javax.swing.JLabel lblDeckAttr;
    private javax.swing.JLabel lblDeckName;
    private javax.swing.JLabel lblDeckRating;
    private javax.swing.JLabel lblMagic;
    private javax.swing.JPanel pnlAttributes;
    private javax.swing.JPanel pnlAugmentations;
    private javax.swing.JPanel pnlGear;
    private javax.swing.JPanel pnlLimits;
    private javax.swing.JPanel pnlMain;
    private javax.swing.JPanel pnlMatrixGear;
    private javax.swing.JPanel pnlSkills;
    private javax.swing.JPanel pnlSpells;
    private javax.swing.JRadioButton rbMagic;
    private javax.swing.JRadioButton rbNormal;
    private javax.swing.JScrollPane spAugs;
    private javax.swing.JSpinner spnAgility;
    private javax.swing.JSpinner spnBody;
    private javax.swing.JSpinner spnCharisma;
    private javax.swing.JSpinner spnEssence;
    private javax.swing.JSpinner spnIntuition;
    private javax.swing.JSpinner spnLogic;
    private javax.swing.JSpinner spnMagic;
    private javax.swing.JSpinner spnReaction;
    private javax.swing.JSpinner spnStrength;
    private javax.swing.JSpinner spnWillpower;
    private javax.swing.JTable tblAugs;
    private javax.swing.JTable tblOtherGear;
    private javax.swing.JTable tblPrograms;
    private javax.swing.JTable tblSkills;
    private javax.swing.JTable tblSpells;
    private javax.swing.JTable tblWeapons;
    private javax.swing.JTabbedPane tbpnMain;
    private javax.swing.JTextField txtArmor;
    private javax.swing.JTextField txtCondMon;
    private javax.swing.JTextField txtDeckAttr;
    private javax.swing.JTextField txtDeckName;
    private javax.swing.JTextField txtDeckRating;
    private javax.swing.JTextField txtInitiative;
    private javax.swing.JTextField txtMental;
    private javax.swing.JTextField txtName;
    private javax.swing.JTextField txtPhysical;
    private javax.swing.JTextField txtSocial;
    // End of variables declaration//GEN-END:variables

    public void storeNewResources() {
        Resources r = Resources.get();
        SkillTableModel smodel = (SkillTableModel)tblSkills.getModel();
        r.addSkills(smodel.getSkillsNotInList());
        AugmentationTableModel amodel = (AugmentationTableModel)tblAugs.getModel();
        r.addAugmentations(amodel.getAugmentationsNotInList());
        if(cbHasMatrixGear.isSelected()) {
            r.addDeck(new CyberDeck(txtDeckName.getText(), txtDeckRating.getText(), txtDeckAttr.getText()));
            ProgramTableModel pmodel = (ProgramTableModel)tblPrograms.getModel();
            r.addPrograms(pmodel.getProgramsNotInList());
        }
        WeaponTableModel wmodel = (WeaponTableModel)tblWeapons.getModel();
        r.addRangedWeapons(wmodel.getRangedWeaponsNotInList());
        r.addMeleeWeapons(wmodel.getMeleeWeaponsNotInList());
        GearTableModel gmodel = (GearTableModel)tblOtherGear.getModel();
        r.addGear(gmodel.getGearNotInList());
    }

    private void saveNPC() {
        npc.isMagic = rbMagic.isSelected();
        npc.name = txtName.getText();
        npc.attributes[NPC.BODY] = (int)spnBody.getValue();
        npc.attributes[NPC.AGILITY] = (int)spnAgility.getValue();
        npc.attributes[NPC.REACTION] = (int)spnReaction.getValue();
        npc.attributes[NPC.STRENGTH] = (int)spnStrength.getValue();
        npc.attributes[NPC.WILLPOWER] = (int)spnWillpower.getValue();
        npc.attributes[NPC.LOGIC] = (int)spnLogic.getValue();
        npc.attributes[NPC.CHARISMA] = (int)spnCharisma.getValue();
        npc.attributes[NPC.INTUITION] = (int)spnIntuition.getValue();
        if(npc.isMagic) {
            npc.attributes[NPC.MAGIC] = (int)spnMagic.getValue();
        }
        npc.essence = (double)spnEssence.getValue();
        npc.initiative = txtInitiative.getText();
        npc.conditionMonitor = txtCondMon.getText();
        npc.limits[NPC.PHYSICAL] = txtPhysical.getText();
        npc.limits[NPC.MENTAL] = txtMental.getText();
        npc.limits[NPC.SOCIAL] = txtSocial.getText();
        npc.armor = txtArmor.getText();
        
        npc.skills.clear();
        npc.skills.addAll(((SkillTableModel)tblSkills.getModel()).getSkills());
        
        npc.augmentations.clear();
        npc.augmentations.addAll(((AugmentationTableModel)tblAugs.getModel()).getAugmentations());
        
        if(cbHasMatrixGear.isSelected()) {
            npc.deck = new CyberDeck(txtDeckName.getText(), txtDeckRating.getText(), txtDeckAttr.getText());
            npc.programs.clear();
            npc.programs.addAll((((ProgramTableModel)tblPrograms.getModel()).getPrograms()));
        } else {
            npc.deck = null;
        }
        
        ArrayList<Weapon> gear1 = ((WeaponTableModel)tblWeapons.getModel()).getWeapons();
        Collections.sort(gear1);
        ArrayList<Gear> gear2 = ((GearTableModel)tblOtherGear.getModel()).getGear();
        Collections.sort(gear2);
        int index1 = 0;
        int index2 = 0;
        npc.gear.clear();
        while(index1 < gear1.size() || index2 < gear2.size()) {
            if(index1 < gear1.size() && index2 < gear2.size()) {
                if(gear1.get(index1).sortOrder <= gear1.get(index2).sortOrder) {
                    npc.gear.add(gear1.get(index1++));
                } else {
                    npc.gear.add(gear2.get(index2++));
                }
            } else if(index1 < gear1.size()) {
                npc.gear.add(gear1.get(index1++));
            } else {
                npc.gear.add(gear2.get(index2++));
            }
        }
        
        if(npc.isMagic) {
            npc.spells.clear();
            npc.spells.addAll((((SpellTableModel)tblSpells.getModel()).getSpells()));
        }
    }

    private void loadCharacter() {
        txtName.setText(npc.name);
        spnBody.setValue(npc.attributes[NPC.BODY]);
        spnAgility.setValue(npc.attributes[NPC.AGILITY]);
        spnReaction.setValue(npc.attributes[NPC.REACTION]);
        spnStrength.setValue(npc.attributes[NPC.STRENGTH]);
        spnCharisma.setValue(npc.attributes[NPC.CHARISMA]);
        spnEssence.setValue(npc.essence);
        spnIntuition.setValue(npc.attributes[NPC.INTUITION]);
        spnLogic.setValue(npc.attributes[NPC.LOGIC]);
        spnWillpower.setValue(npc.attributes[NPC.WILLPOWER]);
        txtCondMon.setText(npc.conditionMonitor);
        txtInitiative.setText(npc.initiative);
        txtArmor.setText(npc.armor);
        txtPhysical.setText(npc.limits[NPC.PHYSICAL]);
        txtMental.setText(npc.limits[NPC.MENTAL]);
        txtSocial.setText(npc.limits[NPC.SOCIAL]);
        SkillTableModel smodel = (SkillTableModel)tblSkills.getModel();
        smodel.addSkills(npc.skills);
        AugmentationTableModel amodel = (AugmentationTableModel)tblAugs.getModel();
        amodel.addAugmentations(npc.augmentations);
        if(npc.deck == null) {
            cbHasMatrixGear.doClick();
        } else {
            txtDeckName.setText(npc.deck.name);
            txtDeckRating.setText(npc.deck.rating);
            txtDeckAttr.setText(npc.deck.attributeArray);
            ProgramTableModel pmodel = (ProgramTableModel)tblPrograms.getModel();
            pmodel.addPrograms(npc.programs);
        }
        WeaponTableModel wmodel = (WeaponTableModel)tblWeapons.getModel();
        wmodel.addWeapons(npc.getWeapons());
        GearTableModel gmodel = (GearTableModel)tblOtherGear.getModel();
        gmodel.addGear(npc.getOtherGear());
        
        if(npc.isMagic) {
            spnMagic.setValue(npc.attributes[NPC.MAGIC]);
            SpellTableModel model = (SpellTableModel)tblSpells.getModel();
            model.addSpells(npc.spells);
            rbMagic.doClick();
        } else {
            rbNormal.doClick();
        }
    }

    @Override
    public void fireRunChangeEvent(RunChangeEvent evt) {
        RunChangeListener[] listeners = listenerList.getListeners(RunChangeListener.class);
        for(RunChangeListener listener : listeners) {
            listener.onRunChanged(evt);
        }
    }

    @Override
    public String getTitleBar() {
        return "NPC: " +npc.name;
    }

    @Override
    public void clickSaveButton() {
        btnSave.doClick();
    }

    @Override
    public void preferenceSave() {
        
    }
}
