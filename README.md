# Creeper Confetti

A modern Minecraft mod that replaces destructive creeper explosions with a colorful burst of confetti, customizable sounds, and complete safety for your builds!

Available for both **NeoForge** and **Fabric** via a unified MultiLoader workspace.

## Features

- **Block Safety**: Blocks are preserved perfectly instead of being destroyed by creeper explosions.
- **Visual Burst**: Creeper explosions create a spectacular confetti effect when they explode.
- **Customizable Damage**: Control whether confetti explosions still damage players.
- **Dynamic Server-to-Client Syncing**: Server-defined settings for `confettiChance` and `damagePlayers` automatically synchronize down to connecting clients.

## Configuration

The mod provides the following options in its configuration file:

- `confettiChance` (0 - 100): The percentage chance a creeper will explode into confetti. Default: `100`.
- `cheerChance` (0 - 100): The percentage chance a confetti explosion will also trigger a cheer sound. Default: `5`.
- `damagePlayers` (true / false): If true, confetti explosions still damage players/entities. Default: `false`.

## Project Structure

This project uses a MultiLoader Gradle setup:

- `common`: The core source code compiled against the vanilla game, including shared logic for handling creeper explosions.
- `fabric`: Loader-specific registration, entrypoints, and server-to-client config syncing for Fabric.
- `neoforge`: Loader-specific registration and configuration for NeoForge.

## Developing & Building

### Prerequisites

- Java 25 or higher

### Building the Mod

To build the project for both platforms, use the following Gradle command:

```bash
./gradlew build
```

The output jars for both NeoForge and Fabric will be generated under their respective `build/libs` directories.
