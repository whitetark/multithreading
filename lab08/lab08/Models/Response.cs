namespace lab08.Models
{
    public class Response
    {
        public int[,] result { get; set; }
        public Response(int[,] result)
        {
            this.result = result;
        }
    }
}
