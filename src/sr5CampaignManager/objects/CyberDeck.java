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
public class CyberDeck implements Comparable<CyberDeck> {

    static CyberDeck load(String input, String delimiter) {
        StringTokenizer tokens = new StringTokenizer(input, delimiter);
        return new CyberDeck(Run.loadFormat(tokens.nextToken()), Run.loadFormat(tokens.nextToken()), Run.loadFormat(tokens.nextToken()));
    }
    
    public String name;
    public String rating;
    public String attributeArray;
    
    public CyberDeck(String name, String rating, String attributeArray) {
        this.name = name;
        this.rating = rating;
        this.attributeArray = attributeArray;
    }
    
    @Override
    public boolean equals(Object o) {
        if(o instanceof CyberDeck) {
            return name.compareTo(((CyberDeck)o).name) == 0;
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 67 * hash + Objects.hashCode(this.name);
        return hash;
    }

    @Override
    public int compareTo(CyberDeck o) {
        if(equals(o)) {
            return 0;
        } else if(rating.compareTo(o.rating) != 0) {
            return rating.compareTo(o.rating);
        } else {
            return name.compareTo(o.name);
        }
    }
    
    @Override
    public String toString() {
        return name;
    }

    String save(String delimiter) {
        return Run.saveFormat(name) +delimiter +Run.saveFormat(rating) +delimiter +Run.saveFormat(attributeArray);
    }
    
}
