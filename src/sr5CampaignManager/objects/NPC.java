/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sr5CampaignManager.objects;

import java.util.ArrayList;
import java.util.StringTokenizer;
import sr5CampaignManager.Resources;

/**
 *
 * @author nbp184
 */
public class NPC extends LinkObject {
    
    public static final int BODY = 0;
    public static final int AGILITY = 1;
    public static final int REACTION = 2;
    public static final int STRENGTH = 3;
    public static final int WILLPOWER = 4;
    public static final int LOGIC = 5;
    public static final int INTUITION = 6;
    public static final int CHARISMA = 7;
    public static final int MAGIC = 8;
    
    public static final int PHYSICAL = 0;
    public static final int MENTAL = 1;
    public static final int SOCIAL = 2;
    
    static int SAVE_LINE_COUNT = 14;

    public static NPC load(String[] input, int startIndex) {
        int index = startIndex;
        NPC rv = new NPC(Run.loadFormat(input[index++]));
        rv.isMagic = Boolean.parseBoolean(input[index++]);
        StringTokenizer tokens = new StringTokenizer(input[index++], Resources.unitSep);
        for(int i = 0; i < rv.attributes.length; i++) {
            rv.attributes[i] = Integer.parseInt(tokens.nextToken());
        }
        rv.essence = Double.parseDouble(input[index++]);
        rv.initiative = Run.loadFormat(input[index++]);
        rv.conditionMonitor = Run.loadFormat(input[index++]);
        rv.armor = Run.loadFormat(input[index++]);
        tokens = new StringTokenizer(input[index++], Resources.unitSep);
        for(int i = 0; i < rv.limits.length; i++) {
            rv.limits[i] = Run.loadFormat(tokens.nextToken());
        }
        if(!input[index].isEmpty()) {
            tokens = new StringTokenizer(input[index], Resources.groupSep);
            rv.skills.add(Skill.load(tokens.nextToken(), Resources.unitSep));
        }
        index++;
        if(!input[index].isEmpty()) {
            tokens = new StringTokenizer(input[index], Resources.groupSep);
            rv.augmentations.add(Run.loadFormat(tokens.nextToken()));
        }
        index++;
        if(!input[index].isEmpty()) {
            rv.deck = CyberDeck.load(input[index], Resources.unitSep);
        }
        index++;
        if(!input[index].isEmpty()) {
            tokens = new StringTokenizer(input[index], Resources.groupSep);
            rv.programs.add(Program.load(tokens.nextToken(), Resources.unitSep));
        }
        index++;
        if(!input[index].isEmpty()) {
            tokens = new StringTokenizer(input[index], Resources.groupSep);
            String line;
            while(tokens.hasMoreTokens()) {
                line = tokens.nextToken();
                if(line.startsWith("W")) {
                    rv.gear.add(Weapon.load(line.substring(1), Resources.unitSep));
                } else {
                    rv.gear.add(Gear.load(line.substring(1), Resources.unitSep));
                }
            }
        }
        index++;
        if(!input[index].isEmpty()) {
            tokens = new StringTokenizer(input[index], Resources.groupSep);
            rv.spells.add(Spell.load(tokens.nextToken(), Resources.unitSep));
        }
        return rv;
    }
    
    public boolean isMagic;
    public final int[] attributes;
    public double essence;
    public String initiative;
    public String conditionMonitor;
    public String armor;
    public final String[] limits;
    public final ArrayList<Skill> skills;
    public final ArrayList<String> augmentations;
    public CyberDeck deck;
    public final ArrayList<Program> programs;
    public final ArrayList<Gear> gear;
    public final ArrayList<Spell> spells;
    
    public NPC(String name) {
        super(name);
        isMagic = false;
        essence = 6;
        initiative = "";
        conditionMonitor = "";
        armor = "";
        attributes = new int[10];
        for(int i = 0; i < attributes.length; i++) {
            attributes[i] = 1;
        }
        limits = new String[]{"", "", ""};
        skills = new ArrayList<>();
        augmentations = new ArrayList<>();
        deck = null;
        programs = new ArrayList<>();
        gear = new ArrayList<>();
        spells = new ArrayList<>();
    }
    
    public NPC() {
        super("");
        initiative = "";
        conditionMonitor = "";
        armor = "";
        attributes = new int[10];
        limits = new String[]{"", "", ""};
        skills = new ArrayList<>();
        augmentations = new ArrayList<>();
        deck = null;
        programs = new ArrayList<>();
        gear = new ArrayList<>();
        spells = new ArrayList<>();
    }
    
    public Weapon[] getWeapons() {
        int count = 0;
        for(Gear g : gear) {
            if(g instanceof Weapon) {
                count++;
            }
        }
        Weapon rv[] = new Weapon[count];
        count = 0;
        for(Gear g : gear) {
            if(g instanceof Weapon) {
                rv[count++] = (Weapon)g;
            }
        }
        return rv;
    }
    
    public Gear[] getOtherGear() {
        int count = 0;
        for(Gear g : gear) {
            if(!(g instanceof Weapon)) {
                count++;
            }
        }
        Gear rv[] = new Gear[count];
        count = 0;
        for(Gear g : gear) {
            if(!(g instanceof Weapon)) {
                rv[count++] = g;
            }
        }
        return rv;
    }

    @Override
    public boolean isObjectOfLink(String link) {
        return link.compareTo(name +".npc") == 0;
    }

    @Override
    public String getLink() {
        return name +".npc";
    }

    @Override
    public String[] save() {
        String[] rv = new String[SAVE_LINE_COUNT];
        rv[0] = Run.saveFormat(name);
        rv[1] = ""+isMagic;
        rv[2] = ""+attributes[0];
        for(int i = 1; i < attributes.length; i++) {
            rv[2] += Resources.unitSep +attributes[i];
        }
        rv[3] = ""+essence;
        rv[4] = Run.saveFormat(initiative);
        rv[5] = Run.saveFormat(conditionMonitor);
        rv[6] = Run.saveFormat(armor);
        rv[7] = Run.saveFormat(limits[0]) +Resources.unitSep +Run.saveFormat(limits[1])
                +Resources.unitSep +Run.saveFormat(limits[2]);
        rv[8] = "";
        for(Skill skill : skills) {
            if(!rv[8].isEmpty()) {
                rv[8] += Resources.groupSep;
            }
            rv[8] += skill.save(Resources.unitSep);
        }
        rv[9] = "";
        for(String aug : augmentations) {
            if(!rv[9].isEmpty()) {
                rv[9] += Resources.groupSep;
            }
            rv[9] += Run.saveFormat(aug);
        }
        if(deck == null) {
            rv[10] = "";
        } else {
            rv[10] = deck.save(Resources.unitSep);
        }
        rv[11] = "";
        for(Program prog : programs) {
            if(!rv[11].isEmpty()) {
                rv[11] += Resources.groupSep;
            }
            rv[11] += prog.save(Resources.unitSep);
        }
        rv[12] = "";
        for(Gear g : gear) {
            if(!rv[12].isEmpty()) {
                rv[12] += Resources.groupSep;
            }
            if(g instanceof Weapon) {
                rv[12] += "W";
            } else {
                rv[12] += "G";
            }
            rv[12] += g.save(Resources.unitSep);
                
        }
        rv[13] = "";
        for(Spell spell : spells) {
            if(!rv[13].isEmpty()) {
                rv[13] += Resources.groupSep;
            }
            rv[13] += spell.save(Resources.unitSep);
        }
        return rv;
    }
    
}
