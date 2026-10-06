public class Cheat implements Strategy
{
    @Override
    public String getMove(String playerMove)
    {
       String computerMove;
       switch (playerMove)
       {
           case "R" : computerMove = "R";
           break;
           case "P" : computerMove = "P";
           break;
           case "S" : computerMove = "S";
           break;
           default: computerMove = "R";
       }
         return computerMove;

    }
}
