package spring.ch1.topic11;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

/**
 * Created by louyuting on 17/1/20.
 * 厨师类
 */
public class Chief {
    private static int index = 0;

    private List<Cake> cakes = null;

    private final int id = index++;

    private String name = "";

    private HashSet<Oven> ovens = null;

    public List<Cake> getCakes() {
        return cakes;
    }
    public int getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public HashSet<Oven> getOvens() {
        return ovens;
    }
    public void setCakes(List<Cake> cakes) {
        this.cakes = cakes;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setOvens(HashSet<Oven> ovens) {
        this.ovens = ovens;
    }



    public List<Cake> makeCakes() {
        for (Iterator<Cake> iterator = cakes.iterator(); iterator.hasNext();) {
            Cake cake = iterator.next();
            System.out.println(name + ": " + cake);
        }
        return getCakes();
    }

    public void userOvens() {
        for (Iterator<Oven> iterator = ovens.iterator(); iterator.hasNext();) {
            Oven oven = iterator.next();
            System.out.println("use " + oven);
        }
    }



}


