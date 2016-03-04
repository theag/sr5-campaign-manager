/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sr5CampaignManager.events;

import java.util.EventListener;

/**
 *
 * @author nbp184
 */
public interface MapChangeListener extends EventListener {
    
    public void mapChanged(MapChangeEvent evt);
    
}
