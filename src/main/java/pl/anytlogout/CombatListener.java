package pl.anytlogout;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.HashMap;
import java.util.UUID;

public class CombatListener implements Listener {

    private final AnyTLogout plugin;

    private final HashMap<UUID, Long> combat =
            new HashMap<>();

    public CombatListener(AnyTLogout plugin) {
        this.plugin = plugin;
    }


    @EventHandler
    public void onDamage(EntityDamageByEntityEvent e) {

        if (e.getEntity() instanceof Player victim &&
            e.getDamager() instanceof Player attacker) {

            long time = System.currentTimeMillis() + 30000;

            combat.put(victim.getUniqueId(), time);
            combat.put(attacker.getUniqueId(), time);

            victim.sendMessage("§cWalka! Nie wychodź przez 30 sekund!");
            attacker.sendMessage("§cWalka! Nie wychodź przez 30 sekund!");
        }
    }


    @EventHandler
    public void onQuit(PlayerQuitEvent e) {

        Player p = e.getPlayer();

        Long end = combat.get(p.getUniqueId());

        if (end != null &&
                end > System.currentTimeMillis()) {

            Bukkit.broadcastMessage(
                    "§4" + p.getName()
                    + " wylogował się podczas walki!"
            );

            // kara po wyjściu
            Bukkit.getScheduler().runTask(plugin, () -> {
                p.setHealth(0);
            });
        }

        combat.remove(p.getUniqueId());
    }
}
