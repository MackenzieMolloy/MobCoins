package net.mackenziemolloy.mobcoins.hooks;

import net.mackenziemolloy.mobcoins.MobCoins;

public class ShopGUIPlusHook {

    private EconomyHook economyProvider;

    public ShopGUIPlusHook() {

        this.economyProvider = new EconomyHook();
        net.brcdev.shopgui.ShopGuiPlusApi.registerEconomyProvider(economyProvider);

        MobCoins.getInstance().getLogger().info("Hooked into ShopGUIPlus!");

    }

}
