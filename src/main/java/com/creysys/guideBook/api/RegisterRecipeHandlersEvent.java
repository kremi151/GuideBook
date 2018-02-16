package com.creysys.guideBook.api;

import net.minecraftforge.fml.common.eventhandler.Event;

/**
 * Event to be called when recipe handlers should be registered
 * @author kremi151
 *
 */
public class RegisterRecipeHandlersEvent extends Event{

	public final void registerHandler(RecipeHandler handler) {
		RecipeManager.registerHandler(handler);
	}
}
