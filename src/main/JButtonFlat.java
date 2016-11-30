package main;

import javax.swing.*;
import java.awt.*;

public class JButtonFlat extends JButton {

    private static final long serialVersionUID = 1L;

    //конструктор класса JButtonFlat, создающий кастомную кнопку с определенным названием и цветом
    public JButtonFlat(String name, Color color) {
        setText(name);
        setBackground(color);
        setForeground(Color.WHITE);
        setFocusPainted(false);
        setFont(new Font("Comic Sans MS", Font.PLAIN, 18));
    }
}
