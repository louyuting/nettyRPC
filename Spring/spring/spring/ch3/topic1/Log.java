package spring.ch3.topic1;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;

/**
 * Created by louyuting on 17/1/24.
 */
public class Log {
    public void washOven() {
        System.out.println("washOven,logging.....");
    }

    public void prepare() {
        System.out.println("prepare,logging.....");
    }

    public void checkOrder(JoinPoint joinpoint) {
        System.out.println("checkOrder");

        for (Object item : joinpoint.getArgs()) {
            if (item instanceof Cake) {
                Cake cake = (Cake) item;
                System.out.println("做的蛋糕的名字是:"+cake.getName());
            }
        }
    }

    public void after() {
        System.out.println("after someting to do,logging.....");
    }

    public void around(ProceedingJoinPoint joinPoint) throws Throwable {
        washOven();
        prepare();
        long startTime = System.currentTimeMillis();//记录开始时间
        //类似于before执行前
        joinPoint.proceed();
        //类似于after执行后
        long endTime = System.currentTimeMillis();
        System.out.println("use time:" + (endTime - startTime));
        after();
    }

}
