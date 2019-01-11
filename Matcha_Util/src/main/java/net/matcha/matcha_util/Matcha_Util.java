package net.matcha.matcha_util;

import org.bukkit.event.EventHandler;
import ru.beykerykt.lightapi.*;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import ru.beykerykt.lightapi.chunks.ChunkInfo;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public final class Matcha_Util extends JavaPlugin implements Listener {

    private Connection connection;
    private String host, database, username, password;
    private int port;

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
        // Plugin shutdown logic
    }

    public boolean onCommand(CommandSender sender, Command cmd, String cmdLine, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender; //That's the current player
        } else {
        }
        host = "localhost";
        port = 3306;
        database = "matcha_database";
        username = "root";
        password = "Ikirudo";
        if ((cmd.getName().equalsIgnoreCase("ban")||cmd.getName().equalsIgnoreCase("minecraft:ban"))&&sender.hasPermission("util.op")) {
            FileConfiguration config = getConfig();
            Player t = getServer().matchPlayer(args[0]).get(0); //That's the player which should be banned
            int kaisuu;
            if(config.get(t.getDisplayName()+"(ban).kaisuu")==null) {
                kaisuu =1;
            }else{
                kaisuu = (int)config.get(t.getDisplayName()+"(ban).kaisuu");
                kaisuu++;
            }
            if(kaisuu == 1) {
                config.set(t.getDisplayName() + "(ban)", "");
            }
            config.set(t.getDisplayName() + "(ban)"+".kaisuu", kaisuu);
            config.set(t.getDisplayName() + "(ban)" + ".banorkick"+kaisuu,"ban");
            Date bandate = new Date();
            Calendar calendar = GregorianCalendar.getInstance();
            calendar.setTime(bandate);
            int years = calendar.get(Calendar.YEAR);
            int months = calendar.get(Calendar.MONTH)+1;
            if (calendar.get(Calendar.MONTH)+1 == 13){
                months = 1;
            }
            int days = calendar.get(Calendar.DAY_OF_MONTH);
            int hours = calendar.get(Calendar.HOUR_OF_DAY);
            int minutes = calendar.get(Calendar.MINUTE);
            int seconds = calendar.get(Calendar.SECOND);
            config.set(t.getDisplayName() + "(ban)" + ".hiduke"+kaisuu, years + years + "/" +months + "/" +days + "-" + hours + ":" + minutes + ":" + seconds);
            if (sender instanceof Player){
                config.set(t.getDisplayName()+"(ban)" + ".staff"+kaisuu, ((Player) sender).getDisplayName());
            }else{
                config.set(t.getDisplayName()+"(ban)" + ".staff"+kaisuu, "ServerConsole");
            }
            if(args.length > 1) {
                config.set(t.getDisplayName()+"(ban)" + ".banreason"+kaisuu, args[1]);
                if(sender instanceof  Player) {
                    Bukkit.getBanList(BanList.Type.NAME).addBan(t.getDisplayName(), args[1], null, ((Player) sender).getDisplayName());
                }else{
                    Bukkit.getBanList(BanList.Type.NAME).addBan(t.getDisplayName(), args[1], null, "ServerConsole");
                }
                t.kickPlayer(args[1]);
            }else{
                config.set(t.getDisplayName()+"(ban)" + ".banreason"+kaisuu, "");
                if(sender instanceof  Player) {
                    Bukkit.getBanList(BanList.Type.NAME).addBan(t.getDisplayName(), "", null, ((Player) sender).getDisplayName());
                }else{
                    Bukkit.getBanList(BanList.Type.NAME).addBan(t.getDisplayName(), "", null, "ServerConsole");
                }
                t.kickPlayer("You have banned from Matcha Server");
            }
            try {
                openConnection();
                String namebanned = t.getDisplayName();
                String hiduke =  years + "/" +months + "/" +days + "-" + hours + ":" + minutes + ":" + seconds;
                String staff;
                if (sender instanceof Player){
                    staff = ((Player) sender).getDisplayName();
                }else{
                    staff = "server";
                }
                String banreason;
                if(args.length > 1) {
                    banreason = (String) args[1];
                }else{
                    banreason = "無し";
                }
                String sql = "INSERT INTO kickbanlist (Name, Times, Hiduke, Staff, BanOrKick, Reason) VALUES (?, ?, ?, ?, ?, ?);";
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setString(1, namebanned);
                preparedStatement.setString(2, Integer.toString(kaisuu));
                preparedStatement.setString(3, hiduke);
                preparedStatement.setString(4, staff);
                preparedStatement.setString(5, "ban");
                preparedStatement.setString(6, banreason);
                preparedStatement.executeUpdate();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            saveConfig();
            return true;
        }
        if ((cmd.getName().equalsIgnoreCase("kick")||cmd.getName().equalsIgnoreCase("minecraft:kick"))&&sender.hasPermission("util.op")) {
            FileConfiguration config = getConfig();
            Player t = getServer().matchPlayer(args[0]).get(0); //That's the player which should be banned
            int kaisuu;
            if(config.get(t.getDisplayName()+"(kick).kaisuu")==null) {
                kaisuu =1;
            }else{
                kaisuu = (int)config.get(t.getDisplayName()+"(kick).kaisuu");
                kaisuu++;
            }
            if(kaisuu == 1) {
                config.set(t.getDisplayName() + "(kick)", "");
            }
            config.set(t.getDisplayName() + "(kick)"+".kaisuu", kaisuu);
            config.set(t.getDisplayName() + "(kick)" + ".banorkick"+kaisuu, "kick");
            Date kickdate = new Date();
            Calendar calendar = GregorianCalendar.getInstance();
            calendar.setTime(kickdate);
            int years = calendar.get(Calendar.YEAR);
            int months = calendar.get(Calendar.MONTH)+1;
            if (calendar.get(Calendar.MONTH)+1 == 13){
                months = 1;
            }
            int days = calendar.get(Calendar.DAY_OF_MONTH);
            int hours = calendar.get(Calendar.HOUR_OF_DAY);
            int minutes = calendar.get(Calendar.MINUTE);
            int seconds = calendar.get(Calendar.SECOND);
            config.set(t.getDisplayName() + "(kick)" + ".hiduke"+kaisuu, years + "/" +months + "/" +days + "-" + hours + ":" + minutes + ":" + seconds);
            if (sender instanceof Player){
                config.set(t.getDisplayName()+"(kick)" + ".staff"+kaisuu, ((Player) sender).getDisplayName());
            }else{
                config.set(t.getDisplayName()+"(kick)" + ".staff"+kaisuu, "ServerConsole");
            }
            if(args.length > 1) {
                config.set(t.getDisplayName()+"(kick)" + ".kickreason"+kaisuu, args[1]);
                t.kickPlayer(args[1]);
            }else{
                config.set(t.getDisplayName()+"(kick)" + ".kickreason"+kaisuu, "");
                t.kickPlayer("You have kicked from Matcha Server");
            }
            try {
                openConnection();
                String namekicked = t.getDisplayName();
                String hiduke =  years + "/" +months + "/" +days + "-" + hours + ":" + minutes + ":" + seconds;
                String staff;
                if (sender instanceof Player){
                    staff = ((Player) sender).getDisplayName();
                }else{
                    staff = "server";
                }
                String kickreason;
                if(args.length > 1) {
                    kickreason = (String) args[1];
                }else{
                    kickreason = "無し";
                }
                String sql = "INSERT INTO kickbanlist (Name, Times, Hiduke, Staff, BanOrKick, Reason) VALUES (?, ?, ?, ?, ?, ?);";
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setString(1, namekicked);
                preparedStatement.setString(2, Integer.toString(kaisuu));
                preparedStatement.setString(3, hiduke);
                preparedStatement.setString(4, staff);
                preparedStatement.setString(5, "kick");
                preparedStatement.setString(6, kickreason);
                preparedStatement.executeUpdate();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            saveConfig();
            return true;
        }
        if(args.length != 0) {
            if (cmd.getName().equalsIgnoreCase("bancheck")&&sender.hasPermission("util.op")) {
                FileConfiguration config = getConfig();
                sender.sendMessage("ID:"+args[0]);
                Player t = getServer().matchPlayer(args[0]).get(0);
                if(config.get(t.getDisplayName()+"(ban).kaisuu")==null) {
                    sender.sendMessage("このプレイヤーはいままでBANされたことがありません。");
                }else {
                    sender.sendMessage("これまでのBAN回数：" + config.get(args[0] + "(ban).kaisuu"));
                    int kaisuu = (int) config.get(args[0] + "(ban).kaisuu");
                    for (int i = 0; i < kaisuu; i++) {
                        String str = String.valueOf(kaisuu);
                        sender.sendMessage(str + "回目BAN日時" + config.get(args[0] + "(ban).hiduke" + str));
                        sender.sendMessage(str + "回目BAN執行者" + config.get(args[0] + "(ban).staff" + str));
                        sender.sendMessage(str + "回目BAN理由" + config.get(args[0] + "(ban).banreason" + str));
                    }
                }
                return true;
            }
            if (cmd.getName().equalsIgnoreCase("kickcheck")&&sender.hasPermission("util.op")) {
                FileConfiguration config = getConfig();
                sender.sendMessage("ID:"+args[0]);
                Player t = getServer().matchPlayer(args[0]).get(0);
                if(config.get(t.getDisplayName()+"(kick).kaisuu")==null) {
                    sender.sendMessage("このプレイヤーはいままでKICKされたことがありません。");
                }else{
                    sender.sendMessage("これまでのKICK回数："+config.get(args[0]+"(kick).kaisuu"));
                    int kaisuu = (int)config.get(args[0]+"(kick).kaisuu");
                    for(int i =0; i<kaisuu; i++) {
                        String str = String.valueOf(kaisuu);
                        sender.sendMessage(str+"回目KICK日時" + config.get(args[0] + "(kick).hiduke"+str));
                        sender.sendMessage(str+"回目KICK執行者" + config.get(args[0] + "(kick).staff"+str));
                        sender.sendMessage(str+"回目KICK理由" + config.get(args[0] + "(kick).kickreason"+str));
                    }
                }
                return true;
            }
            return true;
        }
        return false;
    }
    @EventHandler
    public void onPlaceKougenEvent(BlockPlaceEvent event){
        Player p = event.getPlayer();
        Block block = event.getBlockPlaced();
        Location loc = block.getLocation();
        if (block.getType().equals(Material.GLOWSTONE)&&p.isSneaking()){
            event.setCancelled(true);
            LightAPI.createLight(loc,15,true);
            for (ChunkInfo info : LightAPI.collectChunks(loc)) {
                LightAPI.updateChunk(info);
            }
        }
    }
    private void openConnection() throws SQLException, ClassNotFoundException {
        if (connection != null && !connection.isClosed()) {
            return;
        }

        synchronized (this) {
            if (connection != null && !connection.isClosed()) {
                return;
            }
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://" + this.host + ":" + this.port + "/" + this.database+"?autoReconnect=true&useSSL=false", this.username, this.password);
            Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "MYSQL CONNECTED");
        }
    }
}
