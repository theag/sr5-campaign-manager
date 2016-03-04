/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sr5CampaignManager.events;

import sr5CampaignManager.objects.*;

/**
 *
 * @author nbp184
 */
public class RunChangeEvent {
    
    public static final int UNKNOWN = -1;
    public static final int RUN = 0;
    public static final int NPC_GROUP = 1;
    public static final int NPC = 2;
    public static final int MAP = 3;
    
    public static final int NO_ADD_REMOVE = 0;
    public static final int ADD = 1;
    public static final int REMOVE = 2;

    private final LinkObject source;
    private final int addRemove;
    private final int type;
    private final LinkObject parent;
    
    public RunChangeEvent(LinkObject source, int addRemove) {
        this.source = source;
        this.addRemove = addRemove;
        if(source == null) {
            type = RUN;
        } else if(source instanceof NPCGroup) {
            type = NPC_GROUP;
        } else if(source instanceof NPC) {
            type = NPC;
        } else if(source instanceof Map) {
            type = MAP;
        } else {
            type = UNKNOWN;
        }
        parent = null;
    }
    
    public RunChangeEvent(LinkObject source, int addRemove, LinkObject parent) {
        this.source = source;
        this.addRemove = addRemove;
        if(source == null) {
            type = RUN;
        } else if(source instanceof NPCGroup) {
            type = NPC_GROUP;
        } else if(source instanceof NPC) {
            type = NPC;
        } else if(source instanceof Map) {
            type = MAP;
        } else {
            type = UNKNOWN;
        }
        this.parent = parent;
    }
    
    public LinkObject getSource() {
        return source;
    }
    
    public boolean isAddRemove() {
        return addRemove != NO_ADD_REMOVE;
    }
    
    public int getAddRemove() {
        return addRemove;
    }
    
    public int getType() {
        return type;
    }
    
    public LinkObject getParent() {
        return parent;
    }
    
}
