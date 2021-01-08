package net.mackenziemolloy.mobcoins.api;

import org.bukkit.OfflinePlayer;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

public interface MobCoin {

	public void removeMobCoins(OfflinePlayer offlinePlayer, double value);
	
	public void addMobCoins(OfflinePlayer offlinePlayer, double value);
	
	public double getBalance(OfflinePlayer player);

	public String getBalanceTop(int i);

	public void setMobCoins(ConsoleCommandSender console, String string, String string2, boolean b);

	public void setMobCoins(Player sender, String string, String string2, boolean b);

	public void transferMobCoins(Player sender, String string, String string2, boolean b);
	
}
