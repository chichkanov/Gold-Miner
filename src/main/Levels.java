package main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

public class Levels extends JPanel {

    private static final long serialVersionUID = 1L;
    public static int levelChosen; //выбранный уровень
    public static int completedLevels; //кол-во завершенных уровней
    public static int numberOfLevels = 25; //кол-во уровней
    public static JButtonFlat[] levels = new JButtonFlat[numberOfLevels]; //массив кнопок
    public static int lastOpenedLevel; //последний выбранный уровень
    BufferedReader in;

    //открывает новый уровень
    public static void openLevel(){
        completedLevels++;
        lastOpenedLevel++;
        FileWriter writer = null;
        try {
            writer = new FileWriter("save.txt");
            writer.write(Integer.toString(completedLevels));
        } catch (IOException ex) {
            // report
        } finally {
            try {writer.close();} catch (Exception ex) {/*ignore*/}
        }

    }

    //конструктор класса Levels, описывающий стиль меню выбора уровней: каждый уровень-отдельная кнопка. Все уровни хранятся в массиве уровней
    public Levels() {
        try {
            in=new BufferedReader(new FileReader("save.txt"));
        } catch (FileNotFoundException e) {
            FileWriter writer = null;
            try {
                writer = new FileWriter("save.txt");
                writer.write(Integer.toString(1));
            } catch (IOException ex) {
                // report
            } finally {
                try {writer.close();} catch (Exception ex) {/*ignore*/}
            }
        }

        try {
            String tmp=in.readLine();
            completedLevels = Integer.parseInt(tmp);
        } catch (IOException e) {
            e.printStackTrace();
        }



        setBackground(Color.WHITE);
        setLayout(new GridBagLayout());
        GridBagConstraints gbcl = new GridBagConstraints();
        gbcl.insets = new java.awt.Insets(2, 2, 5, 2);
        gbcl.fill = GridBagConstraints.HORIZONTAL;
        gbcl.gridx = 0;
        gbcl.gridy = 0;

        for (int i = 0; i < numberOfLevels; i++) {
            if(i<completedLevels) {
                levels[i] = new JButtonFlat(Integer.toString(i + 1), new Color(39, 174, 96));
                lastOpenedLevel=i+1;
            }
            else{
                levels[i] = new JButtonFlat(Integer.toString(i + 1), new Color(149, 165, 166));
            }
            add(levels[i], gbcl);
            final int lvl = i + 1;
            if(i<completedLevels) {
                levels[i].addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        levelChosen = lvl;
                        try {
                            Menu.menu.dispose();
                            new Game();
                        } catch (FileNotFoundException e1) {
                            e1.printStackTrace();
                        }
                    }
                });
            }
            gbcl.gridx++;
            if (gbcl.gridx % 5 == 0) {
                gbcl.gridx = 0;
                gbcl.gridy++;
            }
            if (i == 23) {
                JButtonFlat nextLevels = new JButtonFlat(">", new Color(127, 140, 141));
                add(nextLevels, gbcl);
                nextLevels.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent event) {
                        setVisible(false);
                        Menu.mainMenu.setVisible(true);
                    }
                });
            }

        }


        //Back button
        JButtonFlat back = new JButtonFlat("Назад", new Color(211, 84, 0));
        gbcl.gridy = 6;
        gbcl.gridx = 0;
        gbcl.gridwidth = 0;
        gbcl.gridheight = 0;
        add(back, gbcl);
        back.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                setVisible(false);
                Menu.mainMenu.setVisible(true);
            }
        });


    }

    public void setVisible() {
        setVisible(true);
    }

}
