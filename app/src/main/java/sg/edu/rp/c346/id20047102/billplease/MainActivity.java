package sg.edu.rp.c346.id20047102.billplease;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.ToggleButton;

public class MainActivity extends AppCompatActivity {

    EditText amount;
    EditText numPax;
    ToggleButton svs;
    ToggleButton gst;
    TextView totalBill;
    TextView eachPays;
    Button calculate;
    Button reset;
    EditText discount;
    RadioGroup rdgMethod;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        amount = findViewById(R.id.editInputAmount);
        numPax = findViewById(R.id.editInputNumPax);
        totalBill = findViewById(R.id.textTotalBill);
        eachPays = findViewById(R.id.textEachPays);
        svs = findViewById(R.id.tbSvs);
        gst = findViewById(R.id.tbGst);
        calculate = findViewById(R.id.btnCalc);
        reset = findViewById(R.id.btnReset);
        discount = findViewById(R.id.editInputDiscount);
        rdgMethod = findViewById(R.id.rdgMethod);

        calculate.setOnClickListener(v -> {
            if (amount.getText().toString().trim().length() != 0 && numPax.getText().toString().trim().length() != 0) {
                double newAmt = 0.0;
                if (!svs.isChecked() && !gst.isChecked()) {
                    newAmt = Double.parseDouble(amount.getText().toString());
                } else if (svs.isChecked() && !gst.isChecked()) {
                    newAmt = Double.parseDouble(amount.getText().toString()) * 1.1;
                } else if (!svs.isChecked() && gst.isChecked()) {
                    newAmt = Double.parseDouble(amount.getText().toString()) * 1.07;
                } else {
                    newAmt = Double.parseDouble(amount.getText().toString()) * 1.17;

                }

                //Discount
                if (discount.getText().toString().trim().length() != 0) {
                    newAmt *= 1 - Double.parseDouble(discount.getText().toString()) / 100;
                }
                totalBill.setText("Total Bill: $" + String.format("%.2f", newAmt));
                int numPerson = Integer.parseInt(numPax.getText().toString());
                if (numPerson != 1)
                    eachPays.setText("Each Pays: $" + String.format("%.2f", newAmt / numPerson));
                else
                    eachPays.setText("Each Pays: $" + newAmt);

                //Paynow
                int checkedRadioId = rdgMethod.getCheckedRadioButtonId();
                if (checkedRadioId == R.id.radioButtonCash) {
                    // Write the code when male selected
                    eachPays.setText("Each Pays: $" + String.format("%.2f", newAmt / numPerson) + " in cash");
                } else {
                    // Write the code when female selected
                    eachPays.setText("Each Pays: $" + String.format("%.2f", newAmt / numPerson) + " via PayNow to 90302321");
                }
            }

            //Error Validation
            if (amount.getText().toString().trim().length() == 0) {
                amount.setError("Please enter the total Amount!");
            } else if (numPax.getText().toString().trim().length() == 0)
                numPax.setError("Please enter the number of people paying!");
        });

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                amount.setText("");
                numPax.setText("");
                svs.setChecked(false);
                gst.setChecked(false);
                discount.setText("");
                totalBill.setText("Total Bill: ");
                eachPays.setText("Each Pays: ");
            }

        });
    }
}