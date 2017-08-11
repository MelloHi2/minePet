package luckyfish.programs.minepet.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * 资源管理
 * 用于jar包内的资源管理
 */
public class ResourceManager {
	public static InputStream getFileResource(String path) {
		return ResourceManager.class.getResourceAsStream(path);
	}
	public static String getFileContents(InputStream fileResource) throws IOException {
		StringBuilder builder = new StringBuilder();
		BufferedReader reader = new BufferedReader(new InputStreamReader(fileResource));

		String s;
		while ((s = reader.readLine()) != null) {
			builder.append(s).append(System.getProperty("line.separator"));
		}

		return builder.toString();
	}
}
