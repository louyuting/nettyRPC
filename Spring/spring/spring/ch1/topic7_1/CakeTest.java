package spring.ch1.topic7_1;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by louyuting on 17/1/20.
 * 通过bean标签下的 property标签注入属性属性
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/spring/ch1/topic7_1/ApplicationContext-test.xml"})
public class CakeTest {
    @Autowired
    private ApplicationContext applicationContext;

    @Test
    public void testCake(){
        Cake cake = (Cake)applicationContext.getBean("cake");
        System.out.println(cake.getId());
        System.out.println(cake.getName());
        System.out.println(cake.getSize());
    }

}
