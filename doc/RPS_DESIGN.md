#RPS_DESIGN

CRC Cards:

**Player Class**:


*Constructor*:
- Sets name to string parameter
- Sets score to zero

*Methods*:
int returnScore()
void addScore() // increments by one
void resetScore() // sets to zero
String throwWeapon() // return Random weapon that is viable
    
Called in Main to create new players, update score, get weapon

**RuleLoader Class**:

*Constructor*:
- Reads a file giving the initial relationships between weapons
- Creates an instance variable hashmap that maps a string (name of weapon) to a collection of weapons it beats

*Methods*:
boolean beats(String weapon1, String weapon2) // returns true if weapon1 beats weapon2 (s2 is in collection of weapons s1 beats according to map)

void addWeapon(String weaponName, Collection weaponBeats) // adds a new Key to the hashmap with weaponName, maps to the Collection given
    

**Main Class**: 

Runs the game in the following sequence
- Load the Rules from call to Rule Loader class (from file)
- Creates adequate number of player objects
- Loops for each round, compares .throw methods from each player that number of times
- Increments each player's score using .addScore() within loop
compares final scores
- Displays winner

### Use Cases:
- A new game is started with two players, their scores are reset to 0.

Main loads rules: new RuleLoader(file rules)
    
Creates two player objects 
    Player p1 = new Player(String name); 
    Player p2 = new Player(String name);

- A player chooses his RPS "weapon" with which he wants to play for this round.

player.throwWeapon() method generates a random String within the Collection of available weapons

Note: for more complex gameplay, one could stores probabilites representing the odds that a given player throws a weapon and then use these to determine the return of throwWeapon()

- Given two players' choices, one player wins the round, and their scores are updated.

Main Class compares the two weapons using rules.beats(weapon1, weapon2) 
Increments the winning players score by calling player.addScore()

- A new choice is added to an existing game and its relationship to all the other choices is updated.

Call rules.addWeapon(String choice, Collection what it beats)

- A new game is added to the system, with its own relationships for its all its "weapons".

RuleLoader class loads this information from a text file and updates its hashmap with appropriate matchups (by calling constructor)


NOTE*: We chose to implement this HashMap style design within the RuleLoader class in order to avoid having to create a separate class/object for every new weapon type in the game. For example, having 100 different weapons and matchups would have required 100 unique classes, all of which were almost the exact same class with just a String. We believe simply updating a Hashmap for every new weapon and what it beats would result in more adaptable code.






