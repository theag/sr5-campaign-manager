/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sr5CampaignManager.objects;

import java.util.ArrayList;

/**
 *
 * @author nbp184
 */
public class Party {
    
    public String name;
    public final ArrayList<String> players;
    
    public Party(String name) {
        this.name = name;
        players = new ArrayList<>();
    }
    
}
