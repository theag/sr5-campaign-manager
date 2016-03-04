/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sr5CampaignManager.panels;

import sr5CampaignManager.events.RunChangeEvent;
import sr5CampaignManager.events.OpenPanelListener;
import sr5CampaignManager.events.RunChangeListener;

/**
 *
 * @author nbp184
 */
public abstract class RunEditor extends javax.swing.JPanel {
    
    public void addRunChangeListener(RunChangeListener listener) {
        listenerList.add(RunChangeListener.class, listener);
    }
    
    public void addOpenPanelListener(OpenPanelListener listener) {
        listenerList.add(OpenPanelListener.class, listener);
    }
    
    public abstract void fireRunChangeEvent(RunChangeEvent evt);
    
    public abstract String getTitleBar();
    
    public abstract void clickSaveButton();

    public abstract void preferenceSave();
    
}
