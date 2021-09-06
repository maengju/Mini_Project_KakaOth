//회원가입창
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

public class Sign extends JFrame implements ActionListener { // implements 가 있으면, 생성자 밖에 추상메소드 생성
																// 없으면, 익명이너
	private JButton btnExit; // 뒤로가기 버튼
	private JLabel uplabel = new JLabel("아이디"); // KaKaoth을 나타내는 라벨

	private JLabel idlabel; // "아이디" 라벨
	private JTextField idText; // 아이디 적는 텍스트필드
	private JButton idButton; // 아이디 중복확인하는 버튼

	private JLabel pwlabel; // "비밀번호" 라벨
	private JTextField pwText; // 비밀번호 적는 텍스트필드
	private JTextField repwText; // 비밀번호를 재확인하는 텍스트필드
	// JPasswordField는 비밀번호표시를 자동으로 '*'로 표시해준다

	private JLabel namelabel; // "이름" 라벨
	private JTextField nameText; // 이름을 적는 텍스트필드

	private JLabel emaillabel; // "이메일" 라벨
	private JTextField emailText;// 이메일 적는 텍스트필드
	private JButton emailButton; // 이메일 인증번호 보내는 버튼

	private JLabel emailchecklabel; // "인증번호" 라벨
	private JTextField emailcheckText; // 인증번호를 적는 라벨
	private JButton emailcheckButton; // "번호확인" 버튼

	private JButton signCompleteButton; // 계정등록(회원가입) 버튼

	private boolean signidcheck = false;
	private boolean emailreadycheck = false;
	private TestEmail te;

	// 색상 생성
	public static final Color KAKAO_YELLOW = new Color(250, 225, 0);
	public static final Color KAKAO_BROWN = new Color(82, 55, 56);
	protected static final Object anchor = null;

	private MemberDTO dto;
	private MemberDAO dao;

	// 생성자
	public Sign() {
		dao = new MemberDAO();
		setUndecorated(true); // 타이틀바 제거
		FrameDragListener frameDragListener = new FrameDragListener(this);// 반드시 MouseEvent로 Drag 이동을 구현해줘야함
		addMouseListener(frameDragListener);
		addMouseMotionListener(frameDragListener); // 타이틀바 제거시, 프레임이 움직이지 않는 현상을 방지한다

		setLayout(null); // 레이아웃 해제

		/*
		 * 중요! 필드에 따로 생성하더라도, JLabel uplabel = new JLabel("KaKaoth"); 이런식의 변수 선언 생성 및
		 * 선언은 지역변수다(갈색 글자) uplabel = new JLabel("KaKaoth"); 이렇게 쓰면, 전역변수로 잡힌다(파란색 글자)
		 * 전역변수로 안잡히면, 필드에서 노란색밑줄(쓰이지못하고있음)이 잡힌다
		 */
		// 카카오 라벨
		uplabel = new JLabel("KaKaoth");
		uplabel.setFont(new Font("arial", Font.BOLD, 25));
		uplabel.setSize(150, 80);
		uplabel.setLocation(160, 25);
		// 아이디 라벨
		idlabel = new JLabel("아이디");
		idlabel.setFont(new Font("서울남산체", Font.ITALIC, 16));
		idlabel.setSize(150, 35);
		idlabel.setLocation(20, 105);
		// 아이디 텍스트필드
		idText = new JTextField(30);
		idText.setSize(360, 30);
		idText.setLocation(20, 140);
		// 비밀번호 라벨
		pwlabel = new JLabel("비밀번호");
		pwlabel.setFont(new Font("서울남산체", Font.ITALIC, 16));
		pwlabel.setSize(100, 50);
		pwlabel.setLocation(20, 175);
		// 비밀번호 텍스트필드
		pwText = new JTextField(30);
		pwText.setSize(360, 30);
		pwText.setLocation(20, 220);
		// 비밀번호 재입력 텍스트필드
		repwText = new JPasswordField(30);
		repwText.setSize(360, 30);
		repwText.setLocation(20, 255);
		// 이름 라벨
		namelabel = new JLabel("이름");
		namelabel.setFont(new Font("서울남산체", Font.ITALIC, 16));
		namelabel.setSize(100, 50);
		namelabel.setLocation(20, 280);
		// 이름 텍스트필드
		nameText = new JTextField(30);
		nameText.setSize(360, 30);
		nameText.setLocation(20, 320);
		// 이메일 라벨
		emaillabel = new JLabel("이메일");
		emaillabel.setFont(new Font("서울남산체", Font.ITALIC, 16));
		emaillabel.setSize(50, 35);
		emaillabel.setLocation(20, 350);
		// 이메일적는 텍스트필드
		emailText = new JTextField(30);
		emailText.setSize(360, 30);
		emailText.setLocation(20, 380);
		// 이메일인증번호 라벨
		emailchecklabel = new JLabel("인증번호");
		emailchecklabel.setFont(new Font("서울남산체", Font.ITALIC, 16));
		emailchecklabel.setSize(70, 35);
		emailchecklabel.setLocation(20, 425);
		// 이메일인증번호 텍스트필드
		emailcheckText = new JTextField(30);
		emailcheckText.setSize(360, 30);
		emailcheckText.setLocation(20, 460);

		// 뒤로가기 라벨
		Image img = Toolkit.getDefaultToolkit().getImage("image/back.png");
		Icon icon = new ImageIcon(img);
		btnExit = new JButton(icon);
		btnExit.setBounds(375, 0, 25, 25);

		// 아이디 중복확인 버튼
		idButton = new JButton("중복 확인");
		idButton.setBounds(230, 180, 150, 30);
		idButton.setFocusPainted(false);
		idButton.setForeground(Color.WHITE);
		idButton.setBackground(KAKAO_BROWN);
		idButton.setFont(new Font("서울남산체", Font.ITALIC, 16));

		// 인증이메일 전송 버튼
		emailButton = new JButton("이메일 전송");
		emailButton.setBounds(230, 420, 150, 30);
		emailButton.setFocusPainted(false);
		emailButton.setForeground(Color.WHITE);
		emailButton.setBackground(KAKAO_BROWN);
		emailButton.setFont(new Font("서울남산체", Font.ITALIC, 16));

		// 이메일 인증번호 확인하는 버튼
		emailcheckButton = new JButton("번호확인");
		emailcheckButton.setBounds(230, 500, 150, 30);
		emailcheckButton.setFocusPainted(false);
		emailcheckButton.setForeground(Color.WHITE);
		emailcheckButton.setBackground(KAKAO_BROWN);
		emailcheckButton.setFont(new Font("서울남산체", Font.ITALIC, 16));

		// 계정등록 버튼
		signCompleteButton = new JButton("계정등록");
		signCompleteButton.setBackground(KAKAO_YELLOW);
		signCompleteButton.setFont(new Font("서울남산체", Font.BOLD, 16));
		signCompleteButton.setFocusPainted(false);
		signCompleteButton.setForeground(Color.BLACK);
		signCompleteButton.setBounds(20, 575, 360, 50);
		signCompleteButton.setBorderPainted(false); // 버튼 테두리 제거

		// 추가
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

		// swing에서 배경화면 설정
		this.getContentPane().setBackground(Color.WHITE);
		// JFrame 환경설정
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
		if (e.getSource() == idButton) {// id버튼
			String id = idText.getText();
			MemberDAO dao = new MemberDAO();
			String idcheck = dao.idCheck(id);
			boolean check = true;

			if (idcheck != null) {
				check = false;
			}

			if (id.length() == 0) {
				JOptionPane.showMessageDialog(null, "아이디를 입력해주세요");
				return;
			} else if (check) {
				JOptionPane.showMessageDialog(null, "사용가능한 아이디 입니다");
				signidcheck = true;

			} else if (check != true) {
				JOptionPane.showMessageDialog(null, "이미 사용중인 아이디 입니다");
				return;
			}

		} else if (e.getSource() == emailButton) {
			System.out.println("이메일버튼클릭");
			String email = emailText.getText();

			if (email.equals("")) {
				JOptionPane.showMessageDialog(null, "이메일을 입력해주세요");

			} else {
				te = new TestEmail(email);
				emailreadycheck = true;
				JOptionPane.showMessageDialog(null, "인증번호가"+email+"로 전송되었습니다. 확인해주세요");
			}
		} else if (e.getSource() == emailcheckButton) {
			String emailcheck = emailcheckText.getText();

			if (emailcheck.equals(te.getCert())) {
				JOptionPane.showMessageDialog(null, "인증완료");

			}

		} else if (e.getSource() == signCompleteButton) {
			String id = idText.getText();
			@SuppressWarnings("deprecation")
			String pwd = pwText.getText();
			String name = nameText.getText();
			String email = emailText.getText();

			if (signidcheck != true) {
				JOptionPane.showMessageDialog(null, "id 중복확인해주세요");
				// 중복확인 다이로그 끝나도 회원가입시키는데 다시해야함

			} else if (pwd.length() == 0) {
				JOptionPane.showMessageDialog(null, "비밀번호를 입력해주세요");
				return;
			} else if (name.length() == 0) {
				JOptionPane.showMessageDialog(null, "이름을 입력해주세요");
				return;

			}else if(emailreadycheck != true) {
				JOptionPane.showMessageDialog(null, "이메일 인증을 끝내주세요");
				return;
			}else {
				JOptionPane.showMessageDialog(null, "회원가입 되었습니다");
				// 데이터
				

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
