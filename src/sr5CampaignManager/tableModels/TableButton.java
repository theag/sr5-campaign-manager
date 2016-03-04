/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sr5CampaignManager.tableModels;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.AbstractCellEditor;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;

/**
 *
 * @author nbp184
 */
public class TableButton extends AbstractCellEditor implements TableCellRenderer, TableCellEditor, ActionListener {

    private JButton edit;
    
    public TableButton(String text) {
        edit = new JButton(text);
        edit.addActionListener(this);
    }
    
    @Override
    public Object getCellEditorValue() {
        return edit;
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        edit.setActionCommand(row+","+column);
        return edit;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        for(TableButtonListener listener : listenerList.getListeners(TableButtonListener.class)) {
            int index = e.getActionCommand().indexOf(",");
            listener.actionPerformed(Integer.parseInt(e.getActionCommand().substring(0, index)), Integer.parseInt(e.getActionCommand().substring(index+1)));
        }
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        return new JButton(edit.getText());
    }

    public void addTableButtonListener(TableButtonListener listener) {
        listenerList.add(TableButtonListener.class, listener);
    }
    
}
