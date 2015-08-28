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
import javax.swing.JTextField;
import javax.swing.text.BadLocationException;
import javax.swing.text.JTextComponent;
import javax.swing.table.*;

public class EchoClient2 implements ActionListener {
	public static final int ECHO_PORT = 25565;
	        
	        JFrame textframe = new JFrame();
	        JPanel tf = new JPanel();
	        JPanel lb = new JPanel();
	        JLabel label1 = new JLabel();
	        JTextField textfield = new JTextField("", 30);
	        JButton sendb = new JButton();
	        
	        String namaebox;
	        Socket socket = null; //socket�𐧍�
	        PrintWriter out;
	        
		public static void main (String[] args) {
	            EchoClient2 client = new EchoClient2();
	            client.createClient();
		}
	        
	        private void createClient() {
	            String log = "[���O]";
	            Date dat = new Date(); //���t���擾]"; //���O��ݒ�
	            try {

	                    socket = new Socket("localhost", ECHO_PORT); //socket�𐧍�
	                    /*Scanner s = new Scanner(System.in); ���͗p�̃X�L���i�[ */
	                    sendb.addActionListener(this);
	                    sendb.setText("���M�isend�j");
	                    textframe.setSize(480, 120);
	                    textframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	                    tf.setSize(440, 50);
	                    textfield.setSize(100, 30);
	                    tf.add(textfield);
	                    tf.add(sendb);
	                    lb.add(label1);
	                    Container contentpane1 = textframe.getContentPane();
	                    Container contentpane2 = textframe.getContentPane();
	                    contentpane1.add(tf, BorderLayout.SOUTH);
	                    contentpane2.add(lb, BorderLayout.NORTH);
	                    textframe.setVisible(true);

	                   System.out.println(log + dat +"�ڑ����܂����B"
	                                    + socket.getRemoteSocketAddress());
	                    label1.setText(log + dat + "�ڑ����܂����B" + socket.getRemoteSocketAddress());
	                    BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
	                    out = new PrintWriter(socket.getOutputStream(), true);
	                    //BufferedReader keyIn = new BufferedReader(new InputStreamReader(System.in));
	                    //String success = log + dat + "�ڑ����܂����B" + socket.getLocalPort();

	                    System.out.println("�j�b�N�l�[����ݒ肵�Ă��������B");
	                    label1.setText("�j�b�N�l�[����ݒ肵�Ă��������B");
	                    namaebox = textfield.getText();
	                    System.out.println("�y�������ǂݍ��݂܂��B�z");
	                    label1.setText("�R�����g����͂��Ă��������B");
	                    System.out.println("�R�����g����͂��Ă��������B");

	            } catch (IOException e) {
	            }
	            finally {
	                try {
	                    if (socket != null) {
	                        socket.close();
	                    }
	                } catch (IOException e) {
	                    System.out.println("�ؒf����܂����B"
	                                    + socket.getRemoteSocketAddress());
	                }
	            }
	        }

	    @Override
	    public void actionPerformed(ActionEvent e) {
	        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	        String scann = textfield.getText(); //���͂������͂�scann�ɐݒ�
	        label1.setText("���M���Ă��܂��E�E�E�B");
	        out.println(" " + namaebox + "����" +"    " + scann);
	        //out.flush();
	        //String line = in.readLine();
	        String line = textfield.getText();
	        if (line != null) {
	                System.out.println(line);
	        } 
	        
	        
	        System.out.println("�y�������ǂݍ��݂܂��B�z");
	        label1.setText("�R�����g����͂��Ă��������B");
	        System.out.println("�R�����g����͂��Ă��������B");
	    }
	}

}
