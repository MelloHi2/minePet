package luckyfish.programs.minepet.utils.mojangHelpers;

import java.util.UUID;

/**
 * 用户信息
 */
public class UserProperties {
	private UUID uuid;
	private String name;

	public UserProperties(UUID uuid, String name) {
		this.uuid = uuid;
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public UUID getUuid() {
		return uuid;
	}
}
