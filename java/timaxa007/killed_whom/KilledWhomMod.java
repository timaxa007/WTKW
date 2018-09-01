package timaxa007.killed_whom;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import cpw.mods.fml.relauncher.Side;
import net.minecraftforge.common.MinecraftForge;
import timaxa007.killed_whom.network.DeathWTKWMessage;

@Mod(modid = KilledWhomMod.MODID, name = KilledWhomMod.NAME, version = KilledWhomMod.VERSION)
public class KilledWhomMod {

	public static final String
	MODID = "killed_whom",
	NAME = "Who Than Killed Whom Mod",
	VERSION = "0.85";

	@Mod.Instance(MODID)
	public static KilledWhomMod instance;

	@cpw.mods.fml.common.SidedProxy(modId = MODID,
			serverSide = "timaxa007.killed_whom.Proxy",
			clientSide = "timaxa007.killed_whom.client.Proxy")
	public static Proxy proxy;

	public static final SimpleNetworkWrapper network = NetworkRegistry.INSTANCE.newSimpleChannel(MODID);

	@Mod.EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		network.registerMessage(DeathWTKWMessage.Handler.class, DeathWTKWMessage.class, 0, Side.CLIENT);
		//network.registerMessage(DeathWTKWMessage.Handler.class, DeathWTKWMessage.class, 0, Side.SERVER);
		MinecraftForge.EVENT_BUS.register(new Events());
		proxy.preInit(event);
	}

}
