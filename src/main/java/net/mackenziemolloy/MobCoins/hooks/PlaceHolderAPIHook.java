package net.mackenziemolloy.mobCoins.hooks;

import me.clip.placeholderapi.PlaceholderAPI;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import net.mackenziemolloy.mobCoins.MobCoins;
import net.mackenziemolloy.mobCoins.utils.Generic;

import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

public class PlaceHolderAPIHook extends PlaceholderExpansion implements Listener  {

    private MobCoins mobCoins;

    public PlaceHolderAPIHook(final MobCoins mobCoins) {
        this.mobCoins = mobCoins;
    }

    @Override
    public boolean persist() {
        return true;
    }

    @Override
    public String getIdentifier() {
        return "mobcoins";
    }

    @Override
    public String getAuthor() {
        return "Mackenzie Molloy";
    }

    @Override
    public String getVersion() {
        return "1.0";
    }

    public String onPlaceholderRequest(final Player player, final String params) {

        if(params.equalsIgnoreCase("balance")) {

            if(mobCoins.dataFile.get(player.getUniqueId().toString()) != null) {

                return mobCoins.dataFile.getString(player.getUniqueId().toString());

            }

            else {

                return "0.0";

            }

        }

        else if(params.equalsIgnoreCase("balance_rounded")) {

            if(mobCoins.dataFile.get(player.getUniqueId().toString()) != null) {

                return Generic.roundedNumber(mobCoins.dataFile.getDouble(player.getUniqueId().toString()));

            }

            else {

                return "0.0";

            }

        }

        else {

            return "Not a valid placeholder";

        }

    }

}
