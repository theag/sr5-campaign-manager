/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sr5CampaignManager.tableModels;

import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.table.AbstractTableModel;
import sr5CampaignManager.objects.Skill;
import java.util.Collection;

/**
 *
 * @author nbp184
 */
public class SkillTableModel extends AbstractTableModel {
    
    public static final int NAME_COLUMN = 0;
    public static final int VALUE_COLUMN = 1;
    public static final int DICE_POOL_COLUMN = 2;
    public static final int REMOVE_COLUMN = 3;

    private static final String[] columnNames = {"Name", "Value", "Dice Pool", ""};
    private static final Class<?>[] columnClasses = {String.class, Integer.class, Integer.class, JButton.class};
    
    private ArrayList<Skill> skills;
    
    public SkillTableModel() {
        skills = new ArrayList<>();
    }
    
    @Override
    public int getRowCount() {
        return skills.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        switch(columnIndex) {
            case 0:
                return skills.get(rowIndex).name;
            case 1:
                return skills.get(rowIndex).value;
            case 2:
                return skills.get(rowIndex).dicePool;
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
        return columnIndex != NAME_COLUMN || !skills.get(rowIndex).fromList;
    }
    
    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        switch(columnIndex) {
            case NAME_COLUMN:
                skills.get(rowIndex).name = (String)aValue;
                break;
            case VALUE_COLUMN:
                skills.get(rowIndex).value = (Integer)aValue;
                break;
            case DICE_POOL_COLUMN:
                skills.get(rowIndex).dicePool = (Integer)aValue;
                break;
        }
    }
    
    public void addSkill(boolean fromList, String name) {
        skills.add(new Skill(fromList, name));
        this.fireTableRowsInserted(skills.size()-1, skills.size()-1);
    }

    public void removeSkill(int row) {
        skills.remove(row);
        this.fireTableRowsDeleted(row, row);
    }
    
    public String getStringOutput(String t, String nl) {
        String rv = t +"<skillList name=\"Skills\">" +nl;
        for(Skill skill : skills) {
            rv += skill.getStringOutput(t+"\t", nl) + nl;
        }
        rv += t +"</skillList>";
        return rv;
    }
    
    public String[] getSkillsNotInList() {
        int count = 0;
        for(Skill skill : skills) {
            if(!skill.fromList) {
                count++;
            }
        }
        String[] rv = new String[count];
        count = 0;
        for(Skill skill : skills) {
            if(!skill.fromList) {
                rv[count++] = skill.name;
            }
        }
        return rv;
    }

    public void addSkills(ArrayList<Skill> skills) {
        this.skills.addAll(skills);
        this.fireTableDataChanged();
    }

    public ArrayList<Skill> getSkills() {
        return skills;
    }
    
}
