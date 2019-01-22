package net.matcha.matcha_itemstack.commands.GUI;


import net.matcha.matcha_itemstack.Matcha_ItemStack;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Create
{
    private Matcha_ItemStack plugin = (Matcha_ItemStack)Matcha_ItemStack.getPlugin(Matcha_ItemStack.class);

    public boolean create(CommandSender sender, Command command, String label, String[] args)
    {
        if (args.length == 1)
        {
            sender.sendMessage(this.plugin.prefix.getErrorPrefix + "/items create [製作するアイテム名]");
            return true;
        }
        if (args.length > 2)
        {
            sender.sendMessage(this.plugin.prefix.getErrorPrefix + "/items create [製作するアイテム名]");
            return true;
        }
        if (this.plugin.itemFile.items.containsKey(args[1]))
        {
            sender.sendMessage(this.plugin.prefix.getErrorPrefix + args[1] + "はすでに存在します");
            return true;
        }
        this.plugin.createManager.createItem((Player)sender, args[1]);

        return true;
    }
}
