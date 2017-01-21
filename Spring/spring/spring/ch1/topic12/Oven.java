package spring.ch1.topic12;

/**
 * Created by louyuting on 17/1/21
 * 烤炉类
 */
public class Oven {
    private String name = "";

    @Override
    public String toString() {
        return name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
