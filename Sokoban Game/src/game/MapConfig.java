package game;

import javax.swing.ImageIcon;

public interface MapConfig {
	//�زĿ��
	int SOUREC_WIDTH = 50;
	//�زĸ߶�
	int SOUREC_HEIGHT = 50;
	//��ͼ���
	int MAP_WIDTH = 800;
	//��ͼ�߶�
	int MAP_HEIGHT = 800;
	//��ͼ����λ��
	String PATH = "D://������";
	
	ImageIcon icon101 = new ImageIcon("image/wall.png");//ǽ
	ImageIcon icon102 = new ImageIcon("image/floor.png");//�ذ�
	ImageIcon icon103 = new ImageIcon("image/emptybox.png");//û���������
	ImageIcon icon104 = new ImageIcon("image/box.png");//�Ƶ��������
	ImageIcon icon105 = new ImageIcon("image/target.png");//Ŀ��
	ImageIcon icon106 = new ImageIcon("image/player.png");//���
	
	//������ͼƬ�ز��زķ���һ�������У����ڴ����ϵ������б���ӵ����е�ͼƬ�ز�
	ImageIcon[] allicons = {icon101,icon102,icon103,icon104,icon105,icon106};
	
	

}
