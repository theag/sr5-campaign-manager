/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sr5CampaignManager.objects;

import java.util.ArrayList;
import javax.swing.tree.DefaultMutableTreeNode;

/**
 *
 * @author nbp184
 */
public class NPCGroup extends LinkObject {

    public static NPCGroup load(String[] input) {
        NPCGroup rv = new NPCGroup(input[0]);
        for(int i = 1; i < input.length; i += NPC.SAVE_LINE_COUNT) {
            rv.npcs.add(NPC.load(input, i));
        }
        return rv;
    }
    
    public final ArrayList<NPC> npcs;
    
    public NPCGroup(String name) {
        super(name);
        npcs = new ArrayList<>();
    }
    
    @Override
    public boolean isObjectOfLink(String link) {
        return link.compareTo(name +".npcs") == 0;
    }

    @Override
    public String getLink() {
        return name +".npcs";
    }

    @Override
    public DefaultMutableTreeNode getNode() {
        DefaultMutableTreeNode root = super.getNode();
        if(!root.getAllowsChildren()) {
            root.setAllowsChildren(true);
            for(NPC npc : npcs) {
                root.add(npc.getNode());
            }
        }
        return root;
    }

    @Override
    public String[] save() {
        String[] rv = new String[1 + NPC.SAVE_LINE_COUNT*npcs.size()];
        rv[0] = name;
        String[] npcArr;
        for(int i = 0; i < npcs.size(); i++) {
            npcArr = npcs.get(i).save();
            System.arraycopy(npcArr, 0, rv, 1+i*NPC.SAVE_LINE_COUNT, NPC.SAVE_LINE_COUNT);
        }
        return rv;
    }
    
}
