using lab08.Models;
using Microsoft.AspNetCore.Mvc;
using Newtonsoft.Json;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Net;
using System.Net.Http;
using System.Threading.Tasks;

namespace lab08.Controllers
{
    [ApiController]
    [Route("[controller]")]
    public class MatrixController : ControllerBase
    {
        [HttpPost("multiply")]
        public async void MultiplyMatrices(Request request)
        {
            try
            {
                int[][] matrix1 = request.Matrix1;
                int[][] matrix2 = request.Matrix2;

                int[][] result = await Methods.MultiplyMatrix(matrix1, matrix2);

                var response = JsonConvert.SerializeObject(result);

                Response.ContentType = "application/json";

                await Response.WriteAsync(response);
            } catch (Exception ex)
            {
                throw ex;
            }
        }

        [HttpGet("generate/{matrixSize}")]
        public async void GenerateAndMultiplyMatrices(int matrixSize)
        {
            try
            {
                int[][] matrix1 = await Methods.GenerateRandomMatrix(matrixSize, 0, 100);
                int[][] matrix2 = await Methods.GenerateRandomMatrix(matrixSize, 0, 100);

                int[][] result = await Methods.MultiplyMatrix(matrix1, matrix2);

                var response = JsonConvert.SerializeObject(result);

                Response.ContentType = "application/json";

                await Response.WriteAsync(response);
            } catch (Exception ex)
            {
                throw ex;
            }
        }
    }
}
