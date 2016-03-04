/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sr5CampaignManager.dialogs;

import java.util.ArrayList;
import java.util.Enumeration;
import javax.swing.tree.TreeNode;
import sr5CampaignManager.Resources;

/**
 *
 * @author nbp184
 */
public class StringTreeNode implements TreeNode {
    
    private StringTreeNode parent;
    private String value;
    private boolean isLeaf;
    private boolean isEditable;
    private ArrayList<StringTreeNode> children;
    
    public StringTreeNode(String value, boolean isEditable, boolean isLeaf) {
        this.value = value;
        this.isEditable = isEditable;
        this.isLeaf = isLeaf;
        children = new ArrayList<>();
        parent = null;
    }
    
    public StringTreeNode(String value) {
        this.value = value;
        this.isEditable = true;
        this.isLeaf = false;
        children = new ArrayList<>();
        parent = null;
    }
    
    public StringTreeNode(String value, boolean isLeaf) {
        this.value = value;
        this.isEditable = true;
        this.isLeaf = isLeaf;
        if(isLeaf) {
            children = null;
        } else {
            children = new ArrayList<>();
        }
        parent = null;
    }

    @Override
    public TreeNode getChildAt(int childIndex) {
        return children.get(childIndex);
    }

    @Override
    public int getChildCount() {
        return children.size();
    }

    @Override
    public TreeNode getParent() {
        return parent;
    }

    @Override
    public int getIndex(TreeNode node) {
        return children.indexOf(node);
    }

    @Override
    public boolean getAllowsChildren() {
        return !isLeaf;
    }

    @Override
    public boolean isLeaf() {
        return isLeaf;
    }

    @Override
    public Enumeration children() {
        String line = "";
        for(StringTreeNode child : children) {
            line += child.value +Resources.unitSep;
        }
        return new java.util.StringTokenizer(line, Resources.unitSep);
    }
    
    @Override
    public String toString() {
        return value;
    }
    
    public void addChild(StringTreeNode node) {
        children.add(node);
        node.parent = this;
    }
    
    public void insertChildAt(StringTreeNode node, int index) {
        children.add(index, node);
        node.parent = this;
    }
    
    public void moveChildUp(int index) {
        if(index > 0) {
            StringTreeNode node = children.remove(index);
            children.add(index-1, node);
        }
    }
    
    public void moveChildDown(int index) {
        if(index < children.size()-1) {
            StringTreeNode node = children.remove(index);
            children.add(index+1, node);
        }
    }

    public boolean isEditable() {
        return isEditable;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String text) {
        value = text;
    }

    public void removeChild(StringTreeNode node) {
        children.remove(node);
    }

    public void moveOut() {
        if(parent.parent != null) {
            parent.removeChild(this);
            parent.parent.addChild(this);
        }
    }

    public StringTreeNode[] getSubCategories() {
        int count = 0;
        for(StringTreeNode node : children) {
            if(!node.isLeaf) {
                count++;
            }
        }
        StringTreeNode[] rv = new StringTreeNode[count];
        count = 0;
        for(StringTreeNode node : children) {
            if(!node.isLeaf) {
                rv[count++] = node;
            }
        }
        return rv;
    }

    public StringTreeNode[] getSubCategoriesWithoutNode(StringTreeNode exclude) {
        int count = 0;
        for(StringTreeNode node : children) {
            if(!node.isLeaf && node != exclude) {
                count++;
            }
        }
        StringTreeNode[] rv = new StringTreeNode[count];
        count = 0;
        for(StringTreeNode node : children) {
            if(!node.isLeaf && node != exclude) {
                rv[count++] = node;
            }
        }
        return rv;
    }

    public void removeAllChildren() {
        children.clear();
    }

    public void removeAllEditableChildren() {
        ArrayList<StringTreeNode> keeping = new ArrayList<>();
        for(StringTreeNode child : children) {
            if(!child.isEditable) {
                keeping.add(child);
            }
        }
        children.clear();
        children.addAll(keeping);
    }
    
}
