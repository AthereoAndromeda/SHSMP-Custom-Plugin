package me.Athereo.SHSMP;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.scoreboard.Scoreboard;

import me.Athereo.SHSMP.DiscordWebhook.EmbedObject;

public class MyListener implements Listener {
	private Main plugin;
	private MyRecipes recipes;
	private Scoreboard scoreboard;

	public MyListener(Main plugin) {
		this.plugin = plugin;
		this.recipes = new MyRecipes(plugin);
		this.scoreboard = plugin.scoreboard;
	}

	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event) {
		Player player = event.getPlayer();

		if (player.isDead()) {
			scoreboard.getTeam("Dead").addEntry(player.getDisplayName());
			player.sendMessage("You have been assigned to Dead team");
		} else {
			scoreboard.getTeam("Alive").addEntry(player.getDisplayName());
		}
	}

    @EventHandler
    public void onCraft(CraftItemEvent event) throws Exception {
        if (event.isCancelled() || !(event.getWhoClicked() instanceof Player)) {
			return;
		}
		
		// Going to make it check for lore instead soon, because it is possible
		// someone just changes the name using Anvil and get 
		if (event.getRecipe().getResult().getItemMeta().getDisplayName() == recipes.new Necronomicon().getItem().getItemMeta().getDisplayName()) {
			Player player = (Player) event.getWhoClicked();
			String msg = ChatColor.translateAlternateColorCodes('&', "&3" + player.getDisplayName() + " &rhas crafted a &l&8Necronomicon.&r");
			
			Bukkit.broadcastMessage(msg);
			sendWebhook(event);
		}
	}

	@EventHandler
	public void onKill(EntityDamageByEntityEvent event) {
        if (event.isCancelled() || !(event.getEntity() instanceof Player)) {
			return;
		}

		Player player = (Player) event.getEntity();
		// Player killer = player.getKiller();
		scoreboard.getTeam("Dead").addEntry(player.getDisplayName());
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
