package view;

import java.awt.Color;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.Socket;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

import net.WriteClass;

// ID 입력받기 위한 Form
public class IDFrame extends JFrame implements ActionListener {

	public static TextField tf = new TextField(8);

	JButton btn = new JButton("등록");

	ClientFrame cf;
	Socket socket;

	// WriteClass 추가
	WriteClass wc;

	public IDFrame(WriteClass wc, ClientFrame cf) {

		this.cf = cf;
		this.wc = wc;

		setTitle("ID input");
		setLayout(null);

		JLabel label = new JLabel("id:");
		label.setBounds(50, 60, 30, 30);
		add(label);

		tf.setBounds(80, 60, 100, 30);
		add(tf);

		btn.setBounds(80, 110, 100, 30);
		btn.addActionListener(this);
		add(btn);

		setBounds(300, 300, 250, 200);
		setBackground(Color.GREEN);
		setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		// ID 전송
		wc.sendMessage();

		// WriteClass 추가(송신클래스)

		// 첫번째 전송
		cf.isFirst = false;

		// ClientFrame을 시각화
		cf.setVisible(true);

		// 현재창 close
		this.dispose();
	}

}
