package com.amazonaws.samples;

import java.util.ArrayList;
import java.util.List;

import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.cloudwatch.AmazonCloudWatchClient;
import com.amazonaws.services.cloudwatch.model.DescribeAlarmsRequest;
import com.amazonaws.services.cloudwatch.model.DescribeAlarmsResult;
import com.amazonaws.services.cloudwatch.model.Dimension;
import com.amazonaws.services.cloudwatch.model.MetricAlarm;
import com.amazonaws.services.cloudwatch.model.PutMetricAlarmRequest;
import com.amazonaws.services.cloudwatch.model.PutMetricAlarmResult;
import com.amazonaws.services.cloudwatch.model.StandardUnit;
import com.amazonaws.services.databasemigrationservice.model.DescribeAccountAttributesRequest;


public class CloudWatchAlarms {

	public static void main(String[] args) {
	    
		StandardUnit su=null;
		
		String UserAccountNo="274304159946";
		
		String UserAccountName="Progressive_Development";
		
		String snsName="Progressive_Development";
	    
		//Double Threshhold[]={250.0, 500.0, 750.0, 1000.0,1250.0,1900.0};
		Double Threshhold[]={100.0, 1000.0, 5000.0, 10000.0,15000.0,20000.0,30000.0,50000.0,75000.0,99000.0};
		//BasicAWSCredentials bcp=new BasicAWSCredentials("AKIAIIILKAFMIZN3LROQ","tiIj3ma9u46f/rulEQBeksf1pf83I5Jv41pZwiFp");
		BasicAWSCredentials bcp=new BasicAWSCredentials("AKIAISRKTYXPNUMSOGQQ","s8zmRPBnMxAcDtvE/tjr3gYV4unQG8goUILFSNs9");
		AmazonCloudWatchClient cw =new AmazonCloudWatchClient(bcp);
		cw.setRegion((Region)Region.getRegion(Regions.US_EAST_1));

					List<Dimension> dimension = new ArrayList<Dimension>();
			   
			    Dimension dimension1 =new Dimension()
			    .withName("LinkedAccount")
			    .withValue(UserAccountNo);
			    
			    Dimension dimension2 =new Dimension()
			    .withName("Currency")
			    .withValue("USD");
			    
			    dimension.add(dimension1);
			    dimension.add(dimension2);
               List<String> alarmActions=new ArrayList<String>();
              // alarmActions.add("arn:aws:sns:us-east-1:558465961352:"+snsName);
               alarmActions.add("arn:aws:sns:us-east-1:481183158878:"+snsName);
	for(Double i : Threshhold ){		    
			try{
			PutMetricAlarmRequest request = new PutMetricAlarmRequest();
			request.setAlarmName(AccountName+" >="+i);
			request.setAlarmDescription("Programmatically Created Billing Alarm For "+UserAccountName+" >="+i);
			request.setThreshold(i);
			request.setMetricName("EstimatedCharges");
			request.setComparisonOperator("GreaterThanOrEqualToThreshold");
			request.setPeriod(21600);
			request.setEvaluationPeriods(1);
			request.setNamespace("AWS/Billing");
			request.setStatistic("Maximum");
			request.setDimensions(dimension);
			request.setActionsEnabled(true);
			request.setAlarmActions(alarmActions);
			request.setUnit((String)null);
			request.setTreatMissingData(null);
			request.setExtendedStatistic(null);
			cw.putMetricAlarm(request);
			Thread.sleep(3000);
			}
			catch(Exception e){
				e.printStackTrace();
			}
	}
			DescribeAlarmsRequest desc=new  DescribeAlarmsRequest();
			DescribeAlarmsResult result=cw.describeAlarms(desc);
			List<MetricAlarm> metrics= result.getMetricAlarms();
            
		/*	
			
			for(MetricAlarm metric : metrics){
				System.out.println("AlarmName "+metric.getAlarmName());
				System.out.println("MetricName "+metric.getMetricName());
				System.out.println("ComparisonOperator "+metric.getComparisonOperator());
				System.out.println("Threshold "+metric.getThreshold());
				System.out.println("Period "+metric.getPeriod());
				System.out.println("EvaluationPeriods "+metric.getEvaluationPeriods());
				System.out.println("Namespace "+metric.getNamespace());
				System.out.println("Statistic "+metric.getStatistic());
				System.out.println("Dimension "+metric.getDimensions());
				System.out.println("AlarmActions "+metric.getAlarmActions());
				System.out.println("EvaluateLowSampleCountPercentile "+metric.getEvaluateLowSampleCountPercentile());
				System.out.println("ExtendedStatistic "+metric.getExtendedStatistic());
				System.out.println("TreatMissingData "+metric.getTreatMissingData());
				System.out.println("Unit "+metric.getUnit());
				System.out.println("EvaluationPeriods "+metric.getEvaluationPeriods());
				break;
			}
			*/
			
		//	PutMetricAlarmResult response = cw.putMetricAlarm(request);
		
		

	}

}
