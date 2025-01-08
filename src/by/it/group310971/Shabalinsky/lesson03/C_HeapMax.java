package by.it.group310971.Shabalinsky.lesson03;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

// Lesson 3. C_Heap.
// Задача: построить max-кучу = пирамиду = бинарное сбалансированное дерево на массиве.
// ВАЖНО! НЕЛЬЗЯ ИСПОЛЬЗОВАТЬ НИКАКИЕ КОЛЛЕКЦИИ, КРОМЕ ARRAYLIST (его можно, но только для массива)

//      Проверка проводится по данным файла
//      Первая строка входа содержит число операций 1 ≤ n ≤ 100000.
//      Каждая из последующих nn строк задают операцию одного из следующих двух типов:

//      Insert x, где 0 ≤ x ≤ 1000000000 — целое число;
//      ExtractMax.

//      Первая операция добавляет число x в очередь с приоритетами,
//      вторая — извлекает максимальное число и выводит его.

//      Sample Input:
//      6
//      Insert 200
//      Insert 10
//      ExtractMax
//      Insert 5
//      Insert 500
//      ExtractMax
//
//      Sample Output:
//      200
//      500

public class C_HeapMax {

    private class MaxHeap {
        //!!!!!!!!!!!!!!!!!!!!!!!!!!! НАЧАЛО ЗАДАЧИ !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!1
        //тут запишите ваше решение.
        //Будет мало? Ну тогда можете его собрать как Generic и/или использовать в варианте B
        private List<Long> heap = new ArrayList<>();

        int siftDown(int i) { //просеивание вверх
            int parent = (i - 1) / 2; // индекс родителя
            while (i > 0 && heap.get(i) > heap.get(parent)) {
                // обмен местами с родителем
                long temp = heap.get(i);
                heap.set(i, heap.get(parent));
                heap.set(parent, temp);
                // переход к родителю
                i = parent;
                parent = (i - 1) / 2;
            }
            return i;
        }

        int siftUp(int i) { //просеивание вниз
            int left = 2 * i + 1; // индекс левого потомка
            int right = 2 * i + 2; // индекс правого потомка
            while (left < heap.size()) {
                int maxChild = left; // предполагаем, что левый потомок больше
                if (right < heap.size() && heap.get(right) > heap.get(left)) {
                    maxChild = right; // если правый потомок больше, то берем его
                }
                if (heap.get(i) < heap.get(maxChild)) {
                    // обмен местами с максимальным потомком
                    long temp = heap.get(i);
                    heap.set(i, heap.get(maxChild));
                    heap.set(maxChild, temp);
                    // переход к потомку
                    i = maxChild;
                    left = 2 * i + 1;
                    right = 2 * i + 2;
                } else {
                    // если текущий элемент больше, то просеивание завершено
                    break;
                }
            }
            return i;
        }

        void insert(Long value) { //вставка
            heap.add(value); // добавляем в конец кучи
            siftDown(heap.size() - 1); // просеиваем новый элемент вниз
        }

        Long extractMax() { //извлечение и удаление максимума
            Long result = null;
            if (!heap.isEmpty()) {
                result = heap.get(0); // максимальный элемент находится в корне
                heap.set(0, heap.get(heap.size() - 1)); // заменяем корень последним элементом
                heap.remove(heap.size() - 1); // удаляем последний элемент
                siftUp(0); // просеиваем новый корень вверх
            }
            return result;
        }
        //!!!!!!!!!!!!!!!!!!!! КОНЕЦ ЗАДАЧИ !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!1
    }

    //эта процедура читает данные из файла, ее можно не менять.
    Long findMaxValue(InputStream stream) {
        Long maxValue=0L;
        MaxHeap heap = new MaxHeap();
        //прочитаем строку для кодирования из тестового файла
        Scanner scanner = new Scanner(stream);
        Integer count = scanner.nextInt();
        for (int i = 0; i < count; ) {
            String s = scanner.nextLine();
            if (s.equalsIgnoreCase("extractMax")) {
                Long res=heap.extractMax();
                if (res!=null && res>maxValue) maxValue=res;
                System.out.println();
                i++;
            }
            if (s.contains(" ")) {
                String[] p = s.split(" ");
                if (p[0].equalsIgnoreCase("insert"))
                    heap.insert(Long.parseLong(p[1]));
                i++;
                //System.out.println(heap); //debug
            }
        }
        return maxValue;
    }

    public static void main(String[] args) throws FileNotFoundException {
        String root = System.getProperty("user.dir") + "/src/";
        InputStream stream = new FileInputStream(root + "by/it/group310971/Shabalinsky/lesson03/heapData.txt");
        C_HeapMax instance = new C_HeapMax();
        System.out.println("MAX="+instance.findMaxValue(stream));
    }

    // РЕМАРКА. Это задание исключительно учебное.
    // Свои собственные кучи нужны довольно редко.
    // "В реальном бою" все существенно иначе. Изучите и используйте коллекции
    // TreeSet, TreeMap, PriorityQueue и т.д. с нужным CompareTo() для объекта внутри.
}
