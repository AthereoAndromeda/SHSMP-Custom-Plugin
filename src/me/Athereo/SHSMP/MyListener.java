package me.Athereo.SHSMP;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.plugin.java.JavaPlugin;

import me.Athereo.SHSMP.DiscordWebhook.EmbedObject;

public class MyListener implements Listener {
	private JavaPlugin plugin;
	private MyRecipes recipes;

	public MyListener(JavaPlugin plugin) {
		this.plugin = plugin;
		this.recipes = new MyRecipes(this.plugin);
	}

    @EventHandler
    public void onCraft(CraftItemEvent event) throws Exception {
        if (event.isCancelled() || !(event.getWhoClicked() instanceof Player)) {
			return;
		}
		
		if (event.getRecipe().getResult().isSimilar(recipes.new RevivalBook().getItem())) {
			Player player = (Player) event.getWhoClicked();
			String msg = ChatColor.translateAlternateColorCodes('&', "&3" + player.getDisplayName() + " &rhas crafted a &l&8Necronomicon.&r");
			
			Bukkit.broadcastMessage(msg);
			sendWebhook(event);
		}
	
		// System.out.println("Something got crafted uwu");
	}

	@EventHandler
	public void onKill(EntityDamageByEntityEvent event) {
        if (event.isCancelled() || !(event.getEntity() instanceof Player)) {
			return;
		}

		Player player = (Player) event.getEntity();
		// Player killer = player.getKiller();

		if (player.isDead()) {
			Bukkit.broadcastMessage("Big oof. " + player.getDisplayName() + " has died");
		}
	}


    private void sendWebhook(CraftItemEvent event) throws Exception {
		Player player = (Player) event.getWhoClicked();

        DiscordWebhook webhook = new DiscordWebhook("https://discord.com/api/webhooks/808972536774656010/vrB_4q8YxaRN2KQRwBngOQ38unspjWj5p7Zm4mRbM5flxKeqTnp_4KVvKnT0kTNF-ZQ_");
		EmbedObject embed = new EmbedObject()
			.setTitle(player.getName() + " has Crafted a Necronomicon!")
			.setDescription("A player can now be revived!");
		
		webhook.addEmbed(embed);
        webhook.execute();
    }
}
