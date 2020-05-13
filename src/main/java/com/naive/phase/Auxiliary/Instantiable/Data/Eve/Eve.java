package com.naive.phase.Auxiliary.Instantiable.Data.Eve;


import com.naive.phase.Auxiliary.Helper.NBTHelper;
import net.minecraft.nbt.NBTTagCompound;

import java.awt.*;

public class Eve {

    public static final String TAG_EVE = "phase_eve";

    private int phase;
    private int capacity;

    private float hue;
    private float brightness;
    private float saturation;

    public Eve(int phase, int capacity, float hue, float brightness, float saturation) {
        this.phase = phase;
        this.capacity = capacity;
        this.hue = hue;
        this.brightness = brightness;
        this.saturation = saturation;
    }

    public Eve copy() {
        return new Eve(phase, capacity, hue, brightness, saturation);
    }

    public Eve backfire() {
        return new Eve(360 - phase, capacity, 1f - hue, 1f - brightness, saturation);
    }

    public Eve fact(float fact) {
        return new Eve(phase, capacity, hue, brightness, saturation * fact);
    }

    public float getHue() {
        return hue;
    }

    public void setHue(float hue) {
        this.hue = hue;
    }

    public int getPhase() {
        return phase;
    }

    public void setPhase(int phase) {
        this.phase = phase;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public void setBrightness(float brightness) {
        this.brightness = brightness;
    }

    public int getCapacity() {
        return capacity;
    }

    public float getBrightness() {
        return brightness;
    }

    public float getSaturation() {
        return saturation;
    }

    public void setSaturation(float saturation) {
        this.saturation = saturation;
    }

    public int getColor() {
        return Color.HSBtoRGB(hue, saturation, brightness);
    }

    public NBTTagCompound writeToNBT() {

        NBTTagCompound tagEve = new NBTTagCompound();

        tagEve.setFloat("hue", hue);
        tagEve.setFloat("saturation", saturation);
        tagEve.setFloat("brightness", brightness);

        tagEve.setInteger("capacity", capacity);
        tagEve.setInteger("phase", phase);

        return tagEve;
    }

    public Eve readFromNBT(NBTTagCompound tag) {

        if (tag == null)
            tag = new NBTTagCompound();

        this.saturation = NBTHelper.getOrDefault(tag, "saturation", this.saturation);
        this.hue = NBTHelper.getOrDefault(tag, "hue", this.hue);
        this.brightness = NBTHelper.getOrDefault(tag, "brightness", this.brightness);

        this.capacity = NBTHelper.getOrDefault(tag, "capacity", this.capacity);
        this.phase = NBTHelper.getOrDefault(tag, "phase", this.phase);

        return this;
    }
}