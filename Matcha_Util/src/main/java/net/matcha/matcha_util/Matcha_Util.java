package net.matcha.matcha_util;

import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import net.minecraft.server.v1_12_R1.*;
import org.bukkit.craftbukkit.v1_12_R1.inventory.CraftItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;
import ru.beykerykt.lightapi.LightAPI;
import ru.beykerykt.lightapi.chunks.ChunkInfo;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.*;

public final class Matcha_Util extends JavaPlugin implements Listener {

    private Connection connection;
    private String host, database, username, password;
    private int port;

    public HashMap<String, org.bukkit.inventory.ItemStack> items = new HashMap();
    public HashMap<org.bukkit.inventory.ItemStack, File> itemsfile = new HashMap();

    @Override
    public void onEnable() {
        getConfig().options().copyDefaults(true);
        saveDefaultConfig();
        reloadConfig();
        FileConfiguration config = getConfig();
        getServer().getPluginManager().registerEvents(this, this);
        if(!new File(Bukkit.getServer().getPluginManager().getPlugin(Matcha_Util.getPlugin(Matcha_Util.class).getName()).getDataFolder(), File.separator + "ログイン設定.yml").isFile()) {
            File onJoinData = new File(Bukkit.getServer().getPluginManager().getPlugin(Matcha_Util.getPlugin(Matcha_Util.class).getName()).getDataFolder(), File.separator + "ログイン設定.yml");
            YamlConfiguration conf = YamlConfiguration.loadConfiguration(onJoinData);
            try
            {
                conf.save(onJoinData);
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
            List l1 = conf.getStringList("初ログイン時設定.コマンド");
            l1.add("commands1");
            l1.add("commands2");
            l1.add("commands3");
            l1.add("say hello");
            conf.set("初ログイン時設定.コマンド",l1);
            List l2 = conf.getStringList("初ログイン時設定.アイテム");
            l2.add("エクスキャリバー");
            conf.set("初ログイン時設定.アイテム", l2);
            List l3 = conf.getStringList("初ログイン時設定.メッセージ");
            l3.add("messages1");
            l3.add("messages2");
            l3.add("messages3");
            l3.add("やあ");
            conf.set("初ログイン時設定.メッセージ", l3);
            try
            {
                conf.save(onJoinData);
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }
    @EventHandler
    public void onPlayerJoinEvent(PlayerJoinEvent e){
        Player player = e.getPlayer();
        File onJoinData = new File(Bukkit.getServer().getPluginManager().getPlugin(Matcha_Util.getPlugin(Matcha_Util.class).getName()).getDataFolder(), File.separator + "ログイン設定.yml");
        YamlConfiguration conf = YamlConfiguration.loadConfiguration(onJoinData);
        List cmdlist =conf.getStringList("初ログイン時設定.コマンド");
        cmdlist.forEach(cmd -> Bukkit.getServer().dispatchCommand(getServer().getConsoleSender(),cmd.toString()));
        List msglist =conf.getStringList("初ログイン時設定.メッセージ");
        msglist.forEach(msg -> e.getPlayer().sendMessage(msg.toString()));
        List itemlist =conf.getStringList("初ログイン時設定.アイテム");
        File itemFile = new File(Bukkit.getServer().getPluginManager().getPlugin("Matcha_ItemStack").getDataFolder(), File.separator + "Items");
        if (!itemFile.exists()) {
            return;
        }
        File[] ymls = itemFile.listFiles();
        if (ymls == null) {
            return;
        }
        YamlConfiguration files;
        for (File yml : ymls){
            files = YamlConfiguration.loadConfiguration(yml);
            for (String name : files.getKeys(false))
            {
                org.bukkit.inventory.ItemStack item = createItem(files, name);
                this.items.put(name, item);
                this.itemsfile.put(item, yml);
            }
        }
        itemlist.forEach(item -> player.getInventory().addItem(new ItemStack[] { items.get(String.valueOf(item)) }));
        player.playSound(player.getLocation(), Sound.ENTITY_BAT_TAKEOFF, 1.0F, 2.0F);
    }
    private org.bukkit.inventory.ItemStack createItem(YamlConfiguration conf, String name)
    {
        boolean s = false;
        boolean e = false;
        boolean d = false;

        int data = 0;
        long damage = 0L;long weight = 0L;long Speed = 0L;long level = 0L;long Health = 0L;

        String tree = "";
        String display = "";
        String tekiseisyokugyou = "";

        int material = 268;
        String type = "ショートソード";
        if (conf.get(name + ".Item.Type") != null) {
            material = conf.getInt(name + ".Item.Type");
        }
        if (conf.get(name + ".Item.Data") != null) {
            data = conf.getInt(name + ".Item.Data");
        }
        if (conf.get(name + ".Item.DisplayName") != null) {
            display = conf.getString(name + ".Item.DisplayName");
        }
        if (conf.get(name + ".Item.TekiseiSyokugyou") != null) {
            tekiseisyokugyou = conf.getString(name + ".Item.TekiseiSyokugyou");
        }
        org.bukkit.inventory.ItemStack item = new org.bukkit.inventory.ItemStack(Material.WOOD_SWORD, 1, (short)data);
        item.setTypeId(material);

        List<String> lore = new ArrayList();
        if (conf.get(name + ".Description") != null)
        {
            d = true;
            if (conf.get(name + ".Description.one") != null)
            {
                String one = "§7§l" + conf.getString(new StringBuilder().append(name).append(".Description.one").toString());
                lore.add(one);
            }
            if (conf.get(name + ".Description.two") != null)
            {
                String two = "§7§l" + conf.getString(new StringBuilder().append(name).append(".Description.two").toString());
                lore.add(two);
            }
            if (conf.get(name + ".Description.three") != null)
            {
                tree = "§7§l" + conf.getString(new StringBuilder().append(name).append(".Description.three").toString());
                lore.add(tree);
            }
        }
        if (conf.get(name + ".Description") != null&&conf.get(name + ".Stats") != null&&conf.get(name + ".Effect") != null){
            lore.add("");
        }else if(conf.get(name + ".Description") != null&&conf.get(name + ".Stats") != null){
            lore.add("");
        }else if(conf.get(name + ".Description") != null&&conf.get(name + ".Effect") != null){
            lore.add("");
        }else if(conf.get(name + ".Effect") != null&&conf.get(name + ".Stats") != null) {
        }
        if (conf.get(name + ".Stats") != null) {
            lore.add("§eアイテムの情報：");

            s = true;
            if (conf.get(name + ".Stats.Level") != null) {
                level = conf.getInt(name + ".Stats.Level");
                lore.add("§7§lレベル: §a" + level);
            }
            if (conf.get(name + ".Stats.Type") != null) {
                type = conf.getString(name + ".Stats.Type");
                lore.add("§7§l武器タイプ: " + type);
            }
        }
        if (conf.get(name + ".Item.TekiseiSyokugyou") != null)
        {
            type = conf.getString(name + ".Item.TekiseiSyokugyou");
            lore.add("§7§l適正職業: " + type);
        }

        if (conf.get(name + ".Stats") != null) {
            if (conf.get(name + ".Stats.AttackSpeed") != null) {
                lore.add("§7§l攻撃速度: " + conf.getString(new StringBuilder().append(name).append(".Stats.AttackSpeed").toString()));
            }
            if (conf.get(name + ".Stats.Damage") != null)
            {
                damage = conf.getLong(name + ".Stats.Damage");
                lore.add("§7§lダメージ: " + damage);
            }
            if (conf.get(name + ".Stats.Defense") != null)
            {
                long defense = conf.getLong(name + ".Stats.Defense");
                lore.add("§7§l防御力: " + defense);
            }
            if (conf.get(name + ".Stats.Durability") != null)
            {
                long durability = conf.getLong(name + ".Stats.Durability");
                lore.add("§7§l耐久力: " + durability + "/" + durability);
            }
            if (conf.get(name + ".Stats.Weight") != null)
            {
                weight = conf.getLong(name + ".Stats.Weight");
                lore.add("§7§l重さ: " + weight);
            }
        }
        if (conf.get(name + ".Description") != null&&conf.get(name + ".Stats") != null&&conf.get(name + ".Effect") != null){
            lore.add("");
        }else if(conf.get(name + ".Description") != null&&conf.get(name + ".Stats") != null){
        }else if(conf.get(name + ".Description") != null&&conf.get(name + ".Effect") != null){
        }else if(conf.get(name + ".Effect") != null&&conf.get(name + ".Stats") != null) {
            lore.add("");
        }
        if (conf.get(name + ".Effect") != null)
        {
            lore.add("§e利き手に持ったとき：");

            e = true;
            if (conf.get(name + ".Effect.Strong") != null)
            {
                long Strong = conf.getLong(name + ".Effect.Strong");
                lore.add("§7§lSTR: +" + Strong);
            }
            if (conf.get(name + ".Effect.Defense") != null)
            {
                long Defense = conf.getLong(name + ".Effect.Defense");
                lore.add("§7§lDEF: +" + Defense);
            }
            if (conf.get(name + ".Effect.Speed") != null)
            {
                Speed = conf.getLong(name + ".Effect.Speed");
                lore.add("§7§lAGI: +" + Speed);
            }
            if (conf.get(name + ".Effect.Health") != null)
            {
                Health = conf.getLong(name + ".Effect.Health");
                lore.add("§7§lVIT: +" + Health);
            }
        }
        if (conf.get(name + ".Rarity") != null) {
            lore.add("§8>>§fレア度: " + conf.getString(new StringBuilder().append(name).append(".Rarity").toString()));
        }
        ItemMeta meta = item.getItemMeta();
        meta.setLore(lore);
        meta.setDisplayName(display);
        item.setItemMeta(meta);

        item = setDamage(item, (int)damage, weight / 1000.0D * -1.0D + Speed / 100.0D);

        item = setFlag(item);

        return item;
    }
    private org.bukkit.inventory.ItemStack setDamage(org.bukkit.inventory.ItemStack item, int damage, double weight)
    {
        net.minecraft.server.v1_12_R1.ItemStack nmsStack = CraftItemStack.asNMSCopy(item);
        net.minecraft.server.v1_12_R1.NBTTagCompound compound = nmsStack.hasTag() ? nmsStack.getTag() : new net.minecraft.server.v1_12_R1.NBTTagCompound();
        net.minecraft.server.v1_12_R1.NBTTagList modifiers = new NBTTagList();
        net.minecraft.server.v1_12_R1.NBTTagCompound damages = new net.minecraft.server.v1_12_R1.NBTTagCompound();
        damages.set("AttributeName", new net.minecraft.server.v1_12_R1.NBTTagString("generic.attackDamage"));
        damages.set("Name", new NBTTagString("generic.attackDamage"));
        damages.set("Amount", new NBTTagInt(damage));
        damages.set("Operation", new NBTTagInt(0));
        damages.set("UUIDLeast", new NBTTagInt(894654));
        damages.set("UUIDMost", new NBTTagInt(2872));
        damages.set("Slot", new NBTTagString("mainhand"));
        modifiers.add(damages);
        NBTTagCompound speed = new NBTTagCompound();
        speed.set("AttributeName", new NBTTagString("generic.movementSpeed"));
        speed.set("Name", new NBTTagString("generic.movementSpeed"));
        speed.set("Amount", new NBTTagDouble(weight));
        speed.set("Operation", new NBTTagInt(0));
        speed.set("UUIDLeast", new NBTTagInt(894654));
        speed.set("UUIDMost", new NBTTagInt(2872));
        modifiers.add(speed);
        assert (compound != null);
        compound.set("AttributeModifiers", modifiers);
        nmsStack.setTag(compound);
        item = CraftItemStack.asBukkitCopy(nmsStack);

        return item;
    }
    private org.bukkit.inventory.ItemStack setFlag(org.bukkit.inventory.ItemStack item)
    {
        ItemMeta meta = item.getItemMeta();

        meta.setUnbreakable(true);

        meta.addItemFlags(new ItemFlag[] { ItemFlag.HIDE_ATTRIBUTES });
        meta.addItemFlags(new ItemFlag[] { ItemFlag.HIDE_DESTROYS });
        meta.addItemFlags(new ItemFlag[] { ItemFlag.HIDE_ENCHANTS });
        meta.addItemFlags(new ItemFlag[] { ItemFlag.HIDE_PLACED_ON });
        meta.addItemFlags(new ItemFlag[] { ItemFlag.HIDE_POTION_EFFECTS });
        meta.addItemFlags(new ItemFlag[] { ItemFlag.HIDE_UNBREAKABLE });

        item.setItemMeta(meta);

        return item;
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
