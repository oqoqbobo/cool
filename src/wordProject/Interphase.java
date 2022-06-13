package wordProject;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;



//getVoice
class GetVoice implements ActionListener{

	private String word;
	public GetVoice(int i) {
		int other = Interphase.getInter().index*5+i;
		this.word = vacabularys.getList().get(other).getWord();
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		FileDown.stop();
		if(FileDown.judge(word)) {
			PlayMusic.speak(word);
		}else {
			PlayMusic.saveUrlAsaddFile(word);
			
			PlayMusic.speak(word);
		}
	}
}
class Search implements ActionListener{
	private SQL sql;
	public Search() {
		this.sql = new SQL();
	}
	
	@Override
	public void actionPerformed(ActionEvent e){
		Interphase.getInter().getSymbol().setBorder(new LineBorder(Color.black));
		Interphase.getInter().getMean().setBorder(new LineBorder(Color.black));
		
		if(!Interphase.getInter().getWord().getText().equals("")) {
			String mysql = "select * from words where word = '"+Interphase.getInter().getWord().getText()+"'";
			sql.search(mysql);
			if(vacabularys.getList().size() != 0) {
				Interphase.getInter().getMean().setText(vacabularys.getList().get(0).getMean());
				Interphase.getInter().getSymbol().setText(vacabularys.getList().get(0).getSymbol());
				Interphase.getInter().getWord().setBorder(new LineBorder(Color.black));
			}else {
				Interphase.getInter().getWord().setBorder(new LineBorder(Color.red));
				Interphase.getInter().getMean().setText("");
				Interphase.getInter().getSymbol().setText("");
			}
			
		}else {
			Interphase.getInter().getWord().setBorder(new LineBorder(Color.red));
		}
	}
}

class Look implements ActionListener{
	private int i;
	private boolean isWord;
	public Look(int other,boolean theword) {
		i = other;
		isWord = theword;
	}
	private void change(int x) {
		if(Interphase.getInter().show[i][x]) {
			Interphase.getInter().show[i][x] = false;
		}else {
			Interphase.getInter().show[i][x] = true;
		}
		
	}
	@Override
	public void actionPerformed(ActionEvent e){
		int other = Interphase.getInter().index*5+i;
		if(isWord) {
			change(0);
			if(Interphase.getInter().show[i][0]) {
				Interphase.getInter().testWords[i][0].setText(vacabularys.getList().get(other).getWord());
				Interphase.getInter().testWords[i][0].setForeground(new Color(230,0,150));
			}else {
				Interphase.getInter().testWords[i][0].setText("单词");
				Interphase.getInter().testWords[i][0].setForeground(Color.black);
			}
		}else {
			change(1);
			if(Interphase.getInter().show[i][1]) {
				Interphase.getInter().testWords[i][1].setText(vacabularys.getList().get(other).getMean());
				Interphase.getInter().testWords[i][1].setForeground(new Color(230,0,150));
			}else {
				Interphase.getInter().testWords[i][1].setText("中文");
				Interphase.getInter().testWords[i][1].setForeground(Color.black);
			}
		}
		
	}
}

class Drop implements ActionListener{
	private int i;
	private SQL sql;
	public Drop(int other) {
		i = other;
		sql = new SQL();
	}
	@Override
	public void actionPerformed(ActionEvent e){
		
		Interphase.getInter().JBs[i].setVisible(true);
		if(Interphase.getInter().page == Interphase.getInter().all
				&& Interphase.getInter().num%5 == 1 && Interphase.getInter().page != 1) {
			Interphase.getInter().page--;
			Interphase.getInter().all--;
		}
		
		//先删后查，更新数组
		sql.delete(Interphase.getInter().things[i][0].getText());
		System.out.println(Interphase.getInter().things[i][0].getText());
		sql.search("select * from words;");
		
		Interphase.getInter().refresh();
		Interphase.getInter().panely.setVisible(true);
	}
}

class Shut implements ActionListener{
	private int i;
	private boolean isOpen=true;
	private SQL sql;
	public Shut(int other) {
		i=other;
		sql = new SQL();
	}
	
	@Override
	public void actionPerformed(ActionEvent e){
		if(isOpen) {
			Interphase.getInter().things[i][1].setEditable(true);
			Interphase.getInter().things[i][2].setEditable(true);
			Interphase.getInter().JBs[i].setVisible(true);
			isOpen = false;
		}else {
			Interphase.getInter().things[i][1].setEditable(false);
			Interphase.getInter().things[i][2].setEditable(false);
			isOpen = true;
			String symbol = Interphase.getInter().things[i][1].getText();
			String mean = Interphase.getInter().things[i][2].getText();
			String word = Interphase.getInter().things[i][0].getText();
			
			sql.update(word,symbol,mean);
			
			Interphase.getInter().JBs[i].setVisible(false);
		}
	}
}

class Up_Down implements ActionListener{
	private boolean isRight;
	private SQL sql;
	private boolean isPage;
	public Up_Down(boolean or,boolean isPage){
		isRight = or;
		sql = new SQL();
		this.isPage = isPage;
	}
	@Override
	public void actionPerformed(ActionEvent e){
		sql.search("select * from words");
		if(isRight) {
			if(isPage) {
				if(Interphase.getInter().page<Interphase.getInter().all) {
					Interphase.getInter().page++;
					Interphase.getInter().refresh();
				}
			}else {
				if(Interphase.getInter().index<Interphase.getInter().all-1) {
					Interphase.getInter().index++;
					Interphase.getInter().thirdChange();
				}
			}
		}else {
			if(isPage) {
				if(1<Interphase.getInter().page) {
					Interphase.getInter().page--;
					Interphase.getInter().refresh();
				}
			}else {
				if(0<Interphase.getInter().index) {
					Interphase.getInter().index--;
					Interphase.getInter().thirdChange();
				}
			}
	
		}
	}
}

class Changehander implements ActionListener{
	private int i;
	
	public Changehander(int num) {
		this.i = num;
	}
	private int getRan(int other,boolean isOver) {
		Random ra = new Random();
		int end;
		if(isOver) {
			end = ra.nextInt(Interphase.getInter().num/5+1);
			while(end == other) {
				end = ra.nextInt(Interphase.getInter().num/5+1);
			}
			
		}else {
			end = ra.nextInt(Interphase.getInter().num/5);
			while(end == other) {
				end = ra.nextInt(Interphase.getInter().num/5);
			}
		}
		return end;
	}
	@Override
	public void actionPerformed(ActionEvent e){
		if(i==1) {
			Interphase.getInter().panelx.setVisible(true);
			Interphase.getInter().panely.setVisible(false);
			Interphase.getInter().panelz.setVisible(false);
			Interphase.getInter().bottom.setVisible(false);
			Interphase.getInter().title.setVisible(false);
		}
		if(i==2){
			//需要search两次确保SQL.exist被赋值
			SQL sql = new SQL();
			sql.search("select * from words;");
			Interphase.getInter().title.setVisible(true);
			Interphase.getInter().panelx.setVisible(false);
			Interphase.getInter().panelz.setVisible(false);
			Interphase.getInter().refresh();
			Interphase.getInter().panely.setVisible(true);
			Interphase.getInter().drowBottom(Interphase.getInter().page, Interphase.getInter().all,true);
			Interphase.getInter().bottom.setVisible(true);
		} 
		if(i==3) {
			SQL sql = new SQL();
			sql.search("select * from words;");
			int other = Interphase.getInter().index;
			
			if(Interphase.getInter().num%5 != 0) {
				if(Interphase.getInter().num>5) {
					Interphase.getInter().index = getRan(other,true);
				}else {
					Interphase.getInter().index = 0;
				}
			}else {
				if(Interphase.getInter().num>5) {
					Interphase.getInter().index = getRan(other,false);
				}else {
					Interphase.getInter().index = 0;
				}
			}
			Interphase.getInter().thirdChange();
			
			Interphase.getInter().title.setVisible(false);
			Interphase.getInter().panelx.setVisible(false);
			Interphase.getInter().panely.setVisible(false);
			Interphase.getInter().drowBottom(Interphase.getInter().index, Interphase.getInter().all,false);
			Interphase.getInter().bottom.setVisible(true);
			Interphase.getInter().panelz.setVisible(true);
		}
		
	}
}
class Jbutton2hander implements ActionListener{
	
	@Override
	public void actionPerformed(ActionEvent e){
		Interphase.getInter().getWord().setText("");
		Interphase.getInter().getMean().setText("");
		Interphase.getInter().getSymbol().setText("");
	}
}
//创建响应动作监听的类，它需要重写ActionListener接口的actionPerformed()方法
class Jbutton1hander implements ActionListener{
	private String word;
	private String symbol;
	private String mean;
	
	private boolean change() {
		boolean isTrue = true;;
		word = Interphase.getInter().getWord().getText();
		if(word.equals("")) {
			Interphase.getInter().getWord().setBorder(new LineBorder(Color.red));
			isTrue = false;
		}else {
			Interphase.getInter().getWord().setBorder(new LineBorder(Color.black));
		}
		
		mean = Interphase.getInter().getMean().getText();
		if(mean.equals("")) {
			Interphase.getInter().getMean().setBorder(new LineBorder(Color.red));
			isTrue = false;
		}else {
			Interphase.getInter().getMean().setBorder(new LineBorder(Color.black));
		}
		
		symbol = Interphase.getInter().getSymbol().getText();
		if(symbol.equals("")) {
			Interphase.getInter().getSymbol().setBorder(new LineBorder(Color.red));
			isTrue = false;
		}else {
			Interphase.getInter().getSymbol().setBorder(new LineBorder(Color.black));
		}
		return isTrue;
	}
	@Override
	public void actionPerformed(ActionEvent e){
		SQL sql = new SQL();
		
		if(change()) {
			String mySql = "insert into words (word,symbol,mean) values(?,?,?);";
			
			if(!sql.insert(word,symbol,mean)) {
				Interphase.getInter().getWord().setBorder(new LineBorder(Color.red));
			}
		}
		
	}
}

public class Interphase extends JFrame{     //需要继承JFrame
	private static Interphase in;
	public void setInter(Interphase other) {
		in = other;
	}
	public static Interphase getInter(){
		return in;
	}
	private JFrame jf;	//主题窗口
	
	private JTextField  word;
	private JTextField symbol;
	private JTextField mean;
	
	public JPanel panelx;	 //firstWindow
	public JPanel panely;	//secondWindow
	public JPanel panelz;	//thirdWindow
	public JPanel title;	//secondTitle
	private JPanel panel;  //此画板描绘zuo边的工作栏
	public JPanel bottom;			//绘制second的翻页
	public JTextField[][] things;
	public JButton[] JBs;
	public int num;		//所有存储数据的量，在secondWindow的refresh中会刷新
	public JButton[][] testWords;		//	third中用于查看意思和英文
	public JButton[] voices;		//third中发音的按钮
	public ImageIcon icon;
	public JLabel[] theWords;	//third中显示音标
	public boolean show[][];	//third中控制意思和英文是否显示
	public int index;	//thirdWindow中记录默写单词位置的变量
	public static MyTime myTime;
	public JButton[] drop;
	
	static {
		myTime = new MyTime();
	}
	//secondWindow表示当前页数的变量
	public int page=1;
	public int all=1;
	
    public JTextField getWord() {
		return word;
	}
	public JTextField getSymbol() {
		return symbol;
	}
	public JTextField getMean() {
		return mean;
	}
	//jf为true表示显示，为false表示不显示
	public void open() {
		jf.setVisible(true);
	}
	public void close() {
		jf.setVisible(false);
	}
	
	public Interphase(String title)
    {
        jf = new JFrame(title);  
        word = new JTextField();
        symbol = new JTextField();
        mean = new JTextField();
        SQL sql = new SQL();
       
        
        word.setPreferredSize(new Dimension (220,30));
        symbol.setPreferredSize(new Dimension (220,30));
        mean.setPreferredSize(new Dimension (220,30));
        word.setFont(new Font("微软雅黑",Font.PLAIN,18));
        symbol.setFont(new Font("Times New Roman",Font.PLAIN,18));
        mean.setFont(new Font("微软雅黑",Font.PLAIN,18));
        Image icon = Toolkit.getDefaultToolkit().getImage("src/passP/timg.jpg");
        
        jf.setBounds(400,300,700,350); //设置窗口的属性 窗口位置以及窗口的大小
        jf.setDefaultCloseOperation(EXIT_ON_CLOSE); //设置关闭方式,关闭并退出
        jf.setIconImage(icon);
        jf.setLayout(null);
//      jf.setResizable(false);
        init();
        if(sql.exist) {
        	jf.setVisible(true);//设置窗口可见
        }else {
        	jf.setVisible(false);//设置窗口可见
        }
    }
    public void firstWindow() {
    	panelx = new JPanel();
    	panelx.setBounds(80,0,310,250);
    	
		  JPanel panel1 = new JPanel();
		  JPanel other = new JPanel();
	      JPanel panel2 = new JPanel();
	      JPanel panel3 = new JPanel();
	      JPanel panel4 = new JPanel();
          
        
          JLabel l1 = new JLabel("单词");
          l1.setFont(new Font("微软雅黑",Font.PLAIN,18));

          JLabel see = new JLabel("*");
          //设置JLabel字体颜色
          see.setForeground(Color.red);
          see.setFont(new Font("微软雅黑",Font.PLAIN,10));
          
          other.add(l1);
          other.add(see);
          panel1.add(other);
          
          panel1.add(this.word);
          
          
          
          panelx.add(panel1);
          
          JLabel l2 = new JLabel("音    标");
          l2.setFont(new Font("微软雅黑",Font.PLAIN,18));
          panel2.add(l2);
          panel2.add(this.symbol);
          panelx.add(panel2);
          
          JLabel l3 = new JLabel("中    文");
          l3.setFont(new Font("微软雅黑",Font.PLAIN,18));
          panel3.add(l3);
          panel3.add(this.mean);
          panelx.add(panel3);
          
          JButton btn = getButton("submit",Color.LIGHT_GRAY);
          panel4.add(btn);
          //按钮添加监听事件
          btn.addActionListener(new Jbutton1hander());
          
          JButton btn2 = getButton("rewrite",Color.LIGHT_GRAY);
          panel4.add(btn2);
          //按钮添加监听事件
          btn2.addActionListener(new Jbutton2hander());
          
          JButton look = getButton("search",Color.LIGHT_GRAY);
          
          other = new JPanel();
          other.add(look);
          
          see = new JLabel("*");
          //设置JLabel字体颜色
          see.setForeground(Color.red);
          see.setFont(new Font("微软雅黑",Font.PLAIN,10));
          other.add(see);
          
          panel4.add(other);
          look.addActionListener(new Search());
          
          panelx.add(panel4);
          jf.add(panelx);
          
       
    }
    private JTextField setText(String text,boolean or) {
    	JTextField other = new JTextField(text);
    	if(or) {
    		other.setFont(new Font("微软雅黑",Font.PLAIN,16));
    	}else {
    		other.setFont(new Font("Times New Roman",Font.PLAIN,16));
    	}
    	other.setPreferredSize(new Dimension(80,25));
    	other.setEditable(false);
    	return other;
    }
    private JButton getButton(String text,Color color) {
    	JButton JB = new JButton(text);
    	JB.setFont(new Font("微软雅黑",Font.PLAIN,15));
    	
	    
	    //设置鼠标在按钮上的形状（小手状）
		JB.setCursor(new Cursor(Cursor.HAND_CURSOR));
		//设置按钮背景颜色
		JB.setBackground(color);
		return JB;
    }
    private JButton getButton(ImageIcon icon,Color color) {
    	JButton JB = new JButton(icon);
    	JB.setFont(new Font("微软雅黑",Font.PLAIN,15));
    	
	    
	    //设置鼠标在按钮上的形状（小手状）
		JB.setCursor(new Cursor(Cursor.HAND_CURSOR));
		//设置按钮背景颜色
		JB.setBackground(color);
		return JB;
    }
    private void getContent(int first,int last) {
    	for(int i=first;i<last;i++) {
    		JPanel Ptemp = new JPanel();
			Ptemp.setLayout(new GridLayout(1,5));

    		things[i][0] = setText(vacabularys.getList().get(i).getWord(),true);
    		Ptemp.add(things[i][0]);
    		things[i][1] = setText(vacabularys.getList().get(i).getSymbol(),false);
    		Ptemp.add(things[i][1]);
    		things[i][2] = setText(vacabularys.getList().get(i).getMean(),true);
    		Ptemp.add(things[i][2]);
    		JButton JB = getButton("切换",Color.LIGHT_GRAY);
    		JB.addActionListener(new Shut(i));
    		Ptemp.add(JB);
        	JBs[i] = getButton("删除",Color.LIGHT_GRAY);
        	JBs[i].addActionListener(new Drop(i));
        	Ptemp.add(JBs[i]);
        	JBs[i].setVisible(false);
    		panely.add(Ptemp);
    	}
    }
    public void refresh() {
    	panely.removeAll();
    	
    	if(SQL.exist) {
    		SQL sql = new SQL();
			sql.search("select * from words");
    		num = vacabularys.getList().size();
    	}
    	if(num != 0) {
    		all = num/5;
        	if(num%5 != 0) {
        		all+=1;
        	}
        	things = new JTextField[num][];
        	for(int i=0;i<num;i++){
        		things[i] = new JTextField[3];
        	}
        	
        	JBs = new JButton[num];
        	
        	if(page == all) {
        		getContent((page-1)*5,num);
        	}else {
        		getContent((page-1)*5,5*page);
        	}
    	}
    	
    	
    	//验证containerPanel容器及其组件
    	panely.validate();
    	//重绘组件
    	panely.repaint();
    	
    	drowBottom(page,all,true);
    }
		public void drowBottom(int thePage,int theAll,boolean isPage) {
    	JLabel myPage;
    	if(bottom != null) {
    		jf.remove(bottom);
    	}
    	bottom = new JPanel();
    	bottom.setBounds(0,210,550,40);
    	JButton left = getButton("上一页",Color.LIGHT_GRAY);
    	
    	if(isPage) {
	    	myPage = new JLabel(thePage+"/"+theAll);
	    	myPage.setFont(new Font("微软雅黑",Font.PLAIN,18));
    	}else {
    		myPage = new JLabel((thePage+1)+"/"+theAll);
	    	myPage.setFont(new Font("微软雅黑",Font.PLAIN,18));
    	}
    	
    	JButton right = getButton("下一页",Color.LIGHT_GRAY);
    	
    	left.addActionListener(new Up_Down(false,isPage));
    	right.addActionListener(new Up_Down(true,isPage));
    	
	   	
    	bottom.add(left);
    	bottom.add(myPage);
    	bottom.add(right);
    	jf.add(bottom);
    	//验证containerPanel容器及其组件
    	jf.validate();
    	//重绘组件
    	jf.repaint();
    }
	public void secondWindow() {
		panely = new JPanel(new GridLayout(5,1));
		panely.setBounds(80,36,550,170);
		secondTitle();		
		refresh();
		
    	jf.add(panely);
    }
	private JLabel getLabel(String text) {
		JLabel other = new JLabel(text,JLabel.CENTER);
		other.setFont(new Font("微软雅黑",Font.PLAIN,15));
		other.setBorder(BorderFactory.createLineBorder(Color.black));
		return other;
	}

    public void secondTitle() {
    	title = new JPanel(new GridLayout(1,4));
		title.setBounds(80,0,440,36);
		title.add(getLabel("单词"));
		title.add(getLabel("音标"));
		title.add(getLabel("中文"));
		title.add(getLabel("操作"));
		
		jf.add(title);
    }
	private void paintThird(int first,int last) {
		JPanel temp;
		mandInit();
		if(first<last) {
			for(int i=first;i<last;i++) {
				temp = new JPanel(new GridLayout(1,3));
				theWords[i].setText(vacabularys.getList().get(i+index*5).getSymbol());
				
				testWords[i][0].setText("单词");
				
				testWords[i][1].setText("中文");
				
				temp.add(theWords[i]);
				temp.add(testWords[i][0]);
				temp.add(testWords[i][1]);
				temp.add(voices[i]);
	
				panelz.add(temp);
			}
		}
	}
	
	public void thirdChange() {
		panelz.removeAll();
		SQL sql = new SQL();
		sql.search("select * from words");

		num = vacabularys.getList().size();
		all = num/5;
    	if(num%5 != 0) {
    		all+=1;
    	}
    	
    	if(num != 0) {
			if(index != all-1) {
				paintThird(0,5);
			}else {
				if(num%5 != 0) {
					paintThird(0,num%5);
				}else {
					paintThird(0,5);
				}
			}
    	}
		//验证containerPanel容器及其组件
    	panelz.validate();
    	//重绘组件
    	panelz.repaint();
    	
    	drowBottom(index,all,false);
	}
	private String getWord(int i) {
		int other = Interphase.getInter().index*5+i;
	 	String aWord = vacabularys.getList().get(other).getWord();
		return aWord;
	}
	public void mandInit() {
		
		int theNum=0;
		if(index != all-1) {
			theNum = 5;
		}else {
			if(num%5 != 0) {
				theNum = num%5;
			}else {
				theNum = 5;
			}
		}
		if(theNum != 0) {
			testWords = new JButton[theNum][];
			show = new boolean[theNum][];
			theWords = new JLabel[theNum];
			voices = new JButton[theNum];
			drop = new JButton[theNum];
			for(int i=0;i<theNum;i++) {
				testWords[i] = new JButton[2];
				testWords[i][0] = getButton("",Color.orange);
				testWords[i][1] = getButton("",Color.orange);
				voices[i] = getButton(icon,Color.LIGHT_GRAY);
				
				show[i] = new boolean[2];
				show[i][0] = false;
				show[i][1] = false;
				
				testWords[i][0].addActionListener(new Look(i,true));
				testWords[i][1].addActionListener(new Look(i,false));
				voices[i].addActionListener(new GetVoice(i));
				
				theWords[i] = new JLabel("",JLabel.CENTER);
				theWords[i].setFont(new Font("Times New Roman",Font.PLAIN,16));
			}
		}
	}
	public void thirdWindow() {
		panelz = new JPanel(new GridLayout(6,1));
		panelz.setBounds(80,0,550,210);
		if(SQL.exist) {
			num = vacabularys.getList().size();
			mandInit();
			thirdChange();
		}
		
		jf.add(panelz);
	}
    public void init() {
    	icon = new ImageIcon("src/passP/voices.png");
		secondWindow();
		title.setVisible(false);
    	panely.setVisible(false);
    	bottom.setVisible(false);
    	thirdWindow();
    	bottom.setVisible(false);
    	panelz.setVisible(false);
    	
    	firstWindow();
    	
    	panel = new JPanel();
		panel.setBounds(0,0,80,250);
		JButton jb1 = getButton(" add/look ",Color.cyan);
		JButton jb2 = getButton("   edit   ",Color.cyan);
		JButton jb3 = getButton(" listening",Color.cyan);
		JButton jb4 = getButton("   alarm  ",Color.cyan);
		
		panel.add(jb1);
		panel.add(jb2);
		panel.add(jb3);
		panel.add(jb4);
		
		jb1.addActionListener(new Changehander(1));
		jb2.addActionListener(new Changehander(2));
		jb3.addActionListener(new Changehander(3));
		jb4.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				myTime.open();
			}
		});
		
		jf.add(panel);
		
    }

    public static void main(String[] args) {
    	SQL.login();
    	if(Interphase.getInter() == null && SQL.or != 1) {
    		Interphase in = new Interphase("窗口");
        	in.setInter(in);
        	Interphase.getInter().open();
    	}
    }
}

