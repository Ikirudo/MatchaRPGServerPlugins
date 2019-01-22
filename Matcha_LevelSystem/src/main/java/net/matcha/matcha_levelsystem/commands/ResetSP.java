package net.matcha.matcha_levelsystem.commands;

import net.matcha.matcha_levelsystem.Matcha_LevelSystem;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ResetSP
        implements CommandExecutor
{
    private Matcha_LevelSystem plugin = (Matcha_LevelSystem)Matcha_LevelSystem.getPlugin(Matcha_LevelSystem.class);

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args)
    {
        if (!(sender instanceof Player)) {
            return false;
        }
        Player player = (Player)sender;
        if (args.length == 1)
        {
            Player target = this.plugin.getServer().getPlayer(args[0]);
            if (target == null)
            {
                sender.sendMessage(this.plugin.prefix.getErrorPrefix + "指定したプレイヤーが見つかりませんでした");
                return true;
            }
            if (!target.isOnline())
            {
                sender.sendMessage(this.plugin.prefix.getErrorPrefix + "指定したプレイヤーはオフラインです");
                return true;
            }
            resetSP(target);
            player.sendMessage(this.plugin.prefix.getPrefix + args[0] + "のSPをリセットしました");
            return true;
        }
        resetSP(player);
        player.sendMessage(this.plugin.prefix.getPrefix + "自分のSPをリセットしました");

        return true;
    }

    private void resetSP(Player player)
    {
        this.plugin.statsManager.setStatsHealth(player, 0);
        this.plugin.statsManager.setStatsDamage(player, 0);
        this.plugin.statsManager.setStatsDefense(player, 0);
        this.plugin.statsManager.setStatsSpeed(player, 0);
        this.plugin.statsManager.setStats(player);

        this.plugin.statsManager.setSP(player, plugin.getConfig().getInt("Player."+player.getDisplayName()+".Level"));
    }
}