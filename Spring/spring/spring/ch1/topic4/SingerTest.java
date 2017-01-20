package spring.ch1.topic4;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by louyuting on 17/1/20.
 * 这里使用依赖注入的方式测试:依赖注入采用反射机制实例化的.
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/spring/ch1/topic4/ApplicationContext-test.xml"})
public class SingerTest {
    @Autowired
    private ApplicationContext applicationContext;

    @Test
    public void testSinger(){
        Singer singer = (Singer)applicationContext.getBean("jack");
        singer.singSong();
    }



}
