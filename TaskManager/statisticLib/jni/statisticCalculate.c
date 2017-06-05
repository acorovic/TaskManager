#include "statisticCalculate.h"

#include <jni.h>
#include <math.h>

JNIEXPORT jint JNICALL Java_rtrtk_pnrs1_ra54_12014_CalculateStatisticsNative_getStatistic
  (JNIEnv *env, jobject obj, jint totalCnt, jint finishedCnt)
{
	if(totalCnt != 0) {
		return (jint) (finishedCnt*100/totalCnt);
		}
    else {
        return (jint) 0;
    }
    
}

