package net.matcha.matcha_itemstack.manager.items;

import java.util.ArrayList;
import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class MenuManager
{
    public Inventory getItemCreater(int material, int data, String display,String tekiseisyokugyou, String itemtype, int level, String attackspeed, long damage, long defense, long durability, long weight, long estrong, long edefense, long espeed, String lore1, String lore2, String lore3, String Rare, String name)
    {
        Inventory inv = Bukkit.createInventory(null, 54, "§2§lItemStackツール（GUI)");

        ItemStack mainitem = new ItemStack((short)material, 1, (short)data);

        ItemStack Iglass = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short)15);
        ItemStack Sglass = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short)15);
        ItemStack Eglass = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short)15);
        ItemStack Dglass = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short)15);
        ItemStack glass = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short)12);
        ItemStack glass2 = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short)4);
        ItemStack rare = new ItemStack(Material.NETHER_STAR, 1, (short)0);

        ItemStack Type = new ItemStack(Material.SLIME_BALL, 1, (short)0);
        ItemStack Data = new ItemStack(Material.SLIME_BALL, 1, (short)0);
        ItemStack Display = new ItemStack(Material.NAME_TAG, 1, (short)0);
        ItemStack TekiseiSyokugyou = new ItemStack(Material.BARRIER,1,(short)0);

        ItemStack ItemType = new ItemStack(Material.ENDER_PORTAL_FRAME, 1, (short)0);

        ItemStack Level = new ItemStack(Material.EXP_BOTTLE, 1, (short)0);
        ItemStack AttackSpeed = new ItemStack(Material.DIAMOND_SWORD, 1, (short)0);
        ItemStack Damage = new ItemStack(Material.WOOD_SWORD, 1, (short)0);
        ItemStack Defense = new ItemStack(Material.IRON_CHESTPLATE, 1, (short)0);
        ItemStack Durability = new ItemStack(Material.ANVIL, 1, (short)0);
        ItemStack Weight = new ItemStack(Material.SHIELD, 1, (short)0);

        ItemStack EDamage = new ItemStack(Material.IRON_SWORD, 1, (short)0);
        ItemStack EDefense = new ItemStack(Material.IRON_CHESTPLATE, 1, (short)0);
        ItemStack ESpeed = new ItemStack(Material.DIAMOND_BOOTS, 1, (short)0);

        ItemStack one = new ItemStack(Material.INK_SACK, 1, (short)10);
        ItemStack two = new ItemStack(Material.INK_SACK, 1, (short)10);
        ItemStack three = new ItemStack(Material.INK_SACK, 1, (short)10);

        ItemStack savefinal = new ItemStack(Material.WOOL, 1, (short)5);
        ItemStack save = new ItemStack(Material.WOOL, 1, (short)3);
        ItemStack delete = new ItemStack(Material.WOOL, 1, (short)14);

        List<String> lores = new ArrayList();

        Type = setItemsLores(Type, "§fアイテムIDの設定", "§a(現在: " + material + ")");
        Data = setItemsLores(Data, "§fアイテムメタデータの設定", "§a(現在: " + data + ")");
        Display = setItemsLores(Display, "§fアイテム名の設定", "§a(現在: " + display + "§a)");
        TekiseiSyokugyou = setItemsLores(TekiseiSyokugyou,"§f適正職業の設定","§a(現在: " + tekiseisyokugyou + "§a)");
        Level = setItemsLores(Level, "§fレベルの設定", "§a(現在: " + s(level) + ")");
        AttackSpeed = setItemsLores(AttackSpeed, "§f攻撃スピードの設定（予定）", "§a(現在: " + attackspeed + ")");
        Damage = setItemsLores(Damage, "§f攻撃力（武器用）の設定", "§a(現在: " + s(damage) + ")");
        Defense = setItemsLores(Defense, "§f防御力（防具用）の設定", "§a(現在: " + s(defense) + ")");
        Durability = setItemsLores(Durability, "§f耐久力・耐久値の設定", "§a(現在: " + s(durability) + ")");
        Weight = setItemsLores(Weight, "§f（重さ）の設定", "§a(現在: " + s(weight) + ")");

        EDamage = setItemsLores(EDamage, "§fSTRの設定", "§a(現在: " + s(estrong) + ")");
        EDefense = setItemsLores(EDefense, "§fDEFの設定", "§a(現在: " + s(edefense) + ")");
        ESpeed = setItemsLores(ESpeed, "§fAGIの設定", "§a(現在: " + s(espeed) + ")");

        one = setItemsLores(one, "§f詳細文１の設定", "§a(現在: " + lore1 + "§a)");
        two = setItemsLores(two, "§f詳細文２の設定", "§a(現在: " + lore2 + "§a)");
        three = setItemsLores(three, "§f詳細文３の設定", "§a(現在: " + lore3 + "§a)");

        rare = setItemsLores(rare, "§fレア度の設定（予定）", "§a(現在: " + Rare + "§a)");

        mainitem = setRename(mainitem, "§a保存するアイテム名: §f" + name);
        Iglass = setRename(Iglass, "§a§lアイテム詳細");
        Sglass = setRename(Sglass, "§a§lステータス");
        Eglass = setRename(Eglass, "§a§l装備効果");
        Dglass = setRename(Dglass, "§a§l説明欄の設定");
        savefinal = setRename(savefinal, "§2§lアイテムを保存して終了！");
        save = setRename(save, "§3§lアイテムを保存");
        delete = setRename(delete, "§4§l作るのをやめる");
        ItemType = setItemsLores(ItemType, "§f武器装備種類の設定（予定）", "§a(現在: " + itemtype + "§a)");

        AttackSpeed = setFlag(AttackSpeed);
        Damage = setFlag(Damage);

        inv.setItem(0, savefinal);
        inv.setItem(1, save);
        inv.setItem(2, delete);
        inv.setItem(18, Iglass);
        inv.setItem(27, Sglass);
        inv.setItem(36, Eglass);
        inv.setItem(45, Dglass);
        inv.setItem(7, glass);
        inv.setItem(8, ItemType);
        inv.setItem(16, glass);
        inv.setItem(17, glass);

        inv.setItem(19, Type);
        inv.setItem(20, Data);
        inv.setItem(21, Display);
        inv.setItem(22,TekiseiSyokugyou);

        inv.setItem(37, EDamage);
        inv.setItem(38, EDefense);
        inv.setItem(39, ESpeed);

        inv.setItem(46, one);
        inv.setItem(47, two);
        inv.setItem(48, three);

        inv.setItem(43, glass2);
        inv.setItem(44, glass2);
        inv.setItem(52, glass2);
        inv.setItem(53, rare);
        if (itemtype.equals("防具"))
        {
            inv.setItem(28, Level);
            inv.setItem(29, Defense);
            inv.setItem(30, Durability);
            inv.setItem(31, Weight);
        }
        else
        {
            inv.setItem(28, Level);
            inv.setItem(29, AttackSpeed);
            inv.setItem(30, Damage);
            inv.setItem(31, Durability);
            inv.setItem(32, Weight);
        }
        inv.setItem(4, mainitem);

        return inv;
    }

    public Inventory getSpeed()
    {
        Inventory inv = Bukkit.createInventory(null, 9, "§b§l攻撃スピードの設定（予定）");

        ItemStack glass = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short)0);
        ItemStack hasami = new ItemStack(Material.SHEARS, 1, (short)0);
        ItemStack wood = new ItemStack(Material.WOOD_SWORD, 1, (short)0);
        ItemStack gold = new ItemStack(Material.GOLD_SWORD, 1, (short)0);
        ItemStack iron = new ItemStack(Material.IRON_SWORD, 1, (short)0);
        ItemStack stone = new ItemStack(Material.STONE_SWORD, 1, (short)0);
        ItemStack diamond = new ItemStack(Material.DIAMOND_SWORD, 1, (short)0);
        ItemStack sierd = new ItemStack(Material.SHIELD, 1, (short)0);

        hasami = setRename(hasami, "§b§l（攻撃速度予定）とても速い");
        wood = setRename(wood, "§b§l（攻撃速度予定）速い");
        gold = setRename(gold, "§b§l（攻撃速度予定）少し速い");
        iron = setRename(iron, "§b§l（攻撃速度予定）普通");
        stone = setRename(stone, "§b§l（攻撃速度予定）少し遅い");
        diamond = setRename(diamond, "§b§l（攻撃速度予定）遅い");
        sierd = setRename(sierd, "§b§l（攻撃速度予定）とても遅い");
        glass = setRename(glass, "§b§l（攻撃速度予定）設定しない");

        inv.setItem(0, glass);
        inv.setItem(8, glass);

        inv.setItem(1, hasami);
        inv.setItem(2, wood);
        inv.setItem(3, gold);
        inv.setItem(4, iron);
        inv.setItem(5, stone);
        inv.setItem(6, diamond);
        inv.setItem(7, sierd);

        return inv;
    }

    public Inventory getRarity()
    {
        Inventory inv = Bukkit.createInventory(null, 9, "§5§lレア度の設定（予定）");

        ItemStack glass = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short)0);
        ItemStack k1 = new ItemStack(Material.SLIME_BALL, 1, (short)0);
        ItemStack k2 = new ItemStack(Material.SLIME_BALL, 1, (short)0);
        ItemStack k3 = new ItemStack(Material.SLIME_BALL, 1, (short)0);
        ItemStack k4 = new ItemStack(Material.SLIME_BALL, 1, (short)0);
        ItemStack k5 = new ItemStack(Material.SLIME_BALL, 1, (short)0);
        ItemStack k6 = new ItemStack(Material.SLIME_BALL, 1, (short)0);
        ItemStack k7 = new ItemStack(Material.SLIME_BALL, 1, (short)0);

        k1 = setRename(k1, "§c§l（レア度予定）§4SS");
        k2 = setRename(k2, "§c§l（レア度予定）§cS");
        k3 = setRename(k3, "§c§l（レア度予定）§3A");
        k4 = setRename(k4, "§c§l（レア度予定）§9B");
        k5 = setRename(k5, "§c§l（レア度予定）§2C");
        k6 = setRename(k6, "§c§l（レア度予定）§aD");
        k7 = setRename(k7, "§c§l（レア度予定）§5E");
        glass = setRename(glass, "§c§l（レア度予定）設定しない");

        inv.setItem(0, glass);
        inv.setItem(8, glass);

        inv.setItem(1, k1);
        inv.setItem(2, k2);
        inv.setItem(3, k3);
        inv.setItem(4, k4);
        inv.setItem(5, k5);
        inv.setItem(6, k6);
        inv.setItem(7, k7);

        return inv;
    }

    public Inventory getType()
    {
        Inventory inv = Bukkit.createInventory(null, 27, "§c§l武器装備種類の設定（予定）");

        ItemStack k1 = new ItemStack(Material.IRON_SWORD, 1, (short)0);
        ItemStack k2 = new ItemStack(Material.STONE_SWORD, 1, (short)0);
        ItemStack k3 = new ItemStack(Material.WOOD_SWORD, 1, (short)0);
        ItemStack k4 = new ItemStack(Material.DIAMOND_SWORD, 1, (short)0);
        ItemStack k5 = new ItemStack(Material.STICK, 1, (short)0);
        ItemStack k6 = new ItemStack(Material.GOLD_AXE, 1, (short)0);
        ItemStack k7 = new ItemStack(Material.IRON_SWORD, 1, (short)0);
        ItemStack k8 = new ItemStack(Material.IRON_SWORD, 1, (short)0);
        ItemStack k9 = new ItemStack(Material.IRON_SPADE, 1, (short)0);
        ItemStack k10 = new ItemStack(Material.IRON_CHESTPLATE, 1, (short)0);
        ItemStack k11 = new ItemStack(Material.REDSTONE_TORCH_ON,1, (short)0);
        ItemStack glass = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short)15);
        ItemStack barrier = new ItemStack(Material.BARRIER, 1, (short)0);

        k1 = setRename(k1, "§d§l（武器装備種類予定）ショートソード");
        k2 = setRename(k2, "§d§l（武器装備種類予定）ロングソード");
        k3 = setRename(k3, "§d§l（武器装備種類予定）レイピア");
        k4 = setRename(k4, "§d§l（武器装備種類予定）グレートソード");
        k5 = setRename(k5, "§d§l（武器装備種類予定）クラブ");
        k6 = setRename(k6, "§d§l（武器装備種類予定）アックス");
        k7 = setRename(k7, "§d§l（武器装備種類予定）カタナ");
        k8 = setRename(k8, "§d§l（武器装備種類予定）シミター");
        k9 = setRename(k9, "§d§l（武器装備種類予定）スピア");
        k10 = setRename(k10, "§d§l（武器装備種類予定）アーマー");
        k11 = setRename(k11, "§d§l（武器装備種類予定）ダガー");
        barrier = setRename(barrier, "§d§l（武器装備種類予定）設定しない");
        for (int i = 0; i < 27; i++) {
            inv.setItem(i, glass);
        }
        inv.setItem(9, k1);
        inv.setItem(10, k2);
        inv.setItem(11, k3);
        inv.setItem(12, k4);
        inv.setItem(13, k5);
        inv.setItem(14, k6);
        inv.setItem(15, k7);
        inv.setItem(16, k8);
        inv.setItem(17, k9);
        inv.setItem(25, k10);
        inv.setItem(26, k11);
        inv.setItem(22, barrier);

        return inv;
    }

    private ItemStack setRename(ItemStack item, String name)
    {
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(name);
        item.setItemMeta(meta);

        return item;
    }

    private String s(long n)
    {
        String l = "null";
        if (n > 0L) {
            return n + "";
        }
        return l;
    }

    private ItemStack setLore(ItemStack item, List<String> lore)
    {
        ItemMeta meta = item.getItemMeta();
        meta.setLore(lore);
        item.setItemMeta(meta);

        return item;
    }

    private ItemStack setItems(ItemStack item, String name, List<String> lore)
    {
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(name);
        meta.setLore(lore);
        item.setItemMeta(meta);

        return item;
    }

    private ItemStack setItemsLores(ItemStack item, String name, String number)
    {
        ItemMeta meta = item.getItemMeta();
        List<String> lore = new ArrayList();

        lore.add("§e§lクリックで設定画面へ");
        lore.add("§e§l移動します");
        lore.add(number);

        meta.setDisplayName(name);
        meta.setLore(lore);
        item.setItemMeta(meta);

        return item;
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
}