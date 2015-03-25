/*
Jared Starr
CS 350 Term Project
4-8-2014
*/
 
import java.io.*;
import java.util.*;

public class caseOne{
  	
	private int timeUnits = 30; //this is how the 'time' amount is simulated
	private int readyJobs = 0; //this is how many jobs are in the ready queue, initially 0
	private int jobCount = 0; //this keeps track of how many jobs are in memory, initially 0
	private int memoryLoss = 0; //this tracks how much memory is wasted for each particular case
	
	private Queue<Job> rQueue = new LinkedList<Job>(); //rQueue represents the ready queue
    private Queue<Job> jQueue = new LinkedList<Job>(); //jQueue represents the job queue
    private Queue<Job> cQueue = new LinkedList<Job>(); //cQueue represents the completed queue, where jobs that are finished are stored
    private BufferedWriter w;

	
    public caseOne(Job[] job, Segment[] mem)
    {
		jQueue.addAll(Arrays.asList(job));

        int jobs = jQueue.size();
        try {     
            w = new BufferedWriter(new FileWriter("JScaseOneOut.txt")); //this will set up the output file to write the data to
            for(int i = 0; i < timeUnits; i++)
            {
                for(int j = 0; j < jobs; j++)
                { //this will loop through all of the available jobs and attempt to assign memory locations to each of them
					//we need to find a way to see the top value so we know when the job queue is empty, but I don't know how the hell to do that. google them? -eric
                    if(jQueue.peek() != null)
                    { 
                        Job placeHolder = new Job(); //this will create a placeholder job temporarily
                        placeHolder = jQueue.peek(); //set the vaule of the placeholder job to be the first value in the job queue
                        jobCount = 0; //this will set the job count to zero each time the process begins
						
						while(jobCount < mem.length && placeHolder.getmemSeg()==-1)
                        { //this will loop through the memory to find the first fit
                            if(placeHolder.getSize() <= mem[jobCount].getSize() && mem[jobCount].getInUse()==false)
                            {
                                placeHolder.setmemSeg(jobCount); //this will set the placeHolder's segment to the jobCount number, which adds up each iteration
                                placeHolder.setStatus(1); //this will put the placeHolder into the "Ready" status
                                mem[jobCount].addJob(placeHolder); //this will set the placeHolder job into the current place in memory
                                jQueue.remove(); //remove it from the job queue
                            }
                            jobCount++; //add to the jobCount for each iteration
                        }
                        
                        if(placeHolder.getmemSeg() == -1)
                        { 
                            placeHolder = jQueue.remove(); //remove the placeholder value
                            placeHolder.setStatus(0); //set the status back to waiting
                            jQueue.add(placeHolder); //add the placeHolder job back into the job Queue to try again once it rolls around							
                        }
                    }
                }
                for(int k = 0; k < mem.length; k++)
                { //loop through the memory segments
                    if(mem[k].getInUse() && mem[k].getJob().getQue()==false)
                    { //find any segments that are ready and not queued
                        mem[k].getJob().setQue(true);
						rQueue.add(mem[k].getJob());  //add the job that is ready to the ready queue

					}
				}
				if(rQueue.size() <= 4) //this will assign the four 'processors' to work on their own memory segments
					readyJobs = rQueue.size();  //set the size of the jobs in the ready queue to the size of the ready queue
				else 
					readyJobs = 4; //if the readyQueue size is greater than the four 'processors', then it will limit the size back to 4

				for(int l = 0; l < readyJobs; l++)
                { //this will begin execution of all of the processes
					Job currentJob = rQueue.remove(); //remove the currentJob from the ready queue to 
					currentJob.setStatus(2); //this sets the current jobs status to Running
					currentJob.subtractTime(1); //this will subtract one time unit
				   
					if(currentJob.getRemaining() < 1)
                    { //if the remaining time for a job is 0
						currentJob.setStatus(3); //sets the job to 'Finished'
						mem[currentJob.getmemSeg()].removeJob(); //removes the current job from the memory segment in which is allocated
						cQueue.add(currentJob); //this job will be added to the completed queue
					}
					else 
						rQueue.add(currentJob); //if the time isn't 0, add the current job to the ready queue
				}
				for(int m = 0; m < mem.length; m++)
                { //loops through all the memory segments
					if(mem[m].getInUse() == false) //if the memory segment is not in use
						memoryLoss += mem[m].getSize(); //add the amount of memory that is not being used to the memoryLoss variable
				}

				w.write("TIME" + "\t" + "ID" + "\t" + " SEGMENT" + "\t" + " MEM REQUEST" + "\t" + " TIME REM" + "\t\t" + "MESSAGES" + "\n");
               
				if(rQueue.peek() != null) //if something is in the ready queue
					print(rQueue, i, w); //print the ready queue
				if(!jQueue.isEmpty()) //if there is something in the job queue
					print(jQueue, i, w); //print the job queue
				if(!cQueue.isEmpty()) //if there is something in the completed queue
					print(cQueue, i, w); //print the completed queue.  All of these together will print the contents of every queue
                
				w.write("Memory that is not being used: " + memoryLoss + "\n"); //this will show the user how much memory is not being used
                w.write("Number of jobs that are waiting: " + jQueue.size() + "\n\n"); //this will show how many jobs are in the job queue waiting
				memoryLoss = 0;
            }

			w.write("Completed jobs: " + cQueue.size()); //prints out the number of jobs that have been completed.
			w.close(); //closes the output file
        }
		catch(Exception e){System.out.print(e.toString());}	
    }
    //Function used to write the queue contents to the output file
    private static void print(Queue<Job> output, int timeSeg, BufferedWriter w) //this function will write the contents.
    {
	    if(!output.isEmpty())
        { //if there is anything in the output list
		    int len = output.size(); //the length is equal to the size of the output list
		    Job[] job = output.toArray(new Job[len]); //new job list is set to be the output of the array by making a new Job with the size of the len variable
		    for(int i = 0; i < job.length; i++)
            { //while in length of the created job list
			    String idNumber = String.valueOf(job[i].getjobID()); //the id of the job is written
			    String remaining = String.valueOf(job[i].getRemaining()); //the remaining time is written
			    String segment = String.valueOf(job[i].getmemSeg()); //the segment of memory of the job is written
				
			    if(idNumber.length() == 1) 
				    idNumber += " ";
			    if(remaining.length() == 1) 
				    remaining += " ";
			    if(segment.length() == 1) 
				    segment += " ";	
				try{ 
					w.write(timeSeg + "\t\t"); //these will format the information to match the info bar
					w.write(idNumber + "\t\t");
					w.write(segment + "\t\t\t");
					w.write(job[i].getSize() + "\t\t\t\t");
					w.write(remaining + "\t\t\t");
					w.write(job[i].getStatus() + "\n");
				}
				catch(Exception e){System.out.println(e.toString());} 
		    }
	    }
    }	
}