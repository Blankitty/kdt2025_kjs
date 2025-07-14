package exam.web;

import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import exam.vo.StudentVO;

public class StudentGui extends JFrame {
	
	static ArrayList<StudentVO> stdArray;
	JTextField tfStdNo;
	JTextField tfStdName;
	JTextField tfPhone;
	JTextField tfEmail;
	JTextArea outputArea;
	JButton prtBtn;
	
	void ShowInputField() {
		JLabel label1 = new JLabel("학번");
		tfStdNo = new JTextField(10);
		JPanel panel1 = new JPanel();
		
		panel1.add(label1);
		panel1.add(tfStdNo);
		add(panel1);
		
		JLabel label2 = new JLabel("이름");
		tfStdName = new JTextField(10);
		JPanel panel2 = new JPanel();
		panel2.add(label2);
		panel2.add(tfStdName);
		add(panel2);
		
		JLabel label3 = new JLabel("전화번호");
		tfPhone = new JTextField(10);
		JPanel panel3 = new JPanel();
		panel3.add(label3);
		panel3.add(tfPhone);
		add(panel3);
		
		JLabel label4 = new JLabel("이메일");
		tfEmail = new JTextField(10);
		JPanel panel4 = new JPanel();
		panel4.add(label4);
		panel4.add(tfEmail);
		add(panel4);
	}
	void ShowAction() {
		JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER,10,10));
		
		JButton inBtn = new JButton("입력");
		JButton reset = new JButton("리셋");
		prtBtn = new JButton("출력");
		
		panel.add(inBtn);
		panel.add(reset);
		panel.add(prtBtn);
		
		add(panel);
		
		ActionListener inact = e -> {
			StudentVO std = new StudentVO(
					Integer.parseInt(tfStdNo.getText() )
					, tfStdName.getText()
					, tfPhone.getText()
					, tfEmail.getText() );
			
			stdArray.add(std);
		};
		inBtn.addActionListener(inact);
		reset.addActionListener(e->{
			tfStdNo.setText("");
			tfStdName.setText("");
			tfPhone.setText(" ");
			tfEmail.setText("");
		});
		
		prtBtn.addActionListener(e->{
			StringBuilder sb = new StringBuilder();
			outputArea.setText("aaa");
			for( StudentVO st: stdArray) {
				sb.append(st.toString());
				sb.append("\n");
		}
		outputArea.setText(sb.toString());
		
		System.out.println(sb.toString());
			
		});
		
		
	}
	void showResult() {
		JPanel panel3 = new JPanel();
		outputArea = new JTextArea(10, 20);
		//outputArea.setBackground(Color.BLUE);
		panel3.add(outputArea);
		add(panel3);
		
		
	}
	
	public StudentGui() {
		setLayout(new GridLayout(0,1));
		
		ShowInputField();
		ShowAction();
		showResult();
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(260, 300);
		setVisible(true);
	}

	public static void main(String[] args) {
		new StudentGui();

	}

}
		


