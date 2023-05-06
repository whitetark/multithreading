using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace coursework
{
    public class Dijkstra
    {
        public const int IINF = 2 << 32 - 47;
        public static int[] Count(int[][] graph)
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
                for (int i = 0; i < graph.Length; i++)
                {
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
    }
}
