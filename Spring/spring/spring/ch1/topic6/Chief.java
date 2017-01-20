package spring.ch1.topic6;

/**
 * Created by louyuting on 17/1/20.
 */
public class Chief {
    private final int id = index++;

    public int getId() {
        return id;
    }

    public void create() {
        System.out.println("chief created");
    }

    public void destroy() {
        System.out.println("chief destroy");
    }

    private static int index=0;
}


