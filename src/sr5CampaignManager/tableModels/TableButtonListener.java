/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sr5CampaignManager.tableModels;

import java.util.EventListener;

/**
 *
 * @author nbp184
 */
public interface TableButtonListener extends EventListener {
    
    public void actionPerformed(int rowIndex, int columnIndex);
    
}
