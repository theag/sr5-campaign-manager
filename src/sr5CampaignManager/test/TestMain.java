/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sr5CampaignManager.test;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import sr5CampaignManager.objects.Map;
import sr5CampaignManager.objects.maps.MapSecurity;
import sr5CampaignManager.objects.maps.MapText;

/**
 *
 * @author nbp184
 */
public class TestMain {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        int index = 8;
        
        Map map = new Map("Utitled");
        map.width = 1;
        map.height = 1;
        MapText mt = new MapText(MapSecurity.locksList[index], 8, 0, 0, MapText.LEFT, MapText.TOP, false);
        mt.colour = map.getNamedColour(0);
        map.addObject(mt);
        MapSecurity ms = new MapSecurity(MapSecurity.LOCK, MapSecurity.locksList[index], 0.5f, 0.5f);
        if(MapSecurity.isLineType(ms)) {
            ms.point[0] = 0;
            ms.point[2] = 1;
            ms.point[3] = 0.5f;
            ms.lineWidth = 5;
        } else if(ms.type == MapSecurity.LOCK) {
            ms.colour = map.getNamedColour(0);
        }
        map.addObject(ms);
        
        BufferedImage image = new BufferedImage(map.width*map.boxSize+1, map.height*map.boxSize+1, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = image.createGraphics();
        g.setColor(Color.white);
        g.fillRect(0, 0, image.getWidth(), image.getHeight());
        g.setColor(new Color(216, 216, 216));
        for(int i = 0; i < image.getWidth(); i += map.boxSize) {
            g.drawLine(i, 0, i, image.getHeight());
        }
        for(int i = 0; i < image.getHeight(); i += map.boxSize) {
            g.drawLine(0, i, image.getWidth(), i);
        }
        for(int i = 0; i < map.getObjectCount(); i++) {
            map.getObject(i).draw(g, Color.white);
        }
        g.dispose();
        
        boolean write = ImageIO.write(image, "bmp", new File("testimage.bmp"));
        System.out.println(write);
    }
    
}
