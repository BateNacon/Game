/**
 *  This is the main class of the Escape from Dolten In this game, you are in an apocalypic
 *  city after a nuclear war. The goal is to make it to the safezone, but there are many dangers.
 *  The biggest danger is not zombies, but rather radiation and starvation.
 * 
 *  This main class creates and initialises all the others: it creates all
 *  rooms, creates the parser and starts the game.  It also evaluates and
 *  executes the commands that the parser returns.
 * 
 * @author  Michael Kölling and David J. Barnes
 * @version 2016.02.29
 */
import java.util.ArrayList;

public class Game 
{
    private Parser parser;
    private Room currentRoom;

    // Survival Variables
    private int health = 100;
    private int food = 100;
    private int water = 100;
    private int numTurns = 0;
    
    // Weather variables
    private double foodModifier = 1;
    private double waterModifier = 1;
    private double tripInt;

    // Back Command ArrayList
    ArrayList<String> previousDirections = new ArrayList<String>();
    private String transferDirection;
    private int numBacks;

    // Items
    consumableItems cannedMeat, bottledWater, cannedSoup, juiceBox;

    /**
     * Create the game and initialise its internal map. Also initializes the ArrayList used for the back command
     */
    public Game() 
    {
        createRooms();
        createItems();
        parser = new Parser();

        // Back command ArrayList initialization 
        for (int i = 0; i < 1; i++)
        {
            previousDirections.add(null);
        }        
    }

    /**
     * Create all the rooms and link their exits together.
     * Sets the starting room to forest
     */
    private void createRooms()
    {
        // Declaring Rooms
        Room forest, suberb1, shorelineHousing, tacoBell, policeStation, groceryStore, northSuberbs, militaryCheckpoint, 
        fireStation, bridge, crossBridge, building1, building2, researchLab, middleSchool, park, abandonedMilitaryBase, 
        policeHeadquarters, highSchool, skyscrapers, hospital, pawnShop, parkingGarage, powerPlant, suberbsAboveCity, safeZone;
      
        // Create the rooms
        forest = new Room("in a forest near a ruined city.");
        suberb1 = new Room("in the suberbs outside the city.");
        shorelineHousing = new Room("near a few riverside houses.");
        tacoBell = new Room("in a Taco Bell.");
        policeStation = new Room("in a police station");
        groceryStore = new Room("in a grocery store");
        northSuberbs = new Room("in the northern suberbs");
        militaryCheckpoint = new Room("at a military checkpoint");
        fireStation = new Room("in a fire station");
        bridge = new Room("at the begginning of a bridge");
        crossBridge = new Room("crossing the bridge, oh no it collapsed!");
        building1 = new Room("in a random delapitated building");
        building2 = new Room("in a random delapitated building");
        researchLab = new Room("in an abandoned research lab");
        middleSchool = new Room("in a middle school");
        park = new Room("in a park");
        abandonedMilitaryBase = new Room("in an abandoned military base");
        policeHeadquarters = new Room("in the cities police headquaters");
        highSchool = new Room("in a highschool");
        skyscrapers = new Room("in a skycraper");
        hospital = new Room("in a hospital");
        pawnShop = new Room("in a pawn shop");
        parkingGarage = new Room("in a parking garage");
        powerPlant = new Room("in an abandoned military power plant");
        suberbsAboveCity = new Room("in the suberbs");
        safeZone = new Room("in the safe zone");
        
        // Sets the exits

        // Forest exits
        forest.setExit("east", suberb1);
        forest.setExit("north", shorelineHousing);
        
        // shorleineHousing exits
        shorelineHousing.setExit("south", forest);
        shorelineHousing.setExit("east", tacoBell);
        
        // Suberbs exit
        suberb1.setExit("west", forest);
        suberb1.setExit("north", militaryCheckpoint);
        
        // tacoBell exits
        tacoBell.setExit("west", shorelineHousing);
        tacoBell.setExit("east", policeStation);
        
        
        // policeStation exits
        policeStation.setExit("west", tacoBell);
        policeStation.setExit("east", groceryStore);
        
        // groceryStore exits
        groceryStore.setExit("west", policeStation);
        groceryStore.setExit("east", northSuberbs);
        groceryStore.setExit("north", fireStation);
        groceryStore.setExit("south", militaryCheckpoint);
        
        // northSuberbs exits
        northSuberbs.setExit("west", groceryStore);
        northSuberbs.setExit("north", bridge);
        
        // militaryCheckpoint exits
        militaryCheckpoint.setExit("west", groceryStore);
        militaryCheckpoint.setExit("east", northSuberbs);
        militaryCheckpoint.setExit("north", fireStation);
        militaryCheckpoint.setExit("south", suberb1);
        
        // firesStation exits
        fireStation.setExit("north", bridge);
        fireStation.setExit("south", groceryStore);
        
        // Bridge exits
        bridge.setExit("south", fireStation);
        bridge.setExit("north", crossBridge);
        
        // crossBridge exits
        crossBridge.setExit("west", building1);
        crossBridge.setExit("east", building2);
        
        // building1 exit
        building1.setExit("east", crossBridge);
        building1.setExit("north", middleSchool);

        // building2 exit
        building2.setExit("west", crossBridge);
        building2.setExit("north", researchLab);

        // researchLab exit
        researchLab.setExit("south", building2);
        researchLab.setExit("west", middleSchool);
        researchLab.setExit("east", abandonedMilitaryBase);
        researchLab.setExit("north", park);

        // abandonedMilitaryBase exits
        abandonedMilitaryBase.setExit("north", suberbsAboveCity);
        abandonedMilitaryBase.setExit("west", researchLab);

        // park exits
        park.setExit("south", researchLab);
        park.setExit("west", policeHeadquarters);

        // middleSchool exits
        middleSchool.setExit("east", researchLab);
        middleSchool.setExit("west", skyscrapers);
        middleSchool.setExit("north", policeHeadquarters);
        middleSchool.setExit("south", building1);

        // policeHeadquarters exits
        policeHeadquarters.setExit("south", middleSchool);
        policeHeadquarters.setExit("west", highSchool);
        policeHeadquarters.setExit("east", park);

        // highSchool exits
        highSchool.setExit("east", policeHeadquarters);
        highSchool.setExit("south", skyscrapers);
        highSchool.setExit("west", hospital);

        // skycraper exits
        skyscrapers.setExit("north", highSchool);
        skyscrapers.setExit("east", middleSchool);
        
        // hospital exits
        hospital.setExit("east", highSchool);
        hospital.setExit("west", pawnShop);

        // pawnShop exits
        pawnShop.setExit("east", hospital);
        pawnShop.setExit("north", parkingGarage);

        // parkingGarage exits
        parkingGarage.setExit("south", pawnShop);
        parkingGarage.setExit("north", powerPlant);

        // powerPlant exits
        powerPlant.setExit("south", parkingGarage);
        powerPlant.setExit("north", safeZone);
        
        // suberbsAboveCity exits
        suberbsAboveCity.setExit("south", abandonedMilitaryBase);

        currentRoom = forest; // Starts the game in forest
    }

    /**
     * Creates the items
     * pre - must have the items decalred in the instance variables
     * post - all items initialized to their specified parameters
     * location, itemName, itemDesc
     * Object created in the consumableItems class
     */
    private void createItems()
    {
        cannedMeat = new consumableItems("shorelineHousing", "bottledWater", "A simple bottle of water");
        bottledWater = new consumableItems("shorelineHousing", "Bottled Water", "A simple bottled water");
        cannedSoup = new consumableItems("groceryStore", "Canned Soup", "A can of soup");
        juiceBox = new consumableItems("groceryStore", "Juice Box", "AA juice box");
    }

    /**
     *  Main play routine.  Loops until end of play.
     * Called by the main method
     */
    public void play() 
    {            
        printWelcome();

        // Enter the main command loop.  Here we repeatedly read commands and
        // execute them until the game is over.
                
        boolean finished = false;
        int temp = 0;
        while (! finished) {
            Command command = parser.getCommand();
            finished = processCommand(command);
            // Ensures player dies when health, food, or water reach 0
            if (health <= 0) {
                finished = true;
            }else if (food <= 0) {
                finished = true;
            }else if (water <= 0) {
                finished = true;
            }else if( currentRoom.getShortDescription().equals("in the safe zone")) {
                finished = true;
                 temp++;
            }
            }
            if (temp == 1)
            {
            System.out.println("U won!!!");
            }else if (temp == 0){
                System.out.println("Me pesonally, I wouldn't let a game beat me like that...");
            }
    }
        
    /**
     * Print out the opening message for the player.
     */
    private void printWelcome()
    {
        System.out.println();
        System.out.println("Welcome to Escape from Dolten, a game heavil influenced by Escape from Tarkov (tm)"); //Dolten is a combination of the latin word for pain and strife
        System.out.println("Dolten is an abandoned metropolitan area due to nuclear war");
        System.out.println("Type 'help' if you need help. The goal is to survive.");
        System.out.println();
        System.out.println(currentRoom.getLongDescription());
        setWeather();
    }

    /**
     * Given a command, process (that is: execute) the command.
     * @param command The command to be processed.
     * @return true If the command ends the game, false otherwise.
     */
    private boolean processCommand(Command command) 
    {
        boolean wantToQuit = false;

        if(command.isUnknown()) {
            System.out.println("I don't know what you mean...");
            return false; 
        }
        
        String commandWord = command.getCommandWord();
        if (commandWord.equals("help")) {
            printHelp(); // Prints the availible commands
        }
        else if (commandWord.equals("go")) {
            goRoom(command);
            numTurns++;
            food -= 5 * foodModifier;
            water -= 5 * waterModifier;
            showStats(); // Decrements food and water every time the player moves rooms
            
            // Creates a 2% chance the player will trip, reduces 5 health
            trip();
            if (tripInt == 0) {
                System.out.println("");
            }else
            System.out.println("You tripped clumsy person");
            health -= tripInt; //Health decrements by tripInt, default 5
        }
        else if (commandWord.equals("quit")) {
            wantToQuit = quit(command);
        }else if (commandWord.equals("back")) {
            //Back command
            goBack(command);
        }else if (commandWord.equals("map")) {
            //Prints the map
            printMap();
        }else if (commandWord.equals("clear")) {
            //clears the terminal for aethtetics
            clearTerminal();
        }else if (commandWord.equals("cheat")){
            //Gives the player psuedo-inifinite resources to survive
            food = 999999999;
            water = 999999999;
            health = 999999999;
        }else if(commandWord.equals("use"))
        {
            useItem(command.getSecondWord());
            //Ensures player cannot go above 100 of any resource
            if(water > 100)
            {
                water = 100;
            }else if(food > 100)
            {
                food = 100;
            }
            //Shows stats to player
            showStats();
        }else if (commandWord.equals("search"))
        {
            //Searches the currentRoom for items and puts them in inventory
            if (canSearch == true){
                search();
            }else{
                System.out.println("You can't search twice");
            }
        }else if (commandWord.equals("inventory"))
        {
            //Prints the inventory
            printInventory();
        }else if (commandWord.equals("tutorial"))
        {
            //Prints the tutorial
            printTut();
        }
        // else command not recognised.
        return wantToQuit;
    }

    // implementations of user commands:

    /**
     * Print out some help information.
     * Here we print some stupid, cryptic message and a list of the 
     * command words.
     */
    private void printHelp() 
    {
        System.out.println("You are in the middle of a post apocalyptic city,");
        System.out.println("your goal is to make it to the safezone...");
        System.out.println();
        System.out.println("Your command words are:");
        parser.showCommands();
    }

    /** 
     * Try to in to one direction. If there is an exit, enter the new
     * room, otherwise print an error message.
     */
    private void goRoom(Command command) 
    {
        if(!command.hasSecondWord()) {
            // if there is no second word, we don't know where to go...
            System.out.println("Go where?");
            return;
        }

        String direction = command.getSecondWord();
        transferDirection = command.getSecondWord();

        // Try to leave current room.
        Room nextRoom = currentRoom.getExit(direction);

        if (nextRoom == null) {
            System.out.println("There is nothing in that direction");
            System.out.println("Food and Water decremented because you tried to go");
        }
        else {
            currentRoom = nextRoom;
            System.out.println(currentRoom.getLongDescription());
            canSearch = true;
        }
        initializeArrayList();
    }

    /**
     * Creates the arraylist for the back command
     */
    private void initializeArrayList()
    {
        previousDirections.add(numTurns, transferDirection);
    }

    /**
     * The back command which uses an arraylist 
     * to go back mulitple turns
     * If used tooheavily might break
     */
    private void goBack(Command command)
    {
        int arrayNumber;
        arrayNumber = previousDirections.indexOf(null) - 1 - numBacks;
        if (arrayNumber < 0)
        {
            arrayNumber = 0;
        }
        String temp = previousDirections.get(arrayNumber);
        if (temp.equals("north"))
        {
            temp = "south";
        }else if (temp.equals("south"))
        {
            temp = "north";
        }else if (temp.equals("east"))
        {
            temp = "west";
        }else if (temp.equals("west"))
        {
            temp = "east";
        }

        String direction = temp;
        Room nextRoom = currentRoom.getExit(direction);

        if (nextRoom == null) {
            System.out.println("There is nothing in that direction");
            System.out.println("Food and Water decremented because you tried to go");
        }
        else {
            currentRoom = nextRoom;
            System.out.println(currentRoom.getLongDescription());
        }
        numBacks++;
    }

    /** 
     * "Quit" was entered. Check the rest of the command to see
     * whether we really quit the game.
     * @return true, if this command quits the game, false otherwise.
     */
    private boolean quit(Command command) 
    {
        if(command.hasSecondWord()) {
            System.out.println("Quit what?");
            return false;
        }
        else {
            return true;  // signal that we want to quit
        }
    }
    
    /**
     * Helper method to print the map for the game when called by a player as a command
     */
    public void printMap() {
        System.out.println("                                    |-------------|                                            |");
        System.out.println("                                    |  SAFEZONE!  |                                            |");
        System.out.println("        |---------------------------------| |-----|                                            |");
        System.out.println("        | |---------------------------------|                                                  |");
        System.out.println("        | |                                                       NOTHINGNESS                  |");
        System.out.println("   |----| |----|                                                                               |");
        System.out.println("   | Abandoned |                                                                               |");
        System.out.println("   |   Power   |                                                                               |");
        System.out.println("   |   Plant   |                                                                               |"); 
        System.out.println("   |-----------|----------|-------------|------------------|------------|----------------------|");
        System.out.println("   |  Parking  |          | High School |    Police HQ     |    Park    |                      |");
        System.out.println("   |-----------| Hospital |-------------|------------------|------------|  Abandoned Military  |");
        System.out.println("   | Pawn Shop |          | Skyscrapers |   Middle School  | Reseach Lab|         Base         |");
        System.out.println("|--|-----------|----------|-------------|-----------|------|------------|                      |");
        System.out.println("|          Shoreline (nothing)          |Buildings  |      |  Buildings |----------------------|");    
        System.out.println("|---------------------------------------|-----------|Bridge|------------|----------------------|");
        System.out.println("|       River          River         River          |      |       River      River      River |");
        System.out.println("|-----------------------------------------------|---|------|----|------------------------------|");
        System.out.println("|               Shoreline (nothing)             | Fire Station  |       Shoreline (nothing)    |");
        System.out.println("|------------------|----------------------------|---------------|------------------------------|");
        System.out.println("| Shoreline Housing| Taco Bell | Police Station | Grocery Store | Military Checkpoint | Suberbs|");
        System.out.println("|------------------|----------------------------|---------------|------------------------------|");
        System.out.println("| Forest (Start)   |            Nothing         |     Suberbs   |           Nothing            |");
        System.out.println("|------------------|                            |---------------|                              |");        
        System.out.println("");
        currentRoom.getLongDescription();
    }

    //randomly sets the maps weatherconditions
    private void setWeather()
    {
        double rNum = Math.random();
        if (rNum < 0.10) {
            foodModifier = 1.5; //Cold modifier
            System.out.println("It is very cold, I'll need more food to survive");
            return;
        }else if (rNum >= 0.10 && rNum < 0.20) {
            foodModifier = 2; //Snowing modifier
            System.out.println("It is snowing, I'll need more food to survive");
            return;
        }else if (rNum >= 0.20 && rNum < 0.30) {
            waterModifier = 2; //Hot modifier
            System.out.println("It is hot, I'll need more water to survive");
            return;
        }else if (rNum >= 0.30 && rNum < 0.40) {
            foodModifier = 1.5; //Raining modifier
            System.out.println("It is raining, I'll need more food to survive");
            return;
        }else if (rNum >= 0.40 && rNum < 0.50) {
            waterModifier = 0.75; //Perfect weather modifier
            foodModifier = 0.75;  
            System.out.println("The weather is perfect!");
            return;
        } //Otherwise modifier is 1 AKA unchanged
        waterModifier = 1;
        foodModifier = 1;
        System.out.println("The weather is normal");
    }

    //Helper method for tripping
    private void trip()
    {
        if (Math.random() < 0.05) {
            tripInt = 5;
        }else
        tripInt = 0;
    }
    
    //Clears command terminal
    public void clearTerminal()
    {
        System.out.println('\u000C');
    }

    /**
     * Uses the item
     * Ensures player has the item and adds its resources to the player's 
     * resource pool
     * @param item
     */
    private void useItem(String item)
        {
            if (item == null)
            {
                System.out.println("Use what item?");
                return;
            }

            //Uses cannedMeat
            if (item.equals("cannedMeat") && cannedMeat.getDoesHaveItem() == true)
            {
                food += 10;
                water += 5;
                showStats();
                cannedMeat.setDoesNotHaveItem();
            }else if (cannedMeat.getDoesHaveItem() == false)
            {
                System.out.println("You dont have any canned meat");
            }

            //Uses bottledWater
            if (item.equals("bottledWater") && bottledWater.getDoesHaveItem() == true)
            {
                food += 0;
                water += 10;
                showStats();
                bottledWater.setDoesNotHaveItem();
            }else if (bottledWater.getDoesHaveItem() == false)
            {
                System.out.println("You dont have any bottled water");
                return;
            }

            //Uses cannedSoup
            if (item.equals("cannedSoup") && cannedSoup.getDoesHaveItem() == true)
            {
                food += 10;
                water += 10;
                showStats();
                cannedSoup.setDoesNotHaveItem();
            }else if (cannedSoup.getDoesHaveItem() == false)
            {
                System.out.println("You dont have any bottled water");
                return;
            }

            //Uses juiceBox
            if (item.equals("juiceBox") && juiceBox.getDoesHaveItem() == true)
            {
                food += 10;
                water += 10;
                showStats();
                juiceBox.setDoesNotHaveItem();
            }else if (juiceBox.getDoesHaveItem() == false)
            {
                System.out.println("You don't have a juice box");
                return;
            }
        }

        //Shows the player stats
        private void showStats()
        {
            System.out.println("Health: " + health);
            System.out.println("Food: " + food);
            System.out.println("Water: " + water);
        }

        /**
         * Instance variables for the search command so I don't have to go all the
         * way to the top of game class
         * search searches for items and adds them to the players inventory 
         * ensuring that the currentRoom has that item in it
         */
        private boolean foundWater = false;
        private boolean foundMeat = false;
        private boolean foundSoup = false;
        private boolean foundJuiceBox = false;
        private boolean canSearch = true;
        private void search()
        {
            //Local arraylist and currentRoom identitfier
            String roomDescription = currentRoom.getShortDescription();
            String waterRooms[] = {"near a few riverside houses.", "in the suberbs outside the city."};
            String meatRooms[] = {"in a Taco Bell."};
            String soupRoooms[] = {"in a grocery store"};
            String juiceRooms[] = {"in a grocery store"};

            //Checks if room can give itemm then does if possible
            if (roomDescription == waterRooms[0] || roomDescription == waterRooms[1] && foundWater != true)
            {
                bottledWater.setHasItem();
                System.out.println("You found a bottle of water!");
                foundWater = true;
            }else if(roomDescription == meatRooms[0] && foundMeat != true)
            {
                cannedMeat.setHasItem();
                System.out.println("You found canned meat!");
            }else if (roomDescription == soupRoooms[0] && foundSoup != true){
                cannedSoup.setHasItem();
                System.out.println("You found canned soup!");
            }else if(roomDescription == juiceRooms[0] && foundJuiceBox != true){
                juiceBox.setHasItem();
                System.out.println("You found juice box!");
            }else{
                System.out.println("There is nothing here");
            }
            canSearch = false;
        }

        //Prints all items in the players inventory
        private void printInventory()
        {
            if (cannedMeat.getDoesHaveItem() == true)
            {
                System.out.println("cannedMeat");
            }
            if (bottledWater.getDoesHaveItem() == true)
            {
                System.out.println("bottledWater");
            }
            if (cannedSoup.getDoesHaveItem() == true)
            {
                System.out.println("juiceBox");
            }else {
                System.out.println("You have nothing lol");
            }
        }

        //Prints the tutorial
        public void printTut()
        {
            System.out.println("To use the go command locate where the exits are defined,");
            System.out.println("the type go east for example.");
            System.out.println("To use the use command type use cannedMeat or other item for example.");
            System.out.println("The items given in the inventory commmand are the exact spelling and capitialization needed for use command to work");
        }
}