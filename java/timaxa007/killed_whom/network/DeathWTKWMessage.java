package timaxa007.killed_whom.network;

import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class DeathWTKWMessage implements IMessage {

	public int from, whom;
	public NBTTagCompound nbt;

	public DeathWTKWMessage() {}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeInt(from);
		buf.writeInt(whom);
		if (nbt != null)
			ByteBufUtils.writeTag(buf, nbt);
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		from = buf.readInt();
		whom = buf.readInt();
		if (buf.readableBytes() > 2)
			nbt = ByteBufUtils.readTag(buf);
	}

	public static class Handler implements IMessageHandler<DeathWTKWMessage, IMessage> {

		@Override
		public IMessage onMessage(DeathWTKWMessage packet, MessageContext message) {
			if (message.side.isClient())
				act(packet);
			else
				act(message.getServerHandler().playerEntity, packet);
			return null;
		}

		@SideOnly(Side.CLIENT)
		private void act(DeathWTKWMessage packet) {
			Minecraft mc = Minecraft.getMinecraft();
			Entity whom = mc.theWorld.getEntityByID(packet.whom);
			if (!(whom instanceof EntityLivingBase)) return;
			Entity from = mc.theWorld.getEntityByID(packet.from);
			ItemStack than = null;
			if (packet.nbt != null) than = ItemStack.loadItemStackFromNBT(packet.nbt);
			timaxa007.killed_whom.client.Events.addWTKW(from, than, (EntityLivingBase)whom);
		}

		private void act(EntityPlayerMP player, DeathWTKWMessage packet) {}

	}

}
