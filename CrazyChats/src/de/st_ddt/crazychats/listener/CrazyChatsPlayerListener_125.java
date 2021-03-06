package de.st_ddt.crazychats.listener;

import java.util.Set;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerChatEvent;

import de.st_ddt.crazychats.CrazyChats;

@SuppressWarnings("deprecation")
public class CrazyChatsPlayerListener_125 extends CrazyChatsPlayerListener
{

	public CrazyChatsPlayerListener_125(final CrazyChats plugin)
	{
		super(plugin);
	}

	@EventHandler(ignoreCancelled = true, priority = EventPriority.LOWEST)
	public void PlayerChat(final PlayerChatEvent event)
	{
		final ChatResult result = PlayerChat(event.getPlayer(), event.getMessage());
		if (result.isCancelled())
			event.setCancelled(true);
		else
		{
			event.setFormat(result.getAdvancedFormat(event.getPlayer()));
			event.setMessage(result.getMessage());
			final Set<Player> targets = event.getRecipients();
			try
			{
				targets.clear();
				targets.addAll(result.getTargets());
			}
			catch (final UnsupportedOperationException e)
			{}
		}
	}

	@EventHandler(ignoreCancelled = true, priority = EventPriority.HIGHEST)
	public void PlayerChatLog(final PlayerChatEvent event)
	{
		final Player player = event.getPlayer();
		final String message = event.getMessage();
		PlayerChatLog(player, message);
		try
		{
			if (event.getRecipients().remove(player))
				PlayerChatOwnerMessage(event.getFormat(), player, message);
		}
		catch (final UnsupportedOperationException e)
		{}
	}
}
