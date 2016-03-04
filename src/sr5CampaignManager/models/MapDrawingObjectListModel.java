/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sr5CampaignManager.models;

import javax.swing.AbstractListModel;
import sr5CampaignManager.objects.Map;
import sr5CampaignManager.objects.maps.MapDrawingObject;

/**
 *
 * @author nbp184
 */
public class MapDrawingObjectListModel extends AbstractListModel<MapDrawingObject> {
    
    private Map map;
    
    public MapDrawingObjectListModel(Map map) {
        this.map = map;
    }

    @Override
    public int getSize() {
        return map.getObjectCount();
    }

    public void add(MapDrawingObject obj) {
        map.addObject(obj);
        map.sortObjects();
        this.fireContentsChanged(this, 0, map.getObjectCount()-1);
    }

    public void remove(MapDrawingObject obj) {
        int index = map.removeObject(obj);
        this.fireIntervalRemoved(this, index, index);
    }

    @Override
    public MapDrawingObject getElementAt(int index) {
        return map.getObject(index);
    }

    public void update(MapDrawingObject object) {
        map.sortObjects();
        this.fireContentsChanged(this, 0, map.getObjectCount()-1);
    }
    
}
