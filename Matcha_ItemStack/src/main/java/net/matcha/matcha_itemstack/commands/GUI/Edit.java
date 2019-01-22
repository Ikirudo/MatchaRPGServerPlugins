package net.matcha.matcha_itemstack.commands.GUI;

import java.io.File;

import net.matcha.matcha_itemstack.Matcha_ItemStack;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Edit
{
    private Matcha_ItemStack plugin = (Matcha_ItemStack)Matcha_ItemStack.getPlugin(Matcha_ItemStack.class);

    public boolean edit(CommandSender sender, Command command, String label, String[] args)
    {
        if (args.length == 1)
        {
            sender.sendMessage(this.plugin.prefix.getErrorPrefix + "/items editor [保存するアイテム名]");
            return true;
        }
        if (args.length > 3)
        {
            sender.sendMessage(this.plugin.prefix.getErrorPrefix + "/items editor [保存するアイテム名]");
            return true;
        }
        if (!this.plugin.itemFile.items.containsKey(args[1]))
        {
            sender.sendMessage(this.plugin.prefix.getErrorPrefix + args[1] + "は存在しません。");
            return true;
        }
        this.plugin.createManager.editItem((Player)sender, args[1], (File)this.plugin.itemFile.itemsfile.get(this.plugin.itemFile.items.get(args[1])));

        return true;
    }
}
