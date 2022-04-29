package de.mariocst.antiacnk;

import cn.nukkit.plugin.PluginBase;

public class AntiACNK extends PluginBase {
    private static AntiACNK instance;

    private final String prefix = "§8[§cAnti§4AC§cNK§8] §f";

    private ClickListener clickListener;
    private CPSTask cpsTask;

    @Override
    public void onLoad() {
        instance = this;
    }

    @Override
    public void onEnable() {
        this.clickListener = new ClickListener();
        this.getServer().getPluginManager().registerEvents(this.clickListener, this);

        this.cpsTask = new CPSTask();
        this.getServer().getScheduler().scheduleRepeatingTask(this, this.cpsTask, 5);

        this.getServer().getPluginManager().registerEvents(new QuitListener(), this);

        this.getServer().getCommandMap().register("antiacnk", new AntiACNKCommand(this));

        this.log("Enabled AntiACNK!");
    }

    @Override
    public void onDisable() {
        this.log("Disabled AntiACNK!");
    }

    public void log(String msg) {
        this.getLogger().info(this.prefix + msg);
    }

    public String getPrefix() {
        return prefix;
    }

    public ClickListener getClickListener() {
        return clickListener;
    }

    public CPSTask getCPSTask() {
        return cpsTask;
    }

    public static AntiACNK getInstance() {
        return instance;
    }
}
