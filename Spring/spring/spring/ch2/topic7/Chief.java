package spring.ch2.topic7;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Created by louyuting on 17/1/22.
 */
@Component("jack")
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


