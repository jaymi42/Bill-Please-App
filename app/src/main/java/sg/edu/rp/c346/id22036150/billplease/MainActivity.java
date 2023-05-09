package sg.edu.rp.c346.id22036150.billplease;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.ToggleButton;

public class MainActivity extends AppCompatActivity {

    EditText amtInput;
    EditText paxInput;
    ToggleButton SVC;
    ToggleButton GST;
    EditText discInput;
    RadioGroup paymentRG;
    Button split;
    Button reset;

    TextView totalBill;
    TextView payEach;
    double discount = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        amtInput = findViewById(R.id.amtInput);
        paxInput = findViewById(R.id.paxInput);
        SVC = findViewById(R.id.calcSVC);
        GST = findViewById(R.id.calcGST);
        discInput = findViewById(R.id.discInput);
        paymentRG = findViewById(R.id.payRadioGroup);
        split = findViewById(R.id.splitButton);
        reset = findViewById(R.id.resetButton);
        totalBill = findViewById(R.id.totalBill);
        payEach = findViewById(R.id.eachPayment);





            split.setOnClickListener(new View.OnClickListener() {
                @SuppressLint({"SetTextI18n", "DefaultLocale"})
                @Override
                public void onClick(View view) {
                    // Add your code for the action

                    double origBill = Double.parseDouble(amtInput.getText().toString());
                    double finalBill = 0.0;


                    if(origBill > 0) {
                        if ((!SVC.isChecked()) && (!GST.isChecked())) {
                            finalBill = origBill;
                        } else if ((SVC.isChecked()) && (!GST.isChecked())) {
                            finalBill = origBill * 1.1;
                        } else if ((!SVC.isChecked()) && (GST.isChecked())) {
                            finalBill = origBill * 1.08;
                        } else {
                            finalBill = origBill * 1.18;
                        }
                    } else {
                        amtInput.setError("Please enter a valid amount.");
                    }

                    if(Double.parseDouble(discInput.getText().toString()) <= 0) {
                        discInput.setError("Please enter a valid number.");
                    } else {
                        if ((discInput.getText().toString().trim().length()) != 0) {
                            discount = Double.parseDouble(discInput.getText().toString());
                            finalBill *= (1 - discount / 100);
                        }
                    }



                    totalBill.setText("Total Bill: $" + String.format("%.2f", finalBill));

                    String paymentMode = " in cash";
                    if (paymentRG.getCheckedRadioButtonId() == R.id.paymentPayNow) {
                        paymentMode = " via PayNow to 912345678";
                    }

                    int noOfPax = Integer.parseInt(paxInput.getText().toString());
                    if(noOfPax > 0) {
                        if (noOfPax != 1) {
                            payEach.setText("Each Pays: $" + String.format("%.2f", (finalBill / noOfPax)) + paymentMode);
                        } else {
                            payEach.setText("Each Pays: $" + finalBill + paymentMode);
                        }
                    } else {
                        paxInput.setError("Please enter a valid no. of pax");
                    }




                }

            });

            reset.setOnClickListener(new View.OnClickListener() {
                @SuppressLint("SetTextI18n")
                @Override
                public void onClick(View view) {
                    // Add your code for the action
                    amtInput.setText("");
                    paxInput.setText("");
                    SVC.setChecked(false);
                    GST.setChecked(false);
                    discInput.setText("");
                    paymentRG.check(R.id.paymentCash);
                    totalBill.setText("Total Bill: ");
                    payEach.setText("Each Pays: ");


                }
            });


        }
    }
