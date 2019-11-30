package game;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.nio.file.Path;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;




//��Ϸ���忪ʼ��


public class MainGame extends JFrame implements MapConfig {
	
	int[][][] map1 = new int[18][18][1];//��ͼ����
	int playerx =0;//�������
	int playery =0;
	boolean diy = false;//��������ƹؿ�
	int level = 1;//�ؿ�
	//��Ϸ���
	JPanel panel;
	
	public MainGame(){
		
		this.setTitle("������");
		this.setSize(900,950);
		this.setLayout(new FlowLayout());
		this.setDefaultCloseOperation(3);
		//���ô������
		this.setLocationRelativeTo(null);
		//��������
		this.setResizable(false);
		
		JMenuBar menuBar = new JMenuBar();
		this.add(menuBar,BorderLayout.NORTH);
		
		JMenu menu = new JMenu("�˵�");
		menuBar.add(menu);
				
		JMenuItem menuItem = new JMenuItem("ѡ��");
		menuItem.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				//System.out.println("ѡ��")��
				GetLevelDialog dialog = new GetLevelDialog();
				if (dialog.getValue()!=0) {
					diy = dialog.isdiy();
					level = dialog.getValue();
					GetMap(level, diy);
				}

			}
		});
		menu.add(menuItem);
		
		JMenuItem menuItem_1 = new JMenuItem("���¿�ʼ");
		menuItem_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//���¿�ʼ
				GetMap(level,diy);
			}
		});
		menu.add(menuItem_1);
		
		JMenuItem menuItem_2 = new JMenuItem("����");
		menuItem_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//System.out.println("����");
				JOptionPane.showMessageDialog(null, "Sokoban Team","����",JOptionPane.PLAIN_MESSAGE);
			
			}
		});
		
		JMenu menu_1 = new JMenu("\u81EA\u5B9A\u4E49");
		menuBar.add(menu_1);
		
		JMenuItem menuItem_3 = new JMenuItem("��ͼ�༭��");
		menuItem_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new CreatMap();
			}
		});
		menu_1.add(menuItem_3);
		menuBar.add(menuItem_2);
		
		GetMap(level,diy);
		//������Ϸ���
		panel = setpanel();
		
		this.add(panel);
		this.setVisible(true);
		
		//��װ���̼�����
		PanelListenner plis = new PanelListenner();
		this.addKeyListener(plis);
		
		//����ˢ������߳�
		UpdateThread ut = new UpdateThread(panel);
		ut.start();
		
	}
	
	
	
	
	
	
	//��ȡ��ͼ����
	void GetMap(int level,boolean diy) {
		String stringdiy = "";
		if(diy) {
			stringdiy = "diy";
		}
		try {
			DataInputStream in = new DataInputStream(
					new BufferedInputStream(new FileInputStream(PATH + "\\" + stringdiy + level +".map")));
			int i = in.readInt();
			int j = in.readInt();
			for (int ii=0;ii<i;ii++) {
				for(int jj=0;jj<j;jj++) {
					map1[ii][jj][0]=in.readInt();
					if(map1[ii][jj][0] == 5) {
						playerx = ii;
						playery = jj;
						map1[ii][jj][0] = 1;
					}
					
				}
			}
		}catch(Exception e) {
			
		}
	}
	
	
	
	
	
	
	//�Զ����ڲ���Ϸ�����
	class MyPanel extends JPanel{
		@Override
		public void paint(Graphics g) {
			super.paint(g);
			for(int i=0;i<MAP_HEIGHT/SOUREC_HEIGHT;i++) {
				for(int j=0;j<MAP_WIDTH/SOUREC_WIDTH;j++) {
					g.drawImage(GetGameImage(map1[i][j][0]),getDrawX(j),getDrawY(i),SOUREC_WIDTH,SOUREC_HEIGHT,
							null);
				}
			}
			g.drawImage(icon106.getImage(), getDrawX(playery),getDrawY(playerx),SOUREC_WIDTH,SOUREC_HEIGHT,null);
		}
		
		//�������±�ת���ɶ�ӦͼƬ���Ͻ�ͼ��
		public int getDrawX(int j) {
			int x = j*50;
			return x;
		}
		
		//�������±�ת���ɶ�ӦͼƬ���Ͻ�ͼ��
		public int getDrawY(int i) {
			int y = i*50;
			return y;
		}
	}
	
	
	
	//������Ϸ���
	public JPanel setpanel() {
		JPanel panel = new MyPanel();
		panel.setPreferredSize(new Dimension(MAP_WIDTH,MAP_HEIGHT));
		panel.setLayout(null);
		panel.setBackground(Color.black);
		return panel;
	}
	
	
	
	
	
	
	//��ȡ���ֶ�Ӧ��ͼƬ
	Image GetGameImage(int i) {
		if(i>5) {
			i=0;
		}
		return allicons[i].getImage();
	}
	
	
	
	//�ڲ���Ϸ����������
	class PanelListenner extends KeyAdapter {
		// ����������
		public void keyPressed(KeyEvent e) {
			int code = e.getKeyCode();
			switch (code) {
			case KeyEvent.VK_UP:
				if (check(1)) {
					Move(1);
				}						
				break;
			case KeyEvent.VK_DOWN:
				if (check(3)) {
					Move(3);
				}
				break;
			case KeyEvent.VK_LEFT:
				if (check(2)) {
					Move(2);
				}
				break;
			case KeyEvent.VK_RIGHT:
				if (check(4)) {
					Move(4);
				} 
				break;
			case 87:
				if (check(1)) {
					Move(1);
				}	
				break;
			case 65:
				if (check(3)) {
					Move(3);
				}
				break;
			case 83:
				if (check(2)) {
					Move(2);
				}
				break;
			case 68:
				if (check(4)) {
					Move(4);
				} 
				break;
			default:
				break;
 
			}
			if (IsSuccess()) {
				GetNextMap();
			}
		}
 
	}


	
	
	
	
	//����ܲ����ߣ�ǰ����ǽ�����������ӳ����
	boolean check(int direction) {
		switch (direction) {
		case 1:
			// �ж�1.���� 2.ǰ����ǽ 3.ǰ�������ӻ��߿����� ��ǰ�������ӻ���ǽ���߿�����
			if (playerx == 0) {
				return false;
			} else if (map1[playerx - 1][playery][0] == 0) {
				return false;
			} else if (map1[playerx - 1][playery][0] == 2 || map1[playerx - 1][playery][0] == 3) {
				if (map1[playerx - 2][playery][0] == 2 || map1[playerx - 2][playery][0] == 3
						|| map1[playerx - 2][playery][0] == 0) {
					return false;
				}
			}
			break;
		case 2:
			if (playery == 0) {
				return false;
			} else if (map1[playerx][playery - 1][0] == 0) {
				return false;
			} else if (map1[playerx][playery - 1][0] == 2 || map1[playerx][playery - 1][0] == 3) {
				if (map1[playerx][playery - 2][0] == 2 || map1[playerx][playery - 2][0] == 3
						|| map1[playerx][playery - 2][0] == 0) {
					return false;
				}
			}
			break;
		case 3:
			if (playerx == 17) {
				return false;
			} else if (map1[playerx + 1][playery][0] == 0) {
				return false;
			} else if (map1[playerx + 1][playery][0] == 2 || map1[playerx + 1][playery][0] == 3) {
				if (map1[playerx + 2][playery][0] == 2 || map1[playerx + 2][playery][0] == 3
						|| map1[playerx + 2][playery][0] == 0) {
					return false;
				}
			}
			break;
		case 4:
			if (playery == 17) {
				return false;
			} else if (map1[playerx][playery + 1][0] == 0) {
				return false;
			} else if (map1[playerx][playery + 1][0] == 2 || map1[playerx][playery + 1][0] == 3) {
				if (map1[playerx][playery + 2][0] == 2 || map1[playerx][playery + 2][0] == 3
						|| map1[playerx][playery + 2][0] == 0) {
					return false;
				}
			}
			break;
		default:
			break;
		}
		return true;
	}

	
	
	
	
	//��ɫ�ƶ�
	private void Move(int direction) {
		
		switch (direction) {
		// 0ǽ 1�ذ� 2������ 3 ���� 4���ӵ� 5������
		case 1:
			if (map1[playerx - 1][playery][0] == 2) {
				if (map1[playerx - 2][playery][0] == 4) {
					map1[playerx - 1][playery][0] = 1;
					map1[playerx - 2][playery][0] = 3;
				} else {
					map1[playerx - 1][playery][0] = 1;
					map1[playerx - 2][playery][0] = 2;
				}
			} else if (map1[playerx - 1][playery][0] == 3) {
				map1[playerx - 1][playery][0] = 4;
				map1[playerx - 2][playery][0] = 2;
			}
			playerx -= 1;
			break;
		case 2:
			if (map1[playerx][playery - 1][0] == 2) {
				if (map1[playerx][playery - 2][0] == 4) {
					map1[playerx][playery - 1][0] = 1;
					map1[playerx][playery - 2][0] = 3;
				} else {
					map1[playerx][playery - 1][0] = 1;
					map1[playerx][playery - 2][0] = 2;
				}
			} else if (map1[playerx][playery - 1][0] == 3) {
				map1[playerx][playery - 1][0] = 4;
				map1[playerx][playery - 2][0] = 2;
			}
			playery -= 1;
 
			break;
		case 3:
			if (map1[playerx + 1][playery][0] == 2) {
				if (map1[playerx + 2][playery][0] == 4) {
					map1[playerx + 1][playery][0] = 1;
					map1[playerx + 2][playery][0] = 3;
				} else {
					map1[playerx + 1][playery][0] = 1;
					map1[playerx + 2][playery][0] = 2;
				}
			} else if (map1[playerx + 1][playery][0] == 3) {
				map1[playerx + 1][playery][0] = 4;
				map1[playerx + 2][playery][0] = 2;
			}
			playerx += 1;
			break;
		case 4:
			if (map1[playerx][playery + 1][0] == 2) {
				if (map1[playerx][playery + 2][0] == 4) {
					map1[playerx][playery + 1][0] = 1;
					map1[playerx][playery + 2][0] = 3;
				} else {
					map1[playerx][playery + 1][0] = 1;
					map1[playerx][playery + 2][0] = 2;
				}
			} else if (map1[playerx][playery + 1][0] == 3) {
				map1[playerx][playery + 1][0] = 4;
				map1[playerx][playery + 2][0] = 2;
			}
			playery += 1;
			break;
		default:
			break;
		}
	}
	
	
	
	
	//����Ƿ�ɹ�������ͼ��û�п�����
	boolean IsSuccess() {
		for (int i = 0; i < MAP_WIDTH/SOUREC_WIDTH; i++) {
			for (int j = 0; j < MAP_HEIGHT/SOUREC_HEIGHT; j++) {
				if (map1[i][j][0]==2) {
					return false;
				}
			}
		}
		return true;
	}
	
	
	
	//�������أ�������һ��
	void GetNextMap() {
		if (diy) {
			if (Utils.IsExistence(PATH + "\\diy" + (level+1) + ".map")) {
				JOptionPane.showMessageDialog(null, "��һ��", "ȷ��", JOptionPane.PLAIN_MESSAGE);
				level++;
				GetMap(level, diy);
			}else {
				Success();
			}		
		} else {
			
			if (Utils.IsExistence(PATH + "\\" + (level+1) + ".map")) {
				JOptionPane.showMessageDialog(null, "��һ��", "ȷ��", JOptionPane.PLAIN_MESSAGE);
				level++;
				GetMap(level, diy);
			}else {
				Success();
			}
		}
	}


	
	//ȫ�����سɹ�����������ʾ������һ�ػ���������
	void Success() {
		JOptionPane.showMessageDialog(null, "��ϲ�㣬ȫ�����سɹ�", "��ϲ", JOptionPane.PLAIN_MESSAGE);
		level=1;
		GetMap(level, diy);
	}
	
}