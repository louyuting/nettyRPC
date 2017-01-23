package spring.ch2.topic8;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by louyuting on 17/1/23.
 */
@Configuration
public class SpringBeans {
    @Bean
    public Chief jack() {
        Chief chief = new Chief();
        chief.setName("mike");
        return chief;
    }
}
