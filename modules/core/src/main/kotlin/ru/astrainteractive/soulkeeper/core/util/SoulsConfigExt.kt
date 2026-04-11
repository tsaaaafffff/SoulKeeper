package ru.astrainteractive.soulkeeper.core.util

import com.destroystokyo.paper.ParticleBuilder
import org.bukkit.Location
import org.bukkit.Particle
import org.bukkit.entity.Player
import ru.astrainteractive.soulkeeper.core.plugin.SoulsConfig

fun SoulsConfig.Particles.Particle.toBuilder(location: Location): ParticleBuilder {
    val particle = runCatching { Particle.valueOf(key.uppercase()) }.getOrNull() ?: Particle.DUST
    return ParticleBuilder(particle)
        .count(count)
        .location(location)
        .offset(offsetX, offsetY, offsetZ)
        .apply {
            val localDustOptions = dustOptions?.takeIf { particle == Particle.DUST }
            if (localDustOptions != null) {
                data(
                    org.bukkit.Particle.DustOptions(
                        org.bukkit.Color.fromRGB(localDustOptions.color),
                        localDustOptions.size
                    )
                )
            }
        }
}

fun Location.spawnParticleForPlayer(player: Player, config: SoulsConfig.Particles.Particle) {
    config.toBuilder(this)
        .receivers(player)
        .spawn()
}

fun Location.playSoundForPlayer(player: Player, sound: SoulsConfig.Sounds.SoundConfig) {
    val kyoriSound = net.kyori.adventure.sound.Sound.sound(
        net.kyori.adventure.key.Key.key(sound.id),
        net.kyori.adventure.sound.Sound.Source.AMBIENT,
        sound.volume,
        sound.pitch
    )
    player.playSound(kyoriSound, x, y, z)
}
