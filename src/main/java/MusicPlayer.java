import com.sedmelluq.discord.lavaplayer.player.AudioLoadResultHandler;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.player.DefaultAudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.source.AudioSourceManager;
import com.sedmelluq.discord.lavaplayer.source.AudioSourceManagers;
import com.sedmelluq.discord.lavaplayer.tools.FriendlyException;
import com.sedmelluq.discord.lavaplayer.track.AudioPlaylist;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.managers.AudioManager;

public class MusicPlayer {
    private static MusicPlayer INSTANCE;

    private GuildMusicManager guildMusicManager;
    private AudioPlayerManager audioPlayerManager;

    private MusicPlayer() {
        this.audioPlayerManager = new DefaultAudioPlayerManager();
        AudioSourceManagers.registerRemoteSources(this.audioPlayerManager);
        AudioSourceManagers.registerLocalSource(this.audioPlayerManager);

    }

    public static MusicPlayer getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new MusicPlayer();
        }

        return INSTANCE;
    }

//    public GuildMusicManager getGuildMusicManager(AudioManager audioManager) {
//        var guildMusicManager = new GuildMusicManager(this.audioPlayerManager);
//
//        audioManager.setSendingHandler(guildMusicManager.getSendHandler());
//
//        return guildMusicManager;
//    }

    public void loadAndPlay(TextChannel textChannel, String url) {
        var guildMusicManager = new GuildMusicManager(this.audioPlayerManager);
        textChannel.getGuild().getAudioManager().setSendingHandler(guildMusicManager.getSendHandler());

//        audioPlayerManager.loadItemOrdered(guildMusicManager, url, new AudioLoadResultHandler() {
//            @Override
//            public void trackLoaded(AudioTrack track) {
//                guildMusicManager.scheduler.queue(track);
//                textChannel.sendMessage(track.getInfo().title).queue();
//            }
//
//            @Override
//            public void playlistLoaded(AudioPlaylist playlist) {
//
//            }
//
//            @Override
//            public void noMatches() {
//
//            }
//
//            @Override
//            public void loadFailed(FriendlyException exception) {
//
//            }
//        });

        audioPlayerManager.loadItem(url, new AudioLoadResultHandler() {
            @Override
            public void trackLoaded(AudioTrack track) {
                guildMusicManager.scheduler.queue(track);
                textChannel.sendMessage("playing: ").append(track.getInfo().title).queue();
            }

            @Override
            public void playlistLoaded(AudioPlaylist playlist) {
                for (AudioTrack track : playlist.getTracks()) {
                    guildMusicManager.scheduler.queue(track);
                }
            }

            @Override
            public void noMatches() {
                textChannel.sendMessage("error: not found audio track");
            }

            @Override
            public void loadFailed(FriendlyException exception) {
                textChannel.sendMessage("error: everything explode");
            }
        });
    }
}
