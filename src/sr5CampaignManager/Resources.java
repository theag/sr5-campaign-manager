/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sr5CampaignManager;

import sr5CampaignManager.dialogs.StringTreeNode;
import sr5CampaignManager.dialogs.WeapTreeNode;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Stack;
import java.util.StringTokenizer;
import sr5CampaignManager.objects.*;

/**
 *
 * @author nbp184
 */
public class Resources {
    
    private static Resources instance = null;
    public static final String empty = "" +((char)0);
    public static final String shiftOut = "" +((char)14);
    public static final String shiftIn = "" +((char)15);
    public static final String groupSep = "" +((char)29);
    public static final String recordSep = "" +((char)30);
    public static final String unitSep = "" +((char)31);
    public static final String longDash = "" +((char)8212);
    public static final String escape = "" +((char)27);
    //public static final String nl = System.getProperty("line.separator");
    
    public static Resources get() {
        if(instance == null) {
            instance = new Resources();
        }
        return instance;
    }
    
    public static void save() {
        if(instance != null) {
            PrintWriter outFile;
            try {
                outFile = new PrintWriter(new File("skills.lst"));
                for(String skill : instance.skills) {
                    outFile.print(format(skill) +unitSep);
                }
                outFile.println();
                outFile.close();
            } catch(IOException ex) {

            }
            try {
                outFile = new PrintWriter(new File("augs.lst"));
                StringTreeNode current;
                for(int i = 0; i < instance.augs.getChildCount(); i++) {
                    current = (StringTreeNode)instance.augs.getChildAt(i);
                    if(current == instance.newAugs) {
                        outFile.println(format(current.getValue()) +unitSep +current.isEditable() +unitSep +current.isLeaf() +unitSep +"newAugs");
                    } else {
                        outFile.println(format(current.getValue()) +unitSep +current.isEditable() +unitSep +current.isLeaf());
                    }
                    if(!current.isLeaf()) {
                        outFile.println(shiftIn);
                        printStringChildren(outFile, current);
                        outFile.println(shiftOut);
                    }
                }
                outFile.close();
            } catch(IOException ex) {

            }
            try {
                outFile = new PrintWriter(new File("decks.lst"));
                for(CyberDeck deck : instance.decks) {
                    outFile.println(format(deck.name) +unitSep +format(deck.rating) +unitSep +format(deck.attributeArray));
                }
                outFile.close();
            } catch(IOException ex) {
                
            }
            try {
                outFile = new PrintWriter(new File("programs.lst"));
                for(Program prog : instance.programs) {
                    outFile.println(format(prog.name) +unitSep +format(prog.effect));
                }
                outFile.close();
            } catch(IOException ex) {
                
            }
            try {
                outFile = new PrintWriter(new File("weapons.lst"));
                WeapTreeNode current;
                Weapon weap;
                for(int i = 0; i < instance.rangedWeapons.getChildCount(); i++) {
                    current = (WeapTreeNode)instance.rangedWeapons.getChildAt(i);
                    if(current.isLeaf()) {
                        weap = current.getWeapon();
                        outFile.println(weap.isRanged +unitSep +format(weap.name) +unitSep +format(weap.type) +unitSep +format(weap.damage) +unitSep +format(weap.accuracyReach) +unitSep +format(weap.ap));
                    } else {
                        if(current == instance.newRanged) {
                            outFile.println(current.getName() +unitSep +"newRanged");
                        } else {
                            outFile.println(format(current.getName()));
                        }
                        outFile.println(shiftIn);
                        printWeaponChildren(outFile, current);
                        outFile.println(shiftOut);
                    }
                }
                outFile.println(groupSep);
                for(int i = 0; i < instance.meleeWeapons.getChildCount(); i++) {
                    current = (WeapTreeNode)instance.meleeWeapons.getChildAt(i);
                    if(current.isLeaf()) {
                        weap = current.getWeapon();
                        outFile.println(weap.isRanged +unitSep +format(weap.name) +unitSep +format(weap.type) +unitSep +format(weap.damage) +unitSep +format(weap.accuracyReach) +unitSep +format(weap.ap));
                    } else {
                        if(current == instance.newMelee) {
                            outFile.println(current.getName() +unitSep +"newMelee");
                        } else {
                            outFile.println(format(current.getName()));
                        }
                        outFile.println(shiftIn);
                        printWeaponChildren(outFile, current);
                        outFile.println(shiftOut);
                    }
                }
                outFile.close();
            } catch(IOException ex) {

            }
            try {
                outFile = new PrintWriter(new File("gear.lst"));
                StringTreeNode current;
                for(int i = 0; i < instance.gear.getChildCount(); i++) {
                    current = (StringTreeNode)instance.gear.getChildAt(i);
                    if(current == instance.newGear) {
                        outFile.println(format(current.getValue()) +unitSep +current.isEditable() +unitSep +current.isLeaf() +unitSep +"newGear");
                    } else {
                        outFile.println(format(current.getValue()) +unitSep +current.isEditable() +unitSep +current.isLeaf());
                    }
                    if(!current.isLeaf()) {
                        outFile.println(shiftIn);
                        printStringChildren(outFile, current);
                        outFile.println(shiftOut);
                    }
                }
                outFile.close();
            } catch(IOException ex) {

            }
        }
    }
    
    private static void printStringChildren(PrintWriter outFile, StringTreeNode parent) {
        StringTreeNode current;
        for(int i = 0; i < parent.getChildCount(); i++) {
            current = (StringTreeNode)parent.getChildAt(i);
            outFile.println(format(current.getValue()) +unitSep +current.isEditable() +unitSep +current.isLeaf());
            if(!current.isLeaf()) {
                outFile.println(shiftIn);
                printStringChildren(outFile, current);
                outFile.println(shiftOut);
            }
        }
    }
    
    private static void printWeaponChildren(PrintWriter outFile, WeapTreeNode parent) {
        WeapTreeNode current;
        Weapon weap;
        for(int i = 0; i < parent.getChildCount(); i++) {
            current = (WeapTreeNode)parent.getChildAt(i);
            if(current.isLeaf()) {
                weap = current.getWeapon();
                outFile.println(weap.isRanged +unitSep +format(weap.name) +unitSep +format(weap.type) +unitSep +format(weap.damage) +unitSep +format(weap.accuracyReach) +unitSep +format(weap.ap));
            } else {
                outFile.println(format(current.getName()));
                outFile.println(shiftIn);
                printWeaponChildren(outFile, current);
                outFile.println(shiftOut);
            }
        }
    }
    
    private static String format(String str) {
        if(str != null && str.isEmpty()) {
            return empty;
        } else {
            return str;
        }
    }
    
    private static String reverseFormat(String str) {
        if(str != null && str.compareTo(empty) == 0) {
            return "";
        } else {
            return str;
        }
    }
    
    private ArrayList<String> skills;
    private StringTreeNode augs;
    private StringTreeNode newAugs;
    private ArrayList<CyberDeck> decks;
    private ArrayList<Program> programs;
    private WeapTreeNode rangedWeapons;
    private WeapTreeNode newRanged;
    private WeapTreeNode meleeWeapons;
    private WeapTreeNode newMelee;
    private StringTreeNode gear;
    private StringTreeNode newGear;
    
    private Resources() {
        BufferedReader inFile;
        String line;
        StringTokenizer tokens;
        skills = new ArrayList<>();
        try {
            inFile = new BufferedReader(new FileReader("skills.lst"));
            line = inFile.readLine();
            inFile.close();
        } catch(IOException ex) {
            line = null;
        }
        if(line != null) {
            tokens = new StringTokenizer(line, unitSep);
            while(tokens.hasMoreTokens()) {
                skills.add(reverseFormat(tokens.nextToken()));
            }
        }
        augs = new StringTreeNode("Augmentations", false, false);
        try {
            inFile = new BufferedReader(new FileReader("augs.lst"));
            line = inFile.readLine();
            Stack<StringTreeNode> augStack = new Stack<>();
            augStack.push(augs);
            StringTreeNode current = null;
            while(line != null) {
                if(line.compareTo(shiftIn) == 0) {
                    augStack.push(current);
                } else if(line.compareTo(shiftOut) == 0) {
                    augStack.pop();
                } else {
                    tokens = new StringTokenizer(line, unitSep);
                    current = new StringTreeNode(reverseFormat(tokens.nextToken()), tokens.nextToken().compareTo("true") == 0, tokens.nextToken().compareTo("true") == 0);
                    if(tokens.hasMoreTokens()) {
                        newAugs = current;
                    }
                    augStack.peek().addChild(current);
                }
                line = inFile.readLine();
            }
            inFile.close();
        } catch(IOException ex) {
            augs.removeAllChildren();
            augs.addChild(new StringTreeNode("Cyberware", false, false));
            augs.addChild(new StringTreeNode("Bioware", false, false));
            newAugs = new StringTreeNode("Unorganized", false, false);
            augs.addChild(newAugs);
        }
        decks = new ArrayList<>();
        try {
            inFile = new BufferedReader(new FileReader("decks.lst"));
            line = inFile.readLine();
            while(line != null) {
                tokens = new StringTokenizer(line, unitSep);
                decks.add(new CyberDeck(reverseFormat(tokens.nextToken()), reverseFormat(tokens.nextToken()), reverseFormat(tokens.nextToken())));
                line = inFile.readLine();
            }
            inFile.close();
        } catch(IOException ex) {
            decks.clear();
        }
        programs = new ArrayList<>();
        try {
            inFile = new BufferedReader(new FileReader("programs.lst"));
            line = inFile.readLine();
            while(line != null) {
                tokens = new StringTokenizer(line, unitSep);
                programs.add(new Program(reverseFormat(tokens.nextToken()), reverseFormat(tokens.nextToken()), true));
                line = inFile.readLine();
            }
            inFile.close();
        } catch(IOException ex) {
            programs.clear();
        }
        rangedWeapons = new WeapTreeNode("Ranged Weapons", false);
        meleeWeapons = new WeapTreeNode("Melee Weapons", false);
        newRanged = null;
        try {
            inFile = new BufferedReader(new FileReader("weapons.lst"));
            line = inFile.readLine();
            Stack<WeapTreeNode> weapStack = new Stack<>();
            weapStack.push(rangedWeapons);
            WeapTreeNode current = null;
            while(line != null) {
                if(line.compareTo(groupSep) == 0) {
                    weapStack.push(meleeWeapons);
                } else if(line.compareTo(shiftIn) == 0) {
                    weapStack.push(current);
                } else if(line.compareTo(shiftOut) == 0) {
                    weapStack.pop();
                } else {
                    tokens = new StringTokenizer(line, unitSep);
                    if(tokens.countTokens() > 2) {
                        Weapon weap = new Weapon(1, true, tokens.nextToken().compareTo("true") == 0);
                        weap.name = reverseFormat(tokens.nextToken());
                        weap.type = reverseFormat(tokens.nextToken());
                        weap.damage = reverseFormat(tokens.nextToken());
                        weap.accuracyReach = reverseFormat(tokens.nextToken());
                        weap.ap = reverseFormat(tokens.nextToken());
                        current = new WeapTreeNode(weap, true);
                    } else {
                        current = new WeapTreeNode(reverseFormat(tokens.nextToken()), true);
                        if(tokens.hasMoreTokens()) {
                            current.notEditable();
                            if(newRanged == null) {
                                newRanged = current;
                            } else {
                                newMelee = current;
                            }
                        }
                    }
                    weapStack.peek().addChild(current);
                }
                line = inFile.readLine();
            }
            inFile.close();
        } catch(IOException ex) {
            rangedWeapons.removeAllChildren();
            newRanged = new WeapTreeNode("Unorganized", false);
            rangedWeapons.addChild(newRanged);
            meleeWeapons.removeAllChildren();
            newMelee = new WeapTreeNode("Unorganized", false);
            meleeWeapons.addChild(newMelee);
        }
        gear = new StringTreeNode("Other Gear", false, false);
        try {
            inFile = new BufferedReader(new FileReader("gear.lst"));
            line = inFile.readLine();
            Stack<StringTreeNode> gearStack = new Stack<>();
            gearStack.push(gear);
            StringTreeNode current = null;
            while(line != null) {
                if(line.compareTo(shiftIn) == 0) {
                    gearStack.push(current);
                } else if(line.compareTo(shiftOut) == 0) {
                    gearStack.pop();
                } else {
                    tokens = new StringTokenizer(line, unitSep);
                    current = new StringTreeNode(reverseFormat(tokens.nextToken()), tokens.nextToken().compareTo("true") == 0, tokens.nextToken().compareTo("true") == 0);
                    if(tokens.hasMoreTokens()) {
                        newGear = current;
                    }
                    gearStack.peek().addChild(current);
                }
                line = inFile.readLine();
            }
            inFile.close();
        } catch(IOException ex) {
            gear.removeAllChildren();
            newGear = new StringTreeNode("Unorganized", false, false);
            gear.addChild(newGear);
        }        
    }
    
    public String[] getSkills() {
        String rv[] = new String[skills.size()];
        skills.toArray(rv);
        return rv;
    }
    
    public void addSkills(String[] skills) {
        for(String skill : skills) {
            if(!skill.isEmpty() && !this.skills.contains(skill)) {
                this.skills.add(skill);
            }
        }
        Collections.sort(this.skills);
    }

    public StringTreeNode getAugs() {
        return augs;
    }
    
    public void addAugmentations(String[] augs) {
        for(String aug : augs) {
            newAugs.addChild(new StringTreeNode(aug, true));
        }
    }
    
    public CyberDeck[] getDecks() {
        CyberDeck rv[] = new CyberDeck[decks.size()];
        decks.toArray(rv);
        return rv;
    }
    
    public void addDeck(CyberDeck deck) {
        if(!deck.name.isEmpty()) {
            if(decks.contains(deck)) {
                decks.remove(deck);
            }
            decks.add(deck);
            Collections.sort(decks);
        }
    }
    
    public Program[] getPrograms() {
        Program[] rv = new Program[programs.size()];
        programs.toArray(rv);
        return rv;
    }
    
    public void addPrograms(Program[] progs) {
        for(Program prog : progs) {
            if(!prog.name.isEmpty() && !programs.contains(prog)) {
                programs.add(prog);
            }
        }
        Collections.sort(programs);
    }
    
    public WeapTreeNode getRangedWeapons() {
        return rangedWeapons;
    }
    
    public void addRangedWeapons(Weapon[] weapons) {
        for(Weapon weap : weapons) {
            newRanged.addChild(new WeapTreeNode(weap, true));
        }
    }
    
    public WeapTreeNode getMeleeWeapons() {
        return meleeWeapons;
    }
    
    public void addMeleeWeapons(Weapon[] weapons) {
        for(Weapon weap : weapons) {
            newMelee.addChild(new WeapTreeNode(weap, true));
        }
    }

    public StringTreeNode getGear() {
        return gear;
    }
    
    public void addGear(String[] gears) {
        for(String str : gears) {
            newGear.addChild(new StringTreeNode(str, true));
        }
    }
    
}
