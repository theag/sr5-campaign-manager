/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sr5CampaignManager.objects;

/**
 *
 * @author nbp184
 */
public class Augmentation {
    
    public String name;
    public final boolean fromTree;
    
    public Augmentation(String name, boolean fromTree) {
        this.name = name;
        this.fromTree = fromTree;
    }
    
    @Override
    public String toString() {
        return name;
    }
    
}
