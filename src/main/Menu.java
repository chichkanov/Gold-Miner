package main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Menu extends JPanel {

    private static final long serialVersionUID = 1L;
    public static int gameMode; //режим игры: на 1 или 2 игроков
    static JFrame menu; //jframe меню
    Levels levels; //меню выбора уровней
    static JPanel mainMenu; //главное меню


    //конструктор класса Menu, в котором определяются основные характеристики Jframe, инициализируется главное меню и меню выбора уровня, создается Layout для расположения пунктов меню на фрейме, добавляются листнеры для каждой кнопки
    public Menu() {

        menu = new JFrame("Gold Runner");
        menu.setResizable(false);
        menu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        menu.setSize(320, 300);
        menu.setLocationRelativeTo(null);

        mainMenu = new JPanel();

        levels = new Levels();


        //**************************MAIN MENU**************************//
        //**********************************************************//

        mainMenu.setBackground(Color.WHITE);
        mainMenu.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new java.awt.Insets(0, 2, 10, 2);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 0;

        JPanel name = new JPanel();
        name.setBackground(Color.WHITE);
        JLabel gameName = new JLabel();
        gameName.setFont(new Font("Serif", Font.BOLD, 21));
        gameName.setText("<html><font color='purple'>        Gold Runner     <br></font></html>");
        name.add(gameName);
        mainMenu.add(name, gbc);

        //Start for 1 player mode
        JButtonFlat start1 = new JButtonFlat("1 Игрок", new Color(52, 152, 219));
        gbc.gridy = 1;
        mainMenu.add(start1, gbc);
        start1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                gameMode = 1;
                mainMenu.setVisible(false);
                menu.add(levels);
                levels.setVisible();
            }
        });


        //Start for 2 player mode
        JButtonFlat start2 = new JButtonFlat("2 Игрока", new Color(243, 156, 18));
        gbc.gridy = 2;
        mainMenu.add(start2, gbc);
        start2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                gameMode = 2;
                mainMenu.setVisible(false);
                menu.add(levels);
                levels.setVisible();
            }
        });

        //Help button
        JButtonFlat help = new JButtonFlat("Как играть", new Color(46, 204, 113));
        gbc.gridy = 3;
        mainMenu.add(help, gbc);
        help.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                mainMenu.setVisible(false);
                final JPanel help = new JPanel();
                help.setBackground(Color.WHITE);
                help.setLayout(new GridBagLayout());
                GridBagConstraints gbch = new GridBagConstraints();
                gbch.insets = new java.awt.Insets(5, 2, 5, 2);
                gbch.fill = GridBagConstraints.NONE;
                gbch.gridx = 0;
                gbch.gridy = 0;

                JPanel ta = new JPanel();
                ta.setBackground(Color.WHITE);
                JLabel text = new JLabel();
                text.setText("<html><font color='black'>Смысл игры заключается в собирании<br>монет."
                        + " Ваша цель - собрать определенное <br>"
                        + " кол-во золота (отмечено по краям экрана).<br> "
                        + "Не забывайте, что при игре с компьютером <br>"
                        + "нельзя сталкиваться с ПК игроком<br><br>"
                        + "Управление:<br>↑ - движение вверх<br>↓ - движение вниз<br>← - движение влево<br>→ - движение вправо</font></html>");


                ta.add(text);
                help.add(ta, gbch);


                //Back button
                JButtonFlat back = new JButtonFlat("Назад", new Color(211, 84, 0));
                gbch.gridy = 1;
                help.add(back, gbch);
                back.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent event) {
                        menu.remove(help);
                        mainMenu.setVisible(true);
                    }
                });

                menu.add(help);


            }
        });


        //Exit button
        JButtonFlat exit = new JButtonFlat("Выход", new Color(231, 76, 60));
        gbc.gridy = 4;
        mainMenu.add(exit, gbc);
        exit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                System.exit(0);
            }
        });
        //*************************END*****************************//
        //***********************************************************//       


        menu.add(mainMenu);
        menu.setVisible(true);
    }
}







