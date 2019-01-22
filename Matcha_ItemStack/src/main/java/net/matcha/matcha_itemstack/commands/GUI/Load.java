package net.matcha.matcha_itemstack.commands.GUI;

import net.matcha.matcha_itemstack.Matcha_ItemStack;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class Load
{
    private Matcha_ItemStack plugin = (Matcha_ItemStack)Matcha_ItemStack.getPlugin(Matcha_ItemStack.class);

    public boolean list(CommandSender sender, Command command, String label, String[] args)
    {
        if (args.length != 2)
        {
            sender.sendMessage(this.plugin.prefix.getErrorPrefix + "/items load [取り出すアイテム名]");
            return true;
        }
        if (!this.plugin.itemFile.items.containsKey(args[1]))
        {
            sender.sendMessage(this.plugin.prefix.getErrorPrefix + args[1] + "は存在しません");
            return true;
        }
        Player player = (Player)sender;

        player.getInventory().addItem(new ItemStack[] { (ItemStack)this.plugin.itemFile.items.get(args[1]) });
        player.playSound(player.getLocation(), Sound.ENTITY_BAT_TAKEOFF, 1.0F, 2.0F);
        sender.sendMessage(this.plugin.prefix.getPrefix + args[1] + "を取り出し！");

        return true;
    }
}
