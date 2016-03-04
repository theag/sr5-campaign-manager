/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sr5CampaignManager.models;

import java.awt.Color;
import java.awt.Component;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

/**
 *
 * @author nbp184
 */
public class MapDrawingObjectRenderer extends JLabel implements ListCellRenderer {

    public static final Color selectedBackground = new Color(57, 105, 138);
    public static final Color selectedForeground = Color.white;
    
    public MapDrawingObjectRenderer() {
        super();
        this.setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2));
    }
    
    @Override
    public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
        setText("<html>" +value.toString().replaceAll("\\u000A", "<br/>").replaceAll("\\u0009", "&nbsp;&nbsp;&nbsp;&nbsp;") +"</html>");
        if(isSelected) {
            this.setOpaque(true);
            this.setBackground(selectedBackground);
            this.setForeground(selectedForeground);
        } else {
            this.setOpaque(false);
            this.setForeground(Color.black);
        }
        return this;
    }
    
}
