using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace coursework
{
    public class DijkstraParallel
    {
        public const int IINF = 2 << 32 - 47;
        public static int[] Count(int[][] graph, int numOfThreads)
        {
            bool[] visited = new bool[graph.Length];
            int[] result = new int[graph.Length];
            for (int i = 0; i < graph.Length; i++)
            {
                visited[i] = false;
                result[i] = IINF;
            }

            visited[0] = true;
            result[0] = 0;

            for (int count = 0; count < graph.Length - 1; count++)
            {
                var tasks = new List<Task>();
                var work = graph.Length / numOfThreads;
                for (int i = 0; i < graph.Length; i += work)
                {
                    var start = i;
                    tasks.Add(Task.Factory.StartNew(() => SubResult(graph, start, work, visited, result)));
                }

                Task.WaitAll(tasks.ToArray());
                int minI = -1;
                int min = IINF;

                for (int j = 0; j < graph.Length; j++)
                {
                    if (!visited[j])
                    {
                        if (result[j] < min)
                        {
                            minI = j;
                            min = result[j];
                        }
                    }
                }
                visited[minI] = true;
            }

            return result;
        }

        public static void SubResult(int[][] graph, int start, int work, bool[] visited, int[] result)
        {
            for (int i = start; i < start + work; i++)
            {
                if (i >= graph.Length) return;
                for (int j = 0; j < graph.Length; j++)
                {
                    if (graph[i][j] != -1)
                    {
                        if (!visited[j])
                        {
                            result[j] = Math.Min(result[j], result[i] + graph[i][j]);
                        }
                    }
                }
            }
        }
    }
}