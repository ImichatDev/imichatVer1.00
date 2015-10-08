/* The program it was developed by "imichat development project".
 * If you want to modify or alter this program, please contact the development team.
 * If you do if you have discovered a bug in this program, please contact as soon as possible the development team.
 */
package imichatgit; //パッケージ変更予定

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;
import java.util.List;
import java.util.ArrayList;
import java.nio.charset.Charset;

import java.awt.Rectangle;

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
import javax.swing.text.DefaultCaret;


public class EchoServer extends Thread {
	public static final int ECHO_PORT = 30000; //ポートを設定（ECHO_PORTはClientで使用）
	
    Socket clientSocket = null;
    PrintStream myStream = null;
    static List<PrintStream> streamList = new ArrayList<>();

	static JFrame frame1 = new JFrame();
	static JTextArea logtx = new JTextArea();
	static JTextField textfl = new JTextField();
	static Border border;

	public static void main(String args[]) {

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

        makeFrame();
 
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
            myStream = new PrintStream(clientSocket.getOutputStream());
            streamList.add(myStream);

            // クライアントからのメッセージを待ち、受け取ったメッセージをそのまま返す
            while ((line = is.readLine()) != null) {
                if (line.startsWith("GET /")) {
                    String[] terms = line.split(" ");
                    if (terms.length >= 2) {
                        String url = terms[1];
                        url = url.replaceAll(".*/", "");
                        String page = "";
                        page += "HTTP/1.1 200 OK\r\n";
                        if (url.lastIndexOf(".html") > 0) {
                            page += "Content-Type: text/html\r\n";
                        }
                        page += "\r\n";
                        byte[] bytes = page.getBytes(Charset.forName("UTF-8"));
                        myStream.write(bytes);
                        byte[] body = loadChatPage(url);
                        myStream.write(body);
                        myStream.write("\r\n".getBytes(Charset.forName("UTF-8")));
                        myStream.flush();
                        clientSocket.close();
                        break;
                    }
                }
				Date dat = new Date();
				String log = "[ログ]";
				System.out.print(log + dat + "　　　" + "" + line + "\n");
				logtx.append("受信" + log + dat + "　　　" + "" + line + "\n");
                System.out.println(line);
                line = line + "\n";
                byte[] bytes = line.getBytes(Charset.forName("UTF-8"));
                for (PrintStream os : streamList) {
                    if (os == myStream) {
                        continue;
                    }
                    os.write(bytes);
                    os.flush();
                }
				logtx.append("送信" + log + dat + "　　　" + "" + line + "\n");
				System.out.print(log + dat + "　　　" + "" + line + "\n");
				
                logtx.setCaretPosition(logtx.getDocument().getLength());
            }
        }
        catch (IOException e) {
            System.out.println(e);
			System.out.println("エラーが発生しました。");
			logtx.append("エラーが発生したようです。サーバー側にエラーがあるか、クライアント側が不正に退出した可能性があります。");
        }
    }

    private byte[] loadChatPage(String fileName) throws IOException {
        String filePath = "imichatgit/kiki/" + fileName;
        File file = new File(filePath);
        byte[] readBinary = new byte[(int)file.length()];
        FileInputStream fis = new FileInputStream(file);
        BufferedInputStream bis = new BufferedInputStream(fis);
        bis.read(readBinary);
        return readBinary;
    }

	static private void makeFrame() {

		String logdata = "サーバーを開設しています。"; 

		logtx.setEditable(false);
		logtx.setLineWrap(true);
		logtx.setText(logdata + "\n");
		logtx.setLineWrap(true);
		logtx.setSize(940, 300);
		
		JScrollPane scrollpane = new JScrollPane(logtx);
		scrollpane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
       	scrollpane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
	 	scrollpane.setPreferredSize(new Dimension(200, 120));
    	scrollpane.setBorder(new BevelBorder(BevelBorder.RAISED));
    	
    	// scrollpane.getViewport().scrollRectToVisible(new Rectangle(0, Integer.MAX_VALUE - 1, 1, 1));

		Container logpane = frame1.getContentPane();
		logpane.add(scrollpane, BorderLayout.CENTER);
		logpane.add(textfl, BorderLayout.SOUTH);

		frame1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame1.setSize(960, 480);
		frame1.setVisible(true);

	}
	
}