package ru.geekbrains.catch_the_drop;

import javax.swing.*;
import java.awt.*;

public class GameWindow extends JFrame{ //наследование для работы с окнами и свингом

    private static GameWindow game_window; //переменная для окна

    public static void main(String[] args) {
        game_window = new GameWindow(); //создаем переменную окна
        game_window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE); //завершение программы при закрытии окна
        game_window.setLocation(200, 100); //координаты окна
        game_window.setSize(906, 478); //размер окна
        game_window.setResizable(false); //запрет изменений размера окна

        GameField game_field = new GameField(); //создание объекта
        game_window.add(game_field); //добавление к отрисовке

        game_window.setVisible(true); //сделать окно видимым
    }

    private static void onRepaint(Graphics g){ //для возможности рисовать
        g.fillOval(10, 10, 200,100);
        g.drawLine(10,150,200,150);
    }

    private static class GameField extends JPanel{  //класс-панель для рисования

        //делаем динамическое замещение методов
        @Override
        protected void paintComponent(Graphics g){
            super.paintComponent(g); //сначала отрисовка компонента
            onRepaint(g); //после дорисовка
        }
    }
}