package net.matcha.matcha_levelsystem.manager;

import net.matcha.matcha_levelsystem.Matcha_LevelSystem;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;

public class Stat {
    private Matcha_LevelSystem plugin = (Matcha_LevelSystem)Matcha_LevelSystem.getPlugin(Matcha_LevelSystem.class);

    public void setSP(Player p, long stats)
    {
        this.plugin.playerYml.sp.put(p.getName(), Long.valueOf(stats));
        this.plugin.scoreBoardManager.sendScoreBoard(p);
    }

    public Long getSP(Player p)
    {
        return (Long)this.plugin.playerYml.sp.get(p.getName());
    }

    public void setMaxHealth(Player p, double stats)
    {
        this.plugin.playerYml.LEVEL_MAX_HEALTH.put(p.getName(), Double.valueOf(stats));
    }

    public double getMaxHealth(Player p)
    {
        return ((Double)this.plugin.playerYml.LEVEL_MAX_HEALTH.get(p.getName())).doubleValue();
    }

    public void setHealth(Player p, int stats)
    {
        this.plugin.playerYml.LEVEL_HEALTH.put(p.getName(), Integer.valueOf(stats));
    }

    public int getHealth(Player p)
    {
        return ((Integer)this.plugin.playerYml.LEVEL_HEALTH.get(p.getName())).intValue();
    }

    public void setDamage(Player p, int stats)
    {
        this.plugin.playerYml.LEVEL_DAMAGE.put(p.getName(), Integer.valueOf(stats));
    }

    public int getDamage(Player p)
    {
        return ((Integer)this.plugin.playerYml.LEVEL_DAMAGE.get(p.getName())).intValue();
    }

    public void setDefense(Player p, int stats)
    {
        this.plugin.playerYml.LEVEL_DEFENSE.put(p.getName(), Integer.valueOf(stats));
    }

    public int getDefense(Player p)
    {
        return ((Integer)this.plugin.playerYml.LEVEL_DEFENSE.get(p.getName())).intValue();
    }

    public void setSpeed(Player p, int stats)
    {
        this.plugin.playerYml.LEVEL_SPEED.put(p.getName(), Integer.valueOf(stats));
    }

    public int getSpeed(Player p)
    {
        return ((Integer)this.plugin.playerYml.LEVEL_SPEED.get(p.getName())).intValue();
    }

    public void setStatsHealth(Player p, int stats)
    {
        this.plugin.playerYml.STATS_HEALTH.put(p.getName(), Integer.valueOf(stats));
    }

    public int getStatsHealth(Player p)
    {
        return ((Integer)this.plugin.playerYml.STATS_HEALTH.get(p.getName())).intValue();
    }

    public void setStatsDamage(Player p, int stats)
    {
        this.plugin.playerYml.STATS_DAMAGE.put(p.getName(), Integer.valueOf(stats));
    }

    public int getStatsDamage(Player p)
    {
        return ((Integer)this.plugin.playerYml.STATS_DAMAGE.get(p.getName())).intValue();
    }

    public void setStatsDefense(Player p, int stats)
    {
        this.plugin.playerYml.STATS_DEFENSE.put(p.getName(), Integer.valueOf(stats));
    }

    public int getStatsDefense(Player p)
    {
        return ((Integer)this.plugin.playerYml.STATS_DEFENSE.get(p.getName())).intValue();
    }

    public void setStatsSpeed(Player p, int stats)
    {
        this.plugin.playerYml.STATS_SPEED.put(p.getName(), Integer.valueOf(stats));
    }

    public int getStatsSpeed(Player p)
    {
        return ((Integer)this.plugin.playerYml.STATS_SPEED.get(p.getName())).intValue();
    }

    private void setLevelStats(Player p)
    {
        //deprecated
    }

    public void setStats(Player p) {
        // setLevelStats(p);
        // deprecated
    }
}
