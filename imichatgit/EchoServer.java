/* The program it was developed by "imichat development project".
 * If you want to modify or alter this program, please contact the development team.
 * If you do if you have discovered a bug in this program, please contact as soon as possible the development team.
 */
package imichatgit; //パッケージ変更予定

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
import java.util.List;
import java.util.ArrayList;
import java.nio.charset.Charset;

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
import javax.swing.border.*;


public class EchoServer extends Thread {
	public static final int ECHO_PORT = 25565; //ポートを設定（ECHO_PORTはClientで使用）
	
    Socket clientSocket = null;
    static List<PrintStream> streamList = new ArrayList<>();

	static JFrame frame1 = new JFrame();
	static JTextArea logtx = new JTextArea();
	static JScrollPane scrollpane = new JScrollPane(logtx);
	static JTextField textfl = new JTextField();
	static JPanel logp1 = new JPanel();
	static JPanel textp1 = new JPanel();
	static Border border;

	public static void main(String args[]) {
		scrollpane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
   	scrollpane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
	 	scrollpane.setPreferredSize(new Dimension(200, 120));

    	Border border = new BevelBorder(BevelBorder.RAISED);
    	scrollpane.setBorder(border);



		String logdata = "サーバーを開設しています。"; 

		frame1.setSize(960, 480);
		logtx.setEditable(false);
		logtx.setLineWrap(true);
		logtx.setText(logdata + "\n");
		logtx.setLineWrap(true);
		logtx.setSize(940, 300);
		
		logp1.add(logtx, scrollpane);
		//logp1.add(scrollpane);

		textp1.add(textfl);
		
		Container logpane = frame1.getContentPane();
		logpane.add(logp1, BorderLayout.CENTER);
		
		frame1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame1.setVisible(true);

        // ポート9999番を開く
        ServerSocket echoServer = null;
        try {
            echoServer = new ServerSocket(ECHO_PORT);
			System.out.println("EchoServerが起動しました。(port="
					+ echoServer.getLocalPort() + ")");
        }
        catch (IOException e) {
            System.out.println(e);
        }

        // makeFrame();
 
         // クライアントからの要求を受けるソケットを開く 
        try {
            while (true) {
                Socket clientSocket = echoServer.accept();
				System.out.println("接続されました。"
						+ clientSocket.getRemoteSocketAddress());
				logtx.append("接続されました。" + clientSocket.getRemoteSocketAddress() + "\n");
                EchoServer server = new EchoServer(clientSocket);
                server.start();
            }
        }
        catch (IOException e) {
            System.out.println(e);
        }
    }

    public EchoServer(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }
    
    public void run() {
        // ソケットや入出力用のストリームの宣言
        String line;
        BufferedReader is;
        
        try {
            is = new BufferedReader(new InputStreamReader(clientSocket.getInputStream(), "UTF-8"));
            streamList.add(new PrintStream(clientSocket.getOutputStream()));

            // クライアントからのメッセージを待ち、受け取ったメッセージをそのまま返す
            while ((line = is.readLine()) != null) {
				Date dat = new Date();
				String log = "[ログ]";
				System.out.print(log + dat + "　　　" + "" + line + "\n");
				logtx.append("受信" + log + dat + "　　　" + "" + line + "\n");
                System.out.println(line);
                line = line + "\n";
                byte[] bytes = line.getBytes(Charset.forName("UTF-8"));
                for (PrintStream os : streamList) {
                    os.write(bytes);
                    os.flush();
                }
				logtx.append("送信" + log + dat + "　　　" + "" + line + "\n");
				System.out.print(log + dat + "　　　" + "" + line + "\n");
            }
        }
        catch (IOException e) {
            System.out.println(e);
			System.out.println("エラーが発生しました。");
			logtx.append("エラーが発生したようです。サーバー側にエラーがあるか、クライアント側が不正に退出した可能性があります。");
        }
    }
		
	static private void makeFrame() {
		String logdata = "サーバーを開設しています。"; 

		frame1.setSize(960, 480);
		logtx.setEditable(false);
		logtx.setLineWrap(true);
		logtx.setText(logdata + "\n");
		logtx.setLineWrap(true);
		logtx.setSize(940, 300);
		
		logp1.add(logtx, scrollpane);
		textp1.add(textfl);
		
		Container logpane = frame1.getContentPane();
		logpane.add(logp1, BorderLayout.NORTH);
		
		frame1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame1.setVisible(true);
	}
		
}