package com.creysys.guideBook;

import net.minecraft.item.Item;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber
public class RegistryHandler {

	@SubscribeEvent
	public static void onRegisterItems(RegistryEvent.Register<Item> event) {
		System.out.println("### REGISTER GUIDE BOOK");
		event.getRegistry().register(GuideBookMod.guideBook);
	}
	
}
