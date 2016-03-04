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
public class Skill {

    static Skill load(String line, String delimiter) {
        StringTokenizer tokens = new StringTokenizer(line, delimiter);
        Skill rv = new Skill(false);
        rv.name = Run.loadFormat(tokens.nextToken());
        rv.value = Integer.parseInt(tokens.nextToken());
        rv.dicePool = Integer.parseInt(tokens.nextToken());
        return rv;
    }
    
    public final boolean fromList;
    public String name;
    public int value;
    public int dicePool;
    
    public Skill(boolean fromList) {
        name = "";
        value = 1;
        dicePool = 1;
        this.fromList = fromList;
    }
    
    public Skill(boolean fromList, String name) {
        this.name = name;
        value = 1;
        dicePool = 1;
        this.fromList = fromList;
    }
    
    public String getStringOutput(String t, String nl) {
        String rv = t +"<skill>" +nl;
        rv += t +"\t<name>" +name +"</name>" +nl;
        rv += t +"\t<value>" +value +"</value>" +nl;
        rv += t +"\t<dicePool>" +dicePool +"</dicePool>" +nl;
        rv += t +"</skill>";
        return rv;
    }
    
    public String save(String delimiter) {
        return Run.saveFormat(name) +delimiter +value +delimiter +dicePool;
    }
    
}
