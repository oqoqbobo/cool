package wordProject;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

class ButtonControl implements MouseWheelListener{

	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {
		// TODO Auto-generated method stub
		
	}
	
}
class ClickControl implements MouseListener{
	private int day;
	private boolean type;
	ClickControl(int i){
		day = i;
		type = true;
	}

	ClickControl(){
		type = false;
	}
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		if(type) {
			MyTime.click(day);
		}else{
			if(!MyTime.getStr().equals("") && MyTime.getDate().getTime() <= new Date().getTime()){
				MyTime.temp = false;
			}
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
	}
}
class JbClick implements ActionListener{
	JbClick(){
		MyTime.temp = true;
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		MyTime.jButtonSetAuto();
		MyTime.close();
	}
	
}

class WheelControl implements MouseWheelListener{
	private int type;
	WheelControl(int other){
		type = other;
	}
	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {
		int dir = e.getWheelRotation();
	    // down
	    if (dir == 1) {
	    	MyTime.up(type);
	    }
	    // up
	    if (dir == -1) {
	    	MyTime.down(type);
	    }
	}
}
public class MyTime extends JFrame{
	private static JFrame jf;
	//jf为true表示显示，为false表示不显示
	private static JPanel rollP[];
	private static JLabel boxs[][];
	private static int index[];
	private static JLabel show;
	private static JPanel day;
	private static JTextField singleD[];
	private static int curr;
	private JLabel title[];
	private static JButton jButton;
	private static int left = 36;
	private static int top = 4;
	private static Date theDay;
	private static String str;
	public static boolean temp;
	static {
		index = new int[4];
		
		jf = new JFrame("time");
		curr = 0;
		str = Write_read.read("src/passP/theTime.txt");
		String[] other = formatDateToString(new Date());
		index[0] = new Integer(other[0])-2;
		index[1] = (new Integer(other[1])-3)<0 ? new Integer(other[1])+9 : new Integer(other[1])-3;
		curr = new Integer(other[2])-1;
		index[2] = (new Integer(other[3])-2)<0 ? new Integer(other[3])+10 : new Integer(other[3])-2;
		index[3] = (new Integer(other[4])-2)<0 ? new Integer(other[4])+10 : new Integer(other[4])-2;
	}
	private static String[] formatDateToString(Date date){
		//定义字符串格式
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd-HH-mm");
		String dateStr = sdf.format(date);			// 格式化日期，并得到字符串
		String[] other;
	    other = dateStr.split("-"); // 分割字符串
		return other;
	}
	public static String getStr() {
		return str;
	}
	public static Date getDate() {
		Date set=null;
		try {
			set = new SimpleDateFormat("yyyy/MM/dd-HH:mm").parse(str);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return set;
	}
	public static void open() {
		jf.setVisible(true);
	}
	public static void close() {
		jf.setVisible(false);
	}
	public MyTime(){
		jButton = getButton("OK");
		jButton.setBounds(left+80, 650, 80, 30);
		
	    jf.getContentPane().setBackground(new Color(238,238,238));
	    jf.getContentPane().setVisible(true);
		
		jf.setLayout(null);
		jf.setBounds(500,50,350,740);
		Image icon = Toolkit.getDefaultToolkit().getImage("src/passP/timg.jpg");
		jf.setIconImage(icon);
		initRoll();
		initDay();
		jf.add(rollP[0]);
		jf.add(rollP[1]);
		jf.add(rollP[2]);
		jf.add(rollP[3]);
		jf.add(show);
		jf.add(day);
		
		initTitle();
		
		jf.add(jButton);
		jButton.addActionListener(new JbClick());
		jf.addMouseListener(new ClickControl());
		//定时刷新
		Timer ti = new Timer();
		TimerTask tTask = new TimerTask() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				getText(2);
			}
		};
		ti.schedule(tTask, 0, 1000);
		setAuto();
	}
	private JButton getButton(String name) {
		JButton other = new JButton(name);
		other.setFont(new Font("微软雅黑",Font.PLAIN,16));
	    //设置鼠标在按钮上的形状（小手状）
		other.setCursor(new Cursor(Cursor.HAND_CURSOR));
		//设置按钮背景颜色
		other.setBackground(Color.LIGHT_GRAY);
		return other;
	}
	private static void setAuto(){
		if(!str.equals("")) {
			temp = true;
			TimerTask task = new TimerTask(){
				@Override
				public void run(){
					// TODO Auto-generated method stub
					jf.getContentPane().setBackground(Color.orange);
					MyTime.open();
					while(temp){
						FileDown.speak();
					}
					if(!temp) {
						Write_read.write("", "src/passP/theTime.txt");
						jf.getContentPane().setBackground(new Color(238,238,238));
					}
					
				}
			};
			Timer timer = new Timer();
			timer.schedule(task,getDate());
			
		}
	}
	public static void jButtonSetAuto() {
		str = getText(2);
		Write_read.write(str, "src/passP/theTime.txt");
		setAuto();
		System.out.println(str);
	}
	private void initTitle() {
		title = new JLabel[5];
		title[0] = getLabel("年",120,0,0);
		title[1] = getLabel("月",120,140,0);
		title[2] = getLabel("日",250,4,196);
		title[3] = getLabel("时",120,0,420);
		title[4] = getLabel("分",120,140,420);
		for(int i=0;i<5;i++){
			jf.add(title[i]);
		}
	}
	private JLabel getLabel(String name,int len,int x,int y) {
		JLabel temp = new JLabel(name);
		temp.setPreferredSize(new Dimension (len,30));
		temp.setHorizontalAlignment(JLabel.CENTER);
		temp.setBounds(x+left, y+5, len, 30);
		temp.setBorder(BorderFactory.createLineBorder(Color.black));
		temp.setFont(new Font("微软雅黑",Font.PLAIN,18));
		return temp;
	}
	private static int getOther(int a,int i) {
		int len[] = {2018,12,24,60};
		int other = index[a];
		if(a != 0) {
			other = (index[a]+i)%len[a];
			if(a == 1) {
				other+=1;
			}
		}else {
			other += i;
			if(other<1000) {
				other = 1000;
			}else if(other > 9999) {
				other = 9999;
			}
		}
		return other;
	}
	private void initRoll() {
		// TODO Auto-generated method stub
		rollP = new JPanel[4];
		boxs = new JLabel[4][5];
		
		
		for(int a=0;a<4;a++) {
			rollP[a] = new JPanel();
			rollP[a].setLayout(new GridLayout(5,1));
			rollP[a].setBorder(BorderFactory.createLineBorder(Color.black));
			int other;
			for(int i=0;i<5;i++) {
				other = getOther(a,i);
				
				boxs[a][i] = new JLabel(other+"",JLabel.CENTER);
				boxs[a][i].setFont(new Font("微软雅黑",Font.PLAIN,18));
				rollP[a].add(boxs[a][i]);
				
			}
			boxs[a][2].setBorder(BorderFactory.createLineBorder(Color.red));
			rollP[a].addMouseWheelListener(new WheelControl(a));
		}
		rollP[0].setBounds(0+left, 30+top, 120, 150);
		rollP[1].setBounds(140+left, 30+top, 120, 150);
		rollP[2].setBounds(0+left, 450+top, 120,150);
		rollP[3].setBounds(140+left,450+top, 120, 150);
		show = new JLabel();
		show.setBounds(0+left,614,300,30);
		show.setLayout(new GridLayout(1,1));
		show.setFont(new Font("微软雅黑",Font.PLAIN,18));
		show.setText(getText(2));
	}
	public static void up(int i) {
		index[i]+=1;
		int len[] = {2018,12,24,60};
		
		if(index[i]>=(2*len[i]+1)) {
			index[i] = 1;
		}
		for(int x=0;x<5;x++) {
			int other = getOther(i,x);
			boxs[i][x].setText(other+"");
		}
		judge(boxs[0][2].getText(),boxs[1][2].getText());
		show.setText(getText(2));
	}
	public static void down(int i) {
		index[i]-=1;
		int len[] = {2018,12,24,60};
		
		if(index[i]<=0) {
			index[i] = 2*len[i];
		}
		for(int x=0;x<5;x++) {
			int other = getOther(i,x);
			boxs[i][x].setText(other+"");
		}
		judge(boxs[0][2].getText(),boxs[1][2].getText());
		show.setText(getText(2));
	}
	
	private static String getText(int mid) {
		String flag;
		String otherStr = boxs[0][mid].getText()+"/";
		flag = boxs[1][mid].getText().length()<2 ? ("0"+boxs[1][mid].getText()) : boxs[1][mid].getText();
		otherStr += flag + "/";
		flag = ((curr+1)+"").length()<2 ? ("0"+(curr+1)) : ((curr+1)+"");
		otherStr += flag+"-";
		flag = boxs[2][mid].getText().length()<2 ? ("0"+boxs[2][mid].getText()) : boxs[2][mid].getText();
		otherStr += flag;
		flag = boxs[3][mid].getText().length()<2 ? ("0"+boxs[3][mid].getText()) : boxs[3][mid].getText();
		otherStr += ":"+flag;
		
		try {
			Date set = new SimpleDateFormat("yyyy/MM/dd-HH:mm").parse(otherStr);
			long setTime = set.getTime();
			long nowTime = new Date().getTime();
			
			if(setTime<nowTime) {
				jButton.setEnabled(false);
			}else {
				jButton.setEnabled(true);
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return otherStr;
	}
	
	private static void judge(String yearOther,String monthOther) {
		day.removeAll();
		int year = new Integer(yearOther);
		int month = new Integer(monthOther);
		int len = 31;
		if(month == 2) {
			if((year%4 == 0 && year%100 != 0) || year%400 == 0) {
				len = 29;
			}else {
				len = 28;
			}
		}else if(month%2 == 0) {
			len = 30;
		}else if(month%2 == 1) {
			len = 31;
		}
		if(curr>len-1) {
			curr = 0;
		}
		for(int i=0;i<len;i++) {
			singleD[i] = getTextField((i+1)+"");
			day.add(singleD[i]);
			singleD[i].addMouseListener(new ClickControl(i));
		}
		singleD[curr].setBackground(Color.CYAN);
		//验证containerPanel容器及其组件 
		day.validate();
    	//重绘组件
    	day.repaint();
	}
	public static void initDay() {
		day = new JPanel();
		day.setBounds(4+left,226+top,250,184);
		day.setLayout(new FlowLayout(0));
		day.setBorder(BorderFactory.createLineBorder(Color.black));
		singleD = new JTextField[31];
		judge(boxs[0][2].getText(),boxs[1][2].getText());
	}
	private static JTextField getTextField(String i) {
		JTextField temp = new JTextField();
		temp = new JTextField();
		temp.setPreferredSize(new Dimension (30,30));
		temp.setHorizontalAlignment(JTextField.CENTER);
		temp.setFont(new Font("微软雅黑",Font.PLAIN,18));
		temp.setBackground(Color.lightGray);
		temp.setEditable(false);
		temp.setText(i);
		temp.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		return temp;
	}
	public static void click(int a) {
		if(curr != a) {
			singleD[curr].setBackground(Color.lightGray);
			curr = a;
			singleD[curr].setBackground(Color.CYAN);
			show.setText(getText(2));
			singleD[curr].setBackground(Color.CYAN);
		}
	}
	
}
