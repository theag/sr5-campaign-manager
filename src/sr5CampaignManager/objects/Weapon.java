/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sr5CampaignManager.objects;

import java.util.StringTokenizer;

/**
 *
 * @author nbp184
 */
public class Weapon extends Gear {
    
    static Weapon load(String input, String delimiter) {
        StringTokenizer tokens = new StringTokenizer(input, delimiter);
        Weapon weap = new Weapon(Integer.parseInt(tokens.nextToken()), false, Boolean.parseBoolean(tokens.nextToken()));
        weap.name = Run.loadFormat(tokens.nextToken());
        weap.type = Run.loadFormat(tokens.nextToken());
        weap.damage = Run.loadFormat(tokens.nextToken());
        weap.accuracyReach = Run.loadFormat(tokens.nextToken());
        weap.ap = Run.loadFormat(tokens.nextToken());
        return weap;
    }
    
    public String type;
    public String damage;
    public String accuracyReach;
    public String ap;
    public final boolean isRanged;

    public Weapon(int sortOrder, boolean fromList, boolean isRanged) {
        super(sortOrder, fromList);
        type = "";
        damage = "";
        accuracyReach = "";
        ap = "";
        this.isRanged = isRanged;
    }

    public Weapon copy() {
        Weapon rv = new Weapon(sortOrder, fromList, isRanged);
        rv.name = name;
        rv.type = type;
        rv.damage = damage;
        rv.accuracyReach = accuracyReach;
        rv.ap = ap;
        return rv;
    }
    
    @Override
    public String getStringOutput(String t, String nl) {
        String rv = t +"<weapon type=";
        if(isRanged) {
            rv += "\"ranged\">" +nl;
        } else {
            rv += "\"melee\">" +nl;
        }
        rv += t +"\t<name>" +name +"</name>" +nl;
        rv += t +"\t<type>" +type +"</type>" +nl;
        if(!isRanged) {
            if(accuracyReach.isEmpty()) {
                rv += t +"\t<reach>&#8212;</reach>" +nl;
            } else {
                rv += t +"\t<reach>" +accuracyReach +"</reach>" +nl;
            }
        }
        rv += t +"\t<damage>" +damage +"</damage>" +nl;
        if(isRanged) {
            rv += t +"\t<accuracy>" +accuracyReach +"</accuracy>" +nl;
        }
        if(ap.isEmpty()) {
            rv += t +"\t<ap>&#8212;</ap>" +nl;
        } else {
            rv += t +"\t<ap>" +ap +"</ap>" +nl;
        }
        rv += t +"</weapon>";
        return rv;
    }
    
    @Override
    public String save(String delimiter) {
        return sortOrder +delimiter +isRanged +delimiter +Run.saveFormat(name) +delimiter
                +Run.saveFormat(type) +delimiter +Run.saveFormat(damage)
                +delimiter +Run.saveFormat(accuracyReach)
                +delimiter +Run.saveFormat(ap);
    }
    
}
