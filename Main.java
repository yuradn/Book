package com.test.yura.jul31;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import android.content.Intent;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class Main extends ActionBarActivity implements View.OnClickListener{

    Person pp = new Person();
    Button show,email,load, camera, toDB, toPerson;
    EditText FName, LName, BD, date, age;
    String nameLog = "main";

    private static final int REQ_Load = 1; // Код запроса на загрузку изображения
    private static final int REQ_Camera = 2; // Код запроса на камеру

    private ImageView image;

    DBHelper dbHelper = new DBHelper(this); // Класс который создает подключение к базе данных

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.d("Main:","Start");
        setContentView(R.layout.myscreen);

        //Определяем и подключаем кнопочку View
        show = (Button) findViewById(R.id.View);
        show.setOnClickListener(this);

        //Определяем и подключаем кнопочку отправки в базу данных
        toDB = (Button) findViewById(R.id.toDB);
        toDB.setOnClickListener(this);

        //Определяем и подключаем кнопочку запроса из базы данных
        toPerson = (Button) findViewById(R.id.toPerson);
        toPerson.setOnClickListener(this);

        //Определяем и подключаем кнопу загрузки изображения
        load = (Button) findViewById(R.id.load);
        load.setOnClickListener(this);

        //Определяем поле для выводя изображения
        image = (ImageView) findViewById(R.id.imageView);

        //Определяем кнопку для включения камеры
        camera = (Button) findViewById(R.id.camera);
        camera.setOnClickListener(this);

        date = (EditText) findViewById(R.id.Bday);
        age = (EditText) findViewById(R.id.Year);

        email = (Button) findViewById(R.id.toEmail);
        email.setOnClickListener(this);

        FName = (EditText) findViewById(R.id.FName);
        //FName.setText(pp.FirstName);
        LName = (EditText) findViewById(R.id.LName);
        //LName.setText(pp.LastName);
        BD = (EditText) findViewById(R.id.Bday);

        date.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count)
            {
                if(IsDate(date.getText().toString()))
                {
                    age.setText(calcDate());
                }
            }
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void afterTextChanged(Editable s) {      }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        Toast.makeText(this, "Keypressed!",Toast.LENGTH_SHORT).show();
        switch (v.getId()) {
        case R.id.View:
            pp.setFirstName(FName.getText().toString());
            pp.setLastName(LName.getText().toString());
            //pp.DataDB = (Date) BD.getText().toString();
            Intent intent = new Intent(Main.this, Show.class);
            intent.putExtra("firstName",pp.getFirstName());
            intent.putExtra("lastName",pp.getLastName());
            //intent.putExtra("lname",pp.FirstName);
            startActivity(intent);
            //Log.e("exitShow", "Выход из Show! ");
            break;
        case R.id.toEmail:
            MailSenderClass mail = new MailSenderClass("androidhead2038","android2038");
            try {
                mail.sendMail("yura", "yuradn@mail.ru","yuradn@mail.ru","yuradn@mail.ru","");
            } catch (Exception e) {
                e.printStackTrace();
            }
            break;
        case R.id.load:
            Log.d("load", "Load Image to screen");
            Intent i = new Intent(Intent.ACTION_PICK);
            i.setType("image/*");
            startActivityForResult(i, REQ_Load);
            break;
        case R.id.camera:
            Log.d("Camera","Start camera");
            Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(cameraIntent, REQ_Camera);
            break;
        case R.id.toDB:{
            //создаем объект для данных
            ContentValues cv = new ContentValues();

            // Получаем данные из полей ввода
            String firstName = FName.getText().toString();
            String lastName = LName.getText().toString();

            //Подключаемся к БД
            SQLiteDatabase db = dbHelper.getWritableDatabase();


            Log.d("DB", "Insert Person to Database");
            //Подготовим данные для вставки в виде пар: наименование столбца-значение
            cv.put("FirstName", firstName);
            cv.put("LastName", lastName);
            //Вставляем запись и получаем ее ID
            long rowId = db.insert("Person", null, cv);
            Log.d("DB", "row inserted, ID=" + rowId);
            //закрываем подключение к БД
            dbHelper.close();}
            break;
        case R.id.toPerson:{
            //создаем объект для данных
            ContentValues cv = new ContentValues();
            //Подключаемся к БД
            SQLiteDatabase db = dbHelper.getWritableDatabase();

            nameLog="toPerson";
            Log.d(nameLog, "--- Rows in mytable: ---");
            // делаем запрос всех данных из таблицы mytable, получаем Cursor
            Cursor c = db.query("Person", null, null, null, null, null, null);

            String FN="";
            String LN="";
            while (c.moveToNext()) {
                FN=c.getString(c.getColumnIndex("FirstName"));
                LN=c.getString(c.getColumnIndex("LastName"));
            }

            //Выводим переменные из базы на экран
            FName.setText(FN);
            LName.setText(LN);

            // ставим позицию курсора на первую строку выборки
            // если в выборке нет строк, вернется false
            if (c.moveToFirst()) {

                // определяем номера столбцов по имени в выборке
                int idColIndex = c.getColumnIndex("id");
                int FNameColIndex = c.getColumnIndex("FirstName");
                int LNameColIndex = c.getColumnIndex("LastName");

                do {
                    // получаем значения по номерам столбцов и пишем все в лог
                    Log.d(nameLog,
                            "ID = " + c.getInt(idColIndex) +
                                    ", Fname = " + c.getString(FNameColIndex) +
                                    ", Lname = " + c.getString(LNameColIndex));
                    // переход на следующую строку
                    // а если следующей нет (текущая - последняя), то false - выходим из цикла
                } while (c.moveToNext());
            } else
                Log.d(nameLog, "0 rows");
            c.close();
            dbHelper.close();
                                }
            break;
//            case R.id.btnClear:
//                Log.d(LOG_TAG, "--- Clear mytable: ---");
//                // удаляем все записи
//                int clearCount = db.delete("mytable", null, null);
//                Log.d(LOG_TAG, "deleted rows count = " + clearCount);
//                break;
        }
        // закрываем подключение к БД



        }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        Bitmap img = null;
        if (requestCode == REQ_Load && resultCode == RESULT_OK) {
            Uri selectedImage = data.getData();
            try {
                img = MediaStore.Images.Media.getBitmap(getContentResolver(), selectedImage);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            image.setImageBitmap(img); }

        if (requestCode == REQ_Camera) {
            Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
            image.setImageBitmap(thumbnail); }
        //super.onActivityResult(requestCode, resultCode, data);
    }

    private String calcDate()
    {
        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        Date myDate = null;
        try
        {
            myDate = df.parse(date.getText().toString());
        } catch (ParseException e)
        {
            e.printStackTrace();
        }
        Calendar dob = Calendar.getInstance();
        Calendar today = Calendar.getInstance();

        dob.setTime(myDate);
        dob.add(Calendar.DAY_OF_MONTH, -1);
        int Age = today.get(Calendar.YEAR) - dob.get(Calendar.YEAR);
        if (today.get(Calendar.DAY_OF_YEAR) <= dob.get(Calendar.DAY_OF_YEAR)) {
            Age--;
        }
        return String.valueOf(Age);
    }
    private Boolean IsDate(String str_date)
    {
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        try
        {
            Date date = formatter.parse(str_date);
        }
        catch (ParseException e)
        {
            return false;
        }
        return true;
    }
}
