import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.Random;

public class RockPaperScissorsFrame extends JFrame
{
    private int playerRockCount=0;
    private int playerPaperCount=0;
    private int playerScissorsCount=0;

    private int playerWins=0;
    private int computerWins=0;
    private  int ties=0;

    private String lastPlayerMove =  null;

    private Cheat cheatStrategy = new Cheat();
    private RamdomStrategy randomStrategy = new RamdomStrategy();
    private Strategy leastUsedStrategy = new LeastUsedStrategy();
    private Strategy mostUsedStrategy = new MostUsedStrategy();
    private Strategy lastUsedStrategy = new LastUsedStrategy();

    private Random rand = new Random();

    private JButton btnRock, btnPaper, btnScissors, btnQuit;
    private JTextField txtPlayerWins, txtComputerWins, txtTies;
    private JTextArea txtAreaResults;

    public RockPaperScissorsFrame()
    {
        super("Rock Paper Scissors Game");
        buildGUI();
        wireEvents();
    }

    public void buildGUI()
    {
        setLayout(new BorderLayout());

        JPanel buttonPanel = new JPanel();
        btnRock = new JButton("Rock");
        btnPaper = new JButton("Paper");
        btnScissors = new JButton("Scissors");
        btnQuit = new JButton("Quit");

        buttonPanel.add(btnRock);
        buttonPanel.add(btnPaper);
        buttonPanel.add(btnScissors);
        buttonPanel.add(btnQuit);

        buttonPanel.setBorder(BorderFactory.createTitledBorder("Your Moves"));
        add(buttonPanel, BorderLayout.NORTH);

        JPanel statsPanel = new JPanel(new GridLayout(3, 2));

        statsPanel.add(new JLabel("Player Wins:"));
        txtPlayerWins = new JTextField("0");
        txtPlayerWins.setEditable(false);
        statsPanel.add(txtPlayerWins);

        statsPanel.add(new JLabel("Computer Wins:"));
        txtComputerWins = new JTextField("0");
        txtComputerWins.setEditable(false);
        statsPanel.add(txtComputerWins);

        statsPanel.add(new JLabel("Ties:"));
        txtTies = new JTextField("0");
        txtTies.setEditable(false);
        statsPanel.add(txtTies);

        add(statsPanel, BorderLayout.CENTER);

        txtAreaResults= new JTextArea(10, 30);
        txtAreaResults.setEditable(false);
        add(new JScrollPane(txtAreaResults), BorderLayout.SOUTH);
    }

    private void wireEvents()
    {
        ActionListener moveListener = e ->
        {
            String playerMove = null;

            if(e.getSource() == btnRock)
            {
                playerMove = "R";
                playerRockCount++;
            }
            else if (e.getSource() == btnPaper)
            {
                playerMove= "P";
                playerPaperCount++;
            }
            else if (e.getSource() == btnScissors)
            {
                playerMove = "S";
                playerScissorsCount++;
            }

            int prob =rand.nextInt(100)+1;
            Strategy strategy;
            String strategyName;

            if(prob <= 10)
            {
                strategy = cheatStrategy;
                strategyName = "Cheat";
            }
            else if(prob <= 30)
            {
                strategy = leastUsedStrategy;
                strategyName = "Least Used";
            }
            else if(prob <= 60)
            {
                strategy = mostUsedStrategy;
                strategyName = "Most Used";
            }
            else if(prob <= 80)
            {
                strategy = lastUsedStrategy;
                strategyName = "Last Used";
            }
            else
            {
                strategy = randomStrategy;
                strategyName = "Random";
            }

            String computerMove = strategy.getMove(playerMove);
            String result = determineResult(playerMove, computerMove, strategyName);

            lastPlayerMove = playerMove;
            txtAreaResults.append(result+ "\n");
        };

        btnRock.addActionListener(moveListener);
        btnPaper.addActionListener(moveListener);
        btnScissors.addActionListener(moveListener);
        btnQuit.addActionListener(e -> System.exit(0));
    }

    private String determineResult(String playerMove, String computerMove, String strategyName)
    {
        String p = moveToWord(playerMove);
        String c = moveToWord(computerMove);
        String result;

        if(playerMove.equals(computerMove))
        {
            ties++;
            result = "Tie: both chose " + p + "( " + strategyName + " )";

        }
        else if (isPlayerWin(playerMove, computerMove))
        {
            playerWins++;
            result = p+ "beats: " + c + " Player wins! Computer: "  + strategyName + " )";
        }
        else
        {
            computerWins++;
            result = c + "beats " + p + " Computer wins! Computer: "  + "( " + strategyName + " )";
        }

        txtPlayerWins.setText(String.valueOf(playerWins));
        txtComputerWins.setText(String.valueOf(computerWins));
        txtTies.setText(String.valueOf(ties));
        return result;
    }

    private boolean isPlayerWin(String p, String c)
    {
        return (p.equals("R") && c.equals("S")) ||
                (p.equals("P") && c.equals("R")) ||
                (p.equals("S") && c.equals("P"));
    }

    private String moveToWord(String m)
    {
        switch (m) {
            case "R": return "Rock";
            case "P": return "Paper";
            case "S": return "Scissors";
            default: return "Unknown";
        }
    }

    private class LeastUsedStrategy implements Strategy
    {
        @Override
        public String getMove(String playerMove) {
            int r = playerRockCount;
            int p = playerPaperCount;
            int s = playerScissorsCount;
            if (r <= p && r <= s) {
                return "P";
            } else if (p <= r && p <= s) {
                return "S";
            } else {
                return "R";
            }
        }
    }

    private class MostUsedStrategy implements Strategy
    {
        @Override
        public String getMove(String playerMove) {
            int r = playerRockCount;
            int p = playerPaperCount;
            int s = playerScissorsCount;
            if (r >= p && r >= s) {
                return "P";
            } else if (p >= r && p >= s) {
                return "S";
            } else {
                return "R";
            }

        }

    }
    private class LastUsedStrategy implements Strategy
    {
        @Override
        public String getMove(String playerMove) {
            if (lastPlayerMove == null) {
                return randomStrategy.getMove(playerMove);
            } else {
                switch (lastPlayerMove) {
                    case "R":
                        return "P";
                    case "P":
                        return "S";
                    case "S":
                        return "R";
                    default:
                        return randomStrategy.getMove(playerMove);
                }
            }
        }
    }

}
