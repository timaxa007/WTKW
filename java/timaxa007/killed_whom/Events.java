package timaxa007.killed_whom;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.entity.projectile.EntityFireball;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EntityDamageSource;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import timaxa007.killed_whom.network.DeathWTKWMessage;

public class Events {

	@SubscribeEvent
	public void deathWTKW(LivingDeathEvent event) {
		Entity from = event.source.getSourceOfDamage();//Кто убил.
		if (from == null) return;
		EntityLivingBase whom = event.entityLiving;//Кого убили.
		if (whom == null) return;
		if (from instanceof EntityLivingBase && event.source instanceof EntityDamageSource)
			sendWTKW(((EntityDamageSource)event.source).getEntity(), ((EntityLivingBase)from).getHeldItem(), whom);
		else if (from instanceof EntityArrow)
			sendWTKW((EntityLivingBase)((EntityArrow)from).shootingEntity, new ItemStack(Items.arrow), whom);
		else if (from instanceof EntityThrowable)
			sendWTKW(((EntityThrowable)from).getThrower(), null, whom);
		else if (from instanceof EntityFireball)
			sendWTKW(((EntityFireball)from).shootingEntity, new ItemStack(Items.fire_charge), whom);
		else
			sendWTKW(from, null, whom);
	}

	private void sendWTKW(Entity from, ItemStack heldItem, EntityLivingBase whom) {
		DeathWTKWMessage message = new DeathWTKWMessage();
		message.from = from.getEntityId();
		message.whom = whom.getEntityId();
		if (heldItem != null) {
			message.nbt = heldItem.writeToNBT(new NBTTagCompound());
		}
		KilledWhomMod.network.sendToAll(message);
	}

}
