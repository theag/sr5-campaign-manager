/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sr5CampaignManager.dialogs;

import java.awt.Color;
import sr5CampaignManager.objects.Map;
import sr5CampaignManager.objects.maps.NamedColour;

/**
 *
 * @author nbp184
 */
public class MapColourScheme {
    
    public String name;
    private NamedColour[] colours;
    
    public MapColourScheme(Map map) {
        name = "Default";
        colours = new NamedColour[map.getNamedColourCount()+2];
        colours[0] = new NamedColour("background", Color.white);
        colours[1] = new NamedColour("grid lines", new Color(216, 216, 216));
        for(int i = 2; i < colours.length; i++) {
            colours[i] = map.getNamedColour(i-2).copy();
        }
    }
    
    public MapColourScheme(Map map, String name) {
        this.name = name;
        colours = new NamedColour[map.getNamedColourCount()+2];
        colours[0] = new NamedColour("background", Color.white);
        colours[1] = new NamedColour("grid lines", new Color(216, 216, 216));
        for(int i = 2; i < colours.length; i++) {
            colours[i] = map.getNamedColour(i-2).copy();
        }
    }
    
    public int getCount() {
        return colours.length;
    }
    
    public String getColourName(int index) {
        return colours[index].name;
    }
    
    public Color getColour(int index) {
        return colours[index].colour;
    }
    
    public Color getColour(String name) {
        for(NamedColour colour : colours) {
            if(colour.name.compareTo(name) == 0) {
                return colour.colour;
            }
        }
        return null;
    }
    
    public void setColour(int index, Color colour) {
        colours[index].colour = colour;
    }
    
    @Override
    public String toString() {
        return name;
    }
    
}
