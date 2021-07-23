package me.Athereo.SHSMP;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.CraftItemEvent;
// import org.bukkit.event.player.PlayerJoinEvent;
// import org.bukkit.event.player.PlayerQuitEvent;
// import org.bukkit.event.player.PlayerRespawnEvent;
// import org.bukkit.event.server.ServerLoadEvent;
// import org.bukkit.scoreboard.Scoreboard;
// import org.bukkit.scoreboard.Team;

import me.Athereo.SHSMP.DiscordWebhook.EmbedObject;

public class MyListener implements Listener {
	private Main plugin;
	private MyRecipes recipes;

	public MyListener(Main plugin) {
		this.plugin = plugin;
		this.recipes = new MyRecipes(plugin);
	}

	// @EventHandler
	// public void onPlayerJoin(PlayerJoinEvent event) {
	// }

	// @EventHandler
	// public void onPlayerLeave(PlayerQuitEvent event) {
	// }

	@EventHandler
	public void onCraft(CraftItemEvent event) throws Exception {
		if (event.isCancelled() || !(event.getWhoClicked() instanceof Player)) {
			return;
		}

		String eventRecipeName = event.getRecipe().getResult().getItemMeta().getDisplayName();
		String NecroName = recipes.new Necronomicon().getItem().getItemMeta().getDisplayName();

		// Going to make it check for lore instead soon, because it is possible
		// someone just changes the name using Anvil and get
		if (NecroName.equals(eventRecipeName)) {
			Player player = (Player) event.getWhoClicked();
			String rawMessage = "&3" + player.getDisplayName() + " &rhas crafted a &l&8Necronomicon.&r";
			String msg = ChatColor.translateAlternateColorCodes('&', rawMessage);

			Bukkit.broadcastMessage(msg);
			sendWebhook(event);
		}
	}

	@EventHandler
	public void onKill(PlayerDeathEvent event) {
		if (!(event.getEntity() instanceof Player)) {
			return;
		}

		event.setDeathMessage(ChatColor.RED + "You are now dead, sad.");

		Player player = event.getEntity();
		Bukkit.broadcastMessage("Big oof. " + player.getDisplayName() + " has died");
	}

	// @EventHandler
	// public void onPlayerRespawn(PlayerRespawnEvent event) {
	// System.out.println("Respawned");
	// }

	// @EventHandler
	// public void onServerLoad(ServerLoadEvent event) {

	// }

	/**
	 * Sends webhook when a necronomicon has been crafted.
	 * 
	 * @param event
	 * @throws Exception
	 */
	private void sendWebhook(CraftItemEvent event) throws Exception {
		Player player = (Player) event.getWhoClicked();

		Boolean isWebhookEnabled = plugin.getConfig().getBoolean("EnableDiscordWebhook");

		if (isWebhookEnabled) {
			DiscordWebhook webhook = new DiscordWebhook(plugin.getConfig().getString("DiscordWebhook"));
			EmbedObject embed = new EmbedObject().setTitle(player.getName() + " has Crafted a Necronomicon!")
					.setDescription("A player can now be revived!");

			webhook.addEmbed(embed);
			webhook.execute();
		}
	}

}
