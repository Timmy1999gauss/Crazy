package de.st_ddt.crazyplugin.commands;

import de.st_ddt.crazyplugin.CrazyPluginInterface;

public class CrazyPluginCommandMainTree extends CrazyCommandTreeExecutor<CrazyPluginInterface>
{

	public CrazyPluginCommandMainTree(final CrazyPluginInterface plugin)
	{
		super(plugin);
		addSubCommand(new CrazyPluginCommandMainInfo(plugin), "info");
		addSubCommand(new CrazyPluginCommandMainLogger(plugin), "logger", "log");
		addSubCommand(new CrazyPluginCommandMainHelp(plugin), "help");
		addSubCommand(new CrazyPluginCommandMainReload(plugin), "reload");
		addSubCommand(new CrazyPluginCommandMainSave(plugin), "save");
	}
}
