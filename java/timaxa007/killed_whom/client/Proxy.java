package timaxa007.killed_whom.client;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;

public class Proxy extends timaxa007.killed_whom.Proxy {

	@Override
	public void preInit(FMLPreInitializationEvent event) {
		//Config
		Config.init(event.getSuggestedConfigurationFile());
		//Our event class - Наш класс для эвентов
		final Events e = new Events();
		//buses - шины
		MinecraftForge.EVENT_BUS.register(e);
		FMLCommonHandler.instance().bus().register(e);
	}

}
