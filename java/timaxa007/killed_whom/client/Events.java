package timaxa007.killed_whom.client;

import java.util.ArrayList;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import cpw.mods.fml.common.network.FMLNetworkEvent.ClientDisconnectionFromServerEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.event.RenderGameOverlayEvent;

@SideOnly(Side.CLIENT)
public class Events {

	private static final Minecraft mc = Minecraft.getMinecraft();
	public static byte direction = 2;
	public static int delayShowMax = 60;
	private static final ArrayList<WTKW> list = new ArrayList<WTKW>();
	private static int x = 0, y = 0, offsetY = 0;
	private static boolean posTop = true;

	@SubscribeEvent
	public void drawText(RenderGameOverlayEvent.Post event) {
		if (event.type != RenderGameOverlayEvent.ElementType.ALL && event.type != RenderGameOverlayEvent.ElementType.TEXT) return;
		if (list.isEmpty()) return;

		switch(direction % 3) {
		case 0:x = 0;break;
		case 1:x = event.resolution.getScaledWidth() / 2;break;
		case 2:x = event.resolution.getScaledWidth();break;
		}

		switch(direction / 3) {
		case 0:y = 0;break;
		case 1:y = event.resolution.getScaledHeight();break;
		}

		switch(event.type) {
		case ALL:
			Minecraft.getMinecraft().getTextureManager().bindTexture(TextureMap.locationItemsTexture);
			for (int i = list.size() - 1; i >= 0; --i) {
				WTKW wtkw = list.get(i);
				if (wtkw.than == null) continue;
				offsetY = (i * 16);
				mc.ingameGUI.drawTexturedModelRectFromIcon(
						(direction % 3 == 1 ? x - 9 : direction % 3 == 0 ? x + wtkw.offset : x - wtkw.offset - 16),
						(y == 0 ? y + offsetY + 6 : y - offsetY - 15) - 3, wtkw.than.getIconIndex(), 16, 16);
			}
			GL11.glColor4f(1F, 1F, 1F, 1F);
			break;
		case TEXT:
			for (int i = list.size() - 1; i >= 0; --i) {
				WTKW wtkw = list.get(i);
				offsetY = (i * 16);
				mc.fontRenderer.drawStringWithShadow(wtkw.who,
						(direction % 3 == 1 ? x - 9 - wtkw.offset : direction % 3 == 0 ? x : x - wtkw.offset - 16 - mc.fontRenderer.getStringWidth(wtkw.who)),
						(y == 0 ? y + offsetY + 7 : y - offsetY - 13), 0xFFFFFF);
				mc.fontRenderer.drawStringWithShadow(wtkw.whom,
						(direction % 3 == 1 ? x + 9 : direction % 3 == 0 ? x + wtkw.offset + 16 : x - wtkw.offset),
						(y == 0 ? y + offsetY + 6 : y - offsetY - 13), 0xFFFFFF);
			}
			break;
		default:return;
		}
	}

	@SubscribeEvent
	public void tickClient(TickEvent.ClientTickEvent event) {
		if (mc.theWorld == null) return;
		if (list.isEmpty()) return;

		for (int i = 0; i < list.size(); ++i) {
			if (mc.theWorld.getTotalWorldTime() >= list.get(i).time) list.remove(i--);
		}
	}

	@SubscribeEvent
	public void firstMessegeOut(ClientDisconnectionFromServerEvent event) {
		list.clear();
		list.trimToSize();
	}

	/**Who Than Killed Whom**/
	public static class WTKW {

		public final String who, whom;
		public final ItemStack than;
		public final long time;
		public final int offset;

		public WTKW(String who, ItemStack than, String whom, long time, int offset) {
			this.who = who;
			this.than = than;
			this.whom = whom;
			this.time = time;
			this.offset = offset;
		}

	}

	public static void addWTKW(Entity from, ItemStack than, EntityLivingBase whom) {
		if (from == null) return;
		if (whom == null) return;

		String a = null, b = null;

		if (from instanceof EntityPlayer)
			a = ((EntityPlayer)from).getDisplayName();
		else
			a = from.getCommandSenderName();

		if (whom instanceof EntityPlayer)
			b = ((EntityPlayer)whom).getDisplayName();
		else
			b = whom.getCommandSenderName();

		long time = from.worldObj.getTotalWorldTime() + delayShowMax;
		if (time > Long.MAX_VALUE) time = Long.MAX_VALUE;

		if (a != null && b != null) list.add(new WTKW(a, than, b, time,
				(direction % 3 == 2 ? mc.fontRenderer.getStringWidth(b) : mc.fontRenderer.getStringWidth(a))));
	}

}
