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

            result[0] = 0;

            for (int count = 1; count < graph.Length; count++)
            {
                int minI = -1;
                int min = IINF;

                for (int j = 0; j < graph.Length; j++)
                {
                    if (!visited[j] && result[j] < min)
                    {
                        minI = j;
                        min = result[j];
                    }
                }
                visited[minI] = true;

                for (int i = 0; i < graph.Length; i++)
                {
                    for (int j = 0; j < graph.Length; j++)
                    {
                        if (graph[i][j] != -1 && !visited[j])
                        {
                            result[j] = Math.Min(result[j], result[i] + graph[i][j]);
                        }
                    }
                }
            }

            return result;
        }
    }
}
