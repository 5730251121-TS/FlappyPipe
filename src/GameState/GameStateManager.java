package GameState;

import java.awt.Graphics2D;

public class GameStateManager {

	private GameState[] gameStates;
	private int currentState;

	public static final int NUMGAMESTATES = 3;
	public static final int MENUSTATE = 0;
	public static final int PLAYSTATE = 1;
	public static final int SCORESTATE = 2;

	public GameStateManager() {
		// gameStates = new ArrayList<GameState>();

		gameStates = new GameState[NUMGAMESTATES];

		currentState = MENUSTATE;
		// gameStates.add(new MenuState(this));
		// gameStates.add(new PlayState(this));

		loadState(currentState);
	}

	private void loadState(int state) {

		if (state == MENUSTATE)
			gameStates[state] = new MenuState(this);
		if (state == PLAYSTATE)
			gameStates[state] = new PlayState(this);
		if (state == SCORESTATE)
			gameStates[state] = new ScoreState(this);

	}

	private void unloadState(int state) {
		gameStates[state] = null;
	}

	public void setState(int state) {
		unloadState(currentState);
		currentState = state;
		loadState(currentState);
	}

	public void update() {
		try {
			if (gameStates[currentState] != null)
				gameStates[currentState].update();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void draw(Graphics2D g) {
		try {
			if (gameStates[currentState] != null)
				gameStates[currentState].draw(g);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void keyPressed(int k) {
		// TODO Auto-generated method stub
		if (gameStates[currentState] != null)
			gameStates[currentState].keyPressed(k);
	}

	public void keyReleased(int k) {
		// TODO Auto-generated method stub
		if (gameStates[currentState] != null)
			gameStates[currentState].keyReleased(k);

	}
}
