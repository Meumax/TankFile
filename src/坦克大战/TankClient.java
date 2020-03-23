package 坦克大战;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * 每次开启五个敌人
 * 攻击按键：W
 */

public class TankClient extends Frame
{
	private final static int WIDTH = 600;
	private final static int HEIGHT = 500;
	private static Random random = new Random();
	int X = 80,Y = 80;
	char DIR = '上';
	
	BufferedImage image = null;
	
	Tank tank = new Tank(X, Y,DIR,true,this);
	Tank enemytank = new Tank(200, 200, '上',false,this);
	
	List<Tank> tanks = new ArrayList<Tank>();
	List<Missile> missiles = new ArrayList<Missile>();
	
	
	public TankClient()
	{
		Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
		this.setTitle("坦克大战游戏V1.1");
		this.setSize(WIDTH, HEIGHT);
		this.setLocation((dimension.width-WIDTH)/2, (dimension.height-HEIGHT)/2);
		this.setBackground(new Color(210, 239, 101));//颜色
		this.addWindowListener(new WindowAdapter()
		{
			public void windowClosing(WindowEvent e)
			{
				System.exit(0);
			}
		});
		
		//注册键盘监听事件
		this.addKeyListener(new KeyPressedListener());
		
		//让窗体显示出来
		this.setVisible(true);
		new Thread(new PaintThread()).start();
		addEnemyTank();
	}
	
	//重画时候调用 repaint-->update-->paint
	public void update(Graphics graphics)
	{
		if(image == null)
		{
			image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_ARGB);
		}
		Graphics g2 = image.getGraphics();
		paint(g2);
		graphics.drawImage(image, 0, 0, null);
	}
	
	//在窗体上画东西
	//不需要自己调用
	public void paint(Graphics graphics)
	{
		graphics.setColor(new Color(210, 239, 101));
		graphics.fillRect(0, 0, WIDTH, HEIGHT);
		
		//玩家的坦克
		if(tank.isLive())
		{
			tank.Draw(graphics);
		}
		
		//敌人坦克
		for(int i=0;i<tanks.size();i++)
		{
			Tank enemyTank = tanks.get(i);
			enemyTank.Draw(graphics);
		}
		
		//画子弹
		for(int i=0;i<missiles.size();i++)
		{
			Missile missile = missiles.get(i);
			missile.Hits(tanks);
			missile.Hit(tank);
			missile.Draw(graphics);
		}
	}
	
	public void addEnemyTank()
	{
		for(int i=0;i<5;i++)
		{
			tanks.add(new Tank(50+random.nextInt(500),
					60+random.nextInt(400),
					'上',
					false,
					this));
		}
	}
	
	class PaintThread implements Runnable
	{
		public void run()
		{
			while(true)
			{
				repaint();
				try
				{
					Thread.sleep(100);
				} catch (InterruptedException e1)
				{
					e1.printStackTrace();
				}
			}
		}
	}
	
	class KeyPressedListener extends KeyAdapter
	{
		public void keyPressed(KeyEvent e)
		{
			tank.KeyPressed(e);
		}
	}
	
	public static void main(String[] args)
	{
		TankClient tankClient = new TankClient();
	}

}
