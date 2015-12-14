package Entity;

public class Enemy extends MapObject {

	protected int health, maxHealth;
	protected boolean dead;
	protected int damage;

	protected boolean flinching;
	protected long flinchTimer;

	public Enemy() {
	}

	public void setDead(boolean b) {
		dead = b;
	}

	public boolean isDead() {
		return dead;
	}

	public void hit() {
		dead = true;
	}

	public void update() {

	}

}
