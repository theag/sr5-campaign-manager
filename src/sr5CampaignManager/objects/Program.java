/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sr5CampaignManager.objects;

import java.util.Objects;
import java.util.StringTokenizer;

/**
 *
 * @author nbp184
 */
public class Program implements Comparable<Program> {

    static Program load(String input, String delimiter) {
        StringTokenizer tokens = new StringTokenizer(input, delimiter);
        return new Program(Run.loadFormat(tokens.nextToken()), Run.loadFormat(tokens.nextToken()), false);
    }
    
    public String name;
    public String effect;
    public final boolean fromList;
    
    public Program(String name, String effect, boolean fromList) {
        this.name = name;
        this.effect = effect;
        this.fromList = fromList;
    }
    
    @Override
    public String toString() {
        return name;
    }
    
    @Override
    public boolean equals(Object o) {
        if(o instanceof Program) {
            return name.compareTo(((Program)o).name) == 0;
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 53 * hash + Objects.hashCode(this.name);
        return hash;
    }

    @Override
    public int compareTo(Program o) {
        return name.compareTo(o.name);
    }
    
    public String save(String delimiter) {
        return Run.saveFormat(name) +delimiter +Run.saveFormat(effect);
    }
    
}
