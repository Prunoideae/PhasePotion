package com.naive.phase.Item.ItemUpgrades;

import com.naive.phase.Auxiliary.Register.Registry;

public class ItemBrightnessUpgrade extends ItemUpgradeBlank {

    @Registry.ItemInst
    public static ItemBrightnessUpgrade itemInst;

    public ItemBrightnessUpgrade() {
        super("upgrade_brightness");
    }

    @Override
    public int getEnergyCost() {
        return 10;
    }
}
