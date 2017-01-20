package spring.ch1.topic8;

/**
 * Created by louyuting on 17/1/20.
 */
public class LoginServiceImpl {
    public void login(User user) {
        if (user != null) {
            System.out.println(user.getName() + " login");
        }
    }

}
