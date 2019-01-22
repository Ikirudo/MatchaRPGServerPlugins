package net.matcha.matcha_levelsystem.commands;

import net.matcha.matcha_levelsystem.Matcha_LevelSystem;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;


public class Stats implements CommandExecutor {
    private Matcha_LevelSystem plugin = (Matcha_LevelSystem)Matcha_LevelSystem.getPlugin(Matcha_LevelSystem.class);

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args)
    {
        if (!(sender instanceof Player)) {
            return false;
        }
        Player player = (Player)sender;

        this.plugin.statusMenu.open(player);

        return true;
    }
}
