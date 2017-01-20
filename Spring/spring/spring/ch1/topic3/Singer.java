package spring.ch1.topic3;

/**
 * Created by louyuting on 17/1/20.
 *不使用AOP
 */
public class Singer {

    private Song song=null;

    public Singer() {
    }

    public Singer(Song song) {
        this.song = song;
    }

    private void beforeSing() {
        System.out.println("beforeSing");
    }

    public void singSong(){
        beforeSing();
        System.out.println(song.toString());
        afterSing();
    }

    private void afterSing() {
        System.out.println("afterSing");
    }


    /**
     * 没有面向使用AOP时候,模拟日志
     * @param args
     */
    public static void main(String[] args) {

        new Singer(new Song("my heart will gon on")).singSong();

    }
}
