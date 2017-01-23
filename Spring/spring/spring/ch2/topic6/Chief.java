package spring.ch2.topic6;

import org.springframework.beans.factory.annotation.Value;

/**
 * Created by louyuting on 17/1/22.
 */
public class Chief {

    @Value("jack")
    private String name = "";

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
}


