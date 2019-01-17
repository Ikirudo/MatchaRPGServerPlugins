package net.matcha.matcha_itemstack;

import net.matcha.matcha_itemstack.commands.Items;
import net.matcha.matcha_itemstack.commands.TAB.ItemsTAB;
import net.matcha.matcha_itemstack.listener.player;
import net.matcha.matcha_itemstack.manager.items.ChatManager;
import net.matcha.matcha_itemstack.manager.items.MenuManager;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.Configuration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import net.matcha.matcha_itemstack.util.ItemFile;
import net.matcha.matcha_itemstack.util.Prefixs;
import net.matcha.matcha_itemstack.manager.items.CreateManager;
import net.matcha.matcha_itemstack.commands.Item;

import java.util.HashMap;
import org.bukkit.plugin.PluginManager;
import java.util.List;
import java.util.Map;

public final class Matcha_ItemStack extends JavaPlugin implements Listener {

    Map<String,Boolean > write = new HashMap<>();
    Map<String,String> args0 = new HashMap<>();

    public ChatManager chatManager;
    public CreateManager createManager;
    public Prefixs prefix;
    public ItemFile itemFile;
    public MenuManager menuManager;

    @Override
    public void onEnable() {
        getConfig().options().copyDefaults(true);
        saveDefaultConfig();
        reloadConfig();
        getServer().getPluginManager().registerEvents(this, this);


        PluginManager pm = getServer().getPluginManager();

        pm.registerEvents(new player(), this);
        pm.registerEvents(new ChatManager(),this);

        getCommand("items").setExecutor(new Items());
        getCommand("is").setExecutor(new Items());
        getCommand("item").setExecutor(new Item());
        getCommand("i").setExecutor(new Item());
        getCommand("items").setTabCompleter(new ItemsTAB());
        getCommand("it").setTabCompleter(new ItemsTAB());

        this.chatManager = new ChatManager();
        this.createManager = new CreateManager();
        this.prefix = new Prefixs();
        this.itemFile = new ItemFile();
        this.menuManager = new MenuManager();

        this.itemFile.createFile();
    }

    @Override
    public void onDisable() {
    }
    @EventHandler
    public  void onJoin(PlayerJoinEvent e){
        Player p = e.getPlayer();
        String pname = p.getName();
        write.put(pname,false);
        args0.put(pname,"taiki");
    }

    @EventHandler
    public void onInteractItemStackevent(PlayerInteractEvent e){
        Player p = e.getPlayer();
        ItemStack item = p.getItemInHand();
        String pname = p.getName();
        //WRITE
        if(write.get(pname)==true) {
            if (item != null) {
                Configuration config = getConfig();
                getConfig().set("ItemStackList."+args0.get(pname), item);
                write.remove(pname);
                write.put(pname,false);
                args0.remove(pname);
                args0.put(pname,"taiki");
                p.sendMessage(ChatColor.GREEN+"【Matcha_ItemStack】ItemStackをWriteしました。");
                saveConfig();
                return;
            }else{
                p.sendMessage(ChatColor.GREEN+"【Matcha_ItemStack】ItemStackをWrite中ですので何かしらのアイテムを手に持ち何かをクリックしてください。");
                return;
            }
        }else{
            return;
        }
    }

    public boolean onCommand(CommandSender sender, Command cmd, String cmdLine, String[] args) {
        Player p;
        if (sender instanceof Player) {
            p = (Player) sender; //That's the current player
            String pname = p.getName();
            if (cmd.getName().equalsIgnoreCase("itemstackwrite")&&sender.hasPermission("itemstack.op")) {
                if(args.length>=0) {
                    write.remove(pname);
                    write.put(pname, true);
                    args0.remove(pname);
                    args0.put(pname, args[0]);
                    p.sendMessage(ChatColor.GREEN + "【Matcha_ItemStack】ItemStackをWriteします。アイテムを手に持ち何かをクリックしてください。");
                    return true;
                }else{
                    p.sendMessage(ChatColor.GREEN+"【Matcha_ItemStack】設定するItemStack名が二番目に書かれていません。やり直してください。");
                    return false;
                }
            }else if (cmd.getName().equalsIgnoreCase("itemstackread")&&sender.hasPermission("itemstack.op")) {
                if(args.length>=0) {
                    ItemStack item = getConfig().getItemStack("ItemStackList." + args[0]);
                    p.getInventory().addItem(item);
                    return true;
                }else{
                    p.sendMessage(ChatColor.GREEN+"【Matcha_ItemStack】ItemStack名が二番目に書かれていません。やり直してください。");
                    return false;
                }
            }else if (cmd.getName().equalsIgnoreCase("itemstacklist")&&sender.hasPermission("itemstack.op")) {
                p.sendMessage(ChatColor.GREEN+"↓【Matcha_ItemStack】ItemStackList↓");
                List<String> list = getConfig().getStringList("ItemStackList");
                for (String key : getConfig().getConfigurationSection("ItemStackList").getKeys(false)) {
                    p.sendMessage(key);
                }
                for(String risuto : list){
                    p.sendMessage(risuto);
                }
                p.sendMessage(ChatColor.GREEN+"↑【Matcha_ItemStack】ItemStackList↑");
                return true;
            }
        }
        return false;
    }
}
