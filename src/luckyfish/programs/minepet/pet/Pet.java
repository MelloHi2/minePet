package luckyfish.programs.minepet.pet;

import luckyfish.programs.minepet.utils.Location2D;

/**
 * 宠物
 */
public interface Pet {
	Location2D getLocation();
	void setLocation(Location2D location);

	void lookatTarget(Location2D target);
	void cancelLooking();

	void moveTo(Location2D target);
	void jump();
	void sit();

	void update();
}
