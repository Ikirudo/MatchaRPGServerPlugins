package net.matcha.matcha_itemstack.commands.GUI;

import net.matcha.matcha_itemstack.Matcha_ItemStack;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public class List
{
    private Matcha_ItemStack plugin = (Matcha_ItemStack)Matcha_ItemStack.getPlugin(Matcha_ItemStack.class);

    public boolean list(CommandSender sender, Command command, String label, String[] args)
    {
        StringBuilder list = new StringBuilder("§3§l");
        for (String name : this.plugin.itemFile.items.keySet()) {
            list.append(name).append("§7,§3§l ");
        }
        sender.sendMessage(this.plugin.prefix.getPrefix + "§a§lアイテムリスト(" + this.plugin.itemFile.items.size() + "): " + list);

        return true;
    }
}
