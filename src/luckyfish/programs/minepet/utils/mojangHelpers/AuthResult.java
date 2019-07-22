package luckyfish.programs.minepet.utils.mojangHelpers;

import java.util.UUID;

/**
 * 登录结果
 */
public final class AuthResult {
	private String accessToken;
	private UUID playerUUID;
	private String playerName;


	public AuthResult(String accessToken, UUID playerUUID, String playerName) {
		this.accessToken = accessToken;
		this.playerUUID = playerUUID;
		this.playerName = playerName;
	}

	public String getAccessToken() {
		return accessToken;
	}

	public UUID getPlayerUUID() {
		return playerUUID;
	}

	public String getPlayerName() {
		return playerName;
	}
}
