package gif.ui;

import gif.function.Edit;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileFilter;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class MainUI extends JFrame implements ActionListener{

	private static final long serialVersionUID = 1L;
	JTextField path;
	JLabel label_path;
	JButton edit;
	JTextArea area;
	JScrollPane pane;
	
	public MainUI() {
		super("GIF图片压缩器");
		init();
	}

	private void init() {
		
		path = new JTextField(20);
		label_path = new JLabel("图片所在文件夹路径：");
		edit = new JButton("开始压缩");
		area = new JTextArea(5, 25);
		
		pane = new JScrollPane(area);
		
		JPanel north = new JPanel(new FlowLayout());
		north.add(label_path);
		north.add(path);
		north.add(edit);
		
		add(north, BorderLayout.NORTH);
		add(pane, BorderLayout.CENTER);
		
		edit.addActionListener(this);
		
		setSize(500, 400);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	}
	
	public static void main(String[] args) {
		new MainUI();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String p = path.getText();
		File dir = new File(p);
		if(dir.exists()){
			area.setText("开始压缩");
			File[] files = dir.listFiles(new FileFilter() {
				@Override
				public boolean accept(File pathname) {
					return pathname.getName().endsWith(".gif") ? true : false;
				}
			});
			
			Edit edit = new Edit();
			int failCount = 0;
			for(File f : files){
				if(!edit.edit(f)){
					failCount ++;
					area.setText(area.getText() + "\n    GIF图片：" + f.getName() + " 压缩失败！");
				}else{
					area.setText(area.getText() + "\n    GIF图片：" + f.getName() + " 压缩成功，已存入simple目录中！");
				}
				pane.repaint();
			}
			area.setText(area.getText() + "\n 全部操作已完成");
			area.setText(area.getText() + "\n    成功 " + (files.length - failCount) + " 个文件");
			area.setText(area.getText() + "\n    失败 " + failCount + " 个文件");
		}else{
			area.setText("文件路径有误，请重新输入！");
		}
	}
}
