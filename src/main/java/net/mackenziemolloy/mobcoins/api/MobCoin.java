package net.mackenziemolloy.mobcoins.api;

import org.bukkit.OfflinePlayer;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

public interface MobCoin {

	void removeMobCoins(OfflinePlayer offlinePlayer, double value);
	
	void addMobCoins(OfflinePlayer offlinePlayer, double value);
	
	double getBalance(OfflinePlayer player);

	String getBalanceTop(int i);

	void setMobCoins(ConsoleCommandSender console, String string, String string2, boolean b);

	void setMobCoins(Player sender, String string, String string2, boolean b);

	void transferMobCoins(Player sender, String string, String string2, boolean b);
	
}
