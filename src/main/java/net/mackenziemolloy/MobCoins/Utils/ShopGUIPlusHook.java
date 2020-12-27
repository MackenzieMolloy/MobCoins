package net.mackenziemolloy.MobCoins.Utils;

public class ShopGUIPlusHook {

    private EconomyHook economyProvider;

    public ShopGUIPlusHook() {

        this.economyProvider = new EconomyHook();
        net.brcdev.shopgui.ShopGuiPlusApi.registerEconomyProvider(economyProvider);

    }

}
