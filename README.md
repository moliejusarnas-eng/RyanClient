# RyanClient (Fabric 1.21)

A client-side Fabric mod for Minecraft 1.21.

## Features
- Press **O** to open/close the settings menu.
- Press **R** to toggle the TriggerBot placeholder.
- Press **F** to toggle the AimAssist placeholder.
- Press **U** to toggle rotation diagnostics.
- HUD shows RyanClient, FPS, coordinates and key hints.

**Important:** TriggerBot and AimAssist are currently placeholders; they do not automate attacks or aiming. Rotation diagnostics is only a toggle. This is source code, not a precompiled `.jar`.

## Build the `.jar`
Requires JDK 21 and Gradle 8.8.

1. Open a terminal in this folder.
2. Run `gradle build`.
3. Find the compiled mod in `build/libs/ryanclient-1.0.0.jar`.
4. Put that `.jar` in your Minecraft `mods` folder. Use Fabric Loader and Fabric API for Minecraft 1.21.

## Build online with GitHub Actions
1. Create a GitHub repository and upload the contents of this folder.
2. Open **Actions** and run **Build Fabric mod** (or push to `main`).
3. Open the completed workflow run and download the `ryanclient-fabric-1.21` artifact. It contains the compiled `.jar`.
4. Upload the `.jar` to your Modrinth project as a version file. Choose Minecraft **1.21** and loader **Fabric**.

This project has not been compiled in this environment because external downloads are unavailable, so the JAR must be built using one of the methods above before uploading to Modrinth.
