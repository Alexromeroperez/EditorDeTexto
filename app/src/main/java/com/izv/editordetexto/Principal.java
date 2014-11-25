package com.izv.editordetexto;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;


public class Principal extends Activity {

    private EditText texto;
    private String ruta;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);
        texto = (EditText) findViewById(R.id.etTexto);

        Intent intent=getIntent();
        Uri data = intent.getData();
        ruta = data.getPath();

            File f=new File(ruta);
            try {
               BufferedReader br = new BufferedReader(new FileReader(f));
                String linea;
                StringBuffer todo =new StringBuffer();

                while ((linea = br.readLine()) != null) {
                    todo.append(linea);
                    todo.append("\n");
                }
                br.close();
                texto.setText(todo);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        
    }

    public void guardar(View v){
        File f=new File(ruta);
        texto=(EditText)findViewById(R.id.etTexto);
       if(espacioSuficiente(f)){
        try {
            FileWriter fw= new FileWriter(f);
                fw.write(texto.getText().toString());
                fw.flush();
                fw.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
       }else{
       Toast.makeText(this,"No tienes espacio en la memoria",Toast.LENGTH_SHORT).show();
       }
        setResult(0);
        finish();
    }

    public boolean espacioSuficiente(File f){
        double eTotal,eDisponible;
        eTotal=f.getTotalSpace();
        eDisponible=f.getFreeSpace();
        double porcentaje=(eDisponible/eTotal)*100;
        return porcentaje<90;
    }
}
