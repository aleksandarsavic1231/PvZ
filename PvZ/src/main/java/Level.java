/**
 * Levels making up PvZ.
 * 
 * @author kylehorne
 * @version 6 Dec 18
 */
public enum Level {
	
	// nRegularZombies, nPylonZombies, randomness, lowerBound
	ONE (1, 0, 1, 0),
	TWO (2, 1, 5, 2),
	THREE (2, 2, 8, 2) {
        @Override
        public Level next() { return null; };
    };
	
	/**
	 * The number of Regular Zombies to spawn.
	 */
	private final int nRegularZombies;
	
	/**
	 * The number of Pylon Zombies to spwan.
	 */
	private final int nPylonZombies;
	
	/**
	 * The domain of randomness between Zombie spawns.
	 */
	private final int randomness;
	
	/**
	 * The lower bound between the PvZ Board and the first spawnable Zombie.
	 */
	private final int lowerBound;
	
	/**
	 * Constructor.
	 * 
	 * @param nRegularZombies The number of Regular Zombies to spawn.
	 * @param nPylonZombies The number of Pylon Zombies to spawn.
	 * @param randomness The domain of randomness between Zombie spawns.
	 * @param lowerBound The lower bound between the PvZ Board and the first spawnable Zombie.
	 */
	Level(int nRegularZombies, int nPylonZombies, int randomness, int lowerBound) {
		this.nRegularZombies = nRegularZombies;
		this.nPylonZombies = nPylonZombies;
		this.randomness = randomness;
		this.lowerBound = lowerBound + Board.COLUMNS;
	}

	/**
	 * Get the number of Regular Zombies to spawn.
	 * 
	 * @return int The number of Regular Zombies to spawn.
	 */
	public int getNRegularZombies() {
		return nRegularZombies;
	}

	/**
	 * Get the number of Pylon Zombies to spawn.
	 * 
	 * @return int The number of Pylon Zombies to spawn.
	 */
	public int getNPylonZombies() {
		return nPylonZombies;
	}

	/**
	 * Get the domain of randomness between Zombie spawns.
	 * 
	 * @return int The domain of randomness between Zombie spawns.
	 */
	public int getRandomness() {
		return randomness;
	}

	/**
	 * Get the lower bound between the PvZ Board and the first spawnable Zombie.
	 * 
	 * @return int The lower bound between the PvZ Board and the first spawnable Zombie.
	 */
	public int getLowerBound() {
		return lowerBound;
	}
	
	/**
	 * Get the next Level.
	 * 
	 * @return Level The next Level.
	 */
	public Level next() {
        return values()[ordinal() + 1];
    }

}
