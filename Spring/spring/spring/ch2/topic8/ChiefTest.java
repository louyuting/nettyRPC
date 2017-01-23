package spring.ch2.topic8;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by louyuting on 17/1/20.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/spring/ch2/topic8/ApplicationContext-test.xml"})
public class ChiefTest {
    @Autowired
    private ApplicationContext applicationContext;

    @Test
    public void testChief(){
        Chief jack1 = (Chief)applicationContext.getBean(Chief.class);
        System.out.println(jack1.getId());

        Chief jack = (Chief)applicationContext.getBean("jack");
        System.out.println(jack.getId());
    }

}
