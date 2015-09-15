/* The program it was developed by "imichat development project".
 * If you want to modify or alter this program, please contact the development team.
 * If you do if you have discovered a bug in this program, please contact as soon as possible the development team.
 */

package imichatgit;

import java.lang.Object;
import java.util.Scanner;
import java.util.Date;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.text.BadLocationException;
import javax.swing.text.JTextComponent;
import javax.swing.table.*;

import java.io.*;
import java.net.*;
import java.nio.charset.Charset;


public class EchoClient2 extends Thread implements ActionListener {
	public static final int ECHO_PORT = 30000;
	        
	JFrame textframe = new JFrame();
	JPanel tf = new JPanel();
	JPanel lb = new JPanel();
	JPanel textarea = new JPanel();
	JPanel namaep = new JPanel();
	JLabel label1 = new JLabel();
	JLabel namael = new JLabel();
	JTextField namaeran = new JTextField("", 30); 
	JTextField textfield = new JTextField("", 30);
	JButton sendb = new JButton();
	JTextArea texter = new JTextArea();
	JScrollPane texters = new JScrollPane(texter);

	String namaebox;
	Socket socket = null; //socketを制作
    PrintStream os = null;
    BufferedReader is = null;
	        
	public static void main (String[] args) {
        String host = "localhost";
        if (args.length > 0) {
            host = args[0];
        }

		EchoClient2 client = new EchoClient2();
		client.createClient(host);
        client.start();
	}

	public EchoClient2() {
	}
    
    public void run() {
        try {
            // サーバーからのメッセージを受け取り画面に表示します
            String responseLine;
            String responseLine2;
            while (true) {
                responseLine = is.readLine();
                if ("[close]".equals(responseLine) || responseLine == null) {
                    break;
                }
    			texter.append("Server:" + responseLine + "\n");
                System.out.println("Server: " + responseLine);

            }
        } catch (UnknownHostException e) {
            System.err.println("Trying to connect to unknown host: " + e);
        } catch (IOException e) {
            System.err.println("IOException: " + e);
        }
    }
	
	private void createClient(String host) {
		String log = "[ログ]";
		Date dat = new Date(); //日付を取得]"; //ログを設定
		try {

			socket = new Socket(host, ECHO_PORT); //socketを制作
	        os = new PrintStream(socket.getOutputStream());
	        is = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"));

			/*Scanner s = new Scanner(System.in); 文章用のスキャナー */
			sendb.addActionListener(this);
			sendb.setText("送信（send）");
			textframe.setSize(840, 480);
			textframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			texter.setSize(40, 10);
			namaeran.setSize(10, 30);
			JScrollPane texters = new JScrollPane(texter);
			texters.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
			texters.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		 	texters.setPreferredSize(new Dimension(200, 120));
		 	
			
			tf.setSize(440, 50);
			tf.add(textfield);
			tf.add(sendb);
			FlowLayout layout = new FlowLayout();
			JPanel textarea1 = new JPanel();
			textarea1.setLayout(new FlowLayout(FlowLayout.CENTER));
			JPanel nyuryoku = new JPanel();
			Container contentpane1 = textframe.getContentPane();
			Container contentpane2 = textframe.getContentPane();
			Container buttonpane = textframe.getContentPane();
			Container namaepane = textframe.getContentPane();
			Container textareapane = textframe.getContentPane();
			layout.setAlignment(FlowLayout.CENTER);
			namaepane.setLayout(layout);
			textarea1.add(texter);
			textarea1.add(texters);
			nyuryoku.add(namaeran);
			nyuryoku.add(textfield);
			nyuryoku.add(sendb);
			nyuryoku.setLayout(layout);
			namaepane.add(nyuryoku, BorderLayout.SOUTH);
			contentpane1.add(textarea1, BorderLayout.PAGE_START);
			
			
			
			
			texter.append("サーバーに接続しています・・・。" + "行先" + socket.getRemoteSocketAddress() + "\n");
			
			namael.setText("まず、名前を設定して、コメントを入力してください。");
			
			textframe.setVisible(true);
			System.out.println(log + dat +"接続しました。"
				+ socket.getRemoteSocketAddress());
			texter.append("サーバーに接続しました。" + "行先" + socket.getRemoteSocketAddress() + "\n");
			namael.setText("まず、名前を設定して、コメントを入力してください。" + "\n");
			label1.setText(log + dat + "接続しました。" + socket.getRemoteSocketAddress());
			//BufferedReader keyIn = new BufferedReader(new InputStreamReader(System.in));
			//String success = log + dat + "接続しました。" + socket.getLocalPort();
			namaebox = namaeran.getText();
			System.out.println("【文字列を読み込みます。】");
			label1.setText("コメントを入力してください。");
			System.out.println("コメントを入力してください。");

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		try {
			//throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
			String message = textfield.getText(); //入力した文章をscannに設定
			if (message == null || message.length() <= 0) {
				try {
					if (socket != null) {
						socket.close();
					}
				} catch (IOException err) {
					System.out.println("切断されました。"
						+ socket.getRemoteSocketAddress());
				}
				return;
			}
			System.out.println(message);

			// メッセージを送ります
			label1.setText("送信しています・・・。");
			namaebox = namaeran.getText();
			message = " " + namaebox + "さん" + "    " + message + "\n";
			os.write(message.getBytes(Charset.forName("UTF-8")));
			os.flush();

			System.out.println("【文字列を読み込みます。】");
			label1.setText("コメントを入力してください。");
			
			System.out.println("コメントを入力してください。");
		} catch (IOException err) {
			err.printStackTrace();
			System.out.println("鯖が死にました。管理人の気が向いたら直します。");
		}

	}

}