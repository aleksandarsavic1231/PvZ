public enum Level {
	
    ONE   (1, 2, 4),
	TWO   (1, 7, 4),
	THREE (2, 5, 4),
	FOUR  (2, 7, 4),
	FIVE  (3, 7, 4);
	
    private final int nWaves;  
    
    private final int nZombies; 
    
    /**
     * The game iterations between waves.
     */
    private final int spacer;
    
    private int currWave;
   
    Level(int nWaves, int nZombies, int spacer) {
        this.nWaves = nWaves;
        this.nZombies = nZombies;
        this.spacer = spacer;
        this.currWave = 0;
    }
    
    /**
     * Get the number of zombies to spawn in current wave.
     * 
     * @return nZombies The number of zombies to spawn in current wave.
     */
	public int getnZombies() {
		currWave++;
		return nZombies;
	}
	
	/**
	 * Get the game iteration of the next wave.
	 * 
	 * @param gameCounter The current game counter.
	 * @return integer The game iteration of the next wave.
	 */
	public int nextWave(int gameCounter) {
		return gameCounter + spacer;
	}
	
	/**
	 * Is there another wave in current level.
	 * 
	 * @return boolean Is there another wave.
	 */
	public boolean isWave() {
		return !(currWave == nWaves - 1);
	}
    
}