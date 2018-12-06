/**
 * ...
 * 
 * @author kylehorne
 * @version 6 Dec 18
 */
public enum Level {
	
	// nRegularZombies, nPylonZombies, randomness, lowerBound
	ONE (1, 0, 1, 10),
	TWO (2, 1, 5, 12) {
        @Override
        public Level next() { return null; };
    };
	
	/**
	 * THe number of Regular Zombies to spawn.
	 */
	private final int nRegularZombies;
	
	/**
	 * The number of Pylon Zombies to spwan.
	 */
	private final int nPylonZombies;
	
	/**
	 * The randomness between Zombie spawns.
	 */
	private final int randomness;
	
	/**
	 * The lower bound between between the game board and first Zombie.
	 */
	private final int lowerBound;
	
	Level(int nRegularZombies, int nPylonZombies, int randomness, int lowerBound) {
		this.nRegularZombies = nRegularZombies;
		this.nPylonZombies = nPylonZombies;
		this.randomness = randomness;
		this.lowerBound = lowerBound;
	}

	public int getNRegularZombies() {
		return nRegularZombies;
	}

	public int getNPylonZombies() {
		return nPylonZombies;
	}

	public int getRandomness() {
		return randomness;
	}

	public int getLowerBound() {
		return lowerBound;
	}
	
	public Level next() {
        return values()[ordinal() + 1];
    }

}
