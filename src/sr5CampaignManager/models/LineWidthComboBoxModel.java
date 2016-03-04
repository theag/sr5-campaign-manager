/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sr5CampaignManager.models;

import javax.swing.AbstractListModel;
import javax.swing.ComboBoxModel;

/**
 *
 * @author nbp184
 */
public class LineWidthComboBoxModel extends AbstractListModel<String> implements ComboBoxModel<String> {
    
    public static final int[] values = {1, 2, 3, 5, 7, 10};
    public static final int defaultIndex = 3;
    
    private int selectedIndex;
    private int value;
    
    public LineWidthComboBoxModel() {
        selectedIndex = defaultIndex;
        value = 1;
    }

    @Override
    public int getSize() {
        return values.length + 1;
    }

    @Override
    public String getElementAt(int index) {
        if(index < values.length) {
            return ""+values[index];
        } else {
            return "Custom...";
        }
    }

    @Override
    public void setSelectedItem(Object anItem) {
        if(anItem == null || !(anItem instanceof String)) {
            selectedIndex = -1;
        } else {
            String str = (String)anItem;
            for(selectedIndex = 0; selectedIndex < values.length; selectedIndex++) {
                if(str.compareTo(""+values[selectedIndex]) == 0) {
                    break;
                }
            }
        }
    }

    @Override
    public Object getSelectedItem() {
        if(selectedIndex < 0) {
            return null;
        } else if(selectedIndex < values.length) {
            return ""+values[selectedIndex];
        } else {
            System.out.println("bracket");
            return value +" - Custom";
        }
    }
    
    public boolean isCustomSelected() {
        return selectedIndex == values.length;
    }
    
    public int getValue() {
        if(selectedIndex < values.length) {
            return values[selectedIndex];
        } else {
            return value;
        }
    }
    
    public void setValue(int value) {
        this.value = value;
    }
    
}
