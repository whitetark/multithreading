package Models;

public class BlockMatrix{
    public double[][] items;
    public int rows;
    public int cols;
    public BlockMatrix(double[][] items){
        this.items = items;
        this.rows = this.items.length;
        this.cols = this.items[0].length;
    }

    public static BlockMatrix multiply(BlockMatrix block1, BlockMatrix block2){
        BlockMatrix result = new BlockMatrix(new double[block1.rows][block2.cols]);

        for (int i = 0; i < block1.rows; i++) {
            for (int j = 0; j < block2.cols; j++) {
                for (int k = 0; k < block1.cols; k++) {
                    result.items[i][j] += block1.items[i][k] * block2.items[k][j];
                }
            }
        }
        return result;
    }

    public static BlockMatrix add(BlockMatrix block1, BlockMatrix block2){
        BlockMatrix result = new BlockMatrix(new double[block1.rows][block1.rows]);

        for (int i = 0; i < block1.rows; i++) {
            for (int j = 0; j < block1.rows; j++) {
                result.items[i][j] = block1.items[i][j] + block2.items[i][j];
            }
        }
        return result;
    }
}
