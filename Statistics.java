package project;

public class Statistics {
    double averageProfit;
    double averageHoldingPeriod;
    double averageProfitPerDay;
    double winningPercent;
    double averageProfitLong;
    double averageHoldingPeriodLong;
    double averageProfitPerDayLong;
    double winningPercentLong;
    //Do the same fgor shorts
    double averageProfitShort;
    double averageHoldingPeriodShort;
    double averageProfitPerDayShort;
    double winningPercentShort;
    int numOfTrades, numOfShortTrades, numOfLongTrades;


    public String toString() {
        return "Statistics \n { " +
                "Total trades = "+numOfTrades+
                "\n averageProfit=" + averageProfit +
                ", averageHoldingPeriod=" + averageHoldingPeriod +
                ", averageProfitPerDay=" + averageProfitPerDay +
                ", winningPercent=" + winningPercent + "\n" +
                " Number of long trades ="+numOfLongTrades+"\n" +
                ", averageProfitLong=" + averageProfitLong +
                ", averageHoldingPeriodLong=" + averageHoldingPeriodLong +
                ", averageProfitPerDayLong=" + averageProfitPerDayLong +
                ", winningPercentLong=" + winningPercentLong + "\n" +
                " Number of short trades ="+numOfShortTrades+"\n" +
                ", averageProfitShort=" + averageProfitShort +
                ", averageHoldingPeriodShort=" + averageHoldingPeriodShort +
                ", averageProfitPerDayShort=" + averageProfitPerDayShort +
                ", winningPercentShort=" + winningPercentShort +
                '}';
    }

}
