package timaxa007.killed_whom.client;

import cpw.mods.fml.client.config.GuiConfig;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.common.config.ConfigElement;
import timaxa007.killed_whom.KilledWhomMod;

public class KilledWhomConfigGui extends GuiConfig {

	public KilledWhomConfigGui(GuiScreen parent) {
		super(parent, new ConfigElement(Config.config.getCategory(Config.categoryGui)).getChildElements(),
				KilledWhomMod.MODID, false, false, GuiConfig.getAbridgedConfigPath(Config.config.toString()));
	}

}
