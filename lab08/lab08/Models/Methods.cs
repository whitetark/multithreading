using System.Numerics;

namespace lab08.Models
{
    public class Methods
    {
        public static int[,] MultiplyMatrix(int[,] matrix1, int[,] matrix2)
        {
            int rows1 = matrix1.GetLength(0);
            int cols1 = matrix1.GetLength(1);
            int rows2 = matrix2.GetLength(0);
            int cols2 = matrix2.GetLength(1);

            int[,] result = new int[rows1,cols2];

            int temp = 0;

            for (int i = 0; i < rows1; i++)
            {
                for (int j = 0; j < cols2; j++)
                {
                    temp = 0;
                    for (int k = 0; k < cols1; k++)
                    {
                       temp += matrix1[i, k] * matrix2[k, j];
                    }
                    result[i, j] = temp;
                }
            }

            return result;
        }

        public static int[,] GenerateRandomMatrix(int size, int minValue, int maxValue)
        {
            int[,] matrix = new int[size,size];
            Random random = new Random();

            for (int i = 0; i < size; i++)
            {
                for (int j = 0; j < size; j++)
                {
                    matrix[i,j] = random.Next(minValue, maxValue + 1);
                }
            }

            return matrix;
        }
    }
}
