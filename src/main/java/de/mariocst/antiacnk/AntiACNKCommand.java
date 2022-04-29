package de.mariocst.antiacnk;

import cn.nukkit.Player;
import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;

public class AntiACNKCommand extends Command {
    private final AntiACNK plugin;

    public AntiACNKCommand(AntiACNK plugin) {
        super("antiacnk", "AntiACNK command", "antiacnk");
        this.setPermission("antiacnk.command");
        this.plugin = plugin;
    }

    @Override
    public boolean execute(CommandSender sender, String s, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(this.plugin.getPrefix() + "§cPlease run the command InGame!");
            return true;
        }

        Player player = (Player) sender;

        if (player.hasPermission("antiacnk.command") || player.hasPermission("antiacnk.*") || player.hasPermission("*") || player.isOp()) {
            if (args.length > 1) {
                player.sendMessage(this.plugin.getPrefix() + "/antiacnk [target]");
                return true;
            }

            if (args.length == 0) {
                player.sendMessage(this.plugin.getPrefix() + "§cStopped listening to " + this.plugin.getCPSTask().listening.get(player).getName());
                this.plugin.getCPSTask().listening.remove(player);
                return true;
            }

            Player t = this.plugin.getServer().getPlayer(args[0]);

            if (t == null) {
                player.sendMessage(this.plugin.getPrefix() + "§cUnknown player " + args[0]);
                return true;
            }

            this.plugin.getCPSTask().listening.remove(player);
            this.plugin.getCPSTask().listening.put(player, t);

            player.sendMessage(this.plugin.getPrefix() + "Now listening to " + args[0]);
        }
        else {
            player.sendMessage("§cNo permission");
        }

        return false;
    }
}
