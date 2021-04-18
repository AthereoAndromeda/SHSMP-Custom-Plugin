package me.Athereo.SHSMP;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
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
		Player player = event.getPlayer();

		Scoreboard scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
		scoreboard.registerNewObjective("Bruh", "Dummy", "dumboi");


        Team deadTeam = scoreboard.registerNewTeam("Dead");
        Team aliveTeam = scoreboard.registerNewTeam("Alive");

        deadTeam.setPrefix(ChatColor.DARK_RED + "[Dead] " + ChatColor.RESET);
        aliveTeam.setPrefix(ChatColor.DARK_AQUA + "[Alive] " + ChatColor.RESET);

		if (player.isDead()) {
			deadTeam.addEntry(player.getName());
		} else {
			aliveTeam.addEntry(player.getName());
		}

		player.setScoreboard(scoreboard);
	}

	@EventHandler
	public void onPlayerLeave(PlayerQuitEvent event) {
		System.out.println("Big sad gay left");
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
			String msg = ChatColor.translateAlternateColorCodes('&', "&3" + player.getDisplayName() + " &rhas crafted a &l&8Necronomicon.&r");
			
			Bukkit.broadcastMessage(msg);
			sendWebhook(event);
		}
	}

	@EventHandler
	public void onKill(EntityDamageByEntityEvent event) {
		System.out.println("Something Died");
        if (event.isCancelled() || !(event.getEntity() instanceof Player)) {
			return;
		}
		System.out.println("A player died");

		Player player = (Player) event.getEntity();
		// Player killer = player.getKiller();



		// aliveTeam.removeEntry(player.getDisplayName());
		// deadTeam.addEntry(player.getDisplayName());
		player.sendMessage("You have been assigned to Dead team");

		Bukkit.broadcastMessage("Big oof. " + player.getDisplayName() + " has died");
	}


    private void sendWebhook(CraftItemEvent event) throws Exception {
		Player player = (Player) event.getWhoClicked();

        DiscordWebhook webhook = new DiscordWebhook(plugin.getConfig().getString("DiscordWebhook"));
		EmbedObject embed = new EmbedObject()
			.setTitle(player.getName() + " has Crafted a Necronomicon!")
			.setDescription("A player can now be revived!");
		
		webhook.addEmbed(embed);
        webhook.execute();
    }
}
