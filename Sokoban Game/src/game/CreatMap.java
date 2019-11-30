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
	
	//�ؿ�����
	int level = 1;
	//����ѡ���زĵ�������
	private JComboBox<ImageIcon> box;
	//��ͼ����
	static int[][][] map1 = new int[MAP_WIDTH / SOUREC_WIDTH][MAP_HEIGHT/SOUREC_HEIGHT][1];
	//ͼƬ���飬������ʾ��ͼ
	static ImageIcon[][] icons = new ImageIcon[MAP_WIDTH/SOUREC_WIDTH][MAP_HEIGHT/SOUREC_HEIGHT];
	//��������
	public CreatMap() {
		//���ñ���
		setTitle("�༭ģʽ");
		//���ùرշ�ʽ
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		//��ʩ��Сλ��
		setBounds(100,100,1180,735);
		//���
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5,5,5,5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		//�������
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(14,13,800,635);
		contentPane.add(scrollPane);
		//��ͼ���
		panel = new JPanel();
		panel.setPreferredSize(new Dimension(800,800));
		scrollPane.setViewportView(panel);
		scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		//��ǩ
		JLabel lb_1 = new JLabel("�ؿ�");
		lb_1.setBounds(874,160,111,36);
		contentPane.add(lb_1);
		//�༭��
		tf_level = new JTextField();
		//����Ĭ�Ϲؿ�
		tf_level.setText(String.valueOf(level));
		tf_level.setColumns(10);
		tf_level.setBounds(1003,158,117,40);
		contentPane.add(tf_level);
		//�����˵�
		box = new JComboBox<ImageIcon>();
		setBox(box);
		box.setBounds(939,312,123,99);
		contentPane.add(box);
		//�����ͼ��ť
		JButton btn_save = new JButton("�����ͼ");
		btn_save.setBounds(945,578,117,40);
		contentPane.add(btn_save);
		//����尲װ��������
		PanelListener plis = new PanelListener();
		panel.addMouseListener(plis);
		//�������
		setLocationRelativeTo(null);
		//��ʾ����
		setVisible(true);
	}
	//���õ�ͼ���ز�������
	public void setBox(JComboBox<ImageIcon> box) {
		for (int i=0;i<allicons.length;i++) {
			box.addItem(allicons[i]);
		}
	}
	//��������
	class PanelListener extends MouseAdapter{
		public void mouseClicked(MouseEvent e) {
			int num=0;
			//�õ���λ�ö�Ӧ�������±�
			int j = e.getX()/SOUREC_WIDTH;
			int i = e.getY()/SOUREC_HEIGHT;
			System.out.println(i+"<>"+j);
			//�õ�ѡ����е�ͼƬ
			ImageIcon icon = (ImageIcon) box.getSelectedItem();
			//0ǽ 1�ذ� 2û�Ƶ������� 3�Ƶ������� 4Ŀ��� 5������
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
	//��ʱ��ͼ�����
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
		//�������±�ת���ɶ�ӦͼƬ���Ͻ�����
		public int getDrawX(int j) {
			int x = j * 50;
			return x;
		}
		public int getDrawY(int i) {
			int y = i * 50;
			return y;
		}
	}
	//����������
	class Buttonlistener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			//��������˴�����ť���ͱ����ͼ
			if(e.getActionCommand().equals("�����ͼ")) {
				level = Integer.parseInt(tf_level.getText());
				if(Utils.IsExistence(PATH+"\\diy"+level+".map")) {
					int n = JOptionPane.showConfirmDialog(null, "��ͼ�Ѵ��ڣ��Ƿ񸲸�?","����",JOptionPane.YES_NO_OPTION);//0ȷ��1ȡ��
				if (n==0) {
					//ȷ��������
					CreatMapTxt();
				}
				}else {
					//�������ļ��򴴽��ļ�
					CreatMapTxt();
				}
			}
		}
	}
	//������ͼ�ļ�
	void CreatMapTxt() {
		try {
			//�õ��ļ������
			FileOutputStream fos = new FileOutputStream(PATH+"\\diy"+level+".map");
			//���ļ��������װ�ɻ������������
			DataOutputStream dos = new DataOutputStream(fos);
			//�����õĽӿ��еõ���ά����Ĵ�С�����ڱ����Ѿ�ʵ�������Mapconfig�ӿڣ���˿���ֱ�������������
			int i = MAP_HEIGHT/SOUREC_HEIGHT;
			int j = MAP_WIDTH/SOUREC_WIDTH;
			//������Ĵ�Сд���ļ�
			dos.writeInt(i);
			dos.writeInt(j);
			//��˳����ά����д���ļ���������Ϸ��ȡ��ͼ��ʱ��ҲҪ������˳�������
			for(int ii =0;ii<i;ii++) {
				for(int jj = 0;jj<j;jj++) {
					dos.writeInt(map1[ii][jj][0]);
				}
			}
			//ǿ�����е�������ȫ�����
			dos.flush();
			//�ر������
			dos.close();
		}catch(Exception ef) {
			ef.printStackTrace();
		}
		System.out.println("����ɹ�");
	}

}