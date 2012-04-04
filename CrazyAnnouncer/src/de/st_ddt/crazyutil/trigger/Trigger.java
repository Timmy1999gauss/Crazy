package de.st_ddt.crazyutil.trigger;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.plugin.java.JavaPlugin;

import de.st_ddt.crazyutil.action.NamedRunnable;
import de.st_ddt.crazyutil.databases.Saveable;

public abstract class Trigger implements Saveable, Runnable
{

	public String name;
	public List<NamedRunnable> actions = new ArrayList<NamedRunnable>();
	public boolean enabled;
	protected final JavaPlugin plugin;

	public static Trigger load(ConfigurationSection config, List<? extends NamedRunnable> actionlist, JavaPlugin plugin)
	{
		if (config == null)
			return null;
		String type = config.getString("type", "-1");
		if (type == "-1")
		{
			System.out.println("Invalid Trigger Type!");
			return null;
		}
		Class<?> clazz = null;
		try
		{
			clazz = Class.forName(type);
		}
		catch (ClassNotFoundException e)
		{
			try
			{
				clazz = Class.forName("de.st_ddt.crazyutil.trigger." + type);
			}
			catch (ClassNotFoundException e2)
			{
				e.printStackTrace();
				return null;
			}
		}
		if (Trigger.class.isAssignableFrom(clazz))
		{
			System.out.println("Invalid TriggerType " + clazz.toString().substring(6));
			return null;
		}
		Trigger trigger = null;
		try
		{
			trigger = (Trigger) clazz.getConstructor(ConfigurationSection.class, List.class, JavaPlugin.class).newInstance(config, actionlist, plugin);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return null;
		}
		return trigger;
	}

	public Trigger(String name, List<NamedRunnable> actionlist, JavaPlugin plugin)
	{
		super();
		this.name = name;
		this.actions = actionlist;
		this.enabled = true;
		this.plugin = plugin;
	}

	public Trigger(ConfigurationSection config, List<NamedRunnable> actionlist, JavaPlugin plugin)
	{
		super();
		this.name = config.getName();
		List<String> actionnames = config.getStringList("actions");
		for (NamedRunnable action : actionlist)
			if (actionnames.contains(action.getName()))
				this.actions.add(action);
		this.enabled = config.getBoolean("enabled", true);
		this.plugin = plugin;
	}

	@Override
	public void save(ConfigurationSection config, String path)
	{
		List<String> actionnames = new ArrayList<String>();
		config.set(path + "typ", this.getClass().getName().substring(6));
		for (NamedRunnable action : actions)
			actionnames.add(action.getName());
		config.set(path + "actions", actionnames);
		config.set(path + "enabled", enabled);
	}

	@Override
	public String getName()
	{
		return name;
	}

	public boolean isEnabled()
	{
		return enabled;
	}

	public void setEnabled(boolean enabled)
	{
		if (this.enabled != enabled)
			if (enabled)
				register();
			else
				unregister();
		this.enabled = enabled;
	}

	public void addAction(NamedRunnable action)
	{
		if (!actions.contains(action))
			actions.add(action);
	}

	public void removeAction(NamedRunnable action)
	{
		actions.remove(action);
	}

	public abstract boolean needToBeSaved();

	public abstract void register();

	public abstract void unregister();

	@Override
	public void run()
	{
		if (!enabled)
			return;
		for (Runnable action : actions)
			action.run();
	}
}