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
import sr5CampaignManager.objects.Gear;

/**
 *
 * @author nbp184
 */
public class GearTableModel extends AbstractTableModel {
    
    public static final int ORDER_COLUMN = 0;
    public static final int ITEM_COLUMN = 1;
    public static final int BUTTON_COLUMN = 2;
    
    private static final String[] columnNames = {"Order", "Item", ""};
    private static final Class<?>[] columnClasses = {Integer.class, String.class, JButton.class};
    
    private ArrayList<Gear> gear;
    
    public GearTableModel() {
        gear = new ArrayList<>();
    }

    @Override
    public int getRowCount() {
        return gear.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        switch(columnIndex) {
            case ORDER_COLUMN:
                return gear.get(rowIndex).sortOrder;
            case ITEM_COLUMN:
                return gear.get(rowIndex).name;
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
            case ORDER_COLUMN:
                gear.get(rowIndex).sortOrder = (Integer)aValue;
                break;
            case ITEM_COLUMN:
                gear.get(rowIndex).name = (String)aValue;
                break;
        }
    }

    public void addGear() {
        gear.add(new Gear(gear.size()+1, false));
        this.fireTableDataChanged();
    }
    
    public void addGear(String name) {
        Gear g = new Gear(gear.size()+1, true);
        g.name = name;
        gear.add(g);
        this.fireTableDataChanged();
    }

    public void removeGear(int rowIndex) {
        gear.remove(rowIndex);
        this.fireTableDataChanged();
    }

    public void sort() {
        Collections.sort(gear);
        this.fireTableDataChanged();
    }

    public ArrayList<Gear> getGear() {
        return gear;
    }

    public String[] getGearNotInList() {
        int count = 0;
        for(Gear g : gear) {
            if(!g.fromList) {
                count++;
            }
        }
        String[] rv = new String[count];
        count = 0;
        for(Gear g : gear) {
            if(!g.fromList) {
                rv[count++] = g.name;
            }
        }
        return rv;
    }
    
    public void addGear(Gear[] gear) {
        for(Gear g : gear) {
            g.sortOrder = this.gear.size() + 1;
            this.gear.add(g);
        }
        this.fireTableDataChanged();
    }
    
}
