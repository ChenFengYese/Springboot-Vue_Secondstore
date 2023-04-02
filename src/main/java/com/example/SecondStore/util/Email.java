//package com.example.SecondStore.util;
//
//import com.example.SecondStore.entity.PojoEmail;
//import org.apache.commons.mail.EmailException;
//import org.apache.commons.mail.HtmlEmail;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//@RestController
//@RequestMapping(value = "EmailVerification")
//public class Email {
//
//
//    public static String rand(){
//        StringBuilder str = new StringBuilder();
//        str.append((int) (Math.random() * 9 + 1));
//        for (int i = 0; i < 5; i++) {
//            str.append((int) (Math.random() * 10));
//        }
//        return str.toString();
//    }
//
//    @RequestMapping(value = "send")
//    public static String sendEmail(@RequestBody PojoEmail email){
//
//        HtmlEmail mailUtil = new HtmlEmail();
//        mailUtil.setHostName("pop.qq.com");
//        mailUtil.setAuthentication("emailvarification@qq.com", "cltooflnmhjwchde");
//        mailUtil.setCharset("utf-8");
//        String randomNumber = rand();
//        try {
//            mailUtil.addTo(email.getEmail());
//            mailUtil.setFrom("emailvarification@qq.com");
//            mailUtil.setSubject("邮箱验证码【"+randomNumber+"】");
//            mailUtil.setMsg("亲爱的用户：\n\n\n" +
//                    "您好！您正在进行邮箱验证，本次请求的验证码为：\n【" + randomNumber + "】\n"
//                    + "请在十分钟内使用，请勿提供给他人"
//                    + "\n\n\n\t\tBy TrumpeT" );
//            mailUtil.send();
//        } catch (EmailException e) {
//            System.out.println("验证码发送失败，请勿频繁提交请求");
//        }
//        return randomNumber;
//    }
//}
