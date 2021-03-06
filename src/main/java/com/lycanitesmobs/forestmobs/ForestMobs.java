package com.lycanitesmobs.forestmobs;

import com.lycanitesmobs.LycanitesMobs;
import com.lycanitesmobs.ObjectManager;
import com.lycanitesmobs.core.Submod;
import com.lycanitesmobs.core.dispenser.DispenserBehaviorMobEggCustom;
import com.lycanitesmobs.core.info.GroupInfo;
import com.lycanitesmobs.core.info.MobInfo;
import com.lycanitesmobs.core.info.ObjectLists;
import com.lycanitesmobs.core.info.Subspecies;
import com.lycanitesmobs.core.item.ItemCustomFood;
import com.lycanitesmobs.core.item.ItemTreat;
import com.lycanitesmobs.forestmobs.entity.*;
import com.lycanitesmobs.forestmobs.item.ItemForestEgg;
import net.minecraft.block.BlockDispenser;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.monster.EntitySpider;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.EntityEntry;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;

@Mod(modid = ForestMobs.modid, name = ForestMobs.name, version = LycanitesMobs.version, dependencies = "required-after:" + LycanitesMobs.modid, acceptedMinecraftVersions = LycanitesMobs.acceptedMinecraftVersions)
public class ForestMobs extends Submod {
	
	public static final String modid = "forestmobs";
	public static final String name = "Lycanites Forest Mobs";
	
	// Instance:
	@Instance(modid)
	public static ForestMobs instance;
	
	// Proxy:
	@SidedProxy(clientSide="com.lycanitesmobs.forestmobs.ClientSubProxy", serverSide="com.lycanitesmobs.forestmobs.CommonSubProxy")
	public static CommonSubProxy proxy;


	@Mod.EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		super.preInit(event);
	}

	@Mod.EventHandler
	public void init(FMLInitializationEvent event) {
		super.init(event);
	}

	@Mod.EventHandler
	public void postInit(FMLPostInitializationEvent event) {
		super.postInit(event);
	}

	@SubscribeEvent
	public void registerEntities(RegistryEvent.Register<EntityEntry> event) {
		super.registerEntities(event);
	}


	@Override
	public void initialSetup() {
		group = new GroupInfo(this, "Forest Mobs", 1)
				.setDimensionBlacklist("-1,1").setBiomes("FOREST, -MOUNTAIN").setDungeonThemes("SHADOW, NECRO")
				.setEggName("forestegg");
		group.loadFromConfig();
	}

	@Override
	public void createItems() {
		ObjectManager.addItem("forestspawn", new ItemForestEgg());

		ItemCustomFood rawMeat =  new ItemCustomFood("arisaurmeatraw", group, 2, 0.5F, ItemCustomFood.FOOD_CLASS.RAW).setPotionEffect(MobEffects.SATURATION, 45, 2, 0.8F);
		if(ObjectManager.getPotionEffect("paralysis") != null)
			rawMeat.setPotionEffect(ObjectManager.getPotionEffect("paralysis"), 10, 2, 0.8F);
		ObjectManager.addItem("arisaurmeatraw", rawMeat);
		ObjectLists.addItem("vegetables", ObjectManager.getItem("arisaurmeatraw"));

		ItemCustomFood arisaurCooked = new ItemCustomFood("arisaurmeatcooked", group, 6, 0.7F, ItemCustomFood.FOOD_CLASS.COOKED).setAlwaysEdible();
		if(ObjectManager.getPotionEffect("rejuvenation") != null)
			arisaurCooked.setPotionEffect(ObjectManager.getPotionEffect("rejuvenation"), 30, 1, 1.0F);
		ObjectManager.addItem("arisaurmeatcooked", arisaurCooked);
		ObjectLists.addItem("vegetables", ObjectManager.getItem("arisaurmeatcooked"));

		ItemCustomFood paleosalad = new ItemCustomFood("paleosalad", group, 6, 0.7F, ItemCustomFood.FOOD_CLASS.MEAL).setAlwaysEdible();
		if(ObjectManager.getPotionEffect("rejuvenation") != null)
			paleosalad.setPotionEffect(ObjectManager.getPotionEffect("rejuvenation"), 600, 1, 1.0F);
		ObjectManager.addItem("paleosalad", paleosalad.setMaxStackSize(16), 3, 1, 6);
		ObjectLists.addItem("vegetables", ObjectManager.getItem("paleosalad"));

		ObjectManager.addItem("shamblertreat", new ItemTreat("shamblertreat", group));
		ObjectManager.addItem("wargtreat", new ItemTreat("wargtreat", group));
	}

	@Override
	public void createBlocks() {

	}

	@Override
	public void createEntities() {
		// Mobs:
		BlockDispenser.DISPENSE_BEHAVIOR_REGISTRY.putObject(ObjectManager.getItem("forestspawn"), new DispenserBehaviorMobEggCustom());
		MobInfo newMob;

		newMob = new MobInfo(group, "ent", EntityEnt.class, 0x997700, 0x00FF22)
				.setPeaceful(false).setSummonable(true).setSummonCost(2).setDungeonLevel(0)
				.addSubspecies(new Subspecies("russet", "uncommon")).addSubspecies(new Subspecies("scarlet", "uncommon"));
		newMob.spawnInfo.setSpawnTypes("MONSTER, BEAST")
				.setSpawnWeight(8).setAreaLimit(10).setGroupLimits(1, 3).setLightDark(false, true);
		ObjectManager.addMob(newMob);

		newMob = new MobInfo(group, "treant", EntityTreant.class, 0x663300, 0x00AA11)
				.setPeaceful(false).setSummonable(false).setSummonCost(6).setDungeonLevel(2)
				.addSubspecies(new Subspecies("russet", "uncommon")).addSubspecies(new Subspecies("ashen", "uncommon"));
		newMob.spawnInfo.setSpawnTypes("MONSTER, BEAST")
				.setSpawnWeight(1).setAreaLimit(2).setGroupLimits(1, 1).setLightDark(false, true);
		ObjectManager.addMob(newMob);

		newMob = new MobInfo(group, "shambler", EntityShambler.class, 0xDDFF22, 0x005511)
				.setPeaceful(false).setTameable(true).setSummonCost(4).setDungeonLevel(1)
				.addSubspecies(new Subspecies("dark", "uncommon")).addSubspecies(new Subspecies("keppel", "uncommon"));
		newMob.spawnInfo.setSpawnTypes("MONSTER, BEAST")
				.setSpawnWeight(4).setAreaLimit(6).setGroupLimits(1, 2).setLightDark(false, true);
		ObjectManager.addMob(newMob);

		newMob = new MobInfo(group, "arisaur", EntityArisaur.class, 0x008800, 0x00FF00)
				.setPeaceful(true).setSummonCost(2).setDungeonLevel(-1)
				.addSubspecies(new Subspecies("russet", "uncommon")).addSubspecies(new Subspecies("keppel", "uncommon"));
		newMob.spawnInfo.setSpawnTypes("CREATURE, ANIMAL").setDespawn(false)
				.setSpawnWeight(10).setAreaLimit(12).setGroupLimits(1, 3).setLightDark(true, false).setDungeonWeight(0);
		ObjectManager.addMob(newMob);

		newMob = new MobInfo(group, "warg", EntityWarg.class, 0x321806, 0x68523b)
				.setPeaceful(false).setTameable(true).setSummonCost(4).setDungeonLevel(1)
				.addSubspecies(new Subspecies("ashen", "uncommon")).addSubspecies(new Subspecies("dark", "uncommon"));
		newMob.spawnInfo.setSpawnTypes("MONSTER, BEAST")
				.setSpawnWeight(4).setAreaLimit(3).setGroupLimits(1, 3).setLightDark(false, true);
		ObjectManager.addMob(newMob);

		newMob = new MobInfo(group, "calpod", EntityCalpod.class, 0x996436, 0x3d2013)
				.setPeaceful(false).setSummonCost(4).setDungeonLevel(1)
				.addSubspecies(new Subspecies("verdant", "uncommon")).addSubspecies(new Subspecies("violet", "uncommon"));
		newMob.spawnInfo.setSpawnTypes("MONSTER, BEAST")
				.setSpawnWeight(4).setAreaLimit(6).setGroupLimits(1, 2).setLightDark(false, true);
		ObjectManager.addMob(newMob);


		// Projectiles:
	}

	@Override
	public void registerModels() {
		proxy.registerModels(this.group);
	}

	@Override
	public void registerOres() {

	}

	@Override
	public void addRecipes() {
		GameRegistry.addSmelting(ObjectManager.getItem("arisaurmeatraw"), new ItemStack(ObjectManager.getItem("arisaurmeatcooked"), 1), 0.5f);
	}

	@Override
	public void editVanillaSpawns() {
		EntityRegistry.removeSpawn(EntityZombie.class, EnumCreatureType.MONSTER, this.group.biomes);
		EntityRegistry.removeSpawn(EntitySpider.class, EnumCreatureType.MONSTER, this.group.biomes);
		EntityRegistry.removeSpawn(EntityCreeper.class, EnumCreatureType.MONSTER, this.group.biomes);
	}
}
