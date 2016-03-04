/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sr5CampaignManager.objects.maps;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.util.StringTokenizer;
import myutil.MyMath;
import myutil.shapes.Arc;
import myutil.shapes.CubicCurve;
import myutil.shapes.MyShape;

/**
 *
 * @author nbp184
 */
public class MapSecurity extends MapDrawingObject {
    
    public static NamedColour securityColour;
    
    public static final String[] detectionList = {"camera", "pressure sensor", "motion sensor", "olfactory sensor", "sound detector", "trip beam", "wire alarm", "magnetic anomaly detector", "millimeter wave detector"};
    public static final String[] locksList = {"cardreader", "keypad", "transponder", "key", "facial recognition", "dna scanner", "voice recognition", "print scanner"};
    
    public static boolean isLineType(MapSecurity ms) {
        return ms.type == DETECTION && isDetectionLineType(ms.device);
    }
    
    public static boolean isDetectionLineType(String device) {
        return device.compareTo(detectionList[5]) == 0 || device.compareTo(detectionList[6]) == 0;
    }
    
    public static final int DETECTION = 0;
    public static final int LOCK = 1;

    static MapDrawingObject loadSecurity(String input, String delimiter) {
        StringTokenizer tokens = new StringTokenizer(input, delimiter);
        MapSecurity ms = new MapSecurity(Integer.parseInt(tokens.nextToken()), tokens.nextToken(),
                Float.parseFloat(tokens.nextToken()), Float.parseFloat(tokens.nextToken()));
        ms.point[2] = Float.parseFloat(tokens.nextToken());
        ms.point[3] = Float.parseFloat(tokens.nextToken());
        return ms;
    }
    
    public int type;
    public String device;
    public float[] point;
    
    public MapSecurity(int type, String device, float x, float y) {
        super();
        this.type = type;
        this.device = device;
        this.point = new float[]{x, y, 0, 0};
        this.colour = securityColour;
        this.lineWidth = 1;
    }

    @Override
    protected void objectDraw(Graphics2D g, int boxSize, Color background) {
        g.setColor(securityColour.colour);
        g.setStroke(new BasicStroke());
        if(type == DETECTION) {
            drawDetection(g, boxSize, background);
        } else {
            drawLock(g, boxSize, background);
        }
    }
    
    @Override
    public String toString() {
        return layer +" Security " +device +"\n\t(" +point[0] +" " +point[1] +")";
    }

    private void drawDetection(Graphics2D g, int boxSize, Color background) {
        int x = Math.round(point[0]*boxSize);
        int y = Math.round(point[1]*boxSize);
        switch(device) {
            case "camera":
                g.drawRect(x-boxSize/6-boxSize/16, y-boxSize/8, boxSize/3, boxSize/4);
                g.fillRect(x-boxSize/6-boxSize/16, y-boxSize/8, boxSize/3, boxSize/4);
                g.drawRect(x+boxSize/6-boxSize/16, y-boxSize/16, boxSize/8, boxSize/8);
                g.fillRect(x+boxSize/6-boxSize/16, y-boxSize/16, boxSize/8, boxSize/8);
                break;
            case "pressure sensor":
                g.drawOval(x-boxSize/4+8, y-boxSize/4+8, boxSize/2-16, boxSize/2-16);
                g.fillOval(x-boxSize/4+8, y-boxSize/4+8, boxSize/2-16, boxSize/2-16);
                g.setStroke(new BasicStroke(2));
                g.drawOval(x-boxSize/4, y-boxSize/4, boxSize/2, boxSize/2);
                g.drawOval(x-boxSize/4+4, y-boxSize/4+4, boxSize/2-8, boxSize/2-8);
                break;
            case "motion sensor":
                g.setStroke(new BasicStroke(2));
                for(int adjust = 0; adjust < boxSize/4 - 6; adjust += 6) {
                    g.drawArc(x-boxSize/4+adjust, y-boxSize/4+adjust, boxSize/2-2*adjust, boxSize/2-2*adjust, 120, 120);
                    g.drawArc(x-boxSize/4+adjust, y-boxSize/4+adjust, boxSize/2-2*adjust, boxSize/2-2*adjust, 60, -120);
                }
                int bodySize = 8;
                g.setColor(background);
                g.setStroke(new BasicStroke(3));
                g.drawOval(x+boxSize/4-3*bodySize/2, y-boxSize/4+bodySize/2-2, bodySize, bodySize);
                g.setStroke(new BasicStroke(bodySize+2, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL));
                g.drawLine(x+boxSize/4-4*bodySize/2, y-boxSize/4+4*bodySize/2, x+boxSize/4-3*bodySize/2, y-boxSize/4+3*bodySize/2);
                g.setStroke(new BasicStroke(bodySize+2, BasicStroke.CAP_ROUND, BasicStroke.JOIN_BEVEL));
                int[][] body = new int[][]{{x+boxSize/4-4*bodySize/2, x - bodySize, x - 3*bodySize},
                    {y-boxSize/4+4*bodySize/2, y + bodySize, y + bodySize}};
                g.drawPolyline(body[0], body[1], body[0].length);
                int[][] leg = new int[][]{{x, x+3*bodySize/2, x + bodySize},
                    {y, y+3*bodySize/2, y + boxSize/4}};
                g.drawPolyline(leg[0], leg[1], leg[0].length);
                g.setStroke(new BasicStroke(bodySize/2+2, BasicStroke.CAP_ROUND, BasicStroke.JOIN_BEVEL));
                int[][] arms = new int[][]{{x-bodySize/2, x+bodySize/2, x+boxSize/4-3*bodySize/2, x+boxSize/4-bodySize/2, x+boxSize/4+bodySize/2},
                    {y-bodySize, y-4*bodySize/2, y-boxSize/4+3*bodySize/2, y-boxSize/4+5*bodySize/2, y-boxSize/4+3*bodySize/2}};
                g.drawPolyline(arms[0], arms[1], arms[0].length);
                
                g.setColor(MapSecurity.securityColour.colour);
                g.setStroke(new BasicStroke(2));
                g.drawOval(x+boxSize/4-3*bodySize/2, y-boxSize/4+bodySize/2-2, bodySize, bodySize);
                g.fillOval(x+boxSize/4-3*bodySize/2, y-boxSize/4+bodySize/2-2, bodySize, bodySize);
                g.setStroke(new BasicStroke(bodySize, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL));
                g.drawLine(x+boxSize/4-4*bodySize/2, y-boxSize/4+4*bodySize/2, x+boxSize/4-3*bodySize/2, y-boxSize/4+3*bodySize/2);
                g.setStroke(new BasicStroke(bodySize, BasicStroke.CAP_ROUND, BasicStroke.JOIN_BEVEL));
                g.drawPolyline(body[0], body[1], body[0].length);
                g.drawPolyline(leg[0], leg[1], leg[0].length);
                g.setStroke(new BasicStroke(bodySize/2, BasicStroke.CAP_ROUND, BasicStroke.JOIN_BEVEL));
                g.drawPolyline(arms[0], arms[1], arms[0].length);
                break;
            case "olfactory sensor":
                g.setStroke(new BasicStroke(2));
                for(int adjust = 0; adjust < boxSize/4 - 6; adjust += 6) {
                    g.drawArc(x-boxSize/4+adjust, y-boxSize/4+adjust, boxSize/2-2*adjust, boxSize/2-2*adjust, 120, 120);
                    g.drawArc(x-boxSize/4+adjust, y-boxSize/4+adjust, boxSize/2-2*adjust, boxSize/2-2*adjust, 60, -120);
                }
                MyShape stinkLine = new CubicCurve(x, y+boxSize/4-4, x+boxSize/4-2, y+boxSize/4-3-boxSize/8, x-boxSize/8+1, y-boxSize/4+boxSize/8-1, x, y-boxSize/4);
                stinkLine = stinkLine.join(new CubicCurve(x, y+boxSize/4-4, x+boxSize/8-1, y+boxSize/4-3-boxSize/8, x-boxSize/4+2, y-boxSize/4+boxSize/8-1, x, y-boxSize/4));
                g.setColor(background);
                stinkLine.draw(g);
                g.setStroke(new BasicStroke());
                g.setColor(MapSecurity.securityColour.colour);
                stinkLine.fill(g);
                stinkLine.draw(g);
                stinkLine.shift(-boxSize/6, 2);
                g.setColor(background);
                stinkLine.draw(g);
                g.setStroke(new BasicStroke());
                g.setColor(MapSecurity.securityColour.colour);
                stinkLine.fill(g);
                stinkLine.draw(g);
                stinkLine.shift(boxSize/3, 2);
                g.setColor(background);
                stinkLine.draw(g);
                g.setStroke(new BasicStroke());
                g.setColor(MapSecurity.securityColour.colour);
                stinkLine.fill(g);
                stinkLine.draw(g);
                break;
            case "sound detector":
                int[][] speaker = new int[][]{{x, x-boxSize/6, x-boxSize/3, x-boxSize/3, x-boxSize/6, x},
                    {y-boxSize/4, y-boxSize/4+boxSize/6, y-boxSize/4+boxSize/6, y+boxSize/4-boxSize/6, y+boxSize/4-boxSize/6, y+boxSize/4}};
                g.drawPolygon(speaker[0], speaker[1], speaker[0].length);
                g.fillPolygon(speaker[0], speaker[1], speaker[0].length);
                int waveWidth = boxSize/12;
                g.setStroke(new BasicStroke());
                g.setStroke(new BasicStroke(waveWidth, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER));
                g.drawArc(x+waveWidth-boxSize/4, y-boxSize/4+waveWidth/2, boxSize/2-waveWidth, boxSize/2-waveWidth, -90, 180);
                Shape clip = g.getClip();
                g.clipRect(x+waveWidth/2, y-2*waveWidth, 2*waveWidth, 4*waveWidth);
                g.drawOval(x+3*waveWidth-boxSize/4, y-boxSize/4+5*waveWidth/2, boxSize/2-5*waveWidth, boxSize/2-5*waveWidth);
                g.fillOval(x+3*waveWidth-boxSize/4, y-boxSize/4+5*waveWidth/2, boxSize/2-5*waveWidth, boxSize/2-5*waveWidth);
                g.setClip(clip);
                break;
            case "trip beam":
                g.setStroke(new BasicStroke(lineWidth, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0f, new float[]{lineWidth, lineWidth}, lineWidth/2f));
                g.drawLine(x, y, Math.round(point[2]*boxSize), Math.round(point[3]*boxSize));
                break;
            case "wire alarm":
                int x2 = Math.round(point[2]*boxSize);
                int y2 = Math.round(point[3]*boxSize);
                
                g.setStroke(new BasicStroke(lineWidth, BasicStroke.CAP_SQUARE, BasicStroke.JOIN_MITER));
                g.setColor(Color.cyan);
                g.drawLine(x, y, x2, y2);
                g.setColor(MapSecurity.securityColour.colour);
                
                g.setStroke(new BasicStroke(lineWidth/2, BasicStroke.CAP_SQUARE, BasicStroke.JOIN_MITER));
                int xs_t = MyMath.round(x + 3*lineWidth/4*(y-y2)/Math.sqrt((x2-x)*(x2-x)+(y2-y)*(y2-y)));
                int ys_t = MyMath.round(y - 3*lineWidth/4*(x2-x)/Math.sqrt((x2-x)*(x2-x)+(y2-y)*(y2-y)));
                int x2s_t = MyMath.round(x2 + 3*lineWidth/4*(y-y2)/Math.sqrt((x2-x)*(x2-x)+(y2-y)*(y2-y)));
                int y2s_t = MyMath.round(y2 - 3*lineWidth/4*(x2-x)/Math.sqrt((x2-x)*(x2-x)+(y2-y)*(y2-y)));
                g.drawLine(xs_t, ys_t, x2s_t, y2s_t);
                int xs_b = MyMath.round(x - 3*lineWidth/4*(y-y2)/Math.sqrt((x2-x)*(x2-x)+(y2-y)*(y2-y)));
                int ys_b = MyMath.round(y + 3*lineWidth/4*(x2-x)/Math.sqrt((x2-x)*(x2-x)+(y2-y)*(y2-y)));
                int x2s_b = MyMath.round(x2 - 3*lineWidth/4*(y-y2)/Math.sqrt((x2-x)*(x2-x)+(y2-y)*(y2-y)));
                int y2s_b = MyMath.round(y2 + 3*lineWidth/4*(x2-x)/Math.sqrt((x2-x)*(x2-x)+(y2-y)*(y2-y)));
                g.drawLine(xs_b, ys_b, x2s_b, y2s_b);
                
                g.drawLine(xs_b, ys_b, (xs_t+x2s_t)/2, (ys_t+y2s_t)/2);
                g.drawLine((xs_b+x2s_b)/2, (ys_b+y2s_b)/2, x2s_t, y2s_t);
                break;
            case "magnetic anomaly detector":
                g.setStroke(new BasicStroke(2));
                for(int adjust = 0; adjust < boxSize/4 - 6; adjust += 6) {
                    g.drawArc(x-boxSize/4+adjust, y-boxSize/4+adjust, boxSize/2-2*adjust, boxSize/2-2*adjust, 120, 120);
                    g.drawArc(x-boxSize/4+adjust, y-boxSize/4+adjust, boxSize/2-2*adjust, boxSize/2-2*adjust, 60, -120);
                }
                int[][] m = new int[][]{{x-boxSize/6, x-boxSize/6, x, x+boxSize/6, x+boxSize/6},
                    {y+boxSize/4, y-boxSize/4, y, y-boxSize/4, y+boxSize/4}};
                g.setColor(background);
                g.setStroke(new BasicStroke(6, BasicStroke.CAP_SQUARE, BasicStroke.JOIN_BEVEL));
                g.drawPolyline(m[0], m[1], m[0].length);
                
                g.setColor(MapSecurity.securityColour.colour);
                g.setStroke(new BasicStroke(4, BasicStroke.CAP_SQUARE, BasicStroke.JOIN_BEVEL));
                g.drawPolyline(m[0], m[1], m[0].length);
                break;
            case "millimeter wave detector":
                 g.setStroke(new BasicStroke(2));
                for(int adjust = 0; adjust < boxSize/4 - 6; adjust += 6) {
                    g.drawArc(x-boxSize/4+adjust, y-boxSize/4+adjust, boxSize/2-2*adjust, boxSize/2-2*adjust, 120, 120);
                    g.drawArc(x-boxSize/4+adjust, y-boxSize/4+adjust, boxSize/2-2*adjust, boxSize/2-2*adjust, 60, -120);
                }
                break;
        }
    }

    private void drawLock(Graphics2D g, int boxSize, Color background) {
        int x = Math.round(point[0]*boxSize);
        int y = Math.round(point[1]*boxSize);
        
        /*g.drawRect(x-boxSize/4, y, boxSize/2, boxSize/3);
        g.drawRect(x+boxSize/4-boxSize/6, y-boxSize/12, boxSize/6, boxSize/12);
        MyShape arc = new Arc(x-boxSize/4, y-boxSize/4-boxSize/12, boxSize/2, boxSize/2, 0, 180);
        arc = arc.join(new Arc(x-boxSize/12, y-boxSize/4+boxSize/12+1, boxSize/6+1, boxSize/6+1, 180, -180));
        arc.draw(g);*/
        int x1 = x-boxSize/8;
        int x2 = x-boxSize/8 + boxSize/12;
        int x3 = x+boxSize/8 - boxSize/12;
        int x4 = x+boxSize/8;
        g.drawRect(x1, y, x4-x1+1, boxSize/6);
        g.fillRect(x1, y, x4-x1+1, boxSize/6);
        g.drawRect(x3, y-boxSize/24, x4-x3+1, boxSize/24);
        g.fillRect(x3, y-boxSize/24, x4-x3+1, boxSize/24);
        MyShape arc = new Arc(x1, y-boxSize/8-boxSize/24, x4-x1+1, boxSize/4, 0, 180);
        arc = arc.join(new Arc(x2, y-boxSize/8+boxSize/24+1, x3-x2, 2*boxSize/24, 180, -180));
        arc.shift(0, -1);
        arc.drawClosed(g);
        arc.fill(g);
        
        g.setColor(colour.colour);
        int height = boxSize/6 - 2;
        int width = x4-x1+1 - 2;
        x1 += 1;
        y += 1;
        switch(device) {
            case "cardreader":
                g.drawRoundRect(x-height/4, y, height/2, height, height/4, height/4);
                g.drawRect(x, y, height/5, height);
                g.fillRect(x, y, height/5, height);
                break;
            case "keypad":
                for(int xd = x-height/2; xd <= x+height/2; xd += height/2) {
                    g.fillRect(xd, y, height/4, height/4);
                    g.fillRect(xd, y+height/2-height/8, height/4, height/4);
                    g.fillRect(xd, y+height-height/4, height/4, height/4);
                }
                break;
            case "transponder":
                int[][] bluetooth = new int[][]{{x-height/4, x+height/4, x, x, x+height/4, x-height/4},
                    {y+height/4, y+3*height/4, y+height, y, y+height/4, y+3*height/4}};
                g.drawPolyline(bluetooth[0], bluetooth[1], bluetooth[0].length);
                break;
            case "key":
                g.drawOval(x-height/4, y, height/2, height/2);
                g.fillOval(x-height/4, y, height/2, height/2);
                g.drawRect(x-height/8, y+height/2, height/4, height/2);
                g.fillRect(x-height/8, y+height/2, height/4, height/2);
                break;
            case "facial recognition":
                g.drawOval(x-height/2, y, height, height);
                g.drawLine(x-height/4, y+height/3, x-height/4, y+height/3);
                g.drawLine(x+height/4, y+height/3, x+height/4, y+height/3);
                g.drawLine(x-height/4, y+2*height/3, x+height/4, y+2*height/3);
                break;
            case "dna scanner":
                float fontSize = height*1f;
                g.setFont(g.getFont().deriveFont(fontSize));
                FontMetrics fm = g.getFontMetrics();
                while(fm.stringWidth("DNA") > width) {
                    fontSize--;
                    g.setFont(g.getFont().deriveFont(fontSize));
                    fm = g.getFontMetrics();
                }
                g.drawString("DNA", x-fm.stringWidth("DNA")/2, y+height);
                break;
            case "voice recognition":
                int rHeight = 2*height/3;
                int rWidth = height/2;
                g.drawRoundRect(x-rWidth/2, y, rWidth, rHeight, rWidth, rWidth);
                g.drawArc(x-rWidth/2-2, y+rHeight-rWidth-2, rWidth+4, rWidth+4, 0, -180);
                g.drawLine(x, y+rHeight+2, x, y+height);
                g.drawLine(x-rWidth/2, y+height, x+rWidth/2, y+height);
                break;
            case "print scanner":
                g.drawRoundRect(x-height/4, y, height/2, height, height/2, height/2);
                g.fillRoundRect(x-height/4, y, height/2, height, height/2, height/2);
                int strokeWidth = Math.max(height/10, 3);
                g.setStroke(new BasicStroke(strokeWidth, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
                int[][] thumb = new int[][]{{x, x-height/4-strokeWidth, x-height/4-strokeWidth},
                    {y+height-strokeWidth/2, y+height-strokeWidth/2, y+height/2}};
                g.drawPolyline(thumb[0], thumb[1], thumb[0].length);
                break;
        }
    }

    @Override
    protected String saveObject(String delimiter) {
        return type +delimiter +device +delimiter +point[0] +delimiter +point[1] +delimiter +point[2] +delimiter +point[3];
    }
    
}
