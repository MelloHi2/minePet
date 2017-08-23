package luckyfish.programs.minepet.utils.mojangHelpers;

import com.google.gson.Gson;
import luckyfish.programs.minepet.utils.mojangHelpers.exceptions.InvalidCredentialsException;
import luckyfish.programs.minepet.utils.mojangHelpers.exceptions.TooManyRequestsException;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.Base64;
import java.util.List;
import java.util.UUID;

/**
 * 关于mojang的一些杂七杂八的玩意=q=
 */
public final class MojangAPI {
	private static final String mojangProfileAPI = "https://sessionserver.mojang.com/session/minecraft/profile/";

	private static final String mojangUserPropertiesAPI = "https://api.mojang.com/users/profiles/minecraft/$username?at=$timestamp";

	private static final String mojangAuthAPI = "https://authserver.mojang.com/authenticate";
	private static final String mojangValidateAPI = "https://authserver.mojang.com/validate";

	private MojangAPI() {
		throw new UnsupportedOperationException();
	}

	public static AuthResult loginWithPassword(String accountEmailBox, String password) throws IOException, InvalidCredentialsException, TooManyRequestsException {
		AuthPayload payload = new AuthPayload();
		payload.agent = new AuthPayload.Agent();
		payload.password = password;
		payload.requestUser = true;
		payload.username = accountEmailBox;

		Gson gson = new Gson();
		String s = gson.toJson(payload);

		HttpURLConnection connection = (HttpURLConnection) new URL(mojangAuthAPI).openConnection();
		connection.setRequestMethod("POST");
		connection.setRequestProperty("Content-Type", "application/json");
		connection.setDoInput(true);
		connection.setDoOutput(true);

		BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(connection.getOutputStream()));
		writer.write(s);
		writer.flush();

		connection.connect();
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			StringBuilder builder = new StringBuilder();

			for (String str = ""; str != null; str = reader.readLine()) {
				builder.append(str).append(System.getProperty("line.separator"));
			}
			AuthResponse response = gson.fromJson(builder.toString(), AuthResponse.class);

			String accessToken = response.accessToken;
			UUID uuid = getUserProperties(response.selectedProfile.name, System.currentTimeMillis()).getUuid();
			String playerName = response.selectedProfile.name;

			return new AuthResult(accessToken, uuid, playerName);
		} catch (IOException e) {
			Gson g = new Gson();
			ErrorResponse errorResponse = g.fromJson(new InputStreamReader(connection.getErrorStream()), ErrorResponse.class);

			throwSuckingException(connection, errorResponse);
			return null;
		}
	}
	public static String validateAccessToken(String accessToken) throws IOException, InvalidCredentialsException, TooManyRequestsException {
		URL url = new URL(mojangValidateAPI);

		HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
		urlConnection.setRequestMethod("POST");
		urlConnection.setConnectTimeout(5000);
		urlConnection.setDoInput(true);
		urlConnection.setDoOutput(true);
		urlConnection.setUseCaches(false);
		urlConnection.setRequestProperty("Content-Type", "application/json");

		Gson gson = new Gson();
		ValidatePayload validatePayload = new ValidatePayload();
		validatePayload.accessToken = accessToken;
		String payload = gson.toJson(accessToken);

		urlConnection.connect();
		try {
			urlConnection.getOutputStream().write(payload.getBytes());
			urlConnection.getOutputStream().flush();

//			if (urlConnection.getResponseCode() == HttpURLConnection.HTTP_NO_CONTENT) {
			return accessToken;
//			}
		} catch (IOException e) {
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getErrorStream()));
			ErrorResponse errorResponse = gson.fromJson(bufferedReader, ErrorResponse.class);

			throwSuckingException(urlConnection, errorResponse);
			return null;
		}
	}

	private static void throwSuckingException(HttpURLConnection urlConnection, ErrorResponse errorResponse) throws InvalidCredentialsException, TooManyRequestsException, IOException {
		if (urlConnection.getResponseCode() == HttpURLConnection.HTTP_FORBIDDEN) {
			throw new InvalidCredentialsException(errorResponse.errorMessage);
		} else if (urlConnection.getResponseCode() == 429) {
			throw new TooManyRequestsException();
		} else {
			throw new IllegalStateException(errorResponse.errorMessage);
		}
	}

	public static InputStream getPlayerSkinInputStream(UUID playerUUID) throws IOException, InvalidCredentialsException, TooManyRequestsException {
		String target = mojangProfileAPI + playerUUID.toString().replaceAll("-", "") + "?unsigned=false";
//		System.out.println(target);

		URL u = new URL(target);
		HttpURLConnection urlConnection = (HttpURLConnection) u.openConnection();
		urlConnection.setRequestMethod("GET");
		urlConnection.setConnectTimeout(5000);
		urlConnection.setDoInput(true);
		urlConnection.setUseCaches(true);

		urlConnection.connect();
		StringBuilder stringBuilder = new StringBuilder();
		try {
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
			String aLine;
			while ((aLine = bufferedReader.readLine()) != null) {
				stringBuilder.append(aLine).append(System.getProperty("line.separator"));
			}
		} catch (IOException e) {
			Gson gson = new Gson();
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getErrorStream()));
			ErrorResponse errorResponse = gson.fromJson(bufferedReader, ErrorResponse.class);
			throwSuckingException(urlConnection, errorResponse);
		}
		String str = stringBuilder.toString();
//		System.out.println(str);

		Gson gson = new Gson();
		TextureProfile profile = gson.fromJson(str, TextureProfile.class);

		Base64.Decoder decoder = Base64.getDecoder();
		String s = new String(decoder.decode(profile.properties.get(0).value));

		ProfileProperty property = gson.fromJson(s, ProfileProperty.class);
		URL skinURL = new URL(property.textures.SKIN.url);
		URLConnection skinConnection = skinURL.openConnection();
		skinConnection.connect();

		return skinConnection.getInputStream();
	}

	public static UserProperties getUserProperties(String userName, long timeStamp) throws IOException {
		String target = mojangUserPropertiesAPI.replaceAll("\\$username", userName).replaceAll("\\$timestamp", String.valueOf(timeStamp));

		HttpURLConnection connection = (HttpURLConnection) new URL(target).openConnection();
		connection.setDoInput(true);

		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
		StringBuilder stringBuilder = new StringBuilder();
		String aLine;
		while ((aLine = bufferedReader.readLine()) != null) {
			stringBuilder.append(aLine).append(System.getProperty("line.separator"));
		}

		Gson gson = new Gson();
		UserPropertiesResponse properties = gson.fromJson(stringBuilder.toString(), UserPropertiesResponse.class);
		String[] components =new String[]{
				properties.id.substring(0, 8),
				properties.id.substring(8, 12),
				properties.id.substring(12, 16),
				properties.id.substring(16, 20),
				properties.id.substring(20)
		};
		StringBuilder st = new StringBuilder();
		for (int i = 0;i < components.length;i ++) {
			st.append(components[i]);
			if (i != components.length - 1) {
				st.append("-");
			}
		}
		return new UserProperties(UUID.fromString(st.toString()), properties.name);
	}

	private static class UserPropertiesResponse {
		public String id;
		public String name;
	}
	private static class AuthPayload {
		Agent agent;
		String username;
		String password;         // 嘘。。这可是关系到165块钱的大事！
		boolean requestUser;

		public static class Agent {
			public String name = "Minecraft";
			public int version = 1;
		}
	}
	private static class AuthResponse {
		String accessToken;
		String clientToken;

		List<Profile> availableProfiles;

		Profile selectedProfile;

		UserProfile user;

		public static class Profile {
			public String id;
			public String name;
			public boolean legacy;
		}
		static class UserProfile {
			String id;
			List<Property> properties;

			static class Property {
				String name;
				String value;
			}
		}
	}
	private static class ValidatePayload {
		String accessToken;
	}
	private static class TextureProfile {
		public String id;
		public String name;
		List<EncodedProperty> properties;

		public static class EncodedProperty {
			public String name;
			public String value;
			public String signature;
		}
	}
	private static class ProfileProperty {
		public long timestamp;
		public String profileId;
		public String profileName;
		public String signatureRequired;
		Texture textures;

		public static class Texture {
			MojangURL SKIN;
			public MojangURL CAPE;

			static class MojangURL {
				String url;
			}
		}
	}

	private static class ErrorResponse {
		public String error;
		public String errorMessage;
	}
}
