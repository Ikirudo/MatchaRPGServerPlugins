package net.matcha.matcha_itemstack.manager.items;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import net.matcha.matcha_itemstack.Matcha_ItemStack;
import net.matcha.matcha_itemstack.util.ItemFile;
import net.matcha.matcha_itemstack.util.Prefixs;
import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;

public class CreateManager
{
    private List<String> names1 = new ArrayList();
    private List<String> rarelist = new ArrayList();
    private List<String> typelist = new ArrayList();
    private List<String> speedlist = new ArrayList();
    public HashMap<Player, String> playersetting = new HashMap();
    private Matcha_ItemStack plugin = (Matcha_ItemStack)Matcha_ItemStack.getPlugin(Matcha_ItemStack.class);    public HashMap<Player, String> playertool = new HashMap();
    public HashMap<String, File> setfile = new HashMap();
    public HashMap<String, Integer> material = new HashMap();
    public HashMap<String, Integer> data = new HashMap();
    public HashMap<String, String> display = new HashMap();
    public HashMap<String, String> tekiseisyokugyou = new HashMap<>();
    public HashMap<String, Integer> level = new HashMap();
    public HashMap<String, String> type = new HashMap();
    public HashMap<String, String> attackspeed = new HashMap();
    public HashMap<String, Long> damage = new HashMap();
    public HashMap<String, Long> defense = new HashMap();
    public HashMap<String, Long> durability = new HashMap();
    public HashMap<String, Long> weight = new HashMap();
    public HashMap<String, Long> Estrong = new HashMap();
    public HashMap<String, Long> Edefense = new HashMap();
    public HashMap<String, Long> Espeed = new HashMap();
    public HashMap<String, String> one = new HashMap();
    public HashMap<String, String> two = new HashMap();
    public HashMap<String, String> three = new HashMap();
    public HashMap<String, String> rare = new HashMap();

    public CreateManager()
    {
        this.names1.add("アイテムID");
        this.names1.add("アイテムメタデータ");
        this.names1.add("アイテム名");
        this.names1.add("適正職業");
        this.names1.add("レベル");
        this.names1.add("攻撃力（武器用）");
        this.names1.add("防御力（防具用）");
        this.names1.add("耐久力・耐久値");
        this.names1.add("（重さ）");
        this.names1.add("STR");
        this.names1.add("DEF");
        this.names1.add("AGI");
        this.names1.add("詳細文１");
        this.names1.add("詳細文２");
        this.names1.add("詳細文３");

        this.speedlist.add("（攻撃速度予定）とても速い");
        this.speedlist.add("（攻撃速度予定）速い");
        this.speedlist.add("（攻撃速度予定）少し速い");
        this.speedlist.add("（攻撃速度予定）普通");
        this.speedlist.add("（攻撃速度予定）少し遅い");
        this.speedlist.add("（攻撃速度予定）遅い");
        this.speedlist.add("（攻撃速度予定）とても遅い");
        this.speedlist.add("（攻撃速度予定）設定しない");

        this.typelist.add("（武器装備種類予定）ショートソード");
        this.typelist.add("（武器装備種類予定）ロングソード");
        this.typelist.add("（武器装備種類予定）レイピア");
        this.typelist.add("（武器装備種類予定）ダガー");
        this.typelist.add("（武器装備種類予定）グレートソード");
        this.typelist.add("（武器装備種類予定）クラブ");
        this.typelist.add("（武器装備種類予定）アックス");
        this.typelist.add("（武器装備種類予定）カタナ");
        this.typelist.add("（武器装備種類予定）シミター");
        this.typelist.add("（武器装備種類予定）スピア");
        this.typelist.add("（武器装備種類予定）アーマー");
        this.typelist.add("（武器装備種類予定）設定しない");

        this.rarelist.add("（レア度予定）§4SS");
        this.rarelist.add("（レア度予定）§cS");
        this.rarelist.add("（レア度予定）§3A");
        this.rarelist.add("（レア度予定）§9B");
        this.rarelist.add("（レア度予定）§2C");
        this.rarelist.add("（レア度予定）§aD");
        this.rarelist.add("（レア度予定）§5E");
        this.rarelist.add("（レア度予定）設定しない");
    }

    public void createItem(Player player, String name)
    {
        remove(player);

        this.playertool.put(player, name);

        this.material.put(name, Integer.valueOf(268));
        this.data.put(name, Integer.valueOf(0));
        this.display.put(name, null);
        this.tekiseisyokugyou.put(name, null);

        this.level.put(name, Integer.valueOf(0));
        this.type.put(name, "ショートソード");
        this.attackspeed.put(name, null);
        this.damage.put(name, Long.valueOf(0L));
        this.defense.put(name, Long.valueOf(0L));
        this.durability.put(name, Long.valueOf(0L));
        this.weight.put(name, Long.valueOf(0L));

        this.Estrong.put(name, Long.valueOf(0L));
        this.Edefense.put(name, Long.valueOf(0L));
        this.Espeed.put(name, Long.valueOf(0L));

        this.one.put(name, null);
        this.two.put(name, null);
        this.three.put(name, null);

        this.rare.put(name, "E");

        player.openInventory(this.plugin.menuManager.getItemCreater(
                ((Integer)this.material.get(name)).intValue(),
                ((Integer)this.data.get(name)).intValue(),
                (String)this.display.get(name),
                (String)this.tekiseisyokugyou.get(name),
                (String)this.type.get(name),
                ((Integer)this.level.get(name)).intValue(),
                (String)this.attackspeed.get(name),
                ((Long)this.damage.get(name)).longValue(),
                ((Long)this.defense.get(name)).longValue(),
                ((Long)this.durability.get(name)).longValue(),
                ((Long)this.weight.get(name)).longValue(),
                ((Long)this.Estrong.get(name)).longValue(),
                ((Long)this.Edefense.get(name)).longValue(),
                ((Long)this.Espeed.get(name)).longValue(),
                (String)this.one.get(name),
                (String)this.two.get(name),
                (String)this.three.get(name),
                (String)this.rare.get(name), name));
    }

    public void editItem(Player player, String name, File file)
    {
        remove(player);
        String s = name;

        File itemFile = new File(Bukkit.getServer().getPluginManager().getPlugin(this.plugin.getName()).getDataFolder(), File.separator + "Items");
        YamlConfiguration conf = YamlConfiguration.loadConfiguration(new File(itemFile, "/items.yml"));

        ItemStack item = (ItemStack)this.plugin.itemFile.items.get(name);
        this.playertool.put(player, name);
        this.setfile.put(name, file);

        this.material.put(name, Integer.valueOf(conf.getInt(name + ".Item.Type")));
        this.data.put(name, Integer.valueOf(conf.getInt(name + ".Item.Data")));
        this.display.put(name, conf.getString(name + ".Item.DisplayName"));
        this.tekiseisyokugyou.put(name, conf.getString(name + ".Item.TekiseiSyokugyou"));

        this.level.put(name, Integer.valueOf(conf.getInt(name + ".Stats.Level")));
        this.type.put(name, conf.getString(name + ".Stats.Type"));
        this.attackspeed.put(name, conf.getString(name + ".Stats.AttackSpeed"));
        this.damage.put(name, Long.valueOf(conf.getLong(name + ".Stats.Damage")));
        this.defense.put(name, Long.valueOf(conf.getLong(name + ".Stats.Defense")));
        this.durability.put(name, Long.valueOf(conf.getLong(name + ".Stats.Durability")));
        this.weight.put(name, Long.valueOf(conf.getLong(name + ".Stats.Weight")));

        this.Estrong.put(name, Long.valueOf(conf.getLong(name + ".Effect.Strong")));
        this.Edefense.put(name, Long.valueOf(conf.getLong(name + ".Effect.Defense")));
        this.Espeed.put(name, Long.valueOf(conf.getLong(name + ".Effect.Speed")));

        this.one.put(name, conf.getString(name + ".Description.one"));
        this.two.put(name, conf.getString(name + ".Description.two"));
        this.three.put(name, conf.getString(name + ".Description.three"));

        this.rare.put(name, conf.getString(name + ".Rarity"));

        player.openInventory(this.plugin.menuManager.getItemCreater(
                ((Integer)this.material.get(name)).intValue(),
                ((Integer)this.data.get(name)).intValue(),
                (String)this.display.get(name),
                (String)this.tekiseisyokugyou.get(name),
                (String)this.type.get(name),
                ((Integer)this.level.get(name)).intValue(),
                (String)this.attackspeed.get(name),
                ((Long)this.damage.get(name)).longValue(),
                ((Long)this.defense.get(name)).longValue(),
                ((Long)this.durability.get(name)).longValue(),
                ((Long)this.weight.get(name)).longValue(),
                ((Long)this.Estrong.get(name)).longValue(),
                ((Long)this.Edefense.get(name)).longValue(),
                ((Long)this.Espeed.get(name)).longValue(),
                (String)this.one.get(name),
                (String)this.two.get(name),
                (String)this.three.get(name),
                (String)this.rare.get(name), s));
    }

    private long setLong(YamlConfiguration conf, String name)
    {
        long r = 0L;

        return 0L;
    }

    private int setint(YamlConfiguration conf, String name)
    {
        int r = 0;

        return 0;
    }

    private String setType(YamlConfiguration conf, String name)
    {
        String r = "ショートソード";
        if (conf.get(name + ".Stats.Type") != null) {
            r = conf.getString(name + ".Stats.Type");
        }
        return r;
    }

    public void openC(Player player)
    {
        String name = (String)this.playertool.get(player);

        player.openInventory(this.plugin.menuManager.getItemCreater(
                ((Integer)this.material.get(name)).intValue(),
                ((Integer)this.data.get(name)).intValue(),
                (String)this.display.get(name),
                (String)this.tekiseisyokugyou.get(name),
                (String)this.type.get(name),
                ((Integer)this.level.get(name)).intValue(),
                (String)this.attackspeed.get(name),
                ((Long)this.damage.get(name)).longValue(),
                ((Long)this.defense.get(name)).longValue(),
                ((Long)this.durability.get(name)).longValue(),
                ((Long)this.weight.get(name)).longValue(),
                ((Long)this.Estrong.get(name)).longValue(),
                ((Long)this.Edefense.get(name)).longValue(),
                ((Long)this.Espeed.get(name)).longValue(),
                (String)this.one.get(name),
                (String)this.two.get(name),
                (String)this.three.get(name),
                (String)this.rare.get(name), name));
    }

    public void clickItem(InventoryClickEvent e)
    {
        String dname = e.getCurrentItem().getItemMeta().getDisplayName();
        if (dname.equals("§2§lアイテムを保存して終了！"))
        {
            create((Player)e.getWhoClicked());
            remove((Player)e.getWhoClicked());
            e.getWhoClicked().closeInventory();
            return;
        }
        if (dname.equals("§3§lアイテムを保存"))
        {
            create((Player)e.getWhoClicked());
            return;
        }
        if (dname.equals("§4§l作るのをやめる"))
        {
            e.getWhoClicked().closeInventory();
            remove((Player)e.getWhoClicked());
            return;
        }
        if (dname.equals("§f攻撃スピードの設定（予定）"))
        {
            e.getWhoClicked().openInventory(this.plugin.menuManager.getSpeed());
            return;
        }
        if (dname.equals("§fレア度の設定（予定）"))
        {
            e.getWhoClicked().openInventory(this.plugin.menuManager.getRarity());
            return;
        }
        if (dname.equals("§f武器装備種類の設定（予定）"))
        {
            e.getWhoClicked().openInventory(this.plugin.menuManager.getType());
            return;
        }
        for (String name : this.speedlist) {
            if (dname.equals("§b§l" + name))
            {
                if (name.equals("（攻撃速度予定）設定しない"))
                {
                    String n = (String)this.playertool.get(e.getWhoClicked());

                    this.attackspeed.put(n, null);
                    openC((Player)e.getWhoClicked());
                    return;
                }
                String n = (String)this.playertool.get(e.getWhoClicked());

                this.attackspeed.put(n, name);
                openC((Player)e.getWhoClicked());
                return;
            }
        }
        for (String name : this.rarelist) {
            if (dname.equals("§c§l" + name))
            {
                if (name.equals("（レア度予定）設定しない"))
                {
                    String n = (String)this.playertool.get(e.getWhoClicked());

                    this.rare.put(n, null);
                    openC((Player)e.getWhoClicked());
                    return;
                }
                String n = (String)this.playertool.get(e.getWhoClicked());

                this.rare.put(n, name);
                openC((Player)e.getWhoClicked());
                return;
            }
        }
        for (String name : this.typelist) {
            if (dname.equals("§d§l" + name))
            {
                if (name.equals("（武器装備種類予定）設定しない"))
                {
                    String n = (String)this.playertool.get(e.getWhoClicked());

                    this.type.put(n, "???");
                    openC((Player)e.getWhoClicked());
                    return;
                }
                if (name.equals("防具"))
                {
                    String n = (String)this.playertool.get(e.getWhoClicked());

                    this.type.put(n, name);
                    this.damage.put(n, Long.valueOf(0L));
                    this.attackspeed.put(n, null);
                    openC((Player)e.getWhoClicked());
                    return;
                }
                String n = (String)this.playertool.get(e.getWhoClicked());

                this.defense.put(n, Long.valueOf(0L));
                this.type.put(n, name);
                openC((Player)e.getWhoClicked());
                return;
            }
        }
        for (String name : this.names1) {
            if (dname.equals("§f" + name + "の設定"))
            {
                this.playersetting.put((Player)e.getWhoClicked(), name);
                e.getWhoClicked().closeInventory();
                e.getWhoClicked().sendMessage("§e" + name + "を入力してください。");
                e.getWhoClicked().sendMessage("§e設定をしない場合は\"null\"と入力してください。");
                e.getWhoClicked().sendMessage("§e設定をやめたい場合は\"cancel\"と入力してください。");
                return;
            }
        }
    }

    public void remove(Player player)
    {
        if (!this.playertool.containsKey(player)) {
            return;
        }
        String name = (String)this.playertool.get(player);

        this.setfile.remove(name);
        this.playertool.remove(player);
        this.material.remove(name);
        this.data.remove(name);
        this.display.remove(name);
        this.tekiseisyokugyou.remove(name);
        this.level.remove(name);
        this.type.remove(name);
        this.attackspeed.remove(name);
        this.damage.remove(name);
        this.defense.remove(name);
        this.weight.remove(name);
        this.Estrong.remove(name);
        this.Edefense.remove(name);
        this.Espeed.remove(name);
        this.one.remove(name);
        this.two.remove(name);
        this.three.remove(name);
        this.rare.remove(name);
    }

    private void create(Player player)
    {
        String name = (String)this.playertool.get(player);

        File itemFile = new File(Bukkit.getServer().getPluginManager().getPlugin(this.plugin.getName()).getDataFolder(), File.separator + "Items");
        File f = new File(itemFile, "/items.yml");
        if (this.setfile.containsKey(name)) {
            f = (File)this.setfile.get(name);
        }
        YamlConfiguration conf = YamlConfiguration.loadConfiguration(f);

        conf.set(name + ".Item.TekiseiSyokugyou", this.tekiseisyokugyou.get(name));
        conf.set(name + ".Item.DisplayName", this.display.get(name));
        conf.set(name + ".Item.Type", this.material.get(name));
        conf.set(name + ".Item.Data", this.data.get(name));
        if (((Integer)this.level.get(name)).intValue() != 0) {
            conf.set(name + ".Stats.Level", this.level.get(name));
        } else {
            conf.set(name + ".Stats.Level", null);
        }
        if (((Long)this.damage.get(name)).longValue() != 0L) {
            conf.set(name + ".Stats.Damage", this.damage.get(name));
        } else {
            conf.set(name + ".Stats.Damage", null);
        }
        if (((Long)this.defense.get(name)).longValue() != 0L) {
            conf.set(name + ".Stats.Defense", this.defense.get(name));
        } else {
            conf.set(name + ".Stats.Defense", null);
        }
        if (((Long)this.durability.get(name)).longValue() != 0L) {
            conf.set(name + ".Stats.Durability", this.durability.get(name));
        } else {
            conf.set(name + ".Stats.Durability", null);
        }
        if (((Long)this.weight.get(name)).longValue() != 0L) {
            conf.set(name + ".Stats.Weight", this.weight.get(name));
        } else {
            conf.set(name + ".Stats.Weight", null);
        }
        if (((Long)this.Estrong.get(name)).longValue() != 0L) {
            conf.set(name + ".Effect.Strong", this.Estrong.get(name));
        } else {
            conf.set(name + ".Effect.Strong", null);
        }
        if (((Long)this.Edefense.get(name)).longValue() != 0L) {
            conf.set(name + ".Effect.Defense", this.Edefense.get(name));
        } else {
            conf.set(name + ".Effect.Defense", null);
        }
        if (((Long)this.Espeed.get(name)).longValue() != 0L) {
            conf.set(name + ".Effect.Speed", this.Espeed.get(name));
        } else {
            conf.set(name + ".Effect.Speed", null);
        }
        conf.set(name + ".Stats.AttackSpeed", this.attackspeed.get(name));
        conf.set(name + ".Stats.Type", this.type.get(name));
        conf.set(name + ".Description.one", this.one.get(name));
        conf.set(name + ".Description.two", this.two.get(name));
        conf.set(name + ".Description.three", this.three.get(name));
        if (this.rare.get(name) != null) {
            conf.set(name + ".Rarity", this.rare.get(name));
        } else {
            conf.set(name + ".Rarity", null);
        }
        if ((((Long)this.Estrong.get(name)).longValue() == 0L) && (((Long)this.Edefense.get(name)).longValue() == 0L) && (((Long)this.Espeed.get(name)).longValue() == 0L)) {
            conf.set(name + ".Effect", null);
        }
        if (this.one.get(name) == null) {
            conf.set(name + "..Description.one", null);
        }
        if (this.two.get(name) == null) {
            conf.set(name + "..Description.two", null);
        }
        if (this.three.get(name) == null) {
            conf.set(name + "..Description.three", null);
        }
        if ((this.one.get(name) == null) && (this.two.get(name) == null) && (this.three.get(name) == null)) {
            conf.set(name + ".Description", null);
        }
        try
        {
            conf.save(f);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        this.plugin.itemFile.createFile();

        player.sendMessage(this.plugin.prefix.getPrefix + "§eアイテムを保存しました。");
    }
}
