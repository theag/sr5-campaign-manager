/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sr5CampaignManager.tableModels;

import java.awt.Color;
import java.awt.Component;
import java.util.HashMap;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;

/**
 *
 * @author nbp184
 */
public class HideCellsRenderer extends DefaultTableCellRenderer {
    
    private Color colour;
    private HashMap<Class, TableCellRenderer> rendererMap;
    
    public HideCellsRenderer(Color colour) {
        this.colour = colour;
        this.rendererMap = new HashMap<>();
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        if(value != null) {
            if(rendererMap.containsKey(table.getColumnClass(column))) {
                return rendererMap.get(table.getColumnClass(column)).getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            } else {
                return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            }
        } else {
            JPanel rv = new JPanel();
            rv.setBackground(colour);
            return rv;
        }
    }

    public void addSubRenderer(Class aClass, TableCellRenderer rend) {
        rendererMap.put(aClass, rend);
    }
    
}
