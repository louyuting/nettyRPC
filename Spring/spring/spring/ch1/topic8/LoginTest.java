package spring.ch1.topic8;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by louyuting on 17/1/20.
 * 利用AOP 记录日志
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/spring/ch1/topic8/ApplicationContext-test.xml"})
public class LoginTest {
    @Autowired
    private ApplicationContext applicationContext;

    @Test
    public void testLogin(){
        LoginServiceImpl login = applicationContext.getBean(LoginServiceImpl.class);
        User user = applicationContext.getBean(User.class);
        login.login(user);
    }
    /**
     *  //output ~:
     before login
     jack login
     save login log:jack login
     */
}
