This file provides the context for the Gemini CLI agent.

## Project Goal
Project: A Minecraft Mod named "Artificial Connector".

Platform: Java, NeoForge (v. 21.1.213), for Minecraft 1.21.1.

Objective: Create a mod with custom items, blocks, and a GUI to connect to an AI API.

## Project Content (So Far)
We have successfully registered the following:

Items (in ModItems.java):

* RAW_ARTIFICIAL_ORE
* ARTIFICIAL_INGOT
* ARTIFICIAL_NUGGET

Blocks (in ModBlocks.java):

* ARTIFICIAL_ORE (This is a custom class ArtificialOreBlock that emits dark purple particles).
* ARTIFICIAL_BLOCK (This is a custom class ArtificialBlock that emits light purple particles. It's the craftable block made from ingots).

## Technical Setup & Workflow
This is the most critical part to understand.

1. Environment (Dual-World Setup):

* WSL2 (Ubuntu): My primary development environment (~/private/github/ArtificialConnectorMod). This is where all code is written, Git commits are made, and data generators are run.
* Windows: A second clone of the repo (C:\...) exists. This clone is only used for launching the game client with F5 to solve a WSL mouse-capture issue.
* VS Code Setup: I am currently running VS Code on Windows and accessing the WSL filesystem via the \\wsl.localhost\ UNC path, as this allows the Gemini extension (you) to access the internet.

2. Data Generation (CRITICAL): We do not write JSON files manually in src/main/resources. We use the Data Generator system, executed by running ./gradlew runData in the WSL terminal. We have the following providers set up in the hackmnin.artificialconnector.data package:

* ModRecipeProvider.java: Generates smelting (furnace, blast) and crafting (shaped, shapeless) recipes.
* ModItemModelProvider.java: Generates .json models for all items.
* ModLangProvider.java: Generates the en_us.json language file.
* ModBlockStateProvider.java: Generates .json models and blockstates for our blocks (e.g., cube_all).
* ModLootTableProvider.java: Generates block drop loot tables (ore drops raw ore, block drops self).
* ModWorldGenProvider.java: Generates our ore in the world.

3. Configurable World-Gen: The ModWorldGenProvider.java is configurable via system environment variables (which we set in the WSL terminal before running runData).

* ORE_SPAWNS_IN: ("overworld", "nether", "end") - This dynamically changes the RuleTest (e.g., STONE_ORE_REPLACEABLES, BASE_STONE_NETHER) and the BiomeTag (e.g., IS_OVERWORLD, IS_NETHER).
* ORE_SPAWNS_MIN: (e.g., -64)
* ORE_SPAWNS_MAX: (e.g., 128)
* ORE_SPAWNS_PER_CHUNK: (e.g., 10)

We use Java 21 switch expressions to handle this logic cleanly.

## DevOps & Linting Rules
We have a very strict local and server-side setup.

1. Local Git Hooks (via Husky):

* package.json exists and uses husky, commitlint, and config-conventional.
* .husky/pre-commit: Runs ./gradlew check. This task is configured to run checkstyleMain. A commit is REJECTED if any Checkstyle error exists.
* .husky/commit-msg: Runs npx commitlint. A commit is REJECTED if the message does not follow Conventional Commits (e.g., feat:, fix:, chore:, etc.).
* .husky/pre-push: Runs a script. A push is REJECTED if the branch name does not start with dev/ (unless it's main or master).

2. Server-Side CI (GitHub Actions):

* branch-name.yml: Fails any PR to main if the source branch name does not start with dev/.
* lint-commits.yml: Fails any PR to main if any commit in the PR does not follow Conventional Commits.
* Branch Protection: The main branch is protected and requires all these checks to pass before merging.

3. IDE Formatting (VS Code):

* Linter: We use the "Checkstyle for Java" (Microsoft) extension.
* Formatter: We use the "Language Support for Java (Red Hat)" extension.
* Configuration: My global WSL User settings.json is configured to solve all our previous issues.
* java.jdt.ls.java.home is set to /usr/lib/jvm/java-21-openjdk-amd64 to fix ENOENT errors.
* java.checkstyle.configuration points to config/checkstyle/google_checks.xml (using version 10.17.0).
* java.format.settings.url points to config/checkstyle/google_eclipse_formatter.xml.
* editor.formatOnSave is set to false.
* editor.codeActionsOnSave: { "source.fixAll.checkstyle": "explicit" } is enabled. This is our "fix on save" mechanism.
