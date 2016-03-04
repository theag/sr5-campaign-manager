/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sr5CampaignManager.objects;

import sr5CampaignManager.objects.maps.MapDrawingObject;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Collections;
import sr5CampaignManager.objects.maps.MapSecurity;
import sr5CampaignManager.objects.maps.NamedColour;

/**
 *
 * @author nbp184
 */
public class Map extends LinkObject {
    
    private final ArrayList<NamedColour> colourKey;
    public int width;
    public int height;
    public int boxSize;
    
    private final ArrayList<MapDrawingObject> objects;

    public static Map load(String[] input) {
        Map rv = new Map();
        int index = 0;
        rv.name = input[index++];
        rv.width = Integer.parseInt(input[index++]);
        rv.height = Integer.parseInt(input[index++]);
        rv.boxSize = Integer.parseInt(input[index++]);
        int count = Integer.parseInt(input[index++]);
        for(int i = 0; i < count; i++) {
            rv.colourKey.add(NamedColour.load(input[index++]));
        }
        count = Integer.parseInt(input[index++]);
        MapSecurity.securityColour = rv.colourKey.get(count);
        count = Integer.parseInt(input[index++]);
        for(int i = 0; i < count; i++) {
            rv.objects.add(MapDrawingObject.load(rv, input[index++]));
        }
        return rv;
    }
    
    private Map() {
        super("");
        colourKey = new ArrayList<>();
        objects = new ArrayList<>();
    }
    
    public Map(String name) {
        super(name);
        colourKey = new ArrayList<>();
        colourKey.add(new NamedColour("walls", Color.black));
        colourKey.add(new NamedColour("doors", new Color(255, 153, 52)));
        colourKey.add(new NamedColour("windows", Color.cyan));
        MapSecurity.securityColour = new NamedColour("security", Color.red);
        colourKey.add(MapSecurity.securityColour);
        width = 100;
        height = 100;
        boxSize = 100;
        objects = new ArrayList<>();
    }

    @Override
    public boolean isObjectOfLink(String link) {
        return link.compareTo(name +".map") == 0;
    }

    @Override
    public String getLink() {
        return name +".map";
    }    

    @Override
    public String[] save() {
        String[] rv = new String[4 + 1 + colourKey.size() + 1 + 1 + objects.size()];
        rv[0] = name;
        rv[1] = ""+width;
        rv[2] = ""+height;
        rv[3] = ""+boxSize;
        rv[4] = ""+colourKey.size();
        int index = 5;
        for(NamedColour colour : colourKey) {
            rv[index++] = colour.save();
        }
        rv[index++] = ""+colourKey.indexOf(MapSecurity.securityColour);
        rv[index++] = ""+objects.size();
        for(MapDrawingObject obj : objects) {
            rv[index++] = obj.save();
        }
        return rv;
    }

    public int getObjectCount() {
        return objects.size();
    }

    public MapDrawingObject getObject(int i) {
        return objects.get(i);
    }

    public int getNamedColourCount() {
        return colourKey.size();
    }

    public NamedColour getNamedColour(int index) {
        return colourKey.get(index);
    }

    public int getIndexOfNamedColour(NamedColour namedColour) {
        return colourKey.indexOf(namedColour);
    }

    public void addObject(MapDrawingObject object) {
        if(object.setParent(this)) {
            objects.add(0, object);
        }
    }
    
    public void sortObjects() {
        Collections.sort(objects);
    }

    public void addNamedColour(NamedColour namedColour) {
        colourKey.add(namedColour);
    }

    public boolean isUsingColour(NamedColour namedColour) {
        for(MapDrawingObject obj : objects) {
            if(obj.colour == namedColour) {
                return true;
            }
        }
        return false;
    }

    public void removeNamedColour(int index) {
        colourKey.remove(index);
    }

    public int removeObject(MapDrawingObject obj) {
        int index = objects.indexOf(obj);
        objects.remove(obj);
        return index;
    }

    public int getIndexOfObject(MapDrawingObject object) {
        return objects.indexOf(object);
    }
    
}
