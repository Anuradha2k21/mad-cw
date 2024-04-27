package com.example.mad_cw.course;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import com.example.mad_cw.R;

import java.util.Locale;

public class CourseConfirmation extends AppCompatActivity {

    public static final String COURSE_NAME = "COURSE_NAME";
    public static final String BRANCH = "BRANCH";
    public static final String NAME = "NAME";
    public static final String NIC = "NIC";
    public static final String EMAIL = "EMAIL";
    public static final String CONTACT_NO = "CONTACT_NO";
    public static final String ADDRESS = "ADDRESS";
    public static final String DISCOUNT_AMOUNT = "DISCOUNT_AMOUNT";
    public static final String COURSE_FEE = "COURSE_FEE";
    public static final String FINAL_TOTAL = "FINAL_TOTAL";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_confirmation);
        Intent intent = getIntent();

        TextView courseNameView = (TextView) findViewById(R.id.text_view_course_name);
        String courseNameText = intent.getStringExtra(COURSE_NAME);
        courseNameView.setText(courseNameText);

        TextView branchView = (TextView) findViewById(R.id.text_view_branch);
        String branchText = intent.getStringExtra(BRANCH);
        branchView.setText(branchText);

        TextView fullNameView = (TextView) findViewById(R.id.text_view_full_name);
        String fullNameText = intent.getStringExtra(NAME);
        fullNameView.setText(fullNameText);

        TextView nicView = (TextView) findViewById(R.id.text_view_NIC);
        String nicText = intent.getStringExtra(NIC);
        nicView.setText(nicText);

        TextView emailView = (TextView) findViewById(R.id.text_view_email);
        String emailText = intent.getStringExtra(EMAIL);
        emailView.setText(emailText);

        TextView tpView = (TextView) findViewById(R.id.text_view_tp);
        String tpText = intent.getStringExtra(CONTACT_NO);
        tpView.setText(tpText);

        TextView addressView = (TextView) findViewById(R.id.text_view_address);
        String addressText = intent.getStringExtra(ADDRESS);
        addressView.setText(addressText);

        TextView discountView = (TextView) findViewById(R.id.text_view_discount);
        String discountText = (String.valueOf(intent.getDoubleExtra(DISCOUNT_AMOUNT, 0)));
        discountView.setText(discountText);

        TextView courseFeeView = (TextView) findViewById(R.id.text_view_course_fee);
        String courseFeeText = (String.format(Locale.getDefault(), "%.2f", intent.getDoubleExtra(COURSE_FEE,0)));
        courseFeeView.setText(courseFeeText);

        TextView courseFinalTotalView = (TextView) findViewById(R.id.text_view_total_amount);
        String courseFinalTotalText = (String.format(Locale.getDefault(), "%.2f", intent.getDoubleExtra(FINAL_TOTAL,0)));
        courseFinalTotalView.setText(courseFinalTotalText);

        Button btn_ok = findViewById(R.id.btn_ok);
        btn_ok.setOnClickListener(v -> {

        });
    }
}
