package project;

import java.io.File;
import java.io.IOException;
import java.util.Vector;

public class Tester {
    private Vector<String> vSymbols;
    private Vector<Trade> mTrades;
    private String mPath, mFile;
    private int riskFactors;
    public void setPath(String p){
        mPath = p;
    }
    public void setFile(String f){
        mFile = f;
    }
    public Tester(String path, String file, int risk) throws IOException {
        mPath=path;
        mFile=file;
        riskFactors=risk;
        mTrades = new Vector<Trade>();
        vSymbols = Helper.loadSymbols(mPath,mFile);
    }

    public boolean run() throws IOException {
        if(vSymbols.isEmpty())
        {
            vSymbols = new Vector<String>();
        }
        if(mTrades==null)
        {
            mTrades = new Vector<Trade>();
        }

        for (String symbol : vSymbols)
        {
            SymbolTester tester = new SymbolTester(symbol, mPath, riskFactors);
            tester.test();
            mTrades.addAll(tester.getTrades());
        }
        Statistics re = Helper.computeStats(mTrades);
        System.out.println(re);
        return true;
    }
    public Vector<Trade> getTrades(){
        return mTrades;
    }
    public void reset(){
        vSymbols = null;
        mTrades = null;
    }




}
