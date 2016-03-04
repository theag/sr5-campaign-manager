/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sr5CampaignManager.events;

import sr5CampaignManager.panels.RunEditor;

/**
 *
 * @author nbp184
 */
public interface OpenPanelListener extends java.util.EventListener {
    
    public void onOpenPanel(RunEditor panel);
    
}
