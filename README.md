# Miker's Snuffles (Forge + GeckoLib)

This is a Java mod source scaffold for Minecraft Java 26.2, Forge, and GeckoLib. It registers a hostile Snuffles entity with 24 health, 13 attack damage, 0.3 movement speed, a synchronized sleep/panic/attack state, natural-block tunneling, daylight shelter seeking, and four randomized texture variants.

## Important setup steps

The supplied originals are included in the expected resource folders:

- `src/main/resources/assets/mikers_snuffles/geo/mikers_snuffles.geo.json`
- `src/main/resources/assets/mikers_snuffles/animations/mikers_snuffles.animation.json`
- `src/main/resources/assets/mikers_snuffles/textures/entity/mikers_snuffles1.png` through `mikers_snuffles4.png`

The original Blockbench `.bbmodel` is also included for editing. The controller uses the exact clips in the animation file: `Idle`, `Running`, `Sleeping`, `Sprinting`, and `Attack`.

## Version caveat

`gradle.properties` deliberately leaves the Forge build and GeckoLib version as `CHANGE_ME` values. The Forge download index consulted during generation showed 26.3 builds, not a confirmed 26.2 MDK build, so this workspace cannot responsibly pin an unverified Forge artifact. Set `forge_version` and `geckolib_version` to the exact compatible releases you install. Forge and GeckoLib APIs may require small mapping/API adjustments for the specific 26.2 MDK. This source has not been compiled against that MDK.

## Behavior notes

- Daytime sleepers do not acquire targets. Taking damage wakes them into PANIC.
- Night wakes sleepers into PANIC. A visible living target promotes the state to ATTACK.
- Sleep locks movement and plays sleeping; moving panic entities use running, and pursuing attackers use sprinting. The attack controller is provided as a triggerable animation but must be triggered from the final MDK's attack event/hit implementation if that event API differs.
- The tunneling goal uses an explicit whitelist, not broad block tags, so planks, glass, bricks and other player-made blocks are excluded. The whitelist may be edited in `SnufflesBreakNaturalBlocksGoal`.
- The biome modifier adds overworld natural spawns, but does not itself enforce cave-only/night/near-Glow-Lichen checks. Those need a Forge spawn-placement predicate for the exact target MDK and should be added before release.
- No loot is defined, and there is no taming behavior.

## Build

Use the matching Forge 26.2 MDK and Java version for that MDK, set the two `CHANGE_ME` properties, then run `./gradlew build`. A Gradle wrapper is not included because the correct MDK wrapper was not available in the sandbox.
