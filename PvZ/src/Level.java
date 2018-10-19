public enum Level {
	
    ONE   (1, 5),
	TWO   (1, 7),
	THREE (2, 5),
	FOUR  (2, 7),
	FIVE  (3, 7);

	private final int nZombies; 
	
    private final int nWaves;  
    
    private int currWave;
   
    Level(int nWaves, int nZombies) {
        this.nWaves = nWaves;
        this.nZombies = nZombies;
        this.currWave = 0;
    }

    public int getnWaves() {
		return nWaves;
	}
    
	public int getnZombies() {
		return nZombies;
	}
	
	public boolean isWave() {
		if (currWave == getnWaves() - 1) return false;
		currWave++;
		return true;
	}
    
}