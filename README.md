<p align="center">
  <img src=".github/assets/artificial_connector_logo.png" alt="Artificial Connector Logo" width="256"/>
</p>

# Artificial Connector Mod

A Minecraft mod for version 1.21.1 using the NeoForge mod loader, designed to integrate external AI APIs directly into the game.

## 📖 About The Project

The "Artificial Connector" mod introduces a new way to interact with the Minecraft world by allowing players to connect to and communicate with generative AI models. The centerpiece of this mod is the **Connector Block**, a machine that serves as the primary interface to the AI.

## ✨ Features

*   **Custom Blocks & Items:**
    *   `Artificial Ore`: A new ore found in the world.
    *   `Artificial Ingot` & `Nugget`: Crafting materials smelted from the ore.
    *   `Artificial Block`: A decorative or storage block.
    *   `Connector Block`: The main machine for AI interaction, with multiple visual states (`Idle`, `Processing`, `Success`, `Failed`).
*   **Client-Side Configuration:** Players can configure their own API key and preferred AI model via a local config file.
*   **Data-Driven:** All recipes, block models, and loot tables are generated using NeoForge's data generator system.

## 🛠️ Building from Source

To build the project, you need JDK 21.

1.  Clone the repository:
    ```sh
    git clone https://github.com/hackmnin/ArtificialConnectorMod.git
    ```
2.  Build the project using the Gradle wrapper:
    ```sh
    ./gradlew build
    ```
    The compiled `.jar` file will be located in `build/libs/`.

## 🤝 Contributing

Contributions are welcome! Please ensure you adhere to the project's coding standards.

*   **Code Style:** This project uses Google Java Style. A Checkstyle configuration is included. Please run `./gradlew check` to verify your changes before committing.
*   **Commit Messages:** Commits must follow the Conventional Commits specification. A `commitlint` hook is in place to enforce this.
*   **Branching:** All development should happen on branches prefixed with `dev/` (e.g., `dev/new-feature`).

## 📜 License

This project is licensed under the MIT License - see the LICENSE file for details.