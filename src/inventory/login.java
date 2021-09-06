package inventory;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

@SuppressWarnings("serial")
public class login extends JFrame {

	private JPanel pnlFirst; // ��ü�� ����ִ�
	// ù��° ȭ���� ������Ʈ
	private JTextField idT, pwdT;
	private int aa;

	JPanel signPanel;
	JLabel lblImg, lblInfo;
	private JButton btnLogin, btnExit, signButton;
	private JCheckBox Autologin;
	public static final Color KAKAO_YELLOW = new Color(250, 225, 0);
	public static final Color KAKAO_BROWN = new Color(82, 55, 56);

	private MemberDAO dao = new MemberDAO();

	FrameDragListener frameDragListener;

	public login() {

		setUndecorated(true); // ������â�� ��ܸ޴� �������� īī������ �׸��� �ֱ����� ������Ų��
		frameDragListener = new FrameDragListener(this); // �ݵ�� MouseEvent�� Drag �̵��� �����������

		createComponent();
		addComponent();
		addListener();

		setSize(450, 740);

		setVisible(true);

		setResizable(false);

		setLocationRelativeTo(null);

	}

	public void createComponent() { // ù��° ȭ�� ������Ʈ ����

		pnlFirst = new JPanel(null); // ���̾ƿ� ����
		pnlFirst.setBackground(KAKAO_YELLOW);

		Image img = Toolkit.getDefaultToolkit().getImage("image/īī��������1.png");
		Icon icon = new ImageIcon(img);
		lblImg = new JLabel(icon);

		idT = new JTextField("");
		pwdT = new JPasswordField();
		pwdT.setText("");

		btnLogin = new JButton("�α���");
		btnLogin.setForeground(Color.WHITE);
		btnLogin.setBackground(KAKAO_BROWN);

		img = Toolkit.getDefaultToolkit().getImage("image/�ݱ�ǥ��.png");
		icon = new ImageIcon(img);
		btnExit = new JButton(icon);
		btnExit.setBounds(425, 0, 25, 25);
		signButton = new JButton("ȸ������");
//		Autologin = new JCheckBox("�ڵ��α���");
//		Autologin.setOpaque(false);

		signButton = new JButton("ȸ������");
		signButton.setBackground(KAKAO_YELLOW);
		signButton.setFocusPainted(false);
		signButton.setForeground(Color.BLACK);
		signButton.setBorderPainted(false); // ��ư �׵θ� ����

	}

	public void closeLogin() {
		this.setVisible(false);
	}

	public void addComponent() { // ù��° ȭ�� ������Ʈ ����

		lblImg.setBounds(155, 115, 140, 140); // �̹���(������) ��ġ����
		pnlFirst.add(lblImg);

		JPanel pnlLogin = new JPanel(new GridLayout(5, 1, 5, 0));
		pnlLogin.setBackground(KAKAO_YELLOW);
		pnlLogin.add(idT);
		pnlLogin.add(pwdT);
		pnlLogin.add(btnLogin);
//		pnlLogin.add(new JPanel().add(Autologin));
		pnlLogin.add(signButton);
		pnlLogin.setSize(290, 200);
		pnlLogin.setLocation(80, 300);
		pnlFirst.add(pnlLogin);
		pnlFirst.add(btnExit);

		add(pnlFirst); // �������� ����ִ� panel

	}

	public void addListener() {
		// ������ �ݱ��ư �̺�Ʈ ó��
		btnExit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		// �α��� ��ư �̺�Ʈ ó��
		btnLogin.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// ������
				String id = idT.getText().trim();
				String pwd = pwdT.getText().trim();

				// DB
				String name = dao.loginMember(id, pwd);
				boolean check = false;
				if (name != null) {
					check = true;
				}

				if (id.length() == 0 || pwd.length() == 0) {
					JOptionPane.showMessageDialog(null, "�α��� ������ �Է��ϼ���");
					return;
				} else if (name == null) {
					JOptionPane.showMessageDialog(null, "���̵� Ȥ�� ��й�ȣ�� Ʋ�Ƚ��ϴ�");
					return;
				} else if (check) {
					// JOptionPane.showMessageDialog(null, "�α��� ����");
					closeLogin();

					new Inventory_ControlForm().setVisible(true);
					aa = 1;
					// ����� ������ ���� DB�� ���ο� JFrame�� ������ ��ߵ���������
				}
			}

		});

		// ȸ������ â ����
		signButton.addActionListener(new ActionListener() { // ������ �ݱ��ư �̺�Ʈ ó��
			@Override
			public void actionPerformed(ActionEvent e) {
				new Sign();
			}
		});

		// ȭ�鿡 ���콺 �̺�Ʈ ó���� ���� �巡�� �̵��� �����Ѵ�.
		addMouseListener(frameDragListener);
		addMouseMotionListener(frameDragListener);

		
		
	}

	// frameDragListener���� ���콺 �̺�Ʈ
	public static class FrameDragListener extends MouseAdapter {

		private final JFrame frame;
		private Point mouseDownCompCoords = null;

		public FrameDragListener(JFrame frame) {
			this.frame = frame;
		}

		public void mouseReleased(MouseEvent e) {
			mouseDownCompCoords = null;
		}

		public void mousePressed(MouseEvent e) {
			mouseDownCompCoords = e.getPoint();
		}

		public void mouseDragged(MouseEvent e) {
			Point currCoords = e.getLocationOnScreen();
			frame.setLocation(currCoords.x - mouseDownCompCoords.x, currCoords.y - mouseDownCompCoords.y);
		}
	}
}