package net.matcha.matcha_itemstack.commands;

import net.matcha.matcha_itemstack.Matcha_ItemStack;
import net.matcha.matcha_itemstack.commands.GUI.*;
import net.matcha.matcha_itemstack.util.Prefixs;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Items implements CommandExecutor {
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
            case "create":
                return new Create().create(sender, command, label, args);
            case "delete":
                return new Delete().delete(sender, command, label, args);
            case "editor":
                return new Edit().edit(sender, command, label, args);
            case "edit":
                return new Edit().edit(sender, command, label, args);
            case "load":
                return new Load().list(sender, command, label, args);
            case "list":
                return new List().list(sender, command, label, args);
            case "reload":
                return new Reload().list(sender, command, label, args);
            case "rl":
                return new Reload().list(sender, command, label, args);
            case "r":
                return new Reload().list(sender, command, label, args);
        }
        sendHelp((Player)sender);

        return true;
    }

    private void sendHelp(Player sender)
    {
        sender.sendMessage("§a§m-------------------------§c§l§o Matcha_ItemStack §a§m-------------------------");
        sender.sendMessage(" ");
        sender.sendMessage(" §a/items create [アイテム名] §e: §b:アイテム作成欄を開きます。");
        sender.sendMessage(" §a/items delete [アイテム名] §e: §b:アイテムを削除します");
        sender.sendMessage(" §a/items edit [アイテム名] §e: §b:アイテムを編集します。");
        sender.sendMessage(" §a/items load [アイテム名] §e: §b:アイテムを読み取ります。");
        sender.sendMessage(" §a/items list §e: §b:作ったアイテムの確認。");
        sender.sendMessage(" §a/items reload §e: §b:リロード。");
        sender.sendMessage(" ");
        sender.sendMessage("§a§m-------------------------§c§l§o Matcha_ItemStack §a§m-------------------------");
    }
}