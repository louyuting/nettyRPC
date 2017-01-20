package spring.ch1.topic5;

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
@ContextConfiguration(locations = {"/spring/ch1/topic5/ApplicationContext-test.xml"})
public class ChiefTest {
    @Autowired
    private ApplicationContext applicationContext;

    @Test
    public void testSinger(){
        for(int i=0; i<3; i++){
            Chief chief = (Chief)applicationContext.getBean("chief");
            System.out.println("chief id=" + chief.getId());
        }
    }
    /**
     *  //output ~ scope是singleton时
     *  chief id=0
        chief id=0
        chief id=0
     这说明创建的是单例的,也是默认的bean作用域
     */

    /**
     *  //output ~ scope是设置成prototype时
     *  chief id=0
     chief id=1
     chief id=2
     这说明创建的对象是任意次创建
     */


}
