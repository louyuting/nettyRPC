package spring.ch2.topic2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

/**
 * Created by louyuting on 17/1/22.
 */
public class Chief {

    @Qualifier("cake")
    @Autowired(required = false)
    private Cake cake = null;

    private String name = "";

    /*@Autowired
    public Chief(Cake cake) {
        this.cake = cake;
    }*/

    /*public Cake getCake() {
        return cake;
    }*/
    /*@Autowired
    public void setCake(Cake cake) {
        this.cake = cake;
    }*/
    /*@Autowired
    public void injectCake(Cake cake) {
        this.cake = cake;
    }*/

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public Cake makeOneCake() {
        System.out.println(getName() + " make " + cake.getName());
        return cake;
    }
}


