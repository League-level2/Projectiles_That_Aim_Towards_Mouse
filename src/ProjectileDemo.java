import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

public class ProjectileDemo extends JPanel implements MouseListener, ActionListener {
	class Player {
		public int x;
		public int y;
		public int width;
		public int height;

		public Player() {
			this.x = 300;
			this.y = 300;
			this.width = 50;
			this.height = 50;
		}
		
		public void draw(Graphics g) {
			g.setColor(Color.BLUE);
			g.fillRect(x - width / 2, y - height / 2, width, height);
		}
	}

	class Projectile {
		public int x;
		public int y;
		public int width;
		public int height;

		public double xSpeed;
		public double ySpeed;
		public double maxSpeed;

		public Projectile(int x, int y) {
			this.x = x;
			this.y = y;
			this.width = 20;
			this.height = 20;
			this.xSpeed = 0;
			this.ySpeed = 0;
			this.maxSpeed = 20;
		}
		
		public void draw(Graphics g) {
			g.setColor(Color.RED);
			g.fillRect(x - width / 2, y - height / 2, width, height);
		}
		
		public void update() {
			x += xSpeed * maxSpeed;
			y += ySpeed * maxSpeed;
		}
	}

	public static final int WIDTH = 600;
	public static final int HEIGHT = 600;

	private JFrame window;
	private Timer timer;
	private Player player;
	private ArrayList<Projectile> projectiles;
	private Projectile projectile;

	public ProjectileDemo() {
		setPreferredSize(new Dimension(WIDTH, HEIGHT));
		window = new JFrame();
		timer = new Timer(1000 / 60, this);
		player = new Player();
		projectiles = new ArrayList<Projectile>();
		projectile = new Projectile(player.x, player.y);

		window.addMouseListener(this);
		window.add(this);
		window.pack();
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setVisible(true);
		timer.start();
	}

	public static void main(String[] args) {
		new ProjectileDemo();
	}

	public void paintComponent(Graphics g) {
		g.setColor(Color.GRAY);
		g.fillRect(0, 0, WIDTH, HEIGHT);

		g.setColor(Color.BLACK);
		g.drawString("CLICK ANYWHERE", 100, 100);
		
		player.draw(g);
		for(Projectile p : projectiles) {
			p.draw(g);
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
		Projectile projectile = new Projectile(player.x, player.y);
		projectile.x = player.x;
		projectile.y = player.y;

		int xdif = e.getX() - player.x;
		int ydif = e.getY() - player.y;

		double angle = Math.atan2((double) ydif, (double) xdif);

		projectile.xSpeed = Math.cos(angle);
		projectile.ySpeed = Math.sin(angle);
		projectiles.add(projectile);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		for(int i = 0; i < projectiles.size(); i++) {
			Projectile p = projectiles.get(i);
			p.update();
			if(p.x > WIDTH || p.x < 0 || p.y > HEIGHT || p.y < 0) {
				projectiles.remove(i);
			}
		}
		repaint();
	}
	public void mouseReleased(MouseEvent e) {}
	public void mouseEntered(MouseEvent e) {}
	public void mouseExited(MouseEvent e) {}
	public void mouseClicked(MouseEvent e) {}
}
