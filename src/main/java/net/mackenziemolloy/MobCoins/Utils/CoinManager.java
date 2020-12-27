package net.mackenziemolloy.MobCoins.Utils;

import net.mackenziemolloy.MobCoins.MobCoins;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.*;
import java.util.stream.Collectors;

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

    public static String getBalanceTop(Integer page) {

        MobCoins mobCoins = MobCoins.getInstance();
        int amountPerPage = mobCoins.configFile.getInt("options.balancetop_amount_per_page");


        HashMap<String, Double> manager = new HashMap<>();
        for(String playerUUID : mobCoins.dataFile.getKeys(false)) {
            if(mobCoins.dataFile.getDouble(playerUUID) != 0.0) {
                manager.put(playerUUID, mobCoins.dataFile.getDouble(playerUUID));
            }

            else {
                manager.put(playerUUID, 0.0);
            }
        }

        Map<String, Double> sortedMap = manager.entrySet().stream()
                .sorted(Map.Entry.<String, Double>comparingByValue().reversed())
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));

        StringBuilder balances = new StringBuilder();

        if(page > Math.round(sortedMap.size()/amountPerPage)+1) page = Math.round(sortedMap.size()/amountPerPage)+1;

        for(int i = (amountPerPage*page)-amountPerPage; i < (amountPerPage*page); i++) {

            if(sortedMap.keySet().size() <= i) continue;

            String playerUUID = sortedMap.keySet().toArray()[i].toString();
            String playerBalance = sortedMap.values().toArray()[i].toString();

            String playerName = "";

            if(Bukkit.getPlayer(UUID.fromString(playerUUID)) != null) {
                playerName = Bukkit.getPlayer(UUID.fromString(playerUUID)).getName();
            }
            else {
                playerName = Bukkit.getOfflinePlayer(UUID.fromString(playerUUID)).getName();
            }

            if(playerName == null) continue;

            String addToList = ChatColor.translateAlternateColorCodes('&', mobCoins.configFile.getString("messages.balance_top_format")
                    .replace("{placement}", String.valueOf(i+1)).replace("{player}", playerName).replace("{amount}", playerBalance)); //Bukkit.getPlayer(playerUUID).getName()
            balances.append(addToList).append("\n");

        }

        StringBuilder balanceTopMessage = new StringBuilder();


        for(String item : mobCoins.configFile.getStringList("messages.balance_top_list")) {

            String messageToAdd = ChatColor.translateAlternateColorCodes('&', item
                    .replace("{page}", String.valueOf(page)).replace("{maxpage}", String.valueOf(Math.round(sortedMap.size()/amountPerPage)+1)).replace("{balances}", balances.toString()) );
            balanceTopMessage.append(messageToAdd).append("\n");

        }


        return balanceTopMessage.toString();

    }

}
