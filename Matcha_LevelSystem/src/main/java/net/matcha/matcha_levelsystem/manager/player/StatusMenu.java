package net.matcha.matcha_levelsystem.manager.player;

import net.matcha.matcha_levelsystem.Matcha_LevelSystem;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

public class StatusMenu {
    private Matcha_LevelSystem plugin = (Matcha_LevelSystem)Matcha_LevelSystem.getPlugin(Matcha_LevelSystem.class);

    public void open(Player player)
    {
        Inventory inv = Bukkit.createInventory(null, 27,"§a§lステータスポイント割り振り");

        ItemStack glass = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short)0);
        ItemStack sp = new ItemStack(Material.CHEST, 1, (short)0);
        ItemStack m1 = new ItemStack(Material.DIAMOND_SWORD, 1, (short)0);
        ItemStack m2 = new ItemStack(Material.GOLDEN_APPLE, 1, (short)0);
        ItemStack m3 = new ItemStack(Material.GOLD_BOOTS, 1, (short)0);
        ItemStack m4 = new ItemStack(Material.IRON_CHESTPLATE, 1, (short)0);

        glass = setRename(glass, " ");
        sp = setRename(sp, "§b§lステータスポイント§f§l: §e§l" + this.plugin.statsManager.getSP(player));
        m1 = setRename(m1, "§4§lSTR§7(現在: " + this.plugin.statsManager.getStatsDamage(player) + ")");
        m2 = setRename(m2, "§a§lVIT§7(現在: " + this.plugin.statsManager.getStatsHealth(player) + ")");
        m3 = setRename(m3, "§b§lAGI§7(現在: " + this.plugin.statsManager.getStatsSpeed(player) + ")");
        m4 = setRename(m4, "§9§lDEF§7(現在: " + this.plugin.statsManager.getStatsDefense(player) + ")");

        glass = setFlag(glass);
        m1 = setFlag(m1);
        m2 = setFlag(m2);
        m3 = setFlag(m3);
        m4 = setFlag(m4);

        ItemMeta glassmeta = glass.getItemMeta();
        ItemMeta m1meta =m1.getItemMeta();
        ItemMeta m2meta =m2.getItemMeta();
        ItemMeta m3meta =m3.getItemMeta();
        ItemMeta m4meta =m4.getItemMeta();
        glassmeta.setLore(Arrays.asList("§a§lステータスアイコンをクリックで","§a§lSPを消費しステータスアップ！"));
        m1meta.setLore(Arrays.asList("§b§l攻撃力","§a§lSPを消費し攻撃力を強化しよう！"));
        m2meta.setLore(Arrays.asList("§b§l体力","§a§lSPを消費し体力を強化しよう！"));
        m3meta.setLore(Arrays.asList("§b§l素早さ","§a§lSPを消費し素早さを強化しよう！"));
        m4meta.setLore(Arrays.asList("§b§l防御力","§a§lSPを消費し防御力を強化しよう！"));
        glass.setItemMeta(glassmeta);
        m1.setItemMeta(m1meta);
        m2.setItemMeta(m2meta);
        m3.setItemMeta(m3meta);
        m4.setItemMeta(m4meta);

        inv.setItem(4, sp);
        inv.setItem(10, m1);
        inv.setItem(12, m2);
        inv.setItem(14, m3);
        inv.setItem(16, m4);

        player.openInventory(inv);
    }

    private ItemStack setFlag(ItemStack item)
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

    private ItemStack setRename(ItemStack item, String name)
    {
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(name);
        item.setItemMeta(meta);

        return item;
    }
}