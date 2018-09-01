package timaxa007.killed_whom.client;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;

public class Proxy extends timaxa007.killed_whom.Proxy {

	@Override
	public void preInit(FMLPreInitializationEvent event) {
		Events e = new Events();
		Configuration config = new Configuration(event.getSuggestedConfigurationFile());
		config.load();
		String category = "gui";
		e.direction = (byte)config.get("gui", "direction", e.direction,
				"0 - left-top,		1 - center-top,		2 - right-top, \n" +
				"3 - left-botton,	4 - center-botton,	5 - right-botton.").getInt();
		e.offsetX = config.get("gui", "offsetX", e.offsetX).getInt();
		e.offsetY = config.get("gui", "offsetY", e.offsetY).getInt();
		config.save();
		MinecraftForge.EVENT_BUS.register(e);
		FMLCommonHandler.instance().bus().register(e);
	}

}
