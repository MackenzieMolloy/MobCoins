package net.mackenziemolloy.mobcoins.listeners;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;

import net.mackenziemolloy.mobcoins.MobCoins;
import net.mackenziemolloy.mobcoins.api.MobCoin;

public class EntityDeathListener implements Listener {

	private final MobCoins mobCoins;
	private final MobCoin mobCoin;

	public EntityDeathListener(final MobCoins mobCoins) {
		this.mobCoins = mobCoins;
		this.mobCoin = this.mobCoins.getMobCoin();
	}

	@EventHandler
	public void MobDeathEvent(EntityDeathEvent e) {

		if (e.getEntity().getKiller() == null)
			return;

		if (e.getEntity().getKiller() instanceof Player) {

			Player killer = e.getEntity().getKiller();
			Entity killed = e.getEntity();

			if (mobCoins.configFile.getBoolean("options.debug")) {

				killer.sendMessage(killed.getType().toString().toUpperCase());

			}

			if (mobCoins.configFile.getConfigurationSection("options.mobs")
					.contains(killed.getType().toString().toUpperCase())) {

				this.mobCoin.addMobCoins(killer.getPlayer(), mobCoins.configFile
						.getDouble("options.mobs." + killed.getType().toString().toUpperCase() + ".amount"));

			}

		}

	}

}
