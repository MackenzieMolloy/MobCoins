package net.mackenziemolloy.MobCoins.Hooks;

import net.mackenziemolloy.MobCoins.Hooks.EconomyHook;
import net.mackenziemolloy.MobCoins.MobCoins;

public class ShopGUIPlusHook {

    private EconomyHook economyProvider;

    public ShopGUIPlusHook() {

        this.economyProvider = new EconomyHook();
        net.brcdev.shopgui.ShopGuiPlusApi.registerEconomyProvider(economyProvider);

        MobCoins.getInstance().getLogger().info("Hooked into ShopGUIPlus!");

    }

}
