# Living Wilds v0.2

Android prototype for a low-attention survival / creature-discovery game.

## What is included
- 2D forest scene drawn natively with Android Canvas (no external image assets required)
- Original creature: Lumi
- Auto-survival progression
- XP / levels / camp upgrades
- resource gathering
- random events
- rare discovery system
- simple creature collection counter
- one-tap simulation of several hours of world time

## Build from a tablet
This project includes a GitHub Actions workflow.

1. Create a GitHub repository from this folder.
2. Upload all files, keeping `.github/workflows/build-apk.yml`.
3. Open the repository's **Actions** tab.
4. Select **Build Living Wilds APK**.
5. Tap **Run workflow**.
6. When it finishes, open the workflow run and download the **LivingWilds-debug-apk** artifact.
7. Extract the APK and install it on your Android phone/tablet.

No Android Studio is required on your tablet for the cloud build.

## Important
This is a prototype, not a store-ready game. Notifications, real background simulation, richer creature art, persistence, sound, and a server-backed world are planned for later versions.
