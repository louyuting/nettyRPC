package spring.ch2.topic7.filter;

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
@ContextConfiguration(locations = {"/spring/ch2/topic7/filter/ApplicationContext-test.xml"})
public class ChiefTest {
    @Autowired
    private ApplicationContext applicationContext;

    @Test
    public void testChief(){
        Chief jack = (Chief)applicationContext.getBean("chief");
        System.out.println(jack.getName());

        Fighter mike = (Fighter)applicationContext.getBean("fighter");
        System.out.println(mike.getName());

        System.out.println(applicationContext.containsBean("myComponent"));

    }

}
