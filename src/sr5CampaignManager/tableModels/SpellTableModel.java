/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sr5CampaignManager.tableModels;

import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.table.AbstractTableModel;
import sr5CampaignManager.objects.Spell;
import java.util.Collection;

/**
 *
 * @author nbp184
 */
public class SpellTableModel extends AbstractTableModel {
    
    public static final int NAME_COLUMN = 0;
    public static final int TYPE_COLUMN = 1;
    public static final int RANGE_COLUMN = 2;
    public static final int DAMAGE_COLUMN = 3;
    public static final int DURATION_COLUMN = 4;
    public static final int DRAIN_COLUMN = 5;
    public static final int EFFECT_COLUMN = 6;
    public static final int REMOVE_COLUMN = 7;

    private static final String[] columnNames = {"Name", "Type", "Range", "Damage", "Duration", "Drain Value", "Effect", ""};
    private static final Class<?>[] columnClasses = {String.class, String.class, String.class, String.class, String.class, String.class, String.class, JButton.class};
    
    private ArrayList<Spell> spells;
    
    public SpellTableModel() {
        spells = new ArrayList<>();
    }

    @Override
    public int getRowCount() {
        return spells.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        switch(columnIndex) {
            case NAME_COLUMN:
                return spells.get(rowIndex).name;
            case TYPE_COLUMN:
                return spells.get(rowIndex).type;
            case RANGE_COLUMN:
                return spells.get(rowIndex).range;
            case DAMAGE_COLUMN:
                return spells.get(rowIndex).damage;
            case DURATION_COLUMN:
                return spells.get(rowIndex).duration;
            case DRAIN_COLUMN:
                return spells.get(rowIndex).drain;
            case EFFECT_COLUMN:
                return spells.get(rowIndex).effect;
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
        return true;
    }
    
    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        switch(columnIndex) {
            case NAME_COLUMN:
                spells.get(rowIndex).name = (String)aValue;
                break;
            case TYPE_COLUMN:
                spells.get(rowIndex).type = (String)aValue;
                break;
            case RANGE_COLUMN:
                spells.get(rowIndex).range = (String)aValue;
                break;
            case DAMAGE_COLUMN:
                spells.get(rowIndex).damage = (String)aValue;
                break;
            case DURATION_COLUMN:
                spells.get(rowIndex).duration = (String)aValue;
                break;
            case DRAIN_COLUMN:
                spells.get(rowIndex).drain = (String)aValue;
                break;
            case EFFECT_COLUMN:
                spells.get(rowIndex).effect = (String)aValue;
                break;
        }
    }

    public void addSpell() {
        spells.add(new Spell());
        this.fireTableDataChanged();
    }

    public void removeSpell(int rowIndex) {
        spells.remove(rowIndex);
        this.fireTableDataChanged();
    }

    public String getStringOutput(String t, String nl) {
        String rv = t +"<spellList name=\"Spells\">" +nl;
        for(Spell spell : spells) {
            rv += spell.getStringOutput(t+"\t", nl) +nl;
        }
        rv += t +"</spellList>";
        return rv;
    }

    public void addSpells(ArrayList<Spell> spells) {
        this.spells.addAll(spells);
        this.fireTableDataChanged();
    }

    public ArrayList<Spell> getSpells() {
        return spells;
    }
        
}
