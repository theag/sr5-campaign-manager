/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sr5CampaignManager.panels;

import sr5CampaignManager.events.DrawingModeChangeListener;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Shape;
import java.awt.event.KeyEvent;
import sr5CampaignManager.objects.Map;
import sr5CampaignManager.objects.maps.*;

/**
 *
 * @author nbp184
 */
public class MapDrawingPanel extends javax.swing.JPanel {
    
    public static final int MODE_MASK = 0xF0;
    public static final int NORMAL_MODE = 0;
    public static final int LINE_MODE = 0x10;
    public static final int BOX_MODE = 0x20;
    public static final int OVAL_MODE = 0x30;
    public static final int TEXT_MODE = 0x40;
    public static final int SECURITY_MODE = 0x50;
    
    private static final int PLACE_MASK = 0xF;
    private static final int PLACE_1 = 0x1;
    private static final int PLACE_2 = 0x2;
    private static final int PLACE_3 = 0x3;
    

    private Map map;
    private int mode;
    private MapDrawingObject current;
    private Color gridColour;
    private int cursorLocation;
    private Point lastMouseLocation;
    
    /**
     * Creates new form MapDrawingPanel
     */
    public MapDrawingPanel(Map map) {
        initComponents();
        this.map = map;
        this.setPreferredSize(new Dimension(map.width*map.boxSize+1, map.height*map.boxSize+1));
        gridColour = new Color(216, 216, 216);
        mode = NORMAL_MODE;
        setFocusable(true);
        requestFocusInWindow();
        lastMouseLocation = new Point();
    }
    
    public int getMode() {
        return mode;
    }
    
    public void addDrawingModeChangeListener(DrawingModeChangeListener listener) {
        listenerList.add(DrawingModeChangeListener.class, listener);
    }
    
    public void resize() {
        mode = NORMAL_MODE;
        fireModeChanged();
        this.setPreferredSize(new Dimension(map.width*map.boxSize+1, map.height*map.boxSize+1));
        this.setSize(new Dimension(map.width*map.boxSize+1, map.height*map.boxSize+1));
        this.repaint();
    }
    
    public void startLinePlace(NamedColour colour, int lineWidth) {
        current = new MapLine(getNearestCoord(lastMouseLocation.x), getNearestCoord(lastMouseLocation.y), 0, 0);
        current.colour = colour;
        current.lineWidth = lineWidth;
        mode = LINE_MODE | PLACE_1;
        fireModeChanged();
        
    }
    
    public void startBoxPlace(NamedColour colour, int lineWidth, boolean filled) {
        current = new MapBox(getNearestCoord(lastMouseLocation.x), getNearestCoord(lastMouseLocation.y), 0, 0, filled);
        current.colour = colour;
        current.lineWidth = lineWidth;
        mode = BOX_MODE | PLACE_1;
        fireModeChanged();
    }
    
    public void startOvalPlace(NamedColour colour, int lineWidth, boolean filled) {
        current = new MapOval(getNearestCoordPart(lastMouseLocation.x), getNearestCoordPart(lastMouseLocation.y), 0, 0, filled);
        current.colour = colour;
        current.lineWidth = lineWidth;
        mode = OVAL_MODE | PLACE_1;
        fireModeChanged();
    }
    
    public void startTextPlace(NamedColour colour, int fontSize, int horizontal, int vertical, boolean rotate) {
        current = new MapText("Sample Text", fontSize, getNearestCoord(lastMouseLocation.x), getNearestCoord(lastMouseLocation.y), horizontal, vertical, rotate);
        current.colour = colour;
        mode = TEXT_MODE | PLACE_1;
        fireModeChanged();
    }
    
    public void startSecurityPlace(int type, String device, NamedColour colour, int lineWidth) {
        current = new MapSecurity(type, device, getNearestCoordPart(lastMouseLocation.x), getNearestCoordPart(lastMouseLocation.y));
        current.colour = colour;
        current.lineWidth = lineWidth;
        if(MapSecurity.isLineType((MapSecurity)current)) {
            mode = SECURITY_MODE | PLACE_2;
        } else {
            mode = SECURITY_MODE | PLACE_1;
        }
        fireModeChanged();
    }
    
    public void updateDevice(int type, String device) {
        if((mode & MODE_MASK) == SECURITY_MODE) {
            MapSecurity mapSecurity = (MapSecurity)current;
            mapSecurity.type = type;
            mapSecurity.device = device;
            switch(mode & PLACE_MASK) {
                case PLACE_1:
                    if(MapSecurity.isLineType(mapSecurity)) {
                        mode = SECURITY_MODE | PLACE_2;
                    }
                    break;
                case PLACE_2:
                    if(!MapSecurity.isLineType(mapSecurity)) {
                        mode = SECURITY_MODE | PLACE_1;
                    }
                    break;
                case PLACE_3:
                    if(!MapSecurity.isLineType(mapSecurity)) {
                        mode = SECURITY_MODE | PLACE_1;
                        mapSecurity.point[0] = getNearestCoordPart(lastMouseLocation.x);
                        mapSecurity.point[1] = getNearestCoordPart(lastMouseLocation.y);
                    }
                    break;
            }
            repaint();
        }
    }
    
    public void updateFontSize(int fontSize) {
        if((mode & MODE_MASK) == TEXT_MODE) {
            ((MapText)current).fontSize = fontSize;
            repaint();
        }
    }
    
    public void updateOrientation(int index, int value) {
        if((mode & MODE_MASK) == TEXT_MODE) {
            ((MapText)current).orientation[index] = value;
            repaint();
        }
    }
    
    public void updateRotate(boolean rotate) {
        if((mode & MODE_MASK) == TEXT_MODE) {
            ((MapText)current).rotate = rotate;
            repaint();
        }
    }
    
    public void updateFilled(boolean filled) {
        if((mode & MODE_MASK) == BOX_MODE) {
            ((MapBox)current).filled = filled;
            repaint();
        } else if((mode & MODE_MASK) == OVAL_MODE) {
            ((MapOval)current).filled = filled;
            repaint();
        }
    }
    
    public void updateLineWidth(int lineWidth) {
        current.lineWidth = lineWidth;
        repaint();
    }
    
    public void updateColour(NamedColour colour) {
        current.colour = colour;
        repaint();
    }
    
    public void cancelPlace() {
        mode = NORMAL_MODE;
        fireModeChanged();
        repaint();
    }
    
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        if(map != null) {
            Graphics2D g2 = (Graphics2D)g;
            Shape clip = g.getClip();
            g.clipRect(0, 0, getPreferredSize().width, getPreferredSize().height);
            g.setColor(Color.white);
            g.fillRect(0, 0, getWidth(), getHeight());
            g.setColor(gridColour);
            for(int i = 0; i < getWidth(); i += map.boxSize) {
                g.drawLine(i, 0, i, getHeight());
            }
            for(int i = 0; i < getHeight(); i += map.boxSize) {
                g.drawLine(0, i, getWidth(), i);
            }
            for(int i = map.getObjectCount()-1; i >= 0; i--) {
                map.getObject(i).draw(g2, Color.white);
            }
            
            int pointDiameter = map.boxSize/4;
            switch(mode & MODE_MASK) {
                case LINE_MODE:
                    MapLine ml = (MapLine)current;
                    switch(mode & PLACE_MASK) {
                        case PLACE_1:
                            g.setColor(Color.green);
                            g2.setStroke(new BasicStroke());
                            g.drawOval(getMouse(ml.points[0])-pointDiameter/2, getMouse(ml.points[1])-pointDiameter/2, pointDiameter, pointDiameter);
                            g.fillOval(getMouse(ml.points[0])-pointDiameter/2, getMouse(ml.points[1])-pointDiameter/2, pointDiameter, pointDiameter);
                            g.drawOval(getMouse(ml.points[0])-pointDiameter, getMouse(ml.points[1])-pointDiameter, pointDiameter*2, pointDiameter*2);
                            break;
                        case PLACE_2:
                            ml.draw(g2, map.boxSize, Color.white);
                            g.setColor(Color.green);
                            g2.setStroke(new BasicStroke());
                            g.drawOval(getMouse(ml.points[0])-pointDiameter/2, getMouse(ml.points[1])-pointDiameter/2, pointDiameter, pointDiameter);
                            g.fillOval(getMouse(ml.points[0])-pointDiameter/2, getMouse(ml.points[1])-pointDiameter/2, pointDiameter, pointDiameter);
                            g.drawOval(getMouse(ml.points[2])-pointDiameter/2, getMouse(ml.points[3])-pointDiameter/2, pointDiameter, pointDiameter);
                            g.fillOval(getMouse(ml.points[2])-pointDiameter/2, getMouse(ml.points[3])-pointDiameter/2, pointDiameter, pointDiameter);
                            g.drawOval(getMouse(ml.points[2])-pointDiameter, getMouse(ml.points[3])-pointDiameter, pointDiameter*2, pointDiameter*2);
                            break;
                    }
                    break;
                case BOX_MODE:
                    MapBox mb = (MapBox)current;
                    switch(mode & PLACE_MASK) {
                        case PLACE_1:
                            g.setColor(Color.green);
                            g2.setStroke(new BasicStroke());
                            g.drawOval(getMouse(mb.point[0])-pointDiameter/2, getMouse(mb.point[1])-pointDiameter/2, pointDiameter, pointDiameter);
                            g.fillOval(getMouse(mb.point[0])-pointDiameter/2, getMouse(mb.point[1])-pointDiameter/2, pointDiameter, pointDiameter);
                            g.drawOval(getMouse(mb.point[0])-pointDiameter, getMouse(mb.point[1])-pointDiameter, pointDiameter*2, pointDiameter*2);
                            break;
                        case PLACE_2:
                            mb.draw(g2, map.boxSize, Color.white);
                            g.setColor(Color.green);
                            g2.setStroke(new BasicStroke());
                            g.drawOval(getMouse(mb.point[0])-pointDiameter/2, getMouse(mb.point[1])-pointDiameter/2, pointDiameter, pointDiameter);
                            g.fillOval(getMouse(mb.point[0])-pointDiameter/2, getMouse(mb.point[1])-pointDiameter/2, pointDiameter, pointDiameter);
                            g.drawOval(getMouse(mb.getRight())-pointDiameter/2, getMouse(mb.getBottom())-pointDiameter/2, pointDiameter, pointDiameter);
                            g.fillOval(getMouse(mb.getRight())-pointDiameter/2, getMouse(mb.getBottom())-pointDiameter/2, pointDiameter, pointDiameter);
                            g.drawOval(getMouse(mb.getRight())-pointDiameter, getMouse(mb.getBottom())-pointDiameter, pointDiameter*2, pointDiameter*2);
                            break;
                    }
                    break;
                case OVAL_MODE:
                    MapOval mo = (MapOval)current;
                    switch(mode & PLACE_MASK) {
                        case PLACE_1:
                            g.setColor(Color.green);
                            g2.setStroke(new BasicStroke());
                            g.drawOval(getMouse(mo.centre[0])-pointDiameter/2, getMouse(mo.centre[1])-pointDiameter/2, pointDiameter, pointDiameter);
                            g.fillOval(getMouse(mo.centre[0])-pointDiameter/2, getMouse(mo.centre[1])-pointDiameter/2, pointDiameter, pointDiameter);
                            g.drawOval(getMouse(mo.centre[0])-pointDiameter, getMouse(mo.centre[1])-pointDiameter, pointDiameter*2, pointDiameter*2);
                            break;
                        case PLACE_2:
                            mo.draw(g2, map.boxSize, Color.white);
                            g.setColor(Color.green);
                            g2.setStroke(new BasicStroke());
                            g.drawOval(getMouse(mo.centre[0])-pointDiameter/2, getMouse(mo.centre[1])-pointDiameter/2, pointDiameter, pointDiameter);
                            g.fillOval(getMouse(mo.centre[0])-pointDiameter/2, getMouse(mo.centre[1])-pointDiameter/2, pointDiameter, pointDiameter);
                            g.drawOval(getMouse(mo.centre[0]+mo.radius[0])-pointDiameter/2, getMouse(mo.centre[1])-pointDiameter/2, pointDiameter, pointDiameter);
                            g.fillOval(getMouse(mo.centre[0]+mo.radius[0])-pointDiameter/2, getMouse(mo.centre[1])-pointDiameter/2, pointDiameter, pointDiameter);
                            g.drawOval(getMouse(mo.centre[0]+mo.radius[0])-pointDiameter, getMouse(mo.centre[1])-pointDiameter, pointDiameter*2, pointDiameter*2);
                            g.drawOval(getMouse(mo.centre[0]-mo.radius[0])-pointDiameter/2, getMouse(mo.centre[1])-pointDiameter/2, pointDiameter, pointDiameter);
                            g.fillOval(getMouse(mo.centre[0]-mo.radius[0])-pointDiameter/2, getMouse(mo.centre[1])-pointDiameter/2, pointDiameter, pointDiameter);
                            g.drawOval(getMouse(mo.centre[0]-mo.radius[0])-pointDiameter, getMouse(mo.centre[1])-pointDiameter, pointDiameter*2, pointDiameter*2);
                            break;
                        case PLACE_3:
                            mo.draw(g2, map.boxSize, Color.white);
                            g.setColor(Color.green);
                            g2.setStroke(new BasicStroke());
                            g.drawOval(getMouse(mo.centre[0])-pointDiameter/2, getMouse(mo.centre[1])-pointDiameter/2, pointDiameter, pointDiameter);
                            g.fillOval(getMouse(mo.centre[0])-pointDiameter/2, getMouse(mo.centre[1])-pointDiameter/2, pointDiameter, pointDiameter);
                            g.drawOval(getMouse(mo.centre[0]+mo.radius[0])-pointDiameter/2, getMouse(mo.centre[1])-pointDiameter/2, pointDiameter, pointDiameter);
                            g.fillOval(getMouse(mo.centre[0]+mo.radius[0])-pointDiameter/2, getMouse(mo.centre[1])-pointDiameter/2, pointDiameter, pointDiameter);
                            g.drawOval(getMouse(mo.centre[0])-pointDiameter/2, getMouse(mo.centre[1]-mo.radius[1])-pointDiameter/2, pointDiameter, pointDiameter);
                            g.fillOval(getMouse(mo.centre[0])-pointDiameter/2, getMouse(mo.centre[1]-mo.radius[1])-pointDiameter/2, pointDiameter, pointDiameter);
                            g.drawOval(getMouse(mo.centre[0])-pointDiameter, getMouse(mo.centre[1]-mo.radius[1])-pointDiameter, pointDiameter*2, pointDiameter*2);
                            g.drawOval(getMouse(mo.centre[0])-pointDiameter/2, getMouse(mo.centre[1]+mo.radius[1])-pointDiameter/2, pointDiameter, pointDiameter);
                            g.fillOval(getMouse(mo.centre[0])-pointDiameter/2, getMouse(mo.centre[1]+mo.radius[1])-pointDiameter/2, pointDiameter, pointDiameter);
                            g.drawOval(getMouse(mo.centre[0])-pointDiameter, getMouse(mo.centre[1]+mo.radius[1])-pointDiameter, pointDiameter*2, pointDiameter*2);
                            break;
                    }
                    break;
                case TEXT_MODE:
                    MapText mt = (MapText)current;
                    switch(mode & PLACE_MASK) {
                        case PLACE_1:
                            g.setColor(Color.green);
                            g2.setStroke(new BasicStroke());
                            g.drawOval(getMouse(mt.point[0])-pointDiameter/2, getMouse(mt.point[1])-pointDiameter/2, pointDiameter, pointDiameter);
                            g.fillOval(getMouse(mt.point[0])-pointDiameter/2, getMouse(mt.point[1])-pointDiameter/2, pointDiameter, pointDiameter);
                            g.drawOval(getMouse(mt.point[0])-pointDiameter, getMouse(mt.point[1])-pointDiameter, pointDiameter*2, pointDiameter*2);
                            mt.draw(g2, map.boxSize, Color.white);
                            break;
                        case PLACE_2:
                            g.setColor(Color.green);
                            g.drawOval(getMouse(mt.point[0])-pointDiameter/2, getMouse(mt.point[1])-pointDiameter/2, pointDiameter, pointDiameter);
                            g.fillOval(getMouse(mt.point[0])-pointDiameter/2, getMouse(mt.point[1])-pointDiameter/2, pointDiameter, pointDiameter);
                            mt.draw(g2, map.boxSize, Color.white);
                            if(hasFocus()) {
                                mt.drawCursor(g2, cursorLocation, map.boxSize);
                            }
                            break;
                    }
                    break;
                case SECURITY_MODE:
                    MapSecurity ms = (MapSecurity)current;
                    switch(mode & PLACE_MASK) {
                        case PLACE_1:
                            g.setColor(Color.green);
                            g2.setStroke(new BasicStroke());
                            g.drawOval(getMouse(ms.point[0])-pointDiameter, getMouse(ms.point[1])-pointDiameter, pointDiameter*2, pointDiameter*2);
                            ms.draw(g2, map.boxSize, Color.white);
                            break;
                        case PLACE_2:
                            g.setColor(Color.green);
                            g2.setStroke(new BasicStroke());
                            g.drawOval(getMouse(ms.point[0])-pointDiameter/2, getMouse(ms.point[1])-pointDiameter/2, pointDiameter, pointDiameter);
                            g.fillOval(getMouse(ms.point[0])-pointDiameter/2, getMouse(ms.point[1])-pointDiameter/2, pointDiameter, pointDiameter);
                            g.drawOval(getMouse(ms.point[0])-pointDiameter, getMouse(ms.point[1])-pointDiameter, pointDiameter*2, pointDiameter*2);
                            break;
                        case PLACE_3:
                            g.setColor(Color.green);
                            g2.setStroke(new BasicStroke());
                            g.drawOval(getMouse(ms.point[0])-pointDiameter/2, getMouse(ms.point[1])-pointDiameter/2, pointDiameter, pointDiameter);
                            g.fillOval(getMouse(ms.point[0])-pointDiameter/2, getMouse(ms.point[1])-pointDiameter/2, pointDiameter, pointDiameter);
                            g.drawOval(getMouse(ms.point[2])-pointDiameter/2, getMouse(ms.point[3])-pointDiameter/2, pointDiameter, pointDiameter);
                            g.fillOval(getMouse(ms.point[2])-pointDiameter/2, getMouse(ms.point[3])-pointDiameter/2, pointDiameter, pointDiameter);
                            g.drawOval(getMouse(ms.point[2])-pointDiameter, getMouse(ms.point[3])-pointDiameter, pointDiameter*2, pointDiameter*2);
                            ms.draw(g2, map.boxSize, Color.white);
                            break;
                    }
            }
            g.setClip(clip);
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                formMouseMoved(evt);
            }
        });
        addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                formMouseClicked(evt);
            }
        });
        addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                formKeyPressed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void formMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseClicked
        if((mode & MODE_MASK) == LINE_MODE) {
            if(evt.getButton() == java.awt.event.MouseEvent.BUTTON1) {
                if((mode & PLACE_1) == PLACE_1) {
                    mode = LINE_MODE | PLACE_2;
                    repaint();
                } else if((mode & PLACE_MASK) == PLACE_2) {
                    map.addObject(current);
                    startLinePlace(current.colour, current.lineWidth);
                    repaint();
                }
            } else {
                startLinePlace(current.colour, current.lineWidth);
                repaint();
            }
        } else if((mode & MODE_MASK) == BOX_MODE) {
            if(evt.getButton() == java.awt.event.MouseEvent.BUTTON1) {
                if((mode & PLACE_MASK) == PLACE_1) {
                    mode = BOX_MODE | PLACE_2;
                    repaint();
                } else if((mode & PLACE_MASK) == PLACE_2) {
                    map.addObject(current);
                    startBoxPlace(current.colour, current.lineWidth, ((MapBox)current).filled);
                    repaint();
                }
            } else {
                startBoxPlace(current.colour, current.lineWidth, ((MapBox)current).filled);
                repaint();
            }
        } else if((mode & MODE_MASK) == OVAL_MODE) {
            if(evt.getButton() == java.awt.event.MouseEvent.BUTTON1) {
                if((mode & PLACE_MASK) == PLACE_1) {
                    mode = OVAL_MODE | PLACE_2;
                    repaint();
                } else if((mode & PLACE_MASK) == PLACE_2) {
                    if(evt.isAltDown()) {
                        map.addObject(current);
                        startOvalPlace(current.colour, current.lineWidth, ((MapOval)current).filled);
                        repaint();
                    } else {
                        mode = OVAL_MODE | PLACE_3;
                        repaint();
                    }
                } else if((mode & PLACE_MASK) == PLACE_3) {
                    map.addObject(current);
                    startOvalPlace(current.colour, current.lineWidth, ((MapOval)current).filled);
                    repaint();
                }
            } else {
                fireModeChanged();
                repaint();
            }
        } else if((mode & MODE_MASK) == TEXT_MODE) {
            MapText mt = (MapText)current;
            if(evt.getButton() == java.awt.event.MouseEvent.BUTTON1) {
                if((mode & PLACE_MASK) == PLACE_1) {
                    mode = TEXT_MODE | PLACE_2;
                    mt.text = "";
                    cursorLocation = 0;
                    requestFocusInWindow();
                    repaint();
                } else if((mode & PLACE_MASK) == PLACE_2) {
                    requestFocusInWindow();
                    repaint();
                }
            } else {
                startTextPlace(current.colour, mt.fontSize, mt.orientation[0], mt.orientation[1], mt.rotate);
                repaint();
            }
        } else if((mode & MODE_MASK) == SECURITY_MODE) {
            if(evt.getButton() == java.awt.event.MouseEvent.BUTTON1) {
                if((mode & PLACE_MASK) == PLACE_1) {
                    map.addObject(current);
                    startSecurityPlace(((MapSecurity)current).type, ((MapSecurity)current).device, current.colour, current.lineWidth);
                    repaint();
                } else if((mode & PLACE_MASK) == PLACE_2) {
                    mode = SECURITY_MODE | PLACE_3;
                    repaint();
                } else if((mode & PLACE_MASK) == PLACE_3) {
                    map.addObject(current);
                    startSecurityPlace(((MapSecurity)current).type, ((MapSecurity)current).device, current.colour, current.lineWidth);
                    repaint();
                }
            } else {
                fireModeChanged();
                repaint();
            }
        }
    }//GEN-LAST:event_formMouseClicked

    private void formMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseMoved
        lastMouseLocation.x = evt.getX();
        lastMouseLocation.y = evt.getY();
        if((mode & MODE_MASK) == LINE_MODE) {
            MapLine ml = (MapLine)current;
            if((mode & PLACE_MASK) == PLACE_1) {
                ml.points[0] = getNearestCoord(evt.getX());
                ml.points[1] = getNearestCoord(evt.getY());
                ml.points[2] = ml.points[0];
                ml.points[3] = ml.points[1];
            } else if((mode & PLACE_MASK) == PLACE_2) {
                ml.points[2] = getNearestCoord(evt.getX());
                ml.points[3] = getNearestCoord(evt.getY());
            }
            repaint();
        } else if((mode & MODE_MASK) == BOX_MODE) {
            MapBox mb = (MapBox)current;
            if((mode & PLACE_MASK) == PLACE_1) {
                mb.point[0] = getNearestCoord(evt.getX());
                mb.point[1] = getNearestCoord(evt.getY());
            } else if((mode & PLACE_MASK) == PLACE_2) {
                mb.width = getNearestCoord(evt.getX()) - mb.point[0];
                mb.height = getNearestCoord(evt.getY()) - mb.point[1];
            }
            repaint();
        } else if((mode & MODE_MASK) == OVAL_MODE) {
            MapOval mo = (MapOval)current;
            if((mode & PLACE_MASK) == PLACE_1) {
                if(evt.isShiftDown()) {
                    mo.centre[0] = getNearestCoordHalf(evt.getX());
                    mo.centre[1] = getNearestCoordHalf(evt.getY());
                } else {
                    mo.centre[0] = getNearestCoordPart(evt.getX());
                    mo.centre[1] = getNearestCoordPart(evt.getY());
                }
            } else if((mode & PLACE_MASK) == PLACE_2) {
                if(evt.isShiftDown()) {
                    mo.radius[0] = Math.abs(getNearestCoordHalf(evt.getX()) - mo.centre[0]);
                    mo.radius[1] = mo.radius[0];
                } else {
                    mo.radius[0] = Math.abs(getNearestCoordPart(evt.getX()) - mo.centre[0]);
                    mo.radius[1] = mo.radius[0];
                }
            } else if((mode & PLACE_MASK) == PLACE_3) {
                if(evt.isShiftDown()) {
                    mo.radius[1] = Math.abs(getNearestCoordHalf(evt.getY()) - mo.centre[1]);
                } else {
                    mo.radius[1] = Math.abs(getNearestCoordPart(evt.getY()) - mo.centre[1]);
                }
            }
            repaint();
        } else if((mode & MODE_MASK) == TEXT_MODE) {
            MapText mt = (MapText)current;
            if((mode & PLACE_MASK) == PLACE_1) {
                mt.point[0] = getNearestCoord(evt.getX());
                mt.point[1] = getNearestCoord(evt.getY());
            }
            repaint();
        } else if((mode & MODE_MASK) == SECURITY_MODE) {
            MapSecurity mo = (MapSecurity)current;
            if((mode & PLACE_MASK) == PLACE_1 || (mode & PLACE_MASK) == PLACE_2) {
                if(evt.isShiftDown()) {
                    mo.point[0] = getNearestCoordHalf(evt.getX());
                    mo.point[1] = getNearestCoordHalf(evt.getY());
                } else {
                    mo.point[0] = getNearestCoordPart(evt.getX());
                    mo.point[1] = getNearestCoordPart(evt.getY());
                }
                mo.point[2] = mo.point[0];
                mo.point[3] = mo.point[1];
            } else if((mode & PLACE_MASK) == PLACE_3) {
                if(evt.isShiftDown()) {
                    mo.point[2] = getNearestCoordHalf(evt.getX());
                    mo.point[3] = getNearestCoordHalf(evt.getY());
                } else {
                    mo.point[2] = getNearestCoordPart(evt.getX());
                    mo.point[3] = getNearestCoordPart(evt.getY());
                }
            }
            repaint();
        }
    }//GEN-LAST:event_formMouseMoved

    private void formKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_formKeyPressed
        if((mode & MODE_MASK) == TEXT_MODE && (mode & PLACE_MASK) == PLACE_2) {
            MapText mt = (MapText)current;
            switch(evt.getKeyCode()) {
                case KeyEvent.VK_BACK_SPACE:
                    if(cursorLocation > 0) {
                        mt.text = mt.text.substring(0, cursorLocation-1) +mt.text.substring(cursorLocation);
                        cursorLocation--;
                    }
                    break;
                case KeyEvent.VK_DELETE:
                    if(cursorLocation < mt.text.length()) {
                        mt.text = mt.text.substring(0, cursorLocation) +mt.text.substring(cursorLocation+1);
                    }
                    break;
                case KeyEvent.VK_LEFT:
                    if(cursorLocation > 0) {
                        cursorLocation--;
                    }
                    break;
                case KeyEvent.VK_RIGHT:
                    if(cursorLocation < mt.text.length()) {
                        cursorLocation++;
                    }
                    break;
                case KeyEvent.VK_ENTER:
                    map.addObject(current);
                    startTextPlace(current.colour, mt.fontSize, mt.orientation[0], mt.orientation[1], mt.rotate);
                    break;
                default:
                    if(isPrintableChar(evt.getKeyChar())) {
                        if(cursorLocation < mt.text.length()) {
                            mt.text = mt.text.substring(0, cursorLocation) +evt.getKeyChar() +mt.text.substring(cursorLocation+1);
                        } else {
                            mt.text += evt.getKeyChar();
                        }
                        cursorLocation++;
                    }
            }
            evt.consume();
            repaint();
        }
    }//GEN-LAST:event_formKeyPressed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables

    private void fireModeChanged() {
        DrawingModeChangeListener[] listeners = listenerList.getListeners(DrawingModeChangeListener.class);
        for(DrawingModeChangeListener listener : listeners) {
            listener.drawingModeChanged(mode);
        }
    }
    
    private int getNearestCoord(int mouse) {
        return Math.round(mouse*1.0f/map.boxSize);
    }
    
    private float getNearestCoordPart(int mouse) {
        return Math.round(mouse*1f/map.boxSize*10)/10f;
    }
    
    private int getMouse(int coord) {
        return coord*map.boxSize;
    }
    
    private int getMouse(float coord) {
        return Math.round(coord*map.boxSize);
    }

    private float getNearestCoordHalf(int mouse) {
        return Math.round(mouse*1f/map.boxSize*2)/2f;
    }
    
    private boolean isPrintableChar(char c) {
        Character.UnicodeBlock block = Character.UnicodeBlock.of( c );
        return (!Character.isISOControl(c)) &&
                c != KeyEvent.CHAR_UNDEFINED &&
                block != null &&
                block != Character.UnicodeBlock.SPECIALS;
    }
}
