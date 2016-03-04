/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sr5CampaignManager.objects.maps;

import java.awt.Color;
import sr5CampaignManager.Resources;

/**
 *
 * @author nbp184
 */
public class NamedColour implements Comparable<NamedColour> {

    public static NamedColour load(String input) {
        int index = input.indexOf(Resources.unitSep);
        return new NamedColour(input.substring(0, index), new Color(Integer.parseInt(input.substring(index+1))));
    }

    public String name;
    public Color colour;

    public NamedColour(String name, Color colour) {
        this.name = name;
        this.colour = colour;
    }

    @Override
    public int compareTo(NamedColour o) {
        return name.compareTo(o.name);
    }

    @Override
    public String toString() {
        return name;
    }

    public String save() {
        return name +Resources.unitSep +colour.getRGB();
    }

    public NamedColour copy() {
        return new NamedColour(name, colour);
    }
}
