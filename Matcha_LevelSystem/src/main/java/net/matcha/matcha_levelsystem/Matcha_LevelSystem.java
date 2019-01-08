package net.matcha.matcha_levelsystem;

import com.connorlinfoot.actionbarapi.ActionBarAPI;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.player.PlayerExpChangeEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class Matcha_LevelSystem extends JavaPlugin implements Listener {
    Map<String,Integer > level = new HashMap<>();
    Map<String,Integer > exp = new HashMap<>();
    @Override
    public void onEnable() {
        getConfig().options().copyDefaults(true);
        saveDefaultConfig();
        reloadConfig();
        FileConfiguration config = getConfig();
        getServer().getPluginManager().registerEvents(this, this);
    }
    @Override
    public void onDisable() {
    }
    @EventHandler
    public void OnPlayerExpChange(PlayerExpChangeEvent e){
    }
    @EventHandler
    public void onplayerjoinevent(PlayerJoinEvent e){
        Player p = e.getPlayer();
        String pname = p.getDisplayName();
        int playerlevel = getConfig().getInt("Player."+pname+".Level");
        if (playerlevel ==0){
            level.put(pname, 1);
            playerlevel = 1;
        }else {
            level.put(pname, playerlevel);
        }
        int playerexp = getConfig().getInt("Player."+pname+".Exp");
        exp.put(pname,playerexp);
        int playergenzaihituyounaexp;
        if(getConfig().getInt("ExpPerLevel."+playerlevel)==0) {
            playergenzaihituyounaexp = 100;
        }else {
            playergenzaihituyounaexp = getConfig().getInt("ExpPerLevel." + playerlevel);
        }
        createScoreboard(p);
        ActionBarAPI.sendActionBar(p,getConfig().getString("ActionBar")
                .replaceAll("<playerlevel>", Integer.toString(playerlevel))
                .replaceAll("<playerexp>", Integer.toString(playerexp))
                .replaceAll("<playergenzaihituyounaexp>", Integer.toString(playergenzaihituyounaexp))
                .replaceAll("<playernokoriexp>", Integer.toString(playergenzaihituyounaexp-playerexp))
                .replaceAll(";", ":")
                ,99);
    }
    @EventHandler
    public void onplayerquitevent(PlayerQuitEvent e){
        Player p = e.getPlayer();
        String pname = p.getDisplayName();
        getConfig().set("Player."+pname+".Level",level.get(pname));
        getConfig().set("Player."+pname+".Exp",exp.get(pname));
        saveConfig();
        level.remove(pname);
        exp.remove(pname);
    }
    @EventHandler(priority = EventPriority.HIGHEST)
    public void entitydeathevent(EntityDeathEvent e){
        if(e.getEntity().getKiller().getType() == EntityType.PLAYER &&e.getEntity().getKiller().getType() != null) {
            Player p = e.getEntity().getKiller();
            String pname = p.getDisplayName();
            EntityType mobsyurui = e.getEntity().getType();
            int playergenzaiexp = exp.get(pname);
            int playergenzailevel = level.get(pname);
            int playergenzaihituyounaexp;
            if (getConfig().getInt("ExpPerLevel." + playergenzailevel) == 0) {
                playergenzaihituyounaexp = 100;
            } else {
                playergenzaihituyounaexp = getConfig().getInt("ExpPerLevel." + playergenzailevel);
            }
            if (getConfig().getInt("MobToExp." + mobsyurui) != 0) {
                nagai(mobsyurui, playergenzaiexp, playergenzaihituyounaexp, playergenzailevel, pname);
            } else {
                p.sendMessage("おかしいぞこのentity(Ikirudoに報告ください)");
                p.sendMessage("mobの種類を表示test:" + mobsyurui);
            }
            int playerlevel = level.get(pname);
            int playerexp = exp.get(pname);
            if (getConfig().getInt("ExpPerLevel." + playerlevel) == 0) {
                playergenzaihituyounaexp = 100;
            } else {
                playergenzaihituyounaexp = getConfig().getInt("ExpPerLevel." + playerlevel);
            }
            ActionBarAPI.sendActionBar(p, ChatColor.YELLOW + "LEVEL:" + playerlevel + "Lv    " + ChatColor.RED + "EXP: " + playerexp + "/" + playergenzaihituyounaexp, 99);
            getConfig().set("Player." + pname + ".Level", level.get(pname));
            getConfig().set("Player." + pname + ".Exp", exp.get(pname));
            saveConfig();
            createScoreboard(p);
            saveConfig();
        }
    }
    public void createScoreboard(Player player){
        String pname = player.getDisplayName();
        int playerlevel = getConfig().getInt("Player."+pname+".Level");
        int playerexp = getConfig().getInt("Player."+pname+".Exp");
        int playergenzaihituyounaexp;
        if(getConfig().getInt("ExpPerLevel."+playerlevel)==0) {
            playergenzaihituyounaexp = 100;
        }else {
            playergenzaihituyounaexp = getConfig().getInt("ExpPerLevel." + playerlevel);
        }
        ScoreboardManager manager = Bukkit.getScoreboardManager();
        Scoreboard board = manager.getNewScoreboard();
        Objective objective = board.registerNewObjective(getConfig().getString("ScoreBoard.Title"), "dummy");
        objective.setDisplayName(getConfig().getString("ScoreBoard.Title"));
        objective.setDisplaySlot(DisplaySlot.SIDEBAR);
        int suujisize = getConfig().getConfigurationSection("ScoreBoard.scoreList").getKeys(false).size();
        String kuuhakuhuyasu = "              ";
        String kuuhaku =" ";
        kuuhaku=" ";
        kuuhakuhuyasu = "              ";
        for(int zerokara= 0;zerokara<suujisize;zerokara++){
            if(getConfig().getString("ScoreBoard.scoreList."+(suujisize-zerokara))!="space"){
                objective.getScore(getConfig().getString("ScoreBoard.scoreList."+(suujisize-zerokara))
                        .replaceAll("<playerlevel>", Integer.toString(playerlevel))
                        .replaceAll("<playerexp>", Integer.toString(playerexp))
                        .replaceAll("<playergenzaihituyounaexp>", Integer.toString(playergenzaihituyounaexp))
                        .replaceAll("<playernokoriexp>", Integer.toString(playergenzaihituyounaexp-playerexp))
                        .replaceAll(";", ":")
                ).setScore(zerokara);
            }else{
                kuuhaku = kuuhaku + kuuhakuhuyasu;
                objective.getScore(kuuhaku).setScore(zerokara);
            }
        }
        player.setScoreboard(board);
    }
    public void nagai(EntityType mobsyurui,int playergenzaiexp,int playergenzaihituyounaexp,int playergenzailevel,String pname){
        int sinexp;
        int sinlevel;
        int mobexp = getConfig().getInt("MobToExp."+mobsyurui);
        if(playergenzaiexp + mobexp <= playergenzaihituyounaexp) {
            sinexp = playergenzaiexp + mobexp;
            exp.put(pname,sinexp);
        }else if(playergenzaiexp + mobexp == playergenzaihituyounaexp){
            sinexp = playergenzaiexp + mobexp;
            exp.put(pname,sinexp);
        }else {
            for (playergenzailevel = level.get(pname); playergenzaiexp + mobexp >= playergenzaihituyounaexp; playergenzailevel++) {
                sinexp = playergenzaiexp + mobexp -playergenzaihituyounaexp ;
                if(getConfig().getInt("ExpPerLevel."+(playergenzailevel+1))==0) {
                    playergenzaihituyounaexp = 100;
                }else {
                    playergenzaihituyounaexp = getConfig().getInt("ExpPerLevel." + playergenzailevel);
                }
                sinlevel =playergenzailevel + 1;
                level.remove(pname);
                level.put(pname,sinlevel);
                exp.remove(pname);
                exp.put(pname,sinexp);
                playergenzaiexp = sinexp;
            }
        }
    }

}
