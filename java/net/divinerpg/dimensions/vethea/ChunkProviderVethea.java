package net.divinerpg.dimensions.vethea;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import net.divinerpg.dimensions.vethea.all.Bow;
import net.divinerpg.dimensions.vethea.all.Hook;
import net.divinerpg.dimensions.vethea.all.Mushroom;
import net.divinerpg.dimensions.vethea.all.Pickaxe;
import net.divinerpg.dimensions.vethea.all.Pointedsquare;
import net.divinerpg.dimensions.vethea.all.Ring;
import net.divinerpg.dimensions.vethea.all.Sword;
import net.divinerpg.dimensions.vethea.all.Trident;
import net.divinerpg.dimensions.vethea.all.WorldGenConeUp;
import net.divinerpg.dimensions.vethea.all.WorldGenVetheanPillar;
import net.divinerpg.dimensions.vethea.layer1.Crypt1;
import net.divinerpg.dimensions.vethea.layer1.Crypt2;
import net.divinerpg.dimensions.vethea.layer1.Tree4;
import net.divinerpg.dimensions.vethea.layer1.Tree5;
import net.divinerpg.dimensions.vethea.layer1.Tree6;
import net.divinerpg.dimensions.vethea.layer2.HiveNest;
import net.divinerpg.dimensions.vethea.layer2.Pyramid1;
import net.divinerpg.dimensions.vethea.layer2.Pyramid2;
import net.divinerpg.dimensions.vethea.layer2.Tree3;
import net.divinerpg.dimensions.vethea.layer3.KarosMadhouse;
import net.divinerpg.dimensions.vethea.layer3.QuadroticPost;
import net.divinerpg.dimensions.vethea.layer3.Tree7;
import net.divinerpg.dimensions.vethea.layer3.Tree8;
import net.divinerpg.dimensions.vethea.layer3.WorldGenLayer3SmallTree;
import net.divinerpg.dimensions.vethea.layer4.Evergarden;
import net.divinerpg.dimensions.vethea.layer4.RaglokChamber;
import net.divinerpg.dimensions.vethea.layer4.Layer4Tree1;
import net.divinerpg.dimensions.vethea.layer4.Layer4Tree2;
import net.divinerpg.dimensions.vethea.layer4.WreckHall;
import net.divinerpg.dimensions.vethea.village.WorldGenVillageIsland;
import net.divinerpg.utils.blocks.VetheaBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.BlockSand;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.util.IProgressUpdate;
import net.minecraft.world.ChunkPosition;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.MapGenBase;
import net.minecraft.world.gen.NoiseGenerator;
import net.minecraft.world.gen.NoiseGeneratorOctaves;
import net.minecraft.world.gen.NoiseGeneratorPerlin;
import net.minecraft.world.gen.feature.WorldGenMinable;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.terraingen.PopulateChunkEvent;

public class ChunkProviderVethea implements IChunkProvider {

	private Random rand;
	private NoiseGeneratorOctaves noiseGen1;
	private NoiseGeneratorOctaves noiseGen2;
	private NoiseGeneratorOctaves noiseGen3;
	private NoiseGeneratorPerlin noiseGen4;
	public NoiseGeneratorOctaves noiseGen5;
	public NoiseGeneratorOctaves noiseGen6;
	public NoiseGeneratorOctaves mobSpawnerNoise;
	private World worldObj;
	private double[] noiseArray;
	private double[] stoneNoise = new double[256];
	private MapGenBase caveGenerator = new MapGenFloorCrystals();
	private BiomeGenBase[] biomesForGeneration;
	private final ArrayList<WorldGenerator> items;
	private final ArrayList<WorldGenerator> floatingTrees;
	private final ArrayList<WorldGenerator> crypts;
	private final ArrayList<WorldGenerator> l1Trees;
	private final ArrayList<WorldGenerator> pyramids;
	private final ArrayList<WorldGenerator> l2Trees;
	private final ArrayList<WorldGenerator> l3Trees;
	private final ArrayList<WorldGenerator> l4Trees;
	private final ArrayList<WorldGenerator> l3Altars;
	private final ArrayList<WorldGenerator> l4Altars;
	//private final WorldGenerator layer3TreeBig;
	private final MapGenFloorCrystals firecrystals = new MapGenFloorCrystals();
	private final WorldGenConeUp ceilingTexture;
	private final WorldGenerator cracklespikes;
	private final WorldGenerator fernites;
	private final WorldGenerator bulatobes;
	private final WorldGenerator shinegrass;
	//private final WorldGenerator shimmers;
	//private final WorldGenerator dreamglows;
	private final WorldGenerator greenGemTops;
	//private final WorldGenerator purpleGemTops;
	private final WorldGenerator yellowDulahs;
	private final WorldGenerator greenDulahs;
	private final WorldGenerator hungerVillages;
	private final WorldGenerator grassClusters;

	double[] noise3;
	double[] noise1;
	double[] noise2;
	double[] noise5;
	double[] noise6;

	float[] parabolicField;
	int[][] field_73219_j = new int[32][32];

	public ChunkProviderVethea(World par1World, long par2) {
		this.worldObj = par1World;
		this.rand = new Random(par2);
		this.noiseGen1 = new NoiseGeneratorOctaves(this.rand, 16);
		this.noiseGen2 = new NoiseGeneratorOctaves(this.rand, 16);
		this.noiseGen3 = new NoiseGeneratorOctaves(this.rand, 8);
		this.noiseGen4 = new NoiseGeneratorPerlin(this.rand, 4);
		this.noiseGen5 = new NoiseGeneratorOctaves(this.rand, 10);
		this.noiseGen6 = new NoiseGeneratorOctaves(this.rand, 16);
		this.mobSpawnerNoise = new NoiseGeneratorOctaves(this.rand, 8);

		NoiseGenerator[] noiseGens = {noiseGen1, noiseGen2, noiseGen3, noiseGen4, noiseGen5, noiseGen6, mobSpawnerNoise};
		this.noiseGen4 = (NoiseGeneratorPerlin)noiseGens[3];
		this.mobSpawnerNoise = (NoiseGeneratorOctaves)noiseGens[6];

		this.items = new ArrayList(8);
		items.add(new Bow());
		items.add(new Hook());
		items.add(new Mushroom());
		items.add(new Pickaxe());
		items.add(new Pointedsquare());
		items.add(new Ring());
		items.add(new Sword());
		items.add(new Trident());

		this.floatingTrees = new ArrayList(7);
		//floatingTrees.add(new TreeFloating1());
		//floatingTrees.add(new TreeFloating2());
		//floatingTrees.add(new TreeFloating3());
		//floatingTrees.add(new FloatingTree4());
		//floatingTrees.add(new FloatingTree5());
		//floatingTrees.add(new FloatingTree6());
		//floatingTrees.add(new FloatingTree7());

		this.l1Trees = new ArrayList(3);
		l1Trees.add(new Tree4());
		l1Trees.add(new Tree5());
		l1Trees.add(new Tree6());
		
		this.l2Trees = new ArrayList(2);
		l1Trees.add(new Tree3());
		l1Trees.add(new Tree6());
		
		this.crypts = new ArrayList(2);
		crypts.add(new Crypt1());
		crypts.add(new Crypt2());     
		
		ceilingTexture = new WorldGenConeUp();
		hungerVillages = new WorldGenVillageIsland();
		grassClusters = new WorldGenMinable(VetheaBlocks.dreamGrass, 16, VetheaBlocks.dreamStone);
		
		this.pyramids = new ArrayList(3);
		pyramids.add(new Pyramid1());
		pyramids.add(new Pyramid2());
		pyramids.add(new HiveNest());

		this.l3Trees = new ArrayList(3);
		l3Trees.add(new Tree7());
		l3Trees.add(new Tree8());
		l3Trees.add(new WorldGenLayer3SmallTree(false));

		this.l3Altars = new ArrayList(2);
		l3Altars.add(new QuadroticPost());
		l3Altars.add(new KarosMadhouse());

		this.l4Altars = new ArrayList(3);
		l4Altars.add(new Evergarden());
		l4Altars.add(new RaglokChamber());
		l4Altars.add(new WreckHall());
		
		this.l4Trees = new ArrayList(2);
		l4Trees.add(new Layer4Tree1());
		l4Trees.add(new Layer4Tree2());

		
		//layer3TreeBig = new WorldGenLayer3BigTree(false);

		cracklespikes = new WorldGenVetheanFlower(VetheaBlocks.cracklespike);
		fernites = new WorldGenVetheanFlower(VetheaBlocks.fernite);
		bulatobes = new WorldGenVetheanFlower(VetheaBlocks.bulatobe);
		shinegrass = new WorldGenVetheanFlower(VetheaBlocks.shineGrass);
		//shimmers = new WorldGenVetheanFlower(VetheaBlocks.shimmer);
		//dreamglows = new WorldGenVetheanFlower(VetheaBlocks.dreamglow);
		greenGemTops = new WorldGenVetheanFlower(VetheaBlocks.gemtopGreen);
		//purpleGemTops = new WorldGenVetheanFlower(VetheaBlocks.gemtopPurple);
		yellowDulahs = new WorldGenVetheanFlower(VetheaBlocks.yellowDulah);
		greenDulahs = new WorldGenVetheanFlower(VetheaBlocks.greenDulah);
		
	}

	public void generateTerrain(int i, int j, Block[] b) {
		VetheanChunkBuilder builder = new VetheanChunkBuilder();
		VetheanChunkBuilder.toTerrainArray(builder.buildChunk(), b);
	}

	@Override
	public Chunk loadChunk(int par1, int par2) {
		return this.provideChunk(par1, par2);
	}

	@Override
	public Chunk provideChunk(int par1, int par2) {
		this.rand.setSeed((long)par1 * 341873128712L + (long)par2 * 132897987541L);
		Block[] block = new Block[65536];
		byte[] by = new byte[65536];
		this.generateTerrain(par1, par2, block);
		this.biomesForGeneration = this.worldObj.getWorldChunkManager().loadBlockGeneratorData(this.biomesForGeneration, par1 * 16, par2 * 16, 16, 16);
		this.firecrystals.func_151539_a(this, this.worldObj, par1, par2, block);
		Chunk var4 = new Chunk(this.worldObj, block, by, par1, par2);
		byte[] var5 = var4.getBiomeArray();
		for (int var6 = 0; var6 < var5.length; ++var6) {
			var5[var6] = (byte)this.biomesForGeneration[var6].biomeID;
		}

		var4.generateSkylightMap();
		return var4;
	}

	public boolean chunkExists(int par1, int par2) {
		return true;
	}

	@Override
	public void populate(IChunkProvider par1IChunkProvider, int par2, int par3) {
		
		int var4 = par2 * 16;
		int var5 = par3 * 16;
		BiomeGenBase var6 = this.worldObj.getBiomeGenForCoords(var4 + 16, var5 + 16);
		this.rand.setSeed(this.worldObj.getSeed());
		long var7 = this.rand.nextLong() / 2L * 2L + 1L;
		long var9 = this.rand.nextLong() / 2L * 2L + 1L;
		this.rand.setSeed((long)par2 * var7 + (long)par3 * var9 ^ this.worldObj.getSeed());
		boolean var11 = false;
		int var12;
		int var13;
		int var14;

		
		/*for (int i = 0; i < 3; i++) {
			var12 = var4 + this.rand.nextInt(16) + 8;
			var13 = 64;

			var14 = var5 + this.rand.nextInt(16) + 8;
			(ceilingTexture).generate(this.worldObj, this.rand, var12, var13, var14, this.rand.nextInt(3)+1);
		}*/
		
				if(this.rand.nextInt(5)==0)greenGemTops.generate(worldObj, rand, var4, 17, var5);
				//if(this.rand.nextInt(5)==0)purpleGemTops.generate(worldObj, rand, var4, 0, var5);
				if(this.rand.nextInt(5)==0)yellowDulahs.generate(worldObj, rand, var4, 17, var5);
				if(this.rand.nextInt(5)==0)greenDulahs.generate(worldObj, rand, var4, 17, var5);

				if (this.rand.nextInt(32) == 0) {
		            var12 = var4 + this.rand.nextInt(16);
		            var13 = 17 - this.rand.nextInt(2);
		            var14 = var5 + this.rand.nextInt(16);
		            (this.items.get(this.rand.nextInt(7))).generate(this.worldObj, this.rand, var12, var13, var14);
		        }
		        
		        if (this.rand.nextInt(500) == 0) {
		            var12 = var4 + this.rand.nextInt(16) + 8;
		            var13 = 40;
		            var14 = var5 + this.rand.nextInt(16) + 8;
		            //(hungerVillages).generate(this.worldObj, this.rand, var12, var13, var14);//TODO add hunger
		        }
				
		        for (int i = 0; i < 1; i++) {
		            var12 = var4 + this.rand.nextInt(16) + 8;
		            var13 = 17;
		            var14 = var5 + this.rand.nextInt(16) + 8;
		            //(new WorldGenLayer1Forest(false)).generate(this.worldObj, this.rand, var12, var13, var14);
		        }
		        
		        if (this.rand.nextInt(250) == 0) {
		            var12 = var4 + this.rand.nextInt(16) + 8;
		            var13 = 13;
		            var14 = var5 + this.rand.nextInt(16) + 8;
		            (crypts.get(this.rand.nextInt(2))).generate(this.worldObj, this.rand, var12, var13, var14);//TODO add crypt keeper
		        }
		        
		        if (this.rand.nextInt(250) == 0) {
		            var12 = var4 + this.rand.nextInt(16) + 8;
		            var13 = 17;
		            var14 = var5 + this.rand.nextInt(16) + 8;
		            (l1Trees.get(this.rand.nextInt(2))).generate(this.worldObj, this.rand, var12, var13, var14);
		        }
		        
				 // Layer 2
				 
				/*for (int i = 0; i < 3; i++) {
					var12 = var4 + this.rand.nextInt(16) + 8;
					var13 = 96;
					var14 = var5 + this.rand.nextInt(16) + 8;
					 (ceilingTexture).generate(this.worldObj, this.rand, var12, var13, var14, this.rand.nextInt(3)+1);
				}*/
		        
				/*if(this.rand.nextInt(32) == 0) {
					var12 = var4 + rand.nextInt(16);
					var13 = 64;
					var14 = var5 + rand.nextInt(16);
					(this.items.get(this.rand.nextInt(6))).generate(this.worldObj, rand, var12, var13, var14);
				}

				if (this.rand.nextInt(32) == 0) {
					var12 = var4 + this.rand.nextInt(16) + 8;
					var13 = 64;
					var14 = var5 + this.rand.nextInt(16) + 8;
					while(this.worldObj.isAirBlock(var12, var13, var14)) {
						var13--;
					}
					infusion.generate(this.worldObj, this.rand, var12, var13, var14);
				}

				for (int i = 0; i < 3; ++i) {
					var12 = var4 + this.rand.nextInt(16);
					var13 = 75;
					var14 = var5 + this.rand.nextInt(16);
					(this.floatingTrees.get(this.rand.nextInt(6))).generate(this.worldObj, rand, var12, var13, var14);
				}

				if (this.rand.nextInt(2) == 0) {
					var12 = var4 + this.rand.nextInt(16) + 8;
					var13 = 64;
					var14 = var5 + this.rand.nextInt(16) + 8;
					while(!this.worldObj.isAirBlock(var12, var13, var14)) {
						var13++;
					}
					 (this.lamps.get(this.rand.nextInt(2))).generate(this.worldObj, this.rand, var12, var13 - 2, var14);
				}

				if (this.rand.nextInt(250) == 0) {
					var12 = var4 + this.rand.nextInt(16) + 8;
					var13 = 64;
					var14 = var5 + this.rand.nextInt(16) + 8;
					while(!this.worldObj.isAirBlock(var12, var13, var14)) {
						var13++;
					}
					(pyramids.get(this.rand.nextInt(3))).generate(this.worldObj, this.rand, var12, var13, var14);//Add the mobs
				}
				
				for (int i = 0; i < 3; i++) {
					var12 = var4 + this.rand.nextInt(16) + 8;
					var13 = 64;
					var14 = var5 + this.rand.nextInt(16) + 8;
					(new WorldGenLayer2Forest(false)).generate(this.worldObj, this.rand, var12, var13, var14);
				}

				if (this.rand.nextInt(10) == 0) {
					var12 = var4 + this.rand.nextInt(16) + 8;
					var13 = 64;
					var14 = var5 + this.rand.nextInt(16) + 8;
					(fernites).generate(this.worldObj, this.rand, var12, var13, var14);
				}

				if (this.rand.nextInt(10) == 0) {
					var12 = var4 + this.rand.nextInt(16) + 8;
					var13 = 64;
					var14 = var5 + this.rand.nextInt(16) + 8;
					//(dreamglows).generate(this.worldObj, this.rand, var12, var13, var14);
				}

				if (this.rand.nextInt(10) == 0) {
					var12 = var4 + this.rand.nextInt(16) + 8;
					var13 = 64;
					var14 = var5 + this.rand.nextInt(16) + 8;
					//(shimmers).generate(this.worldObj, this.rand, var12, var13, var14);
				}*/
				

				/*
				  * layer 3
				  */
				/*for (int i = 0; i < 3; i++) {
					var12 = var4 + this.rand.nextInt(16) + 8;
					var13 = 192;
					var14 = var5 + this.rand.nextInt(16) + 8;
					//(ceilingTexture).generate(this.worldObj, this.rand, var12, var13, var14, this.rand.nextInt(3)+1);
				}

				if (this.rand.nextInt(16) == 0) {
					var12 = var4 + this.rand.nextInt(16) + 8;
					var13 = 148;
					var14 = var5 + this.rand.nextInt(16) + 8;
					//pillar.generate(this.worldObj, this.rand, var12, var13, var14);
				}

				for (int i = 0; i < 3; i++) {
					var12 = var4 + this.rand.nextInt(16) + 8;
					var13 = 148;
					var14 = var5 + this.rand.nextInt(16) + 8;
					//(new WorldGenLayer1Forest(false)).generate(this.worldObj, this.rand, var12, var13, var14);
				}

				if (this.rand.nextInt(32) == 0) {
					var12 = var4 + rand.nextInt(16);
					var13 = 148;
					var14 = var5 + rand.nextInt(16);
					//(this.items.get(this.rand.nextInt(7))).generate(this.worldObj, rand, var12, var13, var14);
				}


				if (this.rand.nextInt(32) == 0) {
					var12 = var4 + this.rand.nextInt(16) + 8;
					var13 = 148;
					var14 = var5 + this.rand.nextInt(16) + 8;
					while(this.worldObj.isAirBlock(var12, var13, var14)) {
						var13--;
					}
					infusion.generate(this.worldObj, this.rand, var12, var13, var14);
				}

				for (int i = 0; i < 3; ++i) {
					var12 = var4 + this.rand.nextInt(16) + 8;
					var13 = 158;
					var14 = var5 + this.rand.nextInt(16) + 8;
					//(this.floatingTrees.get(this.rand.nextInt(6))).generate(this.worldObj, this.rand, var12, var13, var14);
				}

				if (this.rand.nextInt(2) == 0) {
					var12 = var4 + this.rand.nextInt(16) + 8;
					var13 = 19 + 128;
					var14 = var5 + this.rand.nextInt(16) + 8;
					while(!this.worldObj.isAirBlock(var12, var13, var14)) {
						var13++;
					}
					(this.lamps.get(this.rand.nextInt(2))).generate(this.worldObj, this.rand, var12, var13 - 2, var14);
				}

				if (this.rand.nextInt(250) == 0) {
					var12 = var4 + this.rand.nextInt(16) + 8;
					var13 = 144 ;
					var14 = var5 + this.rand.nextInt(16) + 8;
					while(!this.worldObj.isAirBlock(var12, var13, var14)) {
						var13++;
					}
					//(l3Altars.get(this.rand.nextInt(2))).generate(this.worldObj, this.rand, var12, var13, var14);
				}

				if (this.rand.nextInt(10) == 0) {
					var12 = var4 + this.rand.nextInt(16) + 8;
					var13 = 20 + 128;
					var14 = var5 + this.rand.nextInt(16) + 8;
					(shinegrass).generate(this.worldObj, this.rand, var12, var13, var14);
				}

				if (this.rand.nextInt(10) == 0) {
					var12 = var4 + this.rand.nextInt(16) + 8;
					var13 = 20 + 128;
					var14 = var5 + this.rand.nextInt(16) + 8;
					(cracklespikes).generate(this.worldObj, this.rand, var12, var13, var14);
				}

				if (this.rand.nextInt(10) == 0) {
					var12 = var4 + this.rand.nextInt(16) + 8;
					var13 = 20 + 128;
					var14 = var5 + this.rand.nextInt(16) + 8;
					(bulatobes).generate(this.worldObj, this.rand, var12, var13, var14);
				}

				   //Layer 4
				   
				if (this.rand.nextInt(32) == 0) {
					var12 = var4 + rand.nextInt(16);
					var13 = 160;
					var14 = var5 + rand.nextInt(16);
					(this.items.get(this.rand.nextInt(7))).generate(this.worldObj, rand, var12, var13, var14);
				}

				if (this.rand.nextInt(32) == 0) {
					var12 = var4 + this.rand.nextInt(16) + 8;
					var13 = 160;
					var14 = var5 + this.rand.nextInt(16) + 8;
					while(this.worldObj.isAirBlock(var12, var13, var14)) {
						var13--;
					}
					infusion.generate(this.worldObj, this.rand, var12, var13, var14);
				}

				if (this.rand.nextInt(2) == 0) {
					var12 = var4 + this.rand.nextInt(16) + 8;
					var13 = 160;
					var14 = var5 + this.rand.nextInt(16) + 8;
					while(!this.worldObj.isAirBlock(var12, var13, var14)) {
						var13++;
					}
					(this.lamps.get(this.rand.nextInt(2))).generate(this.worldObj, this.rand, var12, var13 - 2, var14);
				}

				for (int i = 0; i < 5; i++) {
					var12 = var4 + this.rand.nextInt(16) + 8;
					var13 = 160;
					var14 = var5 + this.rand.nextInt(16) + 8;
					(new WorldGenLayer2Forest(false)).generate(this.worldObj, this.rand, var12, var13, var14);
				}

				for (int i = 0; i < 3; ++i) {
					var12 = var4 + this.rand.nextInt(16) + 8;
					var13 = 170 + this.rand.nextInt(23);
					var14 = var5 + this.rand.nextInt(16) + 8;
					(this.floatingTrees.get(this.rand.nextInt(6))).generate(this.worldObj, this.rand, var12, var13, var14);
				}

				if (this.rand.nextInt(150) == 0) {
					var12 = var4 + this.rand.nextInt(16) + 8;
					var13 = 158;
					var14 = var5 + this.rand.nextInt(16) + 8;
					while(!this.worldObj.isAirBlock(var12, var13, var14)) {
						var13++;
					}
					(l4Altars.get(this.rand.nextInt(3))).generate(this.worldObj, this.rand, var12, var13, var14);
				}

				/*if (this.rand.nextInt(150) == 0) {
					var12 = var4 + this.rand.nextInt(16) + 8;
					var13 = 19 + 196;
					var14 = var5 + this.rand.nextInt(16) + 8;  
					while(!this.worldObj.isAirBlock(var12, var13, var14)) {
						var13++;
					}
					(new Layer4MassiveTree(false)).generate(this.worldObj, this.rand, var12, var13, var14);
				}*/

				/*if (this.rand.nextInt(10) == 0) {
					var12 = var4 + this.rand.nextInt(16) + 8;
					var13 = 212;
					var14 = var5 + this.rand.nextInt(16) + 8;
					//(shimmers).generate(this.worldObj, this.rand, var12, var13, var14);
				}

				if (this.rand.nextInt(10) == 0) {
					var12 = var4 + this.rand.nextInt(16) + 8;
					var13 = 212;
					var14 = var5 + this.rand.nextInt(16) + 8;
					(shinegrass).generate(this.worldObj, this.rand, var12, var13, var14);
				}

				if (this.rand.nextInt(10) == 0) {
					var12 = var4 + this.rand.nextInt(16) + 8;
					var13 = 212;
					var14 = var5 + this.rand.nextInt(16) + 8;
					//(dreamglows).generate(this.worldObj, this.rand, var12, var13, var14);
				}*/

				MinecraftForge.EVENT_BUS.post(new PopulateChunkEvent.Post(par1IChunkProvider, worldObj, rand, par2, par3, var11));
				BlockSand.fallInstantly = false;
	}

	public boolean saveChunks(boolean par1, IProgressUpdate par2IProgressUpdate) {
		return true;
	}

	public boolean canSave() {
		return true;
	}

	public String makeString() {
		return "Vethea";
	}

	@Override
	public List getPossibleCreatures(EnumCreatureType enumcreaturetype, int i, int j, int k) {
		BiomeGenBase var5 = this.worldObj.getBiomeGenForCoords(i, k);
		return var5 == null ? null : var5.getSpawnableList(enumcreaturetype);
	}

	public int getLoadedChunkCount() {
		return 0;
	}

	public void recreateStructures(int par1, int par2) { }

	@Override
	public boolean unloadQueuedChunks() {
		return false;
	}

	@Override
	public void saveExtraData() { }

	@Override
	public ChunkPosition func_147416_a(World var1, String var2, int var3, int var4, int var5) {
		return null;
	}
}