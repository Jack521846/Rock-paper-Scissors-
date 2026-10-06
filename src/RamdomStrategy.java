import java.util.Random;

public class RamdomStrategy implements Strategy
{
    private Random rand = new Random();

    @Override
    public String getMove(String playerMove)
    {
        int r = rand.nextInt(3);
        switch (r)
        {
            case 0: return "R";
            case 1: return "P";
            default: return "S";
        }
    }

}
