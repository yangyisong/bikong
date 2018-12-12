package com.ydhl.outsourcing.ts.finance.common.utils;

import com.ydhl.outsourcing.ts.finance.dto.ContractDto;
import com.ydhl.outsourcing.ts.finance.dto.UserDto;
import com.ydhl.outsourcing.ts.finance.enums.ApprovalType;
import com.ydhl.outsourcing.ts.finance.enums.IncomeType;
import com.ydhl.outsourcing.ts.finance.model.PersonalIncome;

import java.math.BigDecimal;
import java.util.*;

/**
 * Created by dell on 2018/1/24.
 */
public class PersonalIncomeUtil {
    public static PersonalIncome[] sum(ContractDto contractDto, Integer productCycle, Integer settlementCycle){
        Integer range = 2;
        if(contractDto.getIntOrB()){
            range = 0;
        }
        PersonalIncome[] demos = null;
        PersonalIncome demo = null;
        BigDecimal money = contractDto.getAmount();//总金额
        BigDecimal profits = contractDto.getEarningRatio();//收益比
        BigDecimal everySum = new BigDecimal(0);//每笔金额
        Date settleTime = null; //结算日期
        String endTime = null;
        String settleStartTime = null;
        Integer[] beginTime = DateUtilHelper.dateToIntegerArray(contractDto.getStartTime());
        //每天金额
        //BigDecimal dayMoney = contractDto.getEarningsDayAmount();
        BigDecimal dayMoney = contractDto.getAmount().multiply(contractDto.getEarningRatio().divide(new BigDecimal(100))).divide(new BigDecimal(360),10,BigDecimal.ROUND_CEILING);

        if(beginTime[2]==1){
            demos = new PersonalIncome[productCycle];
        }else{
            demos = new PersonalIncome[productCycle+1];
        }
        for(int i=0;i<productCycle;i++){
            Integer bt = beginTime[1]+i;
            if(bt-beginTime[1]==0){
                if(bt<=12){
                    everySum = new BigDecimal(31-beginTime[2]).multiply(dayMoney).setScale(range,BigDecimal.ROUND_HALF_UP);
                    endTime = beginTime[0]+"年"+bt+"月30日";
                    settleStartTime = beginTime[0]+"年"+bt+"月"+beginTime[2]+"日";
                    //settleTime = DateUtilHelper.integerArrayToDate(new Integer[]{beginTime[0],bt,30});
                    settleTime = DateUtilHelper.stringToDate(endTime);

                    //demo = new Demo(d, everySum, 0, bt, year);
                    demo = new PersonalIncome();
                    demo.setSettleStartTime(DateUtilHelper.stringToDate(settleStartTime));
                    demo.setEndTime(endTime);
                    setPersonallIncomeAttr(contractDto,demo,settlementCycle);
                    demo.setIncome(everySum);
                    demo.setSettleEndTime(settleTime);
                    demo.setSeq(0);
                    demos[i] = demo;
                    if(beginTime[2]-1!=0){
                        everySum = new BigDecimal(beginTime[2]-1).multiply(dayMoney).setScale(range,BigDecimal.ROUND_HALF_UP);
                        if(bt+productCycle>12){
                            endTime = (beginTime[0]+1)+"年"+((bt+productCycle)%12==0?12:(bt+productCycle)%12)+"月"+(beginTime[2]-1)+"日";
                            settleStartTime = (beginTime[0]+1)+"年"+((bt+productCycle)%12==0?12:(bt+productCycle)%12)+"月1日";
                            settleTime = DateUtilHelper.stringToDate(endTime);
                        }else{
                            endTime = beginTime[0]+"年"+(bt+productCycle)+"月"+(beginTime[2]-1)+"日";
                            settleStartTime = beginTime[0]+"年"+(bt+productCycle)+"月1日";
                            settleTime = DateUtilHelper.stringToDate(endTime);
                        }
                        demo = new PersonalIncome();
                        demo.setSettleStartTime(DateUtilHelper.stringToDate(settleStartTime));
                        demo.setEndTime(endTime);
                        setPersonallIncomeAttr(contractDto,demo,settlementCycle);
                        demo.setSettleEndTime(settleTime);
                        demo.setIncome(everySum);
                        demo.setSeq(0);
                        if(bt%settlementCycle==0){
                            demo.setSeq(1);
                        }
                        demos[demos.length-1] = demo;
                    }
                }
            }else{
                everySum = new BigDecimal(30).multiply(dayMoney).setScale(range,BigDecimal.ROUND_HALF_UP);
                if(bt<=12){
                    endTime = beginTime[0]+"年"+bt+"月30日";
                    settleStartTime = beginTime[0]+"年"+bt+"月1日";
                    settleTime = DateUtilHelper.stringToDate(endTime);
                }else{
                    endTime = (beginTime[0]+1)+"年"+bt%12+"月30日";
                    settleStartTime = (beginTime[0]+1)+"年"+bt%12+"月1日";
                    settleTime = DateUtilHelper.stringToDate(endTime);
                }
                demo = new PersonalIncome();
                demo.setSettleStartTime(DateUtilHelper.stringToDate(settleStartTime));
                demo.setEndTime(endTime);
                setPersonallIncomeAttr(contractDto,demo,settlementCycle);
                demo.setSettleEndTime(settleTime);
                demo.setIncome(everySum);
                demo.setSeq(0);
                demos[i] = demo;
            }
            if(bt%settlementCycle==0){
                demo = demos[i];
                demo.setSeq(1);
            }
            if(demos[demos.length-1]!=null){
                demos[demos.length-1].setSeq(1);
            }
        }
        return demos;
    }

    public static List<PersonalIncome> getCycleMoney(ContractDto contractDto, PersonalIncome[] demos, Integer settlementCycle){
        Integer range = 2;
        if(contractDto.getIntOrB()){
            range = 0;
        }
        List<PersonalIncome> d = new ArrayList<PersonalIncome>();
        PersonalIncome demo = null;
        BigDecimal money = contractDto.getAmount();
        BigDecimal total = new BigDecimal(0);
        Boolean flag = true;
        Date startTime = null;
        for (int i = 0; i < demos.length; i++) {
            total = total.add(demos[i].getIncome()).setScale(range,BigDecimal.ROUND_HALF_UP);
            if(demos[i].getSeq()==1){
                //demo = new Demo(demos[i].getDate(), total, demos[i].getFlag(), demos[i].getMonth(), demos[i].getYear());
                demo = new PersonalIncome();
                if(flag){
                    demo.setSettleStartTime(demos[i].getSettleStartTime());
                }else{
                    demo.setSettleStartTime(startTime);
                }
                demo.setEndTime(demos[i].getEndTime());
                setPersonallIncomeAttr(contractDto,demo,settlementCycle);
                demo.setIncome(total);
                demo.setSettleEndTime(demos[i].getSettleEndTime());
                demo.setSeq(demos[i].getSeq());
                d.add(demo);
                total = new BigDecimal(0);
                flag = true;
            }else{
                if(flag){
                    startTime = demos[i].getSettleStartTime();
                    flag = false;
                }
            }
            if(i==demos.length-1){
                demo = new PersonalIncome();
                demo.setSettleStartTime(demos[i].getSettleEndTime());
                demo.setEndTime(demos[i].getEndTime());
                setPersonallIncomeAttr(contractDto,demo,settlementCycle);
                demo.setSettleEndTime(demos[i].getSettleEndTime());
                demo.setDayNums(0);
                demo.setSeq(-1);
                demo.setIncome(money);
                //d.add(new Demo(demo.getDate(), money, -1, demo.getMonth(), demos[i].getYear()));
                d.add(demo);
            }
        }
        return d;
    }

    public static Map<String, Object> getRandom(ContractDto contractDto, List<PersonalIncome> list, Date dt, Integer settlementCycle){
        BigDecimal dayMoney = contractDto.getAmount().multiply(contractDto.getEarningRatio().divide(new BigDecimal(100))).divide(new BigDecimal(360),10,BigDecimal.ROUND_CEILING);;
        Integer range = 2;
        if(contractDto.getIntOrB()){
            range = 0;
        }
        Map<String, Object> map = new HashMap<>();
        List<PersonalIncome> randomDemos = new ArrayList<PersonalIncome>();
        Integer[] d = DateUtilHelper.dateToIntegerArray(dt);
        Integer[] beginTime = DateUtilHelper.dateToIntegerArray(contractDto.getStartTime());
        Integer year = 0;
        Integer month = 0;
        Date settleTime = null;
        String endTime = null;
        boolean f = true;
        for (PersonalIncome demo : list) {
            if(demo.getSeq()==-1){
                String number = demo.getIncomeNumber();
                endTime = d[0]+"年"+d[1]+"月"+d[2]+"日";
                settleTime = DateUtilHelper.stringToDate(endTime);
                demo.setEndTime(endTime);
                demo.setIncomeNumber(number);
                demo.setDayNums(0);
                demo.setSettleStartTime(settleTime);
                setPersonallIncomeAttr(contractDto,demo,settlementCycle);
                demo.setSettleEndTime(settleTime);
                demo.setSeq(-1);
                randomDemos.add(demo);
                continue;
            }
            Integer minDay = 0;
            Integer minMonth = 0;
            year = DateUtilHelper.stringToIntegerArray(demo.getEndTime())[0];
            month = DateUtilHelper.stringToIntegerArray(demo.getEndTime())[1];

            //
            Integer mmonth = DateUtilHelper.dateToIntegerArray(demo.getSettleStartTime())[1];

            if(month>=d[1] && d[1]>=mmonth && f){
                if(beginTime[0]-d[0]==0){
                    if(settlementCycle==12){
                        minDay = beginTime[2];
                        minMonth = beginTime[1];
                    }
                    if(settlementCycle==3){
                        minDay = 1;
                        if(d[1]%3!=0){
                            minMonth = d[1]/3*3 + 1;
                        }else{
                            minMonth = d[1]-2;
                        }
                        if(minMonth<=beginTime[1]){
                            minMonth = beginTime[1];
                            minDay = beginTime[2];
                        }
                    }
                    if(settlementCycle==1){
                        minDay = 1;
                        minMonth = d[1];
                        if(minMonth==beginTime[1]){
                            minDay = beginTime[2];
                        }
                    }
                }else{
                    if(settlementCycle==12){
                        minDay = 1;
                        minMonth = 1;
                    }
                    if(settlementCycle==3){
                        minDay = 1;
                        if(d[1]%3!=0){
                            minMonth = d[1]/3*3 + 1;
                        }else{
                            minMonth = d[1]-2;
                        }
                    }
                    if(settlementCycle==1){
                        minDay = 1;
                        minMonth = d[1];
                    }
                }
                Integer days = (d[1]-minMonth)*30+(d[2]-minDay);
                System.out.println("天数："+days);
                BigDecimal mon = new BigDecimal(days).multiply(dayMoney).setScale(range,BigDecimal.ROUND_HALF_UP);
                System.out.println(dayMoney+"："+mon);
                endTime = d[0]+"年"+d[1]+"月"+d[2]+"日";
                demo.setEndTime(endTime);
                setPersonallIncomeAttr(contractDto,demo,settlementCycle);
                demo.setIncome(mon);
                //demo.setSettleStartTime(d);
                demo.setSettleEndTime(DateUtilHelper.stringToDate(endTime));
                demo.setDayNums(days);
                demo.setSeq(1);
                randomDemos.add(demo);
                map.put("list", randomDemos);
                map.put("days", days);
                f = false;
            }
        }
        return map;
    }

    public static void setPersonallIncomeAttr(ContractDto contractDto,PersonalIncome personalIncome, Integer settlementCycle){
        Integer[] settleStartTime = DateUtilHelper.dateToIntegerArray(personalIncome.getSettleStartTime());
        Integer[] endTime = DateUtilHelper.stringToIntegerArray(personalIncome.getEndTime());
        String incomeNumber = UUID.randomUUID().toString();
        //personalIncome.setIncomeNumber((personalIncome!=null&&StringUtilPlus.isNoneEmpty(personalIncome.getIncomeNumber()))?personalIncome.getIncomeNumber():incomeNumber);
        personalIncome.setIncomeNumber(incomeNumber);
        personalIncome.setContractNumber(contractDto.getNumber());
        personalIncome.setUserId(contractDto.getUserId());
        personalIncome.setCustomerId(contractDto.getCustomerId());
        if(settlementCycle==1){
            if(settleStartTime[2]!=1){
                personalIncome.setDayNums(30-settleStartTime[2]+1);
            }else if(endTime[2]!=30){
                personalIncome.setDayNums(endTime[2]-settleStartTime[2]+1);
            }else{
                personalIncome.setDayNums(30);
            }
        }else if(settlementCycle==3){
            personalIncome.setDayNums((endTime[1]-settleStartTime[1])*30+endTime[2]-settleStartTime[2]+1);
        }else{
            personalIncome.setDayNums((endTime[1]-settleStartTime[1])*30+endTime[2]-settleStartTime[2]+1);
        }
        /*if(settleStartTime[2]!=1){
            personalIncome.setDayNums(settlementCycle*30-settleStartTime[2]+1);
        }else{
            personalIncome.setDayNums(settlementCycle*30);
        }*/
        personalIncome.setIncomeType(IncomeType.N_S);
        personalIncome.setStruts(ApprovalType.W_P);
        personalIncome.setSeq(0);
        personalIncome.setCustomerName(contractDto.getCustomerName());
        personalIncome.setUserRealname(contractDto.getUser().getRealname());
        personalIncome.setPrincipal(false);
    }

    public static void main(String args[]){
        /*System.out.println("四舍五入取整:(2)=" + new BigDecimal("2.0049").setScale(2, BigDecimal.ROUND_HALF_UP));
        System.out.println("四舍五入取整:(2.1)=" + new BigDecimal("2.0059").setScale(2, BigDecimal.ROUND_HALF_UP));
        System.out.println("四舍五入取整:(2.49)=" + new BigDecimal("2.49").setScale(0, BigDecimal.ROUND_HALF_UP));
        System.out.println("四舍五入取整:(2.9)=" + new BigDecimal("2.9").setScale(0, BigDecimal.ROUND_HALF_UP));*/
        ContractDto contractDto = new ContractDto();
        contractDto.setAmount(new BigDecimal(80000));
        contractDto.setEarningRatio(new BigDecimal(15));
        contractDto.setIntOrB(false);
        String sT = "2018年3月15日";
        Date date = DateUtilHelper.stringToDate(sT);
        contractDto.setStartTime(date);
        contractDto.setCustomerName("龙哥");
        UserDto userDto = new UserDto();
        userDto.setRealname("李小龙");
        contractDto.setUser(userDto);
        //BigDecimal dayNum = new BigDecimal(2000).multiply(new BigDecimal(0.1)).divide(new BigDecimal(360),10,BigDecimal.ROUND_CEILING);
        //System.out.println(dayNum);
        //contractDto.setEarningsDayAmount(dayNum);
        PersonalIncome[] personalIncomes = sum(contractDto,12,12);
        for (int i=0;i<personalIncomes.length;i++){
            System.out.println(personalIncomes[i]);
        }
        System.out.println("-------------");
        List<PersonalIncome> d = getCycleMoney(contractDto,personalIncomes,12);
        for (PersonalIncome demo : d) {
            System.out.println(demo);
        }
        System.out.println("=============");
        sT = "2018年8月12日";
        date = DateUtilHelper.stringToDate(sT);
        System.out.println(date);
        Map<String, Object> randomDemos = getRandom(contractDto,d,date,12);

        for(String key:randomDemos.keySet()){
            System.out.println("key="+key+"and value=" +randomDemos.get(key));
        }
    }
}
