using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace coursework
{
    public class Methods
    {
        public static int[][] CreateMatrix(int size)
        {
            var matrix = new int[size][];
            var random = new Random();
            for (int i = 0; i < size; i++)
            {
                matrix[i] = new int[size];
                for (int j = 0; j < size; j++)
                {
                    if (i != j)
                    {
                        if ((random.Next(0, 10) * 10) < 33)
                        {
                            matrix[i][j] = -1;
                        } else
                        {
                            matrix[i][j] = random.Next(1, 9);
                        }
                    }
                    else
                    {
                        matrix[i][j] = 0;
                    }
                }
            }
            return matrix;
        }
        public static void Print(int[] arr)
        {
            Console.Write("Vertex           Distance " + "from Source\n");
            for (int i = 0; i < arr.Length; i++)
            {
                Console.Write(i + " \t\t " + arr[i] + "\n");
            }
            Console.Write("\n\n");
        }
        public static void Print(int[][] matrix)
        {
            Console.Write("       ");
            for (int i = 0; i < matrix.Length; i++)
            {
                Console.Write("{0}  ", i);
            }

            Console.WriteLine();

            for (int i = 0; i < matrix.Length; i++)
            {
                Console.Write("{0} | [ ", i);

                for (int j = 0; j < matrix.Length; j++)
                {
                    if (matrix[i][j] == -1)
                    {
                        Console.Write(" .,");
                    }
                    else if (matrix[i][j] == 0)
                    {
                        Console.Write(" &,");
                    }
                    else 
                    {
                        Console.Write(" {0},", matrix[i][j]);
                    }
                }
                Console.Write(" ]\r\n");
            }
            Console.Write("\r\n");
        }
        public static void Experiment()
        {
            int[] sizes = new int[] { 1000, 1500, 2000, 3000 };
            int[] numOfThreads = new int[] { 16 };
            foreach (int size in sizes)
            {
                foreach(int numOfThread in numOfThreads)
                {
                    int[][] graph = Methods.CreateMatrix(size);
                    var startTime = DateTime.Now;
                    var result = Dijkstra.Count(graph);
                    var timeNaive = DateTime.Now - startTime;

                    TimeSpan timeParallel = new TimeSpan();
                    for (int i = 0; i < 3; i++)
                    {
                        startTime = DateTime.Now;
                        result = DijkstraParallel.Count(graph, numOfThread);
                        timeParallel += DateTime.Now - startTime;
                    }

                    timeParallel = timeParallel / 3;

                    Console.WriteLine($"Sequential Algorithm. Size: {size}, Time: {timeNaive}");
                    Console.WriteLine($"Parallel Algorithm. Number Of Threads: {numOfThread}, Size: {size}, Average Time: {timeParallel}");
                    Console.WriteLine($"Average SpeedUp: {timeNaive / timeParallel}\n");
                }
            }
        }
    }
}
