package 坦克大战;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.util.Random;

public class Tank
{
	//坦克大小
	final static int WIDTH = 30;//坦克车身正方形边长
	final static int W1 = 10;//炮筒的宽度
	final static int H1 = 20;//炮筒的长度
	
	//坦克位置和方向
	private int X;
	private int Y;
	private char DIR;
	private boolean GOOD;
	private int step;
	private boolean live = true;
	public int HP = 100;
	
	private TankClient tankClient;//用于装弹药
	
	public boolean isGOOD()
	{
		return GOOD;
	}

	public void setGOOD(boolean gOOD)
	{
		GOOD = gOOD;
	}

	public boolean isLive()
	{
		return live;
	}

	public void setLive(boolean live)
	{
		this.live = live;
	}

	//画坦克
	public void Draw(Graphics graphics)
	{
		if(live == false)
		{
			if(!GOOD)
			{
				tankClient.tanks.remove(this);
			}
		}
		
		//颜色选择
		if(GOOD == true)
		{
			graphics.setColor(Color.RED); 
		}else
		{
			graphics.setColor(Color.BLUE); 
		}
		
		//画坦克车身
		graphics.fillRect(X, Y, WIDTH, WIDTH);
		graphics.drawString("HP:"+Integer.toString(HP), X-35, Y);
		
		//画坦克炮筒
		switch (DIR)
		{
			case '上':
				graphics.fillRect(X+10,Y-20, W1, H1);
				break;
			case '下':
				graphics.fillRect(X+10,Y+30, W1, H1);
				break;
			case '左':
				graphics.fillRect(X-20,Y+10, H1, W1);
				break;
			case '右':
				graphics.fillRect(X+30,Y+10, H1, W1);
				break;
		}
		
		//让敌人坦克动起来
		if(GOOD == false)
		{
			move();
		}
	}
	
	//坦克移动
	private void move()
	{
		//每走20步改变一次方向，方向随机
		if(step == 20)
		{
			Random random = new Random();
			int n = random.nextInt(4);
			switch (n)
			{
				case 0:
					DIR = '上';
					break;
				case 1:
					DIR = '下';
					break;
				case 2:
					DIR = '左';
					break;
				case 3:
					DIR = '右';
					break;
			}
			step = 0;
			//随机发射炮弹
			OpenFire(DIR);
		}
		
		//如果碰到边界就不再前进
		if(X <= 20)
		{
			X = 20;
		}else if(X >= 550)
		{
			X = 550;
		}else if(Y <= 50)
		{
			Y = 50;
		}else if(Y >= 450)
		{
			Y = 450;
		}
		
		//前进的方向
		switch (DIR)
		{
			case '上':
				Y -= 5;
				step += 1;
				break;
			case '下':
				Y += 5;
				step += 1;
				break;
			case '左':
				X -= 5;
				step += 1;
				break;
			case '右':
				X += 5;
				step += 1;
				break;
		}		
	}
	
	//按键检测
	public void KeyPressed(KeyEvent e)
	{
		if((e.getKeyCode() == KeyEvent.VK_LEFT) && (X != 25))//检测到左
		{
			X -= 5;
			DIR = '左';
		}else if((e.getKeyCode() == KeyEvent.VK_RIGHT) && (X != 545))//检测到右
		{
			X += 5;
			DIR = '右';
		}else if((e.getKeyCode() == KeyEvent.VK_UP) && (Y != 50))//检测到上
		{
			Y -= 5;
			DIR = '上';
		}else if((e.getKeyCode() == KeyEvent.VK_DOWN) && (Y != 445))//检测到下
		{
			Y += 5;
			DIR = '下';
		}else if((e.getKeyCode() == KeyEvent.VK_W) && this.isLive())
		{
			OpenFire(DIR);
		}
	}
	
	public void OpenFire(char DIR)
	{ 
		Missile missile = new Missile(X+10, Y-30,DIR,GOOD,tankClient);
		tankClient.missiles.add(missile);
	}
	
	public Rectangle getRect()
	{
		return new Rectangle(X,Y,WIDTH,WIDTH);
	}
	
	public boolean isIntersect(Missile missile)
	{
		return this.getRect().intersects(missile.getRect());
	}
	
	public Tank(int x,int y,char dir)
	{
		this.X = x;
		this.Y = y;	
		this.DIR = dir;
	}
	
	public Tank(int x,int y,char dir,boolean good,TankClient tankClient)
	{
		this(x, y, dir);
		this.GOOD = good;
		this.tankClient = tankClient;
	}
	
	
}
