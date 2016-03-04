/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sr5CampaignManager.objects.maps;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.StringTokenizer;

/**
 *
 * @author nbp184
 */
public class MapBox extends MapDrawingObject {
    
    static MapDrawingObject loadBox(String input, String delimiter) {
        StringTokenizer tokens = new StringTokenizer(input, delimiter);
        return new MapBox(Integer.parseInt(tokens.nextToken()), Integer.parseInt(tokens.nextToken()),
                Integer.parseInt(tokens.nextToken()), Integer.parseInt(tokens.nextToken()),
                Boolean.parseBoolean(tokens.nextToken()));
    }
    
    public final int[] point;
    public int width;
    public int height;
    public boolean filled;
    
    public MapBox(int x, int y, int width, int height, boolean filled) {
        super();
        point = new int[]{x, y};
        this.width = width;
        this.height = height;
        this.filled = filled;
    }

    @Override
    protected void objectDraw(Graphics2D g, int boxSize, Color background) {
        int x = point[0];
        int y = point[1];
        int cWidth = width;
        int cHeight = height;
        if(cWidth < 0) {
            x += cWidth;
            cWidth = -cWidth;
        }
        if(cHeight < 0) {
            y += cHeight;
            cHeight = -cHeight;
        }
        g.drawRect(x*boxSize, y*boxSize, cWidth*boxSize, cHeight*boxSize);
        if(filled) {
            for(int xv = x; xv < x+cWidth; xv++) {
                for(int yv = y; yv < y+cHeight; yv++) {
                    g.drawLine(xv*boxSize, (yv+1)*boxSize, (xv+1)*boxSize, yv*boxSize);
                }
            }
        }
    }
    
    @Override
    public String toString() {
        String rv = layer +" Box\n\t(" +point[0] +" " +point[1] +") [" +width +" " +height +"]";
        if(filled) {
            rv += " filled";
        }
        return rv;
    }

    public int getRight() {
        return point[0] + width;
    }

    public int getBottom() {
        return point[1] + height;
    }

    @Override
    protected String saveObject(String delimiter) {
        return point[0] +delimiter +point[1] +delimiter +width +delimiter +height +delimiter +filled;
    }
    
}
