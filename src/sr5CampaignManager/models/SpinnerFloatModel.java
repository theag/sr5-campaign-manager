/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sr5CampaignManager.models;

import javax.swing.AbstractSpinnerModel;
import javax.swing.SpinnerNumberModel;

/**
 *
 * @author nbp184
 */
public class SpinnerFloatModel extends SpinnerNumberModel {

    private float value;
    private final Float minimum;
    private final Float maximum;
    private final float stepSize;
    
    public SpinnerFloatModel(float value, Float minimum, Float maximum, float stepSize) {
        this.value = value;
        this.minimum = minimum;
        this.maximum = maximum;
        this.stepSize = stepSize;
    }

    @Override
    public Object getValue() {
        return value;
    }

    @Override
    public void setValue(Object value) {
        float newValue;
        if(value instanceof Integer) {
            newValue = (Integer)value;
        } else if(value instanceof Float) {
            newValue = (Float)value;
        } else {
            return;
        }
        if((maximum == null || newValue <= maximum) && (minimum == null || newValue >= minimum)) {
            this.value = newValue;
            this.fireStateChanged();
        }
    }

    @Override
    public Object getNextValue() {
        if(maximum == null || value + stepSize <= maximum) {
            return value + stepSize;
        } else {
            return null;
        }
    }

    @Override
    public Object getPreviousValue() {
        if(minimum == null || value - stepSize >= minimum) {
            return value - stepSize;
        } else {
            return null;
        }
    }

}
