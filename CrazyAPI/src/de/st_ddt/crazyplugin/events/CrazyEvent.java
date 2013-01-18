package de.st_ddt.crazyplugin.events;

import org.bukkit.Bukkit;
import org.bukkit.event.Event;
import org.bukkit.plugin.Plugin;

import de.st_ddt.crazyplugin.CrazyLightPluginInterface;

public abstract class CrazyEvent extends Event
{

	public CrazyEvent()
	{
		super();
	}

	@Deprecated
	public CrazyEvent(final CrazyLightPluginInterface plugin)
	{
		super();
	}

	public void callEvent()
	{
		Bukkit.getPluginManager().callEvent(this);
	}

	@Deprecated
	public void callAsyncEvent()
	{
		callEvent();
	}

	@SuppressWarnings("deprecation")
	protected void callEventAsynchronously(final Plugin plugin)
	{
		Bukkit.getScheduler().scheduleAsyncDelayedTask(plugin, new AsyncEventRunnable(this));
	}

	protected class AsyncEventRunnable implements Runnable
	{

		private final CrazyEvent event;

		public AsyncEventRunnable(final CrazyEvent event)
		{
			super();
			this.event = event;
		}

		@Override
		public void run()
		{
			event.callEvent();
		}
	}
}
