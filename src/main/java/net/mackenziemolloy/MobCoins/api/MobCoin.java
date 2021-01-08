package net.mackenziemolloy.mobcoins.api;

import org.bukkit.OfflinePlayer;

public interface MobCoin {

	public void removeMobCoins(OfflinePlayer offlinePlayer, double value);
	
	public void addMobCoins(OfflinePlayer offlinePlayer, double value);
	
	public double getBalance(OfflinePlayer player);
	
}
