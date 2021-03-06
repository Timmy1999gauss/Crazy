package de.st_ddt.crazyarena.command;

import de.st_ddt.crazyarena.CrazyArena;
import de.st_ddt.crazyarena.CrazyArenaPlugin;
import de.st_ddt.crazyplugin.commands.CrazyCommandExecutor;

public abstract class ArenaPluginCommandExecutor<S extends CrazyArenaPlugin> extends CrazyCommandExecutor<S>
{

	public ArenaPluginCommandExecutor(final S plugin)
	{
		super(plugin);
	}

	public final CrazyArena getArenaMainPlugin()
	{
		return CrazyArena.getPlugin();
	}
}
