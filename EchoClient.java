package imichat;

import java.lang.Object;

import java.util.Scanner;
import java.util.Date;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
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

public class EchoClient {
	public static final int ECHO_PORT = 25565;
	public static void main (String[] args) {
		String log = "[���O]";
		Date dat = new Date(); //���t���擾]"; //���O��ݒ�
		Socket socket = null; //socket�𐧍�
		try {

			socket = new Socket("localhost", ECHO_PORT); //socket�𐧍�
			Scanner s = new Scanner(System.in); //���͗p�̃X�L���i�[
			JFrame textframe = new JFrame();
			JLabel label1 = new JLabel();
			JTextField textfield = new JTextField();
			textframe.setSize(480, 360);
			textframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			JPanel tf = new JPanel();
			JPanel lb = new JPanel();
			textfield.setSize(450, 10);
			tf.add(textfield);
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
			PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
			//BufferedReader keyIn = new BufferedReader(new InputStreamReader(System.in));
			//String success = log + dat + "�ڑ����܂����B" + socket.getLocalPort();
			
			System.out.println("�j�b�N�l�[����ݒ肵�Ă��������B");
			label1.setText("�j�b�N�l�[����ݒ肵�Ă��������B");
			String namaebox = textfield.getText();
			System.out.println("�y�������ǂݍ��݂܂��B�z");
			label1.setText("�R�����g����͂��Ă��������B");
			System.out.println("�R�����g����͂��Ă��������B");
			

			while (true) {
				//out.println(input);
				String scann = textfield.getText(); //���͂������͂�scann�ɐݒ�
				label1.setText("���M���Ă��܂��E�E�E�B");
				out.println(" " + namaebox + "����" +"    " + scann);
				//String line = in.readLine();
				String line = textfield.getText();
				if (line != null) {
					System.out.println(line);
				} else {
					break;
				}
			}
		}
			catch (IOException e) {
			e.printStackTrace();
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
}