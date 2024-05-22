package project;

import java.io.File;
import java.io.IOException;
import java.util.Vector;

public class Main {
    public static void main(String[] args) throws IOException {
        Vector<Trade> Trades = new Vector<Trade>(3000);
        int[] riskFactors = {1,2,3,5,10};
        String path = "/Users/nikizheng/Desktop/Nikii HW/MAC286/Project/Data/";
        String file = "Stocks.txt";
        Vector<String> symbols = Helper.loadSymbols(path,file);
        for(int riskFactor: riskFactors)
        {
            System.out.println("riskFactor: "+riskFactor);
            Tester myTester = new Tester(path,file,riskFactor);
            myTester.run();
            Trades.clear();
            System.out.println("----------------------------------------------------");
        }
        /*
        change the pattern with 10days low and 10days high, and printout the # of trades, the averageProit should between the averageProfitLong and averageProitShort
         */
    }
}
