/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sr5CampaignManager.objects;

import javax.swing.tree.DefaultMutableTreeNode;

/**
 *
 * @author nbp184
 */
public abstract class LinkObject {
    
    public String name;
    
    private DefaultMutableTreeNode node;
    
    public LinkObject(String name) {
        this.name = name;
        node = null;
    }
    
    public abstract boolean isObjectOfLink(String link);
    
    public abstract String getLink();
    
    public abstract String[] save();
    
    public DefaultMutableTreeNode getNode() {
        if(node == null) {
            node = new DefaultMutableTreeNode(this, false);
        }
        return node;
    }
    
    @Override
    public String toString() {
        return name;
    }
    
}
