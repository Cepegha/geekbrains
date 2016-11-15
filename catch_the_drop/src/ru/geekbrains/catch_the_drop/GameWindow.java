package ru.geekbrains.catch_the_drop;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;

public class GameWindow extends JFrame{ //наследование для работы с окнами и свингом

    private static GameWindow game_window; //переменная для окна
    private static Image background; //картинка фона
    private static Image game_over; //картинка конец игры
    private static Image drop; //картинка капля
    //переменные для отрисовки капли
    private static float drop_left = 200; //x
    private static float drop_top = -100; //y
    private static float drop_v = 200; //скорость капли
    //энд
    //для подсчета времени между кадрами
    private static long last_frame_time;
    //энд
    private static float score = 0;//ведение счета

    public static void main(String[] args) throws IOException {
        //добавление картинок
        background = ImageIO.read(GameWindow.class.getResourceAsStream("background.png"));
        game_over = ImageIO.read(GameWindow.class.getResourceAsStream("game_over.png"));
        drop = ImageIO.read(GameWindow.class.getResourceAsStream("drop.png"));
        //энд
        game_window = new GameWindow(); //создаем переменную окна
        game_window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE); //завершение программы при закрытии окна
        game_window.setLocation(200, 100); //координаты окна
        game_window.setSize(906, 478); //размер окна
        game_window.setResizable(false); //запрет изменений размера окна

        last_frame_time = System.nanoTime();//текущее время в нанасекундах - начальное значение

        GameField game_field = new GameField(); //создание объекта

        //отлавливаем мышь
        game_field.addMouseListener(new MouseAdapter() {
                                        @Override
                                        public void mousePressed(MouseEvent e) {
                                            int x = e.getX(); //через объект е отлавливаем коррдинаты нажатия
                                            int y = e.getY();
                                            //считаем правую и нижнюю координату капли, чтобы определить попадание клика
                                            float drop_right = drop_left + drop.getWidth(null);
                                            float drop_bottom = drop_top + drop.getHeight(null);
                                            //определение попадания
                                            boolean is_drop = x >= drop_left && x <= drop_right && y >= drop_top && y <= drop_bottom;
                                            //если попало
                                            if (is_drop) {
                                                drop_top = -100;//откинуть каплю по вертикали наверх
                                                drop_left = (int) (Math.random() * (game_field.getWidth() - drop.getWidth(null))); //откинуть капл по вертикали в случайное место
                                                drop_v = drop_v + 20; //увеличение скорости
                                                score++; //счет
                                                game_window.setTitle("Score: " + score);//вывод в заголовок окна
                                            }
                                        }
                                    });
        //энд
        game_window.add(game_field); //добавление к отрисовке

        game_window.setVisible(true); //сделать окно видимым
    }

    private static void onRepaint(Graphics g){ //для возможности рисовать
        long current_time = System.nanoTime(); //локальная переменная - текущее время
        float delta_time = (current_time - last_frame_time) * 0.000000001f; //определение дельты и перевод в секунды
        last_frame_time = current_time; //время предыдущего кадра присвоить текущее время

        drop_top = drop_top + drop_v * delta_time; //падение капли со скоростью
        //отрисовка картинок
        g.drawImage(background, 0, 0, null);
        g.drawImage(drop, (int) drop_left, (int) drop_top, null);
        //энд
        //появление Конец игры при вылете капли
        if(drop_top>game_window.getHeight()) {
            g.drawImage(game_over, 280, 120, null);
        }
        //энд
    }

    private static class GameField extends JPanel{  //класс-панель для рисования

        //делаем динамическое замещение методов
        @Override
        protected void paintComponent(Graphics g){
            super.paintComponent(g); //сначала отрисовка компонента
            onRepaint(g); //после дорисовка
            repaint(); // для обновления отрисовки
        }
    }
}