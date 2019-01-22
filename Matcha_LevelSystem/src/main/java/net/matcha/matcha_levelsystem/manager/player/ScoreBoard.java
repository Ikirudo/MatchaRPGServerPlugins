package net.matcha.matcha_levelsystem.manager.player;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import me.lucko.luckperms.LuckPerms;
import me.lucko.luckperms.api.Group;
import me.lucko.luckperms.api.LuckPermsApi;
import me.lucko.luckperms.api.User;
import net.matcha.matcha_levelsystem.Matcha_LevelSystem;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.configuration.file.FileConfiguration;


public class ScoreBoard {
    private Matcha_LevelSystem plugin = (Matcha_LevelSystem)Matcha_LevelSystem.getPlugin(Matcha_LevelSystem.class);
    public List<String> ps = new ArrayList();

    public void sendScoreBoard(Player player)
    {
        if (this.ps.contains(player + ""))
        {
            player.getScoreboard().clearSlot(DisplaySlot.SIDEBAR);
            return;
        }
        //deprecated
    }

    public void SetName(Player player)
    {
        String pname = player.getDisplayName();
        int playerlevel = plugin.getConfig().getInt("Player."+pname+".Level");
        int playerexp = plugin.getConfig().getInt("Player."+pname+".Exp");
        int playergenzaihituyounaexp;
        if(plugin.getConfig().getInt("ExpPerLevel."+playerlevel)==0) {
            playergenzaihituyounaexp = 100;
        }else {
            playergenzaihituyounaexp = plugin.getConfig().getInt("ExpPerLevel." + playerlevel);
        }
        player.setPlayerListName((plugin.getConfig().getString("ConfigPrefix"))
                .replaceAll("<playername>", pname)
                .replaceAll("<playerlevel>", Integer.toString(playerlevel))
                .replaceAll("<playerexp>", Integer.toString(playerexp))
                .replaceAll("<playergenzaihituyounaexp>", Integer.toString(playergenzaihituyounaexp))
                .replaceAll("<playernokoriexp>", Integer.toString(playergenzaihituyounaexp-playerexp))
                .replaceAll(";", ":"));
    }
}
