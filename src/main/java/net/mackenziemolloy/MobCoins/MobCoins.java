//
// MobCoins (Built against 1.8)
//   By: Mackenzie Molloy
//
package net.mackenziemolloy.mobcoins;

import java.io.File;
import java.io.IOException;

import org.bukkit.plugin.ServicePriority;
import org.bukkit.plugin.java.JavaPlugin;

import net.mackenziemolloy.mobcoins.hooks.PlaceHolderAPIHook;
import net.mackenziemolloy.mobcoins.hooks.PluginAPI;
import net.mackenziemolloy.mobcoins.hooks.ShopGUIPlusHook;
import net.mackenziemolloy.mobcoins.listeners.EntityDeathListener;
import net.mackenziemolloy.mobcoins.utils.CommentedConfiguration;

public class MobCoins extends JavaPlugin {

    // Configuration File
    public CommentedConfiguration configFile;
    // Data Storage File
    public CommentedConfiguration dataFile;
    // Plugin Instance Provider
    public static MobCoins mobCoins;

    @Override
    public void onEnable() {

        // Plugin Instance Setter
        mobCoins = this;

        // Command Initializer
        new Commands(this);

        // Entity Death Listener Initializer
        getServer().getPluginManager().registerEvents(new EntityDeathListener(this), this);

        // Plugin Files Initializer
        generateFiles();

        // Plugin API Register
        getServer().getServicesManager().register(PluginAPI.class, new PluginAPI(), this, ServicePriority.Highest);

        //
        // Plugin Hook Initializers :-
        //

        // ShopGUIPlus (Custom Economy Hook)
        // Checks if ShopGUIPlus is running
        if(getServer().getPluginManager().isPluginEnabled("ShopGUIPlus")) {
            // Hooks only if the config option is enabled
            if(configFile.getBoolean("options.hooks.shopguiplus")) {
                // ShopGUIPlus Hook Initializer
                new ShopGUIPlusHook();
            }
        }

        // PlaceHolderAPI (PlaceHolders - EI: "%mobcoins_balance%)
        // Checks if PlaceHolderAPI is running
        if(getServer().getPluginManager().isPluginEnabled("PlaceholderAPI")) {
            // Hooks only if the config option is enabled
            if(configFile.getBoolean("options.hooks.placeholderapi")) {
                // PlaceHolderAPI Hook Initializer
                new PlaceHolderAPIHook(this).register();
                MobCoins.getInstance().getLogger().info("Hooked into PlaceHolderAPI!");
            }
        }

    }

    // Plugin File Generation Method
    public void generateFiles() {

        // Configuration File
        File config = new File(getDataFolder(), "config.yml");
        // Data Storage File
        File data = new File(getDataFolder(), "data.yml");

        // Generates the Configuration File if it doesn't already exist
        if(!config.exists()) saveResource("config.yml", false);
        // Generates the Data Storage File if it doesn't already exist
        if(!data.exists()) saveResource("data.yml", false);

        // Loads the Configuration File into memory
        configFile = CommentedConfiguration.loadConfiguration(config);
        // Attempts to sync the Configuration File content with what's required by Default
        try {
            configFile.syncWithConfig(config, getResource("config.yml"), "options.mobs"); //decorations
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        // Loads the Data Storage File into memory
        dataFile = CommentedConfiguration.loadConfiguration(data);
        // Attempts to sync the Data Storage File content with what's required by Default
        try {
            dataFile.syncWithConfig(data, getResource("data.yml"), "stupid_option"); //decorations
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    // Provides instance information to the Instance provider
    public static MobCoins getInstance() {
        return mobCoins;
    }

    // Plugin File updating method
    public void saveFiles(boolean config, boolean data) {

        // Copies the in-memory Configuration, to the saved Configuration file
        if(config) {
            try {
                configFile.save(new File(getDataFolder(), "config.yml"));
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        // Copies the in-memory Data Storage, to the saved Data Storage file
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
