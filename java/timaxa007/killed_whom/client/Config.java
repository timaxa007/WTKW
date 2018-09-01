package timaxa007.killed_whom.client;

import java.io.File;

import net.minecraftforge.common.config.Configuration;

/**https://forum.mcmodding.ru/threads/1-7-build-1147-%D0%A2%D1%83%D1%82%D0%BE%D1%80%D0%B8%D0%B0%D0%BB-%D0%9A%D0%B0%D0%BA-%D1%81%D0%B4%D0%B5%D0%BB%D0%B0%D1%82%D1%8C-%D0%BA%D0%BE%D0%BD%D1%84%D0%B8%D0%B3-%D1%81-%D0%B3%D1%83%D0%B8.4682/post-49710**/
public class Config {

	public static Configuration config;
	public static final String categoryGui = "gui";

	public static void init(File file) {
		config = new Configuration(file);
		config.load();
		syncConfig();
	}

	public static void syncConfig() {
		Events.direction = (byte)config.get("gui", "direction", Events.direction,
				"0 - left-top,		1 - center-top,		2 - right-top, \n" +
				"3 - left-botton,	4 - center-botton,	5 - right-botton.").getInt();
		Events.offsetX = config.get("gui", "offsetX", Events.offsetX).getInt();
		Events.offsetY = config.get("gui", "offsetY", Events.offsetY).getInt();
		if (config.hasChanged()) config.save();
	}

}
