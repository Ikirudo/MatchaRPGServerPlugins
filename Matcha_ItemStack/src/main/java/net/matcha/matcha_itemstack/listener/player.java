package net.matcha.matcha_itemstack.listener;

import java.util.HashMap;
import net.matcha.matcha_itemstack.Matcha_ItemStack;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerDropItemEvent;

public class player implements Listener
{
    private HashMap<Player, Boolean> playerD = new HashMap();
    private HashMap<Player, Boolean> playerK = new HashMap();
    private HashMap<Player, Boolean> playerL = new HashMap();
    private HashMap<Player, Boolean> playerM = new HashMap();
    private Matcha_ItemStack plugin = (Matcha_ItemStack)Matcha_ItemStack.getPlugin(Matcha_ItemStack.class);

    @EventHandler
    public void onChat(InventoryClickEvent e)
    {
        if (e.getWhoClicked().getGameMode() != GameMode.CREATIVE) {
            if (e.getClick() == ClickType.NUMBER_KEY) {
                e.setCancelled(true);
            }
        }
        if (!(e.getInventory().getTitle().startsWith("§2§lItemStackツール（GUI)")||e.getInventory().getTitle().startsWith("§b§l攻撃スピードの設定（予定）")
        ||e.getInventory().getTitle().startsWith("§5§lレア度の設定（予定）")||e.getInventory().getTitle().startsWith("§c§l武器装備種類の設定（予定）"))) {
            return;
        }
        e.setCancelled(true);
        if (e.getSlotType() == InventoryType.SlotType.OUTSIDE) {
            return;
        }
        if (e.getCurrentItem().getType() == Material.AIR) {
            return;
        }
        if (!e.getCurrentItem().getItemMeta().hasDisplayName()) {
            return;
        }
        this.plugin.createManager.clickItem(e);
    }
    @EventHandler
    public void onChat(AsyncPlayerChatEvent e) {
        if (this.plugin.createManager.playersetting.containsKey(e.getPlayer())) {
            this.plugin.chatManager.setItem(e);

            return;
        }
        if (!e.getPlayer().isOp()) {
            return;
        }
        Player p = e.getPlayer();
        if (e.getMessage().startsWith("System")) {
            e.setCancelled(true);
        }
    }
    @EventHandler
    public void onDrop(PlayerDropItemEvent e)
    {
        if (e.getPlayer().hasPermission("itemstack.op")) {
            return;
        }
        e.setCancelled(true);
    }

    @EventHandler
    public void onCraft(CraftItemEvent e)
    {
        if (e.getWhoClicked().hasPermission("itemstack.op")) {
            return;
        }
        e.setCancelled(true);
    }
}