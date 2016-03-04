/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sr5CampaignManager.dialogs;

import java.util.ArrayList;
import java.util.Enumeration;
import javax.swing.tree.TreeNode;
import sr5CampaignManager.objects.Weapon;

/**
 *
 * @author nbp184
 */
public class WeapTreeNode implements TreeNode {
    
    private WeapTreeNode parent;
    private Object value;
    private boolean isLeaf;
    private boolean isEditable;
    private ArrayList<WeapTreeNode> children;
    
    public WeapTreeNode(Object value, boolean isEditable) {
        if(value instanceof Weapon) {
            this.value = ((Weapon)value).copy();
        } else {
            this.value = value;
        }
        this.isEditable = isEditable;
        this.isLeaf = value instanceof Weapon;
        children = new ArrayList<>();
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
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public String toString() {
        return value.toString();
    }
    
    public void addChild(WeapTreeNode node) {
        children.add(node);
        node.parent = this;
    }
    
    public void insertChildAt(WeapTreeNode node, int index) {
        children.add(index, node);
        node.parent = this;
    }
    
    public void moveChildUp(int index) {
        if(index > 0) {
            WeapTreeNode node = children.remove(index);
            children.add(index-1, node);
        }
    }
    
    public void moveChildDown(int index) {
        if(index < children.size()-1) {
            WeapTreeNode node = children.remove(index);
            children.add(index+1, node);
        }
    }

    public boolean isEditable() {
        return isEditable;
    }

    public String getName() {
        return value.toString();
    }
    
    public Weapon getWeapon() {
        if(value instanceof Weapon) {
            return ((Weapon)value).copy();
        } else {
            return null;
        }
    }

    public void setValue(String text) {
        if(!isLeaf) {
            value = text;
        }
    }

    public void removeChild(WeapTreeNode node) {
        children.remove(node);
    }

    public void moveOut() {
        if(parent.parent != null) {
            parent.removeChild(this);
            parent.parent.addChild(this);
        }
    }

    public WeapTreeNode[] getSubCategories() {
        int count = 0;
        for(WeapTreeNode node : children) {
            if(!node.isLeaf) {
                count++;
            }
        }
        WeapTreeNode[] rv = new WeapTreeNode[count];
        count = 0;
        for(WeapTreeNode node : children) {
            if(!node.isLeaf) {
                rv[count++] = node;
            }
        }
        return rv;
    }

    public WeapTreeNode[] getSubCategoriesWithoutNode(WeapTreeNode exclude) {
        int count = 0;
        for(WeapTreeNode node : children) {
            if(!node.isLeaf && node != exclude) {
                count++;
            }
        }
        WeapTreeNode[] rv = new WeapTreeNode[count];
        count = 0;
        for(WeapTreeNode node : children) {
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
        ArrayList<WeapTreeNode> keeping = new ArrayList<>();
        for(WeapTreeNode child : children) {
            if(!child.isEditable) {
                keeping.add(child);
            }
        }
        children.clear();
        children.addAll(keeping);
    }

    public void notEditable() {
        isEditable = false;
    }
    
}
