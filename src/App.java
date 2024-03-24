import lib.ui.SimFrame;

public class App {
    public static void main(String[] args) throws Exception {
        SimFrame frame = new SimFrame(1000, 600);
        frame.createFrame();
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