<h1 align="center">Not As Easy Villagers</h1>
<p align="center">Reimplementation of <a href="https://github.com/henkelmax/easy-villagers">Easy Villagers</a>' villager item mechanic in Fabric.<br> You can combine this mod with <a href="https://www.curseforge.com/minecraft/mc-mods/trade-cycling">Trade Cycling</a> to get less cheaty Easy Villagers experience.</p>




<p align="center">
  <a title="Fabric API" href="https://github.com/FabricMC/fabric">
    <img src="https://i.imgur.com/Ol1Tcf8.png" width="151" height="50" />
  </a>
  <a title="Fabric Language Kotlin" href="https://github.com/FabricMC/fabric-language-kotlin" target="_blank" rel="noopener noreferrer">
    <img src="https://i.imgur.com/c1DH9VL.png" width="171" height="50" />
  </a>
</p>
<p align="center">
  <a href="https://github.com/igoro00/not-as-easy-villagers/actions"><img src="https://github.com/igoro00/not-as-easy-villagers/actions/workflows/build.yml/badge.svg"/></a>
  <a href="https://opensource.org/licenses/GPL-3.0"><img src="https://img.shields.io/badge/License-GPL%203.0-brightgreen.svg"></a>
</p>

## Features
 - Shift-Right click a villager with an empty hand to ~~kidnap it~~ get the villager item
 - ~~commit several crimes against humanity like smashing villager's head on an anvil to rename it~~
 - Right click on the ground with the villager item to place the villager (in creative mode, the item won't dissapear so you can use it as a template)

## Contributing
Feel free to contribute to this project(like, really. Please, contribute) by forking this repository and creating a pull request.

**I would especially appreciate helping with combining textures based on the profession, type and level of the villager.**

The model of the villager item(created in Blockbench, project file called `villager.bbmodel` is available in the main directory of the repo) already supports Minecraft's default textures for the villager. The texture in `src/main/resources/assets/not_as_easy_villagers/textures/item/villager.png` is actually just `villager.png` and `types/plains.png` from the default texture pack layered on top of each other in GIMP.

Now, all we need to do is to get VillagerData on item creation (in `MobEntityMixin.java`). Then, based on that, combine appropriate textures and apply it to the item dynamically(I couldn't do either as I'm very knew to Minecraft modding and Java in general). **If you know how to do it, please (at least) create an issue pointing me in the right direction.** 

## License
Distributed under the GNU General Public License 3.0. See `LICENSE` for more information.

## Build
If you want to build this yourself, please clone the repository and execute `gradlew build` in the projects folder. 

Artifacts will be generated at `build/libs`