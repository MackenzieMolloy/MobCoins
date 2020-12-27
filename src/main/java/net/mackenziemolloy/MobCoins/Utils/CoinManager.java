package net.mackenziemolloy.MobCoins.Utils;

import net.mackenziemolloy.MobCoins.MobCoins;
import org.bukkit.entity.Player;

public class CoinManager {

    private final MobCoins mobCoins;

    public CoinManager(final MobCoins mobCoins) {
        this.mobCoins = mobCoins;

    }

    public static void removeMobCoins(Player player, Double amount) {

        MobCoins mobCoins = MobCoins.getInstance();


        if(mobCoins.dataFile.get(player.getUniqueId().toString()) != null) {

            mobCoins.dataFile.set(player.getUniqueId().toString(), mobCoins.dataFile.getDouble(player.getUniqueId().toString()) - amount);

        }

        else {

            mobCoins.dataFile.set(player.getUniqueId().toString(), 0.0);

        }

        mobCoins.saveFiles(false, true);

    }

    public static void addMobCoins(Player player, Double amount) {

        MobCoins mobCoins = MobCoins.getInstance();


        if(mobCoins.dataFile.get(player.getUniqueId().toString()) != null) {

            mobCoins.dataFile.set(player.getUniqueId().toString(), mobCoins.dataFile.getDouble(player.getUniqueId().toString()) + amount);

        }

        else {

            mobCoins.dataFile.set(player.getUniqueId().toString(), amount);

        }

        mobCoins.saveFiles(false, true);

    }

    public static void setMobCoins(Player player, Double amount) {

        MobCoins mobCoins = MobCoins.getInstance();

        mobCoins.dataFile.set(player.getUniqueId().toString(), amount);
        mobCoins.saveFiles(false, true);

    }

    public static double getBalance(Player player) {

        MobCoins mobCoins = MobCoins.getInstance();

        if(mobCoins.dataFile.get(player.getUniqueId().toString()) != null) {

            return mobCoins.dataFile.getDouble(player.getUniqueId().toString());

        }

        else {
            return 0.0;
        }
    }

}
