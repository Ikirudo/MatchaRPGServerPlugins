package net.matcha.matcha_itemstack.util;


import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import net.matcha.matcha_itemstack.Matcha_ItemStack;
import net.minecraft.server.v1_12_R1.*;
import org.apache.commons.io.FileUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.craftbukkit.v1_12_R1.inventory.CraftItemStack;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.meta.ItemMeta;


public class ItemFile
{
    private Matcha_ItemStack plugin = (Matcha_ItemStack)Matcha_ItemStack.getPlugin(Matcha_ItemStack.class);

    public HashMap<Player, String> playertool = new HashMap();
    public HashMap<String, org.bukkit.inventory.ItemStack> items = new HashMap();
    public HashMap<org.bukkit.inventory.ItemStack, File> itemsfile = new HashMap();

    public void createFile()
    {
        File itemFile = new File(Bukkit.getServer().getPluginManager().getPlugin(this.plugin.getName()).getDataFolder(), File.separator + "Items");
        if (!itemFile.exists())
        {
            itemFile.mkdirs();
            this.plugin.getLogger().info("created an Items file");
        }
        if (!new File(itemFile, "/items.yml").exists()) {
            try
            {
                FileUtils.copyInputStreamToFile(this.plugin.getResource("items.yml"), new File(itemFile, "/items.yml"));
                this.plugin.getLogger().info("created an items.yml");
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
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

    public File[] getFiles()
    {
        File itemFile = new File(Bukkit.getServer().getPluginManager().getPlugin(this.plugin.getName()).getDataFolder(), File.separator + "Items");

        return itemFile.listFiles();
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
}