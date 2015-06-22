# Emoji Tools
Multiple useful tools to help Android and iOS/OSX developers with creating and modifying Emoji Font files.

Features:
* Extracts Emojis from '.ttf' files used by Android, iOS, and OSX into individual '.png' files.
  * Takes less than 6 seconds to extract all Emojis! Very quick!
  * Extracted Emojis are named by Unicode names (Android: `uniE537.png` iOS/OSX: `uE537.png`)
  * Extracted Emojis can automatically be stripped of their prefixes (`uniE537.png` -> `E537.png`)
  * Extracted iOS CgBI (pincrushed) Emojis (which show up black on Windows) can automatically be converted to RGBA.
* Converts from CgBI (pincrushed) Emojis to RGBA and vice versa. (Planned)
* Professional, easy-to-use GUI.

# How to Use:
1. Download one of the precompiled Jars from the [downloads page](https://github.com/MitchTalmadge/Emoji-Tools/releases), or clone into IntelliJ and create a Jar out of the sources.
2. Put the jar wherever you'd like. Emojis will be extracted into a new folder which is created in the same directory as the jar file. You can choose the name of this folder using the GUI. By default, it is named `ExtractedEmojis`.
3. Either double-click the jar and follow the instructions on the GUI, or start the jar from the command line using `java -jar EmojiTools.jar yourFileName.ttf`. If the ttf file does not exist, the ttf selection window will be brought up. If it exists, it will automatically start extraction.

# How to Get Help / Make a Suggestion:
* Make a detailed ticket in the [issue tracker](https://github.com/MitchTalmadge/Emoji-Tools/issues).
* If it's very important, you can email me (Mitch Talmadge).
