package pl.anytlogout;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.HashMap;
import java.util.UUID;

public class CombatListener implements Listener {

    private final AnyTLogout plugin;

    private final HashMap<UUID, Long> combat = new HashMap<>();


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


            victim.sendMessage(
                    "§cJesteś w walce! Nie wychodź przez 30 sekund!"
            );

            attacker.sendMessage(
                    "§cJesteś w walce! Nie wychodź przez 30 sekund!"
            );
        }
    }



    @EventHandler
    public void onQuit(PlayerQuitEvent e) {

        Player p = e.getPlayer();

        Long end = combat.get(p.getUniqueId());


        if (end != null && end > System.currentTimeMillis()) {


            Bukkit.broadcastMessage(
                    "§4" + p.getName()
                    + " wylogował się podczas walki!"
            );


            Bukkit.getScheduler().runTask(plugin, () -> {
                p.setHealth(0);
            });
        }


        combat.remove(p.getUniqueId());
    }




    @EventHandler
    public void onCommand(PlayerCommandPreprocessEvent e) {

        Player p = e.getPlayer();


        Long end = combat.get(p.getUniqueId());


        if (end != null && end > System.currentTimeMillis()) {


            String cmd = e.getMessage()
                    .toLowerCase();



            if (
                    cmd.startsWith("/spawn") ||
                    cmd.startsWith("/home") ||
                    cmd.startsWith("/sethome") ||
                    cmd.startsWith("/tpa") ||
                    cmd.startsWith("/tpaccept") ||
                    cmd.startsWith("/warp") ||
                    cmd.startsWith("/rtp") ||
                    cmd.startsWith("/login") ||
                    cmd.startsWith("/l ")
            ) {


                e.setCancelled(true);


                p.sendMessage(
                        "§cNie możesz używać tej komendy podczas walki!"
                );
            }
        }
    }
}
