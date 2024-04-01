![Old MinigameMC Logo](https://raw.githubusercontent.com/MVDW-Java/KratousGameCore/main/assets/minigameMC.png)

# KratousGameCore
A decompilation project of the now abandoned GameCore Minecraft plugin to get it in a working state again

## Things found and removed
To get the gamecore working again we had to remove quite a few things.
For example this piece of code that force the server to close by 2018(so an expiration date):
```java
Calendar expiry = Calendar.getInstance();
expiry.set(2017, 12, 29, 0, 0);
Calendar now = Calendar.getInstance();
if (now.after(expiry)) {
    System.exit(-1);
}
```
## Credits
Thanks for ![@narumii](https://github.com/narumii) for helping me out with this project
