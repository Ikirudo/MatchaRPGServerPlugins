package net.matcha.matcha_itemstack.commands.GUI;

import java.io.File;
import java.io.IOException;

import net.matcha.matcha_itemstack.Matcha_ItemStack;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;

public class Delete
{
    private Matcha_ItemStack plugin = (Matcha_ItemStack)Matcha_ItemStack.getPlugin(Matcha_ItemStack.class);

    public boolean delete(CommandSender sender, Command command, String label, String[] args)
    {
        if (args.length != 2)
        {
            sender.sendMessage(this.plugin.prefix.getErrorPrefix + "/items delete [削除するアイテム名]");
            return true;
        }
        if (!this.plugin.itemFile.items.containsKey(args[1]))
        {
            sender.sendMessage(this.plugin.prefix.getErrorPrefix + args[1] + "はすでに存在します。");
            return true;
        }
        File file = (File)this.plugin.itemFile.itemsfile.get(this.plugin.itemFile.items.get(args[1]));

        YamlConfiguration files = YamlConfiguration.loadConfiguration(file);
        files.set(args[1], null);
        try
        {
            files.save(file);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        this.plugin.itemFile.items.remove(args[1]);

        sender.sendMessage(this.plugin.prefix.getPrefix + args[1] + "を削除しました。");

        return true;
    }
}
