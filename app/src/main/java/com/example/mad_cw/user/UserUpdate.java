package com.example.mad_cw.user;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import java.io.ByteArrayOutputStream;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.widget.EditText;
import android.widget.ImageButton;

import android.app.DatePickerDialog;
import android.widget.DatePicker;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.mad_cw.DatabaseHelper;
import com.example.mad_cw.R;
import com.example.mad_cw.course.CourseRecyclerView;

public class UserUpdate extends AppCompatActivity {
    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final int REQUEST_IMAGE_PICK = 2;

    private ImageView ivProfile;
    private EditText etPassword, etConfirmPassword, etEmail, etName, etTp, etAddress, etCity, etNIC, etDOB;
    private RadioGroup rgGender;
    private RadioButton rbMale, rbFemale, rbOther, rbGender;
    private ImageButton btnViewPassword;
    private Button btnUpdate;
    private String gender;
    private UserModel userModel;
    private DatabaseHelper databaseHelper;
    private byte[] imageBytes;
    private static final int PERMISSIONS_REQUEST_CODE = 1234;
    private static final String[] PERMISSIONS = {
            Manifest.permission.CAMERA,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_update);

        userModel = (UserModel) getIntent().getSerializableExtra("user");
        ivProfile = findViewById(R.id.iv_profile);
        ivProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage();
            }
        });
        if (!hasPermissions()) {
            requestPermissions();
        }
        etPassword = findViewById(R.id.et_pword);
        etConfirmPassword = findViewById(R.id.et_confirm_pword);
        btnViewPassword = findViewById(R.id.btn_view_password);

        etName = findViewById(R.id.et_name);
        etAddress = findViewById(R.id.et_address);
        etCity = findViewById(R.id.et_city);
        etEmail = findViewById(R.id.et_email);
        etTp = findViewById(R.id.et_tp);
        etNIC = findViewById(R.id.et_nic);
        etPassword = findViewById(R.id.et_pword);
        etConfirmPassword = findViewById(R.id.et_confirm_pword);
        etDOB = findViewById(R.id.et_dob);
        btnUpdate = findViewById(R.id.btn_update);
        rgGender = findViewById(R.id.radio_group_gender);
        rbMale = findViewById(R.id.rb_male);
        rbFemale = findViewById(R.id.rb_female);
        rbOther = findViewById(R.id.rb_other);

        rgGender.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // checkedId is the RadioButton selected
                rbGender = (RadioButton) findViewById(checkedId);
                gender = rbGender.getText().toString();
            }
        });

        etDOB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDOB();
            }
        });

        btnViewPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (etPassword.getTransformationMethod().equals(PasswordTransformationMethod.getInstance())) {
                    btnViewPassword.setImageResource(R.drawable.ic_view_password);
                    etPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    etConfirmPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                } else {
                    btnViewPassword.setImageResource(R.drawable.ic_hide_password);
                    etPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    etConfirmPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
            }
        });
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validate();
            }
        });
        loadCurrentData();
    }

    private void loadCurrentData() {
        etName.setText(userModel.getName());
        etAddress.setText(userModel.getAddress());
        etCity.setText(userModel.getCity());
        etEmail.setText(userModel.getEmail());
        etTp.setText(userModel.getTelephone());
        etNIC.setText(userModel.getNic());
        etPassword.setText(userModel.getPassword());
        etConfirmPassword.setText(userModel.getPassword());
        etDOB.setText(userModel.getDob());

        if (userModel.getGender().equals("Male")) {
            rgGender.check(R.id.rb_male);
        }
        else if (userModel.getGender().equals("Female")) {
            rgGender.check(R.id.rb_female);
        }
        else if (userModel.getGender().equals("Other")) {
            rgGender.check(R.id.rb_other);
        }

        imageBytes = userModel.getImageBytes();
        if (imageBytes != null) {
            Bitmap bitmap = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
            ivProfile.setImageBitmap(bitmap);
        }
    }
    private void convertImage() {
//        Convert ImageView to Bitmap
        Bitmap bitmap = ((BitmapDrawable) ivProfile.getDrawable()).getBitmap();

//        Convert Bitmap to byte array
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, bos);
        imageBytes = bos.toByteArray();
    }

    private void validate() {
        if(ivProfile.getDrawable() != null) {
            convertImage();
        }

        if (etName.getText().toString().isEmpty()) {
            etName.setError("Name is required");
            etName.requestFocus();
        }
        else if (etName.getText().toString().length() < 3 || etName.getText().toString().length() > 25){
            etName.setError("Invalid Name");
            etName.requestFocus();
        }
        else if (etAddress.getText().toString().isEmpty()) {
            etAddress.setError("Address is required");
            etAddress.requestFocus();
        }
        else if (etAddress.getText().toString().length() < 3 || etAddress.getText().toString().length() > 40){
            etAddress.setError("Invalid Address");
            etAddress.requestFocus();
        }
        else if (etCity.getText().toString().isEmpty()) {
            etCity.setError("City is required");
            etCity.requestFocus();
        }
        else if (etCity.getText().toString().length() < 3 || etCity.getText().toString().length() > 30) {
            etCity.setError("Invalid City");
            etCity.requestFocus();
        }
        else if (etDOB.getText().toString().isEmpty()) {
            Toast.makeText(UserUpdate.this, "Date of birth is required", Toast.LENGTH_SHORT).show();
            etDOB.requestFocus();
        }
        else if (etNIC.getText().toString().isEmpty()) {
            etNIC.setError("NIC is required");
            etNIC.requestFocus();
        }
        else if (etNIC.getText().toString().length() < 10 || etNIC.getText().toString().length() > 15) {
            etNIC.setError("Invalid NIC");
            etNIC.requestFocus();
        }
        else if (etEmail.getText().toString().isEmpty()) {
            etEmail.setError("Email is required");
            etEmail.requestFocus();
        }
        else if (etEmail.getText().toString().length() < 8 || etEmail.getText().toString().length() > 50) {
            etEmail.setError("Invalid Email");
            etEmail.requestFocus();
        }
        else if (gender == null) {
            Toast.makeText(UserUpdate.this, "Please select a gender", Toast.LENGTH_SHORT).show();
            rgGender.requestFocus();
        }
        else if (etTp.getText().toString().isEmpty()) {
            etTp.setError("Telephone number is required");
            etTp.requestFocus();
        }
        else if (etTp.getText().toString().length() < 9 || etTp.getText().toString().length() > 13) {
            etTp.setError("Invalid Telephone number");
            etTp.requestFocus();
        }
        else if (etPassword.getText().toString().isEmpty()) {
            etPassword.setError("Password is required");
            etPassword.requestFocus();
        }
        else if (etPassword.getText().toString().length() < 8 || etPassword.getText().toString().length() > 20) {
            etPassword.setError("Password should be 8-20 characters long");
            etPassword.requestFocus();
        }
        else if (etConfirmPassword.getText().toString().isEmpty()) {
            etConfirmPassword.setError("Confirmation Password is required");
            etConfirmPassword.requestFocus();
        }
        else if (!etPassword.getText().toString().equals(etConfirmPassword.getText().toString())) {
            etConfirmPassword.setError("Passwords do not match");
            etConfirmPassword.requestFocus();
        }
        else {
            processUpdate();
        }
    }

    private void getDOB() {
        final Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH);
        int mDay = c.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePickerDialog = new DatePickerDialog(UserUpdate.this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        Calendar selectedDate = Calendar.getInstance();
                        selectedDate.set(year, monthOfYear, dayOfMonth);
                        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
                        String formattedDate = sdf.format(selectedDate.getTime());
                        etDOB.setText(formattedDate);
                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.show();
    }

    private void processUpdate() {
        userModel.setName(etName.getText().toString());
        userModel.setAddress(etAddress.getText().toString());
        userModel.setCity(etCity.getText().toString());
        userModel.setDob(etDOB.getText().toString());
        userModel.setNic(etNIC.getText().toString());
        userModel.setEmail(etEmail.getText().toString());
        userModel.setGender(gender);
        userModel.setTelephone(etTp.getText().toString());
        userModel.setPassword(etPassword.getText().toString());
        userModel.setImageBytes(imageBytes);

        databaseHelper = new DatabaseHelper(this);
        UserModel updatedModel;
        try {
            updatedModel = databaseHelper.updateUser(userModel);
        }
        catch (SQLException e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
            throw new RuntimeException(e);
        }

        if(updatedModel != null){
            Toast.makeText(this, "Account updated successfully", Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(UserUpdate.this, CourseRecyclerView.class);
            intent.putExtra("user", updatedModel);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        }
        else {
            Toast.makeText(this, "Account update failed", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean hasPermissions() {
        for (String permission : PERMISSIONS) {
            if (ActivityCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }

    private void requestPermissions() {
        ActivityCompat.requestPermissions(this, PERMISSIONS, PERMISSIONS_REQUEST_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSIONS_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // permissions were granted
            } else {
                Toast.makeText(this, "Please grant the permissions necessary", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void selectImage() {
        List<String> optionsList = new ArrayList<>(Arrays.asList("Take Photo", "Choose from Gallery"));
        if (ivProfile.getDrawable() != null) {
            optionsList.add("Remove Photo");
        }
        optionsList.add("Cancel");

        CharSequence[] options = optionsList.toArray(new CharSequence[0]);

        AlertDialog.Builder builder = new AlertDialog.Builder(UserUpdate.this);
        builder.setTitle("Add a Profile Picture");
        builder.setItems(options, (dialog, item) -> {
            if (options[item].equals("Take Photo")) {
                Intent takePicture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (takePicture.resolveActivity(getPackageManager()) != null) {
                    startActivityForResult(takePicture, REQUEST_IMAGE_CAPTURE);
                }
            } else if (options[item].equals("Choose from Gallery")) {
                Intent pickPhoto = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(pickPhoto, REQUEST_IMAGE_PICK);
            } else if (options[item].equals("Remove Photo")) {
                ivProfile.setImageDrawable(null);
            } else if (options[item].equals("Cancel")) {
                dialog.dismiss();
            }
        });
        builder.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_IMAGE_CAPTURE) {
                Bundle extras = data.getExtras();
                Bitmap imageBitmap = (Bitmap) extras.get("data");
                ivProfile.setImageBitmap(imageBitmap);
            } else if (requestCode == REQUEST_IMAGE_PICK) {
                Uri selectedImage = data.getData();
                ivProfile.setImageURI(selectedImage);
            }
        }
    }
}