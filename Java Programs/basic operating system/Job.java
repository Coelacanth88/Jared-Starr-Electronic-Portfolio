/*
 * @Jared Starr   
 * @assignemnt CS350 Term Project 
 * @date 04/08/2014
 *
*/



public class Job {
    
    private int jobID = 0; // 1.a Identifies the job
    private int jobSize = 0; // 1.b Identifies the size of the job randomly between (12-50)
    private int exTime = 0; // 1.c Identifies the time to complete randomly between (2-10)
    private int memSeg; // 1.d Identifies the segment of memory the job is in
    private int remaining = 0; // 1.e Identifies the remaining time the job has left
    private String status = ""; // 1.f Identifies the status of the job
    
    
    private int arrival; // Identifies the time that the job arrives in the que
    private boolean Qed; // Identifies whether the job is in queu or not
    
    
    // The default Job Constructor
    public Job(){
        
        jobSize = 12 + (int)(Math.random() * ((50-12) + 1)); // Makes the size of the job a random number between 12-50
        exTime = 2 + (int)(Math.random() * ((10 - 2) + 1)); // Makes the execution time a random number between 2-10
        remaining = exTime; // Since no time has passed, remaining time = the execution time
        memSeg = -1; // Since the job is not in a memory segment yet, memSeg = -1
        Qed = false; //It is not in a queue
        status = "Waiting"; // Since the job is not in a queue it is waiting
		//arrival = 0; //all of the jobs arrive at the same time of 0 
    }
	public Job(int i){
	
		jobSize = i;
		exTime = 0;
		remaining = exTime;
		memSeg = -1;
		status = "Waiting";
	    Qed = false;
		
	}
    
    
    /********Getters and Setters for Job********/
    
    public int getjobID(){ //1.a
        return jobID;
    }
    
    public void setJobID(int x){ //1.a
        jobID = x;
    }
    
    public int getSize(){ //1.b
        return jobSize;
    }
    
    public void setSize(int x){ //1.b
        jobSize = x;
    }
    
    
    public int getexTime(){ //1.c
        return exTime;
    }
    
    public void setexTime(int x){ //1.c
        exTime = x;
    }

    public int getmemSeg(){ //1.d
        return memSeg;
    }
    
    public void setmemSeg(int x){ //1.d
        memSeg = x;
    }
    
    public int getRemaining(){ //1.e
        return remaining;
    }
    
    public void setRemaining(){ //1.e
        remaining = exTime;
    }
    
    public String getStatus(){ //1.f
        return status;
    }
    
    public void setStatus(int x){ //1.f
        String[] statuses = {"Waiting", "Ready", "Running", "Finished"}; // Puts the 4 statuses into an array
        status = statuses[x]; // Uses the index of the array to select the status ie. 1 = Waiting, 2 = Ready, etc.
    }
    
    
    
    
    public int getArrival(){
        return arrival;
    }
    
    public void setArrival(int x){
        arrival = x;
    }
    
    public boolean getQue(){
        return Qed;
    }
    
    public void setQue(boolean x){
        Qed = x;
    }
    
    	
	public void subtractTime(int x){
		remaining -= x;
	}

    
    public String toString(){\
        String toString = "";
        
        toString = "Job ID " + jobID + " Burst Time: " + exTime + " Arrival Time " + arrival 
                + "Job Status " + status + " Job Size " + jobSize + "Remaining Time " + remaining +
                " Memory Segment " + memSeg;
        
        return toString;
    }
    
}