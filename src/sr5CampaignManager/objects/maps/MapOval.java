/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sr5CampaignManager.objects.maps;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.Ellipse2D;
import java.util.StringTokenizer;

/**
 *
 * @author nbp184
 */
public class MapOval extends MapDrawingObject {

    static MapDrawingObject loadOval(String input, String delimiter) {
        StringTokenizer tokens = new StringTokenizer(input, delimiter);
        return new MapOval(Float.parseFloat(tokens.nextToken()), Float.parseFloat(tokens.nextToken()),
                Float.parseFloat(tokens.nextToken()), Float.parseFloat(tokens.nextToken()),
                Boolean.parseBoolean(tokens.nextToken()));
    }
    
    public final float[] centre;
    public final float[] radius;
    public boolean filled;
    
    public MapOval(float x, float y, float xRadius, float yRadius, boolean filled) {
        super();
        centre = new float[]{x, y};
        radius = new float[]{xRadius, yRadius};
        this.filled = filled;
    }

    @Override
    protected void objectDraw(Graphics2D g, int boxSize, Color background) {
        int x = Math.round((centre[0]-radius[0])*boxSize);
        int y = Math.round((centre[1]-radius[1])*boxSize);
        int cWidth = Math.round(radius[0]*boxSize)*2;
        int cHeight = Math.round(radius[1]*boxSize)*2;
        if(cWidth < 0) {
            x += cWidth;
            cWidth = -cWidth;
        }
        if(cHeight < 0) {
            y += cHeight;
            cHeight = -cHeight;
        }
        g.drawOval(x, y, cWidth, cHeight);
        if(filled) {
            Shape clip = g.getClip();
            g.clip(new Ellipse2D.Float(x, y, cWidth, cHeight));
            int i1 = Math.round(centre[0]-radius[0]);
            while(i1*boxSize > x) {
                i1--;
            }
            int j1 = Math.round(centre[1]-radius[1]);
            while(j1*boxSize > y) {
                j1--;
            }
            int i2 = i1 + Math.round(radius[0]*2);
            while(i2*boxSize < x+cWidth) {
                i2++;
            }
            int j2 = j1 + Math.round(radius[1]*2);
            while(j2*boxSize < y+cHeight) {
                j2++;
            }
            for(int xv = i1*boxSize; xv < i2*boxSize; xv += boxSize) {
                for(int yv = j1*boxSize; yv < j2*boxSize; yv += boxSize) {
                    g.drawLine(xv, yv+boxSize, xv+boxSize, yv);
                }
            }
            g.setClip(clip);
        }
    }
    
    @Override
    public String toString() {
        String rv = layer +" Oval\n\t(" +centre[0] +" " +centre[1] +") [" +radius[0] +" " +radius[1] +"]";
        if(filled) {
            rv += " filled";
        }
        return rv;
    }

    @Override
    protected String saveObject(String delimiter) {
        return centre[0] +delimiter +centre[1] +delimiter +radius[0] +delimiter +radius[1] +delimiter +filled;
    }
    
}
