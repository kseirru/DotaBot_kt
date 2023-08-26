package core

import com.jagrosh.jdautilities.command.CommandClientBuilder
import dev.minn.jda.ktx.messages.Embed
import io.github.cdimascio.dotenv.Dotenv
import net.dv8tion.jda.api.JDABuilder
import net.dv8tion.jda.api.OnlineStatus
import net.dv8tion.jda.api.entities.Activity
import net.dv8tion.jda.api.entities.Message
import net.dv8tion.jda.api.entities.MessageEmbed
import net.dv8tion.jda.api.requests.GatewayIntent
import net.dv8tion.jda.api.utils.cache.CacheFlag
import org.slf4j.Logger
import org.slf4j.LoggerFactory

class Bot {
    companion object {
        const val COLOR: Int = 0xa31b1c
        val logger: Logger = LoggerFactory.getLogger("DotaBot")

        fun createEmbed() : MessageEmbed {
            return Embed { color = Bot.COLOR }
        }

    }

    init {
        /**
        val databaseManager: DatabaseManager = DatabaseManager()
        val statement = databaseManager.getStatement()
        statement.executeQuery("CREATE TABLE IF NOT EXISTS users (DISCORD_ID TEXT, STEAM_ID TEXT)")
        **/

        val commandClientBuilder = CommandClientBuilder()
            .setStatus(OnlineStatus.ONLINE)
            .setActivity(Activity.watching("for servers"))
            .setOwnerId(Dotenv.load()["owner_id"])
            .useHelpBuilder(false)


        val commandClient = commandClientBuilder.build()

        JDABuilder.create(Dotenv.load()["discord_token"], GatewayIntent.getIntents(GatewayIntent.DEFAULT))
            .addEventListeners(commandClient)
            .disableCache(CacheFlag.ACTIVITY, CacheFlag.CLIENT_STATUS, CacheFlag.ONLINE_STATUS)
            .setEventPassthrough(true)
            .build()

        Message.suppressContentIntentWarning()
    }
}