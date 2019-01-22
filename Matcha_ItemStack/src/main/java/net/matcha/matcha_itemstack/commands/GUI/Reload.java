package net.matcha.matcha_itemstack.commands.GUI;


import net.matcha.matcha_itemstack.Matcha_ItemStack;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public class Reload
{
    private Matcha_ItemStack plugin = (Matcha_ItemStack)Matcha_ItemStack.getPlugin(Matcha_ItemStack.class);

    public boolean list(CommandSender sender, Command command, String label, String[] args)
    {
        sender.sendMessage(this.plugin.prefix.getPrefix + "リロードを開始");
        this.plugin.getServer().getPluginManager().disablePlugin(this.plugin);
        this.plugin.getServer().getPluginManager().enablePlugin(this.plugin);
        sender.sendMessage(this.plugin.prefix.getPrefix + "リロードを完了しました");
        return true;
    }
}
