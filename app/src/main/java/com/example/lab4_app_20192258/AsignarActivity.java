package com.example.lab4_app_20192258;

import static android.Manifest.permission.POST_NOTIFICATIONS;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.lab4_app_20192258.Retrofit.EmployeeRepository;
import com.example.lab4_app_20192258.databinding.ActivityAsignarBinding;
import com.example.lab4_app_20192258.databinding.ActivityTutorBinding;
import com.example.lab4_app_20192258.entity.Employee;
import com.example.lab4_app_20192258.entity.EmployeeDTO;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AsignarActivity extends AppCompatActivity {

    ActivityAsignarBinding binding;
    public static String TAG = "msg-test";
    String channel3Id = "canal3";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAsignarBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        EditText inputText1 = binding.editTextTextPersonName; //Tutor
        EditText inputText2 = binding.editTextTextPersonName3; //Empleado
        Button button = findViewById(R.id.button6);

        if (inputText1.getText().toString().isEmpty() || inputText2.getText().toString().isEmpty()) {
            button.setEnabled(false);
        }
        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (!inputText1.getText().toString().isEmpty() && !inputText2.getText().toString().isEmpty()) {
                    button.setEnabled(true);
                    button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            EmployeeRepository employeeRepository = new Retrofit.Builder()
                                    .baseUrl("http://192.168.100.108:8080")
                                    .addConverterFactory(GsonConverterFactory.create())
                                    .build().create(EmployeeRepository.class);
                            //Log.d("msg-test", "GAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA"+inputText.getText().toString());
                            employeeRepository.obtenerEmployee(Integer.parseInt(inputText2.getText().toString())).enqueue(new Callback<EmployeeDTO>() {
                                @Override
                                public void onResponse(Call<EmployeeDTO> call, Response<EmployeeDTO> response) {
                                    if (response.isSuccessful()) {
                                        Employee employee = response.body().getEmployee();

                                        if(employee != null){
                                            if(employee.getManagerId() != null){
                                                if(employee.getManagerId() == Integer.parseInt(inputText1.getText().toString())){
                                                    if(employee.getMeeting() == 1){
                                                        //Notificacion: El trabajador ya tiene una cita asignada. Elija otro trabajador
                                                        Toast.makeText(getApplicationContext(),"El trabajador ya tiene una cita asignada. Elija otro trabajador", Toast.LENGTH_SHORT).show();


                                                    }else {
                                                        //Notificacion: Asignación del trabajador correcta
                                                        Toast.makeText(getApplicationContext(),"Notificacion: Asignación del trabajador correcta", Toast.LENGTH_SHORT).show();
                                                    }
                                                }else{
                                                    //Mensaje: No es manager del empleado
                                                    Toast.makeText(getApplicationContext(),"No es manager del empleado", Toast.LENGTH_SHORT).show();
                                                }
                                            }else {
                                                //Mensaje: No tiene manager
                                                Toast.makeText(getApplicationContext(),"No tiene manager", Toast.LENGTH_SHORT).show();
                                            }
                                        }else{
                                            Toast.makeText(getApplicationContext(),"Algunos de los ID no existe", Toast.LENGTH_SHORT).show();
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
                } else {
                    // Si alguno de los campos está vacío, desactivar el botón
                    button.setEnabled(false);
                }
            }
        };
        inputText1.addTextChangedListener(textWatcher);
        inputText2.addTextChangedListener(textWatcher);
    }


    public void createNotificationChannel() {
        //android.os.Build.VERSION_CODES.O == 26
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(channel3Id,
                    "Canal 3 notificaciones",
                    NotificationManager.IMPORTANCE_HIGH);
            channel.setDescription("Este es para los mensajes nuevos");

            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);

            /*
            NotificationChannel channel2 = new NotificationChannel(channel2Id,
                    "Canal 1 notificaciones",
                    NotificationManager.IMPORTANCE_HIGH);
            channel.setDescription("Este es para los mensajes que no llegaron a enviarse");
            notificationManager.createNotificationChannel(channel2);
            */

            askPermission();

        }
    }

    public void askPermission(){
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU &&
                ActivityCompat.checkSelfPermission(this, POST_NOTIFICATIONS) == PackageManager.PERMISSION_DENIED) {

            ActivityCompat.requestPermissions(AsignarActivity.this, new String[]{POST_NOTIFICATIONS}, 101);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == 101){
            Log.d(TAG,"permiso concedido");
        }else{
            Log.d(TAG,"permiso denegado");
        }
    }
}