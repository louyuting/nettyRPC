package spring.ch2.topic8;

import org.springframework.beans.factory.annotation.Value;

/**
 * Created by louyuting on 17/1/23.
 */
public class Chief {
    private final int id = index++;

    private static int index = 0;

    public int getId() {
        return id;
    }

    @Value("jack")
    private String name = "";

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
