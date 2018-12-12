package com.ydhl.outsourcing.ts.finance.common.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @author Martins
 * @create 2018/1/12 17:17.
 */
public class DateUtilHelper {
	
	private final static SimpleDateFormat sdfYear = new SimpleDateFormat("yyyy");
	private final static SimpleDateFormat sdfDay = new SimpleDateFormat("yyyy-MM-dd");
	private final static SimpleDateFormat sdfDays = new SimpleDateFormat("yyyyMMdd");
	private final static SimpleDateFormat sdfTime = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");

	/**
	 * 获取YYYY格式
	 * @return
	 */
	public static String getYear() {
		return sdfYear.format(new Date());
	}

	/**
	 * 获取YYYY-MM-DD格式
	 * @return
	 */
	public static String getDay() {
		return sdfDay.format(new Date());
	}
	
	/**
	 * 获取YYYYMMDD格式
	 * @return
	 */
	public static String getDays(){
		return sdfDays.format(new Date());
	}

	/**
	 * 获取YYYY-MM-DD HH:mm:ss格式
	 * @return
	 */
	public static String getTime(Date date) {
		return sdfTime.format(date);
	}

	/**
	* @Title: compareDate
	* @Description: TODO(日期比较，如果s>=e 返回true 否则返回false)
	* @param s
	* @param e
	* @return boolean  
	* @throws
	* @author ectrip
	 */
	public static boolean compareDate(String s, String e) {
		if(fomatDate(s)==null||fomatDate(e)==null){
			return false;
		}
		return fomatDate(s).getTime() >=fomatDate(e).getTime();
	}

	/**
	 * 获取当前日期时间
	 * @param date
	 * @return
	 */
	public static String getDateTime(Date date) {
		return sdfTime.format(date);
	}

	/**
	 * 格式化日期
	 * @return
	 */
	public static Date fomatDate(String date) {
		DateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
		try {
			return fmt.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 校验日期是否合法
	 * @return
	 */
	public static boolean isValidDate(String s) {
		DateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
		try {
			fmt.parse(s);
			return true;
		} catch (Exception e) {
			// 如果throw java.text.ParseException或者NullPointerException，就说明格式不对
			return false;
		}
	}
	
	/**
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	public static int getDiffYear(String startTime,String endTime) {
		DateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
		try {
			//long aa=0;
			int years=(int) (((fmt.parse(endTime).getTime()-fmt.parse(startTime).getTime())/ (1000 * 60 * 60 * 24))/365);
			return years;
		} catch (Exception e) {
			// 如果throw java.text.ParseException或者NullPointerException，就说明格式不对
			return 0;
		}
	}
	 
	/**
     * <li>功能描述：时间相减得到天数
     * @param beginDateStr
     * @param endDateStr
     * @return
     * long 
     * @author Administrator
     */
    public static long getDaySub(String beginDateStr,String endDateStr){
        long day=0;
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date beginDate = null;
        Date endDate = null;
        
            try {
				beginDate = format.parse(beginDateStr);
				endDate= format.parse(endDateStr);
			} catch (ParseException e) {
				e.printStackTrace();
			}
            day=(endDate.getTime()-beginDate.getTime())/(24*60*60*1000);
            //System.out.println("相隔的天数="+day);
      
        return day;
    }
    
    /**
     * 得到n天之后的日期
     * @param days
     * @return
     */
    public static String getAfterDayDate(String days) {
    	int daysInt = Integer.parseInt(days);
    	
        Calendar canlendar = Calendar.getInstance(); // java.util包
        canlendar.add(Calendar.DATE, daysInt); // 日期减 如果不够减会将月变动
        Date date = canlendar.getTime();
        
        SimpleDateFormat sdfd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateStr = sdfd.format(date);
        
        return dateStr;
    }
    
    /**
     * 得到n天之后是周几
     * @param days
     * @return
     */
    public static String getAfterDayWeek(String days) {
    	int daysInt = Integer.parseInt(days);
        Calendar canlendar = Calendar.getInstance(); // java.util包
        canlendar.add(Calendar.DATE, daysInt); // 日期减 如果不够减会将月变动
        Date date = canlendar.getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("E");
        String dateStr = sdf.format(date);
        return dateStr;
    }

	/**
	 * 日期字符串转换为数组 格式：yyyy年mm月dd日
	 * @param date
	 * @return
     */
	public static Integer[] stringToIntegerArray(String date){
		Integer[] d = new Integer[3];
		d[0] = Integer.parseInt(date.split("年")[0]);
		d[1] = Integer.parseInt(date.split("年")[1].split("月")[0]);
		d[2] = Integer.parseInt(date.split("年")[1].split("月")[1].split("日")[0]);
		return d;
	}

	/**
	 * 日期转换为数组
	 * @param date
	 * @return
     */
	public static Integer[] dateToIntegerArray(Date date){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
		String dd = sdf.format(date);
		Integer[] d = stringToIntegerArray(dd);
		return d;
	}

	/**
	 * 字符串转换为日期 格式：yyyy年mm月dd日
	 * @param d
	 * @return
     */
	public static Date stringToDate(String d){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy年mm月dd日");
		Date date = null;
		Integer[] ds = stringToIntegerArray(d);
		try{
			//date = sdf.parse(d);
			Calendar c = Calendar.getInstance(); //获得当前时间
			if(ds[1]==2){
				if(ds[2]==30){
					if ((ds[0]%4==0)&&(ds[0]%100!=0)||(ds[0]%400==0)){
						c.set(Calendar.DAY_OF_MONTH,29);
					}else{
						c.set(Calendar.DAY_OF_MONTH,28);
					}
				}else{
					c.set(Calendar.DAY_OF_MONTH,ds[2]);
				}
			}else{
				c.set(Calendar.DAY_OF_MONTH,ds[2]);
			}
			c.set(Calendar.YEAR,ds[0]);
			c.set(Calendar.MONTH,ds[1]-1);
			return c.getTime();
		}catch(Exception e){
			System.out.println(e.getMessage());
		}
		return date;
	}

    
    public static void main(String[] args) {
    	System.out.println(getDays());
    	System.out.println(getAfterDayWeek("3"));
		Calendar calendar=Calendar.getInstance();
		calendar.set(2017,3,30);  //年月日  也可以具体到时分秒如calendar.set(2015, 10, 12,11,32,52);
		Date date=calendar.getTime();
		System.out.println(date);
		dateToIntegerArray(date);
		date = stringToDate("2017年2月2日");
		System.out.println(date);
		date = fomatDate("2017-2-2");
		System.out.println(date);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String d = sdf.format(new Date());
		System.out.println(d);
    }

}
