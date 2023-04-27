using System.Numerics;

namespace lab08.Models
{
    public class Methods
    {
        public async static Task<int[][]> MultiplyMatrix(int[][] matrix1, int[][] matrix2)
        {

            int[][] result = new int[matrix1.Length][];

            Parallel.For(0, matrix1.Length, i =>
            {
                result[i] = new int[matrix2[0].Length];
                for (int j = 0; j < matrix2[0].Length; j++)
                {
                    result[i][j] = 0;
                    for (int k = 0; k < matrix1[0].Length; k++)
                    {
                       result[i][j] += matrix1[i][k] * matrix2[k][j];
                    }
                }
            });

            return result;
        }
        public async static Task<int[][]> GenerateRandomMatrix(int size, int minVal, int maxVal)
        {
            int[][] matrix = new int[size][];

            Random random = new Random();

            for (int i = 0; i < size; i++)
            {
                matrix[i] = new int[size];

                for (int j = 0; j < size; j++)
                {
                    matrix[i][j] = random.Next(minVal, maxVal + 1);
                }
            }

            return matrix;
        }
    }
}
