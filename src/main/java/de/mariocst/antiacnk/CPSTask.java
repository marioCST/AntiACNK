package de.mariocst.antiacnk;

import cn.nukkit.Player;
import cn.nukkit.scheduler.NukkitRunnable;

import java.util.HashMap;

public class CPSTask extends NukkitRunnable {
    public HashMap<Player, Player> listening = new HashMap<>();

    @Override
    public void run() {
        for (Player player : AntiACNK.getInstance().getServer().getOnlinePlayers().values()) {
            if (player.hasPermission("antiacnk.bypass") || player.hasPermission("antiacnk.*") ||
                    player.hasPermission("*") || player.isOp()) continue;

            if (AntiACNK.getInstance().getClickListener().getCPS(player) > 30) {
                player.kick("§cKicked for cheating", false);
            }
        }

        for (Player player : listening.keySet()) {
            if (listening.get(player) == null) {
                listening.remove(player);
                player.sendMessage(AntiACNK.getInstance().getPrefix() + "The targeted player disconnected!");
                continue;
            }

            player.sendActionBar(listening.get(player).getName() + " - " + AntiACNK.getInstance().getClickListener().getCPS(listening.get(player)));
        }
    }
}
