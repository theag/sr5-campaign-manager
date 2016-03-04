/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sr5CampaignManager.objects;

import java.util.StringTokenizer;

/**
 *
 * @author nbp184
 */
public class Gear implements Comparable<Gear> {

    static Gear load(String input, String delimiter) {
        StringTokenizer tokens = new StringTokenizer(input, delimiter);
        Gear gear = new Gear(Integer.parseInt(tokens.nextToken()), false);
        gear.name = Run.loadFormat(tokens.nextToken());
        return gear;
    }

    public String name;
    public int sortOrder;
    public final boolean fromList;
    
    public Gear(int sortOrder, boolean fromList) {
        name = "";
        this.sortOrder = sortOrder;
        this.fromList = fromList;
    }
    
    @Override
    public String toString() {
        return name;
    }

    @Override
    public int compareTo(Gear o) {
        return sortOrder - o.sortOrder;
    }
    
    public String getStringOutput(String t, String nl) {
        return t +"<item>" +name +"</item>";
    }
    
    public String save(String delimiter) {
        return sortOrder +delimiter +Run.saveFormat(name);
    }
    
}
