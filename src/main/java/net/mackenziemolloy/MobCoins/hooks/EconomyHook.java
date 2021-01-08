package net.mackenziemolloy.mobcoins.hooks;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.bukkit.entity.Player;

import net.brcdev.shopgui.provider.economy.EconomyProvider;
import net.mackenziemolloy.mobcoins.MobCoins;
import net.mackenziemolloy.mobcoins.utils.CoinManager;

public class EconomyHook extends EconomyProvider {

    MobCoins mobCoins = MobCoins.getInstance();

    private Map<UUID, Double> playerBalances;

    public EconomyHook() {

        // (Optional, default ones are empty strings)
        // Override fields from superclass to set currency prefix & suffix
        this.currencyPrefix = "";
        this.currencySuffix = " MobCoins";

        this.playerBalances = new HashMap<>();
    }

    @Override
    public String getName() {
        return "MobCoins";
    }


    @Override
    public double getBalance(Player player) {


        if(mobCoins.dataFile.get(player.getUniqueId().toString()) != null) {

            return mobCoins.dataFile.getDouble(player.getUniqueId().toString());

        }

        else {

            return 0.0;

        }

    }

    @Override
    public void deposit(Player player, double amount) {

        CoinManager.addMobCoins(player, amount);


    }

    @Override
    public void withdraw(Player player, double amount) {

        CoinManager.removeMobCoins(player, amount);

    }

}
