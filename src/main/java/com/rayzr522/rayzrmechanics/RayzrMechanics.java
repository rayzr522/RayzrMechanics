package com.rayzr522.rayzrmechanics;

import org.bukkit.plugin.java.JavaPlugin;

/**
 * @author Rayzr
 */
public class RayzrMechanics extends JavaPlugin {

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(new MechanicsListener(this), this);
    }

}
