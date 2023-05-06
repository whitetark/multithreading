using coursework;

//int[][] graph = new int[][]
//{
//        new int[]{0, 7, -1, -1, 10},
//        new int[]{-1, 0, 3, -1, 4},
//        new int[]{-1, -1, 0, 5, -1},
//        new int[]{-1, -1, -1, 0, -1},
//        new int[]{-1, -1, -1, 2, 0}
//};

//int[][] graph = new int[][]
//{
//        new int[]{  0,  4, -1, -1, -1, -1, -1,  8, -1 },
//        new int[]{  4,  0,  8, -1, -1, -1, -1, 11, -1 },
//        new int[]{ -1,  8,  0,  7, -1,  4, -1, -1,  2 },
//        new int[]{ -1, -1,  7,  0,  9, 14, -1, -1, -1 },
//        new int[]{ -1, -1, -1,  9,  0, 10, -1, -1, -1 },
//        new int[]{ -1, -1,  4, -1, 10,  0,  2, -1, -1 },
//        new int[]{ -1, -1, -1, 14, -1,  2,  0,  1,  6 },
//        new int[]{  8, 11, -1, -1, -1, -1,  1,  0,  7 },
//        new int[]{ -1, -1,  2, -1, -1, -1,  6,  7,  0 }
//};

Console.Write("Num Of Nodes: ");
int N = int.Parse(Console.ReadLine());
int[][] graph = Methods.CreateMatrix(N);

//Methods.Print(graph);

var startTime = DateTime.Now;
var result = Dijkstra.Count(graph);
var timeNaive = DateTime.Now - startTime;
Console.WriteLine($"Main Thread: Time {timeNaive}");
//Methods.Print(result);

int numOfThreads = 4;
startTime = DateTime.Now;
result = DijkstraParallel.Count(graph, numOfThreads);
var timeParallel = DateTime.Now - startTime;
Console.WriteLine($"Number Of Threads: {numOfThreads}, Time: {timeParallel}");
//Methods.Print(result);

Console.WriteLine($"SpeedUp: {timeNaive / timeParallel}");

