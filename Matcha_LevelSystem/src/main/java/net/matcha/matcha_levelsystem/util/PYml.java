package net.matcha.matcha_levelsystem.util;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import net.matcha.matcha_levelsystem.Matcha_LevelSystem;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

public class PYml {
    private Matcha_LevelSystem plugin = (Matcha_LevelSystem)Matcha_LevelSystem.getPlugin(Matcha_LevelSystem.class);
    public HashMap<String, Long> sp = new HashMap();
    public HashMap<String, Double> LEVEL_MAX_HEALTH = new HashMap();
    public HashMap<String, Integer> LEVEL_HEALTH = new HashMap();
    public HashMap<String, Integer> LEVEL_DAMAGE = new HashMap();
    public HashMap<String, Integer> LEVEL_DEFENSE = new HashMap();
    public HashMap<String, Integer> LEVEL_SPEED = new HashMap();
    public HashMap<String, Integer> STATS_HEALTH = new HashMap();
    public HashMap<String, Integer> STATS_DAMAGE = new HashMap();
    public HashMap<String, Integer> STATS_DEFENSE = new HashMap();
    public HashMap<String, Integer> STATS_SPEED = new HashMap();

    public void createPlayerDeta(Player player)
    {
        if (this.sp.containsKey(player.getName())) {
            return;
        }
        File directory = new File(Bukkit.getServer().getPluginManager().getPlugin(this.plugin.getName()).getDataFolder(), File.separator + "PlayerData");
        File playerData = new File(directory, File.separator + player.getUniqueId().toString() + ".yml");

        YamlConfiguration conf = YamlConfiguration.loadConfiguration(playerData);

        String uuid = player.getUniqueId().toString();
        if (conf.get(uuid + "SP") == null)
        {
            conf.set(uuid + "SP", Integer.valueOf(1));
            conf.set(uuid + "LEVEL_MAX_HEALTH", Double.valueOf(20.0D));
            conf.set(uuid + "LEVEL_DAMAGE", Integer.valueOf(0));
            conf.set(uuid + "LEVEL_HEALTH", Integer.valueOf(0));
            conf.set(uuid + "LEVEL_SPEED", Integer.valueOf(0));
            conf.set(uuid + "LEVEL_DEFENSE", Integer.valueOf(0));
            conf.set(uuid + "STATS_DAMAGE", Integer.valueOf(0));
            conf.set(uuid + "STATS_HEALTH", Integer.valueOf(0));
            conf.set(uuid + "STATS_SPEED", Integer.valueOf(0));
            conf.set(uuid + "STATS_DEFENSE", Integer.valueOf(0));
            try
            {
                conf.save(playerData);
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
        this.sp.put(player.getName(), Long.valueOf(conf.getLong(uuid + "SP")));
        this.LEVEL_MAX_HEALTH.put(player.getName(), Double.valueOf(conf.getDouble(uuid + "LEVEL_MAX_HEALTH")));
        this.LEVEL_HEALTH.put(player.getName(), Integer.valueOf(conf.getInt(uuid + "LEVEL_HEALTH")));
        this.LEVEL_DAMAGE.put(player.getName(), Integer.valueOf(conf.getInt(uuid + "LEVEL_DAMAGE")));
        this.LEVEL_DEFENSE.put(player.getName(), Integer.valueOf(conf.getInt(uuid + "LEVEL_DEFENSE")));
        this.LEVEL_SPEED.put(player.getName(), Integer.valueOf(conf.getInt(uuid + "LEVEL_SPEED")));
        this.STATS_HEALTH.put(player.getName(), Integer.valueOf(conf.getInt(uuid + "STATS_HEALTH")));
        this.STATS_DAMAGE.put(player.getName(), Integer.valueOf(conf.getInt(uuid + "STATS_DAMAGE")));
        this.STATS_DEFENSE.put(player.getName(), Integer.valueOf(conf.getInt(uuid + "STATS_DEFENSE")));
        this.STATS_SPEED.put(player.getName(), Integer.valueOf(conf.getInt(uuid + "STATS_SPEED")));
    }

    public void savePlayerFile(Player player)
    {
        File directory = new File(Bukkit.getServer().getPluginManager().getPlugin(this.plugin.getName()).getDataFolder(), File.separator + "PlayerData");
        File playerData = new File(directory, File.separator + player.getUniqueId().toString() + ".yml");

        YamlConfiguration conf = YamlConfiguration.loadConfiguration(playerData);

        String uuid = player.getUniqueId().toString();

        conf.set(uuid + "SP", this.sp.get(player.getName()));
        conf.set(uuid + "LEVEL_MAX_HEALTH", this.LEVEL_MAX_HEALTH.get(player.getName()));
        conf.set(uuid + "LEVEL_HEALTH", this.LEVEL_HEALTH.get(player.getName()));
        conf.set(uuid + "LEVEL_DAMAGE", this.LEVEL_DAMAGE.get(player.getName()));
        conf.set(uuid + "LEVEL_DEFENSE", this.LEVEL_DEFENSE.get(player.getName()));
        conf.set(uuid + "LEVEL_SPEED", this.LEVEL_SPEED.get(player.getName()));
        conf.set(uuid + "STATS_HEALTH", this.STATS_HEALTH.get(player.getName()));
        conf.set(uuid + "STATS_DAMAGE", this.STATS_DAMAGE.get(player.getName()));
        conf.set(uuid + "STATS_DEFENSE", this.STATS_DEFENSE.get(player.getName()));
        conf.set(uuid + "STATS_SPEED", this.STATS_SPEED.get(player.getName()));
        try
        {
            conf.save(playerData);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public void savePlayerFile()
    {
        for (String name : this.sp.keySet()) {
            if (this.sp.containsKey(name))
            {
                File directory = new File(Bukkit.getServer().getPluginManager().getPlugin(this.plugin.getName()).getDataFolder(), File.separator + "PlayerData");
                File playerData = new File(directory, File.separator + this.plugin.getServer().getOfflinePlayer(name).getUniqueId().toString() + ".yml");

                YamlConfiguration conf = YamlConfiguration.loadConfiguration(playerData);

                String uuid = this.plugin.getServer().getOfflinePlayer(name).getUniqueId().toString();

                conf.set(uuid + "SP", this.sp.get(name));
                conf.set(uuid + "LEVEL_MAX_HEALTH", this.LEVEL_MAX_HEALTH.get(name));
                conf.set(uuid + "LEVEL_HEALTH", this.LEVEL_HEALTH.get(name));
                conf.set(uuid + "LEVEL_DAMAGE", this.LEVEL_DAMAGE.get(name));
                conf.set(uuid + "LEVEL_DEFENSE", this.LEVEL_DEFENSE.get(name));
                conf.set(uuid + "LEVEL_SPEED", this.LEVEL_SPEED.get(name));
                conf.set(uuid + "STATS_HEALTH", this.STATS_HEALTH.get(name));
                conf.set(uuid + "STATS_DAMAGE", this.STATS_DAMAGE.get(name));
                conf.set(uuid + "STATS_DEFENSE", this.STATS_DEFENSE.get(name));
                conf.set(uuid + "STATS_SPEED", this.STATS_SPEED.get(name));
                try
                {
                    conf.save(playerData);
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
        }
    }
}