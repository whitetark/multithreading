using lab08.Models;
using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;
using Newtonsoft.Json;
using System.Collections;
using System.Runtime.InteropServices;

namespace lab08.Controllers
{
    [ApiController]
    [Route("[controller]")]
    public class MatrixController : ControllerBase
    {
        private readonly ILogger<MatrixController> _logger;

        public MatrixController(ILogger<MatrixController> logger)
        {
            _logger = logger;
        }
        [HttpPost]
        public async void Multiply()
        {   
            var requestBodyJson = await new StreamReader(Request.Body).ReadToEndAsync();
            var requestBody = JsonConvert.DeserializeObject<Request>(requestBodyJson);

             int[,] matrix1 = requestBody.Matrix1;
             int[,] matrix2 = requestBody.Matrix2;

             int[,] result = Methods.MultiplyMatrix(matrix1, matrix2);

             var response = JsonConvert.SerializeObject(result);
             
             Response.ContentType = "application/json";

             await Response.WriteAsync(response);
        }

        [HttpGet]
        public async void GenerateAndMultiply()
        { 
             int[,] matrix1 = Methods.GenerateRandomMatrix(100, 0, 10);
             int[,] matrix2 = Methods.GenerateRandomMatrix(100, 0, 10);

             int[,] result = Methods.MultiplyMatrix(matrix1, matrix2);

             var response = JsonConvert.SerializeObject(result);

            Response.ContentType = "application/json";

            await Response.WriteAsync(response);
        }
    }
}
