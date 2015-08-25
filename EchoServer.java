package imichat;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.io.PrintStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;

import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

public class EchoServer {
	public static final int ECHO_PORT = 25565; //ポートを設定（ECHO_PORTはClientで使用）
	
	public static void main(String args[]) {
		ServerSocket serverSocket = null;
		Socket socket = null;
		
		String logdata = "サーバーを開設しています。"; 
		
		JFrame frame1 = new JFrame();
		frame1.setSize(960, 480);
		JTextArea logtx = new JTextArea();
		JScrollPane scrollpane = new JScrollPane(logtx);
		logtx.setEditable(false);
		logtx.setLineWrap(true);
		logtx.setText(logdata + "\n");
		logtx.setLineWrap(true);
		logtx.setSize(940, 300);
		JTextField textfl = new JTextField();
		
		JPanel logp1 = new JPanel();
		logp1.add(logtx, scrollpane);
		JPanel textp1 = new JPanel();
		textp1.add(textfl);
		
		Container logpane = frame1.getContentPane();
		logpane.add(logp1, BorderLayout.NORTH);
		
		
		
		frame1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame1.setVisible(true);
		
		
		try {
			serverSocket = new ServerSocket(ECHO_PORT);
			System.out.println("EchoServerが起動しました。(port="
					+ serverSocket.getLocalPort() + ")");
			logtx.append("EchoServerが起動しました。(port="
					+ serverSocket.getLocalPort() + ")" + "\n");
			socket = serverSocket.accept();
			System.out.println("接続されました。"
					+ socket.getRemoteSocketAddress());
			logtx.append("接続されました。" + socket.getRemoteSocketAddress() + "\n");
			Date dat = new Date();
			String log = "[ログ]";
			BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			//PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
			PrintStream out = new PrintStream(socket.getOutputStream());
			String line;
			while ( (line = in.readLine()) != null) {
				System.out.print(log + dat + "　　　" + "" + line + "\n");
				logtx.append("受信" + log + dat + "　　　" + "" + line + "\n");
				out.write(line.getBytes());
				//out.println(line + "\n");
				//out.flush();
				logtx.append("送信" + log + dat + "　　　" + "" + line + "\n");
				System.out.print(log + dat + "　　　" + "" + line + "\n");
			}
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("エラーが発生しました。");
			logtx.append("エラーが発生したようです。サーバー側にエラーがあるか、クライアント側が不正に退出した可能性があります。");
		} finally {
			try {
				if(socket != null) {
					socket.close();
				}
			} catch (IOException e) {}
			try {
				if (serverSocket != null) {
					serverSocket.close();
				}
			} catch (IOException e) {}
		}
	}
	
}