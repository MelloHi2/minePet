package luckyfish.programs.minepet.pet;

/**
 * The type of pet
 */
public enum PetType {
	HUMAN,                                                  // 楞

	ZOMBIE,                                                 // 僵尸
	SKELETON,                                               // 小白
	CREEPER,                                                // 苦力怕
	ZOMBIE_VILLAGER,                                        // 僵尸村民
	WITCH,                                                  // 女巫
	ENDERMAN,                                                 // 末影人
	SPIDER(PetAttribute.FAST_CLIMB_WALL),                     // 蜘蛛
	CAVE_SPIDER(PetAttribute.FAST_CLIMB_WALL),                // 洞穴蜘蛛
	GUARDIAN(PetAttribute.ONLY_JUMP),                       // 守卫者
	ELDER_GUARDIAN(PetAttribute.ONLY_JUMP),                 // 远古守卫者
	PIG_ZOMBIE,                                             // 僵尸猪人
	VINDICATOR,                                             // 那个什么，拿斧头乱砍人的那个
	EVOKER,                                                 // 唤膜者
	SLIVERFISH,                                             // 蠹虫
	ENDERMITE,                                              // 末影螨

	SLIME(PetAttribute.ONLY_JUMP),                          // 史莱姆
	MAGMA_CUBE(PetAttribute.ONLY_JUMP),                     // 岩浆怪

	GHAST(PetAttribute.CAN_FLY),                            // 幽翼
	VEX(PetAttribute.CAN_FLY, PetAttribute.SLOWLY_FALL),    // 小鬼
	BLAZE(PetAttribute.CAN_FLY, PetAttribute.SLOWLY_FALL),  // 烈焰楞

	SHULKER(PetAttribute.CANNOT_MOVE),                      // 欠你之辈

	BAT(PetAttribute.CAN_FLY),                              // 蝙蝠

	CHICKEN(PetAttribute.SLOWLY_FALL),                      // 鸡

	COW,                                                    // 牛
	PIG,                                                    // 老王
	SHEEP,                                                  // 羊
	POLAR_BEAR,                                             // 北极熊
	WOLF,                                                   // 狼
	VILLAGER,                                               // 村民


	SNOWMAN,                                                // 雪人
	IRON_GOLEM;                                             // 铁傀儡

	private final PetAttribute[] petAttributes;
	private PetType() {
		petAttributes = new PetAttribute[0];
	}
	private PetType(PetAttribute ... petAttributes) {
		this.petAttributes = petAttributes;
	}

	public enum PetAttribute {
		CAN_FLY,
		SLOWLY_FALL,
		ONLY_JUMP,
		FAST_CLIMB_WALL,
		CANNOT_MOVE
	}
}
