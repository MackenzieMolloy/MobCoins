package net.mackenziemolloy.MobCoins;

import net.mackenziemolloy.MobCoins.Utils.CoinManager;
import org.apache.commons.lang.math.NumberUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import java.util.concurrent.CompletableFuture;

public class Commands implements CommandExecutor {

    private final MobCoins mobCoins;

    public Commands(final MobCoins mobCoins) {
        this.mobCoins = mobCoins;

        mobCoins.getCommand("mobcoins").setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {

        if(commandSender instanceof Player) {

            Player sender = (Player) commandSender;

            if(args.length <= 0) {

                if (sender.hasPermission("mobcoins.help.staff")) {

                    StringBuilder helpStaffMessage = new StringBuilder();


                    for(String item : mobCoins.configFile.getStringList("messages.help_staff")) {

                        String messageToAdd = ChatColor.translateAlternateColorCodes('&', item);
                        helpStaffMessage.append(messageToAdd).append("\n");

                    }

                    sender.sendMessage(helpStaffMessage.toString());

                }

                else if(sender.hasPermission("mobcoins.help")) {

                    StringBuilder helpMessage = new StringBuilder();


                    for(String item : mobCoins.configFile.getStringList("messages.help")) {

                        String messageToAdd = ChatColor.translateAlternateColorCodes('&', item);
                        helpMessage.append(messageToAdd).append("\n");

                    }

                    sender.sendMessage(helpMessage.toString());

                }

                else {

                    String noPermission = ChatColor.translateAlternateColorCodes('&', mobCoins.configFile.getString("messages.no_permission"));
                    sender.sendMessage(noPermission);

                }

            }

            else if(args[0].equalsIgnoreCase("balance") || args[0].equalsIgnoreCase("bal")) {

                if(sender.hasPermission("mobcoins.commands.balance")) {

                    if (sender.hasPermission("mobcoins.admin.balance")) {

                        if (args.length >= 2) {

                            if(Bukkit.getPlayerExact(args[1]) != null) {

                                Player selected = Bukkit.getPlayerExact(args[1]);
                                String selectedBalance = String.valueOf(CoinManager.getBalance(selected));

                                String balanceOther = ChatColor.translateAlternateColorCodes('&', mobCoins.configFile.getString("messages.balance_other")
                                        .replace("{player}", selected.getName()).replace("{balance}", selectedBalance));
                                sender.sendMessage(balanceOther);

                            }

                            else {

                                String playerNotFound = ChatColor.translateAlternateColorCodes('&', mobCoins.configFile.getString("messages.player_not_found")
                                        .replace("{player}", args[1]));
                                sender.sendMessage(playerNotFound);

                            }

                        }

                        else {

                            String balance = String.valueOf(CoinManager.getBalance(sender));

                            String balanceMessage = ChatColor.translateAlternateColorCodes('&', mobCoins.configFile.getString("messages.balance").replace("{balance}", balance));
                            sender.sendMessage(balanceMessage);
                        }

                    }

                    else {

                        String balance = String.valueOf(CoinManager.getBalance(sender));

                        String balanceMessage = ChatColor.translateAlternateColorCodes('&', mobCoins.configFile.getString("messages.balance").replace("{balance}", balance));
                        sender.sendMessage(balanceMessage);
                    }

                }

                else {

                    String noPermission = ChatColor.translateAlternateColorCodes('&', mobCoins.configFile.getString("messages.no_permission"));
                    sender.sendMessage(noPermission);

                }
            }

            else if(args[0].equalsIgnoreCase("add")) {

                if(sender.hasPermission("mobcoins.admin.add")) {

                    if(args.length >= 3) {

                        if(Bukkit.getPlayerExact(args[1]) == null) {

                            String playerNotFound = ChatColor.translateAlternateColorCodes('&', mobCoins.configFile.getString("messages.player_not_found")
                                    .replace("{player}", args[1]));
                            sender.sendMessage(playerNotFound);

                        }

                        else if(!NumberUtils.isNumber(args[2])) {

                            String notANumber = ChatColor.translateAlternateColorCodes('&', mobCoins.configFile.getString("messages.not_a_number")
                                    .replace("{number}", args[2]));
                            sender.sendMessage(notANumber);

                        }

                        else {

                            Player addTo = Bukkit.getPlayerExact(args[1]);
                            CoinManager.addMobCoins(addTo, Double.valueOf(args[2]));

                            String addedMobCoins = ChatColor.translateAlternateColorCodes('&', mobCoins.configFile.getString("messages.balance_add_added")
                                    .replace("{player}", addTo.getName()).replace("{amount}", args[2]));
                            sender.sendMessage(addedMobCoins);

                        }

                    }

                    else {

                        String incorrectUsage = ChatColor.translateAlternateColorCodes('&', mobCoins.configFile.getString("messages.balance_add_usage"));
                        sender.sendMessage(incorrectUsage);

                    }

                }

                else {

                    String noPermission = ChatColor.translateAlternateColorCodes('&', mobCoins.configFile.getString("messages.no_permission"));
                    sender.sendMessage(noPermission);

                }

            }

            else if(args[0].equalsIgnoreCase("set")) {

                if(sender.hasPermission("mobcoins.admin.set")) {

                    if(args.length >= 3) {

                        if(Bukkit.getPlayerExact(args[1]) == null) {

                            String playerNotFound = ChatColor.translateAlternateColorCodes('&', mobCoins.configFile.getString("messages.player_not_found")
                                    .replace("{player}", args[1]));
                            sender.sendMessage(playerNotFound);

                        }

                        else if(!NumberUtils.isNumber(args[2])) {

                            String notANumber = ChatColor.translateAlternateColorCodes('&', mobCoins.configFile.getString("messages.not_a_number")
                                    .replace("{number}", args[2]));
                            sender.sendMessage(notANumber);

                        }

                        else {

                            Player addTo = Bukkit.getPlayerExact(args[1]);
                            CoinManager.setMobCoins(addTo, Double.valueOf(args[2]));

                            String setMobCoins = ChatColor.translateAlternateColorCodes('&', mobCoins.configFile.getString("messages.balance_set_setted")
                                    .replace("{player}", addTo.getName()).replace("{amount}", args[2]));
                            sender.sendMessage(setMobCoins);

                        }

                    }

                    else {

                        String incorrectUsage = ChatColor.translateAlternateColorCodes('&', mobCoins.configFile.getString("messages.balance_set_usage"));
                        sender.sendMessage(incorrectUsage);

                    }


                }

                else {

                    String noPermission = ChatColor.translateAlternateColorCodes('&', mobCoins.configFile.getString("messages.no_permission"));
                    sender.sendMessage(noPermission);

                }

            }


            else if(args[0].equalsIgnoreCase("remove") || args[0].equalsIgnoreCase("rm") || args[0].equalsIgnoreCase("rem")) {

                if(sender.hasPermission("mobcoins.admin.remove")) {

                    if (args.length >= 3) {

                        if (Bukkit.getPlayerExact(args[1]) == null) {

                            String playerNotFound = ChatColor.translateAlternateColorCodes('&', mobCoins.configFile.getString("messages.player_not_found")
                                    .replace("{player}", args[1]));
                            sender.sendMessage(playerNotFound);

                        } else if (!NumberUtils.isNumber(args[2])) {

                            String notANumber = ChatColor.translateAlternateColorCodes('&', mobCoins.configFile.getString("messages.not_a_number")
                                    .replace("{number}", args[2]));
                            sender.sendMessage(notANumber);

                        } else {

                            Player addTo = Bukkit.getPlayerExact(args[1]);
                            CoinManager.removeMobCoins(addTo, Double.valueOf(args[2]));

                            String setMobCoins = ChatColor.translateAlternateColorCodes('&', mobCoins.configFile.getString("messages.balance_remove_removed")
                                    .replace("{player}", addTo.getName()).replace("{amount}", args[2]));
                            sender.sendMessage(setMobCoins);

                        }

                    } else {

                        String incorrectUsage = ChatColor.translateAlternateColorCodes('&', mobCoins.configFile.getString("messages.balance_remove_usage"));
                        sender.sendMessage(incorrectUsage);

                    }

                }

                else {

                    String noPermission = ChatColor.translateAlternateColorCodes('&', mobCoins.configFile.getString("messages.no_permission"));
                    sender.sendMessage(noPermission);

                }

            }


            else if(args[0].equalsIgnoreCase("reload") || args[0].equalsIgnoreCase("rl")) {

                if(sender.hasPermission("mobcoins.admin.reload")) {

                    CompletableFuture<Void> future = CompletableFuture.runAsync(mobCoins::generateFiles);
                    future.whenComplete((success, error) -> {
                        if(error != null) {
                            sender.sendMessage("An error occurred, check the console!");
                            error.printStackTrace();
                        } else {
                            String configReloadedMsg = ChatColor.translateAlternateColorCodes('&', mobCoins.configFile.getString("messages.reloaded_config"));
                            sender.sendMessage(configReloadedMsg);
                        }
                    });

                }

                else {

                    String noPermission = ChatColor.translateAlternateColorCodes('&', mobCoins.configFile.getString("messages.no_permission"));
                    sender.sendMessage(noPermission);

                }

            }

            else if(args[0].equalsIgnoreCase("balancetop") || args[0].equalsIgnoreCase("baltop")) {

                if(sender.hasPermission("mobcoins.command.baltop")) {
                    if (args.length >= 2) {

                        if (NumberUtils.isNumber(args[1])) {

                            sender.sendMessage(CoinManager.getBalanceTop(Integer.valueOf(args[1])));

                        } else {

                            sender.sendMessage(CoinManager.getBalanceTop(1));

                        }

                    } else {
                        sender.sendMessage(CoinManager.getBalanceTop(1));
                    }
                }

                else {

                    String noPermission = ChatColor.translateAlternateColorCodes('&', mobCoins.configFile.getString("messages.no_permission"));
                    sender.sendMessage(noPermission);

                }

            }

            else {

                String unknownSubCommand = ChatColor.translateAlternateColorCodes('&', mobCoins.configFile.getString("messages.unknown_command"));
                sender.sendMessage(unknownSubCommand);

            }


        }

        else {

            ConsoleCommandSender console = mobCoins.getServer().getConsoleSender();

            if(args.length <= 0) {

                StringBuilder helpStaffMessage = new StringBuilder();


                for(String item : mobCoins.configFile.getStringList("messages.help_staff")) {

                    String messageToAdd = ChatColor.translateAlternateColorCodes('&', item);
                    helpStaffMessage.append(messageToAdd).append("\n");

                }

                console.sendMessage(helpStaffMessage.toString());

            }

            else if(args[0].equalsIgnoreCase("balance")) {

                if (args.length >= 2) {

                    if(Bukkit.getPlayerExact(args[1]) != null) {

                        Player selected = Bukkit.getPlayerExact(args[1]);
                        String selectedBalance = String.valueOf(CoinManager.getBalance(selected));

                        String balanceOther = ChatColor.translateAlternateColorCodes('&', mobCoins.configFile.getString("messages.balance_other")
                                .replace("{player}", selected.getName()).replace("{balance}", selectedBalance));
                        console.sendMessage(balanceOther);

                    }

                    else {

                        String playerNotFound = ChatColor.translateAlternateColorCodes('&', mobCoins.configFile.getString("messages.player_not_found")
                                .replace("{player}", args[1]));
                        console.sendMessage(playerNotFound);

                    }

                }

                else {

                     String balanceUsage = ChatColor.translateAlternateColorCodes('&', mobCoins.configFile.getString("messages.balance_other_usage"));
                     console.sendMessage(balanceUsage);

                }

            }

            else if(args[0].equalsIgnoreCase("add")) {

                if(args.length >= 3) {

                    if(Bukkit.getPlayerExact(args[1]) == null) {

                        String playerNotFound = ChatColor.translateAlternateColorCodes('&', mobCoins.configFile.getString("messages.player_not_found")
                                .replace("{player}", args[1]));
                        console.sendMessage(playerNotFound);

                    }

                    else if(!NumberUtils.isNumber(args[2])) {

                        String notANumber = ChatColor.translateAlternateColorCodes('&', mobCoins.configFile.getString("messages.not_a_number")
                                .replace("{number}", args[2]));
                        console.sendMessage(notANumber);

                    }

                    else {

                        Player addTo = Bukkit.getPlayerExact(args[1]);
                        CoinManager.addMobCoins(addTo, Double.valueOf(args[2]));

                        String addedMobCoins = ChatColor.translateAlternateColorCodes('&', mobCoins.configFile.getString("messages.balance_add_added")
                                .replace("{player}", addTo.getName()).replace("{amount}", args[2]));
                        console.sendMessage(addedMobCoins);

                    }

                }

                else {

                    String incorrectUsage = ChatColor.translateAlternateColorCodes('&', mobCoins.configFile.getString("messages.balance_add_usage"));
                    console.sendMessage(incorrectUsage);

                }

            }

            else if(args[0].equalsIgnoreCase("set")) {

                if(args.length >= 3) {

                    if(Bukkit.getPlayerExact(args[1]) == null) {

                        String playerNotFound = ChatColor.translateAlternateColorCodes('&', mobCoins.configFile.getString("messages.player_not_found")
                                .replace("{player}", args[1]));
                        console.sendMessage(playerNotFound);

                    }

                    else if(!NumberUtils.isNumber(args[2])) {

                        String notANumber = ChatColor.translateAlternateColorCodes('&', mobCoins.configFile.getString("messages.not_a_number")
                                .replace("{number}", args[2]));
                        console.sendMessage(notANumber);

                    }

                    else {

                        Player addTo = Bukkit.getPlayerExact(args[1]);
                        CoinManager.setMobCoins(addTo, Double.valueOf(args[2]));

                        String setMobCoins = ChatColor.translateAlternateColorCodes('&', mobCoins.configFile.getString("messages.balance_set_setted")
                                .replace("{player}", addTo.getName()).replace("{amount}", args[2]));
                        console.sendMessage(setMobCoins);

                    }

                }

                else {

                    String incorrectUsage = ChatColor.translateAlternateColorCodes('&', mobCoins.configFile.getString("messages.balance_set_usage"));
                    console.sendMessage(incorrectUsage);

                }

            }


            else if(args[0].equalsIgnoreCase("remove") || args[0].equalsIgnoreCase("rm") || args[0].equalsIgnoreCase("rem")) {

                if (args.length >= 3) {

                    if (Bukkit.getPlayerExact(args[1]) == null) {

                        String playerNotFound = ChatColor.translateAlternateColorCodes('&', mobCoins.configFile.getString("messages.player_not_found")
                                .replace("{player}", args[1]));
                        console.sendMessage(playerNotFound);

                    } else if (!NumberUtils.isNumber(args[2])) {

                        String notANumber = ChatColor.translateAlternateColorCodes('&', mobCoins.configFile.getString("messages.not_a_number")
                                .replace("{number}", args[2]));
                        console.sendMessage(notANumber);

                    } else {

                        Player addTo = Bukkit.getPlayerExact(args[1]);
                        CoinManager.removeMobCoins(addTo, Double.valueOf(args[2]));

                        String setMobCoins = ChatColor.translateAlternateColorCodes('&', mobCoins.configFile.getString("messages.balance_remove_removed")
                                .replace("{player}", addTo.getName()).replace("{amount}", args[2]));
                        console.sendMessage(setMobCoins);

                    }

                } else {

                    String incorrectUsage = ChatColor.translateAlternateColorCodes('&', mobCoins.configFile.getString("messages.balance_remove_usage"));
                    console.sendMessage(incorrectUsage);

                }

            }


            else if(args[0].equalsIgnoreCase("reload") || args[0].equalsIgnoreCase("rl")) {


                CompletableFuture<Void> future = CompletableFuture.runAsync(mobCoins::generateFiles);
                future.whenComplete((success, error) -> {
                    if(error != null) {
                        console.sendMessage("An error occurred, check the console!");
                        error.printStackTrace();
                    } else {
                        String configReloadedMsg = ChatColor.translateAlternateColorCodes('&', mobCoins.configFile.getString("messages.reloaded_config"));
                        console.sendMessage(configReloadedMsg);
                    }
                });

            }

            else if(args[0].equalsIgnoreCase("balancetop") || args[0].equalsIgnoreCase("baltop")) {

                if (args.length >= 2) {

                    if (NumberUtils.isNumber(args[1])) {

                        console.sendMessage(CoinManager.getBalanceTop(Integer.valueOf(args[1])));

                    } else {

                        console.sendMessage(CoinManager.getBalanceTop(1));

                    }

                } else {
                    console.sendMessage(CoinManager.getBalanceTop(1));
                }

            }

            else {

                String unknownSubCommand = ChatColor.translateAlternateColorCodes('&', mobCoins.configFile.getString("messages.unknown_command"));
                console.sendMessage(unknownSubCommand);

            }


        }

        return false;

    }
}
