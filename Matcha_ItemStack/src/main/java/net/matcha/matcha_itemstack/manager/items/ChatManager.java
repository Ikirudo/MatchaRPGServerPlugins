package net.matcha.matcha_itemstack.manager.items;

import java.util.HashMap;
import net.matcha.matcha_itemstack.Matcha_ItemStack;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class ChatManager {
    private Matcha_ItemStack plugin = (Matcha_ItemStack)Matcha_ItemStack.getPlugin(Matcha_ItemStack.class);

    public void setItem(AsyncPlayerChatEvent e)
    {
        e.setCancelled(true);
        Player player = e.getPlayer();
        String setting = (String)this.plugin.createManager.playersetting.get(e.getPlayer());
        if (e.getMessage().equals("cancel"))
        {
            this.plugin.createManager.playersetting.remove(player);
            this.plugin.createManager.openC(player);
            return;
        }
        if (setting.equals("アイテムID"))
        {
            String name = (String)this.plugin.createManager.playertool.get(player);
            this.plugin.createManager.material.put(name, Integer.valueOf(1));
            if (!e.getMessage().equals("null"))
            {
                if (!isInt(e.getMessage()))
                {
                    player.sendMessage("§eその数字を入力してください。");
                    return;
                }
                this.plugin.createManager.material.put(name, Integer.valueOf(Integer.parseInt(e.getMessage())));
            }
            this.plugin.createManager.playersetting.remove(player);
            this.plugin.createManager.openC(player);
            return;
        }
        if (setting.equals("アイテムメタデータ"))
        {
            String name = (String)this.plugin.createManager.playertool.get(player);
            this.plugin.createManager.data.put(name, Integer.valueOf(0));
            if (!e.getMessage().equals("null"))
            {
                if (!isInt(e.getMessage()))
                {
                    player.sendMessage("§eその数字を入力してください。");
                    return;
                }
                this.plugin.createManager.data.put(name, Integer.valueOf(Integer.parseInt(e.getMessage())));
            }
            this.plugin.createManager.playersetting.remove(player);
            this.plugin.createManager.openC(player);
            return;
        }
        if (setting.equals("レベル"))
        {
            String name = (String)this.plugin.createManager.playertool.get(player);
            this.plugin.createManager.level.put(name, Integer.valueOf(0));
            if (!e.getMessage().equals("null"))
            {
                if (!isInt(e.getMessage()))
                {
                    player.sendMessage("§eその数字を入力してください。");
                    return;
                }
                this.plugin.createManager.level.put(name, Integer.valueOf(Integer.parseInt(e.getMessage())));
            }
            this.plugin.createManager.playersetting.remove(player);
            this.plugin.createManager.openC(player);
            return;
        }
        if (setting.equals("攻撃力（武器用）"))
        {
            String name = (String)this.plugin.createManager.playertool.get(player);
            this.plugin.createManager.damage.put(name, Long.valueOf(0L));
            if (!e.getMessage().equals("null"))
            {
                if (!isLong(e.getMessage()))
                {
                    player.sendMessage("§eその数字を入力してください。");
                    return;
                }
                this.plugin.createManager.damage.put(name, Long.valueOf(Long.parseLong(e.getMessage())));
            }
            this.plugin.createManager.playersetting.remove(player);
            this.plugin.createManager.openC(player);
            return;
        }
        if (setting.equals("防御力（防具用）"))
        {
            String name = (String)this.plugin.createManager.playertool.get(player);
            this.plugin.createManager.defense.put(name, Long.valueOf(0L));
            if (!e.getMessage().equals("null"))
            {
                if (!isLong(e.getMessage()))
                {
                    player.sendMessage("§eその数字を入力してください。");
                    return;
                }
                this.plugin.createManager.defense.put(name, Long.valueOf(Long.parseLong(e.getMessage())));
            }
            this.plugin.createManager.playersetting.remove(player);
            this.plugin.createManager.openC(player);
            return;
        }
        if (setting.equals("耐久力・耐久値"))
        {
            String name = (String)this.plugin.createManager.playertool.get(player);
            this.plugin.createManager.durability.put(name, Long.valueOf(0L));
            if (!e.getMessage().equals("null"))
            {
                if (!isLong(e.getMessage()))
                {
                    player.sendMessage("§eその数字を入力してください。");
                    return;
                }
                this.plugin.createManager.durability.put(name, Long.valueOf(Long.parseLong(e.getMessage())));
            }
            this.plugin.createManager.playersetting.remove(player);
            this.plugin.createManager.openC(player);
            return;
        }
        if (setting.equals("（重さ）"))
        {
            String name = (String)this.plugin.createManager.playertool.get(player);
            this.plugin.createManager.weight.put(name, Long.valueOf(0L));
            if (!e.getMessage().equals("null"))
            {
                if (!isLong(e.getMessage()))
                {
                    player.sendMessage("§eその数字を入力してください。");
                    return;
                }
                this.plugin.createManager.weight.put(name, Long.valueOf(Long.parseLong(e.getMessage())));
            }
            this.plugin.createManager.playersetting.remove(player);
            this.plugin.createManager.openC(player);
            return;
        }
        if (setting.equals("STR"))
        {
            String name = (String)this.plugin.createManager.playertool.get(player);
            this.plugin.createManager.Estrong.put(name, Long.valueOf(0L));
            if (!e.getMessage().equals("null"))
            {
                if (!isLong(e.getMessage()))
                {
                    player.sendMessage("§eその数字を入力してください。");
                    return;
                }
                this.plugin.createManager.Estrong.put(name, Long.valueOf(Long.parseLong(e.getMessage())));
            }
            this.plugin.createManager.playersetting.remove(player);
            this.plugin.createManager.openC(player);
            return;
        }
        if (setting.equals("DEF"))
        {
            String name = (String)this.plugin.createManager.playertool.get(player);
            this.plugin.createManager.Edefense.put(name, Long.valueOf(0L));
            if (!e.getMessage().equals("null"))
            {
                if (!isLong(e.getMessage()))
                {
                    player.sendMessage("§eその数字を入力してください。");
                    return;
                }
                this.plugin.createManager.Edefense.put(name, Long.valueOf(Long.parseLong(e.getMessage())));
            }
            this.plugin.createManager.playersetting.remove(player);
            this.plugin.createManager.openC(player);
            return;
        }
        if (setting.equals("AGI"))
        {
            String name = (String)this.plugin.createManager.playertool.get(player);
            this.plugin.createManager.Espeed.put(name, Long.valueOf(0L));
            if (!e.getMessage().equals("null"))
            {
                if (!isLong(e.getMessage()))
                {
                    player.sendMessage("§eその数字を入力してください。");
                    return;
                }
                this.plugin.createManager.Espeed.put(name, Long.valueOf(Long.parseLong(e.getMessage())));
            }
            this.plugin.createManager.playersetting.remove(player);
            this.plugin.createManager.openC(player);
            return;
        }
        if (setting.equals("アイテム名"))
        {
            String name = (String)this.plugin.createManager.playertool.get(player);
            this.plugin.createManager.display.put(name, null);
            if (!e.getMessage().equals("null")) {
                this.plugin.createManager.display.put(name, e.getMessage().replaceAll("&", "§"));
            }
            this.plugin.createManager.playersetting.remove(player);
            this.plugin.createManager.openC(player);
            return;
        }
        if(setting.equals("適正職業")){
            String name = (String)this.plugin.createManager.playertool.get(player);
            this.plugin.createManager.tekiseisyokugyou.put(name, null);
            if (!e.getMessage().equals("null")) {
                this.plugin.createManager.tekiseisyokugyou.put(name, e.getMessage().replaceAll("&", "§"));
            }
            this.plugin.createManager.playersetting.remove(player);
            this.plugin.createManager.openC(player);
            return;
        }
        if (setting.equals("詳細文１"))
        {
            String name = (String)this.plugin.createManager.playertool.get(player);
            this.plugin.createManager.one.put(name, null);
            if (!e.getMessage().equals("null")) {
                this.plugin.createManager.one.put(name, e.getMessage().replaceAll("&", "§"));
            }
            this.plugin.createManager.playersetting.remove(player);
            this.plugin.createManager.openC(player);
            return;
        }
        if (setting.equals("詳細文２"))
        {
            String name = (String)this.plugin.createManager.playertool.get(player);
            this.plugin.createManager.two.put(name, null);
            if (!e.getMessage().equals("null")) {
                this.plugin.createManager.two.put(name, e.getMessage().replaceAll("&", "§"));
            }
            this.plugin.createManager.playersetting.remove(player);
            this.plugin.createManager.openC(player);
            return;
        }
        if (setting.equals("詳細文３"))
        {
            String name = (String)this.plugin.createManager.playertool.get(player);
            this.plugin.createManager.three.put(name, null);
            if (!e.getMessage().equals("null")) {
                this.plugin.createManager.three.put(name, e.getMessage().replaceAll("&", "§"));
            }
            this.plugin.createManager.playersetting.remove(player);
            this.plugin.createManager.openC(player);
            return;
        }
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

    private boolean isLong(String s)
    {
        try
        {
            Long.parseLong(s);
        }
        catch (NumberFormatException e)
        {
            return false;
        }
        return true;
    }
}

