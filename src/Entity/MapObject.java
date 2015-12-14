package Entity;

import java.awt.Graphics2D;

public class MapObject {

	// position
	protected double x;
	protected double y;
	protected double dx;
	protected double dy;

	// dimension;
	protected int width;
	protected int height;

	// animation
	protected Animation animation;
	protected int currentAction;
	protected int previousAction;

	// movement
	protected boolean jumping;
	protected boolean falling;

	// movement attributes
	protected double moveSpeed;
	protected double maxSpeed;
	protected double fallSpeed;
	protected double maxFallSpeed;
	protected double jumpStart;
	protected double stopJumpSpeed;

	protected boolean outOfScreen;
	protected boolean OnScreen;

	// constructor
	public MapObject() {
	}

	public int getx() {
		return (int) x;
	}

	public int gety() {
		return (int) y;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public boolean isOutOfScreen() {
		return outOfScreen;
	}

	public boolean isOnScreen() {
		return OnScreen;
	}

	public void setPosition(double x, double y) {
		this.x = x;
		this.y = y;
	}

	public void setVector(double dx, double dy) {
		this.dx = dx;
		this.dy = dy;
	}

	public void setJumping(boolean jumping) {
		this.jumping = jumping;
	}

	public void draw(Graphics2D g) {
		g.drawImage(animation.getImage(), (int) (x - width / 2), (int) (y - height / 2), width, height, null);
	}
}
