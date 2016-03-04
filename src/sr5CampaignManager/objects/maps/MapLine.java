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
public class MapLine extends MapDrawingObject {

    static MapDrawingObject loadLine(String input, String delimiter) {
        StringTokenizer tokens = new StringTokenizer(input, delimiter);
        return new MapLine(Integer.parseInt(tokens.nextToken()), Integer.parseInt(tokens.nextToken()),
                Integer.parseInt(tokens.nextToken()), Integer.parseInt(tokens.nextToken()));
    }
    
    public final int[] points;
    
    public MapLine(int x1, int y1, int x2, int y2) {
        super();
        points = new int[]{x1, y1, x2, y2};
    }
    
    public MapLine(int[] points) {
        super();
        if(points.length != 4) {
            throw new ArrayIndexOutOfBoundsException("Must have 4 points: " +points.length);
        }
        this.points = new int[4];
        System.arraycopy(points, 0, this.points, 0, 4);
    }

   @Override
    public String toString() {
        return layer +" Line\n\t(" +points[0] +" " +points[1] +") -> ("+points[2] +" " +points[3] +")";
    }

    @Override
    protected void objectDraw(Graphics2D g, int boxSize, Color background) {
        g.drawLine(points[0]*boxSize, points[1]*boxSize, points[2]*boxSize, points[3]*boxSize);
    }

    @Override
    protected String saveObject(String delimiter) {
        return points[0] +delimiter +points[1] +delimiter +points[2] +delimiter +points[3];
    }
        
}
