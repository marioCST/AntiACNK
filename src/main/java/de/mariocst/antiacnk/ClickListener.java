package de.mariocst.antiacnk;

import cn.nukkit.Player;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.event.server.DataPacketReceiveEvent;
import cn.nukkit.network.protocol.LevelSoundEventPacket;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ClickListener implements Listener {
    private final HashMap<Player, List<Long>> cps = new HashMap<>();

    @EventHandler
    public void onPacket(DataPacketReceiveEvent event) {
        if (!(event.getPacket() instanceof LevelSoundEventPacket)) return;

        LevelSoundEventPacket packet = (LevelSoundEventPacket) event.getPacket();

        if (packet.sound != LevelSoundEventPacket.SOUND_ATTACK && packet.sound != LevelSoundEventPacket.SOUND_ATTACK_NODAMAGE &&
                packet.sound != LevelSoundEventPacket.SOUND_ATTACK_STRONG) return;

        Player player = event.getPlayer();

        List<Long> cpsList = cps.get(player);

        if (cpsList == null) {
            cpsList = new ArrayList<>();
        }

        cpsList.add(System.currentTimeMillis());

        cps.remove(player);
        cps.put(player, cpsList);
    }

    public int getCPS(Player player) {
        List<Long> list = cps.get(player);

        if (list == null) return 0;

        list.removeIf(l -> l < System.currentTimeMillis() - 1000);

        return list.size();
    }
}
