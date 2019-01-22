package net.matcha.matcha_itemstack.commands.TAB;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import net.matcha.matcha_itemstack.Matcha_ItemStack;
import net.matcha.matcha_itemstack.util.ItemFile;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

public class ItemsTAB implements TabCompleter
{
    private Matcha_ItemStack plugin = (Matcha_ItemStack)Matcha_ItemStack.getPlugin(Matcha_ItemStack.class);

    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args)
    {
        ArrayList<String> tab;
        if (args.length == 1)
        {
            ArrayList<String> list = new ArrayList();
            tab = new ArrayList();

            tab.add("create");
            tab.add("delete");
            tab.add("edit");
            tab.add("load");
            tab.add("list");
            tab.add("reload");
            if (!args[0].equals("")) {
                for (String name : tab) {
                    if (name.toLowerCase().startsWith(args[0].toLowerCase())) {
                        list.add(name);
                    }
                }
            } else {
                list.addAll(tab);
            }
            Collections.sort(list);

            return list;
        }
        if (args.length < 1) {
            return null;
        }
        if ((args[0].equalsIgnoreCase("load")) ||
                (args[0].equalsIgnoreCase("delete")) ||
                (args[0].equalsIgnoreCase("editor")))
        {
            ArrayList<String> list = new ArrayList();
            if (!args[1].equals("")) {
                for (String name : this.plugin.itemFile.items.keySet()) {
                    if (name.toLowerCase().startsWith(args[1].toLowerCase())) {
                        list.add(name);
                    }
                }
            } else {
                list.addAll(this.plugin.itemFile.items.keySet());
            }
            Collections.sort(list);

            return list;
        }
        return null;
    }
}