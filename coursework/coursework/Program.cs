﻿using coursework;
using System.Xml.Linq;

//Methods.Experiment();

//int[][] graph = new int[][]
//{
//        new int[]{0, 7, -1, -1, 9},
//        new int[]{-1, 0, 3, -1, 4},
//        new int[]{-1, -1, 0, 5, -1},
//        new int[]{-1, -1, -1, 0, -1},
//        new int[]{-1, -1, -1, 2, 0}
//};

//int[][] graph = new int[][]
//{
//        new int[]{  0, 14, -1, -1, -1, -1, -1, 8,  -1 },
//        new int[]{  4,  0,  8, -1, -1, -1, -1, 11, -1 },
//        new int[]{ -1,  8,  0,  7, -1, 10, -1, -1,  2 },
//        new int[]{ -1, -1,  7,  0,  9, 14, -1, -1, -1 },
//        new int[]{ -1, -1, -1,  9,  0, 10, -1, -1, -1 },
//        new int[]{ -1, -1,  4, -1, 10,  0,  2, -1, -1 },
//        new int[]{ -1, -1, -1, 14, -1,  2,  0,  1,  6 },
//        new int[]{  8, 11, -1, -1, -1, -1,  1,  0,  7 },
//        new int[]{ -1, -1,  2, -1, -1, -1,  16,  7,  0 }
//};

Console.Write("Do You Want To Print Graph? Choose a number: 1/0\n");
int printGraph = int.Parse(Console.ReadLine());

Console.Write("Do You Want To Print Result? Choose a number: 1/0\n");
int printResult = int.Parse(Console.ReadLine());

while (true)
{
    Console.WriteLine("Num Of Nodes Or 0 To Leave: ");
    int nodes = int.Parse(Console.ReadLine());
    if (nodes == 0) { break; }
    int[][] graph = Methods.CreateMatrix(nodes);
    if (printGraph == 1) { Methods.Print(graph); }

    var startTime = DateTime.Now;
    var result = Dijkstra.Count(graph);
    var timeNaive = DateTime.Now - startTime;
    Console.WriteLine($"Sequential Algorithm. Time: {timeNaive}");
    if (printResult == 1) { Methods.Print(result); }


    int numOfThreads = 4;
    startTime = DateTime.Now;
    result = DijkstraParallel.Count(graph, numOfThreads);
    var timeParallel = DateTime.Now - startTime;
    Console.WriteLine($"Parallel Algorithm. Number Of Threads: {numOfThreads}, Time: {timeParallel}");
    if (printResult == 1) { Methods.Print(result); }

    Console.WriteLine($"SpeedUp: {timeNaive / timeParallel}\n");
}

