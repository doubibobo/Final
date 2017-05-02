package chat;

import Tools.find;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientLogin extends JFrame{
	private static final long serialVersionUID = 1L;
	//基本布局工具
	private int screenHeight = (int)this.getToolkit().getScreenSize().getHeight();	//屏幕的高度
	private int screenWidth = (int)this.getToolkit().getScreenSize().getWidth();	//屏幕的宽度
	private int width = 400;	//定义本窗口的宽度
	private int height = 350;	//定义本窗口的高度
	
	private ImageIcon LoginLogo = null;		//设置图标
	private JLabel showLogoLabel = null;	//设置图标标签
	private JLabel userLabel = null;		//设置用户名输入框标签
	private JLabel psdLabel = null;			//设置密码输入框标签
	private JTextField usernameText =null;	//设置普通文本输入框
	private JPasswordField pwdText = null;	//设置密码文本输入框
	private JButton loginBut = null;		//设置登陆按钮
	private JButton regBut = null;			//设置注册按钮

	private String hostName = "localhost";
	private int port = 6000;
	private Socket client = null;
	private BufferedReader in = null;
	private PrintWriter out = null;

	private String sex = null;				//标记用户男女信息

	public ClientLogin(int _width, int _height) {
		width = _width;
		height = _height;
		init();
		addComponent();
		addListener();
		showFrame();
	}
	
	/*
	 * init方法初始化组件
	 */
	private void init() {
//		JFrame默认布局是BoardLayout（边框布局），如果容器只放一个组件并且不指定位置，默认放入容器的中间占满窗口
//		初始化图片
		LoginLogo = new ImageIcon("Imagics/login.jpg");
//		初始化JLabel 并且把图片放到Label中
		showLogoLabel = new JLabel(LoginLogo, JLabel.CENTER);
		userLabel = new JLabel("用户名");
		psdLabel = new JLabel("密　码");
		usernameText = new JTextField();
		pwdText = new JPasswordField();
		loginBut = new JButton("登陆");
		regBut = new JButton("注册");
	}
	
	/*
	 * 把各种组件在此方法中进行组装
	 */
	private void addComponent() {
		this.setLayout(null);
		this.add(showLogoLabel);		//添加图标组件
		showLogoLabel.setBounds(0, 0, width, (int)(0.2*height+1));
//		setBounds方法的功能：前两个组件分别为在容器中的坐标位置
//		setBounds方法的功能：组件的宽度和高度
		
		this.add(userLabel);
		userLabel.setBounds(25, (int)(0.3*height+1), 100, 25);
		this.add(usernameText);
		usernameText.setBounds(80, (int)(0.3*height+1), 200, 25);
		
		this.add(psdLabel);
		psdLabel.setBounds(25, (int)(0.4*height+1), 100, 25);
		this.add(pwdText);
		pwdText.setBounds(80, (int)(0.4*height+1), 200, 25);
		
		this.add(loginBut);
		loginBut.setBounds((width-400)/2+25, (int)(0.6*height+1), 100, 25);
		
		this.add(regBut);
		regBut.setBounds((width-400)/2+150, (int)(0.6*height+1), 100, 25);
	}
	
	/*
	 * 添加监听的事件，给主键添加功能
	 */
	private void addListener() {
		//关闭窗口
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//给登陆按钮添加事件
		loginBut.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				boolean b = find.checkLogin(usernameText.getText(), pwdText.getText());
				//可以增加对用户名和密码的判断
				if (b == true) {
					sex = find.getSex(usernameText.getText());
					this.link();
					ChatFrame app = new ChatFrame(usernameText.getText());
					app.init(in, out);
					//进行窗口的关闭
					loginBut.setEnabled(false); // 确保不会被再次点击
					ClientLogin.this.dispose();
				} else {
					//弹出消息对话框
					JOptionPane.showMessageDialog(null, "用户名或者密码错误");
				}
			}

			private void link(){
				try {
					client = new Socket(hostName, port);
					in = new BufferedReader(new InputStreamReader(client.getInputStream()));
					out = new PrintWriter(client.getOutputStream());
					out.println(usernameText.getText() + "&" + sex);
					out.flush();
				} catch (IOException e) {
					e.printStackTrace();
				}

			}

		});
		//给注册按钮添加事件
		regBut.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ClientLogin.this.dispose();
				new Register(400, 350);
			}
		});
	}
	
	/*
	 * 让窗口显示出来
	 */
	private void showFrame() {
		//窗口从中间弹出:思路
		//得到屏幕的宽度和高度
		//得到本窗口的高度和宽度
		this.setLocation((screenWidth-width)/2, (screenHeight-height)/2);
		this.setSize(width, height);
		this.setVisible(true);
	}

	public static void main(String args[]) {
		new ClientLogin(400, 350);
	}
}
/*
 * sun公司自定义的组件 	Swing
 * 系统自定义的组件		AWT
 * */