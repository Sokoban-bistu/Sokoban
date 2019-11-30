package game;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import java.io.DataOutputStream;
import java.io.FileOutputStream;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

public class CreatMap extends JFrame implements MapConfig{
	
	private JPanel contentPane;
	private JTextField tf_level;
	private JPanel panel;
	
	//关卡变量
	int level = 1;
	//用来选择素材的下拉表
	private JComboBox<ImageIcon> box;
	//地图数组
	static int[][][] map1 = new int[MAP_WIDTH / SOUREC_WIDTH][MAP_HEIGHT/SOUREC_HEIGHT][1];
	//图片数组，用于显示地图
	static ImageIcon[][] icons = new ImageIcon[MAP_WIDTH/SOUREC_WIDTH][MAP_HEIGHT/SOUREC_HEIGHT];
	//创建界面
	public CreatMap() {
		//设置标题
		setTitle("编辑模式");
		//设置关闭方式
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		//设施大小位置
		setBounds(100,100,1180,735);
		//面板
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5,5,5,5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		//滚动面板
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(14,13,800,635);
		contentPane.add(scrollPane);
		//地图面板
		panel = new JPanel();
		panel.setPreferredSize(new Dimension(800,800));
		scrollPane.setViewportView(panel);
		scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		//标签
		JLabel lb_1 = new JLabel("关卡");
		lb_1.setBounds(874,160,111,36);
		contentPane.add(lb_1);
		//编辑框
		tf_level = new JTextField();
		//设置默认关卡
		tf_level.setText(String.valueOf(level));
		tf_level.setColumns(10);
		tf_level.setBounds(1003,158,117,40);
		contentPane.add(tf_level);
		//下拉菜单
		box = new JComboBox<ImageIcon>();
		setBox(box);
		box.setBounds(939,312,123,99);
		contentPane.add(box);
		//保存地图按钮
		JButton btn_save = new JButton("保存地图");
		btn_save.setBounds(945,578,117,40);
		contentPane.add(btn_save);
		//给面板安装鼠标监听器
		PanelListener plis = new PanelListener();
		panel.addMouseListener(plis);
		//界面居中
		setLocationRelativeTo(null);
		//显示界面
		setVisible(true);
	}
	//设置地图中素材下拉表
	public void setBox(JComboBox<ImageIcon> box) {
		for (int i=0;i<allicons.length;i++) {
			box.addItem(allicons[i]);
		}
	}
	//面板监听类
	class PanelListener extends MouseAdapter{
		public void mouseClicked(MouseEvent e) {
			int num=0;
			//得到该位置对应的数组下标
			int j = e.getX()/SOUREC_WIDTH;
			int i = e.getY()/SOUREC_HEIGHT;
			System.out.println(i+"<>"+j);
			//得到选择框中的图片
			ImageIcon icon = (ImageIcon) box.getSelectedItem();
			//0墙 1地板 2没推到的箱子 3推到的箱子 4目标点 5出生点
			int index = box.getSelectedIndex();
			if(index > 5) {
				index = 0;
				icons[i][j]=icon101;
			}else {
				map1[i][j][0] = index;
				icons[i][j] = icon;
			}
			panel.repaint();
		}
	}
	//临时地图面板类
	class MySetPanel extends JPanel{
		@Override
		public void paint(Graphics g) {
			super.paint(g);
			for(int i=0;i<MAP_HEIGHT/SOUREC_HEIGHT;i++) {
				for(int j=0;j<MAP_WIDTH/SOUREC_WIDTH;j++) {
					if(icons[i][j] !=null) {
						g.drawImage(icons[i][j].getImage(), getDrawX(j), getDrawY(i),SOUREC_WIDTH,SOUREC_HEIGHT,null);
					}
				}
			}
		}
		//将数组下标转化成对应图片左上角坐标
		public int getDrawX(int j) {
			int x = j * 50;
			return x;
		}
		public int getDrawY(int i) {
			int y = i * 50;
			return y;
		}
	}
	//按键监听类
	class Buttonlistener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			//如果按下了创建按钮，就保存地图
			if(e.getActionCommand().equals("保存地图")) {
				level = Integer.parseInt(tf_level.getText());
				if(Utils.IsExistence(PATH+"\\diy"+level+".map")) {
					int n = JOptionPane.showConfirmDialog(null, "地图已存在，是否覆盖?","警告",JOptionPane.YES_NO_OPTION);//0确定1取消
				if (n==0) {
					//确定即保存
					CreatMapTxt();
				}
				}else {
					//不存在文件则创建文件
					CreatMapTxt();
				}
			}
		}
	}
	//创建地图文件
	void CreatMapTxt() {
		try {
			//得到文件输出流
			FileOutputStream fos = new FileOutputStream(PATH+"\\diy"+level+".map");
			//将文件输出流包装成基本数据输出流
			DataOutputStream dos = new DataOutputStream(fos);
			//从配置的接口中得到二维数组的大小（由于本类已经实现上面的Mapconfig接口，因此可以直接用里面的数据
			int i = MAP_HEIGHT/SOUREC_HEIGHT;
			int j = MAP_WIDTH/SOUREC_WIDTH;
			//先数组的大小写入文件
			dos.writeInt(i);
			dos.writeInt(j);
			//按顺序将三维数组写入文件，后面游戏读取地图的时候也要按这种顺序读出来
			for(int ii =0;ii<i;ii++) {
				for(int jj = 0;jj<j;jj++) {
					dos.writeInt(map1[ii][jj][0]);
				}
			}
			//强制流中的数据完全输出完
			dos.flush();
			//关闭输出流
			dos.close();
		}catch(Exception ef) {
			ef.printStackTrace();
		}
		System.out.println("保存成功");
	}

}