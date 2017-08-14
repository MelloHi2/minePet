package luckyfish.programs.minepet.utils;

import java.io.*;

/**
 * 资源管理
 * 用于jar包内的资源管理
 */
public class ResourceManager {
	public static InputStream getFileResource(String path) throws FileNotFoundException {
		if (ResourceManager.class.getClassLoader().getResource(path) == null) {
			return new FileInputStream(new File(path));
		}
		if (ResourceManager.class.getClassLoader().getResource(path).getFile().contains("jar!")) {
			return ResourceManager.class.getResourceAsStream(path);
		} else {
			return new FileInputStream(new File(path));
		}
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
