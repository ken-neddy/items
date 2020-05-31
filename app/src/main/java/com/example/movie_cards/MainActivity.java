package com.example.movie_cards;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    private EditText mEditTextEmailAddress;
    //add or subtract
    int quantity = 9;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button buttonorder = findViewById(R.id.button_order);
    }
    private void sendMail(String name,String email,String orderDetails) {
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto"));
        intent.putExtra(Intent.EXTRA_EMAIL, new String[]{email});
        intent.putExtra(Intent.EXTRA_SUBJECT, "coffee order for "+ name);
        intent.putExtra(Intent.EXTRA_TEXT, orderDetails);
        if (intent.resolveActivity(getPackageManager()) != null){
            startActivity(intent);
        }
    }

    public void decrement(View view) {
        if (quantity==1){
            Toast.makeText(getApplicationContext(), "cagura namba nene kuri noti", Toast.LENGTH_SHORT).show();
            return;
        }
        quantity = quantity - 1;
        display(quantity);
    }

    public void increment(View view) {
        if (quantity==10){
            Toast.makeText(getApplicationContext(), "chaguan namba nemingin eng pokol", Toast.LENGTH_SHORT).show();
            return;
        }
        quantity = quantity + 1;
        display(quantity);

    }
    public void display (int number){
        TextView textView = findViewById(R.id.tv);
        textView.setText(""+number);
    }

    public void Submitorder(View view) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd G 'at' HH:mm:ss z");
        String currentDateAndTime = sdf.format(new Date());

        EditText nameEditText=findViewById(R.id.name_et);
        String name = nameEditText.getText().toString();

        EditText emailEditText=findViewById(R.id.email_et);
        String email = emailEditText.getText().toString();

        CheckBox hasFineCreamCheckBox = findViewById(R.id.fine_cream_checked);
        boolean hasFineCream = hasFineCreamCheckBox.isChecked();

        CheckBox whippedCreamCheckBox = findViewById(R.id.whipped_cream_checked);
        boolean hasWhippedCream = whippedCreamCheckBox.isChecked();

        CheckBox chocolateCheckBox = findViewById(R.id.chocolate_checked);
        boolean hasChocolate = chocolateCheckBox.isChecked();

        int price = calculatePrice(hasWhippedCream, hasChocolate, hasFineCream);

        String orderDetails = createOrderSummary(name, price, quantity, hasFineCream, hasChocolate, hasWhippedCream, currentDateAndTime);

        sendMail(name, email, orderDetails);

    }

    private String createOrderSummary(String name, int price, int quantity, boolean hasFineCream, boolean hasChocolate, boolean hasWhippedCream, String currentDateAndTime) {
        String orderMessage = "NEDDY'S COFFEE" + "\n" + "Name: " + name;
        if (hasWhippedCream){
            orderMessage +="/n" + "With Whipped cream";
        }
        if (hasChocolate){
            orderMessage += "\n" + "With chocolate";
        }
        if (hasFineCream) {
            orderMessage += "\n" + "With fine cream";
        }
        orderMessage +="\n" + "quantity: " + quantity;
        orderMessage +="\n" + "total: " + price + "ksh";
        orderMessage +="\n" + currentDateAndTime;
        orderMessage +="\n" + "Thank you for choosing neddy's coffee";
        return null;
    }

    private int calculatePrice(boolean hasWhippedCream,boolean hasChocolate,boolean hasFineCream){
        int basePrice = 200;
        if (hasWhippedCream){
            basePrice = basePrice +30;
        }

        if (hasChocolate){
            basePrice = basePrice + 30;
        }

        if (hasFineCream){
            basePrice = basePrice + 30;
        }
        return basePrice * quantity;
    }
}
