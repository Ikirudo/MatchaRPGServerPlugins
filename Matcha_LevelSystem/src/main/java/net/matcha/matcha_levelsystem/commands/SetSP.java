package net.matcha.matcha_levelsystem.commands;

import net.matcha.matcha_levelsystem.Matcha_LevelSystem;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SetSP implements CommandExecutor {
    private Matcha_LevelSystem plugin = (Matcha_LevelSystem)Matcha_LevelSystem.getPlugin(Matcha_LevelSystem.class);

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args)
    {
        if (!(sender instanceof Player))
        {
            sender.sendMessage(this.plugin.prefix.getErrorPrefix + "プレイヤーのみ使用可能です");
            return true;
        }
        if (args.length > 2)
        {
            sender.sendMessage(this.plugin.prefix.getErrorPrefix + "/setsp [sp] <player>");
            return true;
        }
        if (args.length == 0)
        {
            sender.sendMessage(this.plugin.prefix.getErrorPrefix + "/setsp [sp] <player>");
            return true;
        }
        if (!isInt(args[0]))
        {
            sender.sendMessage(this.plugin.prefix.getErrorPrefix + "その数字を入力してください");
            return true;
        }
        switch (args.length)
        {
            case 1:
                this.plugin.statsManager.setSP((Player)sender, Integer.parseInt(args[0]));
                this.plugin.statsManager.setStats((Player)sender);
                sender.sendMessage(this.plugin.prefix.getPrefix + "SPを" + args[0] + "に設定しました");
                return true;
            case 2:
                Player target = this.plugin.getServer().getPlayer(args[1]);
                if (target == null)
                {
                    sender.sendMessage(this.plugin.prefix.getErrorPrefix + "指定したプレイヤーが見つかりませんでした");
                    return true;
                }
                if (!target.isOnline())
                {
                    sender.sendMessage(this.plugin.prefix.getErrorPrefix + "指定したプレイヤーは現在オフラインです");
                    return true;
                }
                this.plugin.statsManager.setSP(target, Integer.parseInt(args[0]));
                this.plugin.statsManager.setStats(target);
                sender.sendMessage(this.plugin.prefix.getPrefix + "SPを" + args[0] + "に設定しました");
                return true;
        }
        return true;
    }

    private boolean isInt(String s)
    {
        try
        {
            Integer.parseInt(s);
        }
        catch (NumberFormatException e)
        {
            return false;
        }
        return true;
    }
}
