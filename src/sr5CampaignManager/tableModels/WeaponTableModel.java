/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sr5CampaignManager.tableModels;

import java.util.ArrayList;
import java.util.Collections;
import javax.swing.JButton;
import javax.swing.table.AbstractTableModel;
import sr5CampaignManager.objects.Weapon;

/**
 *
 * @author nbp184
 */
public class WeaponTableModel extends AbstractTableModel {

    public static final int ORDER_COLUMN = 0;
    public static final int IS_COLUMN = 1;
    public static final int NAME_COLUMN = 2;
    public static final int TYPE_COLUMN = 3;
    public static final int DAMAGE_COLUMN = 4;
    public static final int ACC_REACH_COLUMN = 5;
    public static final int AP_COLUMN = 6;
    public static final int BUTTON_COLUMN = 7;
    
    private static final String[] columnNames = {"Order", "", "Name", "Type", "Damage", "Accuracy/Reach", "AP", ""};
    private static final Class<?>[] columnClasses = {Integer.class, String.class, String.class, String.class, String.class, String.class, String.class, JButton.class};
    
    private ArrayList<Weapon> weapons;
    
    public WeaponTableModel() {
        weapons = new ArrayList<>();
    }
    
    @Override
    public int getRowCount() {
        return weapons.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        switch(columnIndex) {
            case ORDER_COLUMN:
                return weapons.get(rowIndex).sortOrder;
            case IS_COLUMN:
                if(weapons.get(rowIndex).isRanged) {
                    return "Ranged";
                } else {
                    return "Melee";
                }
            case NAME_COLUMN:
                return weapons.get(rowIndex).name;
            case TYPE_COLUMN:
                return weapons.get(rowIndex).type;
            case DAMAGE_COLUMN:
                return weapons.get(rowIndex).damage;
            case ACC_REACH_COLUMN:
                return weapons.get(rowIndex).accuracyReach;
            case AP_COLUMN:
                return weapons.get(rowIndex).ap;
            default:
                return null;
        }
    }
    
    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }
    
    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return columnClasses[columnIndex];
    }
    
    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return columnIndex != IS_COLUMN && (columnIndex == BUTTON_COLUMN || !weapons.get(rowIndex).fromList);
    }
    
    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        switch(columnIndex) {
            case ORDER_COLUMN:
                weapons.get(rowIndex).sortOrder = (Integer)aValue;
                break;
            case NAME_COLUMN:
                weapons.get(rowIndex).name = (String)aValue;
                break;
            case TYPE_COLUMN:
                weapons.get(rowIndex).type = (String)aValue;
                break;
            case DAMAGE_COLUMN:
                weapons.get(rowIndex).damage = (String)aValue;
                break;
            case ACC_REACH_COLUMN:
                weapons.get(rowIndex).accuracyReach = (String)aValue;
                break;
            case AP_COLUMN:
                weapons.get(rowIndex).ap = (String)aValue;
                break;
        }
    }

    public void addWeapon(boolean isRanged) {
        weapons.add(new Weapon(weapons.size()+1, false, isRanged));
        this.fireTableDataChanged();
    }
    
    public void addWeapon(Weapon weapon) {
        weapon.sortOrder = weapons.size()+1;
        weapons.add(weapon);
        this.fireTableDataChanged();
    }

    public void removeWeapon(int rowIndex) {
        weapons.remove(rowIndex);
        this.fireTableDataChanged();
    }

    public void sort() {
        Collections.sort(weapons);
        this.fireTableDataChanged();
    }

    public ArrayList<Weapon> getWeapons() {
        return weapons;
    }

    public Weapon[] getRangedWeaponsNotInList() {
        int count = 0;
        for(Weapon weap : weapons) {
            if(weap.isRanged && !weap.fromList) {
                count++;
            }
        }
        Weapon[] rv = new Weapon[count];
        count = 0;
        for(Weapon weap : weapons) {
            if(weap.isRanged && !weap.fromList) {
                rv[count++] = weap;
            }
        }
        return rv;
    }

    public Weapon[] getMeleeWeaponsNotInList() {
        int count = 0;
        for(Weapon weap : weapons) {
            if(!weap.isRanged && !weap.fromList) {
                count++;
            }
        }
        Weapon[] rv = new Weapon[count];
        count = 0;
        for(Weapon weap : weapons) {
            if(!weap.isRanged && !weap.fromList) {
                rv[count++] = weap;
            }
        }
        return rv;
    }

    public void addWeapons(Weapon[] weapons) {
        for(Weapon weap : weapons) {
            weap.sortOrder = this.weapons.size() + 1;
            this.weapons.add(weap);
        }
        this.fireTableDataChanged();
    }
    
}
