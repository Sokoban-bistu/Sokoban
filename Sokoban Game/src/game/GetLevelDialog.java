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
		setModal(true);// ��̬ģ��
		setTitle("ѡ��");
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
			if (IsExistence("D:\\������\\diy" + diylevelmax + ".map")) {
				diylevelmax++;
			} else if (IsExistence("D:\\������\\" + levelmax + ".map")) {
				levelmax++;
			} else {
				// System.out.println(i+"----"+j);
				break;
			}
		}
 
		list = new JList(model);
		UpdataModel(diylevelmax);
		scrollPane.setViewportView(list);
		
		JCheckBox checkBox = new JCheckBox("�Զ���");
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
				"ѡ�ؽ��棬��Ϳ���ѡ�񿪷����������ĵ�ͼ��Ҳ�����Լ�����Ϸ���桪���Զ��塪����ͼ�༭�����Լ�����ͼ����ͼ�ļ�����D���������У�1.txt���������ĵ�ͼ��diy1.txt�����Զ��壬���໥�滻");
		txtpndtxtdiytxt.setFont(new Font("����", Font.PLAIN, 15));
		txtpndtxtdiytxt.setBounds(45, 52, 224, 173);
		contentPanel.add(txtpndtxtdiytxt);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("ȷ��");
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
				JButton cancelButton = new JButton("ȡ��");
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
			model.addElement("��"+ i +"��");
		}
	}
 
	
	//�ж��Ƿ�����ļ�file
	// @param file �磺D:\\������\\1.txt
	// @return ���ڼ�Ϊtrue
	 
	boolean IsExistence(String file) {
		File fileuser = new File(file);
		if (!fileuser.exists()) {
			return false;
		}
		return true;
	}
 
	//main�õ���Ϣ @return 0 Ϊȡ��  1-nΪѡ��  
	 
	int getValue() {
		return level;
	}
	
	
	 //�����Ƿ�diy��ͼ
	 //@return
	boolean isdiy() {
		return xuanzhong;
	}


}
