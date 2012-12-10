package Tools;


public class EngineCore {
	public static boolean isWindows = true;
	
	//Scheduler...
	public static int RoundRobin = 0;
	
	public static int engineTimeSlot = 50;
	public static int enginePackSize = 200;
	public static int engineQueueSize = 200;
	
	public static int engineScheduler = 0;

	//Item Types...
	public final static int Reader = 0;
	public final static int Writer = 1;
	public final static int NotNull = 2;
	public final static int Filter = 3;
	public final static int Sorter = 4;
	public final static int Aggregator = 5;
	
	public static boolean isUnixSort = true;
	public static boolean isAggregationWithSort = true;
	
}
