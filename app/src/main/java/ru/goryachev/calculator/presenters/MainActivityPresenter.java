package ru.goryachev.calculator.presenters;


import android.nfc.Tag;
import android.util.Log;

import java.text.ParseException;

import ru.goryachev.calculator.logic.RPNCalculator;
import ru.goryachev.calculator.ui.activity.IMainActivity;

public class MainActivityPresenter implements IMainActivityPresenter {
    private static final String TAG = "MainActivityPres_LOG";
    IMainActivity mainActivity;
    RPNCalculator rpnCalculator;

    public MainActivityPresenter(IMainActivity mainActivity) {
        this.mainActivity = mainActivity;
        rpnCalculator = new RPNCalculator();
    }

    // Закроем пустой конструткор
    private MainActivityPresenter() {
    }

    @Override
    public void addOperand(String operand) {
        rpnCalculator.addOperand(operand);
        Log.d(TAG, rpnCalculator.getOperandsString());
        mainActivity.setCalculateString(rpnCalculator.getOperandsString());
    }

    @Override
    public void getResult() {
        try {
            rpnCalculator.parse();
            mainActivity.setResult(String.valueOf(rpnCalculator.calculate()));
        } catch (ParseException e) {
            mainActivity.setResult("ОШИБКА");
            rpnCalculator.clear();
        }
    }

    @Override
    public void clear() {
        rpnCalculator.clear();
        mainActivity.setCalculateString("");
        mainActivity.setResult("0");
    }
}
