package spring.ch1.topic7_2;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by louyuting on 17/1/20.
 * 怎样通过属性向对象注入另一个对象的引用
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/spring/ch1/topic7_2/ApplicationContext-test.xml"})
public class CakeTest {
    @Autowired
    private ApplicationContext applicationContext;

    @Test
    public void testSinger(){
        Chief chief = applicationContext.getBean(Chief.class);
        System.out.println("chief name:" + chief.getName());
        System.out.println(chief.getName() + " make a cake ,cake's name :" + chief.makeOneCake().getName());
    }
    /**
     *  //output ~:
     *
     */
}
