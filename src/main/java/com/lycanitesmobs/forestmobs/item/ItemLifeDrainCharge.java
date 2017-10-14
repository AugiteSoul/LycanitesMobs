package com.lycanitesmobs.forestmobs.item;

import com.lycanitesmobs.core.entity.EntityProjectileBase;
import com.lycanitesmobs.core.item.ItemCharge;
import com.lycanitesmobs.forestmobs.ForestMobs;
import com.lycanitesmobs.forestmobs.entity.EntityLifeDrain;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemLifeDrainCharge extends ItemCharge {
	
	// ==================================================
	//                   Constructor
	// ==================================================
    public ItemLifeDrainCharge() {
        super();
        this.group = ForestMobs.instance.group;
        this.itemName = "lifedraincharge";
        this.setup();
    }


    // ==================================================
    //                  Get Projectile
    // ==================================================
    @Override
    public EntityProjectileBase getProjectile(ItemStack itemStack, World world, EntityPlayer entityPlayer) {
        return new EntityLifeDrain(world, entityPlayer, 20, 10);
    }
}
