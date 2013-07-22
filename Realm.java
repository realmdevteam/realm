package mods.realm;

import java.io.File;
import java.util.Calendar;
import java.util.Date;

import mods.realm.api.Plants;
import mods.realm.api.temperature.RangedTemperature;
import mods.realm.api.temperature.TemperatureManager;
import mods.realm.block.RealmBlock;
import mods.realm.block.RealmBlockDirt;
import mods.realm.block.RealmBlockGrass;
import mods.realm.block.RealmBlockGreatstone;
import mods.realm.block.RealmBlockLeavesRealmWood;
import mods.realm.block.RealmBlockLiquid;
import mods.realm.block.RealmBlockLiquidFlowing;
import mods.realm.block.RealmBlockLiquidStationary;
import mods.realm.block.RealmBlockLogRealmWood;
import mods.realm.block.RealmBlockOre;
import mods.realm.block.RealmBlockSand;
import mods.realm.block.RealmBlockSaplingRealmWood;
import mods.realm.block.RealmBlockTallgrass;
import mods.realm.block.RealmFlowerBlock;
import mods.realm.dimension.RealmWorldProvider;
import mods.realm.dimension.biome.RealmBiomeGenOcean;
import mods.realm.dimension.biome.RealmBiomeGenPlain;
import mods.realm.dimension.gen.WorldGenRealm;
import mods.realm.dimension.portal.RealmBlockPortalCore;
import mods.realm.dimension.portal.RealmTileEntityPortal;
import mods.realm.entity.EntityBison;
import mods.realm.handlers.RealmBonemealHandler;
import mods.realm.handlers.RealmCreatureSpawnHandler;
import mods.realm.handlers.RealmFuelHandler;
import mods.realm.item.RealmItem;
import mods.realm.item.RealmItemAxe;
import mods.realm.item.RealmItemHoe;
import mods.realm.item.RealmItemOpener;
import mods.realm.item.RealmItemPickaxe;
import mods.realm.item.RealmItemShovel;
import mods.realm.item.RealmItemSword;
import mods.realm.network.PacketHandler;
import mods.realm.proxy.CommonProxy;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.EnumToolMaterial;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.common.EnumHelper;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.Property;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapedOreRecipe;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Init;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.Mod.PreInit;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.registry.EntityRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;


@Mod(name = Realm.name, version = Realm.version, useMetadata = false, modid = Realm.id, dependencies="required-after:Forge@[7.8.0,)")
@NetworkMod(channels = {DefaultProps.NET_CHANNEL_NAME}, packetHandler = PacketHandler.class, clientSideRequired = true, serverSideRequired = true)
public class Realm
{
	@Instance(Realm.id)
	private static Realm instance;
	
	public static Realm getInstance()
	{
		return instance;
	}
	
	@SidedProxy(clientSide = "mods.realm.proxy.ClientProxy", serverSide = "mods.realm.proxy.CommonProxy")
	public static CommonProxy proxy;
	
	public static RealmConfiguration config;
	
	public static final CreativeTabs tabRealm = new RealmTab(12, "The Realm");
	
	public static final String version = "1.0.0";
	public static final String name = "The Realm";
	public static final String id = "TheRealm";
	public static final String consolePrefix = "[Realm] ";
	public static boolean isAprilFools;
	
	
	/** Dimension ID **/
	public static int DimensionID;
	public static int MaxDragon;

	//Rendering ids
	
	//Blocks
	public static Block blockGreatstone;
	/** It means Realm dirt. **/
	public static Block blockDirt;
	/** It means Realm grass. **/
	public static Block blockGrass;
	public static Block blockLeucosand;
	public static Block blockLogRealmWood;
	public static Block blockLeavesRealmWood;
	public static Block blockSaplingRealmWood;
	public static Block blockPlanksRealmWood;
	public static Block blockBells;
	/** It means Realm grass overlay. **/
	public static Block blockTallGrass;
	
	public static Block oreSaphire;
	public static Block oreTopaz;
	public static Block oreGarnet;

	
	public static Block waterStill;
	public static RealmBlockLiquid waterMoving;
	
	
	public static Block blockCobbleGreatstone;
//	public static Block LeucosandBlock;
//	public static Block LeucosandBlock;
	
	public static Block blockPortalCore;
	
	public static Block expeller;
	

	public static Block blockSaphire;
	public static Block blockTopaz;
	public static Block blockGarnet;

		
	
	//Items
	
	public static Item itemOpener;
	
	public static Item itemBell;
	

	public static Item itemIngotSaphire;
	public static Item itemIngotTopaz;
	public static Item itemGarnet;
	
	public static Item itemSwordRealmWood;
	public static Item itemPickaxeRealmWood;
	public static Item itemAxeRealmWood;
	public static Item itemShovelRealmWood;
	public static Item itemHoeRealmWood;
	
	public static Item itemSwordGreatstone;
	public static Item itemPickaxeGreatstone;
	public static Item itemAxeGreatstone;
	public static Item itemSpadeGreatstone;
	public static Item itemHoeGreatstone;

	public static Item itemOverKill;
	public static Item itemDebug;
	
	
	/** Biome's **/
	public static BiomeGenBase biomePlain = null;
	public static BiomeGenBase biomeOcean = null;
	
	@EventHandler
	public void load(FMLPreInitializationEvent evt)
	{
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		isAprilFools = (calendar.get(2)+1 == 4) && (calendar.get(5) == 1);
		
		config = new RealmConfiguration(new File(evt.getModConfigurationDirectory(), "Realm.cfg"));
		try
		{
			config.load();
			
			Property idDim = Realm.config.get("other", "dimensionID", DefaultProps.DimensionID, "This is the id of the dimension change if needed!");
			DimensionID = idDim.getInt();
			
			//Handlers
			MinecraftForge.EVENT_BUS.register(new RealmBonemealHandler());
			MinecraftForge.EVENT_BUS.register(new RealmCreatureSpawnHandler());
			GameRegistry.registerFuelHandler(new RealmFuelHandler());
						
			//Block Registering

			Property idGreatstoneBlock = Realm.config.getTerrainBlock("terrainGen", "greatstone.id", DefaultProps.idGreatstoneBlock, null);
			blockGreatstone = (new RealmBlockGreatstone(idGreatstoneBlock.getInt(), Material.rock)).setHardness(1.5F).setResistance(10.0F).setStepSound(Block.soundStoneFootstep).setUnlocalizedName("greatstone");
			registerBlock(blockGreatstone, "Greatstone");
			
			Property idSoilBlock = Realm.config.getTerrainBlock("terrainGen", "RealmDirt.id", DefaultProps.idSoilBlock, null);
			blockDirt = (new RealmBlockDirt(idSoilBlock.getInt(), Material.ground)).setHardness(0.5F).setStepSound(Block.soundGravelFootstep).setUnlocalizedName("realm_dirt");
			registerBlock(blockDirt, "Realm Soil");

			Property idGrassBlock = Realm.config.getTerrainBlock("terrainGen", "RealmGrass.id", DefaultProps.idGrassBlock, null);
			blockGrass = (new RealmBlockGrass(idGrassBlock.getInt(), Material.ground)).setHardness(0.6F).setStepSound(Block.soundGrassFootstep).setUnlocalizedName("realm_grass");
			registerBlock(blockGrass, "Realm Grass");

			Property idRealmsandBlock = Realm.config.getTerrainBlock("terrainGen", "Realmsand.id", DefaultProps.idRealmsandBlock, null);
			blockLeucosand = (new RealmBlockSand(idRealmsandBlock.getInt(), Material.sand)).setHardness(0.5F).setStepSound(Block.soundSandFootstep).setUnlocalizedName("realm_sand");
			registerBlock(blockLeucosand, "Realm Sand");

			Property idRealmWoodSaplingBlock = Realm.config.getBlock("RealmWoodSaplingBlock.id", DefaultProps.idRealmWoodSaplingBlock);
			blockSaplingRealmWood = (new RealmBlockSaplingRealmWood(idRealmWoodSaplingBlock.getInt())).setHardness(0F).setUnlocalizedName("realmwood_sapling");
			registerBlock(blockSaplingRealmWood, "Realm Wood Sapling");
			
			Property idRealmWoodLogBlock = Realm.config.getBlock("RealmWoodLog.id", DefaultProps.idRealmWoodLogBlock);
			blockLogRealmWood = (new RealmBlockLogRealmWood(idRealmWoodLogBlock.getInt(), Material.wood)).setHardness(2.0F).setStepSound(Block.soundWoodFootstep).setUnlocalizedName("realmwood_log");
			registerBlock(blockLogRealmWood, "Realm Wood Log");

			Property idRealmWoodLeavesBlock = Realm.config.getBlock("RealmWoodLeavesBlock.id", DefaultProps.idRealmWoodLeavesBlock);
			blockLeavesRealmWood = (new RealmBlockLeavesRealmWood(idRealmWoodLeavesBlock.getInt(), Material.leaves)).setLightOpacity(1).setHardness(0.2F).setStepSound(Block.soundGrassFootstep).setUnlocalizedName("realmwood_leaves");
			registerBlock(blockLeavesRealmWood, "Realm Wood Leaves");

			Property idWoodBlock = Realm.config.getBlock("idWoodBlock.id", DefaultProps.idWoodBlock);
			blockPlanksRealmWood = (new RealmBlock(idWoodBlock.getInt(), Material.iron)).setHardness(0.2F).setStepSound(Block.soundWoodFootstep).setUnlocalizedName("realmwood_planks");
			registerBlock(blockPlanksRealmWood, "Realm Wood Planks");

			Property idAsphodelFlowerBlock = Realm.config.getBlock("idAsphodelFlowerBlock.id", DefaultProps.idAsphodelFlowerBlock);
			blockBells = (new RealmFlowerBlock(idAsphodelFlowerBlock.getInt())).setHardness(0.0F).setStepSound(Block.soundGrassFootstep).setUnlocalizedName("yellow_bell");
			registerBlock(blockBells, "Yellow Bells");

			Property idCurlgrassBlock = Realm.config.getBlock("idCurlgrassBlock.id", DefaultProps.idCurlgrassBlock);
			blockTallGrass = new RealmBlockTallgrass(idCurlgrassBlock.getInt()).setHardness(0.0F).setStepSound(Block.soundGrassFootstep).setUnlocalizedName("curlgrass");
			registerBlock(blockTallGrass, "Tallgrass");

			Property idOreSaphireBlock = Realm.config.getBlock("idOreSaphireBlock.id", DefaultProps.idOreSaphireBlock);
			oreSaphire = new RealmBlockOre(idOreSaphireBlock.getInt()).setHardness(3.0F).setResistance(5.0F).setStepSound(Block.soundStoneFootstep).setUnlocalizedName("oreSaphire");
			registerBlock(oreSaphire, "Saphire Ore");

			Property idOreTopazBlock = Realm.config.getBlock("idOreTopazBlock.id", DefaultProps.idOreTopazBlock);
			oreTopaz = new RealmBlockOre(idOreTopazBlock.getInt()).setHardness(3.0F).setResistance(5.0F).setStepSound(Block.soundStoneFootstep).setUnlocalizedName("oreTopaz");
			registerBlock(oreTopaz, "Topaz Ore");
			
			Property idOreGarnetBlock = Realm.config.getBlock("idOreGarnetBlock.id", DefaultProps.idOreGarnetBlock);
			oreGarnet = new RealmBlockOre(idOreGarnetBlock.getInt()).setHardness(3.0F).setResistance(5.0F).setStepSound(Block.soundStoneFootstep).setUnlocalizedName("oreGarnet");
			registerBlock(oreGarnet, "Garnet Ore");

			Property idWaterBlock = Realm.config.getTerrainBlock("terrainGen", "idWaterBlock.id", DefaultProps.idWaterBlock, null);
			waterStill = new RealmBlockLiquidStationary(idWaterBlock.getInt(), Material.water).setHardness(100.0F).setLightOpacity(3).setUnlocalizedName("realm_water");
			registerBlock(waterStill, "Realm Water Still");
			
			Property idWaterFlowingBlock = Realm.config.getTerrainBlock("terrainGen", "idWaterFlowingBlock.id", DefaultProps.idWaterFlowingBlock, null);
			waterMoving = (RealmBlockLiquid) new RealmBlockLiquidFlowing(idWaterFlowingBlock.getInt(), Material.water).setHardness(100.0F).setLightOpacity(3).setUnlocalizedName("realm_water_flow");
			registerBlock(waterMoving, "Realm Water Flowing");
			
			Property idPortalCoreBlock = Realm.config.getBlock("idPortalCoreBlock.id", DefaultProps.idPortalCoreBlock);
			blockPortalCore = new RealmBlockPortalCore(idPortalCoreBlock.getInt(), Material.glass).setHardness(5F).setStepSound(Block.soundGlassFootstep).setUnlocalizedName("portalCore");
			registerBlock(blockPortalCore, "Realm Portal Block");
			
			Property idGreatCobblestoneBlock = Realm.config.getBlock("idGreatCobblestoneBlock.id", DefaultProps.idGreatCobblestone);
			blockCobbleGreatstone = (new RealmBlock(idGreatCobblestoneBlock.getInt(), Material.rock)).setHardness(1.0F).setResistance(10.0F).setStepSound(Block.soundStoneFootstep).setUnlocalizedName("greatstone_cobble");
			registerBlock(blockCobbleGreatstone, "Cobble Greatstone");
		
			
			Property idBlockSaphire = Realm.config.getBlock("idBlockSaphire.id", DefaultProps.idBlockSaphire);
			blockSaphire = new RealmBlock(idBlockSaphire.getInt(), Material.iron).setHardness(3F).setResistance(5F).setStepSound(Block.soundMetalFootstep).setUnlocalizedName("blockSaphire");
			registerBlock(blockSaphire, "Saphire Block");

			Property idBlockTopaz = Realm.config.getBlock("idBlockTopaz.id", DefaultProps.idBlockTopaz);
			blockTopaz = new RealmBlock(idBlockTopaz.getInt(), Material.iron).setHardness(3F).setResistance(5F).setStepSound(Block.soundMetalFootstep).setUnlocalizedName("blockTopaz");
			registerBlock(blockTopaz, "Topaz Block");
			
			
			Property idBlockGarnet = Realm.config.getBlock("idBlockGarnet.id", DefaultProps.idBlockGarnet);
			blockGarnet = new RealmBlock(idBlockGarnet.getInt(), Material.rock).setHardness(3F).setResistance(5F).setStepSound(Block.soundStoneFootstep).setUnlocalizedName("blockGarnet");
			registerBlock(blockGarnet, "Garnet Block");
			


			Block.dragonEgg.setCreativeTab(tabRealm);

			
			
			//Item Registering
			
			Property idOpenerItem = Realm.config.getItem("idOpenerItem.id", DefaultProps.idOpenerItem);
			itemOpener = new RealmItemOpener(idOpenerItem.getInt()).setUnlocalizedName("opener");
			LanguageRegistry.addName(itemOpener, "Realm Opener");


			Property idBellItem = Realm.config.getItem("idBellItem.id", DefaultProps.idBellItem);
			itemBell = new RealmItem(idBellItem.getInt()).setUnlocalizedName("bells");
			LanguageRegistry.addName(itemBell, "Bell");
			

			
			Property idSaphireIngotItem = Realm.config.getItem("idSaphireIngotItem.id", DefaultProps.idSaphireIngotItem);
			itemIngotSaphire = new RealmItem(idSaphireIngotItem.getInt()).setUnlocalizedName("ingotSaphire");
			LanguageRegistry.addName(itemIngotSaphire, "Saphire Ingot");
			
			Property idTopazIngotItem = Realm.config.getItem("idTopazIngotItem.id", DefaultProps.idTopazIngotItem);
			itemIngotTopaz = new RealmItem(idTopazIngotItem.getInt()).setUnlocalizedName("ingotTopaz");
			LanguageRegistry.addName(itemIngotTopaz, "Topaz Ingot");
			
			Property idGarnetItem = Realm.config.getItem("idGarnetItem.id", DefaultProps.idGarnetItem);
			itemGarnet = new RealmItem(idGarnetItem.getInt()).setUnlocalizedName("garnet");
			LanguageRegistry.addName(itemGarnet, "Garnet");
					
			
			
			
			//Tool Registering
			
			EnumToolMaterial REALMWOOD_MAT = EnumHelper.addToolMaterial("REALMWOOD", 0, 59, 2.0F, 0, 15);

			Property idWoodSwordItem = Realm.config.getItem("idWoodSwordItem.id", DefaultProps.idWoodSwordItem);
			itemSwordRealmWood = new RealmItemSword(idWoodSwordItem.getInt(), REALMWOOD_MAT).setUnlocalizedName("swordRealmWood");
			LanguageRegistry.addName(itemSwordRealmWood, "Realm Wood Sword");

			Property idWoodPickaxeItem = Realm.config.getItem("idWoodPickaxeItem.id", DefaultProps.idWoodPickaxeItem);
			itemPickaxeRealmWood = new RealmItemPickaxe(idWoodPickaxeItem.getInt(), REALMWOOD_MAT).setUnlocalizedName("pickaxeRealmWood");
			LanguageRegistry.addName(itemPickaxeRealmWood, "Realm Wood Pickaxe");

			Property idWoodAxeItem = Realm.config.getItem("idWoodAxeItem.id", DefaultProps.idWoodAxeItem);
			itemAxeRealmWood = new RealmItemAxe(idWoodAxeItem.getInt(), REALMWOOD_MAT).setUnlocalizedName("axeRealmWood");
			LanguageRegistry.addName(itemAxeRealmWood, "Realm Wood Axe");

			Property idWoodShovelItem = Realm.config.getItem("idWoodShovelItem.id", DefaultProps.idWoodShovelItem);
			itemShovelRealmWood = new RealmItemShovel(idWoodShovelItem.getInt(), REALMWOOD_MAT).setUnlocalizedName("shovelRealmWood");
			LanguageRegistry.addName(itemShovelRealmWood, "Realm Wood Shovel");
			
			Property idWoodHoeItem = Realm.config.getItem("idWoodHoeItem.id", DefaultProps.idWoodHoeItem);
			itemHoeRealmWood = new RealmItemHoe(idWoodHoeItem.getInt(), REALMWOOD_MAT).setUnlocalizedName("hoeRealmWood");
			LanguageRegistry.addName(itemHoeRealmWood, "Realm Wood Hoe");

			EnumToolMaterial STONE_MAT = EnumHelper.addToolMaterial("PALESTONE", 1, 131, 4.0F, 1, 5);

			Property idStoneSwordItem = Realm.config.getItem("idStoneSwordItem.id", DefaultProps.idStoneSwordItem);
			itemSwordGreatstone = new RealmItemSword(idStoneSwordItem.getInt(), STONE_MAT).setUnlocalizedName("swordGreatstone");
			LanguageRegistry.addName(itemSwordGreatstone, "Realm Stone Sword");

			Property idStonePickaxeItem = Realm.config.getItem("idStonePickaxeItem.id", DefaultProps.idStonePickaxeItem);
			itemPickaxeGreatstone = new RealmItemPickaxe(idStonePickaxeItem.getInt(), STONE_MAT).setUnlocalizedName("pickaxeGreatstone");
			LanguageRegistry.addName(itemPickaxeGreatstone, "Realm Stone Pickaxe");

			Property idStoneAxeItem = Realm.config.getItem("idStoneAxeItem.id", DefaultProps.idStoneAxeItem);
			itemAxeGreatstone = new RealmItemAxe(idStoneAxeItem.getInt(), STONE_MAT).setUnlocalizedName("axeGreatstone");
			LanguageRegistry.addName(itemAxeGreatstone, "Realm Stone Axe");

			Property idStoneShovelItem = Realm.config.getItem("idStoneShovelItem.id", DefaultProps.idStoneShovelItem);
			itemSpadeGreatstone = new RealmItemShovel(idStoneShovelItem.getInt(), STONE_MAT).setUnlocalizedName("shovelGreatstone");
			LanguageRegistry.addName(itemSpadeGreatstone, "Realm Stone Shovel");
			
			Property idStoneHoeItem = Realm.config.getItem("idStoneHoeItem.id", DefaultProps.idStoneHoeItem);
			itemHoeGreatstone = new RealmItemHoe(idStoneHoeItem.getInt(), STONE_MAT).setUnlocalizedName("hoeGreatstone");
			LanguageRegistry.addName(itemHoeGreatstone, "Realm Stone Hoe");
			
			MinecraftForge.setToolClass(itemPickaxeRealmWood, "pickaxe", 0);
	        MinecraftForge.setToolClass(itemAxeRealmWood, "axe", 0);
	        MinecraftForge.setToolClass(itemShovelRealmWood, "shovel", 0);
	        MinecraftForge.setToolClass(itemPickaxeGreatstone, "pickaxe", 1);
	        MinecraftForge.setToolClass(itemAxeGreatstone, "axe", 1);
	        MinecraftForge.setToolClass(itemSpadeGreatstone, "shovel", 1);

			MinecraftForge.setBlockHarvestLevel(oreSaphire, "pickaxe", 1);
			MinecraftForge.setBlockHarvestLevel(oreTopaz, "pickaxe", 2);
			MinecraftForge.setBlockHarvestLevel(oreGarnet, "pickaxe", 2);
		

			MinecraftForge.setBlockHarvestLevel(blockSaphire, "pickaxe", 1);
			MinecraftForge.setBlockHarvestLevel(blockTopaz, "pickaxe", 2);
			MinecraftForge.setBlockHarvestLevel(blockGarnet, "pickaxe", 2);
			
			MinecraftForge.setBlockHarvestLevel(blockGrass, "shovel", 0);
			MinecraftForge.setBlockHarvestLevel(blockDirt, "shovel", 0);
			MinecraftForge.setBlockHarvestLevel(blockLogRealmWood, "axe", 0);
			MinecraftForge.setBlockHarvestLevel(blockPlanksRealmWood, "axe", 0);
			
			//Crafting Registering

			GameRegistry.addRecipe(new ItemStack(itemOpener), new Object[] {"SSS","SDT","TTT", Character.valueOf('S'), Item.sugar, Character.valueOf('T'), Item.ghastTear, Character.valueOf('D'), Item.diamond});
			GameRegistry.addRecipe(new ItemStack(itemOpener), new Object[] {"SSS","SDT","TTT", Character.valueOf('T'), Item.sugar, Character.valueOf('S'), Item.ghastTear, Character.valueOf('D'), Item.diamond});
			GameRegistry.addRecipe(new ItemStack(itemOpener), new Object[] {"SST","SDT","STT", Character.valueOf('S'), Item.sugar, Character.valueOf('T'), Item.ghastTear, Character.valueOf('D'), Item.diamond});
			GameRegistry.addRecipe(new ItemStack(itemOpener), new Object[] {"SST","SDT","STT", Character.valueOf('T'), Item.sugar, Character.valueOf('S'), Item.ghastTear, Character.valueOf('D'), Item.diamond});


			GameRegistry.addRecipe(new ItemStack(itemPickaxeRealmWood), new Object[] {"WWW"," S "," S ", Character.valueOf('S'), Item.stick, Character.valueOf('W'), blockPlanksRealmWood});
			GameRegistry.addRecipe(new ItemStack(itemPickaxeGreatstone), new Object[] {"WWW"," S "," S ", Character.valueOf('S'), Item.stick, Character.valueOf('W'), blockCobbleGreatstone});
			GameRegistry.addRecipe(new ItemStack(itemShovelRealmWood), new Object[] {" W "," S "," S ", Character.valueOf('S'), Item.stick, Character.valueOf('W'), blockPlanksRealmWood});
			GameRegistry.addRecipe(new ItemStack(itemSpadeGreatstone), new Object[] {" W "," S "," S ", Character.valueOf('S'), Item.stick, Character.valueOf('W'), blockCobbleGreatstone});
			GameRegistry.addRecipe(new ItemStack(itemHoeRealmWood), new Object[] {"WW "," S "," S ", Character.valueOf('S'), Item.stick, Character.valueOf('W'), blockPlanksRealmWood});
			GameRegistry.addRecipe(new ItemStack(itemHoeGreatstone), new Object[] {"WW "," S "," S ", Character.valueOf('S'), Item.stick, Character.valueOf('W'), blockCobbleGreatstone});
			GameRegistry.addRecipe(new ItemStack(itemAxeRealmWood), new Object[] {"WW ","WS ", " S ", Character.valueOf('S'), Item.stick, Character.valueOf('W'), blockPlanksRealmWood});
			GameRegistry.addRecipe(new ItemStack(itemAxeGreatstone), new Object[] {"WW ","WS ", " S ", Character.valueOf('S'), Item.stick, Character.valueOf('W'), blockCobbleGreatstone});
			GameRegistry.addRecipe(new ItemStack(itemSwordRealmWood), new Object[] {" W "," W ", " S ", Character.valueOf('S'), Item.stick, Character.valueOf('W'), blockPlanksRealmWood});
			GameRegistry.addRecipe(new ItemStack(itemSwordGreatstone), new Object[] {" W "," W ", " S ", Character.valueOf('S'), Item.stick, Character.valueOf('W'), blockCobbleGreatstone});




			GameRegistry.addRecipe(new ItemStack(blockSaphire), new Object[] {"XXX", "XXX", "XXX", Character.valueOf('X'), itemIngotSaphire});
			GameRegistry.addShapelessRecipe(new ItemStack(itemIngotSaphire, 9), new Object[] {blockSaphire});
			GameRegistry.addRecipe(new ItemStack(blockTopaz), new Object[] {"XXX", "XXX", "XXX", Character.valueOf('X'), itemIngotTopaz});
			GameRegistry.addShapelessRecipe(new ItemStack(itemIngotTopaz, 9), new Object[] {blockTopaz});
			GameRegistry.addRecipe(new ItemStack(blockGarnet), new Object[] {"XXX", "XXX", "XXX", Character.valueOf('X'), itemGarnet});
			GameRegistry.addShapelessRecipe(new ItemStack(itemGarnet, 9), new Object[] {blockGarnet});

			GameRegistry.addShapelessRecipe(new ItemStack(itemBell, 2), new Object[] {blockBells});
			GameRegistry.addShapelessRecipe(new ItemStack(blockPlanksRealmWood, 4), new Object[] {blockLogRealmWood});
			GameRegistry.addShapelessRecipe(new ItemStack(Block.workbench, 1), new Object[] {blockPlanksRealmWood, blockPlanksRealmWood, blockPlanksRealmWood, blockPlanksRealmWood});
			GameRegistry.addShapelessRecipe(new ItemStack(Item.stick, 4), new Object[] {blockPlanksRealmWood, blockPlanksRealmWood});
			//Ore registry
			OreDictionary.registerOre("dyePink", itemBell);
            OreDictionary.registerOre("logWood", blockLogRealmWood);
            OreDictionary.registerOre("treeSapling", blockSaplingRealmWood);
            OreDictionary.registerOre("treeLeaves", blockLeavesRealmWood);
            
            OreDictionary.registerOre("oreTopaz", oreTopaz);
            OreDictionary.registerOre("oreSaphire", oreSaphire);
            OreDictionary.registerOre("oreGarnet", oreGarnet);
            


			//Smelting Registering
			
			GameRegistry.addSmelting(this.oreSaphire.blockID, new ItemStack(this.itemIngotSaphire), 0.7F);
			GameRegistry.addSmelting(this.oreTopaz.blockID, new ItemStack(this.itemIngotTopaz), 1.0F);
			
			
			
			//Entity Registering
			
			GameRegistry.registerTileEntity(RealmTileEntityPortal.class, "RealmTileEntityPortal");

			
//			TickRegistry.registerTickHandler(new TemperatureTickHandler(), Side.SERVER);
			proxy.registerRenderers();
			proxy.installSounds();
			
			//Temperature API
			TemperatureManager.addBlockTemperature(new RangedTemperature(blockGreatstone.blockID, -273, 300));
		}
		finally
		{
			config.save();
		}
	}
	
	@EventHandler
	public void initialize(FMLInitializationEvent evt)
	{
		Plants.addGrassPlant(blockTallGrass, 0, 30);
		Plants.addGrassPlant(blockBells, 0, 10);
		
//		new LiquidStacks();
//		CoreProxy.proxy.addAnimation();
//		LiquidManager.liquids.add(new LiquidData(LiquidStacks.rawCandy, new ItemStack(rawCandyBucket), new ItemStack(Item.bucketEmpty)));
//		LiquidManager.liquids.add(new LiquidData(LiquidStacks.milk, new ItemStack(Item.bucketMilk), new ItemStack(Item.bucketEmpty)));

//		CoreProxy.proxy.initializeRendering();
//		CoreProxy.proxy.initializeEntityRendering();
		
		
		/** Register WorldProvider for Dimension **/
		DimensionManager.registerProviderType(DimensionID, RealmWorldProvider.class, true);
		DimensionManager.registerDimension(DimensionID, DimensionID);
		
		
		biomePlain = new RealmBiomeGenPlain(25);
		biomeOcean = new RealmBiomeGenOcean(26);
		
		
		GameRegistry.registerWorldGenerator(new WorldGenRealm());

	EntityRegistry.registerGlobalEntityID(EntityBison.class, "Bison", EntityRegistry.findGlobalUniqueEntityId(), 0x646464, 0x3A3A3A);
	LanguageRegistry.instance().addStringLocalization("entity.Bison.name", "en_US", "AtmoBison");
		
	}
	
	public static void registerBlock(Block block, String name)
	{
		GameRegistry.registerBlock(block, DefaultProps.modId+":"+block.getUnlocalizedName().substring(5));
		LanguageRegistry.addName(block, name);
	}
	
	public static boolean isHeatWave()
	{
		return false;
	}
	

}