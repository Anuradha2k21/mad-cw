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

import java.util.ArrayList;

public class CourseAdapter extends RecyclerView.Adapter<CourseAdapter.MyViewHolder> {
    private Context context;
    private ArrayList<CourseModel> courseList;

    public CourseAdapter(Context context, ArrayList<CourseModel> courseList) {
        this.context = context;
        this.courseList = courseList;
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

        holder.btnDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(context, "Details button clicked for course: " + courseModel.getName(), Toast.LENGTH_SHORT).show();
                // Create an Intent to navigate to the CourseDetailView activity
                Intent intent = new Intent(context, CourseDetailView.class);

                // Pass the course ID to the CourseDetailView activity
                intent.putExtra("course_id", courseModel.getId());

                // Start the CourseDetailView activity
                context.startActivity(intent);
            }


        });

        holder.btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "Register button clicked for course: " + courseModel.getName(), Toast.LENGTH_SHORT).show();
            }
        });
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
    }
}
