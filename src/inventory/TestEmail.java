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
        String user = "rlawlsdnr903@naver.com"; // ���̹��� ��� ���̹� ����, gmail��� gmail ����
         String password = "rlawlsdnr903903";   // �н�����

         // SMTP ���� ������ �����Ѵ�.
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
               
                //�����ڸ����ּ�
                message.addRecipient(Message.RecipientType.TO, new InternetAddress(email));
            
                // Subject
                message.setSubject("kakaoth ȸ�������� ���� ������ȣ�Դϴ�"); //���� ������ �Է�
            
                // Text
                message.setText(" �ȳ��Ͻʴϱ� kakoth�� ���Ű��� �������� ȯ���մϴ� ������ �̸��� ������ȣ�� " + cert + "�Դϴ�. ������ȣ�� �����Ͽ� �Է����ּ���.");    //���� ������ �Է�
            
                // send the message
                Transport.send(message); ////����
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
	
	
	
	// ���� ���ڵ�
	final String bodyEncoding = "UTF-8"; //������ ���ڵ�
    
    String subject = "kakaoth ȸ�������� ���� ������ȣ�Դϴ�";
    String fromEmail = "rlawlsdnr903@naver.com";
    String fromUsername = "kakaoth MANAGER";
    
    Properties prop = new Properties(); 
    // ���� �ɼ� ����
    prop.put("mail.smtp.host", "smtp.naver.com"); //�̸��� �߼��� ó������ STMP ����
    prop.put("mail.smtp.port", 465);  //SMTP������ ����ϴ� ��Ʈ�� ���ϴµ� gmail�� ��� 465, Naver�� ��� 587
    prop.put("mail.smtp.auth", "true"); 
    prop.put("mail.smtp.ssl.enable", "true"); 
    //prop.put("mail.smtp.ssl.trust");
//    prop.put("mail.smtp.quitwait", "false"); //������, ��������
    
    // ���� ����
    //String toemail = sg.inputEmail(); //signâ���� �̸��� �ۼ��Ȱ����� ����Ѵ�
    String toemail = "dosldnjsss@gmail.com";

    // ���� ����  ���� ���� ����
    Session session = Session.getDefaultInstance(prop, new javax.mail.Authenticator() {
        protected PasswordAuthentication getPasswordAuthentication() {
            return new PasswordAuthentication(username, password);
        }
    });
//
//    // ������ȣ ���� 6�ڸ� ����
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
			
		// ���� ���
		message.setRecipient(Message.RecipientType.TO, new InternetAddress(toemail));
    	// ���� ����
    	message.setSubject("�����Է�");
		// ���� ����
		message.setText("�ȳ��Ͻʴϱ� kakoth�� ���Ű��� �������� ȯ���մϴ�\n������ �̸��� ������ȣ�� ");
		// send the message
		Transport.send(message); //����
		System.out.println("������ȣ�� ���������� ���½��ϴ�");
		
	    } catch (AddressException e) {
	        e.printStackTrace();
	    } catch (MessagingException e) {
	        e.printStackTrace();
	    }
}
}
*/
	
