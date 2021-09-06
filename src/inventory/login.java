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

	private JPanel pnlFirst; // 전체를 잡고있는
	// 첫번째 화면의 컴포넌트
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

		setUndecorated(true); // 윈도우창의 상단메뉴 해제시켜 카카오톡의 테마를 주기위해 해제시킨다
		frameDragListener = new FrameDragListener(this); // 반드시 MouseEvent로 Drag 이동을 구현해줘야함

		createComponent();
		addComponent();
		addListener();

		setSize(450, 740);

		setVisible(true);

		setResizable(false);

		setLocationRelativeTo(null);

	}

	public void createComponent() { // 첫번째 화면 컴포넌트 생성

		pnlFirst = new JPanel(null); // 레이아웃 해제
		pnlFirst.setBackground(KAKAO_YELLOW);

		Image img = Toolkit.getDefaultToolkit().getImage("image/카카오아이콘1.png");
		Icon icon = new ImageIcon(img);
		lblImg = new JLabel(icon);

		idT = new JTextField("");
		pwdT = new JPasswordField();
		pwdT.setText("");

		btnLogin = new JButton("로그인");
		btnLogin.setForeground(Color.WHITE);
		btnLogin.setBackground(KAKAO_BROWN);

		img = Toolkit.getDefaultToolkit().getImage("image/닫기표시.png");
		icon = new ImageIcon(img);
		btnExit = new JButton(icon);
		btnExit.setBounds(425, 0, 25, 25);
		signButton = new JButton("회원가입");
//		Autologin = new JCheckBox("자동로그인");
//		Autologin.setOpaque(false);

		signButton = new JButton("회원가입");
		signButton.setBackground(KAKAO_YELLOW);
		signButton.setFocusPainted(false);
		signButton.setForeground(Color.BLACK);
		signButton.setBorderPainted(false); // 버튼 테두리 제거

	}

	public void closeLogin() {
		this.setVisible(false);
	}

	public void addComponent() { // 첫번째 화면 컴포넌트 부착

		lblImg.setBounds(155, 115, 140, 140); // 이미지(아이콘) 위치조정
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

		add(pnlFirst); // 프레임을 잡고있는 panel

	}

	public void addListener() {
		// 윈도우 닫기버튼 이벤트 처리
		btnExit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		// 로그인 버튼 이벤트 처리
		btnLogin.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// 데이터
				String id = idT.getText().trim();
				String pwd = pwdT.getText().trim();

				// DB
				String name = dao.loginMember(id, pwd);
				boolean check = false;
				if (name != null) {
					check = true;
				}

				if (id.length() == 0 || pwd.length() == 0) {
					JOptionPane.showMessageDialog(null, "로그인 정보를 입력하세요");
					return;
				} else if (name == null) {
					JOptionPane.showMessageDialog(null, "아이디 혹은 비밀번호가 틀렸습니다");
					return;
				} else if (check) {
					// JOptionPane.showMessageDialog(null, "로그인 성공");
					closeLogin();

					new Inventory_ControlForm().setVisible(true);
					aa = 1;
					// 사용자 정보에 대한 DB를 새로운 JFrame에 가져다 줘야되지않을까
				}
			}

		});

		// 회원가입 창 생성
		signButton.addActionListener(new ActionListener() { // 윈도우 닫기버튼 이벤트 처리
			@Override
			public void actionPerformed(ActionEvent e) {
				new Sign();
			}
		});

		// 화면에 마우스 이벤트 처리를 통해 드래그 이동을 구현한다.
		addMouseListener(frameDragListener);
		addMouseMotionListener(frameDragListener);

		
		
	}

	// frameDragListener관련 마우스 이벤트
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