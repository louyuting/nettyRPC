package spring.autoconfig;

import org.springframework.stereotype.Component;

/**
 * Created by louyuting on 17/1/17.
 */
//@Componentb表示当前类会作为组件类,并告  知spring要为这个类创建bean, 后面的括号内可以显示的指定bean的ID
@Component("sgtPeppers")
public class SgtPeppers implements CompactDisc {

    private String title = "Sgt. Pepper's Lonely Hearts Club Band";
    private String artist = "The Beatles";

    @Override
    public void play() {
        System.out.println("Playing " + title + " by " + artist);
    }
}
