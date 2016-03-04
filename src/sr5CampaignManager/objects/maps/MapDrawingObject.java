/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sr5CampaignManager.objects.maps;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.util.StringTokenizer;
import sr5CampaignManager.Resources;
import sr5CampaignManager.objects.Map;

/**
 *
 * @author nbp184
 */
public abstract class MapDrawingObject implements Comparable<MapDrawingObject> {

    public static MapDrawingObject load(Map map, String input) {
        StringTokenizer tokens = new StringTokenizer(input, Resources.groupSep);
        MapDrawingObject obj;
        String type = tokens.nextToken();
        switch(type) {
            case "MapBox":
                obj = MapBox.loadBox(tokens.nextToken(), Resources.unitSep);
                break;
            case "MapLine":
                obj = MapLine.loadLine(tokens.nextToken(), Resources.unitSep);
                break;
            case "MapOval":
                obj = MapOval.loadOval(tokens.nextToken(), Resources.unitSep);
                break;
            case "MapSecurity":
                obj = MapSecurity.loadSecurity(tokens.nextToken(), Resources.unitSep);
                break;
            case "MapText":
                obj = MapText.loadText(tokens.nextToken(), Resources.unitSep);
                break;
            default:
                return null;
        }
        obj.parent = map;
        obj.colour = map.getNamedColour(Integer.parseInt(tokens.nextToken()));
        obj.lineWidth = Integer.parseInt(tokens.nextToken());
        obj.layer = Integer.parseInt(tokens.nextToken());
        return obj;
    }
    
    private Map parent;
    public NamedColour colour;
    public int lineWidth;
    public int layer;

    public MapDrawingObject() {
        parent = null;
        colour = null;
        lineWidth = 0;
        layer = 0;
    }

    public boolean setParent(Map parent) {
        if(this.parent == null) {
            this.parent = parent;
            return true;
        } else {
            return false;
        }
    }

    public Map getParent() {
        return parent;
    }

    public void draw(Graphics2D g, Color background) {
        g.setColor(colour.colour);
        g.setStroke(new BasicStroke(lineWidth, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER));
        objectDraw(g, parent.boxSize, background);
    }
    
    public void draw(Graphics2D g, int boxSize, Color background) {
        g.setColor(colour.colour);
        g.setStroke(new BasicStroke(lineWidth));
        objectDraw(g, boxSize, background);
    }

    protected abstract void objectDraw(Graphics2D g, int boxSize, Color background);

    @Override
    public int compareTo(MapDrawingObject o) {
        return o.layer - layer;
    }

    public String save() {
        String rv = getClass().getSimpleName() +Resources.groupSep;
        rv += saveObject(Resources.unitSep);
        rv += Resources.groupSep +parent.getIndexOfNamedColour(colour)
                +Resources.groupSep +lineWidth +Resources.groupSep +layer;
        return rv;
    }
    
    protected abstract String saveObject(String delimiter);

}
