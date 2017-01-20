package spring.ch1.topic2;

/**
 * Created by louyuting on 17/1/20.
 */
public class Singer {

    private Song song=null;

    public Singer(Song song) {
        this.song = song;
    }

    public void singSong(){
        System.out.println(song.toString());
    }

}
