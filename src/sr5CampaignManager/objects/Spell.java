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
public class Spell {

    static Spell load(String input, String delimiter) {
        Spell rv = new Spell();
        StringTokenizer tokens = new StringTokenizer(input, delimiter);
        rv.name = Run.loadFormat(tokens.nextToken());
        rv.type = Run.loadFormat(tokens.nextToken());
        rv.range = Run.loadFormat(tokens.nextToken());
        rv.damage = Run.loadFormat(tokens.nextToken());
        rv.duration = Run.loadFormat(tokens.nextToken());
        rv.drain = Run.loadFormat(tokens.nextToken());
        rv.effect = Run.loadFormat(tokens.nextToken());
        return rv;
    }
    
    public String name;
    public String type;
    public String range;
    public String damage;
    public String duration;
    public String drain;
    public String effect;
    
    public Spell() {
        name = "";
        type = "";
        range = "";
        damage = "";
        duration = "";
        drain = "";
        effect = "";
    }

    public String getStringOutput(String t, String nl) {
        String rv = t +"<spell>" +nl;
        rv += t +"\t<name>" +name +"</name>" +nl;
        rv += t +"\t<type>" +type +"</type>" +nl;
        rv += t +"\t<range>" +range +"</range>" +nl;
        if(!damage.isEmpty()) {
            rv += t +"\t<damage>" +damage +"</damage>" +nl;
        }
        rv += t +"\t<duration>" +duration +"</duration>" +nl;
        rv += t +"\t<drain>" +drain +"</drain>" +nl;
        if(!effect.isEmpty()) {
            rv += t +"\t<effect>" +effect +"</effect>" +nl;
        }
        rv += t +"</spell>";
        return rv;
    }
    
    public String save(String delimiter) {
        return Run.saveFormat(name) +delimiter +Run.saveFormat(type)
                +delimiter +Run.saveFormat(range) +delimiter +Run.saveFormat(damage)
                +delimiter +Run.saveFormat(duration) +delimiter +Run.saveFormat(drain)
                +delimiter +Run.saveFormat(effect);
    }
    
}
