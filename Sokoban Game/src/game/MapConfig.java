package game;

import javax.swing.ImageIcon;

public interface MapConfig {
	//素材宽度
	int SOUREC_WIDTH = 50;
	//素材高度
	int SOUREC_HEIGHT = 50;
	//地图宽度
	int MAP_WIDTH = 800;
	//地图高度
	int MAP_HEIGHT = 800;
	//地图保存位置
	String PATH = "D://推箱子";
	
	ImageIcon icon101 = new ImageIcon("image/wall.png");//墙
	ImageIcon icon102 = new ImageIcon("image/floor.png");//地板
	ImageIcon icon103 = new ImageIcon("image/emptybox.png");//没到点的箱子
	ImageIcon icon104 = new ImageIcon("image/box.png");//推到点的箱子
	ImageIcon icon105 = new ImageIcon("image/target.png");//目标
	ImageIcon icon106 = new ImageIcon("image/player.png");//玩家
	
	//将所有图片素材素材放入一个数组中，便于窗体上的下拉列表添加到所有的图片素材
	ImageIcon[] allicons = {icon101,icon102,icon103,icon104,icon105,icon106};
	
	

}
