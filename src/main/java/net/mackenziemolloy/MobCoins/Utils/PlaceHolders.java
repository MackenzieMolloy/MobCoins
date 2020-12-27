package net.mackenziemolloy.MobCoins.Utils;

import me.clip.placeholderapi.PlaceholderAPI;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import net.mackenziemolloy.MobCoins.MobCoins;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

public class PlaceHolders extends PlaceholderExpansion implements Listener  {

    private MobCoins mobCoins;

    public PlaceHolders(final MobCoins mobCoins) {
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

        else {

            return "Not a valid placeholder";

        }

    }

}