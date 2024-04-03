package com.example.mad_cw.user;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.mad_cw.R;

import java.util.Date;
import java.util.Properties;
import java.util.Random;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class UserConfirmation extends AppCompatActivity {
    private EditText etCodeInput;
    private int randomCode;
    private long codeGenerationTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_user_confirmation);

//        Intent intent = getIntent();
        etCodeInput = findViewById(R.id.et_code_input);
        Button btnConfirm = findViewById(R.id.btn_confirm);

        // Generate a random number between 100000 and 999999 (inclusive)
        Random random = new Random();
        randomCode = random.nextInt(900000) + 100000;
        codeGenerationTime = new Date().getTime();

        sendEmail();

        btnConfirm.setOnClickListener(v -> {
            onClickConfirm();
        });
    }

    private void sendEmail() {
        try {
            String stringSenderEmail = "madcw2024@gmail.com";
            String stringReceiverEmail = "anuradhasanjaya2024@gmail.com";
            String stringPasswordSenderEmail = "pcup lkcy oboe zhoa";

            String stringHost = "smtp.gmail.com";

            Properties properties = System.getProperties();

            properties.put("mail.smtp.host", stringHost);
            properties.put("mail.smtp.port", "465");
            properties.put("mail.smtp.ssl.enable", "true");
            properties.put("mail.smtp.auth", "true");

            Session session = Session.getInstance(properties, new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(stringSenderEmail, stringPasswordSenderEmail);
                }
            });

            MimeMessage mimeMessage = new MimeMessage(session);
            mimeMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(stringReceiverEmail));

            mimeMessage.setSubject("Your confirmation code");
            mimeMessage.setText("Dear user, \n\nThank you for registering in our application. " +
                    "\nYour confirmation code: " + randomCode + "\n\nThank you!");

            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Transport.send(mimeMessage);
                    } catch (MessagingException e) {
                        Toast.makeText(UserConfirmation.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                    }
                }
            });
            thread.start();

        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "There's an error processing your request " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private void onClickConfirm() {
        if(etCodeInput.getText().toString().matches(".*\\D.*")){
            etCodeInput.setError("Only numbers allowed");
            etCodeInput.requestFocus();
        }
        else {
            int inputCode = Integer.parseInt(etCodeInput.getText().toString());
            long currentTime = new Date().getTime();
            long differenceInMinutes = (currentTime - codeGenerationTime) / (1000 * 60);

            if (Integer.toString(inputCode).length() != 6 || inputCode != randomCode) {
                etCodeInput.setError("Invalid confirmation code");
                etCodeInput.requestFocus();
            } else if (inputCode == randomCode && differenceInMinutes > 10) {
                Toast.makeText(this, "The code has expired. Please request a new code", Toast.LENGTH_SHORT).show();
            } else if (inputCode == randomCode && differenceInMinutes < 10) {
                Intent intent1 = new Intent(UserConfirmation.this, UserLogin.class);
                startActivity(intent1);
                Toast.makeText(this, "Your account created. Please sign in", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Unknown error", Toast.LENGTH_SHORT).show();
            }
        }
    }
}