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
    int infinity = 1000;                     // �������������
    int p = VERTEXES;

    public void getPath(int s, int g) {
        int[] x = new int[VERTEXES];
        int[] t = new int[VERTEXES];
        int[] h = new int[VERTEXES];

        int u;            // ������� ������
        for (u = 0; u < p; u++) {
            t[u] = infinity; //������� ��� ���������� ���� �� s � i
            //����� �������������
            x[u] = 0;        // � ��� ����������� ���� �� ��� ����� �������
        }
        h[s] = 0; // s - ������ ����, ������� ���� ������� ������ �� ������������
        t[s] = 0; // ���������� ���� �� s � s ����� 0
        x[s] = 1; // ��� ������� s ������ ���������� ����
        v = s;    // ������ s ������� ��������

        while (true) {
            // ���������� ��� �������, ������� v, � ���� ��� ��� ���������� ����
            for (u = 0; u < p; u++) {
                if (arr[v][u] == 0) continue; // ������� u � v ���������
                if (x[u] == 0 && t[u] > t[v] + arr[v][u]) //���� ��� ������� u ��� ��
                //������ ���������� ����
                // � ����� ���� � u ������ ���
                //������, ��
                {
                    t[u] = t[v] + arr[v][u];    //���������� ����� �������� ����� ���� �
                    //������ t �
                    h[u] = v;    //����������, ��� v->u ����� �����������
                    //���� �� s->u
                }
            }

            // ���� �� ���� ���� ������������ ����� ����� ��������
            int w = infinity;  // ��� ������ ������ ��������� ����
            v = -1;            // � ����� ������ v - �������, � ������� �����
            // ������ ����� ���������� ����. ��� ������
            // ������� ��������
            for (u = 0; u < p; u++) // ���������� ��� �������.
            {
                if (x[u] == 0 && t[u] < w) // ���� ��� ������� �� ������ ����������
                // ���� � ���� ����� ���� � ������� u ������
                // ��� ���������, ��
                {
                    v = u; // ������� �������� ���������� u-� �������
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
