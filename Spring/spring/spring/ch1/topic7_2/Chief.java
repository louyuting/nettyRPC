package spring.ch1.topic7_2;

/**
 * Created by louyuting on 17/1/20.
 */
public class Chief {
    private Cake cake = null;
    private String name = "";
    private final int id = index++;
    private static int index = 0;



    public Chief(String name) {
        this.name = name;
    }

    public Cake getCake() {
        return cake;
    }
    public void setCake(Cake cake) {
        this.cake = cake;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public Cake makeOneCake() {
        return cake;
    }
}


