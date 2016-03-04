/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sr5CampaignManager.tableModels;

import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.table.AbstractTableModel;
import java.util.Collection;
import sr5CampaignManager.objects.Augmentation;

/**
 *
 * @author nbp184
 */
public class AugmentationTableModel extends AbstractTableModel {

    private static final JButton button = new JButton();
    
    private ArrayList<Augmentation> augs;
    private int maxRowCount;
    
    public AugmentationTableModel() {
        augs = new ArrayList<>();
        maxRowCount = 0;
    }
    
    @Override
    public int getRowCount() {
        if(maxRowCount == 0) {
            return augs.size();
        } else {
            return maxRowCount;
        }
    }

    @Override
    public int getColumnCount() {
        if(maxRowCount == 0) {
            return 2;
        } else {
            return (int)Math.ceil(augs.size()*1.0/maxRowCount)*2;
        }
    }
    
    @Override
    public Class<?> getColumnClass(int columnIndex) {
        if(columnIndex%2 == 0) {
            return Object.class;
        } else {
            return JButton.class;
        }
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        if(maxRowCount == 0 && columnIndex == 0) {
            return augs.get(rowIndex).name;
        } else if(maxRowCount == 0 && columnIndex == 1) {
            return "";
        } else {
            if(columnIndex/2*maxRowCount + rowIndex < augs.size()) {
                if(columnIndex%2 == 0) {
                    return augs.get(columnIndex/2*maxRowCount + rowIndex).name;
                } else {
                    return "";
                }
            } else {
                return null;
            }
        }
    }
    
    @Override
    public String getColumnName(int column) {
        return "";
    }
    
    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        if(maxRowCount == 0) {
            return columnIndex == 1 || !augs.get(rowIndex).fromTree;
        } else {
            return columnIndex/2*maxRowCount + rowIndex < augs.size() && (columnIndex%2 == 1 || !augs.get(columnIndex/2*maxRowCount + rowIndex).fromTree);
        }
    }
    
    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        if(maxRowCount == 0) {
            augs.get(rowIndex).name = (String)aValue;
        } else {
            augs.get(columnIndex/2*maxRowCount + rowIndex).name = (String)aValue;
        }
    }
    
    public int addAugmentation(String name, boolean fromList) {
        augs.add(new Augmentation(name, fromList));
        this.fireTableStructureChanged();
        return augs.size() - 1;
    }

    public void setMaxRowCount(int maxRowCount) {
        this.maxRowCount = maxRowCount;
        this.fireTableStructureChanged();
    }

    public void removeAugmentation(int rowIndex, int columnIndex) {
        augs.remove(columnIndex/2*maxRowCount + rowIndex);
        this.fireTableStructureChanged();
    }

    public String getStringOutput(String t, String nl) {
        if(augs.isEmpty()) {
            return "";
        }
        String rv = t +"<augmentations name=\"Augmentations\">" +nl;
        for(Augmentation aug : augs) {
            rv += t +"\t<value>" +aug +"</value>" +nl;
        }
        rv += t +"</augmentations>";
        return rv;
    }

    public String[] getAugmentationsNotInList() {
        int count = 0;
        for(Augmentation aug : augs) {
            if(!aug.fromTree) {
                count ++;
            }
        }
        String [] rv = new String[count];
        count = 0;
        for(Augmentation aug : augs) {
            if(!aug.fromTree) {
                rv[count++] = aug.name;
            }
        }
        return rv;
    }

    public void addAugmentations(ArrayList<String> augmentations) {
        for(String aug : augmentations) {
            augs.add(new Augmentation(aug, false));
        }
        this.fireTableDataChanged();
    }

    public ArrayList<String> getAugmentations() {
        ArrayList<String> rv = new ArrayList<>();
        for(Augmentation aug : augs) {
            rv.add(aug.name);
        }
        return rv;
    }
    
}
