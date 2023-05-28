package com.example.lab4_app_20192258.Retrofit;

import com.example.lab4_app_20192258.entity.Employee;
import com.example.lab4_app_20192258.entity.EmployeeDTO;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface EmployeeRepository {
    @GET("/tutor/list")
    Call<List<Employee>> listarEmployees();

    @GET("/tutor/infoEmployee")
    Call<EmployeeDTO> obtenerEmployee(@Query("id") Integer id);
}
