package com.surgical.decision3.common.bean.dateTimeStamp;

import com.surgical.decision3.common.constant.MainConstants;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Root;

/**
 * WARNING: When transform XML to Java object with parameter of date and time (string)
 * The dateTime object is not set. make sure that this dateTime value is created.  
 * */
@Root
public class DateTimeStamp
{
	private SimpleDateFormat dateFormat;
	private SimpleDateFormat timeFormat;
	private SimpleDateFormat dateTimeFormat;

	Date dateTime;
	
//	@Text
	String dateTimeString;

	@Attribute (required=false)
	String date;

	@Attribute (required=false)
	String time;
	
//	public String getDateTimeString()
//	{
//		return this.dateTimeString;
//	}
//	
//	public void setDateTimeString( String date, String time )
//	{
//		this.dateTimeString = date + " " + time;
//	}
//	
//	public void setDateTimeString( String dateTimeString1 )
//	{
//		this.dateTimeString = dateTimeString1;
//		
//		this.date = dateTimeString1.sub
//	}
	
	
	/*
	  @Text
   public String getText() {
      return text;           
   }

   @Text
   public void setText(String text){
      this.text = text;           
   }
   
   public String getName() {
      return name;           
   }
	 * */
	
	public DateTimeStamp()
	{
		super();
		
		dateTimeFormat = new SimpleDateFormat( "MM/dd/yyyy HH:mm:ss" );
		dateTimeFormat.setTimeZone( TimeZone.getTimeZone( MainConstants.TIME_ZONE_BANGKOK ) );
		
		dateFormat = new SimpleDateFormat( "MM/dd/yyyy" );
		dateFormat.setTimeZone( TimeZone.getTimeZone( MainConstants.TIME_ZONE_BANGKOK ) );
		
		timeFormat = new SimpleDateFormat( "HH:mm:ss" );
		timeFormat.setTimeZone( TimeZone.getTimeZone( MainConstants.TIME_ZONE_BANGKOK ) );

//		dateTimeFormat = new SimpleDateFormat( "yyyy/MM/dd HH:mm:ss" );
//		dateFormat = new SimpleDateFormat( "yyyy/MM/dd" );
//		timeFormat = new SimpleDateFormat( "HH:mm:ss" );
	}

	//dd-MMM-YY
//	public DateTimeStamp(String dateString)
//	{
//		String day = dateString.substring( 0, 1 );
//		String month = dateString.substring( 3, 5);
//		String year = dateString.substring( 7, 8);
//		
//		date = new date( )
//	}

	public DateTimeStamp(Date date)
	{
		super();
		
		// Set date format
		dateTimeFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
		dateTimeFormat.setTimeZone( TimeZone.getTimeZone( MainConstants.TIME_ZONE_BANGKOK ) );
		    
		dateFormat = new SimpleDateFormat( "MM/dd/yyyy" );
		dateFormat.setTimeZone( TimeZone.getTimeZone( MainConstants.TIME_ZONE_BANGKOK  ) );
		
		timeFormat = new SimpleDateFormat( "HH:mm:ss" );
		timeFormat.setTimeZone( TimeZone.getTimeZone( MainConstants.TIME_ZONE_BANGKOK  ) );
				
		this.dateTime = date;
		this.dateTimeString = dateTimeFormat.format( date );
		this.date = dateFormat.format( date );
		this.time = timeFormat.format( date );
	}

	public Date getDateTime()
	{
		return dateTime;
	}
	
	public String getDateTimeString()
	{
		return dateTimeString;
	}

//	public Date createDateTimeByString(String dateString, String timeString)
//			throws ParseException
//	{
//		return dateTimeFormat.parse( ( dateString + " " + timeString ) );
//	}

//	public String getDateTimeString() throws ParseException
//	{
//		if ( this.date != null && this.time != null )
//		{
//			this.dateTime = dateTimeFormat.parse( ( this.date + " " + this.time) );
//		}
//		return null;
//	}

	public void setDateTime(Date date)
	{
		this.dateTime = date;
		this.date = dateFormat.format( date );
		this.time = timeFormat.format( date );
	}

	public String getDate()
	{
		return date;
	}

	public String getTime()
	{
		return time;
	}

	public void setDate(String dateString) throws ParseException
	{
		this.date = dateString;

//		if (this.dateTime == null)
//		{
//			if (this.time != null)
//			{
//				setDateTime( this.date, this.time );
//			}
//		}
	}

	public void setTime(String timeString) throws ParseException
	{
		this.time = timeString;

//		if (this.dateTime == null)
//		{
//			if (this.date != null)
//			{
//				setDateTime( this.date, this.time );
//			}
//		}
	}
}
