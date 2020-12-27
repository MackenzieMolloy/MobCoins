package net.mackenziemolloy.MobCoins.Events;

import net.mackenziemolloy.MobCoins.MobCoins;
import net.mackenziemolloy.MobCoins.Utils.CoinManager;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;

public class MobDeathEvent implements Listener {

    private final MobCoins mobCoins;

    public MobDeathEvent(final MobCoins mobCoins) {
        this.mobCoins = mobCoins;

    }

    @EventHandler
    public void MobDeathEvent(EntityDeathEvent e) {

        if(e.getEntity().getKiller() == null) return;

        if(e.getEntity().getKiller() instanceof Player ) {

            Player killer = e.getEntity().getKiller();
            Entity killed = e.getEntity();

            if(mobCoins.configFile.getBoolean("options.debug")) {

                killer.sendMessage(killed.getType().toString().toUpperCase());

            }

            if(mobCoins.configFile.getConfigurationSection("options.mobs").contains(killed.getType().toString().toUpperCase())) {

                CoinManager.addMobCoins(killer.getPlayer(), mobCoins.configFile.getDouble("options.mobs." + killed.getType().toString().toUpperCase() + ".amount"));

            }

        }

    }

}