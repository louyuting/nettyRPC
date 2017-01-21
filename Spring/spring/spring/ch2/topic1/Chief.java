package spring.ch2.topic1;

/**
 * Created by louyuting on 17/1/20.
 * 厨师类
 */
public class Chief {
    private Cake cake = null;

    private String name = "";

    //构造器


    public Chief(Cake cake) {
        this.cake = cake;
    }

    public Cake getCake() {
        return cake;
    }

    public String getName() {
        return name;
    }

    public Cake makeOneCake() {
        System.out.println(getName() + " make " + getCake().getName());
        return cake;
    }

    public void setCake(Cake cake) {
        this.cake = cake;
    }

    public void setName(String name) {
        this.name = name;
    }

}


