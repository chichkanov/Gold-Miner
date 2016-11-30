package test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class PathFinder {

    int[][] arr;

    public PathFinder(int[][] lab) {
        arr = lab;
    }

    int v;
    int VERTEXES = 5;
    int infinity = 1000;                     // Бесконечность
    int p = VERTEXES;

    public void getPath(int s, int g) {
        int[] x = new int[VERTEXES];
        int[] t = new int[VERTEXES];
        int[] h = new int[VERTEXES];

        int u;            // Счетчик вершин
        for (u = 0; u < p; u++) {
            t[u] = infinity; //Сначала все кратчайшие пути из s в i
            //равны бесконечности
            x[u] = 0;        // и нет кратчайшего пути ни для одной вершины
        }
        h[s] = 0; // s - начало пути, поэтому этой вершине ничего не предшествует
        t[s] = 0; // Кратчайший путь из s в s равен 0
        x[s] = 1; // Для вершины s найден кратчайший путь
        v = s;    // Делаем s текущей вершиной

        while (true) {
            // Перебираем все вершины, смежные v, и ищем для них кратчайший путь
            for (u = 0; u < p; u++) {
                if (arr[v][u] == 0) continue; // Вершины u и v несмежные
                if (x[u] == 0 && t[u] > t[v] + arr[v][u]) //Если для вершины u еще не
                //найден кратчайший путь
                // и новый путь в u короче чем
                //старый, то
                {
                    t[u] = t[v] + arr[v][u];    //запоминаем более короткую длину пути в
                    //массив t и
                    h[u] = v;    //запоминаем, что v->u часть кратчайшего
                    //пути из s->u
                }
            }

            // Ищем из всех длин некратчайших путей самый короткий
            int w = infinity;  // Для поиска самого короткого пути
            v = -1;            // В конце поиска v - вершина, в которую будет
            // найден новый кратчайший путь. Она станет
            // текущей вершиной
            for (u = 0; u < p; u++) // Перебираем все вершины.
            {
                if (x[u] == 0 && t[u] < w) // Если для вершины не найден кратчайший
                // путь и если длина пути в вершину u меньше
                // уже найденной, то
                {
                    v = u; // текущей вершиной становится u-я вершина
                    w = t[u];
                }
            }
            if (v == -1) {
                break;
            }
            if (v == g)
                u = g;
            while (u != s) {
                System.out.print(u + " ");
                u = h[u];
            }
            System.out.print(s + ". Dlina pyti " + t[g]);
            break;
        }
        x[v] = 1;
    }


    public static void main(String[] args) {
        int[][] lab = {
                {1, 1, 1, 1, 1},
                {1, 1, 1, 1, 1},
                {1, 1, 1, 1, 1},
                {1, 1, 1, 1, 1},
                {1, 1, 1, 1, 1},
        };

        PathFinder pf = new PathFinder(lab);
        pf.getPath(1,2);
    }
}
