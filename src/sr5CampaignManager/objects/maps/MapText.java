/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sr5CampaignManager.objects.maps;

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.util.StringTokenizer;

/**
 *
 * @author nbp184
 */
public class MapText extends MapDrawingObject {
    
    public static final int LEFT = 0;
    public static final int CENTRE = 1;
    public static final int RIGHT = 2;
    
    public static final int TOP = 0;
    public static final int MIDDLE = 1;
    public static final int BASELINE = 2;

    static MapDrawingObject loadText(String input, String delimiter) {
        StringTokenizer tokens = new StringTokenizer(input, delimiter);
        return new MapText(tokens.nextToken(), Integer.parseInt(tokens.nextToken()),
                Integer.parseInt(tokens.nextToken()), Integer.parseInt(tokens.nextToken()),
                Integer.parseInt(tokens.nextToken()), Integer.parseInt(tokens.nextToken()),
                Boolean.parseBoolean(tokens.nextToken()));
    }
    
    public String text;
    public int fontSize;
    public final int[] point;
    public final int[] orientation;
    public boolean rotate;
    
    public MapText(String text, int fontSize, int x, int y, int horizontal, int vertical, boolean rotate) {
        this.text = text;
        this.fontSize = fontSize;
        point = new int[]{x, y};
        orientation = new int[]{horizontal, vertical};
        this.rotate = rotate;
    }

    @Override
    protected void objectDraw(Graphics2D g, int boxSize, Color background) {
        if(fontSize >= 1) {
            g.setFont(g.getFont().deriveFont(fontSize*Toolkit.getDefaultToolkit().getScreenResolution()/72.0f));
        }
        FontMetrics fm = g.getFontMetrics();
        Rectangle2D bounds = fm.getStringBounds(text, g);
        int x = point[0]*boxSize;
        int y = point[1]*boxSize;
        if(rotate) {
            g.rotate(-Math.PI/2.0, x, y);
        }   
        switch(orientation[0]) {
            case CENTRE:
                x -= bounds.getCenterX();
                break;
            case RIGHT:
                x -= bounds.getWidth();
                break;
        }
        switch(orientation[1]) {
            case TOP:
                y += bounds.getHeight();
                break;
            case MIDDLE:
                y -= bounds.getCenterY();
                break;
        }     
        g.drawString(text, x, y);
        if(rotate) {
            g.setTransform(AffineTransform.getRotateInstance(0));
        }
    }
    
    @Override
    public String toString() {
        return layer +" " +text +"\n\t(" +point[0] +" " +point[1] +")";
    }

    public void drawCursor(Graphics2D g, int cursorLocation, int boxSize) {
        if(fontSize >= 1) {
            g.setFont(g.getFont().deriveFont(fontSize*Toolkit.getDefaultToolkit().getScreenResolution()/72.0f));
        }
        FontMetrics fm = g.getFontMetrics();
        Rectangle2D bounds = fm.getStringBounds(text, g);
        int x = point[0]*boxSize;
        int y = point[1]*boxSize;
        if(rotate) {
            g.rotate(-Math.PI/2.0, x, y);
        }   
        switch(orientation[0]) {
            case CENTRE:
                x -= bounds.getCenterX();
                break;
            case RIGHT:
                x -= bounds.getWidth();
                break;
        }
        switch(orientation[1]) {
            case TOP:
                y += bounds.getHeight();
                break;
            case MIDDLE:
                y -= bounds.getCenterY();
                break;
        }     
        bounds = fm.getStringBounds(text.substring(0, cursorLocation), g);
        g.drawLine(x+(int)bounds.getWidth(), y-(int)bounds.getHeight(), x+(int)bounds.getWidth(), y);
        if(rotate) {
            g.setTransform(AffineTransform.getRotateInstance(0));
        }
    }

    @Override
    protected String saveObject(String delimiter) {
        return text +delimiter +fontSize +delimiter +point[0] +delimiter +point[1] +delimiter +orientation[0] +delimiter +orientation[1] +delimiter +rotate;
    }
    
}
