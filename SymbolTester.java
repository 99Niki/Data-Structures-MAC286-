package project;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Vector;

public class SymbolTester {
    private int riskFactor; // holding period in days
    private String mSymbol;
    private String dataPath; //= "C:\Users\oaith\Courses\MAC286\Fall2023\Data\";

    private Vector<Bar> mData;
    private Vector<Trade> mTrades;
    private boolean loaded = false;

    public SymbolTester(String s, String p, int risk) {
        riskFactor = risk;
        mSymbol = s;
        dataPath = p;
        mData = new Vector<Bar>(3000, 100);
        mTrades = new Vector<Trade>(200, 100);
        loaded = false;
    }

    public Vector<Trade> getTrades() {
        return mTrades;
    }
    public void loadData() {
        //create file name
        String fileName = dataPath + mSymbol + "_Daily.csv";
        try {
            FileReader fr = new FileReader(fileName);
            BufferedReader br = new BufferedReader(fr);
            String line = br.readLine();
            while((line = br.readLine()) != null) {
                Bar b = new Bar(line);
                mData.add(b);
            }
            loaded = true;
            br.close();
            fr.close();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch(IOException e) {
            System.out.println("Something is wrong: " + e.getMessage());
            loaded = false;
            return;
        }
    }

    private boolean xDaysLow(int ind, int days) {
        for (int i = ind-1; i > ind-days; i--) {
            if(mData.elementAt(i).getLow() < mData.elementAt(ind).getLow())
                return false;
        }
        return true;
    }
    private boolean xDaysHigh(int ind, int days) {
        for (int i = ind-1; i > ind-days; i--) {
            if(mData.elementAt(i).getHigh() > mData.elementAt(ind).getHigh())
                return false;
        }
        return true;
    }

    //Check the win is the same path or not?! We don't have the target step!
    /*
    void outcomes(Trade T, int ind) {
        for(int i = ind; i < mData.size(); i++) {
            if(T.getDir() == Direction.LONG) {
                if(mData.elementAt(i).getHigh() > T.getTarget()) { //it is a win
                    //consider a gap day
                    if(mData.elementAt(i).getOpen() > T.getTarget()) {
                        //close at open  a gap day
                        T.close(mData.elementAt(i).getDate(), mData.elementAt(i).getOpen(), i-ind);
                        return;
                    }else {
                        //close the trade at target
                        T.close(mData.elementAt(i).getDate(), T.getTarget(), i-ind);
                        return;
                    }
                } else if(mData.elementAt(i).getLow() < T.getStopLoss()) {
                    //check if there is a gap down
                    if(mData.elementAt(i).getOpen() < T.getStopLoss()) {
                        //get out at the open
                        T.close(mData.elementAt(i).getDate(), mData.elementAt(i).getOpen(), i-ind);
                        return;
                    }else {
                        //get out at stoploss
                        T.close(mData.elementAt(i).getDate(), T.getStopLoss(), i-ind);
                        return;
                    }

                }
            }else {// it is a short trade
                if(mData.elementAt(i).getLow() <= T.getTarget()) { //it is a win
                    //consider a gap day
                    if(mData.elementAt(i).getOpen() < T.getTarget()) {
                        //close at open  a gap down day
                        T.close(mData.elementAt(i).getDate(), mData.elementAt(i).getOpen(), i-ind);
                        return;
                    }else {
                        //close the trade at target
                        T.close(mData.elementAt(i).getDate(), T.getTarget(), i-ind);
                        return;
                    }
                } else if(mData.elementAt(i).getHigh() >= T.getStopLoss()) {
                    //check if there is a gap down
                    if(mData.elementAt(i).getOpen() > T.getStopLoss()) {
                        //get out at the open
                        T.close(mData.elementAt(i).getDate(), mData.elementAt(i).getOpen(), i-ind);
                        return;
                    }else {
                        //get out at stoploss
                        T.close(mData.elementAt(i).getDate(), T.getStopLoss(), i-ind);
                        return;
                    }

                }

            }
        }//end of for
        //if we get here the trade is not closed, close it at the close of the last day
        T.close(mData.elementAt(mData.size()-1).getDate(), mData.elementAt(mData.size()-1).getClose(), mData.size()-1-ind);
    }
 */

    public boolean test() {
        if(!loaded) {
            loadData();
            if (!loaded) {
                System.out.println("cannot load data");
                return false;
            }
        }

        //TODO: Code your pattern here

        for(int i = 10; i <mData.size()-11; i++) {
            if(xDaysLow(i, 10) //20 days low, close near low of the day
                    && mData.elementAt(i-1).getClose() <  mData.elementAt(i-2).getClose()
                    && mData.elementAt(i-2).getClose() <  mData.elementAt(i-3).getClose()
                    && mData.elementAt(i-3).getClose() <  mData.elementAt(i-4).getClose()
                    && (mData.elementAt(i).getClose() - mData.elementAt(i).getLow())/(mData.elementAt(i).range()) < 0.1)
            {
                float entryprice = mData.elementAt(i+1).getOpen();
                float exitPrice = mData.elementAt(i+1+riskFactor).getClose();
                Trade T = new Trade();
                T.open(mSymbol, mData.elementAt(i+1).getDate(), entryprice, Direction.LONG);
                T.close(mData.elementAt(i+1+riskFactor).getDate(),exitPrice,riskFactor);
                //add the trade to the Trade vector
                mTrades.add(T);

            }else if(xDaysHigh(i, 10) // 20 days high & i-1,i-2, and i-3: close higher than close of previous day
                    && mData.elementAt(i-1).getClose()>  mData.elementAt(i-2).getClose()
                    && mData.elementAt(i-2).getClose()>  mData.elementAt(i-3).getClose()
                    && mData.elementAt(i-3).getClose()>  mData.elementAt(i-4).getClose()
                    && (mData.elementAt(i).getHigh() - mData.elementAt(i).getClose())/(mData.elementAt(i).getHigh() - mData.elementAt(i).range()) < 0.1)
            // close near the high 10%
            {
                //buy at open of i+1 (tomorrow)
                float entryprice = mData.elementAt(i+1).getOpen();
                float exitPrice = mData.elementAt(i+1+riskFactor).getClose();
                Trade T = new Trade();
                T.open(mSymbol, mData.elementAt(i+1).getDate(), entryprice, Direction.SHORT);
                T.close(mData.elementAt(i+1+riskFactor).getDate(),exitPrice,riskFactor);
                mTrades.add(T);
            }
        }
        return true;
    }

}
