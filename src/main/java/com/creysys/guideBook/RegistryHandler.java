package com.creysys.guideBook;

import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.RegistryEvent.MissingMappings.Mapping;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber
public class RegistryHandler {
	
	private static final ResourceLocation OLD_GUIDEBOOK_ID = new ResourceLocation(GuideBookMod.MODID, "guideBook");

	@SubscribeEvent
	public static void onRegisterItems(RegistryEvent.Register<Item> event) {
		event.getRegistry().register(GuideBookMod.guideBook);
	}

	@SubscribeEvent
	public static void onMissingItemMappings(RegistryEvent.MissingMappings<Item> event){
		for(Mapping<Item> mapping : event.getMappings()) {
			if(mapping.key.equals(OLD_GUIDEBOOK_ID)) {
				mapping.remap(GuideBookMod.guideBook);
			}
		}
	}
	
}
