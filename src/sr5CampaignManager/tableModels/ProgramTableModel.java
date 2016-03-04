/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sr5CampaignManager.tableModels;

import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.table.AbstractTableModel;
import sr5CampaignManager.objects.Program;
import java.util.Collection;

/**
 *
 * @author nbp184
 */
public class ProgramTableModel extends AbstractTableModel {
    
    public static final int NAME_COLUMN = 0;
    public static final int EFFECT_COLUMN = 1;
    public static final int BUTTON_COLUMN = 2;
    
    private static final String[] columnNames = {"Name", "Effect", ""};
    private static final Class<?>[] columnClasses = {String.class, String.class, JButton.class};
    
    private ArrayList<Program> programs;
    
    public ProgramTableModel() {
        programs = new ArrayList<>();
    }

    @Override
    public int getRowCount() {
        return programs.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        switch(columnIndex) {
            case NAME_COLUMN:
                return programs.get(rowIndex).name;
            case EFFECT_COLUMN:
                return programs.get(rowIndex).effect;
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
        return columnIndex == BUTTON_COLUMN || !programs.get(rowIndex).fromList;
    }
    
    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        switch(columnIndex) {
            case NAME_COLUMN:
                programs.get(rowIndex).name = (String)aValue;
                break;
            case EFFECT_COLUMN:
                programs.get(rowIndex).effect = (String)aValue;
                break;
        }
    }

    public void addProgram() {
        programs.add(new Program("", "", false));
        this.fireTableDataChanged();
    }
    
    public void addProgram(Program prog) {
        programs.add(prog);
        this.fireTableDataChanged();
    }

    public void removeProgram(int rowIndex) {
        programs.remove(rowIndex);
        this.fireTableDataChanged();
    }

    public String getStringOutput(String t, String nl) {
        String rv = "";
        for(Program prog : programs) {
            rv += t +"<program>" +nl;
            rv += t +"\t<name>" +prog.name +"</name>" +nl;
            rv += t +"\t<effect>" +prog.effect +"</effect>" +nl;
            rv += t +"</program>" +nl;
        }
        return rv;
    }

    public Program[] getProgramsNotInList() {
        int count = 0;
        for(Program prog : programs) {
            if(!prog.fromList) {
                count++;
            }
        }
        Program[] rv = new Program[count];
        count = 0;
        for(Program prog : programs) {
            if(!prog.fromList) {
                rv[count++] = prog;
            }
        }
        return rv;
    }

    public void addPrograms(ArrayList<Program> programs) {
        this.programs.addAll(programs);
        this.fireTableDataChanged();
    }

    public ArrayList<Program> getPrograms() {
        return programs;
    }
    
}
