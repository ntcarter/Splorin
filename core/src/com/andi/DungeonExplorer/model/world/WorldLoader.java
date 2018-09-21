package com.andi.DungeonExplorer.model.world;

import com.andi.DungeonExplorer.dialogue.ChoiceDialogueNode;
import com.andi.DungeonExplorer.dialogue.Dialogue;
import com.andi.DungeonExplorer.dialogue.LinearDialogueNode;
import com.andi.DungeonExplorer.model.DIRECTION;
import com.andi.DungeonExplorer.model.TERRAIN;
import com.andi.DungeonExplorer.model.Tile;
import com.andi.DungeonExplorer.model.actor.Actor;
import com.andi.DungeonExplorer.model.actor.Character.Skeleton;
import com.andi.DungeonExplorer.model.actor.LimitedWalkingBehavior;

import com.andi.DungeonExplorer.model.world.InteractTiles.CrystalPiece;
import com.andi.DungeonExplorer.model.world.InteractTiles.SnowStairs;
import com.andi.DungeonExplorer.model.world.InteractTiles.SpikeTrapObject;
import com.andi.DungeonExplorer.model.world.Items.Key;
import com.andi.DungeonExplorer.model.world.SpecialTiles.IceTile;
import com.andi.DungeonExplorer.model.world.SpecialTiles.SpikeTrap;
import com.andi.DungeonExplorer.model.world.SpecialTiles.SwitchTile;
import com.andi.DungeonExplorer.model.world.SpecialTiles.TeleportTileDoor;
import com.andi.DungeonExplorer.model.world.SpecialTiles.TeleportTilePortal;
import com.andi.DungeonExplorer.model.world.InteractTiles.Chest;
import com.andi.DungeonExplorer.model.world.InteractTiles.Door;
import com.andi.DungeonExplorer.model.world.cutscene.CutsceneEventQueuer;
import com.andi.DungeonExplorer.model.world.cutscene.CutscenePlayer;
import com.andi.DungeonExplorer.util.AnimationSet;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.GridPoint2;

        import java.util.Random;

/**
 * Created by brytonhayes on 10/23/17.
 */

public class WorldLoader {

    public static World snowMultiplayerMap(AssetManager assetManager,CutscenePlayer player, CutsceneEventQueuer broadcaster, AnimationSet npcAnimations){
        World snowMap = new World("snow_Map",250,250, assetManager, npcAnimations);
        TextureAtlas atlas = assetManager.get("res/graphics_packed/tiles/tilepack.atlas", TextureAtlas.class);
        TextureRegion stairsTexture = atlas.findRegion("snowStairs");

        for (int x = 0; x <250; x++) {
            for (int y = 0; y<250; y++) {
                if (y==0) {
                    snowMap.getMap().setTile(new Tile(null, false), x, y);
                    continue;
                }
                if(y>=1){
                    snowMap.getMap().setTile(new Tile(TERRAIN.SNOW_TILE,true),x,y);
                }



            }
        }

        snowMap.add6x5SnowTreeRegion(30,21);
        snowMap.add6x5SnowTreeRegion(36,21);
        snowMap.add6x5SnowTreeRegion(42,21);
        snowMap.add6x5SnowTreeRegion(30,35);
        snowMap.add6x5SnowTreeRegion(42,35);
        snowMap.add4x19SnowTreeRegion(26,21);
        snowMap.add4x19SnowTreeRegion(48,21);
        snowMap.addVisualObject(31,31,"SnowBoulder",false,2f,2f);
        snowMap.addVisualObject(45,31,"SnowBoulder",false,2f,2f);
        snowMap.getMap().setTile(new SwitchTile(TERRAIN.SNOW_SWITCH,1),33,30);
        snowMap.getMap().setTile(new SwitchTile(TERRAIN.SNOW_SWITCH,2),44,30);
        snowMap.addWall(36,35,6,30,3);

        snowMap.addBoulderPuzzle1();


        SnowStairs stairs = new SnowStairs(37,34,stairsTexture,1,snowMap);



        return snowMap;
    }


    public static World outdoorLevel(AssetManager assetManager, CutscenePlayer player, CutsceneEventQueuer broadcaster, AnimationSet npcAnimations) {
        World world1 = new World("test_level",20,20, assetManager, npcAnimations);
        for (int xi = 0; xi < 20; xi++) {
            for (int yi = 0; yi < 20; yi++) {
                if (xi==0 || xi==18) {
                    if (world1.getMap().getTile(xi, yi).getObject() == null) {
                        world1.addTree(xi,yi);
                    }
                }
                if (yi==0||yi==18) {
                    if (world1.getMap().getTile(xi, yi).getObject() == null) {
                        world1.addTree(xi,yi);
                    }
                }
            }
        }
        world1.getMap().setTile(new IceTile(TERRAIN.ice),9,9);
        world1.getMap().setTile(new IceTile(TERRAIN.ice),9,10);
        world1.getMap().setTile(new IceTile(TERRAIN.ice),9,11);
        world1.getMap().setTile(new IceTile(TERRAIN.ice),9,12);

       world1.addBoulderActor(6,7);


        world1.addVisualObject(2,17,"bush",false,2f,2f);
        world1.addVisualObject(4,17,"bush",false,2f,2f);
        world1.addVisualObject(6,17,"bush",false,2f,2f);
        world1.addVisualObject(8,17,"bush",false,2f,2f);
        world1.addVisualObject(10,17,"bush",false,2f,2f);
        world1.addVisualObject(12,17,"bush",false,2f,2f);
        world1.addVisualObject(14,17,"bush",false,2f,2f);
        world1.addVisualObject(16,17,"bush",false,2f,2f);

        world1.addVisualObject(5,10,"multiTree",false,2f,3f);
        world1.addVisualObject(16,15,"woodAxe",false,2f,2f);
        world1.addVisualObject(5,15,"flowerB",true,1f,2f);
        world1.addVisualObject(10,15,"flowerO",true,1f,2f);
        world1.addVisualObject(5,8,"flowerB",true,1f,2f);
        world1.addVisualObject(10,3,"flowerO",true,1f,2f);
        world1.addVisualObject(12,3,"lake",false,4f,3f);



        world1.addHouse(10,10);

        world1.addFlowers(5,6);
        world1.addFlowers(2,8);
        world1.addFlowers(4,12);
        world1.addFlowers(8,3);
        world1.addFlowers(10,5);
        world1.addFlowers(14,7);

        Key key1 = world1.addKey(1,1,"key1",2);
        world1.addObject(world1.addKey(3,3,"key0",1));
        world1.getMap().setTile(new SwitchTile(TERRAIN.GRASS_0,1),6,6);
        Door door = new Door(13,10, world1.doorOpen, world1.doorClose,2,true,1,broadcaster);

        world1.addObject(door);

        world1.addSpikes(TERRAIN.GRASS_0,player,broadcaster,7,7);


        Chest chest = new Chest(5,5,world1.chestOpen,1,broadcaster);

        chest.addObject(key1);
        world1.addObject(chest);

        world1.setPath(13,8,2,false);
        world1.setPath(16,8,7,false);
        world1.setPath(8,8,9,true);
        world1.setPath(11,3,6,false);
        world1.setPath(8,4,5,false);
        world1.setPath(5,4,4,true);
        return world1;
    }
    
    public static World startRoom(AssetManager assetManager, CutscenePlayer player, CutsceneEventQueuer broadcaster, AnimationSet npcAnimations) {
        World world2 = new World("test_indoors",11,11, assetManager, npcAnimations);//Define world size 11
        for (int x = 0; x < 11; x++) {
            for (int y = 0; y < 11; y++) {
                if (y==0) {
                    world2.getMap().setTile(new Tile(null, false), x, y);
                    continue;
                }
                if (y > 0) {
                    if (Math.random() > 0.05d) {
                        world2.getMap().setTile(new Tile(TERRAIN.INDOOR_TILES, true), x, y);
                    } else {
                        world2.getMap().setTile(new Tile(TERRAIN.INDOOR_TILES_BLOOD, true), x, y);
                    }
                }
            }
        }
        world2.addWall(0, 8, 11, 3, 0);
        Actor enemy = new Actor(world2, 4, 4, npcAnimations);
        enemy.name = "Evil Rick";
        enemy.character = new Skeleton(5, 1, 3000, enemy);
        //Evil Rick is a level 5 skeleton.

        LimitedWalkingBehavior brain = new LimitedWalkingBehavior(enemy, 1, 1, 1, 1, 0.3f, 1f, new Random());
        world2.addActor(enemy, brain);

        //adds dialogue
        Dialogue greeting = new Dialogue();
        LinearDialogueNode node1 = new LinearDialogueNode("Alright Morty, ready to go on an adventure?", 0,false);
        ChoiceDialogueNode node2 = new ChoiceDialogueNode("Let's go Morty?", 1,false);
        LinearDialogueNode node3 = new LinearDialogueNode("Wubba-lubba-dub-dub! ", 2,true);

        LinearDialogueNode node4 = new LinearDialogueNode("I'm sorry, Morty.\n You have no choice.", 3,false);
        node2.addChoice("Oh jeez... okay",2);
        node2.addChoice("No Rick!, I've had enough of your adventures!",3);

        node1.setPointer(1);
        greeting.addNode(node1);
        greeting.addNode(node2);
        greeting.addNode(node3);
        greeting.addNode(node4);
        node3.addActor(enemy);
        enemy.setDialogue(greeting);
        world2.getMap().setTile(new TeleportTilePortal(null,player,broadcaster,"mainRoom",18,10, DIRECTION.NORTH, Color.WHITE),5,8);
        world2.getMap().setTile(new TeleportTileDoor(null, player, broadcaster, "test_level", 13,10,DIRECTION.SOUTH,Color.WHITE), 5, 0);
        world2.addRug(world2,4,0);
        world2.addPortal(world2,5,8,"portal",1f,2f);

        return world2;
    }
    public static World mainRoom(AssetManager assetManager, CutscenePlayer player, CutsceneEventQueuer broadcaster, AnimationSet npcAnimations){

        World mainRoom = new World("mainRoom",38,21, assetManager, npcAnimations);
        for (int x = 0; x <38; x++) {
            for (int y = 0; y<21; y++) {
                if (y==0) {
                    mainRoom.getMap().setTile(new Tile(null, false), x, y);
                    continue;
                }
                else{
                    mainRoom.getMap().setTile(new Tile(TERRAIN.ds_WALL_MIDDLE_MIDDLE,false),x,y);
                }

            }
        }
        int blackCounterLeft = 17;
        int blackCounterRight = 20;
        for(int i = 0;i<8;i++){
            for(int j = 1;j<37;j++){
                if(j>=blackCounterLeft&&j<blackCounterRight){
                    mainRoom.getMap().setTile(new Tile(TERRAIN.DUNGEON_FLOOR,true),j,i);
                }

            }
            blackCounterLeft-=2;
            blackCounterRight+=2;
        }
        int blackCounter = 0;
        int tileCounter = 37;
        for(int i = 8;i<21;i++){
            for(int j=blackCounter;j<tileCounter;j++){
                mainRoom.getMap().setTile(new Tile(TERRAIN.DUNGEON_FLOOR,true),j,i);
            }
            blackCounter++;
            tileCounter--;
        }
        int startWall = 0;
        int endWall = 36;
        for(int i = 8;i<21;i++){
            for(int j = 0;j<37;j++){
                if(i == 20 && j>startWall && j<endWall){
                    mainRoom.addWallObject(j,i,"middleWall",false,1f,3f);
                }
                if(j == startWall){
                    mainRoom.addWallObject(j,i,"middleWall",false,1f,3f);
                }
                if(j == endWall){
                    mainRoom.addWallObject(j,i,"middleWall",false,1f,3f);
                }
            }
            startWall++;
            endWall--;
        }

        mainRoom.getMap().setTile(new TeleportTilePortal(null, player, broadcaster,"upperStar", 20,0,DIRECTION.NORTH,Color.WHITE), 18, 19);
        mainRoom.getMap().setTile(new TeleportTilePortal(null,player,broadcaster,"topRightStar",7,9,DIRECTION.EAST,Color.BLACK),30,13);
        mainRoom.getMap().setTile(new TeleportTilePortal(null,player,broadcaster,"topLeftStar",53,8,DIRECTION.WEST,Color.BLACK),6,13);
        mainRoom.getMap().setTile(new TeleportTilePortal(null,player,broadcaster,"bottomRightStar",9,27,DIRECTION.SOUTH,Color.BLACK),25,3);
        mainRoom.getMap().setTile(new TeleportTilePortal(null,player,broadcaster,"bottomLeftStar",28,27,DIRECTION.SOUTH,Color.BLACK),11,3);
        mainRoom.addWallObject(17,19,"angelStatue",false,1f,2f);
        mainRoom.addWallObject(19,19,"angelStatue",false,1f,2f);

        mainRoom.addVisualObject(17,10,"bigStar",true,3f,2f);

        mainRoom.addVisualObject(24,3,"skeleton",false,1f,1f);
        mainRoom.addVisualObject(26,4,"skeleton",false,1f,1f);
        mainRoom.addVisualObject(10,4,"skeleton",false,1f,1f);
        mainRoom.addVisualObject(12,3,"skeleton",false,1f,1f);

        mainRoom.addWallObject(29,14,"armorStatue",false,1f,2f);
        mainRoom.addWallObject(31,12,"armorStatue",false,1f,2f);
        mainRoom.addWallObject(7,14,"armorStatue",false,1f,2f);
        mainRoom.addWallObject(5,12,"armorStatue",false,1f,2f);

        mainRoom.addPortal(mainRoom,18,19,"smallStar",1f,1f);//upperStar
        mainRoom.addPortal(mainRoom,25,3,"smallStar",1f,1f);//bottomRightStar
        mainRoom.addPortal(mainRoom,11,3,"smallStar",1f,1f);//bottomLeftStar
        mainRoom.addPortal(mainRoom,30,13,"smallStar",1f,1f);//topRightStar
        mainRoom.addPortal(mainRoom,6,13,"smallStar",1f,1f);//topLeftStar

        mainRoom.addCrystal(18,12,1,broadcaster);//upper
        mainRoom.addCrystal(20,11,2,broadcaster);//topright
        mainRoom.addCrystal(16,11,3,broadcaster);//topleft
        mainRoom.addCrystal(17,9,4,broadcaster);//bottomleft
        mainRoom.addCrystal(19,9,5,broadcaster);//bottomright


        return mainRoom;
    }
    public static World upperStar(AssetManager assetManager, CutscenePlayer player, CutsceneEventQueuer broadcaster, AnimationSet npcAnimations) {
        TextureAtlas atlas = assetManager.get("res/graphics_packed/tiles/tilepack.atlas", TextureAtlas.class);
        World upperStar = new World("upperStar",41,30, assetManager, npcAnimations);
        for (int x = 0; x <41; x++) {
            for (int y = 0; y<30; y++) {
                if (y==0) {
                    upperStar.getMap().setTile(new Tile(null, false), x, y);
                    continue;
                }
                else{
                    upperStar.getMap().setTile(new Tile(TERRAIN.ds_WALL_MIDDLE_MIDDLE,false),x,y);
                }

            }
        }
        int blackCounter = 0;
        int tileCounter = 41;
        for(int i = 0;i<41;i++){
            for(int j =blackCounter;j<tileCounter;j++){
                upperStar.getMap().setTile(new Tile(TERRAIN.DUNGEON_FLOOR,true),j,i);
            }
            blackCounter++;
            tileCounter--;

        }
        int startWall = 0;
        int endWall = 40;
        for(int i = 0;i<20;i++){
            for(int j = 0;j<41;j++){
                if(j == startWall){
                    upperStar.addVisualObject(j,i,"middleWall",false,1f,3f);
                }
                else if(j == endWall){
                    upperStar.addVisualObject(j,i,"middleWall",false,1f,3f);
                }



            }
            startWall++;
            endWall--;
        }
        upperStar.addVisualObject(20,20,"middleWall",false,1f,3f);
        upperStar.getMap().setTile(new TeleportTilePortal(null, player, broadcaster, "mainRoom", 18,19,DIRECTION.SOUTH,Color.WHITE), 20, 0);
        upperStar.addVisualObject(20,0,"smallStar",true,1f,1f);


        Actor actor = new Actor(upperStar, 20, 16, npcAnimations);
        LimitedWalkingBehavior brain = new LimitedWalkingBehavior(actor, 1, 1, 1, 1, 0.3f, 1f, new Random());
        upperStar.addActor(actor, brain);
        //adds dialogue
        TextureRegion crystal = atlas.findRegion("crystalPiece");
        CrystalPiece crystalPiece = new CrystalPiece(1,1,crystal,1f,1f,new GridPoint2(0,0),true,1);
        actor.addObject(crystalPiece);
        Dialogue greeting = new Dialogue();
        LinearDialogueNode node1 = new LinearDialogueNode("Answer this question for the prize", 0,false);
        ChoiceDialogueNode node2 = new ChoiceDialogueNode("Whos is the best TA?", 1,false);
        LinearDialogueNode node3 = new LinearDialogueNode("Correct! ", 2,true);
        LinearDialogueNode node4 = new LinearDialogueNode("Mitra is not a TA.", 3,false);
        LinearDialogueNode node5 = new LinearDialogueNode("Gavin is not the best.", 4,false);
        node2.addChoice("Shaiqur",2);
        node2.addChoice("Mitra",3);
        node2.addChoice("Gavin",4);
        node1.setPointer(1);
        greeting.addNode(node1);
        greeting.addNode(node2);
        greeting.addNode(node3);
        greeting.addNode(node4);
        greeting.addNode(node5);
        node3.addActor(actor);


        greeting.addNode(node1);
        greeting.addNode(node2);
        greeting.addNode(node3);
        greeting.addNode(node4);

        actor.setDialogue(greeting);
        return upperStar;

    }


    public static World topRightStar(AssetManager assetManager, CutscenePlayer player, CutsceneEventQueuer broadcaster, AnimationSet npcAnimations){
        TextureAtlas atlas = assetManager.get("res/graphics_packed/tiles/tilepack.atlas", TextureAtlas.class);
        World topRightStar = new World("topRightStar",62,19, assetManager, npcAnimations);
        for (int x = 0; x <62; x++) {
            for (int y = 0; y<19; y++) {
                if (y==0) {
                    topRightStar.getMap().setTile(new Tile(null, false), x, y);
                    continue;
                }
                else{
                    topRightStar.getMap().setTile(new Tile(TERRAIN.ds_WALL_MIDDLE_MIDDLE,false),x,y);
                }

            }

        }
        int tileCounter = 15;
        for(int i = 0;i<16;i++){
            for(int j = 16;j>tileCounter;j--){
                topRightStar.getMap().setTile(new Tile(TERRAIN.DUNGEON_FLOOR,true),i,j);
            }
            tileCounter--;
        }
        int blackCounter = 20;
        for(int i = 2;i<16;i++){
            for(int j = 16;j<blackCounter &&j<62;j++){
                topRightStar.getMap().setTile(new Tile(TERRAIN.DUNGEON_FLOOR,true),j,i);
            }
            blackCounter+=3;
        }
        topRightStar.getMap().setTile(new TeleportTilePortal(null,player,broadcaster,"mainRoom",30,13,DIRECTION.WEST,Color.BLACK),7,9);
        topRightStar.addPortal(topRightStar,7,9,"smallStar",1f,1f);

        topRightStar.addWall(0,16,62,3,1);
        TextureRegion crystal = atlas.findRegion("crystalPiece");
        CrystalPiece crystalPiece = new CrystalPiece(1,1,crystal,1f,1f,new GridPoint2(0,0),true,2);
        //enemy1
        Actor enemy = new Actor(topRightStar, 50, 13, npcAnimations);
        enemy.name = "Ntcarter";
        enemy.character = new Skeleton(8, 1, 3000, enemy);
        //Evil Rick is a level 5 skeleton.
        enemy.addObject(crystalPiece);

        LimitedWalkingBehavior brain = new LimitedWalkingBehavior(enemy, 1, 1, 1, 1, 0.3f, 1f, new Random());
        topRightStar.addActor(enemy, brain);
        return topRightStar;
    }
    public static World topLeftStar(AssetManager assetManager, CutscenePlayer player, CutsceneEventQueuer broadcaster, AnimationSet npcAnimations){
        TextureAtlas atlas = assetManager.get("res/graphics_packed/tiles/tilepack.atlas", TextureAtlas.class);
        World topLeftStar = new World("topLeftStar",62,19, assetManager, npcAnimations);
        for (int x = 0; x <62; x++) {
            for (int y = 0; y<19; y++) {
                if (y==0) {
                    topLeftStar.getMap().setTile(new Tile(null, false), x, y);
                    continue;
                }
                else{
                    topLeftStar.getMap().setTile(new Tile(TERRAIN.ds_WALL_MIDDLE_MIDDLE,false),x,y);
                }

            }

        }
        int tileCounter = 46;
        for(int i = 0;i<16;i++){
            for(int j = 45;j<tileCounter;j++){
                topLeftStar.getMap().setTile(new Tile(TERRAIN.DUNGEON_FLOOR,true),j,i);
            }
            tileCounter++;
        }
        int blackCounter = 41;
        for(int i = 1;i<16;i++){
            for(int j = 45;j>blackCounter&&j>0;j--){
                topLeftStar.getMap().setTile(new Tile(TERRAIN.DUNGEON_FLOOR,true),j,i);
            }
            blackCounter-=3;
        }
        topLeftStar.getMap().setTile(new TeleportTilePortal(null,player,broadcaster,"mainRoom",6,13,DIRECTION.EAST,Color.BLACK),53,8);
        topLeftStar.addPortal(topLeftStar,53,8,"smallStar",1f,1f);

        topLeftStar.addWall(0,16,62,3,1);
        TextureRegion crystal = atlas.findRegion("crystalPiece");
        CrystalPiece crystalPiece = new CrystalPiece(1,1,crystal,1f,1f,new GridPoint2(0,0),true,3);
        //enemy1
        Actor enemy = new Actor(topLeftStar, 45, 13, npcAnimations);
        enemy.name = "Bryton";

        //Evil Rick is a level 8 skeleton.
        enemy.character = new Skeleton(8, 1, 3000, enemy);
        enemy.addObject(crystalPiece);
        LimitedWalkingBehavior brain = new LimitedWalkingBehavior(enemy, 1, 1, 1, 1, 0.3f, 1f, new Random());
        topLeftStar.addActor(enemy, brain);
        return topLeftStar;
    }
    public static World bottomRightStar(AssetManager assetManager, CutscenePlayer player, CutsceneEventQueuer broadcaster, AnimationSet npcAnimations){
        //TODO add clue in time you will make it there
        TextureAtlas atlas = assetManager.get("res/graphics_packed/tiles/tilepack.atlas", TextureAtlas.class);
        World bottomRightStar = new World("bottomRightStar",38,38, assetManager, npcAnimations);
        for (int x = 0; x <38; x++) {
            for (int y = 0; y<38; y++) {
                if (y==0) {
                    bottomRightStar.getMap().setTile(new Tile(null, false), x, y);
                    continue;
                }
                else{
                    bottomRightStar.getMap().setTile(new Tile(TERRAIN.ds_WALL_MIDDLE_MIDDLE,false),x,y);
                }

            }
        }
        int tileCounter = 35;
        for(int i = 0;i<38;i++){
            for(int j = 37;j>tileCounter;j--){
                bottomRightStar.getMap().setTile(new Tile(TERRAIN.DUNGEON_FLOOR,true),j,i);
            }
            if(tileCounter >0){
                tileCounter-=2;
            }

        }
        int blackCounter = 19;
        for(int i = 37;i>2;i--){
            for(int j = blackCounter;j<38;j++){
                bottomRightStar.getMap().setTile(new Tile(TERRAIN.ds_WALL_MIDDLE_MIDDLE,false),j,i);
            }
            if(i%2==0){
                blackCounter++;
            }
        }
        blackCounter = 1;
        for(int i = 19;i<38;i++){
            for(int j = 0;j<blackCounter;j++){
                bottomRightStar.getMap().setTile(new Tile(TERRAIN.ds_WALL_MIDDLE_MIDDLE,false),j,i);
            }
            blackCounter++;
        }
        bottomRightStar.getMap().setTile(new Tile(TERRAIN.ds_WALL_MIDDLE_MIDDLE,false),36,2);
        bottomRightStar.getMap().setTile(new Tile(TERRAIN.ds_WALL_MIDDLE_MIDDLE,false),37,2);
        int wallCountLeft = 19;
        int wallCountRight = 20;
        for(int i=36;i>0;i-=2){
            for(int j = wallCountLeft;j<wallCountRight;j++){
                bottomRightStar.addWallObject(j,i,"middleWall",false,1f,3f);
            }
            wallCountLeft++;
            wallCountRight++;
        }
        wallCountLeft = 0;
        wallCountRight = 1;
        for(int i=19;i<38;i++){
            for(int j = wallCountLeft;j<wallCountRight;j++){
                bottomRightStar.addWallObject(j,i,"middleWall",false,1f,3f);
            }
            wallCountLeft++;
            wallCountRight++;
        }
        bottomRightStar.addLava(16,29,5,5);
        bottomRightStar.addLava(12,24,5,5);
        bottomRightStar.addLava(8,20,5,5);
        bottomRightStar.addLava(4,16,5,5);

        bottomRightStar.addLava(19,23,5,5);
        bottomRightStar.addLava(15,18,5,5);
        bottomRightStar.addLava(11,13,5,5);

        bottomRightStar.addLava(21,17,5,5);
        bottomRightStar.addLava(17,12,5,5);

        bottomRightStar.addLava(23,11,5,5);

        bottomRightStar.addTeleporters(bottomRightStar,16,29,12,26,12,26,10,22,12,26);//bottomleft,topleft,topright,bottomright
        bottomRightStar.addTeleporters(bottomRightStar,12,24,12,26,12,26,10,22,18,31);
        bottomRightStar.addTeleporters(bottomRightStar,8,20,12,26,6,18,12,26,12,26);
        bottomRightStar.addTeleporters(bottomRightStar,4,16,17,20,12,26,12,26,12,26);

        bottomRightStar.addTeleporters(bottomRightStar,19,23,10,22,18,31,13,15,12,26);
        bottomRightStar.addTeleporters(bottomRightStar,15,18,6,18,12,26,13,15,21,25);
        bottomRightStar.addTeleporters(bottomRightStar,11,13,6,18,23,19,12,26,12,26);

        bottomRightStar.addTeleporters(bottomRightStar,21,17,19,14,12,26,13,15,17,20);
        bottomRightStar.addTeleporters(bottomRightStar,17,12,6,18,13,15,17,20,25,13);

        bottomRightStar.addTeleporters(bottomRightStar,23,11,12,26,12,26,12,26,12,26);

        TextureRegion crystal = atlas.findRegion("crystalPiece");
        CrystalPiece crystalPiece = new CrystalPiece(1,1,crystal,1f,1f,new GridPoint2(0,0),true,5);


        Chest chest1 = new Chest(25,14,bottomRightStar.chestOpen,0,broadcaster);
        chest1.addObject(crystalPiece);
        bottomRightStar.addObject(chest1);


        bottomRightStar.getMap().setTile(new Tile(TERRAIN.DUNGEON_FLOOR,true),12,26);
        bottomRightStar.getMap().setTile(new TeleportTilePortal(null,player,broadcaster,"mainRoom",25,3,DIRECTION.NORTH,Color.BLACK),9,27);
        bottomRightStar.addPortal(bottomRightStar,9,27,"smallStar",1f,1f);
        return bottomRightStar;
    }
    public static World bottomLeftStar(AssetManager assetManager, CutscenePlayer player, CutsceneEventQueuer broadcaster, AnimationSet npcAnimations){
        //TODO add clue make a square and you're there
        TextureAtlas atlas = assetManager.get("res/graphics_packed/tiles/tilepack.atlas", TextureAtlas.class);
        World bottomLeftStar = new World("bottomLeftStar",38,38, assetManager, npcAnimations);
        for (int x = 0; x <38; x++) {
            for (int y = 0; y<38; y++) {
                if (y==0) {
                    bottomLeftStar.getMap().setTile(new Tile(null, false), x, y);
                    continue;
                }
                else{
                    bottomLeftStar.getMap().setTile(new Tile(TERRAIN.ds_WALL_MIDDLE_MIDDLE,false),x,y);
                }

            }
        }
        int tileCounter = 2;
        for(int i = 0;i<38;i++){
            for(int j = 1;j<tileCounter;j++){
                bottomLeftStar.getMap().setTile(new Tile(TERRAIN.DUNGEON_FLOOR,true),j,i);
            }
            if(tileCounter<38){
                tileCounter+=2;
            }
        }
        int blackCounter = 20;
        for(int i = 37;i>0;i--){
            for(int j = blackCounter;j>0;j--){
                if(j==0){
                    bottomLeftStar.getMap().setTile(new Tile(TERRAIN.ds_WALL_MIDDLE_MIDDLE,false),j,i);
                }
                bottomLeftStar.getMap().setTile(new Tile(TERRAIN.ds_WALL_MIDDLE_MIDDLE,false),j,i);

            }
            if(i%2==0){
                blackCounter--;
            }
        }

        blackCounter = 36;
        for(int i = 19;i<38;i++){
            for(int j = 37;j>blackCounter;j--){
                bottomLeftStar.getMap().setTile(new Tile(TERRAIN.ds_WALL_MIDDLE_MIDDLE,false),j,i);
            }
            blackCounter--;
        }

        bottomLeftStar.getMap().setTile(new Tile(TERRAIN.ds_WALL_MIDDLE_MIDDLE,false),1,0);
        bottomLeftStar.getMap().setTile(new Tile(TERRAIN.ds_WALL_MIDDLE_MIDDLE,false),3,1);

        int wallCountLeft = 19;
        int wallCountRight = 20;
        for(int i=36;i>0;i-=2){
            for(int j = wallCountRight;j>wallCountLeft;j--){
                bottomLeftStar.addWallObject(j,i,"middleWall",false,1f,3f);
            }
            wallCountLeft--;
            wallCountRight--;
        }

        wallCountLeft = 36;
        wallCountRight = 37;
        for(int i=19;i<36;i++){
            for(int j = wallCountRight;j>wallCountLeft;j--){
                bottomLeftStar.addWallObject(j,i,"middleWall",false,1f,3f);
            }
            wallCountLeft--;
            wallCountRight--;
        }

        for(int i =0;i<38;i++){
            for(int j =0;j<38;j++){
                if(bottomLeftStar.getMap().getTile(i,j).getTerrain() == TERRAIN.DUNGEON_FLOOR){
                    bottomLeftStar.getMap().setTile(new Tile(TERRAIN.lava_WALL_MIDDLE_MIDDLE,false),i,j);
                }
            }
        }
        //LAVA PUZZLE

        bottomLeftStar.addLava(18,26,5,5);
        bottomLeftStar.addLava(22,21,5,5);
        bottomLeftStar.addLava(27,16,5,5);

        bottomLeftStar.addLava(15,20,5,5);
        bottomLeftStar.addLava(20,14,5,5);

        bottomLeftStar.addLava(14,14,5,5);

        bottomLeftStar.addLava(10,9,5,5);

        bottomLeftStar.addTeleporters(bottomLeftStar,18,26,29,18,29,18,29,18,24,23);//bottomleft,topleft,topright,bottomright
        bottomLeftStar.addTeleporters(bottomLeftStar,22,21,20,28,24,23,29,18,24,23);
        bottomLeftStar.addTeleporters(bottomLeftStar,27,16,29,18,24,23,17,22,24,23);

        bottomLeftStar.addTeleporters(bottomLeftStar,15,20,29,18,24,23,29,18,22,16);
        bottomLeftStar.addTeleporters(bottomLeftStar,20,14,16,16,24,23,29,18,22,16);

        bottomLeftStar.addTeleporters(bottomLeftStar,14,14,29,18,12,11,29,18,24,23);

        bottomLeftStar.addTeleporters(bottomLeftStar,10,9,26,23,26,23,26,23,26,23);

        TextureRegion crystal = atlas.findRegion("crystalPiece");
        CrystalPiece crystalPiece = new CrystalPiece(1,1,crystal,1f,1f,new GridPoint2(0,0),true,4);


        Chest chest1 = new Chest(12,12,bottomLeftStar.chestOpen,0,broadcaster);
        chest1.addObject(crystalPiece);
        bottomLeftStar.addObject(chest1);

        bottomLeftStar.getMap().setTile(new TeleportTilePortal(null,player,broadcaster,"mainRoom",11,3,DIRECTION.NORTH,Color.BLACK),28,27);
        bottomLeftStar.addPortal(bottomLeftStar,28,27,"smallStar",1f,1f);
        bottomLeftStar.getMap().setTile(new Tile(TERRAIN.DUNGEON_FLOOR,true),28,26);
        bottomLeftStar.getMap().setTile(new Tile(TERRAIN.DUNGEON_FLOOR,true),28,25);
        bottomLeftStar.getMap().setTile(new Tile(TERRAIN.DUNGEON_FLOOR,true),28,24);
        bottomLeftStar.getMap().setTile(new Tile(TERRAIN.DUNGEON_FLOOR,true),28,23);
        bottomLeftStar.getMap().setTile(new Tile(TERRAIN.DUNGEON_FLOOR,true),27,23);
        bottomLeftStar.getMap().setTile(new Tile(TERRAIN.DUNGEON_FLOOR,true),26,23);
        return bottomLeftStar;
    }
}