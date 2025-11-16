package hackmnin.artificialconnector;

import net.neoforged.neoforge.common.ModConfigSpec;

/**
 * Manages all configuration specifications for the mod.
 */
public class ModConfigs {

    // We use a builder to construct our configuration file's structure.
    public static final ModConfigSpec.Builder CLIENT_BUILDER = new ModConfigSpec.Builder();

    // This holds the final built specification.
    public static final ModConfigSpec CLIENT_SPEC;

    // These are the actual configuration values we can access from our code.
    public static final ModConfigSpec.ConfigValue<String> API_KEY;
    public static final ModConfigSpec.ConfigValue<String> AI_MODEL;

    // A static block is used to define and build the configuration structure.
    static {
        CLIENT_BUILDER.push("AI Settings");

        API_KEY = CLIENT_BUILDER.comment("Your secret API key for the AI service.").define("apiKey",
                "");

        AI_MODEL = CLIENT_BUILDER
                .comment("The specific AI model to use (e.g., 'gpt-4o', 'gemini-1.5-pro').")
                .define("aiModel", "gpt-4o");

        CLIENT_BUILDER.pop();
        CLIENT_SPEC = CLIENT_BUILDER.build();
    }
}
