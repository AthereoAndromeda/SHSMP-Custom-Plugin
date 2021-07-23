package me.Athereo.SHSMP;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.event.server.ServerLoadEvent;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import me.Athereo.SHSMP.DiscordWebhook.EmbedObject;

public class MyListener implements Listener {
	private Main plugin;
	private MyRecipes recipes;

	public MyListener(Main plugin) {
		this.plugin = plugin;
		this.recipes = new MyRecipes(plugin);
	}

	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event) {
		// Create scoreboard for player
		// createScoreboard(event.getPlayer());
		updateScoreboard();
	}

	@EventHandler
	public void onPlayerLeave(PlayerQuitEvent event) {

	}

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
			String msg = ChatColor.translateAlternateColorCodes('&',
					"&3" + player.getDisplayName() + " &rhas crafted a &l&8Necronomicon.&r");

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

		// Player killer = player.getKiller();

		updateScoreboard();

		// aliveTeam.removeEntry(player.getDisplayName());
		// deadTeam.addEntry(player.getDisplayName());
		player.sendMessage("You have been assigned to Dead team");

		Bukkit.broadcastMessage("Big oof. " + player.getDisplayName() + " has died");
	}

	@EventHandler
	public void onPlayerRespawn(PlayerRespawnEvent event) {
		System.out.println("Respawned");
		// Player player = event.getPlayer();
		// Scoreboard scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
		// scoreboard.registerNewObjective("Bruh", "Dummy", "dumboi");

		// Team aliveTeam = scoreboard.registerNewTeam("SHSMP.Alive");

		// aliveTeam.setPrefix(ChatColor.DARK_AQUA + "[Alive] " + ChatColor.RESET);
		// aliveTeam.addEntry(player.getName());

		// player.setScoreboard(scoreboard);
		updateScoreboard();
	}

	@EventHandler
	public void onServerLoad(ServerLoadEvent event) {
		Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "team add SHSMP.Alive \"Alive Players\"");
		Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "team add SHSMP.Dead \"Dead Players\"");
		Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "team modify SHSMP.Alive color dark_aqua");
		Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "team modify SHSMP.Dead color dark_red");
	}

	/**
	 * Sends webhook when a necronomicon has been crafted.
	 * 
	 * @param event
	 * @throws Exception
	 */
	private void sendWebhook(CraftItemEvent event) throws Exception {
		Player player = (Player) event.getWhoClicked();

		DiscordWebhook webhook = new DiscordWebhook(plugin.getConfig().getString("DiscordWebhook"));
		EmbedObject embed = new EmbedObject().setTitle(player.getName() + " has Crafted a Necronomicon!")
				.setDescription("A player can now be revived!");

		webhook.addEmbed(embed);
		webhook.execute();
	}

	/**
	 * Creates a scoreboard for the player
	 * 
	 * @param player
	 */
	private void createScoreboard(Player player) {
		Scoreboard scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
		// scoreboard.registerNewObjective("Bruh", "Dummy", "dumboi");

		Team deadTeam = scoreboard.registerNewTeam("SHSMP.Dead");
		Team aliveTeam = scoreboard.registerNewTeam("SHSMP.Alive");

		deadTeam.setPrefix(ChatColor.DARK_RED + "[Dead] " + ChatColor.RESET);
		aliveTeam.setPrefix(ChatColor.DARK_AQUA + "[Alive] " + ChatColor.RESET);

		for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
			String name = onlinePlayer.getDisplayName();

			if (onlinePlayer.isDead()) {
				aliveTeam.removeEntry(name);
				deadTeam.addEntry(name);

				Bukkit.broadcastMessage("Guy died");
			} else {
				deadTeam.removeEntry(name);
				aliveTeam.addEntry(name);

				Bukkit.broadcastMessage("still alibe");
			}
		}

		player.setScoreboard(scoreboard);
	}

	public void updateScoreboard() {
		for (Player player : Bukkit.getOnlinePlayers()) {
			createScoreboard(player);
		}
	}

}
