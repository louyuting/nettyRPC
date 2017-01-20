package spring.ch1.topic1;

/**
 * Created by louyuting on 17/1/20.
 * 以歌唱家唱歌这个为例,在没有依赖注入的情况下的实现;
 *
 * 主要问题:紧密耦合,要测试Singer必须要new一个song对象出来.
 *         Singer只能唱指定的song,如果换歌必须修改代码.
 */
public class Singer {

    private Song song;

    public Singer() {
        this.song = new Song();//强耦合的地方
    }

    public void singSong(){
        System.out.println(song.toString());
    }


    public static void main(String[] args) {
        new Singer().singSong();
    }
    /**
     *
     * output:~//:
     * sing a song
     */
}
