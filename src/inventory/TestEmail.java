package inventory;

import java.util.Properties;
import java.util.Random;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import javax.mail.Authenticator;


public class TestEmail {

	 private String cert ="";
	 private String email;
            
	 
    public TestEmail(String email) {
       this.email = email;

        String host = "smtp.naver.com";
        String user = "rlawlsdnr903@naver.com"; // 네이버일 경우 네이버 계정, gmail경우 gmail 계정
         String password = "rlawlsdnr903903";   // 패스워드

         // SMTP 서버 정보를 설정한다.
         Properties props = new Properties();
         props.put("mail.smtp.host", host); 
         props.put("mail.smtp.port", 587); 
         props.put("mail.smtp.auth", "true");
         props.put("mail.smtp.ssl.protocols", "TLSv1.2"); 

         
         Session session = Session.getDefaultInstance(props, new Authenticator() {
             protected PasswordAuthentication getPasswordAuthentication() {
                 return new PasswordAuthentication(user, password);
             }
         });



         int count = 1;
         while(count <=6) { 
            int num = (int)(Math.random()*10);
            cert += num;
            count++;}
         System.out.println(cert);
            
         
             try {
                MimeMessage message = new MimeMessage(session);
                message.setFrom(new InternetAddress(user));
               
                //수신자메일주소
                message.addRecipient(Message.RecipientType.TO, new InternetAddress(email));
            
                // Subject
                message.setSubject("kakaoth 회원가입을 위한 인증번호입니다"); //메일 제목을 입력
            
                // Text
                message.setText(" 안녕하십니까 kakoth에 오신것을 진심으로 환영합니다 귀하의 이메일 인증번호는 " + cert + "입니다. 인증번호를 복사하여 입력해주세요.");    //메일 내용을 입력
            
                // send the message
                Transport.send(message); ////전송
                //System.out.println("rlawlsdnr03@naver.com");
                System.out.println("message sent successfully...");
         
            } catch (AddressException e) {
                e.printStackTrace();
            } catch (MessagingException e) {
                e.printStackTrace();
            }
             

    }
    
	public String getCert() {
		return cert;
	}
	
	

	


    
    
    
    
    
    











	
    
	
    
}



/*
//private static String crtfcNo;
//private static final String RandomStringUtils = null;

MemberDTO dto;
Sign sg = new Sign();
final String username = "rlawlsdnr903";         
final String password = "rlawlsdnr903903";
	
public SendEmail() {
	
	
	
	// 메일 인코딩
	final String bodyEncoding = "UTF-8"; //콘텐츠 인코딩
    
    String subject = "kakaoth 회원가입을 위한 인증번호입니다";
    String fromEmail = "rlawlsdnr903@naver.com";
    String fromUsername = "kakaoth MANAGER";
    
    Properties prop = new Properties(); 
    // 메일 옵션 설정
    prop.put("mail.smtp.host", "smtp.naver.com"); //이메일 발송을 처리해줄 STMP 서버
    prop.put("mail.smtp.port", 465);  //SMTP서버와 통신하는 포트를 말하는데 gmail일 경우 465, Naver의 경우 587
    prop.put("mail.smtp.auth", "true"); 
    prop.put("mail.smtp.ssl.enable", "true"); 
    //prop.put("mail.smtp.ssl.trust");
//    prop.put("mail.smtp.quitwait", "false"); //오류시, 지워보기
    
    // 수신 메일
    //String toemail = sg.inputEmail(); //sign창에서 이메일 작성된곳으로 써야한다
    String toemail = "dosldnjsss@gmail.com";

    // 메일 서버  인증 계정 설정
    Session session = Session.getDefaultInstance(prop, new javax.mail.Authenticator() {
        protected PasswordAuthentication getPasswordAuthentication() {
            return new PasswordAuthentication(username, password);
        }
    });
//
//    // 인증번호 난수 6자리 설정
//    // crtfcNo = RandomStringUtils.random(6);
//    StringBuffer temp = new StringBuffer();
//    Random rnd = new Random();
//	    for (int i = 0; i < 9; i++) {
//	        emailnumber = rnd.nextInt(6);
//	        switch (emailnumber) {
//	        case 0:
//	            // a-z
//	            temp.append((char) ((int) (rnd.nextInt(26)) + 97));
//	            break;
//	        case 1:
//	            // A-Z
//	            temp.append((char) ((int) (rnd.nextInt(26)) + 65));
//	            break;
//	        case 2:
//	            // 0-9
//	            temp.append((rnd.nextInt(10)));
//	            break;
//	        }
//	    }
	try {
		MimeMessage message = new MimeMessage(session);
		message.setFrom(new InternetAddress(username));
			
		// 메일 대상
		message.setRecipient(Message.RecipientType.TO, new InternetAddress(toemail));
    	// 메일 제목
    	message.setSubject("제목입력");
		// 메일 내용
		message.setText("안녕하십니까 kakoth에 오신것을 진심으로 환영합니다\n귀하의 이메일 인증번호는 ");
		// send the message
		Transport.send(message); //전송
		System.out.println("인증번호를 성공적으로 보냈습니다");
		
	    } catch (AddressException e) {
	        e.printStackTrace();
	    } catch (MessagingException e) {
	        e.printStackTrace();
	    }
}
}
*/
	
