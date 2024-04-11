package com.example.mad_cw.user;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.mad_cw.DatabaseHelper;
import com.example.mad_cw.R;

import java.sql.SQLException;
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
    private String randomCode;
    private long codeGenerationTime;
    UserModel userModel;
    DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_user_confirmation);

        Intent intent = getIntent();
        userModel = (UserModel) intent.getSerializableExtra("user");

        etCodeInput = findViewById(R.id.et_code_input);
        Button btnConfirm = findViewById(R.id.btn_confirm);

        // Generate a random number between 100000 and 999999 (inclusive)
        Random random = new Random();
        int randomInt = random.nextInt(900000) + 100000;
        randomCode = String.valueOf(randomInt);
        codeGenerationTime = new Date().getTime();

        sendEmail();

        btnConfirm.setOnClickListener(v -> {
            try {
                onClickConfirm();
            }
            catch (Exception e){
                e.printStackTrace();
                Toast.makeText(this, "There's an error processing your request " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void sendEmail() {
        try {
            String stringSenderEmail = "";
            String stringReceiverEmail = userModel.getEmail();
            String stringPasswordSenderEmail = "";

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
                        runOnUiThread(new Runnable() {
                            public void run() {
                                Toast.makeText(UserConfirmation.this,
                                        "Confirmation code sent to your email",
                                        Toast.LENGTH_LONG).show();
                            }
                        });
                    } catch (MessagingException e) {
                        runOnUiThread(new Runnable() {
                            public void run() {
                                Toast.makeText(UserConfirmation.this,
                                        "There's an error processing your request " + e.getMessage(),
                                        Toast.LENGTH_SHORT).show();
                            }
                        });
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

    private void onClickConfirm() throws SQLException {
        long currentTime = new Date().getTime();
        long differenceInMinutes = (currentTime - codeGenerationTime) / (1000 * 60);

        if (etCodeInput.getText().toString().length() != 6 || !etCodeInput.getText().toString().equals(randomCode) || etCodeInput.getText().toString().matches(".*\\D.*")) {
            etCodeInput.setError("Invalid confirmation code");
            etCodeInput.requestFocus();
        } else if (etCodeInput.getText().toString().equals(randomCode) && differenceInMinutes > 10) {
            Toast.makeText(this, "The code has expired. Please request a new code", Toast.LENGTH_SHORT).show();
        } else if (etCodeInput.getText().toString().equals(randomCode) && differenceInMinutes < 10) {


            databaseHelper = new DatabaseHelper(this);
            boolean result = databaseHelper.addUser(userModel);

            if (result) {
                Intent intent1 = new Intent(UserConfirmation.this, UserLogin.class);
                startActivity(intent1);

                Toast.makeText(this, "Your account created. Please sign in", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Failed to create your account", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "Unknown error", Toast.LENGTH_SHORT).show();
        }
    }
}