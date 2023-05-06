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
                        matrix[i][j] = random.Next(1,10);
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
                Console.Write(i+1 + " \t\t " + arr[i] + "\n");
                //Console.Write(arr[i] + " ");
            }
            Console.Write("\n\n");
        }
        public static void Print(int[][] matrix)
        {
            Console.Write("Your Adjacency Matrix\n");
            for (int i = 0; i < matrix.Length; i++)
            {
                for(int j = 0;j < matrix[i].Length; j++)
                {
                    Console.Write(matrix[i][j] + " ");
                }
                Console.Write("\n");
            }
            Console.Write("\n\n");
        }
    }
}
