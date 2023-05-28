package com.example.lab4_app_20192258.Retrofit;

import com.example.lab4_app_20192258.entity.Employee;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface EmployeeRepository {
    @GET("/tutor/list")
    Call<List<Employee>> listarEmployees();
}
