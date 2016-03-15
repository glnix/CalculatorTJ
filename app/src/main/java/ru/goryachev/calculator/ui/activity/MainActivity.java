package ru.goryachev.calculator.ui.activity;

import android.app.Activity;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import ru.goryachev.calculator.R;
import ru.goryachev.calculator.presenters.IMainActivityPresenter;
import ru.goryachev.calculator.presenters.MainActivityPresenter;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, IMainActivity {
    private static final String TAG = "MainActivity_LOG";
    IMainActivityPresenter mainActivityPresenter;
//    Флаг для обнуления полей ввода выражения
    private boolean calculated = false;

    @Bind(R.id.text_calculate_string)
    public TextView calculateString;
    @Bind(R.id.text_output)
    public TextView textOutput;

    @Bind(R.id.btn_1)
    public Button btn_1;
    @Bind(R.id.btn_2)
    public Button btn_2;
    @Bind(R.id.btn_3)
    public Button btn_3;
    @Bind(R.id.btn_4)
    public Button btn_4;
    @Bind(R.id.btn_5)
    public Button btn_5;
    @Bind(R.id.btn_6)
    public Button btn_6;
    @Bind(R.id.btn_7)
    public Button btn_7;
    @Bind(R.id.btn_8)
    public Button btn_8;
    @Bind(R.id.btn_9)
    public Button btn_9;
    @Bind(R.id.btn_0)
    public Button btn_0;

    @Bind(R.id.btn_plus_minus)
    public Button btn_plus_minus;
    @Bind(R.id.btn_point)
    public Button btn_point;
    @Bind(R.id.btn_plus)
    public Button btn_plus;
    @Bind(R.id.btn_minus)
    public Button btn_minus;
    @Bind(R.id.btn_multiply)
    public Button btn_multiply;
    @Bind(R.id.btn_divide)
    public Button btn_divide;

    @Bind(R.id.btn_backspace)
    public Button btn_backspace;
    @Bind(R.id.btn_equals)
    public Button btn_equals;

    @Bind(R.id.btn_open_bracket)
    public Button btn_open_bracket;
    @Bind(R.id.btn_close_bracket)
    public Button btn_close_bracket;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
//        Установим листнеры для кнопок
        setOnClickListnersForButtons(this, findViewById(R.id.buttons_layout));

        mainActivityPresenter = new MainActivityPresenter(this);
    }

    //    Метод пробегается по viewgroup с кнопками и устанавливает OnClickListner, так код просто короче
    public static void setOnClickListnersForButtons(View.OnClickListener clickListener, View v) {
        try {
            if (v instanceof ViewGroup) {
                ViewGroup vg = (ViewGroup) v;
                for (int i = 0; i < vg.getChildCount(); i++) {
                    View child = vg.getChildAt(i);
                    setOnClickListnersForButtons(clickListener, child);
                }
            } else if (v instanceof Button) {
                v.setOnClickListener(clickListener);
            }
        } catch (Exception e) {
        }
    }

    @Override
    public void onClick(View v) {
        if (calculated) {
            calculateString.setText("");
            textOutput.setText("0");
            calculated = false;
        }
        String currentText = String.valueOf(textOutput.getText());

        switch (v.getId()) {
            case R.id.btn_0:
            case R.id.btn_1:
            case R.id.btn_2:
            case R.id.btn_3:
            case R.id.btn_4:
            case R.id.btn_5:
            case R.id.btn_6:
            case R.id.btn_7:
            case R.id.btn_8:
            case R.id.btn_9:
//                Цифру берем из теста кнопки, укорачиваем код
                String digit = String.valueOf(((Button) v).getText());
//                Не даем числу начинаться с 0, если оно целое
                for (int i = 0; i < currentText.length(); i++) {
                    if (!currentText.contains(".") && currentText.charAt(i) == '0') {
                        currentText = currentText.substring(i + 1);
                    } else break;
                }
                textOutput.setText(currentText + digit);
                break;
            case R.id.btn_point:
//                Если десятичной точки в поле ввода нет, то добавляем ее
                if (!currentText.contains(".")) {
                    textOutput.setText(currentText + ".");
                }
                break;
            case R.id.btn_open_bracket:
                mainActivityPresenter.addOperand("(");
                break;

            case R.id.btn_close_bracket:
                mainActivityPresenter.addOperand(currentText);
                mainActivityPresenter.addOperand(")");
                textOutput.setText("0");
                break;
            case R.id.btn_equals:
                if (currentText.length() > 0 && !currentText.equals("0")) {
                    mainActivityPresenter.addOperand(currentText);
                }
                mainActivityPresenter.getResult();
                break;
            case R.id.btn_backspace:
                mainActivityPresenter.clear();
                break;
            case R.id.btn_plus_minus:
                if (!currentText.startsWith("-")) {
                    textOutput.setText("-" + currentText);
                } else {
                    textOutput.setText(currentText.substring(1));
                }
                break;
            case R.id.btn_plus:
                mainActivityPresenter.addOperand(currentText);
                mainActivityPresenter.addOperand("+");
                textOutput.setText("0");
                break;
            case R.id.btn_minus:
                mainActivityPresenter.addOperand(currentText);
                mainActivityPresenter.addOperand("-");
                textOutput.setText("0");
                break;
            case R.id.btn_multiply:
                mainActivityPresenter.addOperand(currentText);
                mainActivityPresenter.addOperand("*");
                textOutput.setText("0");
                break;
            case R.id.btn_divide:
                mainActivityPresenter.addOperand(currentText);
                mainActivityPresenter.addOperand("/");
                textOutput.setText("0");
                break;
        }

    }

    @Override
    public void setResult(String result) {
        calculated = true;
        textOutput.setText(result);
    }

    @Override
    public void setCalculateString(String stringToCalculate) {
        calculateString.setText(stringToCalculate);
    }
}
