package ru.goryachev.calculator.presenters;

public interface IMainActivityPresenter {
    void addOperand(String operand);

    void getResult();

    void clear();
}
