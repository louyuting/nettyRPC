package spring.ch1.topic8;

import org.aspectj.lang.JoinPoint;

/**
 * Created by louyuting on 17/1/20.
 */
public class Log8 {
    public void before() {
        System.out.println("before login");
    }

    public void after(JoinPoint joinpoint) {
        Object[] objects = joinpoint.getArgs();
        for (int i = 0; i < objects.length; i++) {
            User user = (User) objects[i];
            System.out.println("save login log:" + user.getName() + " login");
        }
    }
}
