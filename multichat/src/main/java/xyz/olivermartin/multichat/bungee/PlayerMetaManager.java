package xyz.olivermartin.multichat.bungee;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;

public class PlayerMetaManager {

	static {
		instance = new PlayerMetaManager();
	}

	private static PlayerMetaManager instance;

	public static PlayerMetaManager getInstance() {
		return instance;
	}

	// END OF STATIC

	private Map<UUID, PlayerMeta> metaMap;

	public PlayerMetaManager() {
		this.metaMap = new HashMap<UUID, PlayerMeta>();
	}

	public void registerPlayer(UUID uuid, String name) {
		this.metaMap.put(uuid, new PlayerMeta(uuid, name));
	}

	public void unregisterPlayer(UUID uuid) {
		metaMap.remove(uuid);
	}

	public Optional<PlayerMeta> getPlayer(UUID uuid) {
		if (!metaMap.containsKey(uuid)) return Optional.empty();
		return Optional.of(metaMap.get(uuid));
	}

	public void updateDisplayName(UUID uuid) {

		Optional<PlayerMeta> opm = getPlayer(uuid);

		if (!opm.isPresent()) return;

		ProxiedPlayer player = ProxyServer.getInstance().getPlayer(uuid);

		if (ConfigManager.getInstance().getHandler("config.yml").getConfig().getBoolean("fetch_spigot_display_names") == true && player != null) {
			if (ConfigManager.getInstance().getHandler("config.yml").getConfig().contains("set_display_name")) {
				if (ConfigManager.getInstance().getHandler("config.yml").getConfig().getBoolean("set_display_name")) {
					if (ConfigManager.getInstance().getHandler("config.yml").getConfig().contains("display_name_format")) {
						//player.setDisplayName(opm.get().getDisplayName(ConfigManager.getInstance().getHandler("config.yml").getConfig().getString("display_name_format")));
						player.setDisplayName(opm.get().getSpigotDisplayname());
					} else {
						//player.setDisplayName(opm.get().getDisplayName("%PREFIX%%NICK%%SUFFIX%"));
						player.setDisplayName(opm.get().getSpigotDisplayname());
					}
				}
			} else {
				if (ConfigManager.getInstance().getHandler("config.yml").getConfig().contains("display_name_format")) {
					//player.setDisplayName(opm.get().getDisplayName(ConfigManager.getInstance().getHandler("config.yml").getConfig().getString("display_name_format")));
					// TODO Maybe new option for "fetch_spigot_displayname"?
					player.setDisplayName(opm.get().getSpigotDisplayname());
				} else {
					//player.setDisplayName(opm.get().getDisplayName("%PREFIX%%NICK%%SUFFIX%"));
					player.setDisplayName(opm.get().getSpigotDisplayname());
				}
			}

		}

	}

}
