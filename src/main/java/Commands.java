import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.managers.AudioManager;
import org.jetbrains.annotations.NotNull;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

public class Commands extends ListenerAdapter {
    public String cmd_Join = "aqa芭蕾";
    public String cmd_Leave = "eqe亏内";
    public String cmd_Help = "龙龙";
    public String cmd_Slogan = "我们的口号是";
    public String cmd_Play = "play";

    private static int indexTracker = 0;

    private AudioManager audioManager = null;

    @Override
    public void onGuildMessageReceived(@NotNull GuildMessageReceivedEvent event) {
        super.onGuildMessageReceived(event);

        String[] args = event.getMessage().getContentRaw().split(" ");

        audioManager = event.getGuild().getAudioManager();

        if (args[0].equalsIgnoreCase(cmd_Join)) {
            joinVoiceChannel(event, audioManager);
        }

        if (args[0].equalsIgnoreCase(cmd_Help)) {
            printHelp(event);
        }

        if (args[0].equalsIgnoreCase(cmd_Leave)) {
            leaveVoiceChannel(event, audioManager);
        }

        if (args[0].equalsIgnoreCase(cmd_Slogan)) {
            printSlogan(event);
        }

        if (args[0].equalsIgnoreCase(cmd_Play)) {
            try {
                playVideo(event, args[1], audioManager);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    private void playVideo(GuildMessageReceivedEvent event, String url, AudioManager audioManager) throws IOException {
//        Runtime runtime = Runtime.getRuntime();
//        var process_video = runtime.exec(new String[]{
//                "cmd",
//                "/c",
//                "you-get",
//                "--no-caption",
//                "-f",
//                "-o",
//                "F:\\skyha\\Videos\\you-get_output",
//                "-O",
//                indexTracker + "_video",
//                url
//        });
//
//        boolean downloadingDisplayed = false;
//        while (process_video.isAlive()) {
//            if (!downloadingDisplayed) {
//                System.out.println("downloading...");
//                downloadingDisplayed = true;
//            }
//        }
//        System.out.println("download finished");
//
//        File folder = new File("F:/skyha/Videos/you-get_output/");
//        File[] list = folder.listFiles();
//
//        String tempName = "";
//        for (File file : list) {
//            if (file.isFile()) {
//                CharSequence sequence = new StringBuffer(indexTracker + "_video");
//                if (file.getName().contains(sequence)) {
//                    tempName = file.getName();
//                    System.out.println(file.getName());
//                } else {
//                    System.out.println("don't exist");
//                    return;
//                }
//            }
//        }
//
//        var process_convert = runtime.exec(new String[]{
//                "powershell.exe",
//                "ffmpeg",
//                "-i",
//                "F:\\skyha\\Videos\\you-get_output\\" + tempName,
//                "F:\\skyha\\Videos\\you-get_output\\" + indexTracker + "_audio.mp3"
//        });
//
//        boolean convertingDisplayed = false;
//        while (process_convert.isAlive()) {
//            if (!convertingDisplayed) {
//                System.out.println("converting...");
//                convertingDisplayed = true;
//            }
//        }
//
///////////////////
//
//        process_convert.getOutputStream().close();
//        String line;
//        System.out.println("Standard Output:");
//        BufferedReader stdout = new BufferedReader(new InputStreamReader(
//                process_convert.getInputStream()));
//        while ((line = stdout.readLine()) != null) {
//            System.out.println(line);
//        }
//        stdout.close();
//        System.out.println("Standard Error:");
//        BufferedReader stderr = new BufferedReader(new InputStreamReader(
//                process_convert.getErrorStream()));
//        while ((line = stderr.readLine()) != null) {
//            System.out.println(line);
//        }
//        stderr.close();
//
///////////////////
//
//        System.out.println("convert finished");

        //discord bot play sound from directory
       System.out.println("Play");
        MusicPlayer.getInstance().loadAndPlay(event.getChannel(),url);
    }

    private void printSlogan(GuildMessageReceivedEvent event) {
        event.getChannel().sendMessage("AQA芭蕾，EQE亏内！代表着开心，代表着快乐！OK了家人们。:ok_hand:").queue();
    }

    private void joinVoiceChannel(GuildMessageReceivedEvent event, AudioManager audioManager) {
        System.out.println("Join");
        event.getChannel().sendMessage("代表着开心").queue();
        var vc = event.getMessage().getMember().getVoiceState().getChannel();
        if (vc == null) {
            event.getChannel().sendMessage(event.getAuthor().getName() + " 你是不是有点犟嘴了").queue();
        }

        audioManager.openAudioConnection(vc);
    }

    private void leaveVoiceChannel(GuildMessageReceivedEvent event, AudioManager audioManager) {
        System.out.println("Leave");
        event.getChannel().sendMessage("代表着快乐").queue();
        audioManager.closeAudioConnection();
    }

    private void printHelp(GuildMessageReceivedEvent event) {
        //event.getChannel().sendMessage("一个耗油根").queue();
        EmbedBuilder embed = new EmbedBuilder();
        embed.setTitle("命令列表");
        //embed.setDescription();

        embed.addField(cmd_Help, "显示帮助", true);
        embed.addField(cmd_Join, "加入语音频道", true);
        embed.addField(cmd_Leave, "离开语音频道", true);
        embed.addField(cmd_Slogan, "喊出咱家人口号", true);

        embed.setFooter("Bot created by 徒步阿龙");
        var msg = embed.build();

        event.getChannel().sendMessage(msg).queue();
        embed.clear();
    }


}
