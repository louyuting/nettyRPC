package spring.ch1.topic12;

import java.util.Map;

/**
 * Created by louyuting on 17/1/20.
 * 厨师类
 */
public class Chief {
    private static int index = 0;

    private final int id = index++;

    private String name = "";

    private Map<String, Oven> ovens = null;


    public int getId() {
        return id;
    }
    public String getName() {
        return name;
    }

    public void userOvens() {
        for (String key : ovens.keySet()) {
            System.out.println("oven key:" + key);
            System.out.println(name + " use " + ovens.get(key));
        }
    }

    public void setName(String name) {
        this.name = name;
    }

    public Map<String, Oven> getOvens() {
        return ovens;
    }

    public void setOvens(Map<String, Oven> ovens) {
        this.ovens = ovens;
    }

}


