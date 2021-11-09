package com.example.assignment_4_tip_calculator;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle; // for saving state information
import android.text.Editable; // for EditText event handling
import android.text.TextWatcher; // EditText listener
import android.widget.EditText; // for bill amount input
import android.widget.SeekBar; // for changing the tip percentage
import android.widget.SeekBar.OnSeekBarChangeListener; // SeekBar listener
import android.widget.TextView; // for displaying text
import java.text.NumberFormat; // for currency formatting

public class MainActivity extends AppCompatActivity {

    // currency and percent formatter objects
    private static final NumberFormat currencyFormat =
            NumberFormat.getCurrencyInstance();
    private static final NumberFormat percentFormat =
            NumberFormat.getPercentInstance();

    private double billAmount = 0.0; // bill amount entered by the user
    private double percent = 0.15; // initial tip percentage
    private double tax = 0.10; // initial tax percentage
    private TextView percentTextView; // shows tip percentage
    private TextView taxNumber; // shows Tax Percentage
    private TextView tipTextView; // shows calculated tip amount
    private TextView totalTextView; // shows calculated total bill amount
    private TextView taxTextView; // shows calculated total tax amount

    // called when the activity is first created
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState); // call superclass onCreate
        setContentView(R.layout.activity_main); // inflate the GUI

        // get references to programmatically manipulated TextViews
        percentTextView = (TextView) findViewById(R.id.percentTextView);
        taxNumber = (TextView) findViewById(R.id.taxNumber);
        tipTextView = (TextView) findViewById(R.id.tipTextView);
        taxTextView = (TextView) findViewById(R.id.taxTextView);
        totalTextView = (TextView) findViewById(R.id.totalTextView);
        tipTextView.setText(currencyFormat.format(0));
        taxTextView.setText(currencyFormat.format(0));
        totalTextView.setText(currencyFormat.format(0));

        // set amountEditText's TextWatcher
        EditText amountEditText =
                (EditText) findViewById(R.id.amountEditText);
        amountEditText.addTextChangedListener(amountEditTextWatcher);

        // set percentSeekBar's OnSeekBarChangeListener
        SeekBar percentSeekBar =
                (SeekBar) findViewById(R.id.percentSeekBar);
        percentSeekBar.setOnSeekBarChangeListener(seekBarListener);

        // Set TaxSeekBar OnSeekBarChangeListener

        SeekBar taxSeekBar =
                (SeekBar) findViewById(R.id.taxSeekBar);
        taxSeekBar.setOnSeekBarChangeListener(taxSeekBarListener);
    }

    // calculate and display tip and total amounts
    private void calculate() {
        // format percent and display in percentTextView
        percentTextView.setText(percentFormat.format(percent));

        taxNumber.setText(percentFormat.format(tax));

        // calculate the tip and total
        double tip = billAmount * percent;
        double taxes = billAmount * tax;
        double total = billAmount + tip + taxes;

        // display tip and total formatted as currency
        tipTextView.setText(currencyFormat.format(tip));
        taxTextView.setText(currencyFormat.format(taxes));
        totalTextView.setText(currencyFormat.format(total));
    }

    // listener object for the SeekBar's progress changed events
    private OnSeekBarChangeListener seekBarListener =
            new OnSeekBarChangeListener() {
                // update percent, then call calculate
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress,
                                              boolean fromUser) {
                    percent = progress / 100.0; // set percent based on progress
                    calculate(); // calculate and display tip and total
                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {
                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {
                }
            };
    private OnSeekBarChangeListener taxSeekBarListener =
            new OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar2, int progress, boolean fromUser) {
                    tax = progress / 100.0;
                    calculate();
                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar2) {

                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar2) {

                }
            };

    // listener object for the EditText's text-changed events
    private TextWatcher amountEditTextWatcher = new TextWatcher() {
        // called when the user modifies the bill amount
        @Override
        public void onTextChanged(CharSequence s, int start,
                                  int before, int count) {

            try { // get bill amount and display currency formatted value
                billAmount = Double.parseDouble(s.toString());
            } catch (NumberFormatException e) { // if s is empty or non-numeric
                billAmount = 0.0;
            }

            calculate(); // update the tip and total TextViews
        }

        @Override
        public void afterTextChanged(Editable s) {
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }
    };

}