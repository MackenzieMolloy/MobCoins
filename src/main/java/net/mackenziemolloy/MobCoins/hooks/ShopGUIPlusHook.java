package net.mackenziemolloy.mobCoins.hooks;

import net.mackenziemolloy.mobCoins.MobCoins;
import net.mackenziemolloy.mobCoins.hooks.EconomyHook;

public class ShopGUIPlusHook {

    private EconomyHook economyProvider;

    public ShopGUIPlusHook() {

        this.economyProvider = new EconomyHook();
        net.brcdev.shopgui.ShopGuiPlusApi.registerEconomyProvider(economyProvider);

        MobCoins.getInstance().getLogger().info("Hooked into ShopGUIPlus!");

    }

}
