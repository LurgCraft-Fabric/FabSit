package net.fill1890.fabsit.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import me.lucko.fabric.api.permissions.v0.Permissions;
import net.fill1890.fabsit.config.ConfigManager;
import net.fill1890.fabsit.error.LoadConfigException;
import net.fill1890.fabsit.util.Messages;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;

import static net.minecraft.server.command.CommandManager.literal;

public abstract class ReloadConfigCommand {
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher, boolean ignored) {
        dispatcher.register(literal("fabsit")
                .then(literal("reload")
                .requires(Permissions.require("fabsit.reload", 2))
                .executes(ReloadConfigCommand::run)));
    }

    public static int run(CommandContext<ServerCommandSource> context) {
        try {
            ConfigManager.loadConfig();
            context.getSource().sendFeedback((Text) Messages.configLoadSuccess(context.getSource().getPlayer()), true);
            return 0;
        } catch (LoadConfigException | CommandSyntaxException e) {
            e.printStackTrace();
            return -1;
        }
    }
}
