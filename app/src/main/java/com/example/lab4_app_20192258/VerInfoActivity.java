package com.example.lab4_app_20192258;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.lab4_app_20192258.Retrofit.EmployeeRepository;
import com.example.lab4_app_20192258.databinding.ActivityVerInfoBinding;
import com.example.lab4_app_20192258.entity.Departments;
import com.example.lab4_app_20192258.entity.DepartmentsDTO;
import com.example.lab4_app_20192258.entity.Employee;
import com.example.lab4_app_20192258.entity.EmployeeDTO;
import com.example.lab4_app_20192258.entity.Job;
import com.example.lab4_app_20192258.entity.JobDTO;
import com.google.gson.Gson;

import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class VerInfoActivity extends AppCompatActivity {
    ActivityVerInfoBinding binding;
    public static String TAG = "msg-test";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityVerInfoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        EditText inputText = binding.editTextTextPersonName;
        if (inputText.getText().toString().isEmpty()) {
            // Si está vacío, desactivar el botón
            Button button = binding.button6;
            Button button2 = binding.button7;
            button.setEnabled(false);
            button2.setEnabled(false);
        }

        inputText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (inputText.getText().toString().isEmpty()) {
                    // Si está vacío, desactivar el botón
                    Button button = binding.button6;
                    Button button2 = binding.button7;
                    button.setEnabled(false);
                    button2.setEnabled(false);
                }else{
                    // Si no está vacío, activar el botón
                    Button button2 = binding.button7;
                    button2.setEnabled(true);
                    button2.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            EmployeeRepository employeeRepository = new Retrofit.Builder()
                                    .baseUrl("http://192.168.100.108:8080")
                                    .addConverterFactory(GsonConverterFactory.create())
                                    .build().create(EmployeeRepository.class);
                            //Log.d("msg-test", "GAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA"+inputText.getText().toString());
                            employeeRepository.obtenerEmployee(Integer.parseInt(inputText.getText().toString())).enqueue(new Callback<EmployeeDTO>() {
                                @Override
                                public void onResponse(Call<EmployeeDTO> call, Response<EmployeeDTO> response) {
                                    if (response.isSuccessful()) {
                                        Employee employee = response.body().getEmployee();
                                        binding.textView16.setText(employee.getFirstName());
                                        binding.textView17.setText(employee.getLastName());
                                        binding.textView18.setText(employee.getEmail());
                                        binding.textView19.setText(employee.getPhoneNumber());
                                        binding.textView21.setText(employee.getHireDate().toString());
                                        binding.textView22.setText(employee.getSalary().toString());

                                        if(employee.getManagerId() == null){
                                            binding.textView23.setText("No tiene");
                                        }else{
                                            employeeRepository.obtenerEmployee(employee.getManagerId()).enqueue(new Callback<EmployeeDTO>() {
                                                @Override
                                                public void onResponse(Call<EmployeeDTO> call, Response<EmployeeDTO> response) {
                                                    if (response.isSuccessful()){
                                                        Employee employee2 = response.body().getEmployee();
                                                        binding.textView23.setText(employee2.getFirstName()+" "+employee2.getLastName());
                                                    }
                                                }
                                                @Override
                                                public void onFailure(Call<EmployeeDTO> call, Throwable t) {
                                                    t.printStackTrace();
                                                }
                                            });
                                        }


                                        employeeRepository.obtenerJob(employee.getJobId()).enqueue(new Callback<JobDTO>() {
                                            @Override
                                            public void onResponse(Call<JobDTO> call, Response<JobDTO> response) {
                                                if (response.isSuccessful()){
                                                    Job job = response.body().getJob();
                                                    binding.textView20.setText(job.getJobTitle());
                                                }
                                            }

                                            @Override
                                            public void onFailure(Call<JobDTO> call, Throwable t) {
                                                t.printStackTrace();
                                            }
                                        });


                                        if(employee.getDepartmentId() == 10){
                                            binding.textView24.setText("Administration");
                                        }
                                        if(employee.getDepartmentId() == 20){
                                            binding.textView24.setText("Marketing");
                                        }
                                        if(employee.getDepartmentId() == 30){
                                            binding.textView24.setText("Purchasing");
                                        }
                                        if(employee.getDepartmentId() == 40){
                                            binding.textView24.setText("Human Resources");
                                        }
                                        if(employee.getDepartmentId() == 50){
                                            binding.textView24.setText("Shipping");
                                        }
                                        if(employee.getDepartmentId() == 60){
                                            binding.textView24.setText("IT");
                                        }
                                        if(employee.getDepartmentId() == 70){
                                            binding.textView24.setText("Public Relations");
                                        }
                                        if(employee.getDepartmentId() == 80){
                                            binding.textView24.setText("Sales");
                                        }
                                        if(employee.getDepartmentId() == 90){
                                            binding.textView24.setText("Executive");
                                        }
                                        if(employee.getDepartmentId() == 100){
                                            binding.textView24.setText("Finance");
                                        }
                                        if(employee.getDepartmentId() == 110){
                                            binding.textView24.setText("Accounting");
                                        }
                                        if(employee.getDepartmentId() == 120){
                                            binding.textView24.setText("Treasury");
                                        }
                                        if(employee.getDepartmentId() == 130){
                                            binding.textView24.setText("Corporate Tax");
                                        }
                                        if(employee.getDepartmentId() == 140){
                                            binding.textView24.setText("Control And Credit");
                                        }
                                        if(employee.getDepartmentId() == 150){
                                            binding.textView24.setText("Shareholder Services");
                                        }
                                        if(employee.getDepartmentId() == 160){
                                            binding.textView24.setText("Benefits");
                                        }
                                        if(employee.getDepartmentId() == 170){
                                            binding.textView24.setText("Manufacturing");
                                        }
                                        if(employee.getDepartmentId() == 180){
                                            binding.textView24.setText("Construction");
                                        }
                                        if(employee.getDepartmentId() == 190){
                                            binding.textView24.setText("Contracting");
                                        }
                                        if(employee.getDepartmentId() == 200){
                                            binding.textView24.setText("Operations");
                                        }
                                        if(employee.getDepartmentId() == 210){
                                            binding.textView24.setText("IT Support");
                                        }
                                        if(employee.getDepartmentId() == 220){
                                            binding.textView24.setText("NOC");
                                        }
                                        if(employee.getDepartmentId() == 230){
                                            binding.textView24.setText("IT Helpdesk");
                                        }
                                        if(employee.getDepartmentId() == 240){
                                            binding.textView24.setText("Government Sales");
                                        }
                                        if(employee.getDepartmentId() == 250){
                                            binding.textView24.setText("Retail Sales");
                                        }
                                        if(employee.getDepartmentId() == 260){
                                            binding.textView24.setText("Recruiting");
                                        }
                                        if(employee.getDepartmentId() == 270){
                                            binding.textView24.setText("Payroll");
                                        }



                                    } else {
                                        Log.d("msg-test", "error en la respuesta del webservice");
                                    }
                                }

                                @Override
                                public void onFailure(Call<EmployeeDTO> call, Throwable t) {
                                    t.printStackTrace();
                                }
                            });
                        }
                    });
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    public void guardarComoJson(Employee listaJobs, String id) {
        Gson gson = new Gson();
        String employee = gson.toJson(listaJobs);

        Log.d(TAG, employee);

        String fileName = "informacionDe"+id+".txt";

        try (FileOutputStream fileOutputStream = openFileOutput(fileName, Context.MODE_PRIVATE);
             FileWriter fileWriter = new FileWriter(fileOutputStream.getFD())) {

            fileWriter.write(employee);
            Log.d(TAG, "Guardado exitoso");

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}