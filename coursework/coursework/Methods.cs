using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace coursework
{
    public class Methods
    {
        public static int[][] CreateMatrix(int size, int? value = null)
        {
            var matrix = new int[size][];
            var rng = new Random();
            for (int i = 0; i < size; i++)
            {
                matrix[i] = new int[size];
                for (int j = 0; j < size; j++)
                {
                    if (i != j)
                    {
                        matrix[i][j] = value ?? rng.Next(1,10);
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
            Console.Write("Vertex     Distance " + "from Source\n");
            for (int i = 0; i < arr.Length; ++i)
            {
                Console.Write(i+1 + " \t\t " + arr[i] + "\n");
                //Console.Write(arr[i] + " ");
            }
            Console.Write("\n\n");
        }
        public static void Print(int[][] arr)
        {
            Console.Write("Your Adjacency Matrix\n");
            for (int i = 0; i < arr.Length; ++i)
            {
                for(int j = 0;j < arr[i].Length; ++j)
                {
                    Console.Write(arr[i][j] + " ");
                }
                Console.Write("\n");
            }
            Console.Write("\n\n");
        }
    }
}
