package net.mackenziemolloy.mobcoins.hooks;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.bukkit.entity.Player;

import net.brcdev.shopgui.provider.economy.EconomyProvider;
import net.mackenziemolloy.mobcoins.MobCoins;
import net.mackenziemolloy.mobcoins.api.MobCoin;
import net.mackenziemolloy.mobcoins.utils.CoinManager;

public class EconomyHook extends EconomyProvider {

	private final MobCoins mobCoins = MobCoins.getInstance();
	private final MobCoin mobCoin;

	private Map<UUID, Double> playerBalances;

	public EconomyHook() {

		// (Optional, default ones are empty strings)
		// Override fields from superclass to set currency prefix & suffix
		this.currencyPrefix = "";
		this.currencySuffix = " MobCoins";

		this.playerBalances = new HashMap<>();
		this.mobCoin = this.mobCoins.getMobCoin();
	}

	@Override
	public String getName() {
		return "MobCoins";
	}

	@Override
	public double getBalance(Player player) {

		if (mobCoins.dataFile.get(player.getUniqueId().toString()) != null) {

			return mobCoins.dataFile.getDouble(player.getUniqueId().toString());

		}

		else {

			return 0.0;

		}

	}

	@Override
	public void deposit(Player player, double amount) {

		this.mobCoin.addMobCoins(player, amount);

	}

	@Override
	public void withdraw(Player player, double amount) {

		this.mobCoin.removeMobCoins(player, amount);

	}

}
