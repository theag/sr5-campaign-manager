/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sr5CampaignManager.models;

import java.awt.Color;
import javax.swing.AbstractListModel;
import javax.swing.ComboBoxModel;
import sr5CampaignManager.objects.Map;
import sr5CampaignManager.objects.maps.NamedColour;

/**
 *
 * @author nbp184
 */
public class NamedColourComboBoxModel extends AbstractListModel<NamedColour> implements ComboBoxModel<NamedColour> {

    public static final NamedColour newColour = new NamedColour("Add New", Color.white);
    
    private Map map;
    private int selectedIndex;
    private boolean addNew;
    
    public NamedColourComboBoxModel(Map map) {
        this.map = map;
        this.selectedIndex = 0;
        addNew = false;
    }
    
    public NamedColourComboBoxModel(Map map, boolean addNew) {
        this.map = map;
        this.selectedIndex = 0;
        this.addNew = addNew;
    }

    @Override
    public int getSize() {
        if(addNew) {
            return map.getNamedColourCount()+1;
        } else {
            return map.getNamedColourCount();
        }
    }

    @Override
    public NamedColour getElementAt(int index) {
        if(addNew && index == map.getNamedColourCount()) {
            return newColour;
        } else {
            return map.getNamedColour(index);
        }
    }

    @Override
    public void setSelectedItem(Object anItem) {
        if(anItem == null) {
            selectedIndex = -1;
        } else if(anItem == newColour) {
            selectedIndex = map.getNamedColourCount();
        } else {
            selectedIndex = map.getIndexOfNamedColour((NamedColour)anItem);
        }
    }

    @Override
    public Object getSelectedItem() {
        if(selectedIndex < 0) {
            return null;
        }else if(selectedIndex == map.getNamedColourCount()) {
            return newColour;
        } else {
            return map.getNamedColour(selectedIndex);
        }
    }

    public void add(NamedColour namedColour) {
        map.addNamedColour(namedColour);
        this.fireIntervalAdded(this, map.getNamedColourCount()-1, map.getNamedColourCount()-1);
    }

    public void remove(int index) {
        map.removeNamedColour(index);
        this.fireIntervalRemoved(this, index, index);
    }
    
}
