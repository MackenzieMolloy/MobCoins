package net.mackenziemolloy.MobCoins;

import net.mackenziemolloy.MobCoins.Events.MobDeathEvent;
import net.mackenziemolloy.MobCoins.Utils.CommentedConfiguration;
import net.mackenziemolloy.MobCoins.Utils.EconomyHook;
import net.mackenziemolloy.MobCoins.Utils.PlaceHolders;
import net.mackenziemolloy.MobCoins.Utils.ShopGUIPlusHook;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;

public class MobCoins extends JavaPlugin {

    public CommentedConfiguration configFile;
    public CommentedConfiguration dataFile;
    public static MobCoins mobCoins;

    @Override
    public void onEnable() {

        mobCoins = this;

        new Commands(this);
        getServer().getPluginManager().registerEvents(new MobDeathEvent(this), this);
        generateFiles();

        if(getServer().getPluginManager().isPluginEnabled("ShopGUIPlus")) {
            if(configFile.getBoolean("options.hooks.shopguiplus")) {
                new ShopGUIPlusHook();
            }
        }

        if(getServer().getPluginManager().isPluginEnabled("PlaceholderAPI")) {
            new PlaceHolders(this).register();
        }

    }


    public void generateFiles() {
        File config = new File(getDataFolder(), "config.yml");
        File data = new File(getDataFolder(), "data.yml");

        if(!config.exists()) saveResource("config.yml", false);
        if(!data.exists()) saveResource("data.yml", false);

        configFile = CommentedConfiguration.loadConfiguration(config);
        try {
            configFile.syncWithConfig(config, getResource("config.yml"), "options.mobs"); //decorations
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        dataFile = CommentedConfiguration.loadConfiguration(data);
        try {
            dataFile.syncWithConfig(data, getResource("data.yml"), "stupid_option"); //decorations
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public static MobCoins getInstance() {
        return mobCoins;
    }

    public void saveFiles(boolean config, boolean data) {

        if(config) {
            try {
                configFile.save(new File(getDataFolder(), "config.yml"));
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        if(data) {
            try {
                dataFile.save(new File(getDataFolder(), "data.yml"));
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

    }

}
