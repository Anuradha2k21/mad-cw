package com.example.mad_cw.course;

import android.content.Context;
import android.content.Intent;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mad_cw.R;
import com.example.mad_cw.user.UserLogin;
import com.example.mad_cw.user.UserModel;

import java.util.ArrayList;

public class CourseAdapter extends RecyclerView.Adapter<CourseAdapter.MyViewHolder> {
    private Context context;
    private ArrayList<CourseModel> courseList;
    private boolean isLoggedIn;
    private UserModel userModel;

    public CourseAdapter(Context context, ArrayList<CourseModel> courseList, boolean isLoggedIn, UserModel userModel) {
        this.context = context;
        this.courseList = courseList;
        this.isLoggedIn = isLoggedIn;
        this.userModel = userModel;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.course_row, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CourseAdapter.MyViewHolder holder, int position) {
        CourseModel courseModel = courseList.get(position);

        holder.courseName.setText(String.valueOf(courseModel.getName()));
        holder.startDate.setText(String.valueOf(courseModel.getCourseStartDate()));
        holder.availableSeats.setText(String.valueOf(courseModel.getAvailableSeats()));
        holder.branches.setText(String.valueOf(courseModel.getBranch()));
        holder.concatenateRowData(courseModel.getAvailableSeats(), courseModel.getCourseStartDate(), courseModel.getBranch());


        holder.btnDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(context, "Details button clicked for course: " + courseModel.getName(), Toast.LENGTH_SHORT).show();
                // Create an Intent to navigate to the CourseDetailView activity
                Intent intent = new Intent(context, CourseDetailView.class);

                // Pass the course ID to the CourseDetailView activity
                intent.putExtra("course_id", courseModel.getId());

                intent.putExtra("user", userModel);

                // Start the CourseDetailView activity
                context.startActivity(intent);
            }


        });

        if (isLoggedIn) {
            // If user is logged in, enable the register button
            holder.btnRegister.setOnClickListener(v -> {
                Intent intent = new Intent(context, CourseRegistrationForm.class);
                intent.putExtra("course_id", courseModel.getId());
                intent.putExtra("course_name", courseModel.getName());
                intent.putExtra("user", userModel);
                context.startActivity(intent);
                //Toast.makeText(context, "Register button clicked for course: " + courseModel.getName(), Toast.LENGTH_SHORT).show();
            });
        }
        else {
            // If user is not logged in, show a message and redirect to login page when register button clicked
            holder.btnRegister.setOnClickListener(v -> {
                Toast.makeText(context, "Please sign up to register for this course", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(context, UserLogin.class);
                context.startActivity(intent);
            });
        }

    }
    @Override
    public int getItemCount() {
        return courseList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView courseName, startDate, availableSeats, branches, btnDetails, btnRegister;
        LinearLayout linearLayout;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            courseName = itemView.findViewById(R.id.txt_course_name);
            startDate = itemView.findViewById(R.id.txt_start_date);
            availableSeats = itemView.findViewById(R.id.txt_available_seats);
            branches = itemView.findViewById(R.id.txt_branches);
            btnDetails = itemView.findViewById(R.id.btn_view_details);
            btnRegister = itemView.findViewById(R.id.btn_register);
            linearLayout = itemView.findViewById(R.id.main_layout);
        }
        public void concatenateRowData(int seats, String startingDate, String branch) {
            String availableSeatsText = "Available Seats: " + seats;
            availableSeats.setText(availableSeatsText);

            String startDateText = "Start Date: " + startingDate;
            startDate.setText(startDateText);

            String availableBranches = "Available Branches: " + branch;
            branches.setText(availableBranches);
        }
    }
}
