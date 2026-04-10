@file:Suppress("MaxLineLength", "MaximumLineLength", "LongParameterList")

package ru.astrainteractive.soulkeeper.core.plugin

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import ru.astrainteractive.astralibs.string.StringDesc
import ru.astrainteractive.astralibs.string.plus
import ru.astrainteractive.astralibs.string.replace
import kotlin.time.Duration

@Serializable
class PluginTranslation(
    @SerialName("general")
    val general: General = General(),
    @SerialName("souls")
    val souls: Souls = Souls()
) {
    @Serializable
    data class Souls(
        @SerialName("days_ago_format")
        private val daysAgoFormat: StringDesc.Raw = StringDesc.Raw("%time% days ago"),
        @SerialName("hours_ago_format")
        private val hoursAgoFormat: StringDesc.Raw = StringDesc.Raw("%time% hours ago"),
        @SerialName("minutes_ago_format")
        private val minutesAgoFormat: StringDesc.Raw = StringDesc.Raw("%time% minutes ago"),
        @SerialName("months_ago_format")
        private val monthsAgoFormat: StringDesc.Raw = StringDesc.Raw("%time% months ago"),
        @SerialName("seconds_ago_format")
        private val secondsAgoFormat: StringDesc.Raw = StringDesc.Raw("%time% seconds ago"),
        @SerialName("no_souls_on_page")
        private val noSoulsOnPage: StringDesc.Raw = prefix
            .plus("&#db2c18No souls on page %page%")
            .toRaw(),
        @SerialName("listing_format")
        private val listingFormat: StringDesc.Raw = StringDesc.Raw("&#b8b8b8%index%. ")
            .plus("&#d1a71d%owner% ")
            .plus("&#b8b8b8(%time_ago%) ")
            .plus("&#b8b8b8(%x%; %y%; %z%) ")
            .plus("%dist%m")
            .toRaw(),
        @SerialName("list_souls_title")
        val listSoulsTitle: StringDesc.Raw = prefix
            .plus("&#42f596List of visible souls:")
            .toRaw(),
        @SerialName("free_soul")
        val freeSoul: StringDesc.Raw = StringDesc.Raw("&#b50b05[FREE]"),
        @SerialName("teleport_to_soul")
        val teleportToSoul: StringDesc.Raw = StringDesc.Raw("&#1db2b8[TP]"),
        @SerialName("soul_freed")
        val soulFreed: StringDesc.Raw = prefix
            .plus("&#42f596The soul is now free!")
            .toRaw(),
        @SerialName("could_not_free_soul")
        val couldNotFreeSoul: StringDesc.Raw = prefix
            .plus("&#db2c18Could not free the soul!")
            .toRaw(),
        @SerialName("soul_not_found")
        val soulNotFound: StringDesc.Raw = prefix
            .plus("&#db2c18Soul not found!")
            .toRaw(),
        @SerialName("next_page")
        val nextPage: StringDesc.Raw = StringDesc.Raw("&#42f596[>>NEXT>>]"),
        @SerialName("prev_page")
        val prevPage: StringDesc.Raw = StringDesc.Raw("&#42f596[<<PREVIOUS<<]"),
        @SerialName("soul_of")
        private val soulOf: StringDesc.Raw = StringDesc.Raw("&#317dd4Soul of player &#31d43c%player%")
    ) {
        fun listingFormat(
            index: Int,
            owner: String,
            timeAgo: String,
            distance: Int,
            x: Int,
            y: Int,
            z: Int
        ) = listingFormat
            .replace("%index%", "$index")
            .replace("%owner%", owner)
            .replace("%dist%", "$distance")
            .replace("%time_ago%", timeAgo)
            .replace("%x%", "$x")
            .replace("%y%", "$y")
            .replace("%z%", "$z")

        fun noSoulsOnPage(page: Int) = noSoulsOnPage
            .replace("%page%", page.toString())

        fun soulOf(player: String) = soulOf
            .replace("%player%", player)

        fun daysAgoFormat(time: Duration) = daysAgoFormat
            .replace("%time%", time.inWholeDays.toString())

        fun hoursAgoFormat(time: Duration) = hoursAgoFormat
            .replace("%time%", time.inWholeHours.toString())

        fun minutesAgoFormat(time: Duration) = minutesAgoFormat
            .replace("%time%", time.inWholeMinutes.toString())

        @Suppress("MagicNumber")
        fun monthsAgoFormat(time: Duration) = monthsAgoFormat
            .replace("%time%", time.inWholeDays.div(30).toString())

        fun secondsAgoFormat(time: Duration) = secondsAgoFormat
            .replace("%time%", time.inWholeSeconds.toString())
    }

    @Serializable
    class General(
        @SerialName("reload")
        val reload: StringDesc.Raw = prefix
            .plus("&#dbbb18Reloading plugin")
            .toRaw(),
        @SerialName("reload_complete")
        val reloadComplete: StringDesc.Raw = prefix
            .plus("&#42f596Reload completed successfully")
            .toRaw(),
        @SerialName("no_permission")
        val noPermission: StringDesc.Raw = prefix
            .plus("&#db2c18You do not have permission!")
            .toRaw(),
        @SerialName("wrong_usage")
        val wrongUsage: StringDesc.Raw = prefix
            .plus("&#db2c18Incorrect usage!")
            .toRaw(),
        @SerialName("only_player_command")
        val onlyPlayerCommand: StringDesc.Raw = prefix
            .plus("&#db2c18This command is only for players!")
            .toRaw(),
    )

    companion object {
        val prefix: StringDesc.Raw = StringDesc.Raw("&#18dbd1[SoulKeeper] ")
    }
}

private fun StringDesc.toRaw(): StringDesc.Raw {
    return StringDesc.Raw(this.raw)
}
