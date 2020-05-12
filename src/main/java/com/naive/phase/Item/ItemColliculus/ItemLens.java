package com.naive.phase.Item.ItemColliculus;

import com.naive.phase.Auxiliary.Register.Registry;
import com.naive.phase.Base.Item.PhaseItemBase;

public class ItemLens extends PhaseItemBase {

    @Registry.ItemInst
    public static ItemLens itemInst;

    public ItemLens() {
        super("lens");
        setMaxStackSize(1);
    }
}
