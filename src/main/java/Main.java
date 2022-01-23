import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;

import javax.security.auth.login.LoginException;

public class Main {
    public static void main(String[] args) throws LoginException {
        JDABuilder jda = JDABuilder.createDefault("OTM0MjA3MzAwNjk5MzAzOTk5.YesuWQ.UIwU-KF_BzVRRpfam1MeWBttTTs");
        jda.setActivity(Activity.watching("拷打女主播"));
        jda.setStatus(OnlineStatus.ONLINE);
        jda.addEventListeners(new Commands());
        jda.build();
    }
}
