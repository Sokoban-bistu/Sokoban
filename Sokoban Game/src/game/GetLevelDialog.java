package game;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.border.EmptyBorder;

public class GetLevelDialog extends JDialog {
	
	private DefaultListModel<String> model;
	int levelmax, diylevelmax;
	JList list;
	boolean xuanzhong = true;
	int level =0;
 
	private final JPanel contentPanel = new JPanel();
 
	
	public GetLevelDialog() {
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setModal(true);// 动态模糊
		setTitle("选关");
		setBounds(100, 100, 512, 340);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
 
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(328, 52, 103, 173);
		contentPanel.add(scrollPane);
 
		model = new DefaultListModel<String>();
		diylevelmax = 1;
		levelmax = 1;
		while (true) {
			if (IsExistence("D:\\推箱子\\diy" + diylevelmax + ".map")) {
				diylevelmax++;
			} else if (IsExistence("D:\\推箱子\\" + levelmax + ".map")) {
				levelmax++;
			} else {
				// System.out.println(i+"----"+j);
				break;
			}
		}
 
		list = new JList(model);
		UpdataModel(diylevelmax);
		scrollPane.setViewportView(list);
		
		JCheckBox checkBox = new JCheckBox("自定义");
		checkBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (xuanzhong) {
					UpdataModel(levelmax);
					xuanzhong=false;
				} else {
					UpdataModel(diylevelmax);
					xuanzhong=true;
				}
			}
		});
		checkBox.setSelected(true);
		checkBox.setBounds(328, 23, 103, 23);
		contentPanel.add(checkBox);
 
		JTextPane txtpndtxtdiytxt = new JTextPane();
		txtpndtxtdiytxt.setText(
				"选关界面，你就可以选择开发者做出来的地图，也可以自己在游戏界面——自定义——地图编辑器里自己做地图。地图文件夹在D盘推箱子中，1.txt代表本人做的地图，diy1.txt代表自定义，可相互替换");
		txtpndtxtdiytxt.setFont(new Font("楷体", Font.PLAIN, 15));
		txtpndtxtdiytxt.setBounds(45, 52, 224, 173);
		contentPanel.add(txtpndtxtdiytxt);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("确定");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						level = list.getSelectedIndex()+1;
						dispose();
					}
				});
				okButton.setActionCommand("Cancel");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("取消");
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						dispose();
					}
				});
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
		setLocationRelativeTo(null);
		setVisible(true);
	}
	
	void UpdataModel(int level){
		model.removeAllElements();
		for (int i = 1; i < level; i++) {
			model.addElement("第"+ i +"关");
		}
	}
 
	
	//判断是否存在文件file
	// @param file 如：D:\\推箱子\\1.txt
	// @return 存在即为true
	 
	boolean IsExistence(String file) {
		File fileuser = new File(file);
		if (!fileuser.exists()) {
			return false;
		}
		return true;
	}
 
	//main得到信息 @return 0 为取消  1-n为选关  
	 
	int getValue() {
		return level;
	}
	
	
	 //返回是否diy地图
	 //@return
	boolean isdiy() {
		return xuanzhong;
	}


}
