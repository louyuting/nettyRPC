package spring.ch2.topic7.filter;

import org.springframework.beans.factory.annotation.Value;

/**
 * Created by louyuting on 17/1/23.
 */
public class Fighter implements Person {
    @Value("mike")
    private String name = "";

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
