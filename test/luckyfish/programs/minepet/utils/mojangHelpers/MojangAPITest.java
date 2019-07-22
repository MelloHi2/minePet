package luckyfish.programs.minepet.utils.mojangHelpers;

import org.junit.Test;

import java.util.Scanner;

/**
 * luckyfish.programs.minepet.utils.mojangHelpers.MojangAPITest:
 */
public class MojangAPITest {
	@Test
	public void loginWithPassword() throws Exception {
		try {
			System.out.println("请输入你的帐号：");
			Scanner in = new Scanner(System.in);
			String account = in.nextLine();
			System.out.println("请输入你的密码（请不要在有人看着你的屏幕的时候输入！因为我不会做取消并记录输入！）：");
			String password = in.nextLine();

			AuthResult authResult = MojangAPI.loginWithPassword(account, password);
			System.out.println("登录成功！");
			System.out.println("你的accessToken=" + authResult.getAccessToken());
			System.out.println("你的uuid=" + authResult.getPlayerUUID());
			System.out.println("你的用户名=" + authResult.getPlayerName());
		} catch (Throwable t) {
			t.printStackTrace();
		}
	}

	@Test
	public void validateAccessToken() throws Exception {
	}

	@Test
	public void getPlayerSkinInputStream() throws Exception {
	}

}