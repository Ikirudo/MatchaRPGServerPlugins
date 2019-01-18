package net.matcha.matcha_levelsystem;

import com.connorlinfoot.actionbarapi.ActionBarAPI;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.attribute.Attribute;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerExpChangeEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;

import net.matcha.matcha_levelsystem.commands.ResetSP;
import net.matcha.matcha_levelsystem.commands.SP;
import net.matcha.matcha_levelsystem.commands.SetSP;
import net.matcha.matcha_levelsystem.commands.Stats;

import net.matcha.matcha_levelsystem.manager.Level;
import net.matcha.matcha_levelsystem.manager.Stat;
import net.matcha.matcha_levelsystem.manager.player.ScoreBoard;
import net.matcha.matcha_levelsystem.manager.player.StatusMenu;
import net.matcha.matcha_levelsystem.util.PYml;
import net.matcha.matcha_levelsystem.util.Prefixs;

import org.bukkit.plugin.PluginManager;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.File;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public final class Matcha_LevelSystem extends JavaPlugin implements Listener {

    Map<String,Integer > level = new HashMap<>();
    Map<String,Integer > exp = new HashMap<>();
    Map<String,Integer > str = new HashMap<>();
    Map<String,Integer > vit = new HashMap<>();
    Map<String,Integer > agi = new HashMap<>();
    Map<String,Integer > def = new HashMap<>();
    Map<String,Double> armormap = new HashMap<>();

    public HashMap<Player, Integer> OffHand = new HashMap();
    public Level levelingManager;
    public Stat statsManager;
    public Prefixs prefix;
    public ScoreBoard scoreBoardManager;
    public StatusMenu statusMenu;
    public PYml playerYml;


    @Override
    public void onEnable() {
        getConfig().options().copyDefaults(false);
        saveDefaultConfig();
        reloadConfig();
        FileConfiguration config = getConfig();
        getServer().getPluginManager().registerEvents(this, this);
        if(Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI")){
            new OurCoolPlaceholders(this).hook();
        }

        PluginManager pm = getServer().getPluginManager();

        getCommand("resetsp").setExecutor(new ResetSP());
        getCommand("setsp").setExecutor(new SetSP());

        getCommand("sp").setExecutor(new SP());
        getCommand("stats").setExecutor(new Stats());
        getCommand("status").setExecutor(new Stats());

        this.levelingManager = new Level();
        this.statsManager = new Stat();
        this.prefix = new Prefixs();
        this.scoreBoardManager = new ScoreBoard();
        this.statusMenu = new StatusMenu();
        this.playerYml = new PYml();

        for(Player p : this.getServer().getOnlinePlayers()) {
            this.playerYml.createPlayerDeta(p);
            this.statsManager.setStats(p);
            this.scoreBoardManager.sendScoreBoard(p);
            this.scoreBoardManager.SetName(p);
            setActionBar(p);
            String pname = p.getDisplayName();
            int playerlevel = getConfig().getInt("Player."+pname+".Level");
            if (playerlevel ==0){
                level.put(pname, 1);
            }else {
                level.put(pname, playerlevel);
            }
            int playerexp = getConfig().getInt("Player."+pname+".Exp");
            exp.put(pname,playerexp);
            str.remove(pname);
            vit.remove(pname);
            agi.remove(pname);
            def.remove(pname);
            File directory = new File(Bukkit.getServer().getPluginManager().getPlugin(this.getName()).getDataFolder(), File.separator + "PlayerData");
            File playerData = new File(directory, File.separator + p.getUniqueId().toString() + ".yml");
            YamlConfiguration conf = YamlConfiguration.loadConfiguration(playerData);
            String uuid = p.getUniqueId().toString();
            str.put(pname,conf.getInt(uuid + "STATS_DAMAGE"));
            vit.put(pname,conf.getInt(uuid + "STATS_HEALTH"));
            agi.put(pname,conf.getInt(uuid + "STATS_SPEED"));
            def.put(pname,conf.getInt(uuid + "STATS_DEFENSE"));
        }

    }
    @Override
    public void onDisable() {
        for (Player p : getServer().getOnlinePlayers())
        {
            p.getAttribute(Attribute.GENERIC_ARMOR).setBaseValue(armormap.get(p.getDisplayName()));
            armormap.remove(p.getDisplayName());
            this.playerYml.savePlayerFile(p);
        }
    }
    @EventHandler
    public void OnPlayerExpChange(PlayerExpChangeEvent e){
    }
    @EventHandler
    public void onplayerjoinevent(PlayerJoinEvent e){
        Player p = e.getPlayer();
        String pname = p.getDisplayName();
        p.setWalkSpeed((float)0.2000000298023224);
        armormap.remove(pname);
        armormap.put(pname,p.getAttribute(Attribute.GENERIC_ARMOR).getValue());
        int playerlevel = getConfig().getInt("Player."+pname+".Level");
        if (playerlevel ==0){
            level.put(pname, 1);
            playerlevel = 1;
            getConfig().set("Player."+pname+".Level",1);
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
        //createScoreboard(p);
        for (Player onp : getServer().getOnlinePlayers())
        {
            this.playerYml.createPlayerDeta(onp);
            this.statsManager.setStats(onp);
            this.scoreBoardManager.sendScoreBoard(onp);
            this.scoreBoardManager.SetName(onp);
            setActionBar(onp);
        }
        str.remove(pname);
        vit.remove(pname);
        agi.remove(pname);
        def.remove(pname);
        File directory = new File(Bukkit.getServer().getPluginManager().getPlugin(this.getName()).getDataFolder(), File.separator + "PlayerData");
        File playerData = new File(directory, File.separator + p.getUniqueId().toString() + ".yml");
        YamlConfiguration conf = YamlConfiguration.loadConfiguration(playerData);
        String uuid = p.getUniqueId().toString();
        str.put(pname,conf.getInt(uuid + "STATS_DAMAGE"));
        vit.put(pname,conf.getInt(uuid + "STATS_HEALTH"));
        agi.put(pname,conf.getInt(uuid + "STATS_SPEED"));
        def.put(pname,conf.getInt(uuid + "STATS_DEFENSE"));
        Float speedbairitu = (float)getConfig().getDouble("StatusBairitu.AGI");
        BigDecimal sp1 =BigDecimal.valueOf(speedbairitu);
        BigDecimal sp2 =BigDecimal.valueOf(agi.get(pname));
        BigDecimal sp3 = BigDecimal.valueOf(p.getWalkSpeed());
        BigDecimal spanswer =sp1.multiply(sp2).add(sp3);
        Float a = spanswer.floatValue();
        p.setWalkSpeed(a);
        double armor = armormap.get(pname);
        int armorbairitu = getConfig().getInt("StatusBairitu.DEF");
        BigDecimal val1 =BigDecimal.valueOf(armor);
        BigDecimal val2 =BigDecimal.valueOf(armorbairitu);
        BigDecimal val3 =BigDecimal.valueOf(def.get(pname));
        BigDecimal bigDecimalAnsqwer = val1.add(val2.multiply(val3));
        double d = bigDecimalAnsqwer.doubleValue();
        p.getAttribute(Attribute.GENERIC_ARMOR).setBaseValue(d);
        int HPbairitu = getConfig().getInt("StatusBairitu.VIT");
        p.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(HPbairitu*vit.get(pname)+20);
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
        p.getAttribute(Attribute.GENERIC_ARMOR).setBaseValue(armormap.get(pname));
        armormap.remove(pname);
        this.playerYml.savePlayerFile(p);
    }
    public void explevelchange(EntityType mobsyurui,int playergenzaiexp,int playergenzaihituyounaexp,int playergenzailevel,String pname,Player p){
        int sinexp=0;
        int sinlevel=1;
        int mobexp = getConfig().getInt("MobToExp."+mobsyurui);
        if(playergenzaiexp + mobexp < playergenzaihituyounaexp) {
            sinexp = playergenzaiexp + mobexp;
            exp.put(pname,sinexp);
        }else if(playergenzaiexp + mobexp == playergenzaihituyounaexp){
            sinexp = playergenzaiexp + mobexp;
            exp.put(pname,sinexp);
        }else {
            for (playergenzailevel = level.get(pname); playergenzaiexp + mobexp > playergenzaihituyounaexp; playergenzailevel++) {
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
                statsManager.setSP(p ,(Long)this.playerYml.sp.get(p.getName())+2);
                p.sendMessage(this.prefix.getErrorPrefix+"§b§lレベルアップ！ ステータスポイント +2");
            }
            if(getConfig().getBoolean("LevelUp.Player")) {
                p.sendMessage((getConfig().getString("LevelUp.PlayerMessage"))
                        .replaceAll("<playername>", pname)
                        .replaceAll("<playerlevel>", Integer.toString(sinlevel))
                        .replaceAll("<playerexp>", Integer.toString(sinexp))
                        .replaceAll("<playergenzaihituyounaexp>", Integer.toString(playergenzaihituyounaexp))
                        .replaceAll("<playernokoriexp>", Integer.toString(playergenzaihituyounaexp - sinexp))
                        .replaceAll(";", ":"));
            }
            if(getConfig().getBoolean("LevelUp.TheOthers")) {
                for (Player list : Bukkit.getOnlinePlayers()) {
                    if (list.getDisplayName() != pname) {
                        list.sendMessage((getConfig().getString("LevelUp.TheOthersMessage"))
                                .replaceAll("<playername>", pname)
                                .replaceAll("<playerlevel>", Integer.toString(sinlevel))
                                .replaceAll("<playerexp>", Integer.toString(sinexp))
                                .replaceAll("<playergenzaihituyounaexp>", Integer.toString(playergenzaihituyounaexp))
                                .replaceAll("<playernokoriexp>", Integer.toString(playergenzaihituyounaexp - sinexp))
                                .replaceAll(";", ":"));
                    }
                }
            }
        }
    }
    public boolean onCommand(CommandSender sender, Command cmd, String cmdLine, String[] args) {
            if (cmd.getName().equalsIgnoreCase("expset")&&sender.hasPermission("levelsystem.op")) {
                if(args.length != 0) {
                    String pname = args[0];
                    int maxlevel = getConfig().getInt("MaxLevel");
                    if(level.get(pname)==maxlevel) {
                        int sonolevelnogenzaihituyounaexp;
                        if (getConfig().getInt("ExpPerLevel." + maxlevel) == 0) {
                            sonolevelnogenzaihituyounaexp = 100;
                        } else {
                            sonolevelnogenzaihituyounaexp = getConfig().getInt("ExpPerLevel." + maxlevel);
                        }

                        if (Integer.parseInt(args[1]) < sonolevelnogenzaihituyounaexp) {
                            //コマンドで書かれたexp数値が最大レベルの必要EXP以下なら
                            int expput = Integer.parseInt(args[1]);
                            exp.remove(pname);
                            exp.put(pname, expput);
                            getConfig().set("Player." + pname + ".Exp", exp.get(pname));
                            saveConfig();
                            int playerlevel = getConfig().getInt("Player." + pname + ".Level");
                            int playerexp = getConfig().getInt("Player." + pname + ".Exp");
                            int playergenzaihituyounaexp;
                            if (getConfig().getInt("ExpPerLevel." + playerlevel) == 0) {
                                playergenzaihituyounaexp = 100;
                            } else {
                                playergenzaihituyounaexp = getConfig().getInt("ExpPerLevel." + playerlevel);
                            }
                            if (sender instanceof Player) {
                                Player p = (Player) sender;
                                //createScoreboard(p);
                            }
                            sender.sendMessage(ChatColor.GOLD + "【Matcha_LevelSystem】" + pname + "のEXPが" + expput + "/" + playergenzaihituyounaexp + "に設定されました。");
                            return true;
                        }else if(Integer.parseInt(args[1])==sonolevelnogenzaihituyounaexp){
                            //コマンドで書かれたexp数値が最大レベルの必要EXPなら
                            int expput = Integer.parseInt(args[1]);
                            exp.remove(pname);
                            exp.put(pname, expput);
                            getConfig().set("Player." + pname + ".Exp", exp.get(pname));
                            saveConfig();
                            int playerlevel = getConfig().getInt("Player." + pname + ".Level");
                            int playerexp = getConfig().getInt("Player." + pname + ".Exp");
                            int playergenzaihituyounaexp;
                            if (getConfig().getInt("ExpPerLevel." + playerlevel) == 0) {
                                playergenzaihituyounaexp = 100;
                            } else {
                                playergenzaihituyounaexp = getConfig().getInt("ExpPerLevel." + playerlevel);
                            }
                            if (sender instanceof Player) {
                                Player p = (Player) sender;
                                //createScoreboard(p);
                            }
                            sender.sendMessage(ChatColor.GOLD + "【Matcha_LevelSystem】" + pname + "のEXPが" + expput + "/" + playergenzaihituyounaexp + "に設定されました。");
                            sender.sendMessage(ChatColor.GOLD+"【Matcha_LevelSystem】"+pname + "のレベルが現環境の最大レベル且つ最大EXPに設定されました。ご注意ください。");
                            return true;
                        }else{
                            //コマンドで書かれたexp数値が最大レベルの必要EXPより大きく設定されたなら
                            sender.sendMessage(ChatColor.GOLD+"【Matcha_LevelSystem】"+pname + "のレベルが現環境の最大レベルになっているのでExpは最大以下にしてください。");
                            return true;
                        }
                    }else if(level.get(pname)>maxlevel){
                        sender.sendMessage(ChatColor.GOLD+"【Matcha_LevelSystem】"+pname+"のレベルが現環境の最大レベルを上回る数値にされているので"+maxlevel+"Lv以下に設定してください。");
                    }else{
                        int expput = Integer.parseInt(args[1]);
                        exp.remove(pname);
                        exp.put(pname, expput);
                        getConfig().set("Player." + pname + ".Exp", exp.get(pname));
                        saveConfig();
                        int playerlevel = getConfig().getInt("Player."+pname+".Level");
                        int playerexp = getConfig().getInt("Player."+pname+".Exp");
                        int playergenzaihituyounaexp;
                        if(getConfig().getInt("ExpPerLevel."+playerlevel)==0) {
                            playergenzaihituyounaexp = 100;
                        }else {
                            playergenzaihituyounaexp = getConfig().getInt("ExpPerLevel." + playerlevel);
                        }
                        sender.sendMessage(ChatColor.GOLD+"【Matcha_LevelSystem】"+pname+"のEXPが"+expput+"/"+ playergenzaihituyounaexp +"に設定されました。");
                        return true;
                    }
                }return false;
            }else if(cmd.getName().equalsIgnoreCase("levelset")&&sender.hasPermission("levelsystem.op")) {
                if(args.length != 0) {
                    if(Integer.parseInt(args[1])<getConfig().getInt("MaxLevel")||Integer.parseInt(args[1])==getConfig().getInt("MaxLevel")) {
                        String pname = args[0];
                        int levelput = Integer.parseInt(args[1]);
                        level.remove(pname);
                        level.put(pname, levelput);
                        getConfig().set("Player." + pname + ".Level", level.get(pname));
                        saveConfig();
                        if (sender instanceof Player) {
                            Player p = (Player) sender;
                            int playerlevel = getConfig().getInt("Player." + pname + ".Level");
                            int playerexp = getConfig().getInt("Player." + pname + ".Exp");
                            int playergenzaihituyounaexp;
                            if (getConfig().getInt("ExpPerLevel." + playerlevel) == 0) {
                                playergenzaihituyounaexp = 100;
                            } else {
                                playergenzaihituyounaexp = getConfig().getInt("ExpPerLevel." + playerlevel);
                            }
                            //createScoreboard(p);
                        }
                        sender.sendMessage(ChatColor.GOLD+"【Matcha_LevelSystem】"+pname+"のLEVELが"+levelput+"に設定されました。");
                        if(Integer.parseInt(args[1])==getConfig().getInt("MaxLevel")){
                            int maxlevel = getConfig().getInt("MaxLevel");
                            sender.sendMessage(ChatColor.GOLD+"【Matcha_LevelSystem】"+pname+"のLEVELが現環境の最大レベル"+maxlevel+"に設定されました。");
                            int sonolevelnogenzaihituyounaexp;
                            if (getConfig().getInt("ExpPerLevel." + maxlevel) == 0) {
                                sonolevelnogenzaihituyounaexp = 100;
                            } else {
                                sonolevelnogenzaihituyounaexp = getConfig().getInt("ExpPerLevel." + maxlevel);
                            }
                            if(exp.get(pname)==sonolevelnogenzaihituyounaexp){
                                sender.sendMessage(ChatColor.GOLD+"【Matcha_LevelSystem】"+pname + "のレベルが現環境の最大レベル且つ最大EXPに設定されました。ご注意ください。");
                            }
                        }
                        return true;
                    }else{
                        int maxlevel = getConfig().getInt("MaxLevel");
                        sender.sendMessage(ChatColor.GOLD+"【Matcha_LevelSystem】"+"現環境の最大レベルを上回る数値はsetできません。");
                        sender.sendMessage(ChatColor.GOLD+"【Matcha_LevelSystem】"+maxlevel+"Lv以下に設定してください。");
                        return true;
                    }
                }return false;
            }else if(cmd.getName().equalsIgnoreCase("levelsave")&&sender.hasPermission("levelsystem.op")) {
                for(Player p : this.getServer().getOnlinePlayers()) {
                    String pname = p.getDisplayName();
                    getConfig().set("Player."+pname+".Level",level.get(pname));
                    getConfig().set("Player."+pname+".Exp",exp.get(pname));
                    saveConfig();
                }return true;
            }else if(cmd.getName().equalsIgnoreCase("levelreload")&&sender.hasPermission("levelsystem.op")) {
                saveDefaultConfig();
                reloadConfig();
                for(Player p : this.getServer().getOnlinePlayers()) {
                    String pname = p.getDisplayName();
                    int playerlevel = getConfig().getInt("Player."+pname+".Level");
                    int playerexp = getConfig().getInt("Player."+pname+".Exp");
                    int playergenzaihituyounaexp;
                    if(getConfig().getInt("ExpPerLevel."+playerlevel)==0) {
                        playergenzaihituyounaexp = 100;
                    }else {
                        playergenzaihituyounaexp = getConfig().getInt("ExpPerLevel." + playerlevel);
                    }
                    //createScoreboard(p);
                }return true;
            }return false;
    }

    public void setActionBar(final Player player)
    {
        new BukkitRunnable()
        {
            Player p = player;

            public void run()
            {
                if (this.p == null)
                {
                    cancel();
                    return;
                }
                if (!this.p.isOnline())
                {
                    cancel();
                    return;
                }
                String pname = p.getDisplayName();
                int playerlevel = getConfig().getInt("Player."+pname+".Level");
                int playerexp = getConfig().getInt("Player."+pname+".Exp");
                int playergenzaihituyounaexp;
                if(getConfig().getInt("ExpPerLevel."+playerlevel)==0) {
                    playergenzaihituyounaexp = 100;
                }else {
                    playergenzaihituyounaexp = getConfig().getInt("ExpPerLevel." + playerlevel);
                }
                ActionBarAPI.sendActionBar(p,getConfig().getString("ActionBar")
                        .replaceAll("<playername>", pname)
                        .replaceAll("<playergetname>",p.getName())
                        .replaceAll("<playerlevel>", Integer.toString(playerlevel))
                        .replaceAll("<playerexp>", Integer.toString(playerexp))
                        .replaceAll("<playergenzaihituyounaexp>", Integer.toString(playergenzaihituyounaexp))
                        .replaceAll("<playernokoriexp>", Integer.toString(playergenzaihituyounaexp-playerexp))
                        .replaceAll(";", ":"));
            }
        }.runTaskTimer(this, 0L, 40L);
    }
    @EventHandler(priority = EventPriority.HIGHEST)
    public void entitydeathevent(EntityDeathEvent e){
        try {
            if(e.getEntity().getKiller().getType() == EntityType.PLAYER&&e.getEntity().getKiller().getType() != null&&e.getEntity().getKiller() != null) {
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
                    int maxlevel = getConfig().getInt("MaxLevel");
                    if((level.get(pname)==maxlevel||level.get(pname)>maxlevel)&&(exp.get(pname)==playergenzaihituyounaexp||exp.get(pname)>playergenzaihituyounaexp)){
                        return;
                    }else{
                        explevelchange(mobsyurui, playergenzaiexp, playergenzaihituyounaexp, playergenzailevel, pname ,p);
                    }
                } else {
                    p.sendMessage(ChatColor.GOLD+"【Matcha_LevelSystem】"+"おかしいぞこのentity(Ikirudoに報告ください)");
                    p.sendMessage(ChatColor.GOLD+"【Matcha_LevelSystem】"+"mobの種類を表示test:" + mobsyurui);
                    return;
                }
                int playerlevel = level.get(pname);
                int playerexp = exp.get(pname);
                if (getConfig().getInt("ExpPerLevel." + playerlevel) == 0) {
                    playergenzaihituyounaexp = 100;
                } else {
                    playergenzaihituyounaexp = getConfig().getInt("ExpPerLevel." + playerlevel);
                }
                ActionBarAPI.sendActionBar(p,getConfig().getString("ActionBar")
                                .replaceAll("<playername>", pname)
                                .replaceAll("<playerlevel>", Integer.toString(playerlevel))
                                .replaceAll("<playerexp>", Integer.toString(playerexp))
                                .replaceAll("<playergenzaihituyounaexp>", Integer.toString(playergenzaihituyounaexp))
                                .replaceAll("<playernokoriexp>", Integer.toString(playergenzaihituyounaexp-playerexp))
                                .replaceAll(";", ":")
                        ,99);
                getConfig().set("Player." + pname + ".Level", level.get(pname));
                getConfig().set("Player." + pname + ".Exp", exp.get(pname));
                saveConfig();
                //createScoreboard(p);
                saveConfig();
            }
        } catch (NullPointerException event) {
        }
    }
    @EventHandler
    public void onstrenghevent(EntityDamageByEntityEvent e){
        Entity p = e.getDamager();
        String pname = p.getName();
        int purasubairitu = getConfig().getInt("StatusBairitu.STR");
        e.setDamage(e.getDamage()+(str.get(pname)*purasubairitu));
    }
    @EventHandler
    public void onClicked(InventoryClickEvent e) {
        Player player = (Player) e.getWhoClicked();
        String pname = player.getDisplayName();
        if (e.getSlotType().equals(InventoryType.SlotType.ARMOR)) {
            armormap.remove(pname);
            armormap.put(pname,player.getAttribute(Attribute.GENERIC_ARMOR).getValue());
            double armor = armormap.get(pname);
            int armorbairitu = getConfig().getInt("StatusBairitu.DEF");
            BigDecimal val1 =BigDecimal.valueOf(armor);
            BigDecimal val2 =BigDecimal.valueOf(armorbairitu);
            BigDecimal val3 =BigDecimal.valueOf(def.get(pname));
            BigDecimal bigDecimalAnsqwer = val1.add(val2.multiply(val3));
            double d = bigDecimalAnsqwer.doubleValue();
            player.getAttribute(Attribute.GENERIC_ARMOR).setBaseValue(d);
        }
        if (e.getInventory().getName().equalsIgnoreCase("§a§lステータスポイント割り振り")) {
            e.setCancelled(true);
            String dName =null;
            if (e.getCurrentItem().hasItemMeta()) {
                 dName = e.getCurrentItem().getItemMeta().getDisplayName();
            }else{
                player.sendMessage(this.prefix.getPrefix +"割り振るステータスのアイコンをクリックしてください");
                player.closeInventory();
                return;
            }
            if (dName.startsWith("§4§lSTR§7(現在: ")) {
                if (this.statsManager.getSP(player).longValue() <= 0L) {
                    player.sendMessage(this.prefix.getErrorPrefix + "SPが足りません。");
                    player.closeInventory();
                    return;
                }
                this.statsManager.setSP(player, this.statsManager.getSP(player).longValue() - 1L);
                this.statsManager.setStatsDamage(player, this.statsManager.getStatsDamage(player) + 1);
                this.playerYml.savePlayerFile(player);
                player.sendMessage(this.prefix.getPrefix + "§4§lSTR§fが1上昇しました。");
                this.statsManager.setStats(player);
                this.statusMenu.open(player);
                String uuid = player.getUniqueId().toString();
                File directory = new File(Bukkit.getServer().getPluginManager().getPlugin(this.getName()).getDataFolder(), File.separator + "PlayerData");
                File playerData = new File(directory, File.separator + player.getUniqueId().toString() + ".yml");
                YamlConfiguration conf = YamlConfiguration.loadConfiguration(playerData);
                str.remove(pname);
                str.put(pname,conf.getInt(uuid + "STATS_DAMAGE"));
                return;
            }else if (dName.startsWith("§a§lVIT§7(現在: ")) {
                if (this.statsManager.getSP(player).longValue() <= 0L) {
                    player.sendMessage(this.prefix.getErrorPrefix + "SPが足りません。");
                    player.closeInventory();
                    return;
                }
                this.statsManager.setSP(player, this.statsManager.getSP(player).longValue() - 1L);
                this.statsManager.setStatsHealth(player, this.statsManager.getStatsHealth(player) + 1);
                this.playerYml.savePlayerFile(player);
                player.sendMessage(this.prefix.getPrefix + "§c§lVIT§fが1上昇しました。");
                this.statsManager.setStats(player);
                this.statusMenu.open(player);
                String uuid = player.getUniqueId().toString();
                File directory = new File(Bukkit.getServer().getPluginManager().getPlugin(this.getName()).getDataFolder(), File.separator + "PlayerData");
                File playerData = new File(directory, File.separator + player.getUniqueId().toString() + ".yml");
                YamlConfiguration conf = YamlConfiguration.loadConfiguration(playerData);
                vit.remove(pname);
                vit.put(pname,conf.getInt(uuid + "STATS_HEALTH"));
                int HPbairitu = getConfig().getInt("StatusBairitu.VIT");
                player.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(HPbairitu*vit.get(pname)+20);
                return;
            }else if (dName.startsWith("§b§lAGI§7(現在: ")) {
                if (this.statsManager.getSP(player).longValue() <= 0L) {
                    player.sendMessage(this.prefix.getErrorPrefix + "SPが足りません。");
                    player.closeInventory();
                    return;
                }
                this.statsManager.setSP(player, this.statsManager.getSP(player).longValue() - 1L);
                this.statsManager.setStatsSpeed(player, this.statsManager.getStatsSpeed(player) + 1);
                this.playerYml.savePlayerFile(player);
                player.sendMessage(this.prefix.getPrefix + "§b§lAGI§fが1上昇しました。");
                this.statsManager.setStats(player);
                this.statusMenu.open(player);
                player.setWalkSpeed((float)0.2000000298023224);
                String uuid = player.getUniqueId().toString();
                File directory = new File(Bukkit.getServer().getPluginManager().getPlugin(this.getName()).getDataFolder(), File.separator + "PlayerData");
                File playerData = new File(directory, File.separator + player.getUniqueId().toString() + ".yml");
                YamlConfiguration conf = YamlConfiguration.loadConfiguration(playerData);
                agi.remove(pname);
                agi.put(pname,conf.getInt(uuid + "STATS_SPEED"));
                Float speedbairitu = (float)getConfig().getDouble("StatusBairitu.AGI");
                BigDecimal sp1 =BigDecimal.valueOf(speedbairitu);
                BigDecimal sp2 =BigDecimal.valueOf(agi.get(pname));
                BigDecimal sp3 = BigDecimal.valueOf(player.getWalkSpeed());
                BigDecimal spanswer =sp1.multiply(sp2).add(sp3);
                Float a = spanswer.floatValue();
                player.setWalkSpeed(a);
            }else if (dName.startsWith("§9§lDEF§7(現在: ")) {
                if (this.statsManager.getSP(player).longValue() <= 0L) {
                    player.sendMessage(this.prefix.getErrorPrefix + "SPが足りません。");
                    player.closeInventory();
                    return;
                }
                this.statsManager.setSP(player, this.statsManager.getSP(player).longValue() - 1L);
                this.statsManager.setStatsDefense(player, this.statsManager.getStatsDefense(player) + 1);
                this.playerYml.savePlayerFile(player);
                player.sendMessage(this.prefix.getPrefix + "§9§lDEF§fが1上昇しました。");
                this.statusMenu.open(player);
                String uuid = player.getUniqueId().toString();
                File directory = new File(Bukkit.getServer().getPluginManager().getPlugin(this.getName()).getDataFolder(), File.separator + "PlayerData");
                File playerData = new File(directory, File.separator + player.getUniqueId().toString() + ".yml");
                YamlConfiguration conf = YamlConfiguration.loadConfiguration(playerData);
                def.remove(pname);
                def.put(pname,conf.getInt(uuid + "STATS_DEFENSE"));
                double armor = armormap.get(pname);
                player.sendMessage("armor:"+armor);
                int armorbairitu = getConfig().getInt("StatusBairitu.DEF");
                BigDecimal val1 =BigDecimal.valueOf(armor);
                BigDecimal val2 =BigDecimal.valueOf(armorbairitu);
                BigDecimal val3 =BigDecimal.valueOf(def.get(pname));
                BigDecimal bigDecimalAnsqwer = val1.add(val2.multiply(val3));
                double d = bigDecimalAnsqwer.doubleValue();
                player.getAttribute(Attribute.GENERIC_ARMOR).setBaseValue(d);
                player.sendMessage("armor:"+d);
            }else{
                player.sendMessage(this.prefix.getPrefix +"割り振るステータスのアイコンをクリックしてください");
                player.closeInventory();
            }
        }
    }
}
