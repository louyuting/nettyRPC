package spring.ch1.topic2;

/**
 * Created by louyuting on 17/1/20.
 *
 */
public class Song {
    private String name;

    public Song(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "sing a song "+ this.name;
    }
}
