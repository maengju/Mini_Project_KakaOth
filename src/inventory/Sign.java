//ȸ������â
package inventory;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import inventory.login.FrameDragListener;
//import inventory_management.MemberDAO;
//import inventory_management.MemberDTO;

public class Sign extends JFrame implements ActionListener { // implements �� ������, ������ �ۿ� �߻�޼ҵ� ����
																// ������, �͸��̳�
	private JButton btnExit; // �ڷΰ��� ��ư
	private JLabel uplabel = new JLabel("���̵�"); // KaKaoth�� ��Ÿ���� ��

	private JLabel idlabel; // "���̵�" ��
	private JTextField idText; // ���̵� ���� �ؽ�Ʈ�ʵ�
	private JButton idButton; // ���̵� �ߺ�Ȯ���ϴ� ��ư

	private JLabel pwlabel; // "��й�ȣ" ��
	private JTextField pwText; // ��й�ȣ ���� �ؽ�Ʈ�ʵ�
	private JTextField repwText; // ��й�ȣ�� ��Ȯ���ϴ� �ؽ�Ʈ�ʵ�
	// JPasswordField�� ��й�ȣǥ�ø� �ڵ����� '*'�� ǥ�����ش�

	private JLabel namelabel; // "�̸�" ��
	private JTextField nameText; // �̸��� ���� �ؽ�Ʈ�ʵ�

	private JLabel emaillabel; // "�̸���" ��
	private JTextField emailText;// �̸��� ���� �ؽ�Ʈ�ʵ�
	private JButton emailButton; // �̸��� ������ȣ ������ ��ư

	private JLabel emailchecklabel; // "������ȣ" ��
	private JTextField emailcheckText; // ������ȣ�� ���� ��
	private JButton emailcheckButton; // "��ȣȮ��" ��ư

	private JButton signCompleteButton; // �������(ȸ������) ��ư

	private boolean signidcheck = false;
	private boolean emailreadycheck = false;
	private TestEmail te;

	// ���� ����
	public static final Color KAKAO_YELLOW = new Color(250, 225, 0);
	public static final Color KAKAO_BROWN = new Color(82, 55, 56);
	protected static final Object anchor = null;

	private MemberDTO dto;
	private MemberDAO dao;

	// ������
	public Sign() {
		dao = new MemberDAO();
		setUndecorated(true); // Ÿ��Ʋ�� ����
		FrameDragListener frameDragListener = new FrameDragListener(this);// �ݵ�� MouseEvent�� Drag �̵��� �����������
		addMouseListener(frameDragListener);
		addMouseMotionListener(frameDragListener); // Ÿ��Ʋ�� ���Ž�, �������� �������� �ʴ� ������ �����Ѵ�

		setLayout(null); // ���̾ƿ� ����

		/*
		 * �߿�! �ʵ忡 ���� �����ϴ���, JLabel uplabel = new JLabel("KaKaoth"); �̷����� ���� ���� ���� ��
		 * ������ ����������(���� ����) uplabel = new JLabel("KaKaoth"); �̷��� ����, ���������� ������(�Ķ��� ����)
		 * ���������� ��������, �ʵ忡�� ���������(���������ϰ�����)�� ������
		 */
		// īī�� ��
		uplabel = new JLabel("KaKaoth");
		uplabel.setFont(new Font("arial", Font.BOLD, 25));
		uplabel.setSize(150, 80);
		uplabel.setLocation(160, 25);
		// ���̵� ��
		idlabel = new JLabel("���̵�");
		idlabel.setFont(new Font("���ﳲ��ü", Font.ITALIC, 16));
		idlabel.setSize(150, 35);
		idlabel.setLocation(20, 105);
		// ���̵� �ؽ�Ʈ�ʵ�
		idText = new JTextField(30);
		idText.setSize(360, 30);
		idText.setLocation(20, 140);
		// ��й�ȣ ��
		pwlabel = new JLabel("��й�ȣ");
		pwlabel.setFont(new Font("���ﳲ��ü", Font.ITALIC, 16));
		pwlabel.setSize(100, 50);
		pwlabel.setLocation(20, 175);
		// ��й�ȣ �ؽ�Ʈ�ʵ�
		pwText = new JTextField(30);
		pwText.setSize(360, 30);
		pwText.setLocation(20, 220);
		// ��й�ȣ ���Է� �ؽ�Ʈ�ʵ�
		repwText = new JPasswordField(30);
		repwText.setSize(360, 30);
		repwText.setLocation(20, 255);
		// �̸� ��
		namelabel = new JLabel("�̸�");
		namelabel.setFont(new Font("���ﳲ��ü", Font.ITALIC, 16));
		namelabel.setSize(100, 50);
		namelabel.setLocation(20, 280);
		// �̸� �ؽ�Ʈ�ʵ�
		nameText = new JTextField(30);
		nameText.setSize(360, 30);
		nameText.setLocation(20, 320);
		// �̸��� ��
		emaillabel = new JLabel("�̸���");
		emaillabel.setFont(new Font("���ﳲ��ü", Font.ITALIC, 16));
		emaillabel.setSize(50, 35);
		emaillabel.setLocation(20, 350);
		// �̸������� �ؽ�Ʈ�ʵ�
		emailText = new JTextField(30);
		emailText.setSize(360, 30);
		emailText.setLocation(20, 380);
		// �̸���������ȣ ��
		emailchecklabel = new JLabel("������ȣ");
		emailchecklabel.setFont(new Font("���ﳲ��ü", Font.ITALIC, 16));
		emailchecklabel.setSize(70, 35);
		emailchecklabel.setLocation(20, 425);
		// �̸���������ȣ �ؽ�Ʈ�ʵ�
		emailcheckText = new JTextField(30);
		emailcheckText.setSize(360, 30);
		emailcheckText.setLocation(20, 460);

		// �ڷΰ��� ��
		Image img = Toolkit.getDefaultToolkit().getImage("image/back.png");
		Icon icon = new ImageIcon(img);
		btnExit = new JButton(icon);
		btnExit.setBounds(375, 0, 25, 25);

		// ���̵� �ߺ�Ȯ�� ��ư
		idButton = new JButton("�ߺ� Ȯ��");
		idButton.setBounds(230, 180, 150, 30);
		idButton.setFocusPainted(false);
		idButton.setForeground(Color.WHITE);
		idButton.setBackground(KAKAO_BROWN);
		idButton.setFont(new Font("���ﳲ��ü", Font.ITALIC, 16));

		// �����̸��� ���� ��ư
		emailButton = new JButton("�̸��� ����");
		emailButton.setBounds(230, 420, 150, 30);
		emailButton.setFocusPainted(false);
		emailButton.setForeground(Color.WHITE);
		emailButton.setBackground(KAKAO_BROWN);
		emailButton.setFont(new Font("���ﳲ��ü", Font.ITALIC, 16));

		// �̸��� ������ȣ Ȯ���ϴ� ��ư
		emailcheckButton = new JButton("��ȣȮ��");
		emailcheckButton.setBounds(230, 500, 150, 30);
		emailcheckButton.setFocusPainted(false);
		emailcheckButton.setForeground(Color.WHITE);
		emailcheckButton.setBackground(KAKAO_BROWN);
		emailcheckButton.setFont(new Font("���ﳲ��ü", Font.ITALIC, 16));

		// ������� ��ư
		signCompleteButton = new JButton("�������");
		signCompleteButton.setBackground(KAKAO_YELLOW);
		signCompleteButton.setFont(new Font("���ﳲ��ü", Font.BOLD, 16));
		signCompleteButton.setFocusPainted(false);
		signCompleteButton.setForeground(Color.BLACK);
		signCompleteButton.setBounds(20, 575, 360, 50);
		signCompleteButton.setBorderPainted(false); // ��ư �׵θ� ����

		// �߰�
		add(btnExit);
		add(uplabel);
		add(idlabel);
		add(idText);
		add(idButton);
		add(pwlabel);
		add(pwText);
		// add(repwText);
		add(namelabel);
		add(nameText);
		add(emaillabel);
		add(emailText);
		add(emailButton);
		add(emailchecklabel);
		add(emailcheckText);
		add(emailcheckButton);
		add(signCompleteButton);

		// swing���� ���ȭ�� ����
		this.getContentPane().setBackground(Color.WHITE);
		// JFrame ȯ�漳��
		setSize(400, 680);
		setVisible(true);
		setResizable(false);
		setLocationRelativeTo(null);

		// event
		idButton.addActionListener(this);
		emailButton.addActionListener(this);
		emailcheckButton.addActionListener(this);
		btnExit.addActionListener(this);
		signCompleteButton.addActionListener(this);

	}// sign();

//	public String inputEmail() {
//		String email = emailText.getText();
//		return email;
//	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == idButton) {// id��ư
			String id = idText.getText();
			MemberDAO dao = new MemberDAO();
			String idcheck = dao.idCheck(id);
			boolean check = true;

			if (idcheck != null) {
				check = false;
			}

			if (id.length() == 0) {
				JOptionPane.showMessageDialog(null, "���̵� �Է����ּ���");
				return;
			} else if (check) {
				JOptionPane.showMessageDialog(null, "��밡���� ���̵� �Դϴ�");
				signidcheck = true;

			} else if (check != true) {
				JOptionPane.showMessageDialog(null, "�̹� ������� ���̵� �Դϴ�");
				return;
			}

		} else if (e.getSource() == emailButton) {
			System.out.println("�̸��Ϲ�ưŬ��");
			String email = emailText.getText();

			if (email.equals("")) {
				JOptionPane.showMessageDialog(null, "�̸����� �Է����ּ���");

			} else {
				te = new TestEmail(email);
				emailreadycheck = true;
				JOptionPane.showMessageDialog(null, "������ȣ��"+email+"�� ���۵Ǿ����ϴ�. Ȯ�����ּ���");
			}
		} else if (e.getSource() == emailcheckButton) {
			String emailcheck = emailcheckText.getText();

			if (emailcheck.equals(te.getCert())) {
				JOptionPane.showMessageDialog(null, "�����Ϸ�");

			}

		} else if (e.getSource() == signCompleteButton) {
			String id = idText.getText();
			@SuppressWarnings("deprecation")
			String pwd = pwText.getText();
			String name = nameText.getText();
			String email = emailText.getText();

			if (signidcheck != true) {
				JOptionPane.showMessageDialog(null, "id �ߺ�Ȯ�����ּ���");
				// �ߺ�Ȯ�� ���̷α� ������ ȸ�����Խ�Ű�µ� �ٽ��ؾ���

			} else if (pwd.length() == 0) {
				JOptionPane.showMessageDialog(null, "��й�ȣ�� �Է����ּ���");
				return;
			} else if (name.length() == 0) {
				JOptionPane.showMessageDialog(null, "�̸��� �Է����ּ���");
				return;

			}else if(emailreadycheck != true) {
				JOptionPane.showMessageDialog(null, "�̸��� ������ �����ּ���");
				return;
			}else {
				JOptionPane.showMessageDialog(null, "ȸ������ �Ǿ����ϴ�");
				// ������
				

				dto = new MemberDTO();
				dto.setId(id);
				dto.setPwd(pwd);
				dto.setName(name);
				dto.setEmail(email);

				// DB //??
				dao.insertMember(dto);
				
			}
			

			dispose();

		} else if (e.getSource() == btnExit) {
			dispose();
		}

	}

	
	
	public JTextField getEmailText() {
		return emailText;
	}

}
