/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sr5CampaignManager.objects;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.swing.tree.DefaultMutableTreeNode;
import sr5CampaignManager.Resources;

/**
 *
 * @author nbp184
 */
public class Run {
    
    public static String saveFormat(String str) {
        if(str.isEmpty()) {
            return Resources.empty;
        } else {
            return str.replaceAll("\\u000A", Resources.escape);
        }
    }
    
    public static String loadFormat(String str) {
        if(str.compareTo(Resources.empty) == 0) {
            return "";
        } else {
            return str.replaceAll(Resources.escape, "\n");
        }
    }

    public static Run load(File file) throws IOException {
        BufferedReader inFile = new BufferedReader(new FileReader(file));
        Run run = new Run(loadFormat(inFile.readLine()));
        run.description = loadFormat(inFile.readLine());
        run.text = loadFormat(inFile.readLine());
        int objectCount = Integer.parseInt(inFile.readLine());
        String[] input;
        String line;
        int length;
        for(int i = 0; i < objectCount; i++) {
            line = inFile.readLine();
            length = Integer.parseInt(inFile.readLine());
            input = new String[length];
            for(int j = 0; j < length; j++) {
                input[j] = inFile.readLine();
            }
            switch(line) {
                case "NPCGroup":
                    run.objects.add(NPCGroup.load(input));
                    break;
                case "Map":
                    run.objects.add(Map.load(input));
                    break;
            }
        }
        inFile.close();
        run.remakeRoot();
        return run;
    }
    
    public String name;
    public String description;
    public String text;
    
    public final ArrayList<LinkObject> objects;
    
    public DefaultMutableTreeNode root;
    
    public Run(String name) {
        this.name = name;
        description = "";
        text = "";
        objects = new ArrayList<>();
        
        root = new DefaultMutableTreeNode(this);
        
        DefaultMutableTreeNode node1 = new DefaultMutableTreeNode("NPC Groups");
        DefaultMutableTreeNode node2 = new DefaultMutableTreeNode("Maps");
        DefaultMutableTreeNode objectNode;
        for(LinkObject linkObject : objects) {
            objectNode = linkObject.getNode();
            if(linkObject instanceof NPCGroup) {
                node1.add(objectNode);
            } else if(linkObject instanceof Map) {
                node2.add(objectNode);
            }
        }
        root.add(node1);
        root.add(node2);
    }
    
    private void remakeRoot() {
        root = new DefaultMutableTreeNode(this);
        
        DefaultMutableTreeNode node1 = new DefaultMutableTreeNode("NPC Groups");
        DefaultMutableTreeNode node2 = new DefaultMutableTreeNode("Maps");
        DefaultMutableTreeNode objectNode;
        for(LinkObject linkObject : objects) {
            objectNode = linkObject.getNode();
            if(linkObject instanceof NPCGroup) {
                node1.add(objectNode);
            } else if(linkObject instanceof Map) {
                node2.add(objectNode);
            }
        }
        root.add(node1);
        root.add(node2);
    }
    
    public DefaultMutableTreeNode getNPCGroupNode() {
        return (DefaultMutableTreeNode)root.getChildAt(0);
    }

    public int getNPCGroupCount() {
        return root.getChildAt(0).getChildCount();
    }
    
    public DefaultMutableTreeNode getMapNode() {
        return (DefaultMutableTreeNode)root.getChildAt(1);
    }
    
    public int getMapCount() {
        return root.getChildAt(1).getChildCount();
    }
    
    @Override
    public String toString() {
        return name;
    }

    public void save(File file) throws IOException {
        PrintWriter outFile = new PrintWriter(file);
        outFile.println(saveFormat(name));
        outFile.println(saveFormat(description));
        outFile.println(saveFormat(text));
        outFile.println(objects.size());
        String[] output;
        for(LinkObject obj : objects) {
            outFile.println(obj.getClass().getSimpleName());
            output = obj.save();
            outFile.println(output.length);
            for(String line : output) {
                outFile.println(line);
            }
        }
        outFile.close();
    }

    public void exportAsSite(File selectedFile) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public Map getMapAt(int i) {
        return (Map)((DefaultMutableTreeNode)root.getChildAt(1).getChildAt(i)).getUserObject();
    }
    
}
