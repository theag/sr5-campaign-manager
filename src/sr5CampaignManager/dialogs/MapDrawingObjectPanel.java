/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sr5CampaignManager.dialogs;

import java.awt.Color;
import javax.swing.event.EventListenerList;
import sr5CampaignManager.objects.maps.MapDrawingObject;
import sr5CampaignManager.events.MapChangeEvent;
import sr5CampaignManager.events.MapChangeListener;

/**
 *
 * @author nbp184
 * @param <E>
 */
public abstract class MapDrawingObjectPanel<E extends MapDrawingObject> extends javax.swing.JPanel {
    
    protected final Color plain;
    protected final Color highlight;
    
    private final EventListenerList listenerList;
    
    public MapDrawingObjectPanel() {
        plain = this.getBackground();
        highlight = new Color(255, 255, 204);
        listenerList = new EventListenerList();
        setBorder(javax.swing.BorderFactory.createEtchedBorder());
    }
    
    public abstract boolean isOfType(String typeName);
    
    public abstract void setObject(E object);
    
    public abstract void clearData();
    
    public void addMapChangeListener(MapChangeListener listener) {
        listenerList.add(MapChangeListener.class, listener);
    }
    
    public void removeMapChangeListener(MapChangeListener listener) {
        listenerList.remove(MapChangeListener.class, listener);
    }
    
    public void fireMapChange(MapChangeEvent evt) {
        MapChangeListener[] listeners = listenerList.getListeners(MapChangeListener.class);
        for(MapChangeListener listener : listeners) {
            listener.mapChanged(evt);
        }
    }
    
}
