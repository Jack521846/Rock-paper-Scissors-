import javax.swing.*;

public class RockPaperScissorsRunner
{
    public static void main(String[] args)
    {
        SwingUtilities.invokeLater(() ->
        {
            RockPaperScissorsFrame frame = new RockPaperScissorsFrame();
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }

}
