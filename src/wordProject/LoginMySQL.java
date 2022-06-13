package wordProject;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Dimension;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;

class Login implements ActionListener{
	
	@Override
	public void actionPerformed(ActionEvent e){
		if(SQL.login(LoginMySQL.getLg().pass.getText())) {
			LoginMySQL.getLg().pass.setBorder(new LineBorder(Color.black));
			LoginMySQL.getLg().close();
			if(Interphase.getInter() != null) {
				Interphase.getInter().open();
			}else {
				Interphase in = new Interphase("窗口");
	        	in.setInter(in);
				Interphase.getInter().open();
			}
			
		}else {
			LoginMySQL.getLg().pass.setBorder(new LineBorder(Color.red));
		}
	}
}

public class LoginMySQL extends JFrame{     //需要继承JFrame
	private static LoginMySQL lg;
	private JFrame jf;
	private JPanel pa;//主要面板，放置所有组件
	private SQL sql;
	public static JTextField pass;
	
	public static LoginMySQL getLg(){
		return lg;
	}
	public static void setLg(LoginMySQL lg){
		LoginMySQL.lg = lg;
	}

	LoginMySQL(String title){
		jf = new JFrame(title);
		pa = new JPanel(new GridLayout(3,1));
		pa.setBounds(50,50,300,150);
		pa.setBackground(Color.green);
		sql = new SQL();
		pass = new JTextField();
		
		jf.setBounds(400,300,400,350); //设置窗口的属性 窗口位置以及窗口的大小
        jf.setLayout(null);
        jf.setDefaultCloseOperation(EXIT_ON_CLOSE); //设置关闭方式,关闭并退出
//        jf.setResizable(false);
        init();
        jf.add(pa);
        jf.setVisible(true);//设置窗口可见
	}
	public void close() {
		jf.setVisible(false);
	}
	public void init() {
		JPanel p1 = new JPanel();
		JLabel l1 = new JLabel("默认用户");
		l1.setFont(new Font("微软雅黑",Font.PLAIN,18));
		
		JTextField t1 = new JTextField("root");
		t1.setFont(new Font("微软雅黑",Font.PLAIN,18));
		t1.setEditable(false);
		t1.setPreferredSize(new Dimension (200,30));
		t1.setHorizontalAlignment(JTextField.CENTER);
		p1.add(l1);
		p1.add(t1);
		pa.add(p1);
		
		JPanel p2 = new JPanel();
		JLabel l2 = new JLabel("密        码");
		l2.setFont(new Font("微软雅黑",Font.PLAIN,18));
		
		pass.setPreferredSize(new Dimension (200,30));
		pass.setFont(new Font("微软雅黑",Font.PLAIN,18));
		
		p2.add(l2);
		p2.add(pass);
		pa.add(p2);
		
		JPanel p3 = new JPanel();
		JButton JB = new JButton("submit");
    	JB.setFont(new Font("微软雅黑",Font.PLAIN,15));
    	
	    //设置鼠标在按钮上的形状（小手状）
		JB.setCursor(new Cursor(Cursor.HAND_CURSOR));
		//设置按钮背景颜色
		JB.setBackground(Color.CYAN);
		
		JB.addActionListener(new Login());
		p3.add(JB);
		pa.add(p3);
	}
}

