/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sr5CampaignManager.models;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;
import sr5CampaignManager.objects.maps.NamedColour;

/**
 *
 * @author nbp184
 */
public class MyComboBoxCellRenderer extends JLabel implements ListCellRenderer {
    
    public static final int NAMED_COLOUR = 0;
    public static final int LINE_WIDTH = 1;
    
    public static final Color selectedBackground = new Color(57, 105, 138);
    public static final Color selectedForeground = Color.white;
    
    private final int type;
    
    public MyComboBoxCellRenderer(int type) {
        super();
        this.setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2));
        this.type = type;
    }
    
    @Override
    public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
        if(type == NAMED_COLOUR) {
            setText(value.toString());
            NamedColour colour = (NamedColour)value;
            BufferedImage image = new BufferedImage(14, 14, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g = image.createGraphics();
            g.setColor(colour.colour);
            g.fillRect(1, 1, image.getWidth() - 2, image.getHeight() - 2);
            g.dispose();
            this.setIcon(new ImageIcon(image));
        } else if(type == LINE_WIDTH) {
            String str = value.toString();
            while(str.length() < 12) {
                str += " ";
            }
            setText(str);
        }
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
