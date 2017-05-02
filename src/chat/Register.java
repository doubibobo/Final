package chat;

import Tools.find;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by zhuch on 2017/4/30.
 */
public class Register extends JFrame{
        private static final long serialVersionUID = 1L;
        //基本布局工具
        private int screenHeight = (int)this.getToolkit().getScreenSize().getHeight();	//屏幕的高度
        private int screenWidth = (int)this.getToolkit().getScreenSize().getWidth();	//屏幕的宽度
        private int width = 400;	//定义本窗口的宽度
        private int height = 350;	//定义本窗口的高度

        private ImageIcon LoginLogo = null;		//设置图标
        private JLabel showLogoLabel = null;	//设置图标标签
        private JLabel userLabel = null;		//设置用户名输入框标签
        private JLabel sexLabel = null;         //设置性别标签
        private ButtonGroup sexGroup = null;    //设置性别组
        private JRadioButton boyRadio = null, girlRadio = null, secretRadio = null; //设置性别选择框
        private JLabel psdLabel = null;			//设置密码输入框标签
        private JLabel repsdLabel = null;                // 设置重复密码输入标签
        private JTextField usernameText =null;	//设置普通文本输入框
        private JPasswordField pwdText = null;	//设置密码文本输入框
        private JPasswordField repsdText = null;    //设置重复密码文本输入框
        private JButton regBut = null;			//设置注册按钮
        private JButton reSetBut = null;          // 设置重置按钮

        private String sex = null;				//标记用户男女信息

        private String username = null;
        private String pass = null;
        private String repass = null;

        public Register(int _width, int _height) {
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
            userLabel = new JLabel("用 户 名");
            sexLabel = new JLabel("性　　别");
            psdLabel = new JLabel("密　　码");
            repsdLabel = new JLabel("再次输入");

            sexGroup = new ButtonGroup();
            boyRadio = new JRadioButton("男生");
            boyRadio.setActionCommand("boy");
            girlRadio = new JRadioButton("女生");
            girlRadio.setActionCommand("girl");
            secretRadio = new JRadioButton("保密");
            secretRadio.setActionCommand("unknown");

            sexGroup.add(boyRadio);
            sexGroup.add(girlRadio);
            sexGroup.add(secretRadio);

            usernameText = new JTextField();
            pwdText = new JPasswordField();
            repsdText = new JPasswordField();
            reSetBut = new JButton("重置");
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

            this.add(sexLabel);
            sexLabel.setBounds(25, (int)(0.4*height+1), 100, 25);
            this.add(boyRadio);
            boyRadio.setBounds(80, (int)(0.4*height+1), 60, 25 );
            this.add(girlRadio);
            girlRadio.setBounds(140, (int)(0.4*height+1), 60, 25 );
            this.add(secretRadio);
            secretRadio.setBounds(200, (int)(0.4*height+1), 60, 25 );
            // 默认选中值
            secretRadio.setSelected(true);

            this.add(psdLabel);
            psdLabel.setBounds(25, (int)(0.5*height+1), 100, 25);
            this.add(pwdText);
            pwdText.setBounds(80, (int)(0.5*height+1), 200, 25);

            this.add(repsdLabel);
            repsdLabel.setBounds(25, (int)(0.6*height+1), 100, 25);
            this.add(repsdText);
            repsdText.setBounds(80, (int)(0.6*height+1), 200, 25);

            this.add(regBut);
            regBut.setBounds((width-400)/2+25, (int)(0.7*height+1), 100, 25);

            this.add(reSetBut);
            reSetBut.setBounds((width-400)/2+150, (int)(0.7*height+1), 100, 25);
        }

        /*
         * 添加监听的事件，给主键添加功能
         */
        private void addListener() {
            //关闭窗口
            this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            //给登陆按钮添加事件
            regBut.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    if (usernameText.getText().equals("")) {
                        JOptionPane.showMessageDialog(null, "用户名不能为空！");
                    } else if (pwdText.getText().equals("")){
                        JOptionPane.showMessageDialog(null, "密码不能为空！");
                    } else if (!pwdText.getText().equals(repsdText.getText())) {
                        JOptionPane.showMessageDialog(null, "两次输入密码不一致！");
                    } else {
                        boolean isCanDo = find.checkItems(usernameText.getText(),sexGroup.getSelection().getActionCommand() ,pwdText.getText());
                        if (isCanDo) {
                            boolean after = find.addUser(usernameText.getText(),sexGroup.getSelection().getActionCommand() ,pwdText.getText());
                            if (after) {
                                // 注册成功：跳转到登录界面
                                Register.this.dispose();
                                new ClientLogin(400, 350);
                            } else {
                                // 注册失败
                                JOptionPane.showMessageDialog(null, "注册失败！");
                            }
                        } else {
                            JOptionPane.showMessageDialog(null, "该用户名已经注册！");
                        }
                    }
                }
            });
            reSetBut.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    usernameText.setText("");
                    pwdText.setText("");
                    repsdText.setText("");
                    secretRadio.setSelected(true);
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
}
