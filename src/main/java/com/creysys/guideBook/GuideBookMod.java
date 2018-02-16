package com.creysys.guideBook;

import com.creysys.guideBook.api.RecipeManager;
import com.creysys.guideBook.api.RegisterRecipeHandlersEvent;
import com.creysys.guideBook.client.GuideBookGui;
import com.creysys.guideBook.common.GuiBookContainer;
import com.creysys.guideBook.common.items.ItemGuideBook;
import com.creysys.guideBook.common.proxy.ProxyServer;
import com.creysys.guideBook.network.message.MessagePutItemsInWorkbench;
import com.creysys.guideBook.plugin.vanilla.PluginVanilla;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.IGuiHandler;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

@Mod(modid = GuideBookMod.MODID, version = GuideBookMod.VERSION)
public class GuideBookMod
{
    public static class GuiId {
        public static final int GuideBook = 0;
    }

    public class GuiHandler implements IGuiHandler {
        @Override
        public Object getServerGuiElement(int i, EntityPlayer entityPlayer, World world, int i1, int i2, int i3) {
            switch(i) {
                case GuiId.GuideBook:
                    return new GuiBookContainer();
            }

            return null;
        }

        @Override
        public Object getClientGuiElement(int i, EntityPlayer entityPlayer, World world, int i1, int i2, int i3) {
            switch(i) {
                case GuiId.GuideBook:
                    return new GuideBookGui();
            }

            return null;
        }
    }

    public static final String MODID = "guidebook";
    public static final String VERSION = "1.7.0";

    public static final ItemGuideBook guideBook = new ItemGuideBook();

    @Mod.Instance
    public static GuideBookMod instance;

    public static SimpleNetworkWrapper network;
    public final GuiHandler guiHandler = new GuiHandler();

    @SidedProxy(serverSide = "com.creysys.guideBook.common.proxy.ProxyServer", 
    		clientSide = "com.creysys.guideBook.common.proxy.ProxyClient")
    public static ProxyServer proxy;

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        network = NetworkRegistry.INSTANCE.newSimpleChannel(MODID);
        network.registerMessage(MessagePutItemsInWorkbench.Handler.class, MessagePutItemsInWorkbench.class, 0, Side.SERVER);

        NetworkRegistry.INSTANCE.registerGuiHandler(instance, guiHandler);

        if(event.getSide() == Side.CLIENT) {
            PluginVanilla.preInit();

            //if(Loader.isModLoaded("thaumcraft")) PluginThaumcraft.preInit();
        }

        proxy.registerHandlers();
    }

    @EventHandler
    public void init(FMLInitializationEvent event)
    {
        proxy.registerKeyBinds();
        proxy.registerModels();
    }

    @EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        if(event.getSide() == Side.CLIENT){
            PluginVanilla.postInit();
            MinecraftForge.EVENT_BUS.post(new RegisterRecipeHandlersEvent());
            RecipeManager.load();
        }
    }
    
}
