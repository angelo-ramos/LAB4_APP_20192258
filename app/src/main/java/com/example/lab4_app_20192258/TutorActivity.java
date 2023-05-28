package com.example.lab4_app_20192258;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.lab4_app_20192258.Retrofit.EmployeeRepository;
import com.example.lab4_app_20192258.databinding.ActivityMainBinding;
import com.example.lab4_app_20192258.databinding.ActivityTutorBinding;
import com.example.lab4_app_20192258.entity.Employee;
import com.google.gson.Gson;

import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class TutorActivity extends AppCompatActivity {

    ActivityTutorBinding binding;
    public static String TAG = "msg-test";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityTutorBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EmployeeRepository employeeRepository = new Retrofit.Builder()
                        .baseUrl("http://192.168.100.108:8080")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build().create(EmployeeRepository.class);
                Log.d("msg-test", "GAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
                employeeRepository.listarEmployees().enqueue(new Callback<List<Employee>>() {
                    @Override
                    public void onResponse(Call<List<Employee>> call, Response<List<Employee>> response) {
                        if (response.isSuccessful()) {
                            List<Employee> employeeList = response.body();
                            guardarComoJson(employeeList);
                            Toast.makeText(TutorActivity.this,"listaDeTrabajadores.txt en Storage Interno", Toast.LENGTH_SHORT).show();
                            /* for (Employee e : employeeList) { System.out.println("id: " + e.getFirstName() + " | body: " + e.getEmployeeId()); } */
                        } else {
                            Log.d("msg-test", "error en la respuesta del webservice");
                        }
                    }
                    @Override
                    public void onFailure(Call<List<Employee>> call, Throwable t) {
                        t.printStackTrace();
                    }
                });
            }
        });

        binding.button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TutorActivity.this, BuscarTrabajaActivity.class);
                startActivity(intent);
            }
        });

        binding.button5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TutorActivity.this, AsignarActivity.class);
                startActivity(intent);
            }
        });
    }

    public void guardarComoJson(List<Employee> listaJobs) {
        Gson gson = new Gson();
        String listaJobsAsJson = gson.toJson(listaJobs);

        Log.d(TAG, listaJobsAsJson);

        String fileName = "listaDeTrabajadores.txt";

        try (FileOutputStream fileOutputStream = openFileOutput(fileName, Context.MODE_PRIVATE);
             FileWriter fileWriter = new FileWriter(fileOutputStream.getFD())) {

            fileWriter.write(listaJobsAsJson);
            Log.d(TAG, "Guardado exitoso");

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}