package spring.ch1.topic6;

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
@ContextConfiguration(locations = {"/spring/ch1/topic6/ApplicationContext-test.xml"})
public class ChiefTest {
    @Autowired
    private ApplicationContext applicationContext;

    @Test
    public void testSinger(){
        Chief chief = (Chief)applicationContext.getBean("chief");
        System.out.println("chief id=" + chief.getId());
        System.gc();
    }

}
