package com.naive.phase.Auxiliary.Register;

import net.minecraftforge.fml.relauncher.Side;

import java.lang.annotation.*;

public class Registry {

    /**
     * Indicate the the field is tended to be a Block instance, and the class
     * holding this field will be registered as a block. If an ItemInst field is
     * also present, then the ItemBlock of this Block will also be registered.
     *
     * @author Naive
     */
    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.FIELD)
    public @interface BlockInst {

    }

    /**
     * Indicate the field is tended to be a Item instance, and the class holding
     * this field will be registered as an Item. If a BlockInst field is also
     * present, then the Item will be registered as ItemBlock.
     *
     * @author Naive
     */
    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.FIELD)
    public @interface ItemInst {
        String value = "";
    }

    /**
     * Indicate the class is tended to be a Tile, and the class will be registered
     * right after the block registered.
     * <ul><li>
     * This tile holds a value, where the registry name is.
     * </li></ul>
     *
     * @author Naive
     */
    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.TYPE)
    public @interface Tile {
        String value();
    }

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.TYPE)
    public @interface Potion {

    }

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.FIELD)
    public @interface PotionEffect {

    }

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.TYPE)
    public @interface Packet {
        Side value();
    }
}