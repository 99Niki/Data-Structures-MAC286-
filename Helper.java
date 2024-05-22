package project;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Vector;

import static java.lang.System.exit;

public class Helper {
    public static Vector<String> loadSymbols(String path, String file) throws IOException {
        Vector<String> vSymbols = new Vector<String>();
        try{
            FileReader fr = new FileReader(path+file);
            BufferedReader br = new BufferedReader(fr);
            String st;
            while((st=br.readLine())!=null)
            {
                vSymbols.add(st.trim());
            }
            br.close();
            fr.close();
        }catch (IOException e) {
            System.out.println("Something is wrong: "+e.getMessage());
            exit(0);
        }

        return vSymbols;
    }
    public static Statistics computeStats(Vector<Trade> trades){
        Statistics st = new Statistics();
        double totalProfit = 0,totalProfitLong = 0, totalProfitShort = 0;
        double totalHoldingPeriod = 0, totalHoldingPeriodLong = 0, totalHoldingPeriodShort = 0;
        int totalLongTrades = 0,totalShortTrades = 0;
        int totalNumWinners=0, numWinnersLong =0, numWinnersShort=0;

        for(int i =0; i<trades.size();i++)
        {
           totalProfit += trades.elementAt(i).percentPL();
           totalHoldingPeriod +=trades.elementAt(i).getHoldingPeriod();
           if(trades.elementAt(i).getDir()== Direction.LONG)
           {
               totalProfitLong += trades.elementAt(i).percentPL();
               totalHoldingPeriodLong += trades.elementAt(i).getHoldingPeriod();
               totalLongTrades++;
               if(trades.elementAt(i).percentPL()>=0)
               {
                   numWinnersLong++;
                   totalNumWinners++;
               }

           }else if(trades.elementAt(i).getDir()== Direction.SHORT)
           {
               totalProfitShort += trades.elementAt(i).percentPL();
               totalHoldingPeriodShort += trades.elementAt(i).getHoldingPeriod();
               totalShortTrades++;
               if(trades.elementAt(i).percentPL()>=0)
               {
                   numWinnersShort++;
                   totalNumWinners++;
               }

           }else
           {
               System.out.println("Wrong！");
           }
        }
        //total
        st.numOfTrades = trades.size();
        st.averageProfit =totalProfit/trades.size();
        st.averageHoldingPeriod = totalHoldingPeriod/trades.size();
        st.averageProfitPerDay = st.averageProfit/st.averageHoldingPeriod;
        st.winningPercent= (double) totalNumWinners/trades.size()*100;

        //longs
        st.numOfLongTrades = totalLongTrades;
        st.averageProfitLong = totalProfitLong/totalLongTrades;
        st.averageHoldingPeriodLong = totalHoldingPeriodLong/totalLongTrades;
        st.averageProfitPerDayLong = st.averageProfitLong/st.averageHoldingPeriodLong;
        st.winningPercentLong = (double) numWinnersLong/totalLongTrades*100;

        // shorts
        st.numOfShortTrades = totalShortTrades;
        st.averageProfitShort = totalProfitShort/totalShortTrades;
        st.averageHoldingPeriodShort = totalHoldingPeriodShort/totalShortTrades;
        st.averageProfitPerDayShort = st.averageProfitShort/st.averageHoldingPeriodShort;
        st.winningPercentShort = (double)numWinnersShort/totalShortTrades*100;

        return st;
    }
}
