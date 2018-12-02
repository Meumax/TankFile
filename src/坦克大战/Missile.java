package 坦克大战;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.List;


public class Missile
{
	private int X;
	private int Y;
	private char DIR;
	private boolean GOOD;
	private boolean live = true;
	private final static int WIDTH = 10;
	private final static int HEIGHT = 10;
	private TankClient tankClient;


	public void Draw(Graphics graphics)
	{
		if(live)
		{
			//玩家的坦克炮弹红色
			if(GOOD == true)
			{
				graphics.setColor(Color.RED); 
			}else
			{
				graphics.setColor(Color.BLUE); 
			}
			graphics.fillOval(X, Y, WIDTH, HEIGHT);
			move();
		}else 
		{
			tankClient.missiles.remove(this);
		}
	}
	
	private void move()
	{
		switch(DIR)
		{
			case '上':
				if((Y > 0) && (Y < 500))
				{
					Y -= 10;
				}else Y -= 10;
				break;
			case '下':
				if((Y > 0) && (Y < 500))
				{
					Y += 10;
				}else live = false;
				break;
			case '左':
				if((X > 0) && (X < 600))
				{
					X -= 10;
				}else live = false;
				break;
			case '右':
				if((X > 0) && (X < 600))
				{
					X += 10;
				}else live = false;
				break;						
		}
	}
		
	public boolean Hits(List<Tank> tanks)
	{
		for(int i=0;i<tanks.size();i++)
		{
			Tank tank = tanks.get(i);
			if(Hit(tank))
			{
				return true;
			}
		}
		
		return false;
	}
	
	public boolean Hit(Tank tank)
	{ 
		if(this.live && tank.isLive() && this.isIntersect(tank) && (this.GOOD != tank.isGOOD()))
		{
			this.live = false;
			tank.HP -= 10;
			if(tank.HP == 0)
			{
				tank.setLive(false);
			}
			return true;
		}
		return false;
	}
	
	public boolean isIntersect(Tank tank)
	{
		return this.getRect().intersects(tank.getRect());
	}
	
	public Rectangle getRect()
	{
		return new Rectangle(X,Y,WIDTH,HEIGHT);
	}
	
	public Missile(int x,int y,char dir)
	{
		this.DIR = dir;
		if(DIR == '上')
		{
			this.X = x;
			this.Y = y;
		}else if(DIR == '下')
		{
			this.X = x;
			this.Y = y + 80;
		}else if(DIR == '左')
		{
			this.X = x - 40;
			this.Y = y + 40;
		}else if(DIR == '右')
		{
			this.X = x + 40;
			this.Y = y + 40;
		}		
	}
	
	public Missile(int x,int y,char dir,boolean good,TankClient tankClient)
	{
		this(x,y,dir);
		this.GOOD = good;
		this.tankClient = tankClient;
	}

	
}
