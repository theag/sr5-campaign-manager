/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sr5CampaignManager.events;

import sr5CampaignManager.objects.maps.MapDrawingObject;

/**
 *
 * @author nbp184
 */
public class MapChangeEvent {
    
    public static final int ADD = 0;
    public static final int REMOVE = 1;
    public static final int UPDATE = 2;
    public static final int CANCEL = 3;
    
    private final int type;
    private final MapDrawingObject object;
    
    public MapChangeEvent(int type, MapDrawingObject object) {
        this.type = type;
        this.object = object;
    }
    
    public int getType() {
        return type;
    }
    
    public MapDrawingObject getObject() {
        return object;
    }
    
    @Override
    public String toString() {
        String rv;
        switch(type) {
            case ADD:
                rv = "Add";
                break;
            case REMOVE:
                rv = "Remove";
                break;
            case UPDATE:
                rv = "Update";
                break;
            case CANCEL:
                rv = "Cancel";
                break;
            default:
                rv = "";
                break;
        }
        rv += " " +object;
        return rv;
    }
    
}
