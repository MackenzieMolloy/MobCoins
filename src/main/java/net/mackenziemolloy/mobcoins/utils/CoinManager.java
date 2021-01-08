package net.mackenziemolloy.mobcoins.utils;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import org.apache.commons.lang.math.NumberUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import net.mackenziemolloy.mobcoins.MobCoins;
import net.mackenziemolloy.mobcoins.api.MobCoin;

public class CoinManager implements MobCoin {

	private final MobCoins mobCoins;

	public CoinManager(final MobCoins mobCoins) {
		this.mobCoins = mobCoins;

	}

	public void removeMobCoins(OfflinePlayer player, double amount) {

		MobCoins mobCoins = MobCoins.getInstance();

		if (mobCoins.dataFile.get(player.getUniqueId().toString()) != null) {

			if (mobCoins.dataFile.getDouble(player.getUniqueId().toString()) >= amount
					|| mobCoins.configFile.getBoolean("options.negative_balance")) {

				mobCoins.dataFile.set(player.getUniqueId().toString(),
						mobCoins.dataFile.getDouble(player.getUniqueId().toString()) - amount);

			}

			else {

				mobCoins.dataFile.set(player.getUniqueId().toString(), 0.0);

			}

		}

		else {

			mobCoins.dataFile.set(player.getUniqueId().toString(), 0.0);

		}

		mobCoins.saveFiles(false, true);

	}

	public void addMobCoins(OfflinePlayer player, double amount) {

		MobCoins mobCoins = MobCoins.getInstance();

		if (mobCoins.dataFile.get(player.getUniqueId().toString()) != null) {

			mobCoins.dataFile.set(player.getUniqueId().toString(),
					mobCoins.dataFile.getDouble(player.getUniqueId().toString()) + amount);

		}

		else {

			mobCoins.dataFile.set(player.getUniqueId().toString(), amount);

		}

		mobCoins.saveFiles(false, true);

	}

	public void setMobCoins(Player setter, String receiverName, String amountString, boolean silent) {

		MobCoins mobCoins = MobCoins.getInstance();

		if (Bukkit.getPlayerExact(receiverName) == null) {

			String playerNotFound = ChatColor.translateAlternateColorCodes('&',
					mobCoins.configFile.getString("messages.player_not_found").replace("{player}", receiverName));
			setter.sendMessage(playerNotFound);

		}

		else if (!NumberUtils.isNumber(amountString)) {

			String notANumber = ChatColor.translateAlternateColorCodes('&',
					mobCoins.configFile.getString("messages.not_a_number").replace("{number}", amountString));
			setter.sendMessage(notANumber);

		}

		else {

			Player addTo = Bukkit.getPlayerExact(receiverName);
			double amountToAdd = Double.valueOf(amountString);

			if (mobCoins.configFile.getBoolean("options.negative_balance")) {

				mobCoins.dataFile.set(addTo.getUniqueId().toString(), amountToAdd);
				mobCoins.saveFiles(false, true);
			}

			else {

				mobCoins.dataFile.set(addTo.getUniqueId().toString(), Math.abs(amountToAdd));
				mobCoins.saveFiles(false, true);

			}

			String setMobCoins = ChatColor.translateAlternateColorCodes('&',
					mobCoins.configFile.getString("messages.balance_set_setted").replace("{player}", addTo.getName())
							.replace("{amount}", amountString));
			setter.sendMessage(setMobCoins);

		}

	}

	public void setMobCoins(ConsoleCommandSender setter, String receiverName, String amountString, boolean silent) {

		MobCoins mobCoins = MobCoins.getInstance();

		if (Bukkit.getPlayerExact(receiverName) == null) {

			String playerNotFound = ChatColor.translateAlternateColorCodes('&',
					mobCoins.configFile.getString("messages.player_not_found").replace("{player}", receiverName));
			setter.sendMessage(playerNotFound);

		}

		else if (!NumberUtils.isNumber(amountString)) {

			String notANumber = ChatColor.translateAlternateColorCodes('&',
					mobCoins.configFile.getString("messages.not_a_number").replace("{number}", amountString));
			setter.sendMessage(notANumber);

		}

		else {

			Player addTo = Bukkit.getPlayerExact(receiverName);
			double amountToAdd = Double.valueOf(amountString);

			if (mobCoins.configFile.getBoolean("options.negative_balance")) {

				mobCoins.dataFile.set(addTo.getUniqueId().toString(), amountToAdd);
				mobCoins.saveFiles(false, true);
			}

			else {

				mobCoins.dataFile.set(addTo.getUniqueId().toString(), Math.abs(amountToAdd));
				mobCoins.saveFiles(false, true);

			}

			String setMobCoins = ChatColor.translateAlternateColorCodes('&',
					mobCoins.configFile.getString("messages.balance_set_setted").replace("{player}", addTo.getName())
							.replace("{amount}", amountString));
			setter.sendMessage(setMobCoins);

		}

	}

	public void transferMobCoins(Player sender, String receiverName, String amountString, boolean silent) {

		MobCoins mobCoins = MobCoins.getInstance();

		if (Bukkit.getPlayerExact(receiverName) == null) {

			if (!silent) {
				String playerNotFound = ChatColor.translateAlternateColorCodes('&',
						mobCoins.configFile.getString("messages.player_not_found").replace("{player}", receiverName));
				sender.sendMessage(playerNotFound);
			}

		}

		else if (sender.getName().equalsIgnoreCase(receiverName)
				&& !mobCoins.configFile.getBoolean("options.can_pay_self")) {

			if (!silent) {
				String cantPaySelf = ChatColor.translateAlternateColorCodes('&',
						mobCoins.configFile.getString("messages.balance_pay_self"));
				sender.sendMessage(cantPaySelf);
			}

		}

		else if (!NumberUtils.isNumber(amountString)) {

			if (!silent) {
				String notANumber = ChatColor.translateAlternateColorCodes('&',
						mobCoins.configFile.getString("messages.not_a_number").replace("{number}", amountString));
				sender.sendMessage(notANumber);
			}
		}

		else if (Double.valueOf(amountString) < mobCoins.configFile.getDouble("options.minimum_pay_amount")) {

			if (!silent) {

				String cantPayLessThan = ChatColor.translateAlternateColorCodes('&',
						mobCoins.configFile.getString("messages.balance_pay_less_than"));
				sender.sendMessage(cantPayLessThan);

			}

		}

		else if (mobCoins.dataFile.getDouble(sender.getUniqueId().toString()) < Double.valueOf(amountString)) {

			if (silent == false) {
				String insufficientFunds = ChatColor.translateAlternateColorCodes('&',
						mobCoins.configFile.getString("messages.insufficient_funds"));
				sender.sendMessage(insufficientFunds);
			}

		}

		else {

			Player receiver = Bukkit.getPlayer(receiverName);
			double amount = Double.valueOf(amountString);

			this.addMobCoins(receiver, amount);
			this.removeMobCoins(sender, amount);

			if (!silent) {

				String mobCoinsReceived = ChatColor.translateAlternateColorCodes('&',
						mobCoins.configFile.getString("messages.balance_pay_paid_received")
								.replace("{amount}", String.valueOf(amount)).replace("{player}", sender.getName()));
				receiver.sendMessage(mobCoinsReceived);

				String mobCoinsSent = ChatColor.translateAlternateColorCodes('&',
						mobCoins.configFile.getString("messages.balance_pay_paid").replace("{player}", receiverName)
								.replace("{amount}", String.valueOf(amount)));
				sender.sendMessage(mobCoinsSent);

			}

		}

	}

	@Override
	public double getBalance(OfflinePlayer player) {
		MobCoins mobCoins = MobCoins.getInstance();
		if (mobCoins.dataFile.get(player.getUniqueId().toString()) != null)
			return mobCoins.dataFile.getDouble(player.getUniqueId().toString());
		return 0.0;
	}

	@Override
	public String getBalanceTop(int page) {

		if (page < 1)
			page = 1;

		MobCoins mobCoins = MobCoins.getInstance();
		int amountPerPage = mobCoins.configFile.getInt("options.balancetop_amount_per_page");

		HashMap<String, Double> manager = new HashMap<>();
		for (String playerUUID : mobCoins.dataFile.getKeys(false)) {
			if (mobCoins.dataFile.getDouble(playerUUID) != 0.0) {
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

		if (page > Math.round(sortedMap.size() / amountPerPage) + 1)
			page = Math.round(sortedMap.size() / amountPerPage) + 1;

		for (int i = (amountPerPage * page) - amountPerPage; i < (amountPerPage * page); i++) {

			if (sortedMap.keySet().size() <= i)
				continue;

			String playerUUID = sortedMap.keySet().toArray()[i].toString();
			String playerBalance = sortedMap.values().toArray()[i].toString();

			String playerName;

			if (Bukkit.getPlayer(UUID.fromString(playerUUID)) != null) {
				playerName = Bukkit.getPlayer(UUID.fromString(playerUUID)).getName();
			} else {
				playerName = Bukkit.getOfflinePlayer(UUID.fromString(playerUUID)).getName();
			}

			if (playerName == null)
				continue;

			String addToList = ChatColor.translateAlternateColorCodes('&',
					mobCoins.configFile.getString("messages.balance_top_format")
							.replace("{placement}", String.valueOf(i + 1)).replace("{player}", playerName)
							.replace("{amount}", playerBalance)); // Bukkit.getPlayer(playerUUID).getName()
			balances.append(addToList).append("\n");

		}

		StringBuilder balanceTopMessage = new StringBuilder();

		for (String item : mobCoins.configFile.getStringList("messages.balance_top_list")) {

			String messageToAdd = ChatColor.translateAlternateColorCodes('&',
					item.replace("{page}", String.valueOf(page))
							.replace("{maxpage}", String.valueOf(Math.round(sortedMap.size() / amountPerPage) + 1))
							.replace("{balances}", balances.toString()));
			balanceTopMessage.append(messageToAdd).append("\n");

		}

		return balanceTopMessage.toString();

	}

}
