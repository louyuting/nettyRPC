package spring.ch1.topic7_1;

/**
 * Created by louyuting on 17/1/20.
 * 注入属性,记得属性必须要写setter方法  不然就会抛出异常,注入失败.
 */
public class Cake {
    private static int index = 0;

    private final int id = index++;

    private String name = "";

    private double size = 0;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getSize() {
        return size;
    }

    public void setSize(double size) {
        this.size = size;
    }

    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return "create the cake,its id:" + id + ", size:" + size + " inch ,name:" + name;
    }
}
