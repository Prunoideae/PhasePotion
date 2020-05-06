package com.naive.phase;

import com.naive.phase.Registry.PotionRegistry;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.Potion;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@EventBusSubscriber
public class PhaseEvent {
    @SubscribeEvent
    public static void setNoClip(LivingUpdateEvent event) {
        //Only applied to player or it will absoultly ruin your experience
        if (event.getEntityLiving() instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) event.getEntityLiving();

            Potion potion = PotionRegistry.POTION_PHASE;
            //Only active when flying in capabilities to avoid annoying control
            player.noClip = player.isPotionActive(potion) && player.capabilities.isFlying;
        }
    }
}