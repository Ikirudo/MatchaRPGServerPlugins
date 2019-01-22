package net.matcha.matcha_levelsystem;

import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;

import me.clip.placeholderapi.external.EZPlaceholderHook;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class OurCoolPlaceholders extends EZPlaceholderHook {

    private Matcha_LevelSystem ourPlugin;

    public OurCoolPlaceholders(Matcha_LevelSystem ourPlugin) {
        // this is the plugin that is registering the placeholder and the identifier for our placeholder.
        // the format for placeholders is this:
        // %<placeholder identifier>_<anything you define as an identifier in your method below>%
        // the placeholder identifier can be anything you want as long as it is not already taken by another
        // registered placeholder.
        super(ourPlugin, "levelsystem");
        // this is so we can access our main class below
        this.ourPlugin = ourPlugin;
    }

    @Override
    public String onPlaceholderRequest(Player p, String identifier) {
        if (identifier.equals("exp")) {
            return String.valueOf(ourPlugin.getConfig().getInt("Player."+p.getDisplayName()+".Exp"));
        }
        // always check if the player is null for placeholders related to the player!
        if (p == null) {
            return "";
        }
        if (identifier.equals("level")) {
            return String.valueOf(ourPlugin.getConfig().getInt("Player."+p.getDisplayName()+".Level"));
        }
        if (identifier.equals("genzaihituyounaexp")) {
            int playergenzaihituyounaexp;
            String pname = p.getDisplayName();
            int playerlevel = ourPlugin.getConfig().getInt("Player."+pname+".Level");
            if(ourPlugin.getConfig().getInt("ExpPerLevel."+playerlevel)==0) {
                playergenzaihituyounaexp = 100;
            }else {
                playergenzaihituyounaexp = ourPlugin.getConfig().getInt("ExpPerLevel." + playerlevel);
            }
            return String.valueOf(playergenzaihituyounaexp);
        }
        if(identifier.equals("sp")){
            return String.valueOf(ourPlugin.statsManager.getSP(p));
        }
        if(identifier.equals("maxhp")){
            int HPbairitu = ourPlugin.getConfig().getInt("StatusBairitu.VIT");
            String pname = p.getDisplayName();
            return String.valueOf(p.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue());
        }
        if(identifier.equals("jobname")){
            String syokugyoumei;
            syokugyoumei = "無職";
            if(p.getScoreboardTags().contains("Job-Fencer")){
                syokugyoumei = "剣士";
            }else  if(p.getScoreboardTags().contains("Job-Shielder")){
                syokugyoumei = "盾士";
            }else  if(p.getScoreboardTags().contains("Job-Miner")){
                syokugyoumei = "採掘士";
            }
            return syokugyoumei;
        }
        if(identifier.equals("joblevel")){
            int level;
            level=1;
            for(int count=1;count<21;count++) {
                if (p.getScoreboardTags().contains("Lv"+count)) {
                    level = count;
                    return String.valueOf(level);
                }
            }
            return String.valueOf(1);
        }
        if(identifier.equals("emerald")){
            int emeraldkazu=0;
            for (ItemStack item : p.getInventory().getContents()) {
                if (item != null) {
                    if (item.getType() == Material.EMERALD) {
                        emeraldkazu += item.getAmount();
                    }
                }
            }
            p.sendMessage(String.valueOf(emeraldkazu));
            return String.valueOf(emeraldkazu);
        }
        if(identifier.equals("genzaihp")){
            return String.valueOf(Math.ceil(p.getHealth()));
        }
        // anything else someone types is invalid because we never defined %customplaceholder_<what they want a value for>%
        // we can just return null so the placeholder they specified is not replaced.
        return null;
    }
}