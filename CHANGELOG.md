## [1.0.2](https://github.com/DomSeFutNe/ArtificialConnectorMod/compare/v1.0.1...v1.0.2) (2025-11-16)


### Bug Fixes

* **docs:** Use relative path for logo ([#11](https://github.com/DomSeFutNe/ArtificialConnectorMod/issues/11)) ([71a5ece](https://github.com/DomSeFutNe/ArtificialConnectorMod/commit/71a5ecea8359ff8af38ad123d4158e496c31e5ca))

## [1.0.1](https://github.com/DomSeFutNe/ArtificialConnectorMod/compare/v1.0.0...v1.0.1) (2025-11-16)


### Bug Fixes

* **ci:** Use Project ID for CurseForge Publishing ([#9](https://github.com/DomSeFutNe/ArtificialConnectorMod/issues/9)) ([e739d1f](https://github.com/DomSeFutNe/ArtificialConnectorMod/commit/e739d1f3dba0529ac10645ced5a0f45743c44be7))

# 1.0.0 (2025-11-16)


### Bug Fixes

* **build:** Resolve Checkstyle and CurseGradle build failures ([#5](https://github.com/DomSeFutNe/ArtificialConnectorMod/issues/5)) ([da0e829](https://github.com/DomSeFutNe/ArtificialConnectorMod/commit/da0e829242f1a561f0ee83cc0a3569ba954a9d9e))
* **ci:** Remove invalid inputs for commitlint action ([0d4f627](https://github.com/DomSeFutNe/ArtificialConnectorMod/commit/0d4f6272e2f6ffca82b42b241cc5d04dd7ce2da0))
* **ci:** Use PAT to bypass repository ruleset ([#7](https://github.com/DomSeFutNe/ArtificialConnectorMod/issues/7)) ([8291b08](https://github.com/DomSeFutNe/ArtificialConnectorMod/commit/8291b0823f06417f2e9ef4c687b3f121d67db42e))
* **ci:** Use PAT to bypass repository ruleset ([#8](https://github.com/DomSeFutNe/ArtificialConnectorMod/issues/8)) ([2429b70](https://github.com/DomSeFutNe/ArtificialConnectorMod/commit/2429b7023f636b1eab5a1e27237290124cf6c2e3))
* **release:** add permissions for version tagging and issue comments ([#4](https://github.com/DomSeFutNe/ArtificialConnectorMod/issues/4)) ([fb119e7](https://github.com/DomSeFutNe/ArtificialConnectorMod/commit/fb119e765f65647aab9e96d795c16302054fe337))


### Features

* add Artificial Wrench item and related assets ([4315410](https://github.com/DomSeFutNe/ArtificialConnectorMod/commit/431541088dabcd6ac52ba76b826a959690deb962))
* add creative mode tab for Artificial Connector and update localization ([b5b3403](https://github.com/DomSeFutNe/ArtificialConnectorMod/commit/b5b3403323204c689e5d7a53aafbc5a85a020a88))
* add pre-commit hook for Gradle check and update Checkstyle configuration ([07e966b](https://github.com/DomSeFutNe/ArtificialConnectorMod/commit/07e966b41646b14e78eb1031018a7dbaa8e59840))
* **block:** add basic Connector Block with BlockEntity ([c84f000](https://github.com/DomSeFutNe/ArtificialConnectorMod/commit/c84f000318578b93e6a056108ece84765aebe901))
* **config:** add client-side configuration for API settings ([b2f0732](https://github.com/DomSeFutNe/ArtificialConnectorMod/commit/b2f07324cda26c1cf43fa636fc89b0a9c1b5dfa2))
* **connector:** implement ConnectorBlock with state management and properties ([c230e26](https://github.com/DomSeFutNe/ArtificialConnectorMod/commit/c230e268c9c5538bfbeb248817f76ec0ae1e2eda))
* **core:** Implement Connector Block assets and improve project setup ([#3](https://github.com/DomSeFutNe/ArtificialConnectorMod/issues/3)) ([96c68e3](https://github.com/DomSeFutNe/ArtificialConnectorMod/commit/96c68e32d3a97b2a6f678de262b99441150e5f09))
* remove GEMINI.md from .gitignore and update project context in GEMINI.md ([43b7e1f](https://github.com/DomSeFutNe/ArtificialConnectorMod/commit/43b7e1f75db36e8d167e06c7a5c08821e84446aa))
* reorganize .gitignore, remove unused VS Code configurations, and add Checkstyle plugin ([32f29c4](https://github.com/DomSeFutNe/ArtificialConnectorMod/commit/32f29c426761775c37929418937f52291232156a))
* update Checkstyle configuration and clean up imports ([8ed9e81](https://github.com/DomSeFutNe/ArtificialConnectorMod/commit/8ed9e812dee5b88e3277ae0e891668382015982e))
* update log file path in GEMINI.md and add timestamp format for logs ([a929eea](https://github.com/DomSeFutNe/ArtificialConnectorMod/commit/a929eead59ca47d08bfc0eda15853ef83480cc64))
* update preferred editor setting from vscode to code in settings.json ([1565132](https://github.com/DomSeFutNe/ArtificialConnectorMod/commit/156513275f6fd9c61f08430b5dd21897ff71a2b0))

# Changelog

All notable changes to this project will be documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.0.0/),
and this project adheres to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

## [0.0.0] - 2025-11-15

### Added
- Setup CurseForge publishing via `cursegradle`.
- Implemented animated textures for the Connector Block's processing state.
- Added `README.md` and `LICENSE` files for project documentation.
- Implemented status-based block states for the Connector Block.
- Initial setup of core blocks, items, and data generation.

## [0.1.0] - 2025-11-16
- Initial alpha release.
