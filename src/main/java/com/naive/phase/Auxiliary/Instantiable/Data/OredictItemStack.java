package com.naive.phase.Auxiliary.Instantiable.Data;

import com.naive.phase.Auxiliary.Helper.ItemHelper;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public class OredictItemStack {
    private final String dictionary;
    private final int count;

    public OredictItemStack(String dictionary, int count) {
        this.dictionary = dictionary;
        this.count = count;
    }

    public String getDictionary() {
        return dictionary;
    }

    public int getCount() {
        return count;
    }

    public boolean matches(ItemStack stack) {
        for (ItemStack itemStack : OreDictionary.getOres(this.dictionary)) {
            if (OreDictionary.itemMatches(itemStack, stack, false))
                return stack.getCount() >= this.count;
        }
        return false;
    }

    public ItemStack getSomething() {
        return OreDictionary.doesOreNameExist(this.dictionary) ? ItemHelper.resizeItemStack(OreDictionary.getOres(this.dictionary).get(0), this.count) : ItemStack.EMPTY;
    }
}
