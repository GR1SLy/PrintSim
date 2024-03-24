import java.io.BufferedReader;
import java.io.FileReader;

import lib.ui.SimFrame;

public class App {
    public static void main(String[] args) throws Exception {
        /* SimFrame frame = new SimFrame(1000, 600);
        frame.createFrame(); */
        try {
            BufferedReader reader = new BufferedReader(new FileReader("../test.txt"));
            String s = reader.readLine();
            while (s != null) { System.out.println(s.length()); s = reader.readLine(); }
            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
//max 100 symbols

/*
 * Начало файла - количество фраз
 * Выбор фразы:
 * Рандомом открываем файл по номеру
 * Читаем количество фраз
 * Рандомом выбираем номер фразы
 * Читаем до нужной фразы
 */