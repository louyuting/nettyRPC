package spring.ch1.topic5;

/**
 * Created by louyuting on 17/1/20.
 * 测试bean的作用域
 */
public class Chief {

    private static int index=0;

    private final int id = index++;

    public int getId() {
        return id;
    }
}


