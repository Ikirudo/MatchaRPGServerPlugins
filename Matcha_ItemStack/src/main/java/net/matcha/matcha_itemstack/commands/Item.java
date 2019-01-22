package net.matcha.matcha_itemstack.commands;

import net.matcha.matcha_itemstack.Matcha_ItemStack;
import net.matcha.matcha_itemstack.util.Prefixs;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Item implements CommandExecutor {
    private Matcha_ItemStack plugin = (Matcha_ItemStack)Matcha_ItemStack.getPlugin(Matcha_ItemStack.class);

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args)
    {
        if (!(sender instanceof Player))
        {
            sender.sendMessage(this.plugin.prefix.getErrorPrefix + "プレイヤーのみ");
            return true;
        }
        if (args.length == 0)
        {
            sendHelp((Player)sender);
            return true;
        }
        switch (args[0])
        {
       //deprecated
        }
        sendHelp((Player)sender);

        return true;
    }

    private void sendHelp(Player sender)
    {
        //deprecated
    }
}