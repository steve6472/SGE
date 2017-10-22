package com.steve6472.sge.main.game;

import java.util.List;

import com.steve6472.sge.gfx.Sprite;
import com.steve6472.sge.main.BaseGame;

public abstract class BaseEntity extends Killable implements IObject, Cloneable, IInit
{
	protected Vec2 loc;
	protected AABB box;
	protected Blur blur;
	protected Sprite sprite;
	protected EntityList entityList;
	protected BaseGame game;
	protected List<Task> tasks;
	protected double speed = 1d;
	protected double angle;
	protected int id = 0;
	
	public BaseEntity()
	{
		
	}

	public abstract Sprite setSprite();
	
	/*
	 * Events
	 */
	
	/**
	 * Used for changing variables (Like new location, different sprite ect.)
	 */
	public void cloneEvent() {};

	/*
	 * Methods
	 */

	/**
	 * 
	 * @param fromx1 From X
	 * @param fromy1 From Y
	 * @param fromx2 To X
	 * @param fromy2 To Y
	 * @return angle
	 */
	public double countAngle(double fromx1, double fromy1, double fromx2, double fromy2)
	{
		return -Math.toDegrees(Math.atan2(fromx1 - fromx2, fromy1 - fromy2));
	}
	
	public double countAngle(Vec2 from, Vec2 to)
	{
		return -Math.toDegrees(Math.atan2(from.getX() - to.getX(), from.getY() - to.getY()));
	}
	
	/**
	 * Moves entity in direction (getAngle()) with speed (getSpeed())
	 * Moves loc & box
	 */
	public void move()
	{
		loc.move2(getAngle(), getSpeed());
		if (getSprite() != null)
			setBox(new AABB(loc.getX(), loc.getY(), loc.getX() + getSprite().getWidth(), loc.getY() + getSprite().getHeight()));
	}
	
	/**
	 * Moves entity in direction (angle) with speed (getSpeed)
	 * Moves loc & box
	 */
	public void move(double angle)
	{
		loc.move2(angle, getSpeed());
		if (getSprite() != null)
			setBox(new AABB(loc.getX(), loc.getY(), loc.getX() + getSprite().getWidth(), loc.getY() + getSprite().getHeight()));
	}
	
	/**
	 * Safetly clones entity
	 */
	public BaseEntity clone()
	{
		try
		{
			return (BaseEntity) super.clone();
		} catch (CloneNotSupportedException e)
		{
			e.printStackTrace();
			return null;
		}
	}
	
	public final void tickTasks()
	{
		if (game == null)
			return;
		tasks.forEach((c) -> c.tickTask(this));
	}
	
	/*
	 * Setters
	 */

	public void setLocation(Vec2 newLoc) { this.loc = newLoc; }
	
	public void setId(int id) { this.id = id; }
	
	public void setSize(double width, double height) { this.setBox(new AABB(getLocation(), width, height)); }

	/**
	 * Creates new Vec2 from given newLoc
	 * @param newLoc new Location
	 */
	public void setNewLocation(Vec2 newLoc) { this.loc = newLoc.clone(); }
	
	public void setBox(AABB newBox) { this.box = newBox; }
	
	public void setBlur(Blur blur) { this.blur = blur; }
	
	public void setSprite(Sprite sprite) { this.sprite = sprite; }
	
	public void setEntityList(EntityList entityList) { this.entityList = entityList; }
	
	public void setGame(BaseGame game) { this.game = game; }

	/**
	 * If loc (Vec2) is null will create new Vec2!
	 * @param x
	 * @param y
	 */
	public void setLocation(double x, double y)
	{
		if (this.loc == null)
			loc = new Vec2(0, 0);
		
		this.loc.setLocation(x, y);
	}
	
	public void setSpeed(double newSpeed) { this.speed = newSpeed; }

	public void setAngle(double angle) { this.angle = angle; }

	/*
	 * Getters
	 */

	public final Vec2 getLocation() { return loc; }

	public final Vec2 getNewLocation() { return loc.clone(); }
	
	public final AABB getBox() { return box; }
	
	public final Blur getBlur() { return blur; }
	
	public Sprite getSprite() { if (entityList != null) return entityList.getSprites().get(getId()); else return null; }
	
	public String getName() { return this.getClass().getName().split("\\.")[this.getClass().getName().split("\\.").length - 1]; }
	
	public final BaseGame getGame() { return game; }
	
	public final double getSpeed() { return speed; }

	public final double getAngle() { return angle; }
	
	public final int getId() { return id; }
}

interface IInit
{
	/**
	 * Called when creating the entity
	 * @param game
	 */
	public void initEntity(BaseGame game);
}