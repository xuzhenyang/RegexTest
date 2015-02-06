import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.JLabel;

import java.awt.Font;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.SwingConstants;
import javax.swing.JButton;
import javax.swing.JTextArea;


public class MainFrame extends JFrame
{

	private JPanel contentPane;
	private JTextField inputRegex;
	private JTextField inputUrl;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args)
	{
		EventQueue.invokeLater(new Runnable()
		{
			public void run()
			{
				try
				{
					MainFrame frame = new MainFrame();
					frame.setVisible(true);
				} catch (Exception e)
				{
					e.printStackTrace();
				}
			}
		});
	}
	
	public static String sendGet(String url)
	{
		//连接URL并返回源码
		// result存放抓取结果
		String result = "";
		BufferedReader input = null;

		try
		{
			// 将url转换为URL对象
			URL realUrl = new URL(url);
			// 初始化连接
			URLConnection connection = realUrl.openConnection();
			// 打开连接
			connection.connect();
			// input存储网页代码
			input = new BufferedReader(new InputStreamReader(
					connection.getInputStream(), "UTF-8"));
			// line临时存放每一行数据
			String line;
			while ((line = input.readLine()) != null)
			{
				result += line;
			}
		} catch (Exception e)
		{
			System.out.println("发生异常 ： " + e);
			e.printStackTrace();
		} finally
		{
			if (input != null)
			{
				try
				{
					input.close();
				} catch (IOException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return result;
	}
	
	public static String getResult(String code, String regex)
	{
		//用正则匹配内容并返回结果
		String result = "";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(code);
		if(matcher.find())
		{
			result = matcher.group(1);
		}
		else
		{
			//not find
			result = "Not Found~";
		}
		return result;
	}
	
	public String check()
	{
		String url = inputUrl.getText();
		String code = sendGet(url);
		String regex = inputRegex.getText();
		String result = getResult(code, regex);
		return result;
	}

	/**
	 * Create the frame.
	 */
	public MainFrame()
	{
		setTitle("\u6B63\u5219\u8868\u8FBE\u5F0F\u68C0\u6D4B\u5668");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		inputRegex = new JTextField();
		inputRegex.setBounds(73, 64, 213, 21);
		contentPane.add(inputRegex);
		inputRegex.setColumns(10);
		
		inputUrl = new JTextField();
		inputUrl.setColumns(10);
		inputUrl.setBounds(73, 23, 213, 21);
		contentPane.add(inputUrl);
		
		JLabel labUrl = new JLabel("URL");
		labUrl.setHorizontalAlignment(SwingConstants.CENTER);
		labUrl.setFont(new Font("微软雅黑", Font.PLAIN, 18));
		labUrl.setBounds(10, 24, 54, 18);
		contentPane.add(labUrl);
		
		JLabel labRegex = new JLabel("Regex");
		labRegex.setHorizontalAlignment(SwingConstants.CENTER);
		labRegex.setFont(new Font("微软雅黑", Font.PLAIN, 18));
		labRegex.setBounds(9, 63, 54, 22);
		contentPane.add(labRegex);
		
		final JTextArea textArea = new JTextArea();
		textArea.setText("");
		textArea.setBounds(10, 95, 414, 156);
		contentPane.add(textArea);
		
		//Check按钮
		JButton btnCheck = new JButton("Test");
		btnCheck.setFont(new Font("微软雅黑", Font.PLAIN, 20));
		btnCheck.setBounds(301, 22, 102, 63);
		contentPane.add(btnCheck);
		btnCheck.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(java.awt.event.ActionEvent e)
			{
				// TODO Auto-generated method stub
				String result = check();
				textArea.setText(result);
			}
		});
		
	}
}
