package rtrtk.pnrs1.ra54_2014;

/**
 * Created by alexa on 5.6.17..
 */

public class CalculateStatisticsNative {
    public native int getStatistic(int totalCnt, int finishedCnt);

    static {
        System.loadLibrary("StatisticNative");
    }
}
